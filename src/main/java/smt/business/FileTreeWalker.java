package smt.business;

import java.io.File;
import java.io.FilenameFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTreeWalker {
    /**
в    *   get all the files which's names contain the pattern
    *   @param root - anchor directory
    d*               *  @param pattern - regex to mach suitable names
    **/
    public List<File> findFiles(File root, String pattern){
        if(!root.isDirectory())
            throw new IllegalArgumentException(root.getName() + " is not a directory");
        return findFiles(root, pattern, new LinkedList<>());
    }

    private List<File> findFiles(File dir, String pattern, List<File> accumulator) {
        if(dir.listFiles() != null)
            for (File file : dir.listFiles()) {
            if(file.isFile() && file.getName().matches(pattern)){
                    accumulator.add(file);
                }
            else if(file.isDirectory()) findFiles(file, pattern, accumulator);
            }
            return accumulator;
    }
}
