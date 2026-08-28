class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();
        int half = n / 2;

        // Frequency of every character in s
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Check whether a palindrome is possible
        int oddCount = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 == 1) {
                oddCount++;
                middle = (char) ('a' + i);
            }
        }

        if (oddCount > 1) {
            return "";
        }

        /*
         * halfFreq contains the number of each character
         * that must appear in the first half.
         */
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        /*
         * ----------------------------------------------------
         * Step 1:
         * Try making the first half exactly equal to
         * target[0 ... half-1].
         * ----------------------------------------------------
         */
        int[] remaining = halfFreq.clone();
        boolean possible = true;

        for (int i = 0; i < half; i++) {
            int idx = target.charAt(i) - 'a';

            if (remaining[idx] == 0) {
                possible = false;
                break;
            }

            remaining[idx]--;
        }

        if (possible) {

            StringBuilder firstHalf = new StringBuilder();

            for (int i = 0; i < half; i++) {
                firstHalf.append(target.charAt(i));
            }

            String candidate = buildPalindrome(
                    firstHalf.toString(),
                    middle,
                    n
            );

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * ----------------------------------------------------
         * Step 2:
         * The exact first half doesn't work.
         *
         * Find the rightmost position where we can increase
         * target[i].
         * ----------------------------------------------------
         */
        for (int i = half - 1; i >= 0; i--) {

            remaining = halfFreq.clone();

            /*
             * Keep target[0 ... i-1] exactly the same.
             */
            boolean prefixPossible = true;

            for (int j = 0; j < i; j++) {

                int idx = target.charAt(j) - 'a';

                if (remaining[idx] == 0) {
                    prefixPossible = false;
                    break;
                }

                remaining[idx]--;
            }

            if (!prefixPossible) {
                continue;
            }

            /*
             * Find the smallest character that is strictly
             * greater than target[i].
             */
            int current = target.charAt(i) - 'a';

            int bigger = -1;

            for (int c = current + 1; c < 26; c++) {
                if (remaining[c] > 0) {
                    bigger = c;
                    break;
                }
            }

            if (bigger == -1) {
                continue;
            }

            /*
             * Construct the first half:
             *
             * target[0 ... i-1]
             * + bigger character
             * + remaining characters in ascending order
             */
            StringBuilder firstHalf = new StringBuilder();

            for (int j = 0; j < i; j++) {
                firstHalf.append(target.charAt(j));
            }

            firstHalf.append((char) ('a' + bigger));
            remaining[bigger]--;

            // Fill the rest with the smallest possible characters
            for (int c = 0; c < 26; c++) {
                while (remaining[c] > 0) {
                    firstHalf.append((char) ('a' + c));
                    remaining[c]--;
                }
            }

            return buildPalindrome(
                    firstHalf.toString(),
                    middle,
                    n
            );
        }

        return "";
    }

    private String buildPalindrome(
            String firstHalf,
            char middle,
            int n) {

        StringBuilder result = new StringBuilder();

        // First half
        result.append(firstHalf);

        // Middle character for odd length
        if (n % 2 == 1) {
            result.append(middle);
        }

        // Reverse of first half
        for (int i = firstHalf.length() - 1; i >= 0; i--) {
            result.append(firstHalf.charAt(i));
        }

        return result.toString();
    }
}