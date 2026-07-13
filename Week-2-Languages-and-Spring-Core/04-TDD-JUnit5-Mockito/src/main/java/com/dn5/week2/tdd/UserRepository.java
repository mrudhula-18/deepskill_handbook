package com.dn5.week2.tdd;

import java.util.Optional;

/**
 * Persistence abstraction for {@link User} entities.
 */
public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    void deleteById(Long id);
}
