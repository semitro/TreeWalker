package smt.business;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class encapsulate the logic of finding
 * appropriate files in the file hierarchy
 * multi threading is provided by parallel() method of Stream API
**/
public class FileFinder{
    private FileContentReviewer fileContentReviewer = new FileByteContentReviewer();

    /**
     * find file in the file system
     *
     * @param root - the directory where searching starts
     * @param postfix - only files that is ended by the postfix are selected
     * @param text - only files that contains the text are selected
     * @return all the selected files or empty list if there aren't any
     *
     **/
    public List<Path> findFiles(File root, String postfix, String text) throws IOException{
        if(!root.isDirectory())
            throw new IllegalArgumentException(root + " is not a directory");
        try(
                Stream<Path> pathStream =
                        Files.find(root.toPath(), 256,
                                (path, attributes) -> attributes.isRegularFile() && path.toString().endsWith(postfix))
                        .parallel()
                        .filter(path->{
                            try {
                                return fileContentReviewer.contains(path.toFile(), text);
                            } catch (IOException e) {
                                // make it normal
                                e.printStackTrace();
                            }
                            return false;
                        })){
            return  pathStream.collect(Collectors.toList());
        }
    }
}
