package com.dn5.week1.dsa.searching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinarySearchTest {

    @Test
    void findsElementInSortedArray() {
        assertEquals(4, BinarySearch.search(new int[]{1, 3, 5, 7, 9, 11}, 9));
    }

    @Test
    void returnsMinusOneWhenNotFound() {
        assertEquals(-1, BinarySearch.search(new int[]{1, 3, 5, 7, 9}, 4));
    }

    @Test
    void emptyArrayReturnsMinusOne() {
        assertEquals(-1, BinarySearch.search(new int[]{}, 1));
    }

    @Test
    void singleElementFound() {
        assertEquals(0, BinarySearch.search(new int[]{5}, 5));
    }

    @Test
    void findsFirstAndLastElements() {
        int[] arr = {2, 4, 6, 8, 10};
        assertEquals(0, BinarySearch.search(arr, 2));
        assertEquals(4, BinarySearch.search(arr, 10));
    }

    @Test
    void duplicateValuesFindsAMatch() {
        int[] arr = {1, 2, 2, 2, 3};
        int index = BinarySearch.search(arr, 2);
        assertEquals(2, arr[index]);
    }

    @Test
    void nullArrayReturnsMinusOne() {
        assertEquals(-1, BinarySearch.search(null, 1));
    }
}
