import java.util.HashSet;

class Solution {
    public int repeatedNTimes(int[] nums) {
        HashSet<Integer> seen = new HashSet<>();
        
        for (int num : nums) {
            if (seen.contains(num)) {
                return num; // Found the repeated element
            }
            seen.add(num);
        }
        
        return -1; // Will never happen due to constraints
    }
}
