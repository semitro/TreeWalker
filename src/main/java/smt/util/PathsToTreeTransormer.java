package smt.util;

import javafx.scene.control.TreeItem;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class PathsToTreeTransormer {
    public TreeItem<String> pathsToTree(List<Path> paths, Path root){
        TreeItem<String> rootItem = new TreeItem<>(root.toString()
        .substring(root.toString().lastIndexOf(File.separator)+1));
        for(Path path : paths){
            TreeItem<String> currentItem = rootItem;
            String pathStr = path.toString().replaceFirst(root.toString(), "");
            for (String level : pathStr.split(File.separator)) {
                if(level.equals("")) continue;
                System.err.println(level);
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
}
