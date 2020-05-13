package main.java.ui.model.feature;

import main.java.ui.utils.EntropyCalculator;

import java.util.*;

import static java.lang.String.format;

public class Feature {
    private String key;
    private List<String> entries;
    private HashMap<String,Meta> valueMeta;
    private boolean label = false;

    public Feature(String featureKey){
        key = featureKey;
        entries = new ArrayList<>();
        valueMeta = new HashMap<>();
    }

    public void markLabel(){
        label = true;
    }

    public void bindValueForLabel(String value, String label) {
        entries.add(value);
        upsertMeta(value,label);
    }

    private void upsertMeta(String value, String label) {
        if (!valueMeta.containsKey(value)){
            valueMeta.put(value,new Meta());
        }
        valueMeta.get(value).upsert(label);
    }


    public double getInformationGain(Set<String> values){
        double entropy = EntropyCalculator.execute(getValuePartitions(values),entries.size());
        for (Meta meta : valueMeta.values()){
            entropy -= meta.getEntropy()*meta.partitionSize()/entries.size();
        }
        return entropy;
    }

    private Collection<Integer> getValuePartitions(Set<String> values) {
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
        return format("%s [%s] : %s",key,label, valueMeta);
    }

    public Set<String> getValues() {
        return valueMeta.keySet();
    }

    public String getEntry(int index) {
        return entries.get(index);
    }
}
