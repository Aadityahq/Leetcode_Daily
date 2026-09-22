class Solution {

    static class Node {
        int product;
        int[] prefix;

        Node(int k) {
            prefix = new int[k];
        }
    }

    int n;
    int k;
    int[] nums;
    Node[] tree;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.nums = nums;
        this.k = k;
        this.n = nums.length;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int q = 0; q < queries.length; q++) {

            int index = queries[q][0];
            int value = queries[q][1];
            int start = queries[q][2];
            int x = queries[q][3];

            // Point update
            nums[index] = value;
            update(1, 0, n - 1, index);

            // Query [start, n - 1]
            Node node = query(1, 0, n - 1, start, n - 1);

            result[q] = node.prefix[x];
        }

        return result;
    }

    // Build Segment Tree
    private void build(int node, int left, int right) {

        if (left == right) {

            tree[node] = new Node(k);

            int remainder = nums[left] % k;

            tree[node].product = remainder;
            tree[node].prefix[remainder] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        build(node * 2, left, mid);
        build(node * 2 + 1, mid + 1, right);

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Point update
    private void update(int node, int left, int right, int index) {

        if (left == right) {

            tree[node] = new Node(k);

            int remainder = nums[index] % k;

            tree[node].product = remainder;
            tree[node].prefix[remainder] = 1;

            return;
        }

        int mid = left + (right - left) / 2;

        if (index <= mid) {
            update(node * 2, left, mid, index);
        } else {
            update(node * 2 + 1, mid + 1, right, index);
        }

        tree[node] = merge(tree[node * 2], tree[node * 2 + 1]);
    }

    // Range query
    private Node query(
        int node,
        int left,
        int right,
        int queryLeft,
        int queryRight
    ) {

        // Completely outside
        if (right < queryLeft || left > queryRight) {
            return null;
        }

        // Completely inside
        if (queryLeft <= left && right <= queryRight) {
            return tree[node];
        }

        int mid = left + (right - left) / 2;

        Node leftNode = query(
            node * 2,
            left,
            mid,
            queryLeft,
            queryRight
        );

        Node rightNode = query(
            node * 2 + 1,
            mid + 1,
            right,
            queryLeft,
            queryRight
        );

        return mergeNullable(leftNode, rightNode);
    }

    // Merge two nodes where one/both may be null
    private Node mergeNullable(Node left, Node right) {

        if (left == null) {
            return right;
        }

        if (right == null) {
            return left;
        }

        return merge(left, right);
    }

    // Merge two valid nodes
    private Node merge(Node left, Node right) {

        Node result = new Node(k);

        // All prefixes completely inside the left segment
        for (int r = 0; r < k; r++) {
            result.prefix[r] += left.prefix[r];
        }

        // Prefixes that contain the entire left segment
        // and some prefix of the right segment
        for (int r = 0; r < k; r++) {

            int newRemainder =
                (left.product * r) % k;

            result.prefix[newRemainder] += right.prefix[r];
        }

        // Product of the complete segment
        result.product =
            (left.product * right.product) % k;

        return result;
    }
}