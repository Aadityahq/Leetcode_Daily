class Solution {
    public int xorAfterQueries(int[] nums, int[][] queries) {
        int mod = 1_000_000_007;
        
        // Process each query
        for (int[] q : queries) {
            int l = q[0];
            int r = q[1];
            int k = q[2];
            int v = q[3];
            
            // Jump from l to r with step k
            for (int idx = l; idx <= r; idx += k) {
                long val = (long) nums[idx] * v;
                nums[idx] = (int) (val % mod);
            }
        }
        
        // Compute XOR of all elements
        int xor = 0;
        for (int num : nums) {
            xor ^= num;
        }
        
        return xor;
    }
}