# Responsible Use of AI Coding Assistants — Checklist

Run through this checklist before merging any code produced or substantially assisted by an AI coding tool (GitHub Copilot, Claude, ChatGPT, etc.).

- [ ] **Licensing and attribution risk verified.** The suggestion isn't a verbatim or near-verbatim reproduction of a snippet with an incompatible license (e.g., GPL code pasted into a proprietary codebase). Long, distinctive, or unusually specific completions get extra scrutiny.
- [ ] **No secrets or PII pasted into prompts.** API keys, passwords, connection strings, customer records, and other sensitive data never go into a prompt, chat window, or comment sent to a third-party model.
- [ ] **Generated code reviewed for security vulnerabilities.** Injection risks (SQL/command/XSS), missing input validation, insecure defaults (e.g., disabled TLS verification, weak crypto), and hardcoded credentials are checked before merging — the same as any human-written PR.
- [ ] **Generated tests are not blindly trusted.** Tests are read, not just run: check they assert the right thing, cover real edge cases, and aren't just asserting the implementation's current (possibly buggy) behavior back at itself.
- [ ] **AI-assisted code is disclosed per team policy.** If the team requires a PR label, commit tag, or comment noting AI assistance, it's added consistently.
- [ ] **Watch for hallucinated APIs.** Method/class/package names that "look right" are verified against real documentation or by compiling — assistants can confidently invent APIs, config flags, or library functions that don't exist.
- [ ] **Understand the code before merging it.** If you can't explain what a generated block does and why, don't ship it — you own it once it's merged, regardless of who (or what) wrote it.
- [ ] **Check for silently narrowed scope.** Generated code sometimes handles only the happy path implied by the prompt; confirm error handling, null cases, and boundary conditions are covered.
