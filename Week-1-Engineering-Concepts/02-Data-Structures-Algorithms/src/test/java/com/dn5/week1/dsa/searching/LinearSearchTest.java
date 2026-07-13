package com.dn5.week1.dsa.searching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinearSearchTest {

    @Test
    void findsElement() {
        assertEquals(3, LinearSearch.search(new int[]{4, 2, 9, 1, 7}, 1));
    }

    @Test
    void returnsMinusOneWhenNotFound() {
        assertEquals(-1, LinearSearch.search(new int[]{4, 2, 9}, 100));
    }

    @Test
    void emptyArrayReturnsMinusOne() {
        assertEquals(-1, LinearSearch.search(new int[]{}, 1));
    }

    @Test
    void singleElementFound() {
        assertEquals(0, LinearSearch.search(new int[]{5}, 5));
    }

    @Test
    void duplicateValuesReturnsFirstIndex() {
        assertEquals(1, LinearSearch.search(new int[]{9, 3, 3, 3}, 3));
    }

    @Test
    void nullArrayReturnsMinusOne() {
        assertEquals(-1, LinearSearch.search(null, 1));
    }
}
