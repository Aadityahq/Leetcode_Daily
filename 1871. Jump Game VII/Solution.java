import java.util.*;

class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);

        boolean[] visited = new boolean[n];
        visited[0] = true;

        int farthest = 1;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Explore new valid range only
            int start = Math.max(current + minJump, farthest);
            int end = Math.min(current + maxJump, n - 1);

            for (int i = start; i <= end; i++) {
                if (s.charAt(i) == '0' && !visited[i]) {

                    // Reached destination
                    if (i == n - 1) {
                        return true;
                    }

                    visited[i] = true;
                    queue.offer(i);
                }
            }

            // Update farthest checked position
            farthest = end + 1;
        }

        return n == 1;
    }
}