class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        // Find indices of minimum and maximum elements
        int minIndex = 0;
        int maxIndex = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }

            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        // Case 1: Remove both from the front
        int removeFromFront = Math.max(minIndex, maxIndex) + 1;

        // Case 2: Remove both from the back
        int removeFromBack = n - Math.min(minIndex, maxIndex);

        // Case 3: Remove min from front and max from back
        int minFrontMaxBack = (minIndex + 1) + (n - maxIndex);

        // Case 4: Remove max from front and min from back
        int maxFrontMinBack = (maxIndex + 1) + (n - minIndex);

        return Math.min(
            Math.min(removeFromFront, removeFromBack),
            Math.min(minFrontMaxBack, maxFrontMinBack)
        );
    }
}