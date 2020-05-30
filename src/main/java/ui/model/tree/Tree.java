package ui.model.tree;

import java.util.List;

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

    public List<String> labelValues() {
        return root.featureSet().labelValues();
    }
}
