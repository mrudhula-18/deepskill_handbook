package com.dn5.week1.designpatterns.structural.proxy;

/** The expensive real subject: "loads" from disk as soon as it is constructed. */
public class RealImage implements Image {

    private final String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("Loading " + filename + " from disk (expensive operation)...");
    }

    @Override
    public void display() {
        System.out.println("Displaying " + filename);
    }
}
