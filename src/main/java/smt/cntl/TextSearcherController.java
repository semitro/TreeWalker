package smt.cntl;

import javafx.fxml.FXML;
import smt.business.FileFinder;
import java.io.IOException;

public class TextSearcherController {

    @FXML private BottomMenuController bottomMenuController;
    @FXML private FilesMenuController filesMenuController;

    @FXML
    private void initialize() {
        FileFinder fileFinder = new FileFinder();
        if(filesMenuController == null)
            System.err.println("top");
        bottomMenuController.setFindClickCallback((root, postfix, text)->{
            try {
                filesMenuController.setTextToSearch(text);
                filesMenuController.setFiles(
                        fileFinder.findFiles(root, postfix, text),
                        root.toPath());
            }catch (IOException ioe){

                ioe.printStackTrace();
            }
        });
    }
}
