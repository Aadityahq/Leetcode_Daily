class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;
        int stabilityScore = 0;
        int maxVal = Integer.MIN_VALUE;

        int[] suffixMin = new int[n];

        suffixMin[n-1] = nums[n-1];

        for(int i = n-2; i >= 0; i--) {
            suffixMin[i] = Math.min(suffixMin[i+1], nums[i]);
        }

        int prefixMax = nums[0];
        for(int i = 0; i < nums.length; i++) {
            prefixMax = Math.max(prefixMax, nums[i]);
            stabilityScore = prefixMax - suffixMin[i];

            if(stabilityScore <= k)
                return i;
        }
        return -1;
    }
}