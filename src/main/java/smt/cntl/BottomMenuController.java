package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import smt.business.TriConsumer;

import java.io.File;

public class BottomMenuController {

    @FXML private TextField filePostfix;
    @FXML private TextArea textToSearching;

    private File rootDirectory;
    // callback being invoked when "find" button is clicked
    private TriConsumer<File, String,String> walkerAction;
    public void onSetRootClick() {
        rootDirectory = new DirectoryChooser().showDialog(null);
    }

    public void setFindClickCallback(TriConsumer<File, String, String> callback){
        walkerAction = callback;
    }

    public void onFindClick(){
        if(rootDirectory == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Укажите корневую директория для поиска!");
            alert.show();
            return;
        }
        StringBuilder warningMessage = new StringBuilder();

        if(filePostfix.getText().equals("")){
            warningMessage.append("Расширение не выбрано." +
                    "Поиск будет осуществлён по всем файлам\n");
        }
        if(textToSearching.getText().equals("")){
            warningMessage.append("Текст для поиска не указан.\n" +
                    "будут выбраны все файлы");
        }
        if(!warningMessage.toString().equals("")){
           Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
           warning.setContentText(warningMessage.toString());
           if(warning.showAndWait().get() == ButtonType.OK){
                walkerAction.accept(rootDirectory, filePostfix.getText(), textToSearching.getText());
           }
        }
    }
}
