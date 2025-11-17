class Solution {
    public boolean kLengthApart(int[] nums, int k) {
        int prev = -1;  // index of previous 1

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                if (prev != -1) {
                    // distance between current 1 and previous 1
                    if (i - prev - 1 < k)
                        return false;
                }
                prev = i; // update position of last 1
            }
        }
        return true;
    }
}
