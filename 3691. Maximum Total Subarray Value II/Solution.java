import java.util.*;

class Solution {

    private int[][] maxSt;
    private int[][] minSt;
    private int[] log2;

    private long value(int l, int r) {
        int len = r - l + 1;
        int p = log2[len];

        int mx = Math.max(
                maxSt[p][l],
                maxSt[p][r - (1 << p) + 1]
        );

        int mn = Math.min(
                minSt[p][l],
                minSt[p][r - (1 << p) + 1]
        );

        return (long) mx - mn;
    }

    public long maxTotalValue(int[] nums, int k) {
        int n = nums.length;

        log2 = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            log2[i] = log2[i / 2] + 1;
        }

        int LOG = log2[n] + 1;

        maxSt = new int[LOG][n];
        minSt = new int[LOG][n];

        for (int i = 0; i < n; i++) {
            maxSt[0][i] = nums[i];
            minSt[0][i] = nums[i];
        }

        for (int p = 1; p < LOG; p++) {
            int len = 1 << p;

            for (int i = 0; i + len <= n; i++) {
                maxSt[p][i] = Math.max(
                        maxSt[p - 1][i],
                        maxSt[p - 1][i + (len >> 1)]
                );

                minSt[p][i] = Math.min(
                        minSt[p - 1][i],
                        minSt[p - 1][i + (len >> 1)]
                );
            }
        }

        class Node {
            int l, r;
            long val;

            Node(int l, int r, long val) {
                this.l = l;
                this.r = r;
                this.val = val;
            }
        }

        PriorityQueue<Node> pq =
                new PriorityQueue<>((a, b) -> Long.compare(b.val, a.val));

        for (int l = 0; l < n; l++) {
            pq.offer(new Node(l, n - 1, value(l, n - 1)));
        }

        long ans = 0;

        while (k-- > 0) {
            Node cur = pq.poll();

            ans += cur.val;

            if (cur.r > cur.l) {
                int nr = cur.r - 1;
                pq.offer(new Node(
                        cur.l,
                        nr,
                        value(cur.l, nr)
                ));
            }
        }

        return ans;
    }
}