import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();

        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<Integer> digits = new ArrayList<>();

        // Store non-zero digits and their original indices
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (ch != '0') {
                indices.add(i);
                digits.add(ch - '0');
            }
        }

        int m = digits.size();

        long[] prefixNum = new long[m + 1];
        long[] prefixSum = new long[m + 1];
        long[] pow10 = new long[m + 1];

        pow10[0] = 1;
        for (int i = 1; i <= m; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        for (int i = 0; i < m; i++) {
            prefixNum[i + 1] = (prefixNum[i] * 10 + digits.get(i)) % MOD;
            prefixSum[i + 1] = prefixSum[i] + digits.get(i);
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int left = lowerBound(indices, l);
            int right = lowerBound(indices, r + 1);

            // No non-zero digits in this range
            if (left == right) {
                ans[i] = 0;
                continue;
            }

            int len = right - left;

            long sum = prefixSum[right] - prefixSum[left];

            long x = (prefixNum[right]
                    - (prefixNum[left] * pow10[len]) % MOD
                    + MOD) % MOD;

            ans[i] = (int) ((x * sum) % MOD);
        }

        return ans;
    }

    private int lowerBound(ArrayList<Integer> list, int target) {
        int lo = 0, hi = list.size();

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            if (list.get(mid) < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        return lo;
    }
}