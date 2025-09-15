package Day_01;

import java.util.*;

public class Solution {

    public int canBeTypedWords(String text, String brokenLetters) {

        String[] words = text.split(" ");

        Set<Character> brokenSet = new HashSet<>();
        for (char c1 : brokenLetters.toCharArray()) {
            brokenSet.add(c1);
        }

        int count = 0;

        for (String s : words) {
            boolean typable = true; // reset for each word

            for (char c : s.toCharArray()) {
                if (brokenSet.contains(c)) {
                    typable = false; // word cannot be typed
                    break; // stop checking this word
                }
            }

            if (typable) {
                count++; // only add if full word is typable
            }
        }

        return count;
    }
}

    

