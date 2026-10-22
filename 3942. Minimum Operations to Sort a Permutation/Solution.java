class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        int zeroIndex = -1;
        int maxIndex = -1;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zeroIndex = i;
            }
            if (nums[i] == n - 1) {
                maxIndex = i;
            }
        }

        int answer = Integer.MAX_VALUE;

        // Case 1: nums is a rotation of [0, 1, 2, ..., n - 1].
        boolean increasingRotation = true;
        for (int i = 0; i < n; i++) {
            int expected = (nums[0] + i) % n;
            if (nums[i] != expected) {
                increasingRotation = false;
                break;
            }
        }

        if (increasingRotation) {
            answer = Math.min(answer, Math.min(zeroIndex, n - zeroIndex + 2));
        }

        // Case 2: nums is a rotation of [n - 1, n - 2, ..., 0].
        boolean decreasingRotation = true;
        for (int i = 0; i < n; i++) {
            int expected = (nums[0] - i + n) % n;
            if (nums[i] != expected) {
                decreasingRotation = false;
                break;
            }
        }

        if (decreasingRotation) {
            int p = (n - maxIndex) % n;
            answer = Math.min(answer, 1 + Math.min(p, n - p));
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}