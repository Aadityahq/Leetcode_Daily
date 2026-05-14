import java.util.Arrays;

class Solution {
    public boolean isGood(int[] nums) {

        Arrays.sort(nums);

        int n = nums.length;

        // Check first n-1 elements
        for (int i = 0; i < n - 1; i++) {

            // Expected value should be i+1
            if (nums[i] != i + 1) {
                return false;
            }
        }

        // Last element should also be n-1
        return nums[n - 1] == n - 1;
    }
}