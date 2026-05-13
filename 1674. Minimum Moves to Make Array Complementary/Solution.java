class Solution {
    public int minMoves(int[] nums, int limit) {

        int n = nums.length;

        // Difference array
        int[] diff = new int[2 * limit + 2];

        for (int i = 0; i < n / 2; i++) {

            int a = nums[i];
            int b = nums[n - 1 - i];

            int low = Math.min(a, b) + 1;
            int high = Math.max(a, b) + limit;

            int sum = a + b;

            // Initially assume 2 moves for all sums
            diff[2] += 2;
            diff[2 * limit + 1] -= 2;

            // One move range
            diff[low] -= 1;
            diff[high + 1] += 1;

            // Zero move at exact sum
            diff[sum] -= 1;
            diff[sum + 1] += 1;
        }

        int answer = Integer.MAX_VALUE;
        int current = 0;

        // Check all possible target sums
        for (int s = 2; s <= 2 * limit; s++) {

            current += diff[s];

            answer = Math.min(answer, current);
        }

        return answer;
    }
}