package main.java.ui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.*;

public class Feature {
    private String key;
    private List<String> entries;
    private HashMap<String,Integer> valueAmount;
    private boolean label = false;

    public Feature(String featureKey){
        key = featureKey;
        entries = new ArrayList<>();
        valueAmount = new HashMap<>();
    }

    public String getEntry(int index){
        return entries.get(index);
    }

    public void markLabel(){
        label = true;
    }

    public void insertValue(String value) {
        entries.add(value);
        upsertValue(value);
    }

    private void upsertValue(String value) {
        if (!valueAmount.containsKey(value)){
            valueAmount.put(value,1);
        }else {
            valueAmount.put(value, valueAmount.get(value)+1);
        }
    }

    public void display() {
        System.out.println(format("%s [%s] : %s",key,label, valueAmount));
    }
}
