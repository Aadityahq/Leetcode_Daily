class Solution {
    public int numDistinct(String s, String t) {

        int m = t.length();

        int[] dp = new int[m + 1];

        // There is exactly one way to form an empty target:
        // choose nothing.
        dp[0] = 1;

        for (int i = 1; i <= s.length(); i++) {

            // Go backwards so dp[j - 1] still represents
            // the previous state.
            for (int j = m; j >= 1; j--) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[j] = dp[j] + dp[j - 1];
                }
            }
        }

        return dp[m];
    }
}