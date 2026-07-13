package com.dn5.week1.designpatterns.structural.adapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdapterTest {

    @Test
    void adapterConvertsKnownXmlIntoExpectedJson() {
        JsonDataProvider adapter = new XmlToJsonAdapter(new XmlDataProvider());
        String json = adapter.getJsonData();
        assertEquals("{\"name\":\"Bob\",\"age\":30}", json);
    }

    @Test
    void adapterSatisfiesTheModernInterface() {
        JsonDataProvider adapter = new XmlToJsonAdapter(new XmlDataProvider());
        assertTrue(adapter instanceof JsonDataProvider);
    }
}
