class Solution {
    public int numSub(String s) {
        long MOD = 1000000007;
        long count = 0; // count continuous 1s
        long ans = 0;

        for (char c : s.toCharArray()) {
            if (c == '1') {
                count++;
            } else {
                ans = (ans + (count * (count + 1) / 2) % MOD) % MOD;
                count = 0;
            }
        }

        // Add last segment
        ans = (ans + (count * (count + 1) / 2) % MOD) % MOD;

        return (int) ans;
    }
}
