package com.dn5.week1.dsa.sorting;

/**
 * Insertion sort, sorted in place ascending. Best case O(n) for nearly-sorted
 * input; average and worst case O(n^2).
 */
public final class InsertionSort {

    private InsertionSort() {
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }
}
