package ui;

import ui.id3.model.SearchableTuple;
import ui.model.FeatureSet;

import java.util.List;

public interface Runner {
    void fit(FeatureSet featureSet);
    Analytics predict(List<SearchableTuple> searchables);
}
