# Contributing Guide

This document describes the feature-branch contribution workflow expected
for changes in this repository (and, as a training exercise, for any DN5.0
Java FSE assignment repo modeled after it).

## 1. Branch Naming Convention

Create a new branch for every unit of work off the latest `main`:

| Prefix        | Purpose                                   | Example                          |
|---------------|--------------------------------------------|-----------------------------------|
| `feature/*`   | New functionality                          | `feature/add-order-service`       |
| `bugfix/*`    | Fixing a defect in existing functionality  | `bugfix/null-pointer-on-checkout` |
| `hotfix/*`    | Urgent production fix                      | `hotfix/payment-gateway-timeout`  |
| `chore/*`     | Tooling, build, or dependency maintenance  | `chore/upgrade-spring-boot`       |
| `docs/*`      | Documentation-only changes                 | `docs/update-readme`              |

Rules:
- Use lowercase, hyphen-separated, descriptive slugs.
- Reference a ticket/issue number if one exists, e.g. `feature/DN5-142-login-form`.
- Keep branches short-lived and focused on a single concern.

## 2. Commit Message Convention

Follow a Conventional-Commits-style format:

```
<type>(<optional scope>): <short summary>

<optional body explaining what/why>

<optional footer, e.g. Refs #123>
```

Common `<type>` values:

- `feat` - a new feature
- `fix` - a bug fix
- `docs` - documentation only
- `test` - adding or fixing tests
- `refactor` - code change that neither fixes a bug nor adds a feature
- `chore` - build process or tooling changes

Examples:

```
feat(orders): add support for partial refunds
fix(auth): correct token expiry check that logged users out early
docs(readme): document new environment variables
```

Guidelines:
- Keep the summary line under ~72 characters, written in the imperative
  mood ("add", not "added" or "adds").
- Use the body to explain *why* a change was made when it is not obvious.
- Make commits small and focused; do not bundle unrelated changes.

## 3. Opening a Pull Request

1. Push your branch: `git push -u origin feature/your-branch`.
2. Open a PR targeting `main` (or `develop`, if the repo uses Gitflow).
3. Fill in the PR description: what changed, why, and how it was tested.
4. Link the related issue/ticket, if any.
5. Ensure CI (build + tests) passes before requesting review.
6. Keep the PR scoped - prefer several small PRs over one large one.

## 4. Code Review Expectations

- At least one approving review is required before merge.
- Reviewers check for: correctness, test coverage, readability, adherence
  to project conventions, and potential security/performance issues.
- Authors should respond to every comment (either by addressing it or
  explaining why not) rather than resolving silently.
- Prefer requesting changes over blocking discussions in review threads;
  keep the tone constructive and specific.
- Re-request review after pushing fixes so reviewers know it's ready again.

## 5. Merge Strategy

- **Squash merge** (default for feature/bugfix branches): collapses all
  commits on the branch into a single commit on `main`, keeping history
  clean and making it easy to revert an entire feature as one unit. Use
  this for typical, incremental feature work.
- **Merge commit**: preserves the full commit history of the branch and
  creates an explicit merge commit. Use this for large or multi-author
  features where the individual commit history has value (e.g. release
  branches in a Gitflow setup, or long-running epics).
- Avoid rebasing/force-pushing shared branches that others have already
  pulled from, to prevent rewriting history out from under collaborators.
- Delete the branch after it has been merged to keep the branch list clean.
