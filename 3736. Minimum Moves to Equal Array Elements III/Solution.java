
class Solution {
    public int minMoves(int[] nums) {

        int max = 0;
        int moves = 0;

        // Find the maximum element
        for (int num : nums) {
            max = Math.max(max, num);
        }

        // Calculate the moves required
        for (int num : nums) {
            moves += max - num;
        }

        return moves;
    }
}
