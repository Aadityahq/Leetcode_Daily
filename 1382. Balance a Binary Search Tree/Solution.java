import java.util.ArrayList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {

    // List to store inorder traversal
    private List<Integer> inorder = new ArrayList<>();

    public TreeNode balanceBST(TreeNode root) {
        // Step 1: Get sorted values via inorder traversal
        inorderTraversal(root);

        // Step 2: Build balanced BST from sorted list
        return buildBalancedBST(0, inorder.size() - 1);
    }

    // Inorder traversal to collect values
    private void inorderTraversal(TreeNode root) {
        if (root == null) return;

        inorderTraversal(root.left);
        inorder.add(root.val);
        inorderTraversal(root.right);
    }

    // Build balanced BST from sorted list
    private TreeNode buildBalancedBST(int left, int right) {
        if (left > right) return null;

        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(inorder.get(mid));

        node.left = buildBalancedBST(left, mid - 1);
        node.right = buildBalancedBST(mid + 1, right);

        return node;
    }
}
