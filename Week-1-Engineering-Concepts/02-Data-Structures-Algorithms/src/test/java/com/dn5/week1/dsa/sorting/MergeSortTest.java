package com.dn5.week1.dsa.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MergeSortTest {

    @Test
    void sortsRandomArray() {
        int[] arr = {5, 2, 9, 1, 5, 6};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 5, 5, 6, 9}, arr);
    }

    @Test
    void emptyArrayDoesNotThrow() {
        int[] arr = {};
        assertDoesNotThrow(() -> MergeSort.sort(arr));
        assertArrayEquals(new int[]{}, arr);
    }

    @Test
    void singleElementArray() {
        int[] arr = {7};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{7}, arr);
    }

    @Test
    void alreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void reverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    void nullArrayDoesNotThrow() {
        assertDoesNotThrow(() -> MergeSort.sort(null));
    }

    @Test
    void duplicateValues() {
        int[] arr = {3, 1, 3, 2, 1};
        MergeSort.sort(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3}, arr);
    }
}
