
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        // Step 1: Build the original tree
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        // Step 2: Track the nearest ancestor
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);

        int[] newParent = new int[n];
        int[] previous = new int[n];

        // Iterative DFS using enter/exit events
        int[] stack = new int[2 * n];
        int top = 0;

        stack[top++] = 0;

        while (top > 0) {

            int node = stack[--top];
            int ch = s.charAt(node) - 'a';

            // Exit event
            if (node < 0) {
                node = ~node;
                int c = s.charAt(node) - 'a';

                lastSeen[c] = previous[node];
                continue;
            }

            // Find closest matching ancestor
            int ancestor = lastSeen[ch];

            if (node == 0) {
                newParent[node] = -1;
            } else if (ancestor != -1) {
                newParent[node] = ancestor;
            } else {
                newParent[node] = parent[node];
            }

            // Save previous value before updating
            previous[node] = lastSeen[ch];

            // Current node becomes the latest ancestor
            lastSeen[ch] = node;

            // Schedule exit event
            stack[top++] = ~node;

            // Visit original children
            for (int child : tree[node]) {
                stack[top++] = child;
            }
        }

        // Step 3: Build the modified tree
        List<Integer>[] newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        // Step 4: Get parent-before-child traversal order
        int[] order = new int[n];
        int[] dfsStack = new int[n];

        int top2 = 0;
        int idx = 0;

        dfsStack[top2++] = 0;

        while (top2 > 0) {

            int node = dfsStack[--top2];

            order[idx++] = node;

            for (int child : newTree[node]) {
                dfsStack[top2++] = child;
            }
        }

        // Step 5: Calculate subtree sizes
        int[] answer = new int[n];
        Arrays.fill(answer, 1);

        // Process children before parents
        for (int i = n - 1; i > 0; i--) {

            int node = order[i];

            answer[newParent[node]] += answer[node];
        }

        return answer;
    }
}
