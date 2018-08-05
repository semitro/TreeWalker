package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import smt.util.PathsToTreeTransormer;

import java.nio.file.Path;
import java.util.List;

public class FilesMenuController {

    @FXML private TreeView fileHierarchy;
    @FXML private TabPane tabPane;

    @FXML
    public void initialize(){
        fileHierarchy.getSelectionModel().selectedItemProperty().addListener((observable,old,node)->{
            if(((TreeItem<String>)node).isLeaf())
                System.out.println(new PathsToTreeTransormer().leafToPath((TreeItem<String>) node));

            if(tabPane.getTabs().
                    filtered(tab->tab.getText().equals(((TreeItem<String>) node).getValue()))
                    .isEmpty()){
                Tab newTab = new Tab(((TreeItem<String>) node).getValue());
                TextFlow textFlow = new TextFlow();
                Text text1 = new Text("asf");
                Text text2 = new Text("sff");
                text2.setStyle("-fx-color: green");
                text2.setStroke(Color.GREEN);
                textFlow.getChildren().add(text1);
                textFlow.getChildren().add(text2);
                newTab.setContent(textFlow);
                tabPane.getTabs().add(newTab);
            }
        });
    }
    public void setFiles(List<Path> files, Path root){
        fileHierarchy.setRoot(new PathsToTreeTransormer().pathsToTree(files, root));
        System.err.println("sffs");
    }
}
