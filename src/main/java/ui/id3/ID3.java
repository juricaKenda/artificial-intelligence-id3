package main.java.ui.id3;

import main.java.ui.id3.model.Config;
import main.java.ui.id3.model.Model;
import main.java.ui.id3.model.SearchableTuple;
import main.java.ui.id3.utils.Analytics;
import main.java.ui.id3.utils.TreeZip;
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
    private Model model;

    public ID3(Config config){
        this.config = config;
    }

    public void fit(FeatureSet featureSet){
        Tree tree = new Tree(trainModel(featureSet, null, 0));
        model = TreeZip.zip(tree);
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

    public Analytics predict(List<SearchableTuple> searchables){
        Analytics analytics = new Analytics(model.labelSize(),model.depthLog());
        String labelKey = model.labelKey();
        for (SearchableTuple searchable : searchables) {
            String prediction = predict(searchable);
            String actual = searchable.get(labelKey);
            analytics.commit(prediction,actual);
        }
        return analytics;
    }

    public String predict(SearchableTuple searchable){
        model.load();
        while (model.requireFeed()){
            String key = model.nextRequest();
            String value = searchable.get(key);
            model.feed(value);
        }
        return model.result();
    }
}
