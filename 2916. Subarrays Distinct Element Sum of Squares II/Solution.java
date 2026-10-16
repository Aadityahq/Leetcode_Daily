import java.util.Arrays;

class Solution {
    private static final long MOD = 1_000_000_007L;

    private long[] sum;
    private long[] squareSum;
    private long[] lazy;
    private int n;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        squareSum = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        Arrays.fill(last, -1);

        long answer = 0;

        for (int i = 0; i < n; i++) {
            int left = last[nums[i]] + 1;

            update(1, 0, n - 1, left, i, 1);

            answer = (answer + squareSum[1]) % MOD;

            last[nums[i]] = i;
        }

        return (int) answer;
    }

    private void apply(int node, int l, int r, long delta) {
        long len = r - l + 1L;
        long oldSum = sum[node];

        squareSum[node] = (
            squareSum[node]
            + 2L * delta % MOD * oldSum % MOD
            + len * delta % MOD * delta % MOD
        ) % MOD;

        sum[node] = (oldSum + len * delta) % MOD;
        lazy[node] = (lazy[node] + delta) % MOD;
    }

    private void pushDown(int node, int l, int r) {
        if (lazy[node] == 0 || l == r) {
            return;
        }

        int mid = l + (r - l) / 2;
        long delta = lazy[node];

        apply(node * 2, l, mid, delta);
        apply(node * 2 + 1, mid + 1, r, delta);

        lazy[node] = 0;
    }

    private void update(
        int node, int l, int r,
        int ql, int qr, long delta
    ) {
        if (qr < l || r < ql) {
            return;
        }

        if (ql <= l && r <= qr) {
            apply(node, l, r, delta);
            return;
        }

        pushDown(node, l, r);

        int mid = l + (r - l) / 2;

        update(node * 2, l, mid, ql, qr, delta);
        update(node * 2 + 1, mid + 1, r, ql, qr, delta);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        squareSum[node] = (
            squareSum[node * 2] + squareSum[node * 2 + 1]
        ) % MOD;
    }
}