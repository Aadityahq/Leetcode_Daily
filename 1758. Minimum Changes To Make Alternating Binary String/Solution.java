class Solution {
    public int minOperations(String s) {
        int flipsStart0 = 0; // pattern 010101
        int flipsStart1 = 0; // pattern 101010
        
        for (int i = 0; i < s.length(); i++) {
            
            char expected0 = (i % 2 == 0) ? '0' : '1';
            char expected1 = (i % 2 == 0) ? '1' : '0';
            
            if (s.charAt(i) != expected0) {
                flipsStart0++;
            }
            
            if (s.charAt(i) != expected1) {
                flipsStart1++;
            }
        }
        
        return Math.min(flipsStart0, flipsStart1);
    }
}