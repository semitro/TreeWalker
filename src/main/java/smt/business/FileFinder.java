package smt.business;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileFinder{
    private FileContentReviewer fileContentReviewer = new FileByteContentReviewer();

    public List<Path> findFiles(File root, String postfix, String text) throws IOException{
        try(
                Stream<Path> pathStream =
                        Files.find(root.toPath(), 256,
                                (path, attributes) -> path.toString().endsWith(postfix))
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
