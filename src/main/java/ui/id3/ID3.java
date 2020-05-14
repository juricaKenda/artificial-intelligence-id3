package main.java.ui.id3;

import main.java.ui.model.FeatureSet;
import main.java.ui.model.feature.Feature;
import main.java.ui.model.tree.Leaf;
import main.java.ui.model.tree.Node;
import main.java.ui.model.tree.Tree;
import main.java.ui.model.tree.TreeElement;

import java.util.ArrayList;
import java.util.List;

public class ID3 {

    public Tree train(FeatureSet featureSet){
        return new Tree(trainModel(featureSet, null));
    }

    private TreeElement trainModel(FeatureSet featureSet,TreeElement parent) {
        return featureSet.maxGainFeature()
                .map(splitter -> buildNode(featureSet, parent, splitter))
                .orElse(buildLeaf(featureSet, parent));
    }

    private TreeElement buildNode(FeatureSet featureSet, TreeElement parent, Feature splitter) {
        List<TreeElement> children = new ArrayList<>();
        TreeElement node = new Node(parent, children, splitter, featureSet);
        for (FeatureSet set : featureSet.split(splitter)) {
            children.add(trainModel(set, node));
        }
        return node;
    }

    private Leaf buildLeaf(FeatureSet featureSet, TreeElement parent) {
        return new Leaf(parent,featureSet,featureSet.labelValue());
    }
}
