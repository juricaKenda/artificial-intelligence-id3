package main.java.ui.model.tree;

public class Tree {
    private TreeElement root;

    public Tree(TreeElement root) {
        this.root = root;
    }

    public TreeElement root() {
        return root;
    }

    public String labelKey() {
        return root.featureSet().label();
    }

    public int labelSize() {
        return root.featureSet().labelSize();
    }
}
