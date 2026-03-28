class Solution {
    public String findTheString(int[][] lcp) {
        int n = lcp.length;
        char[] word = new char[n];
        
        // Step 1: Validate diagonal
        for (int i = 0; i < n; i++) {
            if (lcp[i][i] != n - i) return "";
        }
        
        // Step 2: Build string
        char ch = 'a';
        
        for (int i = 0; i < n; i++) {
            if (word[i] != 0) continue;
            
            if (ch > 'z') return ""; // more than 26 groups
            
            word[i] = ch;
            
            for (int j = i + 1; j < n; j++) {
                if (lcp[i][j] > 0) {
                    word[j] = ch;
                }
            }
            
            ch++;
        }
        
        // Step 3: Validate using DP
        int[][] dp = new int[n + 1][n + 1];
        
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (word[i] == word[j]) {
                    dp[i][j] = dp[i + 1][j + 1] + 1;
                } else {
                    dp[i][j] = 0;
                }
                
                if (dp[i][j] != lcp[i][j]) {
                    return "";
                }
            }
        }
        
        return new String(word);
    }
}