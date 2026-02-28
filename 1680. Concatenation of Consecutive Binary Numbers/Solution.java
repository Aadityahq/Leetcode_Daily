class Solution {
    public int concatenatedBinary(int n) {
        long result = 0;
        int mod = 1000000007;
        int bitLength = 0;
        
        for (int i = 1; i <= n; i++) {
            
            // If i is power of 2, increase bit length
            if ((i & (i - 1)) == 0) {
                bitLength++;
            }
            
            // Shift result left by bitLength and add i
            result = ((result << bitLength) + i) % mod;
        }
        
        return (int) result;
    }
}