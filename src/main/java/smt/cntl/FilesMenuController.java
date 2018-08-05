package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import jdk.nashorn.api.tree.Tree;
import smt.util.PathsToTreeTransormer;

import java.nio.file.Path;
import java.util.List;

public class FilesMenuController {

    @FXML private TreeView fileHierarchy;
    @FXML private TabPane tabPane;

    @FXML
    public void initialize(){
        // on file hierarchy click
        fileHierarchy.getSelectionModel().selectedItemProperty().addListener((observable,old,node)->{
            TreeItem<String> selectedFile = (TreeItem<String>) node;
            if( selectedFile.isLeaf()
                    && // and there's no the same tab
                    tabPane.getTabs().
                            filtered(tab->tab.getText().equals(selectedFile.getValue()))
                            .isEmpty())
            {
                Tab newTab = new Tab(((TreeItem<String>) node).getValue());
//                newTab.setContent(textFlow);
                tabPane.getTabs().add(newTab);
            }
        });
    }
    public void setFiles(List<Path> files, Path root){
        fileHierarchy.setRoot(new PathsToTreeTransormer().pathsToTree(files, root));
    }
}
