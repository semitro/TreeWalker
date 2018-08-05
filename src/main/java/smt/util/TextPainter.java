package smt.util;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/*

 */

public class TextPainter {
    public static void main(String[] args) {
        String str  = "123ab33аb2adbb1ab1ababf";
        for (String ab : new TextPainter().split(str, "ab")) {
            System.err.println(ab);
        }
    }
    private TextSplitter splitter = new TextSplitter();
    /**
      Generates textFlow in which words styles are changed
     @param text - source text
     @param style - new style
     @param words - words to highlight
     */
    public TextFlow highlightWords(String text, String regexp, String style ){
        boolean mark = false;
        TextFlow textFlow = new TextFlow();
        for (String stub : splitter.split(text, regexp)) {
            Text nextPiece = new Text(stub);
            if(mark)
                nextPiece.setStyle(style);
            textFlow.getChildren().add(nextPiece);
            mark = !mark;
        }
        return textFlow;
    }

}
