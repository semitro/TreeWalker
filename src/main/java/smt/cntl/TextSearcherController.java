package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import smt.business.FileFinder;
import smt.util.PathsToTreeTransormer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextSearcherController {

    @FXML private TreeView fileHierarchy;
    @FXML private TabPane tabPane;
    @FXML private BottomMenuController bottomMenuController;
    private FileFinder fileFinder = new FileFinder();
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

    public void onHierarchyClick(MouseEvent event){
        System.out.println(event.getPickResult().getIntersectedNode().toString());
        bottomMenuController.setFindClickCallback((root, postfix, text)->{
            try {
                List<Path> paths = fileFinder.findFiles(root, postfix, text)
                        .sorted().collect(Collectors.toList());
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        });
  }

}
