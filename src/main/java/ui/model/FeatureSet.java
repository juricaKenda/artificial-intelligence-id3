package ui.model;

import ui.model.feature.Feature;
import ui.utils.Occurrences;

import java.util.*;
import java.util.stream.Collectors;

public class FeatureSet {
    private List<Feature> features;
    private Feature label;
    private int entryCount;
    private String proxyAttribute;

    public FeatureSet(String[] featureKeys) {
        features = new ArrayList<>();
        for (String key : featureKeys){
            features.add(new Feature(key));
        }
        label = features.get(features.size() - 1);
        proxyAttribute = ".";
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

    public FeatureSet split(List<Integer> partition) {
        List<Feature> partitionedFeatures = new ArrayList<>();
        for (Feature feature : features) {
            partitionedFeatures.add(feature.subsetFrom(partition));
        }
        return new FeatureSet(partitionedFeatures,label.subsetFrom(partition),partition.size(),proxyAttribute);
    }

    public void siftOnly(List<Integer> selected){
        List<String> wanted = new ArrayList<>();
        wanted.add(features.get(featureCount()-1).key());
        for (Integer index : selected) {
            wanted.add(features.get(index).key());
        }
        features = features.stream()
                .filter(feature -> wanted.contains(feature.key()))
                .collect(Collectors.toList());
    }


    private FeatureSet subsetFrom(List<Integer> partition, Feature splitter) {
        List<Feature> partitionedFeatures = new ArrayList<>();
        for (Feature feature : features) {
            if (feature.equals(splitter)){
                continue;
            }
            partitionedFeatures.add(feature.subsetFrom(partition));
        }
        Integer index = partition.get(0);
        String attr = splitter.entryByIndex(index);
        return new FeatureSet(partitionedFeatures, label.subsetFrom(partition), partition.size(), attr);
    }

    public Optional<Feature> maxGainFeature(){
        return features.stream()
                .filter(feature -> !isLabel(feature))
                .max((f1, f2) -> {
                    Collection<String> labelValues = label.getValuesDistinct();
                    int compare = Double.compare(f1.getInformationGain(labelValues)
                            , f2.getInformationGain(labelValues));
                    if (compare == 0) {
                        return f2.key().compareTo(f1.key());
                    }
                    return compare;
                });
    }

    private boolean isLabel(Feature feature) {
        return feature.key().equals(label.key());
    }

    public String proxyAttribute() {
        return proxyAttribute;
    }

    public String mostFrequentValue(){
        return Occurrences.maxByCount(label.getValues(), Occurrences.Tiebreak.byName());
    }

    public String label() {
        return label.key();
    }

    public int datasetSize() {
        return entryCount;
    }

    public int featureCount() {
        return features.size();
    }

    public List<String> features() {
        List<String> feat = new ArrayList<>();
        for (Feature feature : features) {
            if (isLabel(feature)){
                continue;
            }
            feat.add(feature.key());
        }
        return feat;
    }

    public List<String> labelValues() {
        return label.getValues().stream().distinct().collect(Collectors.toList());
    }

    public String leafLabelValue() {
        if (labelValues().size() == 1 ){
            return labelValues().get(0);
        }
        throw new UnsupportedOperationException("multiple leaf values present");
    }

    public boolean isPure() {
        return labelValues().size() == 1;
    }
}
