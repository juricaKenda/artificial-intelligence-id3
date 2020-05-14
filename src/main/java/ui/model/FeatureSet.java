package main.java.ui.model;

import main.java.ui.model.feature.Feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FeatureSet {
    private List<Feature> features;
    private Feature label;
    private int entryCount;
    private String proxyAttribute = ".";

    public FeatureSet(String[] featureKeys) {
        features = new ArrayList<>();
        for (String key : featureKeys){
            features.add(new Feature(key));
        }
        label = features.get(features.size() - 1);
    }

    private FeatureSet(List<Feature> features, Feature label, int entries, String attribute){
        this.features = features;
        this.label = label;
        this.entryCount = entries;
        this.proxyAttribute = attribute;
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

    public List<FeatureSet> split(Feature splitter){
        List<FeatureSet> sets = new ArrayList<>();
        List<List<Integer>> partitions = splitter.valueIndexes();
        for (List<Integer> partition : partitions) {
            FeatureSet featureSet = subsetFrom(partition, splitter);
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
        Integer index = partition.get(0);
        String attr = splitter.entryByIndex(index);
        return new FeatureSet(partitionedFeatures,label.subsetFrom(partition),partition.size(),attr);
    }

    public Optional<Feature> maxGainFeature(){
        double maxGain = Double.MIN_VALUE;
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
        if (maxGain == Double.MIN_VALUE){
            return Optional.empty();
        }
        return Optional.of(maxFeature);
    }

    public String labelValue() {
        return label.entriesByIndex().values().stream().findAny().orElseThrow();
    }


    public String proxyAttribute() {
        return proxyAttribute;
    }
}
