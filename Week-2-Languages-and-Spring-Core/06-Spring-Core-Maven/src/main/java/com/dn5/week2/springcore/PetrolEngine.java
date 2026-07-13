package com.dn5.week2.springcore;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Petrol engine implementation of {@link EngineService}.
 */
@Component
@Qualifier("petrol")
public class PetrolEngine implements EngineService {

    @Override
    public String start() {
        return "Petrol engine started";
    }
}
