class Solution {
    public int minOperations(int[] nums, int x) {

        int n = nums.length;

        // Calculate total sum
        long total = 0;

        for (int num : nums) {
            total += num;
        }

        // Sum of the subarray that we want to keep
        long target = total - x;

        // If target is negative, it is impossible
        if (target < 0) {
            return -1;
        }

        // If target is 0, we have to remove every element
        if (target == 0) {
            return n;
        }

        int left = 0;
        long sum = 0;
        int maxLength = -1;

        for (int right = 0; right < n; right++) {

            sum += nums[right];

            // Shrink window if sum becomes greater than target
            while (left <= right && sum > target) {
                sum -= nums[left];
                left++;
            }

            // Found a subarray with required sum
            if (sum == target) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }

        // No valid subarray found
        if (maxLength == -1) {
            return -1;
        }

        // Remove everything outside the longest subarray
        return n - maxLength;
    }
}