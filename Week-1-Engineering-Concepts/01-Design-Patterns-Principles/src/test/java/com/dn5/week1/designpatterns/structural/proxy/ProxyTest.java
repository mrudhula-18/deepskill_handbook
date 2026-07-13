package com.dn5.week1.designpatterns.structural.proxy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProxyTest {

    @Test
    void realImageIsNotLoadedUntilFirstDisplay() {
        ProxyImage proxy = new ProxyImage("holiday.png");
        assertFalse(proxy.isRealImageLoaded());

        proxy.display();

        assertTrue(proxy.isRealImageLoaded());
    }

    @Test
    void realImageIsCreatedExactlyOnceAcrossMultipleDisplays() {
        ProxyImage proxy = new ProxyImage("holiday.png");

        proxy.display();
        proxy.display();
        proxy.display();

        assertEquals(1, proxy.getRealImageCreationCount());
    }
}
