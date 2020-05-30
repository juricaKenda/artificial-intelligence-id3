package ui;

import ui.id3.ID3;
import ui.id3.model.Config;
import ui.randomforest.RandomForest;
import ui.utils.parser.Parser;

import static java.lang.String.format;

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
