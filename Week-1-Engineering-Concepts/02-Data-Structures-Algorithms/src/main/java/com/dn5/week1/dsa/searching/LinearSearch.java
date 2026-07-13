package com.dn5.week1.dsa.searching;

/**
 * Sequential scan search over an unsorted (or sorted) array. Time complexity: O(n)
 * in the average and worst case, regardless of whether the array is sorted.
 */
public final class LinearSearch {

    private LinearSearch() {
    }

    /**
     * @return the index of the first element equal to {@code target}, or -1 if not found
     */
    public static int search(int[] arr, int target) {
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
}
