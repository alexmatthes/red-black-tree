/**
 * Implements a basic, unbalanced Binary Search Tree for analysis and comparison.
 * Author: Alex Matthes
 */
public class BinarySearchTree {

    private static class Node {
        int data;
        Node parent;
        Node leftChild;
        Node rightChild;

        Node(int data) {
            this.data = data;
        }
    }

    private Node root;

    public BinarySearchTree() {
        this.root = null;
    }

    public void insert(int item) {
        Node newNode = new Node(item);
        Node current = this.root;
        Node parent = null;

        while (current != null) {
            parent = current;
            if (newNode.data < current.data) {
                current = current.leftChild;
            } else {
                current = current.rightChild;
            }
        }

        newNode.parent = parent;
        if (parent == null) {
            this.root = newNode; // Tree was empty
        } else if (newNode.data < parent.data) {
            parent.leftChild = newNode;
        } else {
            parent.rightChild = newNode;
        }
    }
}
