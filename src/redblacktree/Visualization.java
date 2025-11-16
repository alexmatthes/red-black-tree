package redblacktree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Visualize the Red-Black Tree.
 * Author: Alex Matthes
 */
public class Visualization {

  private final DrawingPanel canvas;
  private final RedBlackTree tree;

  /**
   * Visualize the Red-Black Tree.
   *
   * @param tree The Red-Black tree to visualize.
   */
  public Visualization(RedBlackTree tree) {
    this.tree = tree;

    // Setup the main window.
    JFrame frame = new JFrame("Red-Black Tree Visualizer");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Arrange the panels with BorderLayout.
    frame.setLayout(new BorderLayout());

    // Create the drawing canvas.
    canvas = new DrawingPanel();

    // Add the canvas to the center of the window.
    frame.add(canvas, BorderLayout.CENTER);

    // Create the interactive control panel
    JPanel controlPanel = new JPanel();
    JLabel label = new JLabel("Value:");
    JTextField numberField = new JTextField(10); // 10 chars wide
    JButton insertButton = new JButton("Insert");
    JButton deleteButton = new JButton("Delete");

    // Add components to the control panel
    controlPanel.add(label);
    controlPanel.add(numberField);
    controlPanel.add(insertButton);
    controlPanel.add(deleteButton);

    // Add the control panel to the bottom of the window
    frame.add(controlPanel, BorderLayout.SOUTH);

    // Insert Button Logic
    insertButton.addActionListener(e -> {
      try {
        String text = numberField.getText();
        int value = Integer.parseInt(text);
        tree.insert(value);
        canvas.repaint(); // Redraw the tree
        numberField.setText(""); // Clear the text field
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Please enter a valid integer.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    // Delete Button Logic
    deleteButton.addActionListener(e -> {
      try {
        String text = numberField.getText();
        int value = Integer.parseInt(text);
        tree.delete(value);
        canvas.repaint(); // Redraw the tree
        numberField.setText(""); // Clear the text field
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Please enter a valid integer.",
                "Input Error", JOptionPane.ERROR_MESSAGE);
      } catch (NoSuchElementException ex) {
        JOptionPane.showMessageDialog(frame, ex.getMessage(),
                "Delete Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    // Make the window visible (must be last)
    frame.setVisible(true);
  }

  // This is the "canvas" we'll draw on
  private class DrawingPanel extends JPanel {

    private int inOrderIndex = 0; // Tracks X-position

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setFont(new Font("Arial", Font.PLAIN, 12));

      super.paintComponent(g);
      inOrderIndex = 0;
      // Pass 0,0 for root's "parent" - it won't be used.
      drawNode(g, tree.getRoot());
    }

    // parentX and parentY are the coordinates of this node's parent
    private void drawNode(Graphics g, RedBlackTree.Node node) {
      if (node == tree.getNullNode()) {
        return;
      }

      // 1. Go Left
      // We don't know our (x,y) yet, so we can't pass them down.
      // This is the fundamental problem.

      // --- Let's try again ---
      // The "two-pass" method (storing coords in a Map) is
      // really the only clean way.

      // --- OK, a "post-order" drawing hack ---
      // Calculate all coordinates first (in-order), then draw (post-order).

      // Map to store where each node should be
      Map<RedBlackTree.Node, Point> nodeCoords = new HashMap<>();
      inOrderIndex = 0;

      // 1. PASS 1: Calculate all coordinates
      calculateCoords(tree.getRoot(), 0, nodeCoords);

      // 2. PASS 2: Draw all lines
      drawLines(g, tree.getRoot(), nodeCoords);

      // 3. PASS 3: Draw all nodes (so they are on top of the lines)
      drawNodes(g, tree.getRoot(), nodeCoords);
    }

    // PASS 1: (Your original logic, but just for coords)
    private void calculateCoords(RedBlackTree.Node node, int depth,
                                 Map<RedBlackTree.Node, Point> coords) {
      if (node == tree.getNullNode()) {
        return;
      }

      calculateCoords(tree.getLeftChild(node), depth + 1, coords);

      int x = (inOrderIndex * 60) + 50;
      int y = (depth * 60) + 50;
      coords.put(node, new Point(x, y));
      inOrderIndex++;

      calculateCoords(tree.getRightChild(node), depth + 1, coords);
    }

    // PASS 2: Draw lines
    private void drawLines(Graphics g, RedBlackTree.Node node,
                           Map<RedBlackTree.Node, Point> coords) {
      if (node == tree.getNullNode()) {
        return;
      }

      Point p1 = coords.get(node);
      RedBlackTree.Node left = tree.getLeftChild(node);
      RedBlackTree.Node right = tree.getRightChild(node);

      g.setColor(Color.BLACK);

      if (left != tree.getNullNode()) {
        Point p2 = coords.get(left);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        drawLines(g, left, coords); // Recurse
      }

      if (right != tree.getNullNode()) {
        Point p2 = coords.get(right);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
        drawLines(g, right, coords); // Recurse
      }
    }

    // PASS 3: Draw nodes (so they're on top of lines)
    private void drawNodes(Graphics g, RedBlackTree.Node node,
                           Map<RedBlackTree.Node, Point> coords) {
      if (node == tree.getNullNode()) {
        return;
      }

      Point p = coords.get(node);

      if (tree.isNodeRed(node)) {
        g.setColor(Color.RED);
      } else {
        g.setColor(Color.BLACK);
      }

      g.fillOval(p.x - 15, p.y - 15, 30, 30);
      g.setColor(Color.WHITE);
      g.drawString(String.valueOf(tree.getNodeData(node)), p.x - 5, p.y + 5);

      drawNodes(g, tree.getLeftChild(node), coords);
      drawNodes(g, tree.getRightChild(node), coords);
    }
  }

  /**
   * Method to refresh the canvas when an update has been made to the Red-Black Tree.
   */
  public void repaint() {
    canvas.repaint();
  }
}
