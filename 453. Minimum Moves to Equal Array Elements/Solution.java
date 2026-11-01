
class Solution {
    public int minMoves(int[] nums) {

        long min = nums[0];
        long sum = 0;

        for (int num : nums) {
            min = Math.min(min, num);
            sum += num;
        }

        return (int) (sum - (nums.length * min));
    }
}
