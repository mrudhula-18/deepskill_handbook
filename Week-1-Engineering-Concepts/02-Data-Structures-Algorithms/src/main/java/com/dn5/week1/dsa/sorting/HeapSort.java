package com.dn5.week1.dsa.sorting;

/**
 * Heap sort, sorted in place ascending using a max-heap. Best, average, and
 * worst case are all O(n log n).
 */
public final class HeapSort {

    private HeapSort() {
    }

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int end = n - 1; end > 0; end--) {
            int tmp = arr[0];
            arr[0] = arr[end];
            arr[end] = tmp;
            heapify(arr, end, 0);
        }
    }

    private static void heapify(int[] arr, int size, int root) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;
        if (left < size && arr[left] > arr[largest]) {
            largest = left;
        }
        if (right < size && arr[right] > arr[largest]) {
            largest = right;
        }
        if (largest != root) {
            int tmp = arr[root];
            arr[root] = arr[largest];
            arr[largest] = tmp;
            heapify(arr, size, largest);
        }
    }
}
