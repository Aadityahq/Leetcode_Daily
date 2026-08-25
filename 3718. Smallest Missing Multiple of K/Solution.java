class Solution {
    public int missingMultiple(int[] nums, int k) {
        boolean[] present = new boolean[101];

        for(int num:nums) {
            present[num] = true;
        }

        int multiple = k;

        while(true) {
            if(multiple > 100 || !present[multiple])
                return multiple;

            multiple += k;
        }

    }
}