package ui.utils;

import java.util.Collection;

public class EntropyCalculator {

    public static double execute(Collection<Integer> amounts, int total){
        double entropy = 0;
        for (int amount : amounts){
            double probability = 1.0*amount / total;
            entropy -= probability * log2(probability);
        }
        return entropy;
    }

    private static double log2(double n) {
        return Math.log(n)/Math.log(2);
    }

}
