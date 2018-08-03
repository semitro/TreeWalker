import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestFileFinder {

    @Before
    public void initTree(){
        try {
            Runtime.getRuntime().exec("mkdir test");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   @Test
   public void test1(){
       System.out.println("sfsA");
   }
    @After
    public void clearTree(){

    }
}
