class Solution {
    public int numberOfSpecialChars(String word) {
        
        int[] lastLower = new int[26];
        int[] firstUpper = new int[26];

        // initialize
        for (int i = 0; i < 26; i++) {
            lastLower[i] = -1;
            firstUpper[i] = -1;
        }

        // traverse string
        for (int i = 0; i < word.length(); i++) {

            char ch = word.charAt(i);

            // lowercase
            if (Character.isLowerCase(ch)) {
                lastLower[ch - 'a'] = i;
            }

            // uppercase
            else {
                int idx = ch - 'A';

                // store first occurrence only
                if (firstUpper[idx] == -1) {
                    firstUpper[idx] = i;
                }
            }
        }

        int count = 0;

        // check all characters
        for (int i = 0; i < 26; i++) {

            // both exist
            if (lastLower[i] != -1 && firstUpper[i] != -1) {

                // all lowercase before uppercase
                if (lastLower[i] < firstUpper[i]) {
                    count++;
                }
            }
        }

        return count;
    }
}