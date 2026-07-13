# Module 14 - DevOps & CI/CD

## 1. CI vs CD

**Continuous Integration (CI)** is the practice of merging code changes into
a shared branch frequently (multiple times a day) and automatically building
and testing each change. The goal is to catch integration problems, broken
builds, and failing tests as early as possible - ideally within minutes of a
commit or pull request being opened.

**Continuous Delivery/Deployment (CD)** picks up where CI leaves off.
- *Continuous Delivery* means every change that passes CI is automatically
  packaged into a release-ready artifact (a jar, a Docker image, a build
  bundle) that *could* be deployed at any time, though a human still decides
  when to actually release it.
- *Continuous Deployment* goes one step further and automatically deploys
  every change that passes all pipeline stages straight to production,
  with no manual approval gate.

In short: **CI answers "does this change work and integrate cleanly?"**,
while **CD answers "how do we get a working change safely into users'
hands?"**

## 2. Walkthrough: `.github/workflows/ci.yml`

This workflow demonstrates a typical GitHub Actions CI setup for a
polyglot (Java + JavaScript) training repo:

1. **Triggers** - `on: push` and `on: pull_request` for the `main` branch,
   so CI runs both when code lands on `main` and while a PR is still open.
2. **Job `java-build-and-test`** runs on `ubuntu-latest`:
   - `actions/checkout@v4` pulls the repository content onto the runner.
   - `actions/setup-java@v4` installs Eclipse Temurin JDK 17 and enables
     Maven dependency caching.
   - Two separate `mvn -f <module-path> -B test` steps run the unit tests
     for two representative modules (Week 2's TDD/JUnit5/Mockito module and
     Week 3's Spring REST module) in **batch mode** (`-B`, no interactive
     output). The comments in the file call out that these paths are
     placeholders - point them at whichever modules you want validated.
3. **Job `frontend-build`** runs independently (in parallel) on
   `ubuntu-latest`:
   - `actions/checkout@v4` checks out the code again for this job (each job
     gets a fresh runner/workspace).
   - `actions/setup-node@v4` installs Node 18 and enables npm caching.
   - `npm ci` performs a clean, lockfile-exact dependency install (faster
     and more reproducible than `npm install` in CI).
   - `npm run build` produces the production build of the React app.
4. Because the two jobs have no `needs:` dependency between them, GitHub
   Actions runs them **concurrently**, giving faster overall feedback.

## 3. Walkthrough: `Jenkinsfile`

This is a **declarative pipeline** (the modern, structured Jenkins syntax,
as opposed to the older scripted-pipeline Groovy style):

1. **`agent any`** - let Jenkins run this pipeline on any available agent/
   executor.
2. **`tools`** - declares that a Maven install named `Maven-3.9` and a JDK
   install named `JDK-17` (configured centrally in Jenkins) should be put on
   the `PATH` for this build.
3. **Stage `Checkout`** - `checkout scm` pulls the source code configured
   for this Jenkins job (branch/repo settings come from the job or
   multibranch pipeline configuration).
4. **Stage `Build`** - runs `mvn -B clean package`, compiling the project
   and producing a packaged artifact (jar/war). The comment in the file
   shows how to wrap this in `dir('path/to/module')` to target one module
   in a multi-module repo.
5. **Stage `Test`** - runs `mvn -B test` to execute the unit test suite,
   then in its `post { always { ... } }` block calls
   `junit 'target/surefire-reports/*.xml'` so Jenkins parses the Surefire
   XML reports and shows pass/fail trends and a Test Results page on the
   build, regardless of whether the build passed or failed.
6. **Stage `Package/Archive`** - `archiveArtifacts artifacts: 'target/*.jar'`
   attaches the built jar to the Jenkins build record so it can be
   downloaded or promoted to a later deployment stage without rebuilding.
7. **`post { success / failure }`** - top-level notifications/logging based
   on the overall pipeline result.

## 4. CI/CD Tools and Platforms - Comparison

| Aspect                | GitHub Actions                          | Jenkins                                   | GitLab CI                              | CircleCI                               |
|------------------------|------------------------------------------|--------------------------------------------|------------------------------------------|------------------------------------------|
| Hosting model          | SaaS, built into GitHub                 | Self-hosted (or Jenkins-as-a-service)      | SaaS (GitLab.com) or self-hosted         | SaaS (or self-hosted runners)            |
| Config format          | YAML (`.github/workflows/*.yml`)        | Groovy DSL (`Jenkinsfile`)                 | YAML (`.gitlab-ci.yml`)                  | YAML (`.circleci/config.yml`)            |
| Setup effort           | Low - no infra to manage                | High - you manage the controller/agents    | Low (SaaS) / High (self-hosted)          | Low - no infra to manage                 |
| Extensibility          | Huge marketplace of reusable "Actions"  | Enormous plugin ecosystem, very mature     | Built-in features + templates            | Orbs (reusable config packages)          |
| Best fit               | Projects already hosted on GitHub       | Enterprises needing full control/on-prem   | Teams already using GitLab for repos     | Teams wanting a fast, managed SaaS CI    |
| Native to this repo    | Yes (this module includes `ci.yml`)     | Yes (this module includes a `Jenkinsfile`) | Not used in this repo                    | Not used in this repo                    |

All four tools solve the same core problem - trigger a pipeline on a code
event, run build/test/deploy stages, and report results - but differ in
hosting model, configuration language, and ecosystem maturity. Many
organizations use more than one (e.g. GitHub Actions for CI on pull
requests, Jenkins for complex on-prem deployment pipelines).
