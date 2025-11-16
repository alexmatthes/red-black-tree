import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import redblacktree.RedBlackTree;

/**
 * Comprehensive test suite for Red-Black Tree implementation.
 * Tests all core operations and edge cases.
 * Author: ALex Matthes
 */
class RedBlackTreeTest {

  // ==============================================
  // SEARCH TESTS
  // ==============================================

  @Nested
  @DisplayName("Search Operations")
  class SearchTests {

    @Test
    @DisplayName("Search in empty tree returns false")
    void testSearchOnEmptyTree() {
      RedBlackTree tree = new RedBlackTree();
      assertFalse(tree.search(10));
    }

    @Test
    @DisplayName("Search finds single inserted element")
    void testSearchSingleElement() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      assertTrue(tree.search(10));
    }

    @Test
    @DisplayName("Search returns false for non-existent element")
    void testSearchNonExistentElement() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      assertFalse(tree.search(99));
    }

    @Test
    @DisplayName("Search finds element in multi-node tree")
    void testSearchInMultiNodeTree() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(2);
      tree.insert(7);
      tree.insert(20);

      assertTrue(tree.search(7));
      assertTrue(tree.search(2));
      assertTrue(tree.search(20));
      assertFalse(tree.search(100));
    }

    @Test
    @DisplayName("Search finds all elements after multiple insertions")
    void testSearchAllInsertedElements() {
      RedBlackTree tree = new RedBlackTree();
      int[] values = {50, 25, 75, 10, 30, 60, 80};

      for (int val : values) {
        tree.insert(val);
      }

      for (int val : values) {
        assertTrue(tree.search(val), "Should find value: " + val);
      }
    }
  }

  // ==============================================
  // INSERT TESTS
  // ==============================================

  @Nested
  @DisplayName("Insert Operations")
  class InsertTests {

    @Test
    @DisplayName("Insert single element makes it root")
    void testInsertSingleElement() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      assertEquals(10, tree.getRootData());
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Insert maintains BST property")
    void testInsertMaintainsBinarySearchTreeProperty() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);

      assertTrue(tree.search(10));
      assertTrue(tree.search(5));
      assertTrue(tree.search(15));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Insert causes black uncle line case (right)")
    void testInsertBlackUncleLineRight() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(15);
      tree.insert(20);

      assertEquals(15, tree.getRootData());
      assertTrue(tree.search(10));
      assertTrue(tree.search(15));
      assertTrue(tree.search(20));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Insert causes black uncle line case (left)")
    void testInsertBlackUncleLineLeft() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(30);
      tree.insert(20);
      tree.insert(10);

      assertEquals(20, tree.getRootData());
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Insert causes black uncle triangle case (right)")
    void testInsertBlackUncleTriangleRight() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(15);
      tree.insert(12);

      assertEquals(12, tree.getRootData());
      assertTrue(tree.search(10));
      assertTrue(tree.search(15));
      assertTrue(tree.search(12));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Insert causes black uncle triangle case (left)")
    void testInsertBlackUncleTriangleLeft() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(30);
      tree.insert(10);
      tree.insert(20);

      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Insert with red uncle causes recoloring")
    void testInsertRedUncleRecoloring() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(50);
      tree.insert(25);
      tree.insert(75);
      tree.insert(10); // Should cause recoloring

      assertTrue(tree.isRedBlackTree());
      assertTrue(tree.search(10));
    }

    @Test
    @DisplayName("Insert maintains RB properties with sequential values")
    void testInsertSequentialValues() {
      RedBlackTree tree = new RedBlackTree();

      for (int i = 1; i <= 10; i++) {
        tree.insert(i);
        assertTrue(tree.isRedBlackTree(),
                "Tree should maintain RB properties after inserting " + i);
      }
    }

    @Test
    @DisplayName("Insert maintains RB properties with reverse sequential values")
    void testInsertReverseSequentialValues() {
      RedBlackTree tree = new RedBlackTree();

      for (int i = 10; i >= 1; i--) {
        tree.insert(i);
        assertTrue(tree.isRedBlackTree(),
                "Tree should maintain RB properties after inserting " + i);
      }
    }

    @Test
    @DisplayName("Insert maintains RB properties with random-like values")
    void testInsertRandomLikeValues() {
      RedBlackTree tree = new RedBlackTree();
      int[] values = {50, 25, 75, 10, 30, 60, 80, 5, 15, 27, 55, 70, 85};

      for (int val : values) {
        tree.insert(val);
        assertTrue(tree.isRedBlackTree(),
                "Tree should maintain RB properties after inserting " + val);
      }
    }
  }

  // ==============================================
  // DELETE TESTS
  // ==============================================

  @Nested
  @DisplayName("Delete Operations")
  class DeleteTests {

    @Test
    @DisplayName("Delete from empty tree throws exception")
    void testDeleteFromEmptyTree() {
      RedBlackTree tree = new RedBlackTree();
      assertThrows(NoSuchElementException.class, () -> tree.delete(10));
    }

    @Test
    @DisplayName("Delete non-existent element throws exception")
    void testDeleteNonExistentElement() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      assertThrows(NoSuchElementException.class, () -> tree.delete(99));
    }

    @Test
    @DisplayName("Delete single element (root)")
    void testDeleteRoot() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.delete(10);

      assertFalse(tree.search(10));
      assertThrows(IllegalStateException.class, tree::getRootData);
    }

    @Test
    @DisplayName("Delete leaf node")
    void testDeleteLeafNode() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);

      tree.delete(5);

      assertFalse(tree.search(5));
      assertTrue(tree.search(10));
      assertTrue(tree.search(15));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Delete node with one child (left)")
    void testDeleteNodeWithLeftChild() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(3);

      tree.delete(5);

      assertFalse(tree.search(5));
      assertTrue(tree.search(3));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Delete node with one child (right)")
    void testDeleteNodeWithRightChild() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(20);

      tree.delete(15);

      assertFalse(tree.search(15));
      assertTrue(tree.search(20));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Delete node with two children")
    void testDeleteNodeWithTwoChildren() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(12);
      tree.insert(20);

      tree.delete(15);

      assertFalse(tree.search(15));
      assertTrue(tree.search(12));
      assertTrue(tree.search(20));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Delete root with two children")
    void testDeleteRootWithTwoChildren() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);
      tree.insert(12);

      tree.delete(10);

      assertEquals(12, tree.getRootData());
      assertFalse(tree.search(10));
      assertTrue(tree.search(5));
      assertTrue(tree.search(15));
      assertTrue(tree.search(12));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Delete maintains RB properties after multiple deletions")
    void testDeleteMultipleNodes() {
      RedBlackTree tree = new RedBlackTree();
      int[] values = {50, 25, 75, 10, 30, 60, 80};

      for (int val : values) {
        tree.insert(val);
      }

      tree.delete(10);
      assertTrue(tree.isRedBlackTree());

      tree.delete(30);
      assertTrue(tree.isRedBlackTree());

      tree.delete(25);
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Delete and re-insert same values")
    void testDeleteAndReinsert() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      tree.insert(5);
      tree.insert(15);

      tree.delete(5);
      assertFalse(tree.search(5));

      tree.insert(5);
      assertTrue(tree.search(5));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Delete all nodes sequentially")
    void testDeleteAllNodes() {
      RedBlackTree tree = new RedBlackTree();
      int[] values = {10, 5, 15, 3, 7, 12, 20};

      for (int val : values) {
        tree.insert(val);
      }

      for (int val : values) {
        tree.delete(val);
        assertFalse(tree.search(val));
        assertTrue(tree.isRedBlackTree());
      }
    }
  }

  // ==============================================
  // PROPERTY VALIDATION TESTS
  // ==============================================

  @Nested
  @DisplayName("Red-Black Tree Property Validation")
  class PropertyValidationTests {

    @Test
    @DisplayName("Empty tree is valid RB tree")
    void testEmptyTreeIsValid() {
      RedBlackTree tree = new RedBlackTree();
      // Empty tree should throw exception for getRootData
      assertThrows(IllegalStateException.class, tree::getRootData);
    }

    @Test
    @DisplayName("Root is always black after operations")
    void testRootIsBlack() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(10);
      assertTrue(tree.isRedBlackTree());

      tree.insert(5);
      tree.insert(15);
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Properties maintained after complex operations")
    void testComplexOperations() {
      RedBlackTree tree = new RedBlackTree();

      // Insert phase
      for (int i = 1; i <= 20; i++) {
        tree.insert(i);
      }
      assertTrue(tree.isRedBlackTree());

      // Delete phase
      for (int i = 1; i <= 10; i++) {
        tree.delete(i);
        assertTrue(tree.isRedBlackTree());
      }

      // Mixed phase
      tree.insert(25);
      assertTrue(tree.isRedBlackTree());
      tree.delete(15);
      assertTrue(tree.isRedBlackTree());
    }
  }

  // ==============================================
  // EDGE CASE TESTS
  // ==============================================

  @Nested
  @DisplayName("Edge Cases")
  class EdgeCaseTests {

    @Test
    @DisplayName("Handle negative values")
    void testNegativeValues() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(-10);
      tree.insert(-20);
      tree.insert(-5);

      assertTrue(tree.search(-10));
      assertTrue(tree.search(-20));
      assertTrue(tree.search(-5));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Handle zero value")
    void testZeroValue() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(0);
      tree.insert(-5);
      tree.insert(5);

      assertTrue(tree.search(0));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Handle large positive and negative values")
    void testExtremeValues() {
      RedBlackTree tree = new RedBlackTree();
      tree.insert(1000000);
      tree.insert(-1000000);
      tree.insert(0);

      assertTrue(tree.search(1000000));
      assertTrue(tree.search(-1000000));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Large tree maintains properties")
    void testLargeTree() {
      RedBlackTree tree = new RedBlackTree();

      // Insert 100 elements
      for (int i = 0; i < 100; i++) {
        tree.insert(i);
      }

      assertTrue(tree.isRedBlackTree());

      // Verify all elements exist
      for (int i = 0; i < 100; i++) {
        assertTrue(tree.search(i));
      }
    }

    @Test
    @DisplayName("Stress test with many operations")
    void testStressTest() {
      RedBlackTree tree = new RedBlackTree();

      // Insert 50 elements (0, 2, 4, 6, ... 98)
      for (int i = 0; i < 50; i++) {
        tree.insert(i * 2);
      }

      // Delete every other element (0, 8, 16, 24, ...)
      for (int i = 0; i < 25; i++) {
        tree.delete(i * 4);
      }

      // Verify: elements at positions i*2 where i*2 is NOT divisible by 4
      // Should have: 2, 6, 10, 14, 18, 22, 26, 30, ...
      for (int i = 0; i < 50; i++) {
        int value = i * 2;
        boolean shouldExist = (value % 4 != 0);
        assertEquals(shouldExist, tree.search(value),
                "Value " + value + " should " + (shouldExist ? "exist" : "not exist"));
      }

      assertTrue(tree.isRedBlackTree());
    }
  }

  // ==============================================
  // SPECIAL TEST SCENARIOS
  // ==============================================

  @Nested
  @DisplayName("Special Scenarios")
  class SpecialScenarios {

    @Test
    @DisplayName("Insert and delete in alternating pattern")
    void testAlternatingInsertDelete() {
      RedBlackTree tree = new RedBlackTree();

      tree.insert(10);
      tree.insert(5);
      tree.delete(5);
      tree.insert(15);
      tree.delete(10);
      tree.insert(20);

      assertFalse(tree.search(5));
      assertFalse(tree.search(10));
      assertTrue(tree.search(15));
      assertTrue(tree.search(20));
      assertTrue(tree.isRedBlackTree());
    }

    @Test
    @DisplayName("Build tree, clear it, rebuild it")
    void testRebuildTree() {
      RedBlackTree tree = new RedBlackTree();
      int[] values = {10, 5, 15, 3, 7, 12, 20};

      // Build
      for (int val : values) {
        tree.insert(val);
      }

      // Clear
      for (int val : values) {
        tree.delete(val);
      }

      // Rebuild
      for (int val : values) {
        tree.insert(val);
      }

      // Verify
      for (int val : values) {
        assertTrue(tree.search(val));
      }
      assertTrue(tree.isRedBlackTree());
    }
  }
}