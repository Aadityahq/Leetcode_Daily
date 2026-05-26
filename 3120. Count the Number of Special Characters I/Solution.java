import java.util.*;

class Solution {
    public int numberOfSpecialChars(String word) {
        Set<Character> set = new HashSet<>();

        // Store all characters
        for (char ch : word.toCharArray()) {
            set.add(ch);
        }

        int count = 0;

        // Check all letters a-z
        for (char ch = 'a'; ch <= 'z'; ch++) {

            // lowercase and uppercase both present
            if (set.contains(ch) && set.contains(Character.toUpperCase(ch))) {
                count++;
            }
        }

        return count;
    }
}