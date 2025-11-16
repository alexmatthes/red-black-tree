import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {
    @Test
    void testSearchOnEmptyTree() {
        // 1. Arrange
        RedBlackTree tree = new RedBlackTree();

        // 2. Act

        // 3. Assert
        assertFalse(tree.search(10));
    }

    @Test
    void testSearchOnNonEmptyTree1() {
        // 1. Arrange
        RedBlackTree tree = new RedBlackTree();

        // 2. Act
        tree.insert(10);

        // 3. Assert
        assertTrue(tree.search(10));
    }

    @Test
    void testSearchOnNonEmptyTree2() {
        // 1. Arrange
        RedBlackTree tree = new RedBlackTree();

        // 2. Act
        tree.insert(10);

        // 3. Assert
        assertFalse(tree.search(99));
    }

    @Test
    void testSearchOnNonEmptyTree3() {
        // 1. Arrange
        RedBlackTree tree = new RedBlackTree();

        // 2. Act
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(2);
        tree.insert(7);
        tree.insert(20);

        // 3. Assert
        assertTrue(tree.search(7));
    }

    @Test
    void testInsertCausesBlackUncleLineCase() {
        // This tests the "Line (Right)" scenario
        // Inserting 10, 15, 20 should trigger a leftRotate on 10.

        // 1. Arrange
        RedBlackTree tree = new RedBlackTree();

        // 2. Act
        tree.insert(10);
        tree.insert(15);
        tree.insert(20);

        // 3. Assert
        // The root should have changed from 10 to 15
        assertEquals(15, tree.getRootData());

        // And all nodes should still be searchable
        assertTrue(tree.search(10));
        assertTrue(tree.search(15));
        assertTrue(tree.search(20));
        assertFalse(tree.search(99));
    }

    @Test
    void testInsertCausesBlackUncleTriangleCase() {
        // This tests the "Triangle (Right)" scenario
        // Inserting 10, 15, 12 should trigger rightRotate(15) then leftRotate(10).

        // 1. Arrange
        RedBlackTree tree = new RedBlackTree();

        // 2. Act
        tree.insert(10);
        tree.insert(15);
        tree.insert(12);

        // 3. Assert
        // The final root should be 12
        assertEquals(12, tree.getRootData());

        assertTrue(tree.search(10));
        assertTrue(tree.search(15));
        assertTrue(tree.search(12));
    }

    @Test
    void testDeleteLeafNode() {
        // 1. Arrange
        RedBlackTree tree = new RedBlackTree();
        tree.insert(10);
        tree.insert(5); // 5 is a leaf

        // 2. Act
        tree.delete(5);

        // 3. Assert
        assertFalse(tree.search(5));
        assertTrue(tree.search(10));
    }

    @Test
    void testDeleteNodeWithTwoChildren() {
        // Deleting the root (10) should cause its successor (12) to replace it.

        // 1. Arrange
        RedBlackTree tree = new RedBlackTree();
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(12); // Successor of 10

        // 2. Act
        tree.delete(10);

        // 3. Assert
        // The successor (12) should move up and become the new root
        assertEquals(12, tree.getRootData());

        assertFalse(tree.search(10));
        assertTrue(tree.search(5));
        assertTrue(tree.search(15));
        assertTrue(tree.search(12));
    }
}