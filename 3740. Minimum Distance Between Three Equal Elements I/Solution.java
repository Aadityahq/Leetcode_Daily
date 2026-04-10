import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        // Step 1: Store indices
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
        
        int minDist = Integer.MAX_VALUE;
        
        // Step 2: Process each value
        for (List<Integer> indices : map.values()) {
            if (indices.size() < 3) continue;
            
            // Step 3: Check consecutive triplets
            for (int i = 0; i <= indices.size() - 3; i++) {
                int a = indices.get(i);
                int c = indices.get(i + 2);
                
                int dist = 2 * (c - a);
                minDist = Math.min(minDist, dist);
            }
        }
        
        return minDist == Integer.MAX_VALUE ? -1 : minDist;
    }
}