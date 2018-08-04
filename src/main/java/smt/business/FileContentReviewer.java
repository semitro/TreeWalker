package smt.business;

import java.io.File;
import java.io.IOException;

public interface FileContentReviewer {
    boolean contains(File file, String text) throws IOException;
}
