package main.java.ui.model.tree;

import main.java.ui.model.FeatureSet;
import main.java.ui.model.feature.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Node extends TreeElement {
    private List<TreeElement> children;
    private Feature splitterFeature;

    public Node(Feature splitterFeature, FeatureSet featureSet) {
        super(featureSet);
        this.children = new ArrayList<>();
        this.splitterFeature = splitterFeature;
    }
    public void addChild(TreeElement treeElement){
        children.add(treeElement);
    }
    @Override
    public List<TreeElement> children() {
        return children;
    }

    @Override
    public Optional<Feature> splitterFeature() {
        return Optional.of(splitterFeature);
    }

    @Override
    public Optional<String> labelValue() {
        return Optional.empty();
    }

}
