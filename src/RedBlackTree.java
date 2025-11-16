enum Color {
    RED,
    BLACK
}

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

    //Constructor
    public RedBlackTree() {
        NIL = new Node(0);
        NIL.color = Color.BLACK;

        NIL.parent = NIL;
        NIL.leftChild = NIL;
        NIL.rightChild = NIL;

        this.root = NIL;
    }

    public void insert(int item) {
        Node z = new Node(item);

        z.color = Color.RED;
        z.parent = NIL;
        z.leftChild = NIL;
        z.rightChild = NIL;

        Node x = this.root;
        Node y = NIL;

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

    public void delete(int key) {
        // Public-facing delete
    }

    public boolean search(int key) {
        // Public-facing search
    }

    private void leftRotate(Node x) {
        // All the rotation logic...
    }

    private void rightRotate(Node y) {
        // ...
    }

    private void insertFixUp(Node z) {
        // ...
    }
}
