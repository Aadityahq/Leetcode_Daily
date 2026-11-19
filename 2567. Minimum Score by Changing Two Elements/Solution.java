import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {

        // Step 1: Sort the array
        Arrays.sort(nums);

        int n = nums.length;

        // Step 2: Calculate all three possibilities

        int case1 = nums[n - 1] - nums[2];

        int case2 = nums[n - 2] - nums[1];

        int case3 = nums[n - 3] - nums[0];

        // Step 3: Return the minimum score
        return Math.min(case1,
               Math.min(case2, case3));
    }
}
