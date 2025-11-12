class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int overallGcd = nums[0];

        // Step 1: Compute overall GCD of array
        for (int num : nums) {
            overallGcd = gcd(overallGcd, num);
        }

        // If overall gcd > 1, impossible to get 1
        if (overallGcd != 1) return -1;

        // Step 2: Check if there's already a 1
        int countOnes = 0;
        for (int num : nums) {
            if (num == 1) countOnes++;
        }
        if (countOnes > 0) {
            // Each non-1 element requires one operation
            return n - countOnes;
        }

        // Step 3: Find smallest subarray with gcd = 1
        int minLen = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int currentGcd = nums[i];
            for (int j = i + 1; j < n; j++) {
                currentGcd = gcd(currentGcd, nums[j]);
                if (currentGcd == 1) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // no need to extend further
                }
            }
        }

        // Step 4: Total operations = (L - 1) + (n - 1)
        return (minLen - 1) + (n - 1);
    }

    // Helper function to compute GCD
    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
}
