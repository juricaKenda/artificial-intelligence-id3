package ui.id3;

import ui.Analytics;
import ui.Runner;
import ui.id3.model.Config;
import ui.id3.model.Model;
import ui.id3.model.SearchableTuple;
import ui.id3.utils.ID3Analytics;
import ui.model.FeatureSet;
import ui.model.feature.Feature;
import ui.model.tree.Leaf;
import ui.model.tree.Node;
import ui.model.tree.Tree;
import ui.model.tree.TreeElement;

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
        model = new Model(tree);
    }

    private TreeElement trainModel(FeatureSet featureSet, int depth) {
        if (config.depth() == depth){
            return forceBuildLeaf(featureSet);
        }
        if (featureSet.isPure()){
            return new Leaf(featureSet,featureSet.leafLabelValue());
        }
        return featureSet.maxGainFeature()
                .map(feature -> buildNode(featureSet, feature, depth))
                .orElseGet(()-> new Leaf(featureSet,featureSet.mostFrequentValue()));
    }

    private TreeElement buildNode(FeatureSet featureSet, Feature splitter, int depth) {
        Node node = new Node(splitter, featureSet);
        for (FeatureSet set : featureSet.split(splitter)) {
            node.addChild(trainModel(set, depth+1));
        }
        return node;
    }

    private TreeElement forceBuildLeaf(FeatureSet featureSet) {
        if (featureSet.isPure()){
            return new Leaf(featureSet,featureSet.leafLabelValue());
        }
        return new Leaf(featureSet, featureSet.mostFrequentValue());
    }

    @Override
    public Analytics predict(List<SearchableTuple> searchables){
        ID3Analytics analytics = new ID3Analytics(model.depthLog(),model.labelValues());
        String labelKey = model.labelKey();
        for (SearchableTuple searchable : searchables) {
            String prediction = predict(searchable);
            String actual = searchable.get(labelKey);
            analytics.commit(prediction,actual);
        }
        return analytics;
    }

    public String predict(SearchableTuple searchable){
        model.load(searchable);
        return model.result();
    }
}
