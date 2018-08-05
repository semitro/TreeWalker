package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import smt.business.FileFinder;
import java.io.IOException;
import java.util.stream.Collectors;

public class TextSearcherController {

    @FXML private BottomMenuController bottomMenuController;
    @FXML private FilesMenuController filesMenuController;

    @FXML
    public void initialize() {
        FileFinder fileFinder = new FileFinder();
        if(filesMenuController == null)
            System.err.println("top");
        bottomMenuController.setFindClickCallback((root, postfix, text)->{
            try {
                filesMenuController.setFiles(
                        fileFinder.findFiles(root, postfix, text).collect(Collectors.toList()),
                        root.toPath());
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        });
    }
}
