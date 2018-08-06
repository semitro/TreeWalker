package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import smt.business.TriConsumer;

import java.io.File;

/**
 * Menu where attributes for the searching are getting
 * from the user
 **/
public class BottomMenuController {

    @FXML private TextField filePostfix;
    @FXML private TextArea textToSearching;
    @FXML private Button findButton;
    @FXML private Label currentRootLabel;

    // the directory where we start searching
    private File rootDirectory;
    // callback being invoked when "find" button is clicked
    private TriConsumer<File, String,String> walkerAction;
    public void onSetRootClick() {
        rootDirectory = new DirectoryChooser().showDialog(null);
        if(rootDirectory != null)
            currentRootLabel.setText("Директория поиска:\n" + rootDirectory.getName());
    }

    public void setFindClickCallback(TriConsumer<File, String, String> callback){
        walkerAction = callback;
    }

    // if the button that produces the searching is clicked
    @FXML
    private void onFindClick(){
        if(rootDirectory == null) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Укажите корневую директория для поиска!");
            alert.show();
            return;
        }
        final StringBuilder warningMessage = new StringBuilder();
        String warningMessageHeader = null;
        if(filePostfix.getText().equals("")){
            warningMessageHeader = "Расширение не выбрано";
            warningMessage.append("Поиск будет осуществлён по всем файлам\n");
        }
        if(textToSearching.getText().equals("")){
            warningMessageHeader = "Текст для поиска не указан";
            warningMessage.append("Все файлы будут отобраны");
        }
        if(!warningMessage.toString().equals("")){
            final Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            warning.setHeaderText(warningMessageHeader);
            warning.setContentText(warningMessage.toString());
            if(warning.showAndWait().get() == ButtonType.OK){
                walkerAction.accept(rootDirectory, filePostfix.getText(), textToSearching.getText());
            }
        }
        else
            walkerAction.accept(rootDirectory, filePostfix.getText(), textToSearching.getText());
    }
    // to deactivate the button
    void setIsSearching(boolean isSearching){
        findButton.setDisable(isSearching);
    }
}
