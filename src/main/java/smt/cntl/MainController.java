package smt.cntl;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import smt.business.FileFinder;

/**
 * The main controller that is bounded to the root stage
 **/

public class MainController {

    @FXML private BottomMenuController bottomMenuController;
    @FXML private FilesMenuController filesMenuController;

    @FXML private ProgressIndicator searchingProgressIndicator;

    @FXML
    private void initialize() {

        final FileFinder fileFinder = new FileFinder();
        // if an exception has occurred
        fileFinder.addListener(message -> {
            Alert exceptionDuringFindingAlert = new Alert(Alert.AlertType.WARNING);
            exceptionDuringFindingAlert.setHeaderText(message.getHeader());
            exceptionDuringFindingAlert.setContentText(message.getContent());
            exceptionDuringFindingAlert.show();
        });

        //on "find" click
        bottomMenuController.setFindClickCallback((root, postfix, text)->
                new Thread(()->{
                    searchingRoutinePrefix(text);
                    // we should use fx thread if we touch its structures
                    final List<Path> founded = new LinkedList<>();
                    try {
                        founded.addAll(fileFinder.findFiles(root, postfix, text));
                    }catch (IOException | UncheckedIOException ioe){
                        Alert noFilesAlert = new Alert(Alert.AlertType.WARNING);
                        noFilesAlert.setHeaderText("Ошибка при поиске");
                        noFilesAlert.setContentText(ioe.getMessage());
                        noFilesAlert.show();
                    }finally {
                        searchingRoutinePostfix(founded, root.toPath());
                    }
                }).start());
    }

    // that should be done before searching starts
    private void searchingRoutinePrefix(String textToSearch){
        Platform.runLater(()->{
            searchingProgressIndicator.setVisible(true);
            searchingProgressIndicator.setProgress(-1.);
            bottomMenuController.setIsSearching(true);
            filesMenuController.setTextToSearch(textToSearch);
        });
    }

    private void searchingRoutinePostfix(List<Path> foundedFiles, Path root){
        Platform.runLater(()->{
            searchingProgressIndicator.setVisible(false);
            bottomMenuController.setIsSearching(false);
            if(foundedFiles.isEmpty()){
                Alert noFilesAlert = new Alert(Alert.AlertType.INFORMATION);
                noFilesAlert.setHeaderText("Не найдено");
                noFilesAlert.setContentText("Ни одного подходящго файла найдено не было");
                noFilesAlert.show();
            }
            else
                filesMenuController.setFiles(foundedFiles, root);
        });

    }
}
