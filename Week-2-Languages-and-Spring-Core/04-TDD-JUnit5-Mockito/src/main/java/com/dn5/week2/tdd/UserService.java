package com.dn5.week2.tdd;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Business logic around {@link User} registration and lookup.
 * Depends on {@link UserRepository} via constructor injection so that it can
 * be unit-tested with a mock repository.
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException(
                    "A user with email " + user.getEmail() + " already exists");
        }
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + id));
    }
}
