package main.java.ui.randomforest.utils;

import main.java.ui.Analytics;
import main.java.ui.model.ConfusionMatrix;
import main.java.ui.utils.Occurrences;

import java.util.ArrayList;
import java.util.List;

public class RFAnalytics implements Analytics {
    private List<TreeMeta> treeMetas;
    private List<Analytic> analytics;
    private int current;
    private List<String> labels;

    public RFAnalytics(List<List<Integer>> partitions, List<List<String>> features, List<String> labels){
        analytics = new ArrayList<>();
        treeMetas = mergeTreeMetas(partitions,features);
        this.labels = labels;
    }

    private List<TreeMeta> mergeTreeMetas(List<List<Integer>> partitions, List<List<String>> features) {
        if (partitions.size() != features.size()){
            throw new UnsupportedOperationException("Invalid tree metadata!");
        }
        List<TreeMeta> metas = new ArrayList<>();
        for (int i=0; i<partitions.size(); i++){
            metas.add(new TreeMeta(partitions.get(i), features.get(i)));
        }
        return metas;
    }

    @Override
    public void show() {
        treeMetas.forEach(System.out::println);
        analytics.forEach(Analytic::decide);
        analytics.forEach(a-> System.out.print(a.majority+" "));
        showAccuracy();
    }

    public void commit(String prediction) {
        if (analytics.size() <= current){
            analytics.add(new Analytic());
        }
        analytics.get(current).predictions.add(prediction);
    }

    public void conclude(String actual) {
        analytics.get(current).actual = actual;
        current++;
    }


    private void showAccuracy() {
        ConfusionMatrix matrix = new ConfusionMatrix(labels);
        for (Analytic analytic : analytics) {
            String prediction = analytic.majority;
            String actual = analytic.actual;
            matrix.commit(prediction,actual);
        }
        matrix.displayAccuracy();
        matrix.displayConfusion();
    }



    static class Analytic {
        private List<String> predictions;
        private String majority;
        private String actual;

        Analytic(){
            predictions = new ArrayList<>();
        }
        void decide(){
            majority = Occurrences.maxByCount(predictions, Occurrences.Tiebreak.byName());
        }
    }
    static class TreeMeta{
        private List<Integer> partitions;
        private List<String> features;

        TreeMeta(List<Integer> partitions, List<String> features) {
            this.partitions = partitions;
            this.features = features;
        }

        @Override
        public String toString(){
            StringBuilder builder = new StringBuilder();
            features.forEach(feat->builder.append(feat).append(" "));
            builder.append("\n");
            partitions.forEach(part->builder.append(part).append(" "));
            return builder.toString();
        }
    }
}
