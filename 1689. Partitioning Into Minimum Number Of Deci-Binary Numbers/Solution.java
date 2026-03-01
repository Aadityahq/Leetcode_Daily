class Solution {
    public int minPartitions(String n) {
        int maxDigit = 0;
        
        for (char c : n.toCharArray()) {
            int digit = c - '0';
            maxDigit = Math.max(maxDigit, digit);
            
            // Optimization: if we find 9, we can stop early
            if (maxDigit == 9) {
                return 9;
            }
        }
        
        return maxDigit;
    }
}