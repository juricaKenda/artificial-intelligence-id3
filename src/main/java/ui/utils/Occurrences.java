package main.java.ui.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Occurrences {
    
    public static String maxByCount(Collection<String> values,Comparator<Map.Entry<String, Long>> tiebreak){
        return values.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(tiebreak)
                .map(Map.Entry::getKey)
                .orElseThrow();
    }

    public static class Tiebreak{
        public static Comparator<Map.Entry<String, Long>> byName() {
            return (e1,e2)->{
                if (equalCount(e1, e2)){
                    return compareByName(e1, e2);
                }
                return compareByCount(e1, e2);
            };
        }

        private static int compareByCount(Map.Entry<String, Long> e1, Map.Entry<String, Long> e2) {
            return e1.getValue().compareTo(e2.getValue());
        }

        private static boolean equalCount(Map.Entry<String, Long> e1, Map.Entry<String, Long> e2) {
            return e1.getValue().equals(e2.getValue());
        }

        private static int compareByName(Map.Entry<String, Long> e1, Map.Entry<String, Long> e2) {
            return e2.getKey().compareTo(e1.getKey());
        }
    }


}
