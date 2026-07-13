# User Stories — Online Bookstore

Each story follows the standard format, is annotated against INVEST, and carries acceptance criteria in Given-When-Then form.

---

## Story 1 — Registration

**As a** first-time visitor,
**I want** to register an account with my email and a password,
**so that** I can save my details and check out faster on future visits.

**INVEST:**
- **Independent:** Can be built and shipped without any other story; only depends on the database being available.
- **Negotiable:** The exact password-strength rules and whether social sign-in is included can be discussed with the team.
- **Valuable:** Without an account, no purchase history, wishlist, or saved cart is possible — this unlocks the rest of the product.
- **Estimable:** Well-understood scope (form, endpoint, hashing, validation); the team sized it at 5 points.
- **Small:** Fits inside a single sprint alongside other Sprint 1 items.
- **Testable:** Success/failure of registration is observable via the database and the confirmation screen.

**Acceptance Criteria:**
1. Given a visitor on the registration page, When they submit a valid, unused email and a password of at least 8 characters, Then an account is created and they are redirected to their account dashboard.
2. Given a visitor on the registration page, When they submit an email that is already registered, Then they see an inline error "This email is already registered" and no duplicate account is created.
3. Given a visitor on the registration page, When they submit a password shorter than 8 characters, Then the form is rejected with a validation message and no account is created.

---

## Story 2 — Catalog search

**As a** shopper,
**I want** to search for books by title, author, or ISBN,
**so that** I can quickly find a specific book I already have in mind.

**INVEST:**
- **Independent:** Search works against the existing catalog table with no dependency on cart or checkout stories.
- **Negotiable:** Whether search is fuzzy/typo-tolerant or exact-match is an open implementation detail.
- **Valuable:** Search is the primary discovery path for shoppers who know what they want.
- **Estimable:** Scope is clear enough to size at 8 points (indexing plus UI).
- **Small:** Deliverable within one sprint.
- **Testable:** Query results can be asserted for a fixed set of seeded books.

**Acceptance Criteria:**
1. Given the catalog contains "The Pragmatic Programmer", When a shopper searches "pragmatic", Then the book appears in the results list.
2. Given a shopper searches for an ISBN that does not exist in the catalog, When they submit the search, Then they see a "No books found" message instead of an error.
3. Given a shopper searches by author name, When multiple books by that author exist, Then all matching books are returned, sorted by relevance.

---

## Story 3 — Book detail page

**As a** shopper,
**I want** to view a book's full details including price, description, and reviews,
**so that** I can decide whether to buy it.

**INVEST:**
- **Independent:** Reads from the catalog and reviews tables; does not require cart or checkout to exist.
- **Negotiable:** Layout and which metadata fields are shown (e.g., page count, publisher) can change without affecting the story's intent.
- **Valuable:** Directly informs the shopper's purchase decision, driving conversion.
- **Estimable:** Team sized this at 5 points based on similar past detail-page work.
- **Small:** One page, one endpoint, ships within a sprint.
- **Testable:** Rendered fields can be checked against seeded book data.

**Acceptance Criteria:**
1. Given a valid book ID, When a shopper navigates to its detail page, Then the title, price, cover image, and blurb are displayed.
2. Given a book has existing customer reviews, When the detail page loads, Then the average star rating and the most recent reviews are shown.
3. Given an invalid or removed book ID, When a shopper navigates to that detail page, Then a "Book not found" page is shown instead of an error stack trace.

---

## Story 4 — Shopping cart

**As a** shopper,
**I want** to add and remove books from a shopping cart,
**so that** I can collect multiple items before paying for them together.

**INVEST:**
- **Independent:** Cart state can be built and tested without the checkout/payment story being complete.
- **Negotiable:** Whether the cart persists across devices (server-side) or is session/local-only is a decision the team can defer.
- **Valuable:** A cart is a prerequisite for any multi-item purchase — core to the shopping flow.
- **Estimable:** Sized at 5 points; the team has built similar cart features before.
- **Small:** Add/remove/update-quantity fits in one sprint.
- **Testable:** Cart contents and totals can be asserted after each action.

**Acceptance Criteria:**
1. Given a shopper is viewing a book, When they click "Add to Cart", Then the book appears in the cart with quantity 1 and the cart total updates.
2. Given a book is already in the cart, When the shopper adds it again, Then its quantity increases by 1 rather than creating a duplicate line item.
3. Given a book is in the cart, When the shopper clicks "Remove", Then the item disappears from the cart and the total recalculates.

---

## Story 5 — Order confirmation email

**As a** shopper,
**I want** to receive an email confirming my order after checkout,
**so that** I have a record of what I purchased and when it will arrive.

**INVEST:**
- **Independent:** Only depends on a checkout event firing; can be built in parallel with the payment UI.
- **Negotiable:** Exact email template wording/branding can change without affecting the underlying trigger logic.
- **Valuable:** Builds shopper trust and reduces "did my order go through?" support requests.
- **Estimable:** Sized at 3 points — a template plus an event-triggered send.
- **Small:** Single email, single trigger point, fits well within a sprint.
- **Testable:** Email delivery and content can be verified against a test inbox or mock mail server.

**Acceptance Criteria:**
1. Given a shopper completes checkout successfully, When the order is placed, Then an email is sent to their registered address within 2 minutes.
2. Given an order contains 3 items, When the confirmation email is generated, Then all 3 items, their prices, and the order total are listed in the email body.
3. Given the email provider is temporarily unavailable, When the send fails, Then the failure is logged and retried rather than silently dropped.
