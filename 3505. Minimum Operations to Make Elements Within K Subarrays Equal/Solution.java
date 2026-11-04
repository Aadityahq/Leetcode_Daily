
import java.util.*;

class Solution {

    TreeMap<Long, Integer> left = new TreeMap<>();
    TreeMap<Long, Integer> right = new TreeMap<>();

    long leftSum = 0;
    long rightSum = 0;

    int leftSize = 0;
    int rightSize = 0;

    // Add an element to a TreeMap
    private void add(TreeMap<Long, Integer> map, long val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    // Remove an element from a TreeMap
    private void remove(TreeMap<Long, Integer> map, long val) {
        int count = map.get(val);

        if (count == 1) {
            map.remove(val);
        } else {
            map.put(val, count - 1);
        }
    }

    // Insert an element into the two halves
    private void insert(long val) {

        if (leftSize == 0 || val <= left.lastKey()) {
            add(left, val);
            leftSum += val;
            leftSize++;
        } else {
            add(right, val);
            rightSum += val;
            rightSize++;
        }

        balance();
    }

    // Remove an element from the two halves
    private void erase(long val) {

        if (left.containsKey(val)) {
            remove(left, val);
            leftSum -= val;
            leftSize--;
        } else {
            remove(right, val);
            rightSum -= val;
            rightSize--;
        }

        balance();
    }

    // Maintain median partition
    private void balance() {

        while (leftSize > rightSize + 1) {

            long val = left.lastKey();

            remove(left, val);
            leftSum -= val;
            leftSize--;

            add(right, val);
            rightSum += val;
            rightSize++;
        }

        while (leftSize < rightSize) {

            long val = right.firstKey();

            remove(right, val);
            rightSum -= val;
            rightSize--;

            add(left, val);
            leftSum += val;
            leftSize++;
        }
    }

    // Calculate minimum cost for the current window
    private long getCost() {

        long median = left.lastKey();

        return median * leftSize - leftSum
             + rightSum - median * rightSize;
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        int m = n - x + 1;

        long[] cost = new long[m];

        // Step 1: Calculate cost of every window

        for (int i = 0; i < x; i++) {
            insert(nums[i]);
        }

        for (int i = 0; i < m; i++) {

            cost[i] = getCost();

            if (i + x < n) {

                erase(nums[i]);
                insert(nums[i + x]);
            }
        }

        // Step 2: Dynamic Programming

        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        Arrays.fill(prev, 0);

        for (int j = 1; j <= k; j++) {

            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                // Choice 1: Skip current element
                curr[i] = curr[i - 1];

                // Choice 2: Select a window of size x
                if (i >= x) {

                    int start = i - x;

                    if (prev[start] != INF) {

                        curr[i] = Math.min(
                            curr[i],
                            prev[start] + cost[start]
                        );
                    }
                }
            }

            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}
