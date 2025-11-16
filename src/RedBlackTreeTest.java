import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {

    @Test
    void testInsertAndSearchOnEmptyTree() {
        // 1. Arrange: Create the object you are testing
        RedBlackTree tree = new RedBlackTree();

        // 2. Act: Call the method(s) you want to test
        tree.insert(10);

        // 3. Assert: Check if the result is what you expected
        //    We use the helper methods from Assertions.
        assertTrue(tree.search(10));
        assertFalse(tree.search(99)); // Check a value that shouldn't exist
    }

    @Test
    void testSearchOnEmptyTree() {
        // 1. Arrange
        RedBlackTree tree = new RedBlackTree();

        // 2. Act (nothing to act, we're testing the initial state)

        // 3. Assert
        assertFalse(tree.search(10));
    }

    // You will add many more @Test methods here...
}