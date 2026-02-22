class Solution {
    public int binaryGap(int n) {
        int maxDistance = 0;
        int previousPosition = -1;
        int currentPosition = 0;
        
        while (n > 0) {
            if ((n & 1) == 1) {  // Check if current bit is 1
                if (previousPosition != -1) {
                    maxDistance = Math.max(maxDistance, currentPosition - previousPosition);
                }
                previousPosition = currentPosition;
            }
            
            n = n >> 1;   // Right shift
            currentPosition++;
        }
        
        return maxDistance;
    }
}