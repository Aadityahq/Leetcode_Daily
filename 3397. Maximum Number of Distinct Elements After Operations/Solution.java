import java.util.*;

class Solution {
    public int maxDistinctElements(int[] nums, int k) {
        Arrays.sort(nums);
        int count = 0;
        long last = Long.MIN_VALUE;  // last used distinct number

        for (int num : nums) {
            long minVal = (long) num - k;
            long maxVal = (long) num + k;

            // Assign smallest possible number > last
            long assign = Math.max(minVal, last + 1);

            // Only count if within range
            if (assign <= maxVal) {
                count++;
                last = assign;
            }
        }

        return count;
    }
}
