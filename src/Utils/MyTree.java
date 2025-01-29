package Utils;

import java.util.ArrayList;
import java.util.List;

public class MyTree<T> {
    private Node<T> root;

    public MyTree() {
        root = null;
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public void inorderTraversal(List<T> list, Node<T> node) {
        if (node == null) return;
        inorderTraversal(list, node.getLeft());
        list.add(node.getValue());
        inorderTraversal(list, node.getRight());
    }

    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public String toString() {
        List<T> list = new ArrayList<>();
        inorderTraversal(list, root);
        return list.toString();
    }
}
