class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        // Count frequency of characters in s
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // prefix[i] stores the characters used to match target
        StringBuilder prefix = new StringBuilder();

        int matched = 0;

        // Try to match target character by character
        while (matched < n) {
            char ch = target.charAt(matched);

            if (freq[ch - 'a'] > 0) {
                prefix.append(ch);
                freq[ch - 'a']--;
                matched++;
            } else {
                break;
            }
        }

        /*
         * Try making the answer greater at position i.
         * We start from the rightmost possible position.
         */
        for (int i = matched; i >= 0; i--) {

            // If i is part of the matched prefix,
            // restore target[i] because we are going to replace it.
            if (i < matched) {
                char ch = prefix.charAt(prefix.length() - 1);
                prefix.deleteCharAt(prefix.length() - 1);
                freq[ch - 'a']++;
            }

            // Find the smallest available character > target[i]
            if (i < n) {
                for (int c = target.charAt(i) - 'a' + 1; c < 26; c++) {
                    if (freq[c] > 0) {

                        StringBuilder answer = new StringBuilder(prefix);

                        // Place the smallest character greater than target[i]
                        answer.append((char) ('a' + c));
                        freq[c]--;

                        // Append remaining characters in sorted order
                        for (int j = 0; j < 26; j++) {
                            while (freq[j] > 0) {
                                answer.append((char) ('a' + j));
                                freq[j]--;
                            }
                        }

                        return answer.toString();
                    }
                }
            }
        }

        return "";
    }
}