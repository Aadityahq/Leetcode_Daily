import java.util.*;

class Solution {

    private static final int MAX = 4;

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        /*
         * arr[i] = {start, end, weight, originalIndex}
         */
        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        // Sort by starting position
        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        /*
         * next[i] = first interval j such that
         * arr[j][0] > arr[i][1]
         */
        int[] next = new int[n];

        for (int i = 0; i < n; i++) {
            next[i] = findNext(arr, i);
        }

        /*
         * dp[i][k] = maximum score we can get from
         * index i onward using at most k intervals.
         */
        long[][] dp = new long[n + 1][MAX + 1];

        /*
         * choice[i][k] stores the lexicographically smallest
         * answer that produces dp[i][k].
         *
         * Since at most 4 indices are required and each index
         * is < 50000, each index can fit in 16 bits.
         */
        long[][] choice = new long[n + 1][MAX + 1];

        // Fill DP from right to left
        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= MAX; k++) {

                // Option 1: Skip current interval
                long skipScore = dp[i + 1][k];
                long skipChoice = choice[i + 1][k];

                // Option 2: Take current interval
                long takeScore =
                        arr[i][2] + dp[next[i]][k - 1];

                long takeChoice =
                        prepend(arr[i][3], choice[next[i]][k - 1]);

                // Choose the better score
                if (takeScore > skipScore) {

                    dp[i][k] = takeScore;
                    choice[i][k] = takeChoice;

                } else if (takeScore < skipScore) {

                    dp[i][k] = skipScore;
                    choice[i][k] = skipChoice;

                } else {

                    // Same score -> choose lexicographically smaller
                    dp[i][k] = takeScore;

                    if (lexicographicallySmaller(
                            takeChoice, skipChoice)) {

                        choice[i][k] = takeChoice;

                    } else {
                        choice[i][k] = skipChoice;
                    }
                }
            }
        }

        return decode(choice[0][MAX]);
    }

    /*
     * Find first interval whose start > current interval's end.
     */
    private int findNext(int[][] arr, int i) {

        int end = arr[i][1];

        int left = i + 1;
        int right = arr.length;

        while (left < right) {

            int mid = left + (right - left) / 2;

            if (arr[mid][0] > end) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    /*
     * Put an index at the beginning of the encoded answer.
     */
    private long prepend(int index, long key) {

        return (key << 16) | (index + 1L);
    }

    /*
     * Compare two encoded arrays lexicographically.
     */
    private boolean lexicographicallySmaller(long a, long b) {

        for (int pos = 3; pos >= 0; pos--) {

            int shift = pos * 16;

            int x = (int) ((a >>> shift) & 0xFFFF);
            int y = (int) ((b >>> shift) & 0xFFFF);

            if (x != y) {
                return x < y;
            }
        }

        return false;
    }

    /*
     * Convert the encoded long back into int[].
     */
    private int[] decode(long key) {

        int[] result = new int[MAX];
        int count = 0;

        for (int pos = 3; pos >= 0; pos--) {

            int shift = pos * 16;

            int value = (int) ((key >>> shift) & 0xFFFF);

            if (value == 0) {
                break;
            }

            result[count++] = value - 1;
        }

        return Arrays.copyOf(result, count);
    }
}