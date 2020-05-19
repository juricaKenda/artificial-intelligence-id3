package main.java.ui.model.tree;

import java.util.List;
import java.util.Optional;

public class Tree {
    private TreeElement root;
    private Optional<String> fallback;

    public Tree(TreeElement root) {
        this.root = root;
        fallback = Optional.empty();
    }

    public TreeElement root() {
        return root;
    }

    public String labelKey() {
        return root.featureSet().label();
    }

    public String fallback() {
        return fallback.orElse(root.featureSet().mostFrequentValue());
    }

    public List<String> labelValues() {
        return root.featureSet().labelValues();
    }

    public void setFallback(String fallback) {
        this.fallback = Optional.of(fallback);
    }
}
