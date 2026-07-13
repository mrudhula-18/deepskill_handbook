/**
 * Naive, hand-written email validator. Kept here to show the "before" state
 * that an AI coding assistant (e.g., GitHub Copilot) helps refactor in
 * after.java. This version is buggy: it accepts almost anything containing
 * an "@" character.
 */
public class EmailValidator {

    // BUG: this "validation" only checks for the presence of "@" anywhere in
    // the string. It happily accepts "@", "a@", "@@@", "hello@" as valid,
    // and it throws a NullPointerException if the input is null.
    public static boolean isValid(String email) {
        return email.contains("@");
    }
}
