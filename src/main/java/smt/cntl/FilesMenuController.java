package smt.cntl;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

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

    private final PathsToTreeTransformer pathsToTreeTransformer = new PathsToTreeTransformer();
    private final TextPainter textPainter = new TextPainter();

    @FXML
    private void initialize(){
        // set up file hierarchy click listener
        fileHierarchy.getSelectionModel().selectedItemProperty().addListener((observable,old,node)->{
            final TreeItem<String> selectedFile = (TreeItem<String>) node;
            if( selectedFile!= null
                    && selectedFile.isLeaf() // if it's a file, not a directory
                    && // and there's no the same tab
                    tabPane.getTabs().
                            filtered(tab->tab.getText().equals(selectedFile.getValue()))
                            .isEmpty())
            {
                // load file content
                final TextFlow fileContent = new TextFlow();
                try {
                    // restriction: it can highlight only the text that less than one stroke!
                    Files.readAllLines(rootDirectory.resolve(pathsToTreeTransformer.leafToPath(selectedFile)))
                            .forEach(
                            line->
                                fileContent.getChildren().
                                        addAll(textPainter.highlightWords
                                                    (line,textToSearch.equals("") ? "$" : Pattern.quote(textToSearch),
                                                            "highlighted-text", "usual-text"),
                                                    new Text("\n"))

                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //creating tab
                final ScrollPane scrollPane = new ScrollPane();
                final Tab newTab = new Tab(selectedFile.getValue());
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
