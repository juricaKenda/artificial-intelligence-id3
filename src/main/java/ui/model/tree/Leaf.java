package ui.model.tree;

import ui.model.FeatureSet;

import java.util.Optional;

public class Leaf extends TreeElement {
    private String labelValue;

    public Leaf(FeatureSet featureSet, String label) {
        super(featureSet);
        this.labelValue = label;
    }

    @Override
    public Optional<String> labelValue() {
        return Optional.of(labelValue);
    }
}
