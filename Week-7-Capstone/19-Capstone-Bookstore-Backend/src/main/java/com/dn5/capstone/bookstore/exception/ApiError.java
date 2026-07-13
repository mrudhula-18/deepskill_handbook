package com.dn5.capstone.bookstore.exception;

import java.time.LocalDateTime;

/**
 * Consistent error response shape returned by the {@link GlobalExceptionHandler}.
 */
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(LocalDateTime.now(), status, error, message, path);
    }
}
