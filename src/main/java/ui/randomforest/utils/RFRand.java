package ui.randomforest.utils;

import java.util.*;

public class RFRand {

    public static List<Integer> dataInstances(int datasetSize, double exampleRatio) {
        int instanceCount = (int)(exampleRatio*datasetSize);
        Random random = new Random();
        List<Integer> instances = new ArrayList<>();
        while (instances.size() < instanceCount){
            int index = random.nextInt(datasetSize);
            if (!instances.contains(index)){
                instances.add(index);
            }
        }
        return instances;
    }

    public static List<Integer> featureInstances(int featureCount, double featureRatio) {
        int instanceCount = (int)(featureCount*featureRatio);
        Random random = new Random();
        List<Integer> instances = new ArrayList<>();
        while (instances.size() < instanceCount){
            int index = random.nextInt(featureCount);
            if (index != featureCount-1 && !instances.contains(index)){
                instances.add(index);
            }
        }
        return instances;
    }

}
