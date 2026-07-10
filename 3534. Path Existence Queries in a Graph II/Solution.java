import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {

        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;

        Arrays.sort(order, (a, b) -> {
            if (nums[a] != nums[b]) return nums[a] - nums[b];
            return a - b;
        });

        int[] value = new int[n];
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            value[i] = nums[order[i]];
            rank[order[i]] = i;
        }

        // Connected components
        int[] comp = new int[n];
        int id = 0;
        comp[0] = 0;

        for (int i = 1; i < n; i++) {
            if (value[i] - value[i - 1] > maxDiff) id++;
            comp[i] = id;
        }

        // next[i] = farthest reachable index in one edge
        int[] next = new int[n];
        int j = 0;

        for (int i = 0; i < n; i++) {
            while (j + 1 < n && value[j + 1] - value[i] <= maxDiff) {
                j++;
            }
            next[i] = j;
        }

        int LOG = 18; // since 2^17 > 1e5
        int[][] up = new int[LOG][n];

        for (int i = 0; i < n; i++) up[0][i] = next[i];

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {

            int a = rank[queries[qi][0]];
            int b = rank[queries[qi][1]];

            if (a > b) {
                int t = a;
                a = b;
                b = t;
            }

            if (comp[a] != comp[b]) {
                ans[qi] = -1;
                continue;
            }

            if (a == b) {
                ans[qi] = 0;
                continue;
            }

            int cur = a;
            int jumps = 0;

            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][cur] < b) {
                    cur = up[k][cur];
                    jumps += 1 << k;
                }
            }

            ans[qi] = jumps + 1;
        }

        return ans;
    }
}