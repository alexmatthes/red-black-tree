package redblacktree;

/**
 * Main Program.
 * Author: Alex Matthes
 */
public class Main {
  static void main(String[] args) throws InterruptedException {
    RedBlackTree tree = new RedBlackTree();
    Visualization viz = new Visualization(tree);

    int[] values = {10, 5, 15, 2, 7, 20, 12};

    for (int v : values) {
      tree.insert(v);
      viz.repaint(); // Tell the visualizer to redraw
      Thread.sleep(1000); // Pause for 1 second to watch it
    }
  }
}