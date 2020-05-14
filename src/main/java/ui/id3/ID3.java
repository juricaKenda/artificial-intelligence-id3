package main.java.ui.id3;

import main.java.ui.id3.model.Config;
import main.java.ui.model.FeatureSet;
import main.java.ui.model.feature.Feature;
import main.java.ui.model.tree.Leaf;
import main.java.ui.model.tree.Node;
import main.java.ui.model.tree.Tree;
import main.java.ui.model.tree.TreeElement;

import java.util.ArrayList;
import java.util.List;

public class ID3 {
    private Config config;
    public ID3(Config config){
        this.config = config;
    }

    public Tree train(FeatureSet featureSet){
        return new Tree(trainModel(featureSet, null,0));
    }

    private TreeElement trainModel(FeatureSet featureSet, TreeElement parent, int depth) {
        if (depthExceeded(depth)){
            return new Leaf(parent,featureSet,featureSet.mostFrequentValue());
        }
        return featureSet.maxGainFeature()
                .map(splitter -> buildNode(featureSet, parent, splitter, depth))
                .orElse(buildLeaf(featureSet, parent));
    }

    private TreeElement buildNode(FeatureSet featureSet, TreeElement parent, Feature splitter, int depth) {
        List<TreeElement> children = new ArrayList<>();
        TreeElement node = new Node(parent, children, splitter, featureSet);
        for (FeatureSet set : featureSet.split(splitter)) {
            children.add(trainModel(set, node, depth+1));
        }
        return node;
    }

    private Leaf buildLeaf(FeatureSet featureSet, TreeElement parent) {
        return new Leaf(parent,featureSet,featureSet.leafLabelValue());
    }

    private boolean depthExceeded(int depth) {
        return config.depth() == depth;
    }
}
