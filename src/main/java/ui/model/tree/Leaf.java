package main.java.ui.model.tree;

import main.java.ui.model.FeatureSet;
import main.java.ui.model.feature.Feature;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Leaf extends TreeElement {
    private String labelValue;

    public Leaf(TreeElement parent, FeatureSet featureSet, String label) {
        super(parent,featureSet);
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
    public boolean isPure() {
        return true;
    }

    @Override
    public Optional<String> labelValue() {
        return Optional.of(labelValue);
    }
}
