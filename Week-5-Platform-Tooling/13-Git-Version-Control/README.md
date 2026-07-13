# Module 13 - Git & Version Control Cheat Sheet

This module is a hands-on reference for the Git workflows used throughout the
Digital Nurture 5.0 Java FSE program. It covers the commands you will use
every day, plus the branching/collaboration models referenced in the program
handbook.

## 1. Creating / Cloning a Repository

```bash
# Start a brand-new repository in the current folder
git init

# Clone an existing remote repository
git clone https://github.com/<org>/<repo>.git

# Clone into a specific local folder name
git clone https://github.com/<org>/<repo>.git my-folder
```

`git init` creates the hidden `.git` directory that turns a plain folder into
a Git repository. `git clone` does that for you automatically and also wires
up the `origin` remote to point back at the source repository.

## 2. The Core Loop: add / commit / status / log

```bash
# See what has changed (untracked, modified, staged files)
git status

# Stage specific files, or everything
git add path/to/file.java
git add .

# Commit staged changes with a message
git commit -m "Add TDD example for calculator service"

# Inspect history
git log
git log --oneline
git log --oneline --graph --all
git log -p path/to/file.java     # show diffs per commit
```

Workflow: **edit files -> `git status` to review -> `git add` to stage ->
`git commit` to snapshot -> `git log` to verify history.**

## 3. Branching

```bash
# List branches
git branch

# Create a branch (does not switch to it)
git branch feature/login-form

# Create AND switch to a new branch (classic syntax)
git checkout -b feature/login-form

# Create AND switch to a new branch (modern syntax, Git 2.23+)
git switch -c feature/login-form

# Switch between existing branches
git checkout main
git switch main
```

Branches are cheap, lightweight pointers to commits. Create a new branch for
every feature, bug fix, or experiment so `main` always stays deployable.

## 4. Merging & Resolving a Conflict

```bash
# From the branch that should receive the changes
git switch main
git merge feature/login-form
```

If both branches changed the same lines, Git stops and marks the file:

```
<<<<<<< HEAD
System.out.println("Welcome to the app");
=======
System.out.println("Welcome, please log in");
>>>>>>> feature/login-form
```

Steps to resolve:

1. Open the conflicted file and decide on the final content, removing the
   `<<<<<<<`, `=======`, and `>>>>>>>` markers.
2. Stage the resolved file: `git add path/to/file.java`
3. Complete the merge: `git commit` (Git pre-fills a merge commit message).
4. Verify with `git log --oneline --graph --all`.

If you want to abandon a messy merge instead: `git merge --abort`.

## 5. Remotes: Pushing & Pulling

```bash
# Connect a local repo to a remote named "origin"
git remote add origin https://github.com/<org>/<repo>.git

# View configured remotes
git remote -v

# First push of a new branch: set the upstream tracking branch
git push -u origin main

# Subsequent pushes only need
git push

# Fetch + merge the latest remote changes into your current branch
git pull

# Fetch without merging (inspect before integrating)
git fetch origin
```

## 6. Forking + Pull Request Workflow

Forking is the standard workflow for contributing to a repository you do not
have write access to (most open-source projects, and many company repos with
strict branch protection):

1. **Fork** the upstream repository on GitHub - this creates a full copy
   under your own account.
2. **Clone your fork** locally: `git clone https://github.com/<you>/<repo>.git`.
3. **Add the original repo as a second remote** (conventionally named
   `upstream`) so you can stay in sync:
   `git remote add upstream https://github.com/<org>/<repo>.git`
4. **Create a feature branch** in your fork: `git switch -c feature/x`.
5. **Commit and push** your changes to your fork: `git push -u origin feature/x`.
6. **Open a Pull Request (PR)** from `your-fork:feature/x` into
   `upstream:main` via the GitHub UI.
7. **Address review feedback** by pushing more commits to the same branch -
   the PR updates automatically.
8. Once approved, a maintainer **merges** the PR (merge commit, squash, or
   rebase, depending on project convention) and you can delete the branch.
9. Keep your fork current: `git fetch upstream && git merge upstream/main`
   (or `git rebase upstream/main`) on your local `main`.

## 7. Collaboration Workflows

**Centralized Workflow.** Everyone commits directly to a single shared
branch (usually `main`), much like a traditional SVN setup. It is simple and
works for small teams or trivial projects, but offers no isolation - every
commit is immediately visible to everyone, and conflicts must be resolved
constantly as people push. There is no code-review gate built into the
model itself.

**Feature Branch Workflow.** Every change, no matter how small, gets its own
branch (`feature/*`, `bugfix/*`) cut from `main`. Developers push the branch
to the shared remote and open a Pull Request for review before merging back.
This keeps `main` always releasable, enables CI to validate each branch in
isolation, and is the default workflow for most internal teams (including
this training program).

**Forking Workflow.** Each contributor works in their own personal copy
(fork) of the repository instead of a shared branch on the same remote.
Contributors push to their fork and open PRs from fork to upstream, giving
maintainers full control over what gets merged. It is the standard model for
open-source projects and for large organizations where most contributors
should not have direct push access to the canonical repo.

**Gitflow Workflow.** A stricter branching model with long-lived `main`
(production) and `develop` (integration) branches, plus supporting branches
for `feature/*`, `release/*`, and `hotfix/*`. Features branch from and merge
back into `develop`; when `develop` is stable, a `release/*` branch is cut,
stabilized, and merged into both `main` and `develop`; urgent production
fixes branch from `main` via `hotfix/*`. Gitflow suits projects with
scheduled releases and formal versioning, at the cost of more process
overhead than a simple feature-branch workflow.

## 8. Related Files in This Module

- `scripts/setup-repo.sh` / `scripts/setup-repo.bat` - runnable demos of
  init -> commit -> branch -> merge.
- `sample.gitignore` - a reference `.gitignore` for mixed Java/Node projects.
- `CONTRIBUTING.md` - the contribution workflow contributors to this repo
  are expected to follow.
