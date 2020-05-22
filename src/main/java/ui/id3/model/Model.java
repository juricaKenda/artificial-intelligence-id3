package main.java.ui.id3.model;

import main.java.ui.model.tree.Tree;
import main.java.ui.model.tree.TreeElement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Model {
    private TreeElement walker;
    private Tree tree;
    private SearchableTuple searchableTuple;

    public Model(Tree tree){
        this.tree = tree;
        this.walker = tree.root();
    }

    public void load(SearchableTuple searchable) {
        searchableTuple = searchable;
        walker = tree.root();
    }

    public String result(){
        return search();
    }

    private String search() {
        if (walker.labelValue().isPresent()){
            return walker.labelValue().get();
        }
        if (walker.splitterFeature().isPresent()){
            String key = walker.splitterFeature().get().key();
            String value = searchableTuple.get(key);
            walker = findChild(value,walker.children());
            return search();
        }
        return null;
    }

    private TreeElement findChild(String value, List<TreeElement> children) {
        for (TreeElement child : children) {
            if (child.featureSet().proxyAttribute().equals(value)){
                return child;
            }
        }
        throw new UnsupportedOperationException("");
    }

    public String labelKey() {
        return tree.labelKey();
    }

    public HashMap<Integer, List<String>> depthLog(){
        return new HashMap<>();
    }

    public List<String> labelValues() {
        return tree.labelValues();
    }
}
