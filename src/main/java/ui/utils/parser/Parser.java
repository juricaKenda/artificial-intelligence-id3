package main.java.ui.utils.parser;

import main.java.ui.model.FeatureSet;
import main.java.ui.utils.iterator.Iterator;

public class Parser {


    public FeatureSet parseFeatureSet(Iterator iterator){
        if (!iterator.hasNext()){
            throw new UnsupportedOperationException("Cannot parse an uniterable!");
        }
        FeatureSet featureSet = new FeatureSet(parseFeatureKeys(iterator.next()));
        while (iterator.hasNext()){
            featureSet.insert(parseFeatureValues(iterator.next()));
        }

        iterator.onTermination();
        return featureSet;
    }

    private String[] parseFeatureValues(String values) {
        return values.split(",");
    }

    private String[] parseFeatureKeys(String keys) {
        return keys.split(",");
    }
}
