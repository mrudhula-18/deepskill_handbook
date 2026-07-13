import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the refactored {@link EmailValidator} (after.java).
 */
class EmailValidatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "jane.doe@example.com",
            "john_smith99@sub.example.co.uk",
            "a@b.io",
            "first.last+tag@example.org"
    })
    void acceptsWellFormedAddresses(String email) {
        assertTrue(EmailValidator.isValid(email), () -> email + " should be valid");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "   ",
            "@example.com",
            "jane.doe@",
            "jane..doe@example.com",
            ".jane@example.com",
            "jane@example",
            "jane@.com",
            "@@@",
            "not-an-email"
    })
    void rejectsMalformedAddresses(String email) {
        assertFalse(EmailValidator.isValid(email), () -> email + " should be invalid");
    }

    @Test
    void rejectsNullInput() {
        assertFalse(EmailValidator.isValid(null));
    }
}
