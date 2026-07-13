# Module 15 - Docker & Containerization

## 1. Bringing Up the Full Stack with Docker Compose

From this folder (`15-Docker-Containerization/`), after adjusting the
`build.context` paths in `docker-compose.yml` to point at your real
backend/frontend module folders:

```bash
# Build all images defined in docker-compose.yml
docker compose build

# Start all services in the background (detached mode)
docker compose up -d

# Follow the logs from all services (Ctrl+C to stop watching, containers
# keep running)
docker compose logs -f

# Tear everything down AND remove the named volume (db-data), i.e. wipe
# the database too
docker compose down -v
```

Other useful variants:

```bash
docker compose up -d --build     # rebuild images then start, in one step
docker compose ps                 # list running services
docker compose logs -f backend    # follow logs for a single service
docker compose down               # stop and remove containers, keep volumes
```

## 2. Building & Running Each Dockerfile Standalone

### Backend

```bash
docker build -f backend.Dockerfile -t dn5-backend:latest ../../Week-3-Data-and-REST/09-Spring-REST-SpringBoot3

docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/dn5_capstone \
  -e SPRING_DATASOURCE_USERNAME=dn5_user \
  -e SPRING_DATASOURCE_PASSWORD=dn5_password \
  dn5-backend:latest
```

### Frontend

```bash
docker build -f frontend.Dockerfile -t dn5-frontend:latest ../../Week-4-Frontend-Frameworks/11-React-App

docker run --rm -p 80:80 dn5-frontend:latest
```

Then browse to `http://localhost` (frontend) and `http://localhost:8080`
(backend API), or use the compose stack so both are wired together
automatically.

## 3. Docker Networking, Volumes & Storage Drivers

**Networking.** By default, `docker compose` creates a single private
bridge network for the whole stack and attaches every service to it.
Containers can reach each other by **service name** as a hostname (this is
why `docker-compose.yml` sets `SPRING_DATASOURCE_URL` to
`jdbc:postgresql://db:5432/...` - `db` resolves via Docker's embedded DNS to
the database container's IP on that network). Only ports explicitly
published with `ports: "host:container"` are reachable from the host
machine or outside world; everything else stays private to the compose
network.

**Volumes.** Containers are ephemeral - anything written inside a
container's writable layer disappears when the container is removed. Named
volumes (like `db-data` in `docker-compose.yml`) are managed by Docker
outside of any single container's lifecycle, so data (e.g. Postgres's
`/var/lib/postgresql/data`) survives `docker compose down` (though not
`docker compose down -v`, which explicitly deletes volumes too). Bind
mounts are the alternative - mapping a host path directly into the
container - useful for local development (e.g. mounting source code for
hot reload) but not used in this module's production-style Dockerfiles.

**Storage drivers.** The storage driver (commonly `overlay2` on Linux
hosts) is what implements each image's layered filesystem - every
`RUN`/`COPY`/`ADD` instruction in a Dockerfile creates a new read-only
layer, and layers are shared/cached across images and builds whenever
their content is identical. This is why both Dockerfiles in this module
copy dependency manifests (`pom.xml`, `package*.json`) and install
dependencies *before* copying the rest of the source: as long as the
manifest doesn't change, Docker reuses the cached dependency-install layer
on the next build instead of re-downloading everything, which meaningfully
speeds up iterative builds.

## 4. Files in This Module

- `backend.Dockerfile` - multi-stage Spring Boot build (Maven build stage +
  slim JRE runtime stage).
- `frontend.Dockerfile` - multi-stage React build (Node build stage + nginx
  runtime stage).
- `nginx.conf` - SPA-friendly nginx config used by the frontend image.
- `docker-compose.yml` - wires up Postgres + backend + frontend for local
  development.
