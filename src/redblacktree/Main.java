package redblacktree;

/**
 * Main Program to launch the Red-Black Tree Visualization Application.
 * Author: Alex Matthes
 */
public class Main {
  static void main() {
    // Create a single instance of a Red-Black Tree
    RedBlackTree tree = new RedBlackTree();

    // Create a single instance of the visualization, passing in the tree.
    new Visualization(tree);
  }
}