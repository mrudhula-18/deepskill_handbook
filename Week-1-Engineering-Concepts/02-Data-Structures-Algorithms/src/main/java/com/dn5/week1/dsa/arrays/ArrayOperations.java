package com.dn5.week1.dsa.arrays;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Basic array operations: traversal (formatted as a string for testability),
 * linear search by index, and in-place reversal.
 */
public final class ArrayOperations {

    private ArrayOperations() {
    }

    /**
     * Traverses the array and returns a formatted, comma-joined string,
     * e.g. {@code [1, 2, 3]}. Returns {@code "[]"} for a null or empty array.
     */
    public static String traverse(int[] arr) {
        if (arr == null || arr.length == 0) {
            return "[]";
        }
        return "[" + IntStream.of(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", ")) + "]";
    }

    /**
     * Linear search: returns the index of the first element equal to {@code target},
     * or -1 if not found (or if the array is null). O(n) time complexity.
     */
    public static int linearSearchIndex(int[] arr, int target) {
        if (arr == null) {
            return -1;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Reverses the given array in place. No-op for null, empty, or single-element arrays.
     */
    public static void reverseInPlace(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int left = 0;
        int right = arr.length - 1;
        while (left < right) {
            int tmp = arr[left];
            arr[left] = arr[right];
            arr[right] = tmp;
            left++;
            right--;
        }
    }
}
