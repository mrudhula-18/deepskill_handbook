package com.dn5.week1.dsa.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class QuickSortTest {

    @Test
    void sortsRandomArray() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        QuickSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, arr);
    }

    @Test
    void emptyArrayDoesNotThrow() {
        int[] arr = {};
        assertDoesNotThrow(() -> QuickSort.sort(arr));
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void singleElementArray() {
        int[] arr = {7};
        QuickSort.sort(arr);
        assertArrayEquals(new int[]{7}, arr);
    }

    @Test
    void alreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        QuickSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void reverseSortedArrayWorstCasePivot() {
        int[] arr = {5, 4, 3, 2, 1};
        QuickSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void nullArrayDoesNotThrow() {
        assertDoesNotThrow(() -> QuickSort.sort(null));
    }

    @Test
    void duplicateValues() {
        int[] arr = {3, 1, 3, 2, 1};
        QuickSort.sort(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3}, arr);
    }
}
