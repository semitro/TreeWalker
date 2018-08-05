package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import smt.business.FileFinder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;

/**
 * The main controller that is bounded to the root stage
 **/

public class MainController {

    @FXML private BottomMenuController bottomMenuController;
    @FXML private FilesMenuController filesMenuController;

    @FXML private ProgressIndicator searchingProgressIndicator;

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

        //on "find" click
        bottomMenuController.setFindClickCallback((root, postfix, text)->{
            searchingProgressIndicator.setVisible(true);
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
                noFilesAlert.setHeaderText("Ошибка при поиске");
                noFilesAlert.setContentText(ioe.getMessage());
                noFilesAlert.show();
            }finally {
                searchingProgressIndicator.setVisible(false);
            }
        });
    }
}
