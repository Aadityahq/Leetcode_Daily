class Solution {
    public int largestInteger(int[] nums, int k) {

        int[] count = new int[51];

        // Generate every subarray of size k
        for (int i = 0; i <= nums.length - k; i++) {

            boolean[] present = new boolean[51];

            // Traverse the current subarray
            for (int j = i; j < i + k; j++) {
                present[nums[j]] = true;
            }

            // Count this subarray only once for each number
            for (int x = 0; x <= 50; x++) {
                if (present[x]) {
                    count[x]++;
                }
            }
        }

        // Find the largest number appearing in exactly one subarray
        for (int x = 50; x >= 0; x--) {
            if (count[x] == 1) {
                return x;
            }
        }

        return -1;
    }
}