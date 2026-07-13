package com.dn5.week2.tdd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link UserService} using Mockito to mock {@link UserRepository}.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Arrange - userService is constructed by Mockito via @InjectMocks,
        // using the mocked userRepository declared above (constructor injection).
    }

    @Test
    @DisplayName("registerUser() should save the user when the email is not already registered")
    void registerUser_withNewEmail_savesUser() {
        // Arrange
        User newUser = new User(null, "Alice", "alice@example.com");
        User savedUser = new User(1L, "Alice", "alice@example.com");
        when(userRepository.findByEmail("alice@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.registerUser(newUser);

        // Assert
        assertEquals(savedUser, result);
        verify(userRepository).save(userCaptor.capture());
        User captured = userCaptor.getValue();
        assertEquals("Alice", captured.getName());
        assertEquals("alice@example.com", captured.getEmail());
    }

    @Test
    @DisplayName("registerUser() should throw IllegalArgumentException when the email already exists")
    void registerUser_withExistingEmail_throwsException() {
        // Arrange
        User newUser = new User(null, "Bob", "bob@example.com");
        User existingUser = new User(2L, "Bob Existing", "bob@example.com");
        when(userRepository.findByEmail("bob@example.com")).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(newUser));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("getUser() should return the user when found")
    void getUser_whenFound_returnsUser() {
        // Arrange
        User existingUser = new User(1L, "Carol", "carol@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        // Act
        User result = userService.getUser(1L);

        // Assert
        assertEquals(existingUser, result);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("getUser() should throw NoSuchElementException when the user is not found")
    void getUser_whenNotFound_throwsException() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.getUser(99L));
    }
}
