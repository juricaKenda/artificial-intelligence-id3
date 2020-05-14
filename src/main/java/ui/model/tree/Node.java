package main.java.ui.model.tree;

import main.java.ui.model.FeatureSet;
import main.java.ui.model.feature.Feature;

import java.util.List;
import java.util.Optional;

public class Node extends TreeElement {
    private List<TreeElement> children;
    private Feature splitterFeature;


    public Node(TreeElement parent, List<TreeElement> children, Feature splitterFeature, FeatureSet featureSet) {
        super(parent,featureSet);
        this.children = children;
        this.splitterFeature = splitterFeature;
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
    public boolean isPure() {
        return false;
    }

    @Override
    public Optional<String> labelValue() {
        return Optional.empty();
    }

}
