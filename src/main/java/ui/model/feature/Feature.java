package main.java.ui.model.feature;

import main.java.ui.utils.EntropyCalculator;

import java.util.*;

public class Feature {
    private String key;
    private HashMap<Integer,String> entriesByIndex;
    private HashMap<String,Meta> valueMeta;

    public Feature(String featureKey){
        key = featureKey;
        entriesByIndex = new HashMap<>();
        valueMeta = new HashMap<>();
    }

    private Feature(String featureKey, HashMap<Integer, String> entriesByIndex, HashMap<String, Meta> valueMeta){
        this.key = featureKey;
        this.entriesByIndex = entriesByIndex;
        this.valueMeta = valueMeta;
    }


    public void bindValueForLabel(String value, String label,int index) {
        entriesByIndex.put(index,value);
        upsertMeta(value,label,index);
    }

    private void upsertMeta(String value, String label,int index) {
        if (!valueMeta.containsKey(value)){
            valueMeta.put(value,new Meta());
        }
        valueMeta.get(value).upsert(label,index);
    }


    public double getInformationGain(Collection<String> values){
        double entropy = EntropyCalculator.execute(getValuePartitions(values), entriesByIndex.size());
        for (Meta meta : valueMeta.values()){
            entropy -= meta.getEntropy()*meta.partitionSize()/ entriesByIndex.size();
        }
        return entropy;
    }

    private Collection<Integer> getValuePartitions(Collection<String> values) {
        List<Integer> totals = new ArrayList<>();
        for (String value : values){
            int sum = 0;
            for (Meta meta : valueMeta.values()){
                sum += meta.countOrDefault(value,0);
            }
            totals.add(sum);
        }
        return totals;
    }

    @Override
    public String toString() {
        return key;
    }

    public Collection<String> getValues() {
        return entriesByIndex.values();
    }

    public List<List<Integer>> valueIndexes() {
        List<List<Integer>> indexes = new ArrayList<>();
        for (Meta meta : valueMeta.values()){
            indexes.add(meta.partitionIndexes());
        }
        return indexes;
    }

    public Feature subsetFrom(List<Integer> partition) {
        HashMap<Integer,String> partitionedEntries = partitionEntries(partition);
        HashMap<String, Meta> partitionedMeta = partitionMeta(partition);
        return new Feature(key,partitionedEntries,partitionedMeta);
    }

    private HashMap<String, Meta> partitionMeta(List<Integer> partition) {
        HashMap<String, Meta> partitioned = new HashMap<>();
        for (String key : valueMeta.keySet()) {
            Meta meta = valueMeta.get(key);
            if (meta.partitionContainsAny(partition)){
                if (!partitioned.containsKey(key)){
                    partitioned.put(key,new Meta());
                }
                for (Integer index : partition) {
                    meta.keyForEntry(index).ifPresent(entry-> upsertMeta(partitioned, key, index, entry));
                }

            }
        }
        return partitioned;
    }

    private void upsertMeta(HashMap<String, Meta> partitioned, String key, Integer index, String entry) {
        partitioned.get(key).upsert(entry, index);
    }

    private HashMap<Integer, String> partitionEntries(List<Integer> partition) {
        HashMap<Integer,String> partitioned = new HashMap<>();
        for (Integer index : partition) {
            partitioned.put(index, entriesByIndex.get(index));
        }
        return partitioned;
    }
    
    public HashMap<Integer, String> entriesByIndex() {
        return entriesByIndex;
    }
    public String entryByIndex(int index) {
        return entriesByIndex.get(index);
    }

}
