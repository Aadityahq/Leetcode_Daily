class Solution {
    private static final long MOD = 1_000_000_007L;

    public int countPermutations(int[] complexity) {
        int n = complexity.length;
        // Check if complexity[0] is strictly smaller than all other elements
        for (int i = 1; i < n; ++i) {
            if (complexity[i] <= complexity[0]) {
                return 0;
            }
        }
        // compute (n-1)! % MOD
        long ans = 1;
        for (int i = 1; i <= n - 1; ++i) {
            ans = (ans * i) % MOD;
        }
        return (int) ans;
    }
}
