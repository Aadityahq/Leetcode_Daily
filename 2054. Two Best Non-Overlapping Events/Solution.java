import java.util.*;

class Solution {
    public int maxTwoEvents(int[][] events) {
        int n = events.length;

        // Step 1: Sort events by start time
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));

        // Step 2: Suffix max array
        int[] suffixMax = new int[n];
        suffixMax[n - 1] = events[n - 1][2];

        for (int i = n - 2; i >= 0; i--) {
            suffixMax[i] = Math.max(suffixMax[i + 1], events[i][2]);
        }

        int answer = 0;

        // Step 3: For each event, binary search the next valid event
        for (int i = 0; i < n; i++) {
            int end = events[i][1];
            int value = events[i][2];

            // Case 1: Take only this event
            answer = Math.max(answer, value);

            // Case 2: Take this event + another non-overlapping event
            int nextIndex = binarySearch(events, end + 1);

            if (nextIndex < n) {
                answer = Math.max(answer, value + suffixMax[nextIndex]);
            }
        }

        return answer;
    }

    // Binary search to find first event with startTime >= target
    private int binarySearch(int[][] events, int target) {
        int left = 0, right = events.length - 1;
        int result = events.length;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (events[mid][0] >= target) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }
}
