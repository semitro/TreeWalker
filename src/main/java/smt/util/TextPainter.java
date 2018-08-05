package smt.util;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/*

 */

public class TextPainter {

    private TextSplitter splitter = new TextSplitter();
    /**
      Generates textFlow in which some words's styles are changed
     @param text - source text
     @param style - new style
     @param words - words to highlight
     */
    public TextFlow highlightWords(String text, String regexp, String style){
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
