
class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {

        int n = nums.size();
        int ans = Integer.MAX_VALUE;

        // Try every starting index
        for (int i = 0; i < n; i++) {

            int sum = 0;

            // Try different subarray lengths
            for (int j = i; j < n; j++) {

                sum += nums.get(j);

                int len = j - i + 1;

                // Check if length is within [l, r]
                if (len >= l && len <= r) {

                    // We need the smallest positive sum
                    if (sum > 0) {
                        ans = Math.min(ans, sum);
                    }
                }

                // No need to check longer subarrays
                if (len >= r) {
                    break;
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
