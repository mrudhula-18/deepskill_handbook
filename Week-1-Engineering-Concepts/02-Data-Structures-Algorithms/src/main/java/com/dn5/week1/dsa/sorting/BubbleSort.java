package com.dn5.week1.dsa.sorting;

/**
 * Bubble sort, sorted in place ascending. Best case O(n) when the array is
 * already sorted (thanks to the early-exit optimization); average and worst
 * case O(n^2).
 */
public final class BubbleSort {

    private BubbleSort() {
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }
}
