package main.java.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;

public class ConfusionMatrix {
    private HashMap<String,Row> rowsByLabel;
    private int total;
    private int correct;

    public ConfusionMatrix(List<String> labels){
        rowsByLabel = new HashMap<>();
        for (String label : labels) {
            rowsByLabel.put(label,new Row(labels));
        }
    }

    public void displayAccuracy() {
        System.out.println(format("\n%2.5f",1.0*correct/total));
    }

    public void displayConfusion() {
        List<String> labels = new ArrayList<>(rowsByLabel.keySet());
        Collections.sort(labels);
        for (String label : labels) {
            Row row = rowsByLabel.get(label);
            ArrayList<String> results = new ArrayList<>(row.countByPrediction.keySet());
            Collections.sort(results);
            for (String result : results) {
                Integer value = row.countByPrediction.get(result);
                System.out.print(value+" ");
            }
            System.out.println();
        }
    }

    public void commit(String prediction, String actual) {
        Row row = rowsByLabel.get(actual);
        row.submit(prediction);
        total++;
        if (prediction.equals(actual)){
            correct++;
        }
    }

    static class Row{
        private HashMap<String,Integer> countByPrediction;

        public Row(List<String> labels) {
            countByPrediction = new HashMap<>();
            for (String label : labels) {
                countByPrediction.put(label,0);
            }
        }
        public void submit(String prediction) {
            countByPrediction.put(prediction,countByPrediction.get(prediction)+1);
        }
    }
}
