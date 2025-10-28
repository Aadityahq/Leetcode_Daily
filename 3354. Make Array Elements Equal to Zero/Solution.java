class Solution {
    public int countValidSelections(int[] nums) {
        int n = nums.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                // Try both directions: right (1) and left (-1)
                if (canMakeAllZero(nums, i, 1)) count++;
                if (canMakeAllZero(nums, i, -1)) count++;
            }
        }

        return count;
    }

    private boolean canMakeAllZero(int[] nums, int start, int dir) {
        int n = nums.length;
        int[] arr = nums.clone(); // Clone to simulate without changing the original
        int curr = start;

        while (curr >= 0 && curr < n) {
            if (arr[curr] == 0) {
                curr += dir; // Move in the same direction
            } else {
                arr[curr]--;  // Decrement by 1
                dir = -dir;   // Reverse direction
                curr += dir;  // Move in new direction
            }
        }

        // Check if all elements are 0
        for (int val : arr) {
            if (val != 0) return false;
        }
        return true;
    }
}
