package main.java.ui.id3.utils;

import main.java.ui.Analytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ID3Analytics implements Analytics {
    private int labelSize;
    private int totalChecks;
    private double correct;
    private List<String> pairs;
    private List<String> predictions;
    private HashMap<Integer,List<String>> depthLog;

    public ID3Analytics(int labelSize, HashMap<Integer, List<String>> depthLog) {
        this.labelSize = labelSize;
        pairs = new ArrayList<>();
        predictions = new ArrayList<>();
        this.depthLog = depthLog;
    }

    public void commit(String prediction, String actual) {
        if (prediction.equals(actual)){
            correct++;
        }
        pairs.add(actual+prediction);
        predictions.add(prediction);
        totalChecks++;
    }

    @Override
    public void show(){
        showDepthLog();
        showPredictions();
        showAccuracy();
        showConfusionMatrix();
    }

    private void showPredictions() {
        predictions.forEach(p->System.out.print(p+" "));

    }

    private void showDepthLog() {
        StringBuilder builder = new StringBuilder();
        int depth = 0;
        while (true){
            if (!depthLog.containsKey(depth)){
                break;
            }
            List<String> splitters = depthLog.get(depth);

            for (String splitter : splitters) {
                builder.append(depth).append(":").append(splitter).append(", ");
            }
            depth++;
        }
        String result = builder.toString();
        int lastindex = result.lastIndexOf(", ");
        if (lastindex != -1){
            result = result.substring(0,lastindex);
        }
        System.out.println(result);
    }

    private void showConfusionMatrix() {
        List<Integer> counts = buildConfusionList();
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (Integer count : counts) {
            if (i%labelSize == 0 && i != 0){
                builder.append("\n");
            }
            builder.append(count).append(" ");
            i++;
        }
        System.out.println(builder.toString());
    }

    private List<Integer> buildConfusionList() {
        Collections.sort(pairs);
        String prev = null;
        int count = 0;
        List<Integer> counts = new ArrayList<>();
        for (String pair : pairs) {
            if (!pair.equals(prev)){
                if (prev != null){
                    counts.add(count);
                }
                count = 0;
                prev = pair;
            }
            count++;
        }
        counts.add(count);
        return counts;
    }

    private void showAccuracy() {
        System.out.println(String.format("\n%2.5f",correct / totalChecks));
    }


}
