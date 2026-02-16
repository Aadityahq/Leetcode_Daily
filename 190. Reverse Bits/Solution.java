public class Solution {
    public int reverseBits(int n) {
        int result = 0;
        
        for (int i = 0; i < 32; i++) {
            // Shift result to left to make space
            result = result << 1;
            
            // Add the last bit of n to result
            result = result | (n & 1);
            
            // Shift n to right to process next bit
            n = n >> 1;
        }
        
        return result;
    }
}
