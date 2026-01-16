import java.util.*;

class Solution {
    public int maximizeSquareArea(int m, int n, int[] hFences, int[] vFences) {
        final int MOD = 1_000_000_007;

        // Add boundary fences
        int[] h = new int[hFences.length + 2];
        int[] v = new int[vFences.length + 2];

        h[0] = 1;
        h[h.length - 1] = m;
        for (int i = 0; i < hFences.length; i++) {
            h[i + 1] = hFences[i];
        }

        v[0] = 1;
        v[v.length - 1] = n;
        for (int i = 0; i < vFences.length; i++) {
            v[i + 1] = vFences[i];
        }

        // Sort fences
        Arrays.sort(h);
        Arrays.sort(v);

        // Store all possible vertical gaps
        Set<Integer> verticalGaps = new HashSet<>();
        for (int i = 0; i < v.length; i++) {
            for (int j = i + 1; j < v.length; j++) {
                verticalGaps.add(v[j] - v[i]);
            }
        }

        long maxSide = 0;

        // Check horizontal gaps from largest to smallest
        for (int i = 0; i < h.length; i++) {
            for (int j = i + 1; j < h.length; j++) {
                int gap = h[j] - h[i];
                if (verticalGaps.contains(gap)) {
                    maxSide = Math.max(maxSide, gap);
                }
            }
        }

        if (maxSide == 0) return -1;

        long area = (maxSide * maxSide) % MOD;
        return (int) area;
    }
}
