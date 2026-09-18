import java.util.*;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {

        int n = arr.length;
        int INF = n + 1;

        // dp[i] = minimum length of a valid subarray
        // ending at or before index i
        int[] dp = new int[n];

        Arrays.fill(dp, INF);

        int left = 0;
        int sum = 0;
        int answer = INF;

        for (int right = 0; right < n; right++) {

            sum += arr[right];

            // Shrink the window if sum becomes too large
            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            // We found a subarray with sum = target
            if (sum == target) {

                int length = right - left + 1;

                // Check if there is a previous non-overlapping subarray
                if (left > 0 && dp[left - 1] != INF) {
                    answer = Math.min(answer,
                                      dp[left - 1] + length);
                }

                // Store the shortest valid subarray
                // seen up to this right index
                if (right == 0) {
                    dp[right] = length;
                } else {
                    dp[right] = Math.min(dp[right - 1], length);
                }

            } else {

                // No valid subarray ending at right,
                // so carry forward the previous best
                if (right > 0) {
                    dp[right] = dp[right - 1];
                }
            }
        }

        return answer == INF ? -1 : answer;
    }
}