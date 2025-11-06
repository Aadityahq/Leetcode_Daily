import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

class Solution {
    static class DSU {
        int[] parent;
        DSU(int n) {
            parent = new int[n + 1];
            for (int i = 1; i <= n; i++) parent[i] = i;
        }
        int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        void union(int a, int b) {
            int pa = find(a), pb = find(b);
            if (pa != pb) parent[pb] = pa;
        }
    }

    public int[] processQueries(int c, int[][] connections, int[][] queries) {
        DSU dsu = new DSU(c);

        // Step 1: Build connected components
        for (int[] conn : connections) {
            dsu.union(conn[0], conn[1]);
        }

        // Step 2: Map component → TreeSet of online stations
        Map<Integer, TreeSet<Integer>> gridOnline = new HashMap<>();
        for (int i = 1; i <= c; i++) {
            int comp = dsu.find(i);
            gridOnline.putIfAbsent(comp, new TreeSet<>());
        }

        for (int i = 1; i <= c; i++) {
            gridOnline.get(dsu.find(i)).add(i); // initially all online
        }

        // Step 3: Track offline status
        boolean[] offline = new boolean[c + 1];
        List<Integer> ansList = new ArrayList<>();

        // Step 4: Process each query
        for (int[] q : queries) {
            int type = q[0], x = q[1];
            int comp = dsu.find(x);

            if (type == 1) { // maintenance check
                if (!offline[x]) {
                    ansList.add(x);
                } else {
                    TreeSet<Integer> set = gridOnline.get(comp);
                    ansList.add(set.isEmpty() ? -1 : set.first());
                }
            } else { // type == 2 → station goes offline
                if (!offline[x]) {
                    offline[x] = true;
                    gridOnline.get(comp).remove(x);
                }
            }
        }

        // Convert result list to array for LeetCode return type
        int[] res = new int[ansList.size()];
        for (int i = 0; i < ansList.size(); i++) res[i] = ansList.get(i);
        return res;
    }
}
