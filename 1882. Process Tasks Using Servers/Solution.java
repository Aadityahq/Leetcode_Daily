
import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        int[] ans = new int[m];

        // Available servers: [weight, index]
        PriorityQueue<int[]> available = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) {
                    return Integer.compare(a[0], b[0]);
                }
                return Integer.compare(a[1], b[1]);
            }
        );

        // Busy servers: [finishTime, weight, index]
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> {
                int cmp = Long.compare(a[0], b[0]);

                if (cmp != 0) return cmp;

                cmp = Long.compare(a[1], b[1]);

                if (cmp != 0) return cmp;

                return Long.compare(a[2], b[2]);
            }
        );

        // Initially, all servers are available.
        for (int i = 0; i < n; i++) {
            available.offer(new int[]{servers[i], i});
        }

        long time = 0;

        for (int j = 0; j < m; j++) {

            time = Math.max(time, j);

            // Release servers that have finished.
            while (!busy.isEmpty() && busy.peek()[0] <= time) {

                long[] s = busy.poll();

                available.offer(new int[]{
                    (int) s[1],
                    (int) s[2]
                });
            }

            // If no server is free, wait.
            if (available.isEmpty()) {

                time = busy.peek()[0];

                // Release all servers free at this time.
                while (!busy.isEmpty() && busy.peek()[0] <= time) {

                    long[] s = busy.poll();

                    available.offer(new int[]{
                        (int) s[1],
                        (int) s[2]
                    });
                }
            }

            // Select the best available server.
            int[] s = available.poll();

            int weight = s[0];
            int index = s[1];

            ans[j] = index;

            // Calculate finishing time.
            long finishTime = time + tasks[j];

            busy.offer(new long[]{
                finishTime,
                weight,
                index
            });
        }

        return ans;
    }
}
