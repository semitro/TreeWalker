import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import smt.business.FileTreeWalker;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
        fileWalker.findFiles(new File("/tmp/walkerTest"), ".*log$")
                .forEach(System.out::println);
   }
    @After
    public void clearTree(){

    }
}
