package com.dn5.week2.springcore;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Diesel engine implementation of {@link EngineService}.
 */
@Component
@Qualifier("diesel")
public class DieselEngine implements EngineService {

    @Override
    public String start() {
        return "Diesel engine started";
    }
}
