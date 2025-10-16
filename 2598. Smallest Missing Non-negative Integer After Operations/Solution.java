class Solution {
    public int findSmallestInteger(int[] nums, int value) {
        int[] modCount = new int[value];

        for (int num : nums) {
            int mod = ((num % value) + value) % value; // handle negative remainders
            modCount[mod]++;
        }

        int mex = 0;
        while (true) {
            int mod = mex % value;
            if (modCount[mod] == 0) break;
            modCount[mod]--;
            mex++;
        }

        return mex;
    }
}
