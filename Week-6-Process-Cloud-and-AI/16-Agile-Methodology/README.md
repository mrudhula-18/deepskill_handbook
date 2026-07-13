# 16 — Agile Methodology

Deep-skilling exercise applying Scrum to a fictional "Online Bookstore" product: a prioritized backlog, a Sprint 1 plan, INVEST-annotated user stories, and a sprint burndown chart.

## Scrum Roles

- **Product Owner** — owns the product backlog, sets priority based on business value, and accepts or rejects completed work against acceptance criteria.
- **Scrum Master** — facilitates ceremonies, removes impediments, and shields the team from scope disruption mid-sprint.
- **Development Team** — a cross-functional, self-organizing group that estimates, builds, and tests the increment; collectively accountable for the Sprint Goal.

## Scrum Ceremonies

- **Sprint Planning** — the team pulls backlog items into the sprint (see `sprint-1-backlog.md`), agrees a Sprint Goal, and breaks stories into tasks.
- **Daily Scrum** — a 15-minute daily sync on progress, plan for the day, and blockers.
- **Sprint Review** — the team demos the increment to stakeholders at the end of the sprint and gathers feedback.
- **Sprint Retrospective** — the team reflects on process (what went well, what didn't, what to change) after the review.
- **Backlog Refinement** — an ongoing activity where the Product Owner and team clarify, split, and re-estimate upcoming backlog items.

## Scrum Artifacts

- **Product Backlog** (`product-backlog.md`) — the full, ordered list of everything that might be built.
- **Sprint Backlog** (`sprint-1-backlog.md`) — the subset of backlog items committed to the current sprint, plus their task breakdown.
- **Increment** — the sum of all completed, "Done" backlog items, usable and potentially shippable.

## Definition of Done

A backlog item is "Done" only when: code is written and peer-reviewed, unit and integration tests pass, the feature meets its acceptance criteria, documentation is updated, and the change is merged and deployed to a staging environment.

## Story Points and Planning Poker

Story points measure relative effort/complexity/uncertainty rather than raw hours, which keeps estimates stable across engineers of different speed. Teams typically use a Fibonacci-like scale (1, 2, 3, 5, 8, 13, ...) because the widening gaps force a real choice between sizes instead of false precision at the high end. Planning poker operationalizes this: each team member privately picks a card representing their estimate for a story, everyone reveals simultaneously, and any large spread is discussed (the highest and lowest estimators explain their reasoning) before re-voting until the team converges — this avoids anchoring bias from whoever speaks first.

## Files in This Module

- [`product-backlog.md`](./product-backlog.md) — full prioritized backlog (14 items).
- [`sprint-1-backlog.md`](./sprint-1-backlog.md) — Sprint 1 goal, committed items, and task breakdown.
- [`user-stories.md`](./user-stories.md) — 5 INVEST-annotated user stories with Given-When-Then acceptance criteria.
- [`burndown-data.csv`](./burndown-data.csv) — 10-day ideal vs. actual remaining-points data for Sprint 1.
- [`plot_burndown.py`](./plot_burndown.py) / [`requirements.txt`](./requirements.txt) — chart generator.

## Running the Burndown Chart

```bash
pip install -r requirements.txt
python plot_burndown.py
```

This reads `burndown-data.csv` and writes `burndown-chart.png` (ideal vs. actual burndown lines) into this directory.
