
import java.util.*;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;

        Deque<int[]> dq = new ArrayDeque<>();

        long cost = 0;
        long ans = 0;

        int j = n - 1;

        for (int i = n - 1; i >= 0; i--) {

            int num = nums[i];
            int count = 1;

            // Merge smaller elements into the current element.
            while (!dq.isEmpty() && dq.peekLast()[0] < num) {

                int[] last = dq.removeLast();

                int nextNum = last[0];
                int nextCount = last[1];

                // Cost of increasing these elements to num.
                cost += (long) (num - nextNum) * nextCount;

                count += nextCount;
            }

            // Add the current element as a new group.
            dq.offerLast(new int[]{num, count});

            // Reduce the window if operations exceed k.
            while (cost > k) {

                int[] first = dq.removeFirst();

                int rightmostNum = first[0];
                int rightmostCount = first[1];

                // Remove the contribution of the rightmost element.
                cost -= (long) (rightmostNum - nums[j]);
                j--;

                // Keep the remaining elements of this group.
                if (rightmostCount > 1) {
                    dq.offerFirst(
                        new int[]{rightmostNum, rightmostCount - 1}
                    );
                }
            }

            // Count all valid subarrays starting at i.
            ans += j - i + 1;
        }

        return ans;
    }
}
