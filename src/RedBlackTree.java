enum Color {
    RED,
    BLACK
}

/**
 * Implements a Red-Black Tree data structure.
 * This class ensures O(log n) time complexity for insertion, deletion,
 * and search operations by maintaining a balanced binary search tree.
 */
public class RedBlackTree {

    //Node Class
    private static class Node {
        int data;
        Color color;
        Node parent;
        Node leftChild;
        Node rightChild;

        //Constructor
        Node(int data) {
            this.data = data;
        }
    }

    private Node root;
    private final Node NIL;

    /**
     * Constructs an empty Red-Black Tree.
     * Initializes the sentinel NIL node and sets the root to NIL.
     */
    public RedBlackTree() {
        NIL = new Node(0);
        NIL.color = Color.BLACK;

        NIL.parent = NIL;
        NIL.leftChild = NIL;
        NIL.rightChild = NIL;

        this.root = NIL;
    }

    /**
     * Inserts a new item into the Red-Black Tree.
     * After insertion, it performs the necessary rotations and re-coloring
     * to maintain the Red-Black Tree properties.
     *
     * @param item The data key to be inserted.
     */
    public void insert(int item) {
        Node z = new Node(item);

        z.color = Color.RED;
        z.parent = NIL;
        z.leftChild = NIL;
        z.rightChild = NIL;

        Node y = this.root;
        Node x = NIL;

        while (y != NIL) {
            x = y;

            if (z.data < y.data) {
                y = y.leftChild;
            } else {
                y = y.rightChild;
            }
        }

        z.parent = x;
        if (x == NIL) {
            this.root = z;
        } else if (z.data < x.data) {
            x.leftChild = z;
        } else {
            x.rightChild = z;
        }

        insertFixUp(z);
    }

    /**
     * Deletes a given node from the Red Black Tree.
     *
     * @param key The data key for the node to delete.
     */
    public void delete(int key) {

    }

    /**
     * Searches for a specific key within the tree.
     *
     * @param key The data key to search for.
     *
     * @return true if the key is found, false otherwise.
     */
    public boolean search(int key) {
        return searchHelper(this.root, key);
    }

    /**
     * Helper method to recursively search for a key.
     *
     * @param node The root of the tree to be searched.
     * @param key The data key to search for.
     *
     * @return true if the key is found, false otherwise.
     */
    private boolean searchHelper(Node node, int key) {
        if (node == NIL) {
            return false;
        }

        if (node.data == key) {
            return true;
        }

        if (node.data < key) {
            return searchHelper(node.leftChild, key);
        } else {
            return searchHelper(node.rightChild, key);
        }
    }


    /**
     * Rotate the tree to the left around a given node.
     *
     * @param x The node to perform a left rotation around.
     */
    private void leftRotate(Node x) {
        Node y = x.rightChild;
        x.rightChild = y.leftChild;

        if (y.leftChild != NIL) {
            y.leftChild.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.leftChild) {
            x.parent.leftChild = y;
        } else {
            x.parent.rightChild = y;
        }

        y.leftChild = x;
        x.parent = y;
    }

    /**
     * Rotate the tree to the right around a given node.
     *
     * @param x The node to perform a right rotation around.
     */
    private void rightRotate(Node x) {
        Node y = x.leftChild;
        x.leftChild = y.rightChild;

        if (y.rightChild != NIL) {
            y.rightChild.parent = x;
        }

        y.parent = x.parent;

        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.rightChild) {
            x.parent.rightChild = y;
        } else {
            x.parent.leftChild = y;
        }

        y.rightChild = x;
        x.parent = y;
    }

    /**
     * Fixes the tree after inserting a new node, including
     * performing rotations and re-coloring nodes to preserve
     * the Red-Black properties.
     *
     * @param z The newly inserted node.
     */
    private void insertFixUp(Node z) {
        while (z.parent.color == Color.RED) {

            if (z.parent == z.parent.parent.leftChild) {
                Node y = z.parent.parent.rightChild;

                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.rightChild) {
                        z = z.parent;
                        leftRotate(z);
                    }

                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    rightRotate(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.leftChild;

                if (y.color == Color.RED) {
                    z.parent.color = Color.BLACK;
                    y.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.leftChild) {
                        z = z.parent;
                        rightRotate(z);
                    }

                    z.parent.color = Color.BLACK;
                    z.parent.parent.color = Color.RED;
                    leftRotate(z.parent.parent);
                }
            }
        }

        this.root.color = Color.BLACK;
    }
}
