
class Solution {
    public int numOfSubarrays(int[] arr) {

        int MOD = 1_000_000_007;

        int odd = 0;
        int even = 1;

        int prefixSum = 0;
        int result = 0;

        for (int num : arr) {

            prefixSum += num;

            if (prefixSum % 2 == 0) {
                result += odd;
                even++;
            } else {
                result += even;
                odd++;
            }

            result %= MOD;
        }

        return result;
    }
}
