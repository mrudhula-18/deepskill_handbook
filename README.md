# DN 5.0 Deep Skilling — Java FSE (React) — Weekwise Assignments

Full working solutions for the Cognizant Digital Nurture 5.0 Java FSE Deep Skilling program,
organized week-by-week to match the handbook's 7-week schedule. Every module folder contains
its own `README.md` with build/run instructions plus complete, runnable source code (not just
problem statements).

## Prerequisites

| Tool | Version | Used for |
|---|---|---|
| JDK | 17+ | All Java/Spring modules |
| Maven | 3.8+ | Building Java modules |
| Node.js | 18+ | React modules |
| Docker & Docker Compose | latest | Containerization modules |
| Oracle DB (or compatible) | any | PL/SQL module |
| Git | 2.x | Version control module |

## Index

### Week 1 — Engineering Concepts
| Module | Topic |
|---|---|
| [01-Design-Patterns-Principles](Week-1-Engineering-Concepts/01-Design-Patterns-Principles) | SOLID + GoF design patterns |
| [02-Data-Structures-Algorithms](Week-1-Engineering-Concepts/02-Data-Structures-Algorithms) | Arrays, linked lists, searching, sorting |
| [03-PLSQL-Programming](Week-1-Engineering-Concepts/03-PLSQL-Programming) | PL/SQL blocks, cursors, procedures, packages, triggers |

### Week 2 — Languages & Spring Core
| Module | Topic |
|---|---|
| [04-TDD-JUnit5-Mockito](Week-2-Languages-and-Spring-Core/04-TDD-JUnit5-Mockito) | Test-driven development, JUnit5, Mockito |
| [05-SLF4J-Logging-Lombok](Week-2-Languages-and-Spring-Core/05-SLF4J-Logging-Lombok) | SLF4J/Logback logging, Lombok |
| [06-Spring-Core-Maven](Week-2-Languages-and-Spring-Core/06-Spring-Core-Maven) | IoC, DI, AOP, Maven |

### Week 3 — Data & REST
| Module | Topic |
|---|---|
| [07-Spring-Data-JPA-Hibernate](Week-3-Data-and-REST/07-Spring-Data-JPA-Hibernate) | Spring Data JPA, Hibernate, pagination, auditing |
| [08-SonarQube-Code-Quality](Week-3-Data-and-REST/08-SonarQube-Code-Quality) | Static analysis, code smells, quality gates |
| [09-Spring-REST-SpringBoot3](Week-3-Data-and-REST/09-Spring-REST-SpringBoot3) | REST controllers, DTOs, validation, HATEOAS, security |

### Week 4 — Microservices & Frontend
| Module | Topic |
|---|---|
| [10-Microservices-SpringBoot-SpringCloud](Week-4-Microservices-and-Frontend/10-Microservices-SpringBoot-SpringCloud) | Eureka, Gateway, Feign, Circuit Breaker |
| [11-React-SPA](Week-4-Microservices-and-Frontend/11-React-SPA) | React components, hooks, forms, API calls |
| [12-Application-Debugging](Week-4-Microservices-and-Frontend/12-Application-Debugging) | Chrome DevTools & VS Code debugging |

### Week 5 — Platform Tooling
| Module | Topic |
|---|---|
| [13-Git-Version-Control](Week-5-Platform-Tooling/13-Git-Version-Control) | Git workflow scripts & cheat sheet |
| [14-DevOps-CICD](Week-5-Platform-Tooling/14-DevOps-CICD) | GitHub Actions, Jenkins |
| [15-Docker-Containerization](Week-5-Platform-Tooling/15-Docker-Containerization) | Dockerfiles, Docker Compose |

### Week 6 — Process, Cloud & AI
| Module | Topic |
|---|---|
| [16-Agile-Methodology](Week-6-Process-Cloud-and-AI/16-Agile-Methodology) | Backlogs, user stories, burndown |
| [17-Cloud-Fundamentals-AWS](Week-6-Process-Cloud-and-AI/17-Cloud-Fundamentals-AWS) | Lambda, SAM, CloudFormation snippets |
| [18-GenAI-Fundamentals-Copilot](Week-6-Process-Cloud-and-AI/18-GenAI-Fundamentals-Copilot) | Prompt engineering, Copilot practices |

### Week 7 — Capstone
| Module | Topic |
|---|---|
| [19-Capstone-Bookstore-Backend](Week-7-Capstone/19-Capstone-Bookstore-Backend) | Full Spring Boot 3 REST + JPA backend |
| [20-Capstone-Bookstore-Frontend-React](Week-7-Capstone/20-Capstone-Bookstore-Frontend-React) | React frontend consuming the backend |
| [21-Capstone-Deployment-CICD](Week-7-Capstone/21-Capstone-Deployment-CICD) | Docker Compose + CI pipeline for the full stack |

## Pushing to GitHub

```bash
cd DN5.0-DeepSkilling-Assignments
git init
git add .
git commit -m "DN 5.0 Java FSE deep skilling - weekwise assignments"
git branch -M main
git remote add origin <your-empty-github-repo-url>
git push -u origin main
```

## Notes

- Each module is independently buildable — there is no parent multi-module `pom.xml` at the repo
  root, so you can open/import each module folder on its own in your IDE.
- The Week 7 capstone reuses the same patterns taught in Weeks 1-6 in a single cohesive
  "Bookstore" application (backend + frontend + Docker + CI) as end-to-end integration practice
  ahead of the Final KBA.
