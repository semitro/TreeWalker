package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import smt.util.PathsToTreeTransformer;
import smt.util.TextPainter;

/**
 * Controller maintaining file tree and tabs
 */
public class FilesMenuController {

    @FXML private TreeView fileHierarchy;
    @FXML private TabPane tabPane;

    private String textToSearch;
    private Path rootDirectory;

    private PathsToTreeTransformer pathsToTreeTransformer = new PathsToTreeTransformer();
    private TextPainter textPainter = new TextPainter();

    @FXML
    private void initialize(){
        // on file hierarchy click
        fileHierarchy.getSelectionModel().selectedItemProperty().addListener((observable,old,node)->{
            TreeItem<String> selectedFile = (TreeItem<String>) node;
            if( selectedFile.isLeaf() // if it's a file, not a directory
                    && // and there's no the same tab
                    tabPane.getTabs().
                            filtered(tab->tab.getText().equals(selectedFile.getValue()))
                            .isEmpty())
            {
                // load file content
                TextFlow fileContent = new TextFlow();
                try {
                    // restriction: it can highlight only the text that less than one stroke!
                    Files.readAllLines(rootDirectory.resolve(pathsToTreeTransformer.leafToPath(selectedFile)))
                            .forEach(
                            line->
                                fileContent.getChildren().
                                        addAll(textPainter.highlightWords
                                                    (line,textToSearch, "highlighted-text" ),
                                                    new Text("\n"))

                    );
                } catch (IOException e) {
                    System.err.println("Here!!");
                    e.printStackTrace();
                }
                //creating tab
                ScrollPane scrollPane = new ScrollPane();
                Tab newTab = new Tab(selectedFile.getValue());
                scrollPane.setContent(fileContent);
                newTab.setContent(scrollPane);
                tabPane.getTabs().add(newTab);
            }
        });
    }

    public void setFiles(List<Path> files, Path root){
        rootDirectory = root;
        fileHierarchy.setRoot(new PathsToTreeTransformer().pathsToTree(files, root));
    }

    public void setTextToSearch(String textToSearch) {
        this.textToSearch = textToSearch;
    }
}
