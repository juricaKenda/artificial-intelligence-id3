package ui.id3.utils;

import ui.Analytics;
import ui.model.ConfusionMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ID3Analytics implements Analytics {
    private List<String> predictions;
    private HashMap<Integer,List<String>> depthLog;
    private ConfusionMatrix confusionMatrix;

    public ID3Analytics(HashMap<Integer, List<String>> depthLog, List<String> labels) {
        predictions = new ArrayList<>();
        this.depthLog = depthLog;
        confusionMatrix = new ConfusionMatrix(labels);
    }

    public void commit(String prediction, String actual) {
        confusionMatrix.commit(prediction,actual);
        predictions.add(prediction);
    }

    @Override
    public void show(){
        showDepthLog();
        showPredictions();
        confusionMatrix.displayAccuracy();
        confusionMatrix.displayConfusion();
    }

    private void showPredictions() {
        predictions.forEach(p->System.out.print(p+" "));

    }

    private void showDepthLog() {
        StringBuilder builder = new StringBuilder();
        int depth = 0;
        while (depthLog.containsKey(depth)) {
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
        System.out.println(result.trim());
    }



}
