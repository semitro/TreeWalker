import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import smt.business.FileByteContentReviewer;
import smt.business.FileTreeWalker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestFileFinder {


    @Before
    public void initTree(){
        try {
            Runtime.getRuntime().exec("mkdir test");
        } catch (IOException e) {
            e.printStackTrace();
        }
   }
   private FileTreeWalker fileWalker = new FileTreeWalker();
   @Test
   public void test1(){
     //   fileWalker.findFiles(new File("/tmp/walkerTest"), ".*log$")
     //          .forEach(System.out::println);
   }


   private String textInFile = "abc123jjj";
   private File file1;
   @Before
   public void fileContentReviewerPreparing(){
       try {
           Path tFile = Files.createTempFile("reviewer-test", "txt");
           file1 = tFile.toFile();
           Files.write(tFile, textInFile.getBytes());
       } catch (IOException e) {
           System.err.println("Error during file content reviewer test");
           e.printStackTrace();
       }
   }
   @Test
   public void fileContentReviewerSimpleTests(){
       FileByteContentReviewer reviewer = new FileByteContentReviewer();
       try {
           System.err.println(reviewer.contains(file1, textInFile));
           System.err.println(reviewer.contains(file1, textInFile.substring(0, 2)));
           System.err.println(reviewer.contains(file1, textInFile.substring(4, textInFile.length())));
           System.err.println(reviewer.contains(file1, ""));
           System.err.println(reviewer.contains(file1, "a"));
           System.err.println(reviewer.contains(file1, "c2"));

           assert reviewer.contains(file1, textInFile);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
    @After
    public void clearTree(){

    }
}
