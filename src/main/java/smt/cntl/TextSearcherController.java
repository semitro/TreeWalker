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

    @FXML private BottomMenuController bottomMenuController;
    private FileFinder fileFinder = new FileFinder();

    @FXML
    public void initialize() {
   RRR
    }
    public void onHierarchyClick(MouseEvent event){
        System.out.println(event.getPickResult().getIntersectedNode().toString());
  }

}
