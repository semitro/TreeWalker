package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import smt.util.PathsToTreeTransformer;
import smt.util.TextPainter;

import javax.swing.event.ChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FilesMenuController {

    @FXML private TreeView fileHierarchy;
    @FXML private TabPane tabPane;

    private PathsToTreeTransformer pathsToTreeTransformer = new PathsToTreeTransformer();
    private TextPainter textPainter = new TextPainter();
    @FXML
    public void initialize(){
        // on file hierarchy click
        fileHierarchy.getSelectionModel().selectedItemProperty().addListener((observable,old,node)->{
            TreeItem<String> selectedFile = (TreeItem<String>) node;
            if( selectedFile.isLeaf()
                    && // and there's no the same tab
                    tabPane.getTabs().
                            filtered(tab->tab.getText().equals(selectedFile.getValue()))
                            .isEmpty())
            {
                // load file content
                TextFlow fileContent = new TextFlow();
                try {
                    // can highlight only the text that less than one stroke!
                    Files.readAllLines(rootDirectory.resolve(pathsToTreeTransformer.leafToPath(selectedFile))).forEach(
                            line->
                                fileContent.getChildren().
                                        addAll( textPainter .highlightWords
                                                (line,textToSearch, "highlighted-text" ),
                                                new Text("\n"))

                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ScrollPane scrollPane = new ScrollPane();
                Tab newTab = new Tab(selectedFile.getValue());
                scrollPane.setContent(fileContent);
                newTab.setContent(scrollPane);
                tabPane.getTabs().add(newTab);
            }
        });
    }

    private String textToSearch;
    private Path rootDirectory;

    public void setFiles(List<Path> files, Path root){
        rootDirectory = root;
        fileHierarchy.setRoot(new PathsToTreeTransformer().pathsToTree(files, root));
    }

    public void setTextToSearch(String textToSearch) {
        this.textToSearch = textToSearch;
    }
}
