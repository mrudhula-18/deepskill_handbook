# 18 — GenAI Fundamentals & GitHub Copilot

Deep-skilling exercise on prompt engineering and using an AI coding assistant (GitHub Copilot) responsibly, illustrated with a small email-validator refactor.

## Prompt Engineering Techniques

- **Zero-shot prompting** — ask the model to perform the task directly with no examples. Fast, but leaves the model guessing at the expected rigor and output shape.
- **Few-shot prompting** — supply 2+ example input/output pairs before the real ask. Anchors the model on the exact contract (format, edge-case handling) you want.
- **Chain-of-thought prompting** — explicitly ask the model to reason step by step before producing code. Surfaces edge cases and assumptions up front, and produces a natural place to note limitations.
- **Iterative refinement** — treat the first response as a draft: follow up with "handle the null case," "add tests," or "explain this regex" rather than expecting one perfect prompt to do everything.
- **Role/context framing** — telling the assistant what kind of codebase or standard to follow (e.g., "using JUnit 5," "matching our existing style") narrows its guesses.

See [`prompt-examples.md`](./prompt-examples.md) for all three techniques worked through against the same task: "write a function that validates an email address."

## GitHub Copilot Core Features

- **Tab-complete (inline suggestions)** — Copilot proposes the next line(s) of code as you type, based on surrounding context.
- **Generate-from-comment** — writing a descriptive comment (e.g., `// validate an email address using a regex`) above an empty function prompts Copilot to generate an implementation.
- **Documentation generation** — Copilot can generate Javadoc/docstrings for an existing method by summarizing its parameters, return value, and behavior.
- **Test generation** — Copilot Chat can generate a starter test class (e.g., JUnit 5) covering the obvious valid/invalid cases for a given method — a useful starting point, not a substitute for review.
- **Refactor suggestions** — Copilot Chat can take a rough implementation and suggest a cleaner, more defensive version (see the before/after example below), though the result still needs to be checked for correctness.

## Refactor Example: Email Validator

- [`refactor-example/before.java`](./refactor-example/before.java) — the naive, buggy starting point: `isValid` just checks `email.contains("@")`, so `"@"`, `"a@"`, and `"@@@"` all pass, and a `null` input throws `NullPointerException`.
- [`refactor-example/after.java`](./refactor-example/after.java) — the Copilot-assisted refactor: null/blank checks, a `Pattern`/`Matcher`-based RFC-5322-lite regex that rejects malformed local parts and domains (including consecutive/leading/trailing dots), and a Javadoc explaining the known limitation (no IDN/quoted-local-part support).
- [`refactor-example/EmailValidatorTest.java`](./refactor-example/EmailValidatorTest.java) — JUnit 5 parameterized tests covering well-formed addresses, malformed addresses, and `null` input.

## Responsible Use

See [`ethics-checklist.md`](./ethics-checklist.md) for the checklist to run through before merging any AI-assisted code: licensing/attribution risk, never pasting secrets/PII into prompts, reviewing for security vulnerabilities, not blindly trusting generated tests, disclosing AI assistance per team policy, and watching for hallucinated APIs.

## Files in This Module

- [`prompt-examples.md`](./prompt-examples.md)
- [`refactor-example/before.java`](./refactor-example/before.java)
- [`refactor-example/after.java`](./refactor-example/after.java)
- [`refactor-example/EmailValidatorTest.java`](./refactor-example/EmailValidatorTest.java)
- [`ethics-checklist.md`](./ethics-checklist.md)
