import java.util.HashSet;

class Solution {
    public int findFinalValue(int[] nums, int original) {
        // Step 1: Put all numbers into a HashSet for O(1) lookup
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        // Step 2: Keep doubling original while it exists in the set
        while (set.contains(original)) {
            original *= 2;
        }

        // Step 3: Return when original is no longer found
        return original;
    }
}
