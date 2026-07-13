# Book Tracker - React SPA (Module 11)

A small Vite + React single-page app that lists, adds, and deletes books
against a REST API. It's designed to point at the Week 3 Module 9
Spring Boot `/api/books` backend, but any REST API exposing the same
`{ id, title, author, isbn, price }` shape works.

## Objectives

- Build a component tree with props flowing top-down and state lifted to
  the right level (`App.jsx`).
- Fetch data from a REST backend with `axios` inside `useEffect`.
- Handle loading / empty / error states with conditional rendering.
- Render lists correctly with `.map()` and a stable `key`.
- Build a controlled form with client-side validation.
- Compare ES6 class components against function components + hooks.

## Setup & run

```bash
cd 11-React-SPA/book-tracker-app
npm install

# point the app at your backend
cp .env.example .env
# then edit .env if your backend isn't on http://localhost:8080

npm run dev
```

The dev server starts on `http://localhost:5173` (Vite's default port -
Module 12's `launch.json` assumes this port). Make sure the backend
(Week 3 Module 9, or Module 10's `api-gateway` if you're routing `/api/books`
through it) is running and reachable at the URL in `.env`, otherwise
`BookList` will show its error state.

```bash
npm run build     # production build to dist/
npm run preview   # preview the production build locally
```

## Where each React concept lives

| Concept (handbook Module 9 / React topics) | File |
|---|---|
| JSX syntax | every `.jsx` file |
| Functional components & props | `src/components/BookItem.jsx`, `src/components/ConditionalMessage.jsx` (both receive props and render JSX from them) |
| State with hooks (`useState`) | `src/components/BookForm.jsx`, `src/components/BookList.jsx`, `src/App.jsx`, `src/components/FunctionCounter.jsx` |
| Side effects / data fetching (`useEffect`) | `src/components/BookList.jsx` (fetch on mount + on `refreshKey` change), `src/components/FunctionCounter.jsx` (mount/unmount logging) |
| Conditional rendering (`&&`, ternary) | `src/components/ConditionalMessage.jsx`, loading/empty/error branches in `src/components/BookList.jsx` |
| Lists & keys | `src/components/BookList.jsx` (`books.map(book => <BookItem key={book.id} ... />)`) |
| Controlled forms & validation | `src/components/BookForm.jsx` |
| ES6 class component (state, lifecycle, `render()`) | `src/components/ClassCounter.jsx` |
| Function component equivalent of the class above | `src/components/FunctionCounter.jsx` (see both side by side via `src/components/CounterDemo.jsx`) |
| API calls / service layer (axios) | `src/services/bookApi.js` |
| Environment-based configuration | `.env.example` + `import.meta.env.VITE_API_BASE_URL` in `src/services/bookApi.js` |
| App composition / lifting state up | `src/App.jsx` (owns `books` and `refreshKey`, passes props/callbacks down to `BookForm`, `BookList`, `ConditionalMessage`) |

## Project structure

```
book-tracker-app/
├── index.html
├── package.json
├── vite.config.js
├── .env.example
└── src/
    ├── main.jsx
    ├── App.jsx
    ├── index.css
    ├── components/
    │   ├── BookForm.jsx
    │   ├── BookList.jsx
    │   ├── BookItem.jsx
    │   ├── ConditionalMessage.jsx
    │   ├── ClassCounter.jsx
    │   ├── FunctionCounter.jsx
    │   └── CounterDemo.jsx
    └── services/
        └── bookApi.js
```
