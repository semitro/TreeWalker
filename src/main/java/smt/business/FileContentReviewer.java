package smt.business;

import java.io.File;
import java.io.IOException;

/**
  check if file contains the text or not
 */
public interface FileContentReviewer {
    boolean contains(File file, String text) throws IOException;
}
