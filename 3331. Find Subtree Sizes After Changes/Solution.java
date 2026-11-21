
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        // Build original tree
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        // Track nearest matching ancestor
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);

        int[] newParent = new int[n];
        int[] previous = new int[n];

        int[] stack = new int[2 * n];
        int top = 0;

        stack[top++] = 0;

        while (top > 0) {

            int node = stack[--top];

            // Check exit event FIRST
            if (node < 0) {

                node = ~node;

                int ch = s.charAt(node) - 'a';

                lastSeen[ch] = previous[node];

                continue;
            }

            int ch = s.charAt(node) - 'a';

            // Find nearest matching ancestor
            int ancestor = lastSeen[ch];

            if (node == 0) {
                newParent[node] = -1;
            } else if (ancestor != -1) {
                newParent[node] = ancestor;
            } else {
                newParent[node] = parent[node];
            }

            // Save previous ancestor
            previous[node] = lastSeen[ch];

            // Update last seen
            lastSeen[ch] = node;

            // Add exit event
            stack[top++] = ~node;

            // Add children
            for (int child : tree[node]) {
                stack[top++] = child;
            }
        }

        // Build modified tree
        List<Integer>[] newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        // Get traversal order
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

        // Calculate subtree sizes
        int[] answer = new int[n];
        Arrays.fill(answer, 1);

        for (int i = n - 1; i > 0; i--) {

            int node = order[i];

            answer[newParent[node]] += answer[node];
        }

        return answer;
    }
}
