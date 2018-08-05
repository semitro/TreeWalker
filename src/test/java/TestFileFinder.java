import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import smt.business.FileByteContentReviewer;
import smt.business.FileContentReviewer;
import smt.business.FileTreeWalker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

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
   public void fileContentReviewerPreparing() throws IOException {
       Path tFile = Files.createTempFile("reviewer-test", "txt");
       file1 = tFile.toFile();
       Files.write(tFile, textInFile.getBytes());
   }
   @Test
   public void fileContentReviewerSimpleTests(){
       FileByteContentReviewer reviewer = new FileByteContentReviewer();
       try {
           reviewer.contains(file1, textInFile);
           reviewer.contains(file1, textInFile.substring(0, 2));
           reviewer.contains(file1, textInFile.substring(4, textInFile.length()));
           reviewer.contains(file1, "");
           reviewer.contains(file1, "a");
           reviewer.contains(file1, "c2");

           assert reviewer.contains(file1, textInFile);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
   private File bigFile;
   private String bigText;
   private final int bigFileSize = 1024*1024*20; // 20 MB
   @Before
   public void bigFileTestPreparation() throws IOException {
       Random r = new Random(System.currentTimeMillis());
       byte randomBytes[] = new byte[bigFileSize];
       r.nextBytes(randomBytes);
       Path path =  Files.createTempFile("reviewer-big-test", "big");
       bigFile = path.toFile();
       bigText = new String(randomBytes);
       Files.write(path, randomBytes);
   }
   @Test
   public void t() throws IOException {
       FileContentReviewer reviewer = new FileByteContentReviewer();
    //   assert reviewer.contains(Paths.get("/tmp/1.txt").toFile(),"end");
     //  assert reviewer.contains(Paths.get("/tmp/2b.txt").toFile(),"end");
   }
   @Test
   public void bigFileTest() throws IOException {
       FileContentReviewer reviewer = new FileByteContentReviewer();
       Random r = new Random(System.currentTimeMillis());
       int sRange = r.nextInt(bigText.length()/4);
       int sFrom = bigText.length()/2 + r.nextInt(bigText.length() / 4);
      // assert reviewer.contains(bigFile, bigText.substring(0, 1));
       //assert reviewer.contains(bigFile, bigText.substring(2049, 5000));
       //assert reviewer.contains(bigFile, bigText.substring(sFrom, sFrom + sRange));
   }
    @After
    public void clearTree(){

    }
}
