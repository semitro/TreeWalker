package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import smt.business.FileFinder;

import javax.management.ReflectionException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.List;

/**
 * The main controller that is bounded to the root stage
 **/

public class TextSearcherController {

    @FXML private BottomMenuController bottomMenuController;
    @FXML private FilesMenuController filesMenuController;

    @FXML
    private void initialize() {
        FileFinder fileFinder = new FileFinder();
        // if an exception has occurred
        fileFinder.addListener(message -> {
            Alert exceptionDuringFindingAlert = new Alert(Alert.AlertType.WARNING);
            exceptionDuringFindingAlert.setHeaderText(message.getHeader());
            exceptionDuringFindingAlert.setContentText(message.getContent());
            exceptionDuringFindingAlert.show();
        });
        if(filesMenuController == null)
            System.err.println("top");
        bottomMenuController.setFindClickCallback((root, postfix, text)->{
            filesMenuController.setTextToSearch(text);
            try {
                List<Path> founded = fileFinder.findFiles(root, postfix, text);
                if(founded.isEmpty()){
                    Alert noFilesAlert = new Alert(Alert.AlertType.INFORMATION);
                    noFilesAlert.setHeaderText("Не найдено");
                    noFilesAlert.setContentText("Ни одного подходящго файла найдено не было");
                    noFilesAlert.show();
                }
                else
                    filesMenuController.setFiles(founded, root.toPath());
            }catch (IOException | UncheckedIOException ioe){
                Alert noFilesAlert = new Alert(Alert.AlertType.WARNING);
                noFilesAlert.setHeaderText("Ошибка поиска");
                noFilesAlert.setContentText(ioe.getMessage());
                noFilesAlert.show();
            }
        });
    }
}
