package main.java.ui.id3.utils;

import main.java.ui.id3.model.Model;
import main.java.ui.model.tree.Tree;
import main.java.ui.model.tree.TreeElement;

public class TreeZip {

    public static Model zip(Tree tree) {
        String fallback = tree.fallback();
        Model model = new Model(tree.labelKey(),fallback,tree.labelValues());
        recursiveZip(tree.root(),0,model);
        return model;
    }

    private static void recursiveZip(TreeElement root, int depth, Model model) {
        zipNode(root,depth,model);
        for (TreeElement child : root.children()) {
            recursiveZip(child,depth+1, model);
        }
    }

    private static void zipNode(TreeElement root, int depth, Model model) {
        if (root.labelValue().isPresent()){
            model.bindResult(root.proxy(),root.labelValue().get());
            return;
        }
        if (root.splitterFeature().isPresent()){
            String splitter = root.splitterFeature().get().toString();
            model.bindRequest(root.proxy(), splitter);
            model.log(depth,splitter);
            return;
        }
        throw new UnsupportedOperationException("Unexpected tree element!");
    }


}
