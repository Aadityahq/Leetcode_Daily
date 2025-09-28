import java.util.Arrays;

class Solution {
    public int largestPerimeter(int[] nums) {
        Arrays.sort(nums); // Step 1: Sort ascending
        int n = nums.length;

        // Step 2: Check from largest triplet
        for (int i = n - 1; i >= 2; i--) {
            if (nums[i - 2] + nums[i - 1] > nums[i]) {
                return nums[i] + nums[i - 1] + nums[i - 2]; // Step 3: Return perimeter
            }
        }
        return 0; // Step 4: No valid triangle
    }
}
