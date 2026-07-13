package com.dn5.week1.designpatterns.structural.proxy;

/**
 * Proxy: defers creating the expensive {@link RealImage} until {@link
 * #display()} is first called, then caches and reuses it.
 */
public class ProxyImage implements Image {

    private final String filename;
    private RealImage realImage;
    private int realImageCreationCount = 0;

    public ProxyImage(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if (realImage == null) {
            realImage = new RealImage(filename);
            realImageCreationCount++;
        }
        realImage.display();
    }

    /** Exposed only so tests can verify lazy-loading behavior. */
    public boolean isRealImageLoaded() {
        return realImage != null;
    }

    /** Exposed only so tests can verify the real image is created exactly once. */
    public int getRealImageCreationCount() {
        return realImageCreationCount;
    }
}
