package ui.utils.parser;

import ui.id3.model.Config;
import ui.id3.model.SearchableTuple;
import ui.model.FeatureSet;
import ui.utils.iterator.Iterator;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public FeatureSet parseFeatureSet(Iterator iterator){
        if (!iterator.hasNext()){
            throw new UnsupportedOperationException("Can not parse an uniterable!");
        }
        FeatureSet featureSet = new FeatureSet(parseFeatureKeys(iterator.next()));
        while (iterator.hasNext()){
            featureSet.insert(parseFeatureValues(iterator.next()));
        }

        iterator.onTermination();
        return featureSet;
    }

    public List<SearchableTuple> parseTestSet(Iterator iterator){
        if (!iterator.hasNext()){
            throw new UnsupportedOperationException("Can not parse an uniterable!");
        }
        SearchableTuple keys = parseKeys(iterator.next());
        List<SearchableTuple> tuples = new ArrayList<>();
        while (iterator.hasNext()){
            SearchableTuple tuple = new SearchableTuple();
            String line = iterator.next();
            String[] values = parseFeatureValues(line);
            for (int i=0; i<values.length; i++){
                String key = keys.get(Integer.toString(i));
                tuple.bind(key,values[i]);
            }
            tuples.add(tuple);
        }

        iterator.onTermination();
        return tuples;
    }

    private SearchableTuple parseKeys(String line) {
        SearchableTuple tuple = new SearchableTuple();
        String[] values = parseFeatureValues(line);
        for (int i=0; i<values.length; i++){
            String key = Integer.toString(i);
            tuple.bind(key,values[i]);
        }
        return tuple;
    }

    public Config parseConfig(Iterator iterator) {
        Config.Builder builder = new Config.Builder();
        while (iterator.hasNext()){
            String[] keyVal = parseConfigPair(iterator.next());
            builder.set(keyVal[0],keyVal[1]);
        }
        iterator.onTermination();
        return builder.build();
    }

    private String[] parseFeatureKeys(String keys) {
        return keys.split(",");
    }

    private String[] parseFeatureValues(String values) {
        return values.split(",");
    }

    private String[] parseConfigPair(String line) {
        return line.split("=");
    }
}
