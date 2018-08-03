package smt.cntl;

import javafx.scene.Scene;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

public class TextSearcherController {
    private Stage fileChooserStage = new Stage();
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    public void onSetRootClick(){
        directoryChooser.showDialog(null);
    }
}
