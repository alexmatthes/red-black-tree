package redblacktree;

import java.util.NoSuchElementException;

/**
 * Implements a Red-Black Tree data structure.
 * This class ensures O(log n) time complexity for insertion, deletion,
 * and search operations by maintaining a balanced binary search tree.
 * Author: Alex Matthes
 */
public class RedBlackTree {
  private enum Color {
    RED,
    BLACK
  }

  /**
   *  Represents a node in the Red-Black Tree.
   *  Each node contains data, color, and references to parent and children.
   */
  private static class Node {
    int data;
    Color color;
    Node parent;
    Node leftChild;
    Node rightChild;

    /**
     * Constructs a new node with the specified data.
     * Color and references are initialized separately.
     *
     * @param data The integer value data to store in the node.
     */
    Node(int data) {
      this.data = data;
    }
  }

  private Node root;
  private final Node nullNode;

  /**
   * Constructs an empty Red-Black Tree.
   * Initializes the sentinel NIL node and sets the root to NIL.
   */
  public RedBlackTree() {
    nullNode = new Node(0);
    nullNode.color = Color.BLACK;

    nullNode.parent = nullNode;
    nullNode.leftChild = nullNode;
    nullNode.rightChild = nullNode;

    this.root = nullNode;
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
    newNode.parent = nullNode;
    newNode.leftChild = nullNode;
    newNode.rightChild = nullNode;

    Node currentNode = this.root;
    Node parentNode = nullNode;

    while (currentNode != nullNode) {
      parentNode = currentNode;

      if (newNode.data < currentNode.data) {
        currentNode = currentNode.leftChild;
      } else {
        currentNode = currentNode.rightChild;
      }
    }

    newNode.parent = parentNode;
    if (parentNode == nullNode) {
      this.root = newNode;
    } else if (newNode.data < parentNode.data) {
      parentNode.leftChild = newNode;
    } else {
      parentNode.rightChild = newNode;
    }

    insertFixUp(newNode);
  }

  /*
   ---------------------Delete Methods------------------------
   */

  /**
   * Deletes a given node from the Red Black Tree.
   *
   * @param key The data key for the node to delete.
   *
   * @throws NoSuchElementException If the given key is not found in the tree.
   */
  public void delete(int key) {
    Node nodeToDelete = findNode(root, key);

    if (nodeToDelete == nullNode) {
      throw new NoSuchElementException("Key not found: " + key);
    }

    deleteNode(nodeToDelete);
  }

  /**
   * Finds and returns the node containing the specified key.
   *
   * @param node The root of the subtree to search
   * @param key The key to search for
   *
   * @return The node containing the key, or nullNode if not found
   */
  private Node findNode(Node node, int key) {
    if (node == nullNode) {
      return nullNode;
    }

    if (node.data == key) {
      return node;
    }

    if (key < node.data) {
      return findNode(node.leftChild, key);
    } else {
      return findNode(node.rightChild, key);
    }
  }

  /**
   * Deletes the given node from the Red-Black Tree.
   *
   * @param nodeToDelete The node in the tree to be deleted.
   *
   */
  private void deleteNode(Node nodeToDelete) {
    Node successor = nodeToDelete;
    Node nodeChild;
    Color originalColor = successor.color;

    if (nodeToDelete.leftChild == nullNode) {
      nodeChild = nodeToDelete.rightChild;
      transplant(nodeToDelete, nodeToDelete.rightChild);
    } else if (nodeToDelete.rightChild == nullNode) {
      nodeChild = nodeToDelete.leftChild;
      transplant(nodeToDelete, nodeToDelete.leftChild);
    } else {
      successor = minimum(nodeToDelete.rightChild);
      originalColor = successor.color;
      nodeChild = successor.rightChild;

      if (successor.parent == nodeToDelete) {
        nodeChild.parent = successor;
      } else {
        transplant(successor, successor.rightChild);
        successor.rightChild = nodeToDelete.rightChild;
        successor.rightChild.parent = successor;
      }

      transplant(nodeToDelete, successor);
      successor.leftChild = nodeToDelete.leftChild;
      successor.leftChild.parent = successor;
      successor.color = nodeToDelete.color;
    }

    if (originalColor == Color.BLACK) {
      deleteFixUp(nodeChild);
    }
  }

