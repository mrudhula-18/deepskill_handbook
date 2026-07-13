package com.dn5.week1.designpatterns.structural.adapter;

/**
 * Legacy component the codebase cannot change, exposing data only as a
 * simple XML-like string.
 */
public class XmlDataProvider {

    public String getXmlData() {
        return "<name>Bob</name><age>30</age>";
    }
}
