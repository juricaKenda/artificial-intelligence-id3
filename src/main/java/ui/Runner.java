package main.java.ui;

import main.java.ui.id3.model.SearchableTuple;
import main.java.ui.model.FeatureSet;

import java.util.List;

public interface Runner {
    void fit(FeatureSet featureSet);
    Analytics predict(List<SearchableTuple> searchables);
}
