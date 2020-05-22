package main.java.ui.randomforest;

import main.java.ui.Analytics;
import main.java.ui.Runner;
import main.java.ui.id3.ID3;
import main.java.ui.id3.model.Config;
import main.java.ui.id3.model.SearchableTuple;
import main.java.ui.model.FeatureSet;
import main.java.ui.randomforest.utils.RFAnalytics;
import main.java.ui.randomforest.utils.RFRand;

import java.util.ArrayList;
import java.util.List;

public class RandomForest implements Runner {
    private Config config;
    private List<ID3> models;
    private List<List<Integer>> partitions;
    private List<List<String>> features;

    public RandomForest(Config config) {
       this.config = config;
       models = new ArrayList<>();
       partitions = new ArrayList<>();
       features = new ArrayList<>();
    }

    @Override
    public void fit(FeatureSet featureSet) {
        String globalFallback = featureSet.mostFrequentValue();
        List<FeatureSet> treeSets = generateForest(featureSet);
        for (FeatureSet treeSet : treeSets) {
            ID3 id3 = new ID3(config);
            id3.fit(treeSet);
            models.add(id3);
        }
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
        RFAnalytics analytics = new RFAnalytics(partitions,features,models.get(0).labels());
        for (SearchableTuple searchable : searchables) {
            for (ID3 model : models) {
                String prediction = model.predict(searchable);
                analytics.commit(prediction);
            }
            analytics.conclude(searchable.get(models.get(0).labelKey()));
        }
        return analytics;
    }
}
