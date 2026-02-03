class Solution {
    public boolean isTrionic(int[] nums) {
        int n = nums.length;
        if (n < 3) return false;

        int i = 1;

        // 1️⃣ First strictly increasing part
        while (i < n && nums[i] > nums[i - 1]) {
            i++;
        }
        // Must have at least one increase
        if (i == 1 || i == n) return false;

        // 2️⃣ Strictly decreasing part
        while (i < n && nums[i] < nums[i - 1]) {
            i++;
        }
        // Must have at least one decrease
        if (i == n) return false;

        // 3️⃣ Final strictly increasing part
        while (i < n && nums[i] > nums[i - 1]) {
            i++;
        }

        // If we consumed entire array correctly
        return i == n;
    }
}
