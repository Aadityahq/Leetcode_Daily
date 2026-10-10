
class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        int n = nums1.length;
        int[] diff = new int[n];

        int maxDiff = 0;
        long totalDiff = 0;

        // Step 1: Calculate absolute differences
        for (int i = 0; i < n; i++) {
            diff[i] = Math.abs(nums1[i] - nums2[i]);
            maxDiff = Math.max(maxDiff, diff[i]);
            totalDiff += diff[i];
        }

        // Step 2: Combine both operation limits
        long k = (long) k1 + k2;

        // If all differences can become zero
        if (k >= totalDiff) {
            return 0L;
        }

        // Step 3: Binary search for the optimal level
        int low = 0;
        int high = maxDiff;

        while (low < high) {
            int mid = low + (high - low) / 2;
            long operations = 0;

            for (int d : diff) {
                if (d > mid) {
                    operations += d - mid;
                }
            }

            if (operations <= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int level = low;
        long used = 0;
        long answer = 0;

        // Step 4: Reduce all differences to at most 'level'
        for (int d : diff) {
            if (d > level) {
                used += d - level;
                d = level;
            }

            answer += (long) d * d;
        }

        // Step 5: Use remaining operations to reduce
        // some differences from level to level - 1
        long remaining = k - used;
        answer -= remaining * (2L * level - 1);

        return answer;
    }
}
