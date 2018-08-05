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
     @param styleNotMatch - style for text that doesn't match.
     If it's null then it's ignored
     */
    public TextFlow highlightWords(String text, String regexp, String style, String styleNotMatch){
        boolean mark = false;
        TextFlow textFlow = new TextFlow();
        for (String stub : splitter.split(text, regexp)) {
            Text nextPiece = new Text(stub);
            if (mark) nextPiece.getStyleClass().add(style);
            else if (styleNotMatch != null)
                nextPiece.getStyleClass().add(styleNotMatch);
            textFlow.getChildren().add(nextPiece);
            mark = !mark;
        }

        return textFlow;
    }

    public TextFlow highlightWords(String text, String regexp, String style){
        return highlightWords(text, regexp, style, null);
    }

}
