package main.java.ui.model.feature;

import main.java.ui.utils.EntropyCalculator;

import java.util.HashMap;

public class Meta {
    private HashMap<String,Integer> with;
    private int partitionSize;

    public Meta(){
        with = new HashMap<>();
    }

    public void upsert(String label) {
        if (!with.containsKey(label)){
            with.put(label,1);
        }else {
            with.put(label,with.get(label)+1);
        }
        partitionSize++;
    }

    @Override
    public String toString(){
        return with.toString();
    }

    public Integer partitionSize() {
        return partitionSize;
    }

    public double getEntropy(){
        return EntropyCalculator.execute(with.values(), partitionSize);
    }

    public int countOrDefault(String value, int defaultVal) {
        if (!with.containsKey(value)){
            return defaultVal;
        }
        return with.get(value);
    }
}
