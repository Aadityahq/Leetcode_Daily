// class Solution {
//     int result = 0;
    
//     public int[] averageOfSubtree(TreeNode root) {
//         dfs(root);
//         return ans;
//     }

//     public int[] dfs(TreeNode node) {

//         if(node == null) 
//             return new int[]{0,0};

//         int[] left = dfs(node.left);
//         int[] right = dfs(node.right);

//         int sum = node.val + left[0] + right[0];
//         int count = 1 + left[1] + right[1];

//         if(node.val == sum / count) 
//             result++;

//         return new int[]{sum, count};
//     }
// }


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

    int ans = 0;

    public int averageOfSubtree(TreeNode root) {
        dfs(root);
        return ans;
    }

    private int[] dfs(TreeNode node) {

        // Base case
        if (node == null) {
            return new int[]{0, 0};
        }

        // Get information from left subtree
        int[] left = dfs(node.left);

        // Get information from right subtree
        int[] right = dfs(node.right);

        // Calculate current subtree sum
        int sum = node.val + left[0] + right[0];

        // Calculate current subtree node count
        int count = 1 + left[1] + right[1];

        // Check whether node value equals subtree average
        if (node.val == sum / count) {
            ans++;
        }

        // Return sum and count to parent
        return new int[]{sum, count};
    }
}