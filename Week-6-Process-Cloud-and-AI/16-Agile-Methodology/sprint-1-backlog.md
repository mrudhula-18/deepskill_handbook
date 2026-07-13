# Sprint 1 Backlog — Online Bookstore

**Sprint:** Sprint 1
**Start date:** 2026-07-13 (Monday)
**End date:** 2026-07-24 (Friday, 2-week sprint)

## Sprint Goal

Ship a working "browse and buy" walking skeleton: a shopper can register, search the catalog, view a book's details, and manage a shopping cart. Checkout and payment are out of scope for this sprint and land in Sprint 2.

## Items Pulled Into Sprint 1

| ID | Title | Story Points | Status |
|----|-------|---------------|--------|
| BS-001 | User can register and log in with email/password | 5 | Done |
| BS-002 | User can search the catalog by title, author, or ISBN | 8 | Done |
| BS-003 | User can view a book's detail page (price, blurb, cover, reviews) | 5 | In Progress |
| BS-004 | User can add/remove items from a shopping cart | 5 | In Progress |
| BS-006 | User receives an order-confirmation email after checkout | 3 | To Do |

Sprint commitment: 26 story points.

## Task Breakdown

### BS-001 — Register and log in
1. Design `users` table schema (email, hashed password, created_at).
2. Build `POST /api/register` and `POST /api/login` endpoints.
3. Add password hashing (BCrypt) and input validation.
4. Build registration/login forms on the front end.

### BS-002 — Search the catalog
1. Add full-text search index on `title`, `author`, `isbn` columns.
2. Build `GET /api/books/search?q=` endpoint.
3. Build search bar and results-list UI component.
4. Write integration tests covering partial-match and no-result cases.

### BS-003 — Book detail page
1. Build `GET /api/books/{id}` endpoint returning book, price, and reviews.
2. Build detail-page UI (cover image, blurb, price, review list).
3. Handle "book not found" (404) state in the UI.

### BS-004 — Shopping cart
1. Design cart data model (session-based or persisted per user).
2. Build `POST /api/cart/items` and `DELETE /api/cart/items/{id}` endpoints.
3. Build cart UI widget (add, remove, quantity update).
4. Write unit tests for cart total calculation.

### BS-006 — Order-confirmation email
1. Integrate transactional email provider (e.g., SES/SMTP).
2. Create confirmation email template (order number, items, total).
3. Trigger email send on successful checkout event.
