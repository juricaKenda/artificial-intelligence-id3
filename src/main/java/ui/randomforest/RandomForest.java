package ui.randomforest;

import ui.Analytics;
import ui.Runner;
import ui.id3.ID3;
import ui.id3.model.Config;
import ui.id3.model.SearchableTuple;
import ui.model.FeatureSet;
import ui.randomforest.utils.RFAnalytics;
import ui.randomforest.utils.RFRand;

import java.util.ArrayList;
import java.util.List;

public class RandomForest implements Runner {
    private Config config;
    private List<ID3> models;
    private List<List<Integer>> partitions;
    private List<List<String>> features;
    private List<String> labels;
    private String labelKey;

    public RandomForest(Config config) {
       this.config = config;
       models = new ArrayList<>();
       partitions = new ArrayList<>();
       features = new ArrayList<>();
    }

    @Override
    public void fit(FeatureSet featureSet) {
        List<FeatureSet> treeSets = generateForest(featureSet);
        for (FeatureSet treeSet : treeSets) {
            ID3 id3 = new ID3(config);
            id3.fit(treeSet);
            models.add(id3);
        }
        labels = featureSet.labelValues();
        labelKey = featureSet.label();
    }

    private List<FeatureSet> generateForest(FeatureSet totalSet) {
        List<FeatureSet> treeSets = new ArrayList<>();
        while (treeSets.size() < config.numTrees()){
            FeatureSet featureSubset = selectFeatureSubset(selectDataSubset(totalSet));
            treeSets.add(featureSubset);
        }
        return treeSets;
    }

    private FeatureSet selectDataSubset(FeatureSet featureSet) {
        List<Integer> partition = RFRand.dataInstances(featureSet.datasetSize(), config.exampleRatio());
        partitions.add(partition);
        return featureSet.split(partition);
    }

    private FeatureSet selectFeatureSubset(FeatureSet featureSet) {
        List<Integer> selected = RFRand.featureInstances(featureSet.featureCount(), config.featureRatio());
        featureSet.siftOnly(selected);
        features.add(featureSet.features());
        return featureSet;
    }


    @Override
    public Analytics predict(List<SearchableTuple> searchables) {
        RFAnalytics analytics = new RFAnalytics(partitions,features,labels);
        for (SearchableTuple searchable : searchables) {
            for (ID3 model : models) {
                String prediction = model.predict(searchable);
                analytics.commit(prediction);
            }
            analytics.conclude(searchable.get(labelKey));
        }
        return analytics;
    }
}
