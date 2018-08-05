package smt.business;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileFinder{
    public List<Path> findFiles(File root, String postfix, String text) throws IOException{
        try(Stream<Path> pathStream = Files.find(root.toPath(), 256,
                (p, a) -> p.toString().endsWith(postfix))){
            return pathStream.collect(Collectors.toList());
        }
    }
}
