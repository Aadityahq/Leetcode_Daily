import java.util.Arrays;

class Solution {
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        
        long infinity = Integer.MAX_VALUE;
        
        long[][] dist = new long[26][26];

        for (int i = 0; i < 26; i++) {
            Arrays.fill(dist[i], infinity);
            dist[i][i] = 0;
        }

        for (int i = 0; i < original.length; ++i) {
            int u = original[i] - 'a';
            int v = changed[i] - 'a';
            dist[u][v] = Math.min(dist[u][v], (long) cost[i]);
        }

        for (int k = 0; k < 26; ++k) {
            for (int i = 0; i < 26; ++i) {
                for (int j = 0; j < 26; ++j) {
                    if (dist[i][k] < infinity && dist[k][j] < infinity) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        long totalCost = 0;
        int n = source.length();

        for (int i = 0; i < n; ++i) {
            int u = source.charAt(i) - 'a';
            int v = target.charAt(i) - 'a';

            if (dist[u][v] >= infinity) {
                return -1;
            }
            
            totalCost += dist[u][v];
        }

        return totalCost;
    }
}