package smt.util;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextSplitter {

    /**
     Consistently splits string to 2 groups:
     which matches regexp and which doesn't,
     so they follow in the list one by one: match, no match, match, no match..
     for example:
     text: abc123abc123123qwerty
     regexp: 123
     result:
     [abc, 123, abc, 123123, qwerty ]
     @param text - string to be split
     @param regexp - substring that matches the regexp, will be stored
     with odd indexes
     @result List of string in which strings with odd indexes were highlighted
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
