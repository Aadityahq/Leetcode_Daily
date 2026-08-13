class Solution {

    class Node {
        int prefix, suffix, best, len;
        char leftChar, rightChar;
    }

    private Node[] tree;
    private char[] arr;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        arr = s.toCharArray();

        tree = new Node[4 * n];
        build(1, 0, n - 1);

        int k = queryIndices.length;
        int[] ans = new int[k];

        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = tree[1].best;
        }

        return ans;
    }

    private void build(int idx, int l, int r) {
        tree[idx] = new Node();

        if (l == r) {
            tree[idx].prefix = 1;
            tree[idx].suffix = 1;
            tree[idx].best = 1;
            tree[idx].len = 1;
            tree[idx].leftChar = arr[l];
            tree[idx].rightChar = arr[l];
            return;
        }

        int mid = (l + r) / 2;

        build(idx * 2, l, mid);
        build(idx * 2 + 1, mid + 1, r);

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private void update(int idx, int l, int r, int pos, char ch) {
        if (l == r) {
            arr[pos] = ch;

            tree[idx].prefix = 1;
            tree[idx].suffix = 1;
            tree[idx].best = 1;
            tree[idx].leftChar = ch;
            tree[idx].rightChar = ch;
            return;
        }

        int mid = (l + r) / 2;

        if (pos <= mid) {
            update(idx * 2, l, mid, pos, ch);
        } else {
            update(idx * 2 + 1, mid + 1, r, pos, ch);
        }

        tree[idx] = merge(tree[idx * 2], tree[idx * 2 + 1]);
    }

    private Node merge(Node a, Node b) {
        Node res = new Node();

        res.len = a.len + b.len;
        res.leftChar = a.leftChar;
        res.rightChar = b.rightChar;

        // Prefix
        res.prefix = a.prefix;
        if (a.prefix == a.len && a.rightChar == b.leftChar) {
            res.prefix = a.len + b.prefix;
        }

        // Suffix
        res.suffix = b.suffix;
        if (b.suffix == b.len && a.rightChar == b.leftChar) {
            res.suffix = b.len + a.suffix;
        }

        // Best inside
        res.best = Math.max(a.best, b.best);

        // Crossing middle
        if (a.rightChar == b.leftChar) {
            res.best = Math.max(res.best, a.suffix + b.prefix);
        }

        return res;
    }
}