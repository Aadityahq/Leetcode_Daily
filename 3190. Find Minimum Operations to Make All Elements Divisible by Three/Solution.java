class Solution {
    public int minimumOperations(int[] nums) {
        int operations = 0;

        for (int num : nums) {
            int r = num % 3;
            if (r == 1 || r == 2) {
                operations += 1;
            }
        }

        return operations;
    }
}
