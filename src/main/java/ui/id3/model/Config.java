package main.java.ui.id3.model;

import static java.lang.String.format;

public class Config {
    private String mode;
    private String model;
    private int depth;
    private int numTrees;
    private double featureRatio;
    private double exampleRatio;

    private Config(String mode, String model, int depth, int numTrees, double featureRatio, double exampleRatio) {
        this.mode = mode;
        this.model = model;
        this.depth = depth;
        this.numTrees = numTrees;
        this.featureRatio = featureRatio;
        this.exampleRatio = exampleRatio;
    }

    public String mode() {
        return mode;
    }

    public String model() {
        return model;
    }

    public int depth() {
        return depth;
    }

    public int numTrees() {
        return numTrees;
    }

    public double featureRatio() {
        return featureRatio;
    }

    public double exampleRatio() {
        return exampleRatio;
    }

    public static class Builder{
        private String mode;
        private String model;
        private int depth;
        private int numTrees;
        private double featureRatio;
        private double exampleRatio;

        public void set(String key, String value) {
            switch (key){
                case "mode":
                    mode = value;
                    break;
                case "model":
                    model = value;
                    break;
                case "max_depth":
                    depth = Integer.parseInt(value);
                    break;
                case "num_trees":
                    numTrees = Integer.parseInt(value);
                    break;
                case "feature_ratio":
                    featureRatio = Double.parseDouble(value);
                    break;
                case "example_ratio":
                    exampleRatio = Double.parseDouble(value);
                    break;
                default:
                    throw new UnsupportedOperationException(format("Unexpected config value : %s",value));
            }
        }

        public Config build() {
            return new Config(mode,model,depth,numTrees,featureRatio,exampleRatio);
        }
    }
}
