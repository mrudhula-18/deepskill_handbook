package com.dn5.week1.designpatterns.structural.adapter;

/** Modern interface the rest of the application expects to talk to. */
public interface JsonDataProvider {
    String getJsonData();
}
