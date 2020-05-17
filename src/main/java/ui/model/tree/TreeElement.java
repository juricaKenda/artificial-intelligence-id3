package main.java.ui.model.tree;

import main.java.ui.model.FeatureSet;
import main.java.ui.model.feature.Feature;

import java.util.List;
import java.util.Optional;

public abstract class TreeElement {
    private FeatureSet featureSet;

    public TreeElement(FeatureSet featureSet) {
        this.featureSet = featureSet;
    }

    public FeatureSet featureSet(){
        return featureSet;
    }
    public String proxy(){
        return featureSet.proxyAttribute();
    }

    public abstract List<TreeElement> children();
    public abstract Optional<Feature> splitterFeature();
    public abstract Optional<String> labelValue();
}
