# 02 - Data Structures & Algorithms

Standalone Maven module for the Cognizant Digital Nurture 5.0 "Java FSE" program,
Week 1 - Engineering Concepts. Covers core data structures (arrays, linked lists)
and fundamental searching and sorting algorithms, in plain Java with JUnit 5 tests.

## Objectives

- Implement and understand array traversal, linear search, and in-place reversal.
- Implement singly, doubly, and circular linked lists from scratch (insert, delete,
  search, traversal) without relying on `java.util.LinkedList`.
- Implement linear search and iterative binary search, and understand their time
  complexity trade-offs (O(n) vs O(log n)).
- Implement five classic sorting algorithms (bubble, insertion, heap, quick, merge)
  in place, and understand their best/average/worst-case time complexity.
- Validate every implementation with JUnit 5 unit tests covering empty, single-element,
  normal, and duplicate-value inputs.

## Folder layout

```
02-Data-Structures-Algorithms/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/dn5/week1/dsa/
    │   ├── arrays/
    │   │   └── ArrayOperations.java      - traverse(), linearSearchIndex(), reverseInPlace()
    │   ├── linkedlist/
    │   │   ├── Node.java                 - generic singly-linked node (data + next)
    │   │   ├── SinglyLinkedList.java      - insertFirst/insertLast/delete/search/traverse/size
    │   │   ├── DoublyLinkedList.java      - own prev/next node, forward & backward traversal
    │   │   └── CircularLinkedList.java    - insert/delete/traverse, terminates after one loop
    │   ├── searching/
    │   │   ├── LinearSearch.java          - O(n) sequential search
    │   │   └── BinarySearch.java          - O(log n) iterative search, requires sorted input
    │   └── sorting/
    │       ├── BubbleSort.java            - O(n) best (early-exit), O(n^2) avg/worst
    │       ├── InsertionSort.java         - O(n) best, O(n^2) avg/worst
    │       ├── HeapSort.java              - O(n log n) best/avg/worst
    │       ├── QuickSort.java             - O(n log n) best/avg, O(n^2) worst
    │       └── MergeSort.java             - O(n log n) best/avg/worst
    └── test/java/com/dn5/week1/dsa/
        ├── arrays/ArrayOperationsTest.java
        ├── linkedlist/SinglyLinkedListTest.java
        ├── linkedlist/DoublyLinkedListTest.java
        ├── linkedlist/CircularLinkedListTest.java
        ├── searching/LinearSearchTest.java
        ├── searching/BinarySearchTest.java
        └── sorting/
            ├── BubbleSortTest.java
            ├── InsertionSortTest.java
            ├── HeapSortTest.java
            ├── QuickSortTest.java
            └── MergeSortTest.java
```

All five sorting classes share a consistent contract: `static void sort(int[] arr)`
which sorts the array **in place** (ascending) and tolerates `null`, empty, and
single-element input without throwing.

## Build / run / test commands

Run from this folder (`02-Data-Structures-Algorithms/`):

```bash
mvn test
```

Package into a jar (also runs tests):

```bash
mvn package
```

Skip tests during packaging (not recommended, but useful for a quick build check):

```bash
mvn package -DskipTests
```
