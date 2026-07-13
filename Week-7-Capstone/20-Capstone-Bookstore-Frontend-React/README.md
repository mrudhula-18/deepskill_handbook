# Module 20 - Capstone Bookstore Frontend (React)

A Vite + React (JavaScript) single-page application that consumes the Module 19 Bookstore Backend REST API
(`../19-Capstone-Bookstore-Backend`). Routing is handled with `react-router-dom`; HTTP calls go through
`axios` via `src/services/bookApi.js`.

The app lives in `bookstore-frontend/`.

## Getting Started

```bash
cd bookstore-frontend
npm install
cp .env.example .env
npm run dev
```

The dev server starts at `http://localhost:5173`. Make sure the Module 19 backend is running at
`http://localhost:8080` first (`mvn spring-boot:run` from `19-Capstone-Bookstore-Backend`), or set
`VITE_API_BASE_URL` in `.env` to point at wherever it's running.

```bash
npm run build     # production build to dist/
npm run preview   # preview the production build locally
```

## Environment Variables

| Variable              | Default                              | Purpose                          |
|------------------------|---------------------------------------|-----------------------------------|
| `VITE_API_BASE_URL`   | `http://localhost:8080/api/v1`       | Base URL for the backend REST API |

Copy `.env.example` to `.env` and adjust as needed. In the Docker/CI setup (Module 21), this value is
instead baked in at image build time via a Docker build `ARG`.

## Project Structure

```
bookstore-frontend/
├── src/
│   ├── main.jsx                 Bootstraps React + BrowserRouter
│   ├── App.jsx                  NavBar + <Routes>
│   ├── services/bookApi.js      axios instance + API functions
│   ├── pages/
│   │   ├── BookListPage.jsx     Paginated list + search + delete
│   │   ├── BookDetailPage.jsx   Single book view
│   │   └── BookFormPage.jsx     Create/Edit form (shared component)
│   └── components/
│       ├── NavBar.jsx
│       ├── LoadingSpinner.jsx
│       └── ErrorBanner.jsx
├── index.html
├── vite.config.js
├── package.json
├── Dockerfile                   Multi-stage build (see Module 21)
├── nginx.conf                   SPA fallback routing for the Dockerized build
└── .env.example
```

## Routes -> Backend Endpoints

| Route               | Page               | Backend endpoint(s) used                                             |
|----------------------|---------------------|------------------------------------------------------------------------|
| `/`                  | `BookListPage`     | `GET /api/v1/books` (paged), `GET /api/v1/books/search?keyword=`, `DELETE /api/v1/books/{id}` |
| `/books/:id`         | `BookDetailPage`   | `GET /api/v1/books/{id}`, `DELETE /api/v1/books/{id}`                 |
| `/books/new`         | `BookFormPage`     | `GET /api/v1/authors`, `GET /api/v1/categories`, `POST /api/v1/books` |
| `/books/:id/edit`    | `BookFormPage`     | `GET /api/v1/books/{id}`, `GET /api/v1/authors`, `GET /api/v1/categories`, `PUT /api/v1/books/{id}` |

`BookFormPage` is a single component used for both create and edit: the `mode` prop (`"create"` /
`"edit"`) combined with the `:id` route param decides which mode is active, whether to pre-fill the
form (via `useParams` + `useEffect`), and whether to call `createBook` or `updateBook` on submit.

## Notes on Validation

The form performs client-side validation mirroring the backend's `@NotBlank` / `@NotNull` / `@Positive` /
`@PositiveOrZero` constraints (title/isbn non-blank, price > 0, stockQuantity >= 0, author/category
selected). If the backend still rejects the request (e.g. a race on referenced ids), the server's error
message is surfaced via the `ErrorBanner` component.
