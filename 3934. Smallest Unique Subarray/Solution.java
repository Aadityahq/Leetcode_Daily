
import java.util.*;

class Solution {

    public int smallestUniqueSubarray(int[] nums) {

        int n = nums.length;

        int[] sa = buildSuffixArray(nums);
        int[] lcp = buildLCP(nums, sa);

        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int answer = n;

        for (int i = 0; i < n; i++) {

            int pos = rank[i];

            int prevLCP = pos > 0 ? lcp[pos] : 0;
            int nextLCP = pos < n - 1 ? lcp[pos + 1] : 0;

            int requiredLength =
                Math.max(prevLCP, nextLCP) + 1;

            if (requiredLength <= n - i) {
                answer = Math.min(answer, requiredLength);
            }
        }

        return answer;
    }

    private int[] buildSuffixArray(int[] nums) {

        int n = nums.length;

        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] newRank = new int[n];
        int[] temp = new int[n];

        // Coordinate compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int[] unique = new int[n];
        int uniqueCount = 0;

        for (int x : sorted) {
            if (uniqueCount == 0 ||
                unique[uniqueCount - 1] != x) {

                unique[uniqueCount++] = x;
            }
        }

        int classes = uniqueCount;

        for (int i = 0; i < n; i++) {

            rank[i] = Arrays.binarySearch(
                unique, 0, uniqueCount, nums[i]
            );

            sa[i] = i;
        }

        // Doubling algorithm
        for (int k = 1; k < n; k *= 2) {

            countingSort(sa, temp, rank, k, classes, n);

            countingSort(temp, sa, rank, 0, classes, n);

            int newClasses = 1;

            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {

                int a = sa[i - 1];
                int b = sa[i];

                int aSecond = a + k < n
                    ? rank[a + k] : -1;

                int bSecond = b + k < n
                    ? rank[b + k] : -1;

                if (rank[a] != rank[b] ||
                    aSecond != bSecond) {

                    newClasses++;
                }

                newRank[b] = newClasses - 1;
            }

            int[] swap = rank;
            rank = newRank;
            newRank = swap;

            classes = newClasses;

            if (classes == n) {
                break;
            }
        }

        return sa;
    }

    private void countingSort(
        int[] input,
        int[] output,
        int[] rank,
        int offset,
        int classes,
        int n
    ) {

        int[] count = new int[classes + 1];

        for (int i = 0; i < n; i++) {

            int index = input[i] + offset;

            int key = index < n
                ? rank[index] + 1 : 0;

            count[key]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {

            int value = input[i];

            int index = value + offset;

            int key = index < n
                ? rank[index] + 1 : 0;

            output[--count[key]] = value;
        }
    }

    private int[] buildLCP(int[] nums, int[] sa) {

        int n = nums.length;

        int[] rank = new int[n];
        int[] lcp = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int common = 0;

        for (int i = 0; i < n; i++) {

            int pos = rank[i];

            if (pos == 0) {
                common = 0;
                continue;
            }

            int j = sa[pos - 1];

            while (i + common < n &&
                   j + common < n &&
                   nums[i + common] == nums[j + common]) {

                common++;
            }

            lcp[pos] = common;

            if (common > 0) {
                common--;
            }
        }

        return lcp;
    }
}
