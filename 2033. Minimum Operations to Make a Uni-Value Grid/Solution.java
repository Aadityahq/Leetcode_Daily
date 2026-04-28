import java.util.*;

class Solution {
    public int minOperations(int[][] grid, int x) {

        List<Integer> nums = new ArrayList<>();

        // Step 1: Flatten grid
        for (int[] row : grid) {
            for (int num : row) {
                nums.add(num);
            }
        }

        // Step 2: Check feasibility
        int remainder = nums.get(0) % x;

        for (int num : nums) {
            if (num % x != remainder) {
                return -1;
            }
        }

        // Step 3: Sort
        Collections.sort(nums);

        // Step 4: Find median
        int median = nums.get(nums.size() / 2);

        // Step 5: Count operations
        int operations = 0;

        for (int num : nums) {
            operations += Math.abs(num - median) / x;
        }

        return operations;
    }
}