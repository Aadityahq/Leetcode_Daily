import java.util.Arrays;

class Solution {
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        
        // Store first and last occurrence of each character
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, -1);
        Arrays.fill(last, -1);
        
        // Fill first & last occurrence
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if (first[c] == -1) first[c] = i;
            last[c] = i;
        }
        
        int count = 0;
        
        // For every character as boundary
        for (int c = 0; c < 26; c++) {
            int L = first[c];
            int R = last[c];
            if (L == -1 || L >= R) continue; // no valid middle region
            
            boolean[] seen = new boolean[26];
            
            // Collect distinct middle characters
            for (int i = L + 1; i < R; i++) {
                seen[s.charAt(i) - 'a'] = true;
            }
            
            // Add number of distinct middle characters
            for (boolean b : seen) {
                if (b) count++;
            }
        }
        
        return count;
    }
}
