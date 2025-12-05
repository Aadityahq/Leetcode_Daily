class Solution {
    public int countPartitions(int[] nums) {
        int n = nums.length;

        // Step 1: Compute total sum
        int totalSum = 0;
        for (int x : nums) {
            totalSum += x;
        }

        // Step 2: If total sum is odd → 0 partitions possible
        if (totalSum % 2 != 0) {
            return 0;
        }

        // Step 3: All (n - 1) partitions are valid
        return n - 1;
    }
}
