package smt.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextSplitter {

    /**
     Последовательно split string to 2 groups:
     which matches regexp and which doesn't
     */

    public List<String> split(String text, String regexp){
        List<String> words = new LinkedList<>();

        Matcher matcher = Pattern.compile(regexp).matcher(text);
        int previousMatch = 0;
        while (matcher.find()){
            words.add(text.substring(previousMatch, matcher.start()));
            words.add(text.substring(matcher.start(), matcher.end()));
            previousMatch = matcher.end();
        }
        if(previousMatch != text.length())
            words.add(text.substring(previousMatch, text.length()));

        return  words;
    }
}
