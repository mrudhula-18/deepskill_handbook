package com.dn5.week1.dsa.arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayOperationsTest {

    @Test
    void traverseFormatsElements() {
        assertEquals("[1, 2, 3]", ArrayOperations.traverse(new int[]{1, 2, 3}));
    }

    @Test
    void traverseEmptyArray() {
        assertEquals("[]", ArrayOperations.traverse(new int[]{}));
    }

    @Test
    void traverseSingleElement() {
        assertEquals("[9]", ArrayOperations.traverse(new int[]{9}));
    }

    @Test
    void linearSearchIndexFound() {
        assertEquals(2, ArrayOperations.linearSearchIndex(new int[]{5, 6, 7, 8}, 7));
    }

    @Test
    void linearSearchIndexNotFound() {
        assertEquals(-1, ArrayOperations.linearSearchIndex(new int[]{5, 6, 7, 8}, 100));
    }

    @Test
    void linearSearchIndexFirstMatchWithDuplicates() {
        assertEquals(1, ArrayOperations.linearSearchIndex(new int[]{1, 2, 2, 3}, 2));
    }

    @Test
    void reverseInPlaceNormal() {
        int[] arr = {1, 2, 3, 4, 5};
        ArrayOperations.reverseInPlace(arr);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, arr);
    }

    @Test
    void reverseInPlaceSingleElement() {
        int[] arr = {42};
        ArrayOperations.reverseInPlace(arr);
        assertArrayEquals(new int[]{42}, arr);
    }

    @Test
    void reverseInPlaceEmptyArray() {
        int[] arr = {};
        ArrayOperations.reverseInPlace(arr);
        assertArrayEquals(new int[]{}, arr);
    }
}
