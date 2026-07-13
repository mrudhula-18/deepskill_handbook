import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validates email addresses against a practical, RFC-5322-lite pattern.
 *
 * <p>This is the Copilot-assisted refactor of {@code before.java}'s naive
 * {@code contains("@")} check. It rejects {@code null}/blank input, requires
 * a well-formed local part and domain, and disallows leading/trailing or
 * consecutive dots in either segment.
 *
 * <p><b>Known limitation:</b> this pattern does not support internationalized
 * domain names (IDN/punycode) or quoted local parts (e.g. {@code "john doe"@example.com}),
 * which are uncommon in typical user-facing forms.
 */
public final class EmailValidator {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9_%+-]+(?:\\.[A-Za-z0-9_%+-]+)*@"
                    + "[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?"
                    + "(?:\\.[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?)*"
                    + "\\.[A-Za-z]{2,}$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private EmailValidator() {
        // Utility class; no instances.
    }

    /**
     * Checks whether the given string is a syntactically valid email address.
     *
     * @param email the candidate email address; may be {@code null}
     * @return {@code true} if {@code email} is non-null, non-blank, and
     *         matches the expected {@code local-part@domain.tld} shape
     */
    public static boolean isValid(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email.trim());
        return matcher.matches();
    }
}
