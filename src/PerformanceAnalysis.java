import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import redblacktree.RedBlackTree;

/**
 * Runs the performance analysis for the report.
 * Compares RedBlackTree vs. BinarySearchTree for random and sorted data.
 * Author: Alex Matthes
 */
public class PerformanceAnalysis {

    // We'll test with 100,000 items.
    private static final int N = 100_000;

    public static void main(String[] args) {

        // --- 1. Create Test Data ---
        System.out.println("Preparing test data for N = " + N + "...");

        // Create sorted data (0, 1, 2, ... N-1)
        int[] sortedData = new int[N];

        for (int i = 0; i < N; i++) {
            sortedData[i] = i;
        }

        // Create shuffled data
        // To shuffle, we must use a List, then convert back to an array
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        int[] shuffledData = new int[N];

        for (int i = 0; i < N; i++) {
            shuffledData[i] = list.get(i);
        }

        System.out.println("Data ready. Running experiments...\n");

        // --- 2. Run Experiments ---

        // Scenario 1: RedBlackTree (Random Data)
        RedBlackTree rbtRandom = new RedBlackTree();

        long startTime = System.nanoTime();

        for (int item : shuffledData) {
            rbtRandom.insert(item);
        }

        long endTime = System.nanoTime();

        long rbtRandomTime = (endTime - startTime) / 1_000_000; // ms

        System.out.println("RedBlackTree (Random Data): " + rbtRandomTime + " ms");

        // Scenario 2: BinarySearchTree (Random Data)
        BinarySearchTree bstRandom = new BinarySearchTree();

        startTime = System.nanoTime();

        for (int item : shuffledData) {
            bstRandom.insert(item);
        }

        endTime = System.nanoTime();

        long bstRandomTime = (endTime - startTime) / 1_000_000; // ms

        System.out.println("BinarySearchTree (Random):  " + bstRandomTime + " ms");

        System.out.println("---");

        // Scenario 3: RedBlackTree (Sorted Data)
        RedBlackTree rbtSorted = new RedBlackTree();

        startTime = System.nanoTime();

        for (int item : sortedData) {
            rbtSorted.insert(item);
        }

        endTime = System.nanoTime();

        long rbtSortedTime = (endTime - startTime) / 1_000_000; // ms

        System.out.println("RedBlackTree (Sorted Data): " + rbtSortedTime + " ms");

        // Scenario 4: BinarySearchTree (Sorted Data)
        BinarySearchTree bstSorted = new BinarySearchTree();

        startTime = System.nanoTime();

        for (int item : sortedData) {
            bstSorted.insert(item);
        }
        endTime = System.nanoTime();

        long bstSortedTime = (endTime - startTime) / 1_000_000; // ms

        System.out.println("BinarySearchTree (Sorted):  " + bstSortedTime + " ms");
    }
}
