# Prompt Engineering Examples — Email Validator

Task: "Write a function that validates an email address." The three prompts below show the same task approached with zero-shot, few-shot, and chain-of-thought prompting, with the kind of response each style tends to produce from a coding assistant like GitHub Copilot Chat.

---

## 1. Zero-Shot Prompt

**Prompt:**

> Write a Java function that validates an email address.

**Expected response:**

A single, self-contained method with no surrounding context or explanation of edge cases — typically a quick regex check and nothing more:

```java
public static boolean isValidEmail(String email) {
    return email != null && email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");
}
```

Zero-shot prompts are fast but give the model no signal about the level of rigor expected, so it tends to produce the simplest regex that "looks right" rather than one that has been checked against tricky cases (subdomains, plus-addressing, uppercase TLDs, etc.).

---

## 2. Few-Shot Prompt

**Prompt:**

> Here are examples of an email validator's behavior:
>
> Input: `"jane.doe@example.com"` -> Output: `true`
> Input: `"not-an-email"` -> Output: `false`
>
> Now write a Java function `isValidEmail(String email)` that matches this behavior for any input, including edge cases like missing `@`, missing domain, or a `null` input.

**Expected response:**

Because the examples establish a true/false contract and hint at edge cases, the assistant tends to add explicit guards before the regex check:

```java
public static boolean isValidEmail(String email) {
    if (email == null || email.isBlank()) {
        return false;
    }
    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
}
```

Few-shot examples anchor the model on the exact input/output shape wanted, which reduces ambiguity about what counts as "valid" and nudges it to handle the null/blank case shown implicitly by the second example.

---

## 3. Chain-of-Thought Prompt

**Prompt:**

> Before writing any code, think step by step about what makes an email address valid: the required parts (local part, `@`, domain, TLD), which characters are legal in each part, and common edge cases (consecutive dots, leading/trailing dots, missing TLD, internationalized domains). Then write a Java function `isValidEmail(String email)` using a regex that reflects that reasoning, and briefly note any known limitations in a comment.

**Expected response:**

The assistant typically produces a short reasoning trace followed by a more deliberate regex and an explicit limitations comment:

> Reasoning: a valid email needs a non-empty local part (letters, digits, and a limited set of symbols, no leading/trailing or consecutive dots), an `@`, and a domain with at least one dot separating labels made of letters/digits/hyphens, ending in a TLD of 2+ letters.

```java
/**
 * Validates an email address against a practical RFC-5322-lite pattern.
 * Limitation: does not support internationalized domain names (IDN/punycode)
 * or quoted local parts, which are rare in real-world user input.
 */
public static boolean isValidEmail(String email) {
    if (email == null || email.isBlank()) {
        return false;
    }
    String pattern = "^[A-Za-z0-9](?:[A-Za-z0-9._%+-]*[A-Za-z0-9])?@"
            + "[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?"
            + "(\\.[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?)*"
            + "\\.[A-Za-z]{2,}$";
    return email.matches(pattern);
}
```

Chain-of-thought prompting front-loads the reasoning about edge cases so the resulting regex and code are less likely to be a copy-pasted "good enough" pattern, and it produces a natural place (the reasoning trace) to surface known limitations instead of leaving them undocumented.
