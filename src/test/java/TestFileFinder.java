import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import smt.business.FileByteContentReviewer;
import smt.business.FileContentReviewer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


public class TestFileFinder {

    private String textInFile = "\nabc\n123jjj";
    private File file1;

    @Before
    public void fileContentReviewerPreparing() throws IOException {
        Path tFile = Files.createTempFile("reviewer-test", "txt");
        file1 = tFile.toFile();
        Files.write(tFile, textInFile.getBytes());
    }

    @Test
    public void fileContentReviewerSimpleTests() throws IOException {
        FileByteContentReviewer reviewer = new FileByteContentReviewer();
        assert reviewer.contains(file1, textInFile);
        assert reviewer.contains(file1, textInFile.substring(0, 2));
        assert reviewer.contains(file1, textInFile.substring(4, textInFile.length()));
        assert reviewer.contains(file1, "");
        assert reviewer.contains(file1, "a");
        assert reviewer.contains(file1, textInFile);
    }

    @Test
    public void fileContentReviewerSimpleTestsNotContain() throws IOException {
        FileContentReviewer reviewer = new FileByteContentReviewer();
        assert !reviewer.contains(file1, "c2");

    }
    private File bigFile;
    private final int bigFileSize = 1024*1024*20; // 20 MB
    @Before
    public void bigFileTestPreparation() throws IOException {
        char[] bigStr = new char[bigFileSize];
        Arrays.fill(bigStr, 'a');
        bigStr[bigStr.length-25] = 'b';

        Path path =  Files.createTempFile("reviewer-big-test", "big");
        bigFile = path.toFile();
        Files.write(path, new String(bigStr).getBytes());
    }

    @Test
    public void bigFileTest() throws IOException {
        FileContentReviewer reviewer = new FileByteContentReviewer();
        assert reviewer.contains(bigFile, "ab");
        assert !reviewer.contains(bigFile, "c");
    }

    @After
    public void clearTree() throws IOException {
        Files.delete(bigFile.toPath());
        Files.delete(file1.toPath());
    }
}
