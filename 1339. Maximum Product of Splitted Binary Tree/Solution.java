class Solution {
    private long totalSum = 0;
    private long maxProduct = 0;
    private static final int MOD = 1_000_000_007;

    public int maxProduct(TreeNode root) {
        // First DFS to compute total sum of tree
        totalSum = getTotalSum(root);

        // Second DFS to compute max product
        getSubtreeSum(root);

        return (int)(maxProduct % MOD);
    }

    private long getTotalSum(TreeNode node) {
        if (node == null) return 0;
        return node.val + getTotalSum(node.left) + getTotalSum(node.right);
    }

    private long getSubtreeSum(TreeNode node) {
        if (node == null) return 0;

        long left = getSubtreeSum(node.left);
        long right = getSubtreeSum(node.right);

        long subtreeSum = node.val + left + right;

        long product = subtreeSum * (totalSum - subtreeSum);
        maxProduct = Math.max(maxProduct, product);

        return subtreeSum;
    }
}
