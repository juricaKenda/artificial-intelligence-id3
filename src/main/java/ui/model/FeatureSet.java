package main.java.ui.model;

import main.java.ui.model.feature.Feature;

import java.util.ArrayList;
import java.util.List;

public class FeatureSet {
    private List<Feature> features;
    private Feature label;
    private int entryCount;

    public FeatureSet(String[] featureKeys) {
        entryCount = 0;
        features = new ArrayList<>();
        for (String key : featureKeys){
            features.add(new Feature(key));
        }
        label = features.get(features.size() - 1);
        label.markLabel();
    }

    public void insert(String[] featureValues) {
        if (featureValues.length != features.size()){
            throw new UnsupportedOperationException("Invalid feature key-value count!");
        }

        entryCount++;
        String label = featureValues[featureValues.length-1];
        for (int i=0; i<features.size(); i++){
            String value = featureValues[i];
            Feature feature = features.get(i);
            feature.bindValueForLabel(value,label);
        }
    }

    public void display() {
        features.forEach(System.out::println);
        features.forEach(feature -> System.out.println(feature.getInformationGain(label.getValues())));
    }

    public List<String> recreateEntries(){
        List<String> entries = new ArrayList<>();
        for (int i=0; i<entryCount; i++){
            StringBuilder entry = new StringBuilder();
            for (Feature feature : features){
                entry.append(feature.getEntry(i)).append(" ");
            }
            entries.add(entry.toString());

        }
        return entries;
    }
}
