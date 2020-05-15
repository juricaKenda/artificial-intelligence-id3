package main.java.ui.randomforest;

import main.java.ui.Analytics;
import main.java.ui.Runner;
import main.java.ui.id3.ID3;
import main.java.ui.id3.model.Config;
import main.java.ui.id3.model.SearchableTuple;
import main.java.ui.model.FeatureSet;
import main.java.ui.randomforest.utils.RFRand;

import java.util.ArrayList;
import java.util.List;

public class RandomForest implements Runner {
    private Config config;
    private List<ID3> models;

    public RandomForest(Config config) {
       this.config = config;
       models = new ArrayList<>();
    }

    @Override
    public void fit(FeatureSet featureSet) {
        List<FeatureSet> treeSets = generateForest(featureSet);
        for (FeatureSet treeSet : treeSets) {
            ID3 id3 = new ID3(config);
            id3.fit(treeSet);
            models.add(id3);
        }
    }

    private List<FeatureSet> generateForest(FeatureSet featureSet) {
        List<FeatureSet> treeSets = new ArrayList<>();
        while (treeSets.size() < config.numTrees()){
            FeatureSet dataSubset = selectDataSubset(featureSet);
            FeatureSet featureSubset = selectFeatureSubset(dataSubset);
            treeSets.add(featureSubset);
        }
        return treeSets;
    }

    private FeatureSet selectDataSubset(FeatureSet featureSet) {
        int datasetSize = featureSet.datasetSize();
        double exampleRatio = config.exampleRatio();
        return featureSet.split(RFRand.dataInstances(datasetSize, exampleRatio));
    }

    private FeatureSet selectFeatureSubset(FeatureSet featureSet) {
        int featureCount = featureSet.featureCount();
        double featureRatio = config.featureRatio();
        List<Integer> selected = RFRand.featureInstances(featureCount, featureRatio);
        featureSet.sift(selected);
        return featureSet;
    }


    @Override
    public Analytics predict(List<SearchableTuple> searchables) {
        for (SearchableTuple searchable : searchables) {
            for (ID3 model : models) {
                String predict = model.predict(searchable);
                System.out.print(predict+" ");
            }
            System.out.println();
        }
        return () -> {
        };
    }
}
