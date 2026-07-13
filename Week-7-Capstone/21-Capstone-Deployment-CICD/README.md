# Module 21 - Capstone Deployment & CI/CD

Ties Module 19 (Spring Boot backend) and Module 20 (React frontend) together into a single
`docker compose` stack, plus a GitHub Actions pipeline that builds and tests both, closing the loop on
the full DN5.0 Java FSE 7-week program.

## Layout

```
21-Capstone-Deployment-CICD/
├── docker-compose.yml
├── .github/workflows/capstone-ci.yml
└── README.md

19-Capstone-Bookstore-Backend/
└── Dockerfile                                  (multi-stage: maven build -> temurin JRE run)

20-Capstone-Bookstore-Frontend-React/bookstore-frontend/
├── Dockerfile                                  (multi-stage: node build -> nginx run)
└── nginx.conf                                  (SPA fallback routing)
```

## Running the Full Stack Locally

From this directory:

```bash
docker compose up --build
```

This builds:

- `backend` from `../19-Capstone-Bookstore-Backend/Dockerfile` (Maven multi-stage build ->
  `eclipse-temurin:17-jre-alpine` runtime), exposed on `http://localhost:8080`.
- `frontend` from `../20-Capstone-Bookstore-Frontend-React/bookstore-frontend/Dockerfile` (Node 18
  build -> nginx runtime), exposed on `http://localhost:80`.

Open `http://localhost` in a browser. The frontend calls the backend at `http://localhost:8080/api/v1`
from the browser (that URL is baked into the static JS bundle at image-build time via the
`VITE_API_BASE_URL` Docker build `ARG` declared in `docker-compose.yml` and consumed by the frontend's
`Dockerfile`).

Stop the stack:

```bash
docker compose down
```

## How the Frontend's Backend URL Is Wired

Vite inlines `import.meta.env.VITE_API_BASE_URL` at **build time**, not at container start time. So the
simplest correct approach (used here) is:

1. `docker-compose.yml` passes `VITE_API_BASE_URL` as a `build.args` entry on the `frontend` service.
2. The frontend `Dockerfile` declares `ARG VITE_API_BASE_URL` and `ENV VITE_API_BASE_URL=$VITE_API_BASE_URL`
   before running `npm run build`, so the value is compiled into the static bundle nginx later serves.

If you need to change the backend URL after the image is built, rebuild the frontend image with a new
`--build-arg VITE_API_BASE_URL=...` rather than trying to change an environment variable at container
runtime.

## CI/CD Pipeline

`.github/workflows/capstone-ci.yml` defines three jobs on every push/PR to `main`:

1. **backend-tests** - sets up JDK 17, runs `mvn -B -f Week-7-Capstone/19-Capstone-Bookstore-Backend test`
   (unit + integration tests from Module 19, including the H2-backed `BookControllerIT`).
2. **frontend-build** - sets up Node 18, runs `npm ci && npm run build` inside
   `Week-7-Capstone/20-Capstone-Bookstore-Frontend-React/bookstore-frontend`.
3. **docker-build** (`needs: [backend-tests, frontend-build]`) - builds both Docker images with
   `docker/build-push-action` tagged `bookstore-backend:ci` / `bookstore-frontend:ci`, with `push: false`
   so it only proves the Dockerfiles build successfully — nothing is pushed to a registry.

> This workflow file lives under `21-Capstone-Deployment-CICD/.github/workflows/` to keep every capstone
> artifact inside the `Week-7-Capstone` tree. GitHub Actions only auto-discovers workflows in a
> **repo-root** `.github/workflows/` directory, so to actually run it on GitHub, copy/symlink this file to
> `<repo-root>/.github/workflows/capstone-ci.yml`.

## Mapping Back to Earlier Weeks

This module is the practical capstone of the DevOps/CI-CD and Docker material from Week 5
(`Week-5-Platform-Tooling`):

| Week 5 concept                  | Applied here                                                        |
|----------------------------------|------------------------------------------------------------------------|
| Git branching & PR workflow     | CI triggers on `push`/`pull_request` to `main`                         |
| Dockerfiles & multi-stage builds| Backend and frontend `Dockerfile`s (build stage -> slim runtime stage) |
| Docker Compose orchestration    | `docker-compose.yml` wiring `backend` + `frontend` together            |
| CI pipelines (build/test gates) | `capstone-ci.yml` - Maven tests, npm build, then Docker image builds   |

Together with Module 19 (Spring Boot REST API + JPA + validation + testing) and Module 20 (React SPA +
routing + Axios), this module closes the loop for the full 7-week DN5.0 Java FSE deep-skilling program:
source code, automated tests, containerization, and a CI pipeline that gates on both.
