/**
 * Implements a Red-Black Tree data structure.
 * This class ensures O(log n) time complexity for insertion, deletion,
 * and search operations by maintaining a balanced binary search tree.
 */
public class RedBlackTree {
    private enum Color {
        RED,
        BLACK
    }

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
        Node newNode = new Node(item);

        newNode.color = Color.RED;
        newNode.parent = NIL;
        newNode.leftChild = NIL;
        newNode.rightChild = NIL;

        Node currentNode = this.root;
        Node parentNode = NIL;

        while (currentNode != NIL) {
            parentNode = currentNode;

            if (newNode.data < currentNode.data) {
                currentNode = currentNode.leftChild;
            } else {
                currentNode = currentNode.rightChild;
            }
        }

        newNode.parent = parentNode;
        if (parentNode == NIL) {
            this.root = newNode;
        } else if (newNode.data < parentNode.data) {
            parentNode.leftChild = newNode;
        } else {
            parentNode.rightChild = newNode;
        }

        insertFixUp(newNode);
    }

    /**
     * Deletes a given node from the Red Black Tree.
     *
     * @param key The data key for the node to delete.
     */
    public void delete(int key) {
        // TODO
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

        if (key < node.data ) {
            return searchHelper(node.leftChild, key);
        } else {
            return searchHelper(node.rightChild, key);
        }
    }


    /**
     * Rotate the tree to the left around a given node.
     *
     * @param currentNode The node to perform a left rotation around.
     */
    private void leftRotate(Node currentNode) {
        Node currentRightChild = currentNode.rightChild;
        currentNode.rightChild = currentRightChild.leftChild;

        if (currentRightChild.leftChild != NIL) {
            currentRightChild.leftChild.parent = currentNode;
        }

        currentRightChild.parent = currentNode.parent;

        if (currentNode.parent == NIL) {
            root = currentRightChild;
        } else if (currentNode == currentNode.parent.leftChild) {
            currentNode.parent.leftChild = currentRightChild;
        } else {
            currentNode.parent.rightChild = currentRightChild;
        }

        currentRightChild.leftChild = currentNode;
        currentNode.parent = currentRightChild;
    }

    /**
     * Rotate the tree to the right around a given node.
     *
     * @param currentNode The node to perform a right rotation around.
     */
    private void rightRotate(Node currentNode) {
        Node currentLeftChild = currentNode.leftChild;
        currentNode.leftChild = currentLeftChild.rightChild;

        if (currentLeftChild.rightChild != NIL) {
            currentLeftChild.rightChild.parent = currentNode;
        }

        currentLeftChild.parent = currentNode.parent;

        if (currentNode.parent == NIL) {
            root = currentLeftChild;
        } else if (currentNode == currentNode.parent.rightChild) {
            currentNode.parent.rightChild = currentLeftChild;
        } else {
            currentNode.parent.leftChild = currentLeftChild;
        }

        currentLeftChild.rightChild = currentNode;
        currentNode.parent = currentLeftChild;
    }

    /**
     * Fixes the tree after inserting a new node, including
     * performing rotations and re-coloring nodes to preserve
     * the Red-Black properties.
     *
     * @param currentNode The newly inserted node.
     */
    private void insertFixUp(Node currentNode) {
        while (currentNode.parent.color == Color.RED) {

            if (currentNode.parent == currentNode.parent.parent.leftChild) {
                Node uncleRightChild = currentNode.parent.parent.rightChild;

                if (uncleRightChild.color == Color.RED) {
                    currentNode.parent.color = Color.BLACK;
                    uncleRightChild.color = Color.BLACK;
                    currentNode.parent.parent.color = Color.RED;
                    currentNode = currentNode.parent.parent;
                } else {
                    if (currentNode == currentNode.parent.rightChild) {
                        currentNode = currentNode.parent;
                        leftRotate(currentNode);
                    }

                    currentNode.parent.color = Color.BLACK;
                    currentNode.parent.parent.color = Color.RED;
                    rightRotate(currentNode.parent.parent);
                }
            } else {
                Node uncleLeftChild = currentNode.parent.parent.leftChild;

                if (uncleLeftChild.color == Color.RED) {
                    currentNode.parent.color = Color.BLACK;
                    uncleLeftChild.color = Color.BLACK;
                    currentNode.parent.parent.color = Color.RED;
                    currentNode = currentNode.parent.parent;
                } else {
                    if (currentNode == currentNode.parent.leftChild) {
                        currentNode = currentNode.parent;
                        rightRotate(currentNode);
                    }

                    currentNode.parent.color = Color.BLACK;
                    currentNode.parent.parent.color = Color.RED;
                    leftRotate(currentNode.parent.parent);
                }
            }
        }

        this.root.color = Color.BLACK;
    }
    /*
     * ---------------------Test Methods------------------------
     */

    /**
     * Gets the data of the root node. For testing purposes.
     *
     * @return The data of the root, or -1 if tree is empty.
     */
    public int getRootData() {
        return this.root.data;
    }

    /**
     * Checks the tree to see if it is a valid Red-Black tree.
     *
     * @return Whether the Red-Black tree is valid.
     */
    public boolean isRedBlackTree() {
        boolean result;

        //Check if the root is black, if not it is not a valid Red-Black tree.
        result = this.root.color == Color.BLACK;

        //Check if there are any red nodes back to back, if not it is not a valid Red-Black tree.
        int height = validate(this.root);
        if (height == -1) {
            result = false;
        }

        return result;
    }

    /**
     * return the black-height of the subtree.
     *
     * @return The black-height root, or -1.
     */
    private int validate(Node n) {
        if (n == NIL) {
            return 1;
        }

        if (n.color == Color.RED) {
            if (n.leftChild.color == Color.RED ||  n.rightChild.color == Color.RED) {
                return -1;
            }
        }

        int leftBHeight = validate(n.leftChild);
        int rightBHeight = validate(n.rightChild);

        if (leftBHeight == -1 || rightBHeight == -1) {
            return -1;
        }

        if (leftBHeight != rightBHeight) {
            return -1;
        }

        if (n.color == Color.RED) {
            return leftBHeight;
        } else {
            return leftBHeight + 1;
        }

    }
}