  /**
   * Fixes the tree after deleting a node, including
   * performing rotations and re-coloring nodes to preserve
   * the Red-Black properties.
   *
   * @param nodeToBeFixed The node to fix.
   */
  private void deleteFixUp(Node nodeToBeFixed) {
    while (nodeToBeFixed != root && nodeToBeFixed.color == Color.BLACK) {
      if (nodeToBeFixed == nodeToBeFixed.parent.leftChild) {
        Node sibling = nodeToBeFixed.parent.rightChild;

        if (sibling.color == Color.RED) {
          sibling.color = Color.BLACK;
          nodeToBeFixed.parent.color = Color.RED;

          leftRotate(nodeToBeFixed.parent);

          sibling = nodeToBeFixed.parent.rightChild;
        }

        if (sibling.leftChild.color == Color.BLACK
                && sibling.rightChild.color == Color.BLACK) {
          sibling.color = Color.RED;

          nodeToBeFixed = nodeToBeFixed.parent;
        } else {
          if (sibling.rightChild.color == Color.BLACK) {
            sibling.leftChild.color = Color.BLACK;
            sibling.color = Color.RED;

            rightRotate(sibling);

            sibling = nodeToBeFixed.parent.rightChild;
          }

          sibling.color = nodeToBeFixed.parent.color;

          nodeToBeFixed.parent.color = Color.BLACK;
          sibling.rightChild.color = Color.BLACK;

          leftRotate(nodeToBeFixed.parent);

          nodeToBeFixed = root;
        }
      } else {
        Node sibling = nodeToBeFixed.parent.leftChild;

        if (sibling.color == Color.RED) {

          sibling.color = Color.BLACK;
          nodeToBeFixed.parent.color = Color.RED;

          rightRotate(nodeToBeFixed.parent);

          sibling = nodeToBeFixed.parent.leftChild;
        }

        if (sibling.rightChild.color == Color.BLACK
                && sibling.leftChild.color == Color.BLACK) {
          sibling.color = Color.RED;

          nodeToBeFixed = nodeToBeFixed.parent;
        } else {
          if (sibling.leftChild.color == Color.BLACK) {
            sibling.rightChild.color = Color.BLACK;
            sibling.color = Color.RED;

            leftRotate(sibling);

            sibling = nodeToBeFixed.parent.leftChild;
          }
          sibling.color = nodeToBeFixed.parent.color;

          nodeToBeFixed.parent.color = Color.BLACK;
          sibling.leftChild.color = Color.BLACK;

          rightRotate(nodeToBeFixed.parent);
          nodeToBeFixed = root;

        }
      }
    }

    nodeToBeFixed.color = Color.BLACK;
  }

  /**
   * Helper method to transplant two nodes within the Red-Black tree.
   *
   * @param oldRoot The old node in the tree.
   * @param newRoot The new node to be added to the tree.
   */
  private void transplant(Node oldRoot, Node newRoot) {
    if (oldRoot.parent == nullNode) {
      this.root = newRoot;
    } else if (oldRoot == oldRoot.parent.leftChild) {
      oldRoot.parent.leftChild = newRoot;
    } else {
      oldRoot.parent.rightChild = newRoot;
    }
    newRoot.parent = oldRoot.parent;
  }

  /**
   * Helper method to get the minimum node in the tree.
   *
   * @param root The root of the tree to find the minimum value of.
   */
  private Node minimum(Node root) {
    Node current = root;

    while (current.leftChild != nullNode) {
      current = current.leftChild;
    }

    return current;
  }

  /*
   ---------------------Search Methods------------------------
  */

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
    if (node == nullNode) {
      return false;
    }

    if (node.data == key) {
      return true;
    }

    if (key < node.data) {
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

    if (currentRightChild.leftChild != nullNode) {
      currentRightChild.leftChild.parent = currentNode;
    }

    currentRightChild.parent = currentNode.parent;

    if (currentNode.parent == nullNode) {
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

    if (currentLeftChild.rightChild != nullNode) {
      currentLeftChild.rightChild.parent = currentNode;
    }

    currentLeftChild.parent = currentNode.parent;

    if (currentNode.parent == nullNode) {
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
        Node uncle = currentNode.parent.parent.rightChild;

        if (uncle.color == Color.RED) {
          currentNode.parent.color = Color.BLACK;
          uncle.color = Color.BLACK;
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
        Node uncle = currentNode.parent.parent.leftChild;

        if (uncle.color == Color.RED) {
          currentNode.parent.color = Color.BLACK;
          uncle.color = Color.BLACK;
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
   ---------------------Test Methods------------------------
   */

  /**
   * Gets the data of the root node. For testing purposes.
   *
   * @return The data of the root, or -1 if tree is empty.
   *
   * @throws IllegalStateException if the tree is empty.
   */
  public int getRootData() {
    if (root == nullNode) {
      throw new IllegalStateException("Tree is empty");
    }
    return this.root.data;
  }

  /**
   * Checks the tree to see if it is a valid Red-Black tree.
   *
   * @return Whether the Red-Black tree is valid.
   */
  public boolean isRedBlackTree() {
    if (root.color != Color.BLACK) {
      return false;
    }

    if (!isBinarySearchTree(root, null, null)) {
      return false;
    }

    return (validate(root) != -1);
  }

  /**
   * Checks if the tree maintains the Binary Search Tree property.
   * Uses nullable Integer bounds to handle edge cases properly.
   *
   * @param node The current node being validated
   * @param min The minimum allowed value (null means no minimum)
   * @param max The maximum allowed value (null means no maximum)
   * @return true if BST property is maintained, false otherwise
   */
  private boolean isBinarySearchTree(Node node, Integer min, Integer max) {
    if (node == nullNode) {
      return true;
    }

    // Check minimum bound (if it exists)
    if (min != null && node.data <= min) {
      return false;
    }

    // Check maximum bound (if it exists)
    if (max != null && node.data >= max) {
      return false;
    }

    return isBinarySearchTree(node.leftChild, min, node.data)
            && isBinarySearchTree(node.rightChild, node.data, max);
  }

  /**
   * return the black-height of the subtree.
   *
   * @return The black-height root, or -1.
   */
  private int validate(Node n) {
    if (n == nullNode) {
      return 1;
    }

    if (n.color == Color.RED) {
      if (n.leftChild.color == Color.RED ||  n.rightChild.color == Color.RED) {
        return -1;
      }
    }

    int leftBlackHeight = validate(n.leftChild);
    int rightBlackHeight = validate(n.rightChild);

    if (leftBlackHeight == -1 || rightBlackHeight == -1) {
      return -1;
    }

    if (leftBlackHeight != rightBlackHeight) {
      return -1;
    }

    if (n.color == Color.RED) {
      return leftBlackHeight;
    } else {
      return leftBlackHeight + 1;
    }
  }
}
