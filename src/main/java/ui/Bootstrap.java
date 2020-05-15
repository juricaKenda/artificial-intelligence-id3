package main.java.ui;

import main.java.ui.id3.ID3;
import main.java.ui.id3.model.Config;
import main.java.ui.randomforest.RandomForest;
import main.java.ui.utils.parser.Parser;

import static java.lang.String.*;

public class Bootstrap {

    public static Runner getRunner(Config config) {
        switch (config.model()){
            case "ID3":
                return new ID3(config);
            case "RF":
                return new RandomForest(config);
            default:
                throw new UnsupportedOperationException(format("%s model is not supported!",config.model()));
        }
    }

    public static Parser getParser() {
        return new Parser();
    }
}
