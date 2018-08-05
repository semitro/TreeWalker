package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class BottomMenuController {

    private File rootDirectory;
    @FXML private TextField filePostfix;

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
        }
        StringBuilder warningMessage = new StringBuilder();

        if(filePostfix.getText().equals("")){
            warningMessage.append("Расширение не выбрано." +
                    "Поиск будет осуществлён по всем файлам\n");
        }
    }
}
