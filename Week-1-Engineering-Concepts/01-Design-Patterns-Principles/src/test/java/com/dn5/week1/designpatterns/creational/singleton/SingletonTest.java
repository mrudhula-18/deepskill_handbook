package com.dn5.week1.designpatterns.creational.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SingletonTest {

    @Test
    void getInstanceAlwaysReturnsTheSameInstance() {
        ConfigurationManager first = ConfigurationManager.getInstance();
        ConfigurationManager second = ConfigurationManager.getInstance();
        assertSame(first, second);
    }

    @Test
    void stateSetThroughOneReferencePersistsAcrossAllReferences() {
        ConfigurationManager.getInstance().setProperty("key1", "value1");
        ConfigurationManager other = ConfigurationManager.getInstance();
        assertEquals("value1", other.getProperty("key1"));
    }
}
