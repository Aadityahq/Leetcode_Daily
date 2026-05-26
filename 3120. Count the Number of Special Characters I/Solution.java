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



//----------- Brute Force Method -----------

// class Solution {
//     public int numberOfSpecialChars(String word) {

//         boolean[] counted = new boolean[26];
//         int count = 0;

//         // Traverse every character
//         for (int i = 0; i < word.length(); i++) {

//             char ch = word.charAt(i);

//             // If lowercase
//             if (ch >= 'a' && ch <= 'z') {

//                 char upper = (char)(ch - 32);

//                 // Search uppercase in whole string
//                 for (int j = 0; j < word.length(); j++) {

//                     if (word.charAt(j) == upper && !counted[ch - 'a']) {
//                         count++;
//                         counted[ch - 'a'] = true;
//                         break;
//                     }
//                 }
//             }

//             // If uppercase
//             else {

//                 char lower = (char)(ch + 32);

//                 // Search lowercase in whole string
//                 for (int j = 0; j < word.length(); j++) {

//                     if (word.charAt(j) == lower && !counted[ch - 'A']) {
//                         count++;
//                         counted[ch - 'A'] = true;
//                         break;
//                     }
//                 }
//             }
//         }

//         return count;
//     }
// }