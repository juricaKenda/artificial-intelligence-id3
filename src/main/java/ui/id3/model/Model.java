package main.java.ui.id3.model;

import main.java.ui.model.tree.Tree;
import main.java.ui.model.tree.TreeElement;
import main.java.ui.utils.Fallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

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
            Optional<TreeElement> child = findChild(value, walker.children());
            if (child.isPresent()){
                walker = child.get();
                return search();
            }
            return Fallback.get();
        }
        throw new UnsupportedOperationException("Unexpected walker node element!");
    }

    private Optional<TreeElement> findChild(String value, List<TreeElement> children) {
        for (TreeElement child : children) {
            if (child.featureSet().proxyAttribute().equals(value)){
                return of(child);
            }
        }
        return Optional.empty();
    }

    public String labelKey() {
        return tree.labelKey();
    }

    public HashMap<Integer, List<String>> depthLog(){
        HashMap<Integer, List<String>> depths = new HashMap<>();
        logDepths(tree.root(),depths,0);
        return depths;
    }

    private void logDepths(TreeElement root, HashMap<Integer, List<String>> depths, int depth) {
        if (root.splitterFeature().isPresent()){
            if (!depths.containsKey(depth)){
                depths.put(depth,new ArrayList<>());
            }
            depths.get(depth).add(root.splitterFeature().get().key());
            for (TreeElement child : root.children()) {
                logDepths(child,depths,depth+1);
            }
        }
    }

    public List<String> labelValues() {
        return tree.labelValues();
    }
}
