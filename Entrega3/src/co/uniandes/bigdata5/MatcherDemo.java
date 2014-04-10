package co.uniandes.bigdata5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class MatcherDemo {

    private static final String REGEX =
        "#(\\w*)";
    private static final String INPUT =
        "#yoloswag asdfjasdñfkljas #swag";

    public static void main(String[] args) {
       Pattern p = Pattern.compile(REGEX);
       //  get a matcher object
       Matcher m = p.matcher(INPUT);
       int count = 0;
       ArrayList<String> matches = new ArrayList<String>();
       while(m.find()) {
    	   matches.add(m.group());
           count++;
           System.out.println("Match number "
                              + count);
           System.out.println(m.group());
           System.out.println("start(): "
                              + m.start());
           System.out.println("end(): "
                              + m.end());
      }
       System.out.println(matches.size());
      System.out.println(Arrays.toString(matches.toArray()));
   }
}