package ui.model.tree;

import ui.model.FeatureSet;
import ui.model.feature.Feature;

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

}
