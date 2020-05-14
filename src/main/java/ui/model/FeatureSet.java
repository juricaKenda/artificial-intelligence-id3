package main.java.ui.model;

import main.java.ui.model.feature.Feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    }

    private FeatureSet(List<Feature> features,Feature label, int entries){
        this.features = features;
        this.label = label;
        this.entryCount = entries;
    }

    public void insert(String[] featureValues) {
        if (featureValues.length != features.size()){
            throw new UnsupportedOperationException("Invalid feature key-value count!");
        }

        String label = featureValues[features.size() - 1];
        for (int i=0; i<features.size(); i++){
            String value = featureValues[i];
            Feature feature = features.get(i);
            feature.bindValueForLabel(value,label,entryCount);
        }
        entryCount++;
    }

    public List<FeatureSet> split(){
        Optional<Feature> featureOpt = maxGainFeature();
        if (featureOpt.isEmpty()) {
            return Collections.emptyList();
        }
        Feature feature = featureOpt.get();
        List<FeatureSet> sets = new ArrayList<>();
        List<List<Integer>> partitions = feature.valueIndexes();
        for (List<Integer> partition : partitions) {
            FeatureSet featureSet = subsetFrom(partition, feature);
            sets.add(featureSet);
        }
        return sets;
    }

    private FeatureSet subsetFrom(List<Integer> partition, Feature splitter) {
        List<Feature> partitionedFeatures = new ArrayList<>();
        for (Feature feature : features) {
            if (feature.equals(splitter) || feature.equals(label)){
                continue;
            }
            partitionedFeatures.add(feature.subsetFrom(partition));
        }
        return new FeatureSet(partitionedFeatures,label.subsetFrom(partition),partition.size());
    }

    private Optional<Feature> maxGainFeature(){
        double maxGain = 0;
        Feature maxFeature = null;
        for (Feature feature : features){
            if (feature.equals(label)){
                continue;
            }
            double gain = feature.getInformationGain(label.getValues());
            if(gain > maxGain){
                maxFeature = feature;
                maxGain = gain;
            }
        }
        if (maxGain == 0 && maxFeature == null){
            return Optional.empty();
        }
        return Optional.of(maxFeature);
    }
}
