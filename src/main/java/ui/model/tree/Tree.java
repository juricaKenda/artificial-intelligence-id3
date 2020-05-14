package main.java.ui.model.tree;

public class Tree {
    private TreeElement root;

    public Tree(TreeElement root) {
        this.root = root;
    }

    public TreeElement root() {
        return root;
    }
}
