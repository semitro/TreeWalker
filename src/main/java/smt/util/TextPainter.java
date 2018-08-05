package smt.util;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Class to highlight words in the GUI
 *
 */
public class TextPainter {

    private TextSplitter splitter = new TextSplitter();
    /**
      Generates textFlow in which some words's styles are changed
     @param text - source text
     @param regexp - if matches, style will be set
     @param style - css style
     */
    public TextFlow highlightWords(String text, String regexp, String style){
        boolean mark = false;
        TextFlow textFlow = new TextFlow();
        for (String stub : splitter.split(text, regexp)) {
            Text nextPiece = new Text(stub);
            if(mark) nextPiece.getStyleClass().add(style);
            textFlow.getChildren().add(nextPiece);
            mark = !mark;
        }
        return textFlow;
    }

}
