import java.util.*;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) {
                continue;
            }

            boolean valid = true;

            for (int start = 0; start < n; start += k) {
                int descents = 0;

                for (int j = 0; j < k; j++) {
                    int current = nums[start + j];
                    int next = nums[start + (j + 1) % k];

                    if (current > next) {
                        descents++;
                    }
                }

                if (descents > 1) {
                    valid = false;
                    break;
                }

                int min = nums[start];
                for (int j = 1; j < k; j++) {
                    min = Math.min(min, nums[start + j]);
                }

                int minSorted = sorted[start];
                if (min != minSorted) {
                    valid = false;
                    break;
                }

                int[] block = Arrays.copyOfRange(nums, start, start + k);
                Arrays.sort(block);

                for (int j = 0; j < k; j++) {
                    if (block[j] != sorted[start + j]) {
                        valid = false;
                        break;
                    }
                }

                if (!valid) {
                    break;
                }
            }

            if (valid) {
                ans += k;
            }
        }

        return ans;
    }
}