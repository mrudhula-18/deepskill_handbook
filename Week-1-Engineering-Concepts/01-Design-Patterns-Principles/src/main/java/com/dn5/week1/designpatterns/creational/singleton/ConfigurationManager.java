package com.dn5.week1.designpatterns.creational.singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Thread-safe Singleton implemented with double-checked locking and a
 * volatile instance reference. Holds configuration key/value state that
 * persists across every call to {@link #getInstance()}.
 */
public class ConfigurationManager {

    private static volatile ConfigurationManager instance;

    private final Map<String, String> settings = new HashMap<>();

    private ConfigurationManager() {
        // private constructor prevents external instantiation
    }

    public static ConfigurationManager getInstance() {
        ConfigurationManager result = instance;
        if (result == null) {
            synchronized (ConfigurationManager.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ConfigurationManager();
                }
            }
        }
        return result;
    }

    public void setProperty(String key, String value) {
        settings.put(key, value);
    }

    public String getProperty(String key) {
        return settings.get(key);
    }
}
