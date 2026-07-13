package com.dn5.week2.springcore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Car depends on an {@link EngineService}. In the annotation-driven
 * configuration, Spring injects the bean qualified as "petrol".
 */
@Component
public class Car {

    private final EngineService engineService;

    @Autowired
    public Car(@Qualifier("petrol") EngineService engineService) {
        this.engineService = engineService;
    }

    public String start() {
        return engineService.start();
    }
}
