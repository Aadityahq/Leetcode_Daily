class Solution {
    private static final long MOD = 1_000_000_007L;

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        long[] up = new long[m];
        long[] down = new long[m];

        // Length = 2 initialization
        for (int x = 0; x < m; x++) {
            up[x] = x;              // smaller values before x
            down[x] = m - 1 - x;    // larger values before x
        }

        // Build lengths 3..n
        for (int len = 3; len <= n; len++) {

            long[] prefixDown = new long[m];
            long[] suffixUp = new long[m];

            prefixDown[0] = down[0];
            for (int i = 1; i < m; i++) {
                prefixDown[i] = (prefixDown[i - 1] + down[i]) % MOD;
            }

            suffixUp[m - 1] = up[m - 1];
            for (int i = m - 2; i >= 0; i--) {
                suffixUp[i] = (suffixUp[i + 1] + up[i]) % MOD;
            }

            long[] newUp = new long[m];
            long[] newDown = new long[m];

            for (int x = 0; x < m; x++) {

                // Need previous trend DOWN and previous value < x
                newUp[x] = (x > 0) ? prefixDown[x - 1] : 0;

                // Need previous trend UP and previous value > x
                newDown[x] = (x + 1 < m) ? suffixUp[x + 1] : 0;
            }

            up = newUp;
            down = newDown;
        }

        long ans = 0;

        if (n == 2) {
            for (int x = 0; x < m; x++) {
                ans = (ans + up[x] + down[x]) % MOD;
            }
            return (int) ans;
        }

        for (int x = 0; x < m; x++) {
            ans = (ans + up[x] + down[x]) % MOD;
        }

        return (int) ans;
    }
}