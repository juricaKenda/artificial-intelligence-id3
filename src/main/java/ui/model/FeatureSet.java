package ui.model;

import ui.model.feature.Feature;
import ui.utils.Occurrences;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

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
        return splitter.valueIndexes().stream()
                .map(partition->subsetFrom(partition,splitter))
                .collect(toList());
    }

    public FeatureSet split(List<Integer> partition) {
        List<Feature> partitionedFeatures = features.stream()
                .map(feature -> feature.subsetFrom(partition))
                .collect(toList());
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
                .collect(toList());
    }


    private FeatureSet subsetFrom(List<Integer> partition, Feature splitter) {
        List<Feature> partitionedFeatures = features.stream()
                .filter(feature -> !feature.equals(splitter))
                .map(feature->feature.subsetFrom(partition))
                .collect(toList());

        Integer anyIncludedIndex = partition.get(0);
        String attr = splitter.entryByIndex(anyIncludedIndex);
        return new FeatureSet(partitionedFeatures, label.subsetFrom(partition), partition.size(), attr);
    }

    public Optional<Feature> maxGainFeature(){
        return features.stream()
                .filter(this::isNotLabel)
                .max(Feature.byGain(label.getValuesDistinct()));
    }

    private boolean isNotLabel(Feature feature) {
        return !feature.key().equals(label.key());
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
        return features.stream()
                .filter(this::isNotLabel)
                .map(Feature::key)
                .collect(toList());
    }

    public List<String> labelValues() {
        return label.getValues().stream().distinct().collect(toList());
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
