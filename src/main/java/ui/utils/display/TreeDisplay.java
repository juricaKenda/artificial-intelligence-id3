package main.java.ui.utils.display;

import main.java.ui.model.tree.Tree;
import main.java.ui.model.tree.TreeElement;

import static java.lang.String.format;

public class TreeDisplay {

    public static void show(Tree tree) {
        showRecursive(tree.root(),1);
    }

    private static void showRecursive(TreeElement root, int depth) {
        System.out.println(formatted(root,depth));
        for (TreeElement child : root.children()) {
            showRecursive(child,depth+1);
        }
    }

    private static String formatted(TreeElement root, int depth) {
        String dashes = dashes(depth);
        if (root.labelValue().isPresent()){
            return format("%s %s [%s]",dashes,root.proxy(),root.labelValue().get());
        }
        if (root.splitterFeature().isPresent()){
            return format("%s %s (%s)",dashes,root.proxy(),root.splitterFeature().get());
        }
        throw new UnsupportedOperationException("Unexpected tree element!");
    }

    private static String dashes(int depth) {
        String dashes = "- - ";
        while (depth > 0){
            dashes += dashes;
            depth--;
        }
        return dashes;
    }

}
