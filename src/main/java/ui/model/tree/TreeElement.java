package ui.model.tree;

import ui.model.FeatureSet;
import ui.model.feature.Feature;

import java.util.Collections;
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

    public List<TreeElement> children(){
        return Collections.emptyList();
    }
    public Optional<Feature> splitterFeature(){
        return Optional.empty();
    }
    public Optional<String> labelValue(){
        return Optional.empty();
    }
}
