package main.java.ui;

import main.java.ui.id3.ID3;
import main.java.ui.id3.model.Config;
import main.java.ui.id3.model.SearchableTuple;
import main.java.ui.id3.utils.Analytics;
import main.java.ui.model.FeatureSet;
import main.java.ui.utils.iterator.FileIterator;
import main.java.ui.utils.parser.Parser;

import java.util.List;


public class Test {

    public static void main(String[] args) {
        Parser parser = new Parser();
        FeatureSet trainSet = parser.parseFeatureSet(new FileIterator("/Users/juricakenda/desktop/lab3_files/datasets/volleyball.csv"));
        ID3 id3 = new ID3(new Config(1));
        id3.fit(trainSet);
        List<SearchableTuple> tuples = parser.parseTestSet(new FileIterator("/Users/juricakenda/desktop/lab3_files/datasets/volleyball_test.csv"));
        Analytics analytics = id3.predict(tuples);
        analytics.show();
    }

    private static SearchableTuple buildTuple() {
        SearchableTuple tuple = new SearchableTuple();
        tuple.bind("weather","sunny");
        tuple.bind("wind","weak");
        tuple.bind("humidity","high");
        return tuple;
    }
}
