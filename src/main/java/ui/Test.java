package main.java.ui;

import main.java.ui.id3.model.Config;
import main.java.ui.id3.model.SearchableTuple;
import main.java.ui.model.FeatureSet;
import main.java.ui.utils.iterator.FileIterator;
import main.java.ui.utils.parser.Parser;

import java.util.List;


public class Test {

    public static void main(String[] args) {
        Parser parser = Bootstrap.getParser();
        FeatureSet trainSet = parser.parseFeatureSet(new FileIterator("/Users/juricakenda/desktop/lab3_files/datasets/volleyball.csv"));
        Config config = parser.parseConfig(new FileIterator("/Users/juricakenda/desktop/lab3_files/config/id3.cfg"));
        List<SearchableTuple> tuples = parser.parseTestSet(new FileIterator("/Users/juricakenda/desktop/lab3_files/datasets/volleyball_test.csv"));

        Runner runner = Bootstrap.getRunner(config);
        runner.fit(trainSet);
        Analytics analytics = runner.predict(tuples);
        analytics.show();
    }
}
