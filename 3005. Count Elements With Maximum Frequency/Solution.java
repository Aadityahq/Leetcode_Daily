class Solution {
    public int maxFrequencyElements(int[] nums) {
        
        int n = nums.length;
        int maxFreq = 0;

        // Step 1: Find maximum frequency
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (nums[i] == nums[j]) {
                    count++;
                }
            }
            if (count > maxFreq) {
                maxFreq = count;
            }
        }

        // Step 2: Count total elements with max frequency (avoid duplicates)
        int result = 0;
        boolean[] visited = new boolean[n]; // to mark already counted numbers

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int count = 0;
                for (int j = 0; j < n; j++) {
                    if (nums[i] == nums[j]) {
                        count++;
                        visited[j] = true; // mark as processed
                    }
                }
                if (count == maxFreq) {
                    result += count;
                }
            }
        }

        return result;
    }
}
