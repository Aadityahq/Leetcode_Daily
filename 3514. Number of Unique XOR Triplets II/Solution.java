class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int pick = 0; pick < 3; pick++) {
            for (int xor = 0; xor < MAX; xor++) {
                if (!dp[pick][xor]) continue;

                for (int num : nums) {
                    dp[pick + 1][xor ^ num] = true;
                }
            }
        }

        int ans = 0;
        for (boolean possible : dp[3]) {
            if (possible) ans++;
        }

        return ans;
    }
}