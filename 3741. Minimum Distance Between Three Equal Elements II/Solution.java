import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        // Step 1: Store indices
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
        
        int ans = Integer.MAX_VALUE;
        
        // Step 2: Process each value
        for (List<Integer> list : map.values()) {
            if (list.size() < 3) continue;
            
            // Check consecutive triplets
            for (int i = 0; i + 2 < list.size(); i++) {
                int dist = 2 * (list.get(i + 2) - list.get(i));
                ans = Math.min(ans, dist);
            }
        }
        
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}