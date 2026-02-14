class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {
        
        double[][] dp = new double[101][101];
        dp[0][0] = poured;
        
        for (int row = 0; row < 100; row++) {
            for (int col = 0; col <= row; col++) {
                
                if (dp[row][col] > 1) {
                    double overflow = (dp[row][col] - 1) / 2.0;
                    
                    dp[row + 1][col] += overflow;
                    dp[row + 1][col + 1] += overflow;
                    
                    dp[row][col] = 1; // glass can only hold 1
                }
            }
        }
        
        return Math.min(1, dp[query_row][query_glass]);
    }
}
