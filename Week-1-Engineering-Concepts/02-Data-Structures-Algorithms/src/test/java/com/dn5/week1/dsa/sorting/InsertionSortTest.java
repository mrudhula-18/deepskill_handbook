package com.dn5.week1.dsa.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class InsertionSortTest {

    @Test
    void sortsRandomArray() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        InsertionSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, arr);
    }

    @Test
    void emptyArrayDoesNotThrow() {
        int[] arr = {};
        assertDoesNotThrow(() -> InsertionSort.sort(arr));
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void singleElementArray() {
        int[] arr = {7};
        InsertionSort.sort(arr);
        assertArrayEquals(new int[]{7}, arr);
    }

    @Test
    void alreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        InsertionSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void reverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        InsertionSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void nullArrayDoesNotThrow() {
        assertDoesNotThrow(() -> InsertionSort.sort(null));
    }

    @Test
    void duplicateValues() {
        int[] arr = {3, 1, 3, 2, 1};
        InsertionSort.sort(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3}, arr);
    }
}
