package main.java.ui.utils;

import main.java.ui.model.FeatureSet;

public class Fallback {
    private static String fallback = null;


    public static void set(String val){
        fallback = val;
    }

    public static void set(FeatureSet featureSet){
        if (fallback == null){
            fallback = featureSet.mostFrequentValue();
        }
    }

    public static String get(){
        return fallback;
    }
}
