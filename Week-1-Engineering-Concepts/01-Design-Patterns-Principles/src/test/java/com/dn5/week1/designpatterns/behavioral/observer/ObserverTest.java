package com.dn5.week1.designpatterns.behavioral.observer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObserverTest {

    @Test
    void registeredObserverReceivesPriceUpdates() {
        StockTicker ticker = new StockTicker();
        Investor investor = new Investor("Carol");
        ticker.registerObserver(investor);

        ticker.setPrice(101.5);

        assertEquals(101.5, investor.getLastKnownPrice(), 0.0001);
    }

    @Test
    void removedObserverDoesNotReceiveFurtherUpdates() {
        StockTicker ticker = new StockTicker();
        Investor investor = new Investor("Dave");
        ticker.registerObserver(investor);
        ticker.setPrice(50.0);
        assertEquals(50.0, investor.getLastKnownPrice(), 0.0001);

        ticker.removeObserver(investor);
        ticker.setPrice(999.0);

        assertEquals(50.0, investor.getLastKnownPrice(), 0.0001);
    }

    @Test
    void multipleObserversAllReceiveUpdates() {
        StockTicker ticker = new StockTicker();
        Investor a = new Investor("A");
        Investor b = new Investor("B");
        ticker.registerObserver(a);
        ticker.registerObserver(b);

        ticker.setPrice(75.0);

        assertEquals(75.0, a.getLastKnownPrice(), 0.0001);
        assertEquals(75.0, b.getLastKnownPrice(), 0.0001);
    }
}
