package smt.business;

import java.io.File;
import java.io.FileNotFoundException;

public interface FileContentReviewer {
    boolean contains(File file, String text) throws FileNotFoundException;
}
