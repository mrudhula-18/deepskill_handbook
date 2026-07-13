package com.dn5.week2.springcore;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Loads the annotation-driven Spring context and verifies the {@link Car}
 * bean is present and wired with the petrol engine.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class SpringContextTest {

    @Autowired
    private Car car;

    @Test
    void carBean_isPresentInContext() {
        assertNotNull(car);
    }

    @Test
    void carBean_startsWithPetrolEngine() {
        assertEquals("Petrol engine started", car.start());
    }
}
