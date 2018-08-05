package smt.util;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 * This class helps you to get path in file system
 * from valid javafx's Tree view
 * or to build a tree view from list of paths
 *
 **/
public class PathsToTreeTransformer {
    /**
     * build fx tree from a set of files
     * that have the same root directory
     * for example,
     *  if paths are like
     *  /1/2/root/a/b
     *  /1/2/root/a/c
     *  and the root param is 'root'
     * it will build a tree like
     * root
     *     |
     *      a
     *       |b
     *       |c
     *
     * */
    public TreeItem<String> pathsToTree(List<Path> paths, Path root){
        TreeItem<String> rootItem = new TreeItem<>(root.toString()
        .substring(root.toString().lastIndexOf(File.separator)+1));
        for(Path path : paths){
            TreeItem<String> currentItem = rootItem;
            String pathStr = path.toString().replaceFirst(root.toString(), "");
            for (String level : pathStr.split(File.separator)) {
                if(level.equals("")) continue;
                boolean hasSuchChild = false;
                for(TreeItem<String> node : currentItem.getChildren()){
                    if(node.getValue().equals(level)){
                        currentItem = node;
                        hasSuchChild = true;
                        break;
                    }
                }
                if(!hasSuchChild){
                    TreeItem<String> newChild = new TreeItem<>(level);
                    currentItem.getChildren().add(newChild);
                    currentItem = newChild;
                }

            }
        }
        return rootItem;
    }

    /**
     * Build a file system path from a leaf of a javafx tree
     */
    public Path leafToPath(TreeItem<String> leaf){
        StringBuilder builder = new StringBuilder();
        while (leaf.getParent() != null){
            builder.insert(0,leaf.getValue() + File.separator);
            leaf = leaf.getParent();
        }
        File file = new File(builder.toString());
        return file.toPath();
    }
}
