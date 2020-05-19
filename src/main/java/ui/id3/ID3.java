package main.java.ui.id3;

import main.java.ui.Analytics;
import main.java.ui.Runner;
import main.java.ui.id3.model.Config;
import main.java.ui.id3.model.Model;
import main.java.ui.id3.model.SearchableTuple;
import main.java.ui.id3.utils.ID3Analytics;
import main.java.ui.id3.utils.TreeZip;
import main.java.ui.model.FeatureSet;
import main.java.ui.model.feature.Feature;
import main.java.ui.model.tree.Leaf;
import main.java.ui.model.tree.Node;
import main.java.ui.model.tree.Tree;
import main.java.ui.model.tree.TreeElement;

import java.util.ArrayList;
import java.util.List;

public class ID3 implements Runner {
    private Config config;
    private Model model;

    public ID3(Config config){
        this.config = config;
    }

    @Override
    public void fit(FeatureSet featureSet){
        Tree tree = new Tree(trainModel(featureSet, 0));
        featureSet.fallbackValue().ifPresent(tree::setFallback);
        model = TreeZip.zip(tree);
    }

    private TreeElement trainModel(FeatureSet featureSet, int depth) {
        if (config.depth() == depth){
            return new Leaf(featureSet,featureSet.mostFrequentValue());
        }
        return featureSet.maxGainFeature()
                .map(splitter -> buildNode(featureSet, splitter, depth))
                .orElse(new Leaf(featureSet,featureSet.leafLabelValue()));
    }

    private TreeElement buildNode(FeatureSet featureSet, Feature splitter, int depth) {
        List<TreeElement> children = new ArrayList<>();
        TreeElement node = new Node(children, splitter, featureSet);
        for (FeatureSet set : featureSet.split(splitter)) {
            children.add(trainModel(set, depth+1));
        }
        return node;
    }

    @Override
    public Analytics predict(List<SearchableTuple> searchables){
        ID3Analytics ID3Analytics = new ID3Analytics(model.depthLog(),model.labelValues());
        String labelKey = model.labelKey();
        for (SearchableTuple searchable : searchables) {
            String prediction = predict(searchable);
            String actual = searchable.get(labelKey);
            ID3Analytics.commit(prediction,actual);
        }
        return ID3Analytics;
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

    public String labelKey() {
        return model.labelKey();
    }
    public List<String> labels() {
        return model.labelValues();
    }
}
