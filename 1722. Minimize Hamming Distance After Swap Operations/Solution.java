import java.util.*;

class Solution {

    class DSU {
        int[] parent;

        DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++)
                parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]); // path compression
            return parent[x];
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px != py)
                parent[px] = py;
        }
    }

    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;

        DSU dsu = new DSU(n);

        // Step 1: Build components
        for (int[] swap : allowedSwaps) {
            dsu.union(swap[0], swap[1]);
        }

        // Step 2: Group indices by parent
        Map<Integer, List<Integer>> groups = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int parent = dsu.find(i);
            groups.computeIfAbsent(parent, k -> new ArrayList<>()).add(i);
        }

        int result = 0;

        // Step 3: Process each group
        for (List<Integer> group : groups.values()) {

            Map<Integer, Integer> freq = new HashMap<>();

            // count source elements
            for (int idx : group) {
                freq.put(source[idx], freq.getOrDefault(source[idx], 0) + 1);
            }

            // try matching target
            for (int idx : group) {
                if (freq.getOrDefault(target[idx], 0) > 0) {
                    freq.put(target[idx], freq.get(target[idx]) - 1);
                } else {
                    result++; // mismatch
                }
            }
        }

        return result;
    }
}