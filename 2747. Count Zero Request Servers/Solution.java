
import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        // Step 1: Sort logs based on request time
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        // Step 2: Store queries with their original indices
        int m = queries.length;
        int[][] sortedQueries = new int[m][2];

        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        // Step 3: Sort queries based on query time
        Arrays.sort(sortedQueries,
            (a, b) -> Integer.compare(a[0], b[0]));

        // Step 4: Maintain frequency of server requests
        Map<Integer, Integer> map = new HashMap<>();

        int[] ans = new int[m];

        int left = 0;
        int right = 0;

        // Step 5: Process queries using sliding window
        for (int[] q : sortedQueries) {

            int start = q[0] - x;
            int end = q[0];

            // Add logs whose time is <= end
            while (right < logs.length && logs[right][1] <= end) {

                int server = logs[right][0];

                map.put(server, map.getOrDefault(server, 0) + 1);

                right++;
            }

            // Remove logs whose time is < start
            while (left < right && logs[left][1] < start) {

                int server = logs[left][0];

                map.put(server, map.get(server) - 1);

                if (map.get(server) == 0) {
                    map.remove(server);
                }

                left++;
            }

            // Servers with no requests = total - active servers
            ans[q[1]] = n - map.size();
        }

        return ans;
    }
}
