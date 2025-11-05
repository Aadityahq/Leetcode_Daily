class Solution {
    public int minCost(String colors, int[] neededTime) {
        int totalTime = 0;
        int n = colors.length();
        
        for (int i = 1; i < n; i++) {
            if (colors.charAt(i) == colors.charAt(i - 1)) {
                // Remove the smaller one and keep the larger one
                totalTime += Math.min(neededTime[i], neededTime[i - 1]);
                
                // Update the current balloon’s time to the max one (as if we kept the larger)
                neededTime[i] = Math.max(neededTime[i], neededTime[i - 1]);
            }
        }
        return totalTime;
    }
}
