package ui;

import ui.id3.model.Config;
import ui.id3.model.SearchableTuple;
import ui.model.FeatureSet;
import ui.utils.parser.Parser;

import java.util.List;


public class Solution {

    public static void main(String[] args) {
        Parser parser = Bootstrap.getParser();
        String trainPath = args[0];
        String testPath = args[1];
        String configPath = args[2];
        FeatureSet trainSet = parser.parseFeatureSet(new ui.utils.iterator.FileIterator(trainPath));
        Config config = parser.parseConfig(new ui.utils.iterator.FileIterator(configPath));
        List<SearchableTuple> tuples = parser.parseTestSet(new ui.utils.iterator.FileIterator(testPath));

        Runner runner = Bootstrap.getRunner(config);
        runner.fit(trainSet);
        Analytics analytics = runner.predict(tuples);
        analytics.show();
    }

}
