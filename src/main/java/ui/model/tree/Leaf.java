package ui.model.tree;

import ui.model.FeatureSet;
import ui.model.feature.Feature;

import java.util.Collections;

import java.util.List;
import java.util.Optional;

public class Leaf extends TreeElement {
    private String labelValue;

    public Leaf(FeatureSet featureSet, String label) {
        super(featureSet);
        this.labelValue = label;
    }

    @Override
    public List<TreeElement> children() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Feature> splitterFeature() {
        return Optional.empty();
    }

    @Override
    public Optional<String> labelValue() {
        return Optional.of(labelValue);
    }
}
