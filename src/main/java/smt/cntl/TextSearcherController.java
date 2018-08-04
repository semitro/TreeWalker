package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import smt.util.PathsToTreeTransormer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TextSearcherController {
    private DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML private TreeView fileHierarchy;
    @FXML private TextField filePostfix;
    public void onSetRootClick() {
        File root = directoryChooser.showDialog(null);
        System.out.println(root.toPath());
        try(Stream<Path> pathStream = Files.find(root.toPath(), 256,
                    (p, a) -> p.toString().endsWith(filePostfix.getText()))){
            List<Path> paths = pathStream.sorted().collect(Collectors.toList());
            fileHierarchy.setRoot(new PathsToTreeTransormer().pathsToTree(paths, root.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onFindClick(){
        TreeItem<String> root = new TreeItem<>();
        root.setValue("safsa");
        root.getChildren().add(new TreeItem<>("a"));
        root.getChildren().add(new TreeItem<>("a"));
        fileHierarchy.setRoot(root);
    }

    public TextField getFilePostfix() {
        return filePostfix;
    }

    public void setFilePostfix(TextField filePostfix) {
        this.filePostfix = filePostfix;
    }
}
