package com.dn5.week1.dsa.searching;

/**
 * Iterative binary search. The input array MUST already be sorted in ascending
 * order; behaviour is undefined otherwise. Time complexity: O(log n), versus
 * O(n) for {@link LinearSearch}.
 */
public final class BinarySearch {

    private BinarySearch() {
    }

    /**
     * @param sortedArr array sorted in ascending order (precondition, not checked)
     * @return the index of an element equal to {@code target}, or -1 if not found
     */
    public static int search(int[] sortedArr, int target) {
        if (sortedArr == null) {
            return -1;
        }
        int low = 0;
        int high = sortedArr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sortedArr[mid] == target) {
                return mid;
            } else if (sortedArr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}
