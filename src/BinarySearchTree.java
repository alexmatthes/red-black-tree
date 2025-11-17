/**
 * Implements a basic Binary Search Tree data structure for analysis and comparison.
 * Author: Alex Matthes
 */
public class BinarySearchTree {
  /**
   *  Represents a node in the Binary Search Tree.
   *  Each node contains data and references to parent and children.
   */
  private static class Node {
    int data;
    Node parent;
    Node leftChild;
    Node rightChild;

    /**
     * Constructs a new node with the specified data.
     *
     * @param data The integer value data to store in the node.
     */
    Node(int data) {
      this.data = data;
    }
  }

  private Node root;

  /**
   * Constructs an empty Binary Search Tree.
   * Initializes the sentinel null node and sets the root to the null node.
   */
  public BinarySearchTree() {
    this.root = null;
  }

  /**
   * Inserts a new item into the Red-Black Tree.
   * This is an unbalanced insertion.
   *
   * @param item The data key to be inserted.
   */
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
      this.root = newNode;
    } else if (newNode.data < parent.data) {
      parent.leftChild = newNode;
    } else  {
      parent.rightChild = newNode;
    }
  }

  /**
   * Searches for a specific key within the tree.
   *
   * @param key The data key to search for.
   *
   * @return true if the key is found, false otherwise.
   */
  public boolean search(int key) {
    Node current = this.root;

    while (current != null) {
      if (current.data == key) {
        return true;
      } else if (current.data > key) {
        current = current.leftChild;
      } else  {
        current = current.rightChild;
      }
    }
    return false;
  }
}
