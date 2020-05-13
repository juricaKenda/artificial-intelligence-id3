package main.java.ui.model;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.*;

public class FeatureSet {
    private List<Feature> features;

    private int entryCount;

    public FeatureSet(String[] featureKeys) {
        entryCount = 0;
        features = new ArrayList<>();
        for (String key : featureKeys){
            features.add(new Feature(key));
        }
        features.get(features.size()-1).markLabel();
    }

    public void insert(String[] featureValues) {
        if (featureValues.length != features.size()){
            throw new UnsupportedOperationException("Invalid feature key-value count!");
        }

        entryCount++;
        for (int i=0; i<features.size(); i++){
            String value = featureValues[i];
            Feature feature = features.get(i);
            feature.insertValue(value);
        }
    }

    

    public void display() {
        System.out.println(format("Total entries : %d",entryCount));
        for (Feature feature : features){
            feature.display();
        }
    }
}
