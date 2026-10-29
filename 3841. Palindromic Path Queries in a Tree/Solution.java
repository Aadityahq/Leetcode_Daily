
import java.util.*;

class Solution {

    private int LOG;
    private int[][] up;
    private int[] depth, tin, tout, baseMask;
    private int[] bit;
    private int n;

    // Fenwick Tree: XOR update
    private void add(int index, int value) {
        while (index < bit.length) {
            bit[index] ^= value;
            index += index & -index;
        }
    }

    // XOR update on inclusive Euler Tour range [left, right]
    private void rangeXor(int left, int right, int value) {
        if (value == 0) {
            return;
        }

        add(left + 1, value);
        add(right + 2, value);
    }

    // Get the XOR of all updates affecting this position
    private int pointQuery(int index) {
        int result = 0;

        for (int i = index + 1; i > 0; i -= i & -i) {
            result ^= bit[i];
        }

        return result;
    }

    private int charMask(char c) {
        return 1 << (c - 'a');
    }

    // Lowest Common Ancestor using binary lifting
    private int lca(int u, int v) {

        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int k = 0; k < LOG; k++) {
            if ((diff & (1 << k)) != 0) {
                u = up[k][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int k = LOG - 1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }

        return up[0][u];
    }

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        this.n = n;

        // Build adjacency list
        List<Integer>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            graph[u].add(v);
            graph[v].add(u);
        }

        depth = new int[n];
        tin = new int[n];
        tout = new int[n];
        baseMask = new int[n];

        int[] parent = new int[n];
        int[] order = new int[n];
        int[] stack = new int[n];
        int[] subtreeSize = new int[n];

        Arrays.fill(parent, -1);

        // Iterative DFS to root the tree at node 0
        int top = 0;
        int timer = 0;

        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[--top];

            tin[u] = timer;
            order[timer++] = u;

            for (int v : graph[u]) {
                if (v == parent[u]) {
                    continue;
                }

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        // Calculate subtree sizes in reverse DFS order
        Arrays.fill(subtreeSize, 1);

        for (int i = n - 1; i > 0; i--) {
            int u = order[i];
            subtreeSize[parent[u]] += subtreeSize[u];
        }

        // Each subtree occupies a contiguous Euler Tour interval
        for (int u = 0; u < n; u++) {
            tout[u] = tin[u] + subtreeSize[u] - 1;
        }

        // Calculate initial root-to-node character masks
        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int u = order[i];

            if (u == 0) {
                baseMask[u] = charMask(chars[u]);
            } else {
                baseMask[u] =
                        baseMask[parent[u]] ^ charMask(chars[u]);
            }
        }

        // Build binary lifting table
        LOG = 1;

        while ((1 << LOG) <= n) {
            LOG++;
        }

        up = new int[LOG][n];
        up[0] = parent.clone();

        for (int k = 1; k < LOG; k++) {
            for (int u = 0; u < n; u++) {
                up[k][u] = up[k - 1][up[k - 1][u]];
            }
        }

        // Fenwick Tree uses 1-based indexing
        bit = new int[n + 2];

        List<Boolean> answer = new ArrayList<>();

        // Process queries in order
        for (String q : queries) {

            String[] parts = q.split(" ");

            if (parts[0].equals("update")) {

                int u = Integer.parseInt(parts[1]);
                char newChar = parts[2].charAt(0);

                int oldMask = charMask(chars[u]);
                int newMask = charMask(newChar);

                int delta = oldMask ^ newMask;

                // Update root masks of u and all its descendants
                rangeXor(tin[u], tout[u], delta);

                chars[u] = newChar;

            } else {

                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v);

                int maskU =
                        baseMask[u] ^ pointQuery(tin[u]);

                int maskV =
                        baseMask[v] ^ pointQuery(tin[v]);

                int pathMask = maskU
                        ^ maskV
                        ^ charMask(chars[ancestor]);

                // At most one character may have odd frequency
                answer.add(Integer.bitCount(pathMask) <= 1);
            }
        }

        return answer;
    }
}
