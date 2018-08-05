package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class BottomMenuController {

    private File rootDirectory;
    @FXML private TextField filePostfix;
    @FXML private TextArea textToSearching;

    public void onSetRootClick() {
        rootDirectory = new DirectoryChooser().showDialog(null);
      /*  try(Stream<Path> pathStream = Files.find(root.toPath(), 256,
                (p, a) -> p.toString().endsWith(filePostfix.getText()))){
            List<Path> paths = pathStream.sorted().collect(Collectors.toList());
            fileHierarchy.setRoot(new PathsToTreeTransormer().pathsToTree(paths, root.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

    }
    //public void setFindClickCallback(BiFunction<File rootDir, String postfix, >)
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

           }
        }
    }
}
