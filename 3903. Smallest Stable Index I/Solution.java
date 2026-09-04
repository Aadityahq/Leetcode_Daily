class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int instabilityScore = 0;
        int maxVal = Integer.MIN_VALUE;

        for(int i = 0; i < nums.length; i++) {
            int minVal = Integer.MAX_VALUE;
            
            for(int j = 0; j <= i; j++) {
                if(nums[j]>maxVal)
                    maxVal = nums[j];
            }
            
            for(int j = i; j < nums.length; j++) {
                if(nums[j] < minVal)
                    minVal = nums[j];
            }
            
            instabilityScore = maxVal - minVal;

            if(instabilityScore <= k)
                return i;
        }
        return -1;
    }
}