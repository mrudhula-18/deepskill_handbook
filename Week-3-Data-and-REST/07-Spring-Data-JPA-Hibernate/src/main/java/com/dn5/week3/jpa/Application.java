package com.dn5.week3.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Entry point for the Spring Data JPA / Hibernate demo module.
 *
 * {@code @EnableJpaAuditing} activates the {@code @CreatedDate} /
 * {@code @LastModifiedDate} auditing callbacks declared on {@link Auditable}.
 */
@SpringBootApplication
@EnableJpaAuditing
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
