package main.java.ui.model.tree;

import main.java.ui.model.FeatureSet;
import main.java.ui.model.feature.Feature;

import java.util.List;
import java.util.Optional;

public abstract class TreeElement {
    private TreeElement parent;
    private FeatureSet featureSet;

    public TreeElement(TreeElement parent, FeatureSet featureSet) {
        this.parent = parent;
        this.featureSet = featureSet;
    }

    public TreeElement parent(){
        return parent;
    }
    public FeatureSet featureSet(){
        return featureSet;
    }
    public String proxy(){
        return featureSet.proxyAttribute();
    }

    public abstract List<TreeElement> children();
    public abstract Optional<Feature> splitterFeature();
    public abstract boolean isPure();
    public abstract Optional<String> labelValue();
}
