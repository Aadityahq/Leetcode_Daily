
class Solution {
    public int maxSum(List<Integer> nums, int k) {
        final long MOD = 1_000_000_007L;

        int[] bitCount = new int[32];

        // Step 1: Count set bits at every position
        for (int num : nums) {
            for (int bit = 0; bit < 32; bit++) {
                if ((num & (1 << bit)) != 0) {
                    bitCount[bit]++;
                }
            }
        }

        long ans = 0;

        // Step 2: Construct k maximum numbers
        for (int i = 0; i < k; i++) {
            long x = 0;

            for (int bit = 0; bit < 32; bit++) {
                if (bitCount[bit] > 0) {
                    x |= (1L << bit);
                    bitCount[bit]--;
                }
            }

            // Step 3: Add the square modulo MOD
            ans = (ans + x * x % MOD) % MOD;
        }

        return (int) ans;
    }
}
