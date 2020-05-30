package ui.model.feature;


import ui.utils.EntropyCalculator;

import java.util.*;

public class Meta {
    private HashMap<String,List<Integer>> with;
    private List<Integer> partitionCache;

    public Meta(){
        with = new HashMap<>();
        partitionCache = new ArrayList<>();
    }

    public void upsert(String label,int index) {
        if (!with.containsKey(label)){
            with.put(label,new ArrayList<>());
        }
        with.get(label).add(index);
        partitionCache.add(index);
    }


    public Integer partitionSize() {
        return partitionCache.size();
    }

    public double getEntropy(){
        return EntropyCalculator.execute(flatToSize(with.values()), partitionSize());
    }

    private Collection<Integer> flatToSize(Collection<List<Integer>> values) {
        Collection<Integer> sizes = new ArrayList<>();
        for (List<Integer> value : values) {
            sizes.add(value.size());
        }
        return sizes;
    }

    public int countOrDefault(String value, int defaultVal) {
        if (!with.containsKey(value)){
            return defaultVal;
        }
        return with.get(value).size();
    }

    public List<Integer> partitionIndexes() {
        return partitionCache;
    }

    public boolean partitionContainsAny(List<Integer> partition) {
        for (Integer index : partition) {
            if (partitionCache.contains(index)){
                return true;
            }
        }
        return false;
    }

    public Optional<String> keyForEntry(int index){
        for (String key : with.keySet()) {
            List<Integer> indexes = with.get(key);
            if (indexes.contains(index)){
                return Optional.ofNullable(key);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString(){
        return with.toString();
    }
}
