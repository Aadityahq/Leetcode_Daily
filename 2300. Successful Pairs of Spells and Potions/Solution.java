import java.util.*;

class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        int n = spells.length;
        int m = potions.length;
        int[] result = new int[n];
        
        for (int i = 0; i < n; i++) {
            long minPotion = (success + spells[i] - 1) / spells[i]; // ceiling division
            int index = binarySearch(potions, minPotion);
            result[i] = m - index;
        }
        return result;
    }
    
    private int binarySearch(int[] potions, long target) {
        int left = 0, right = potions.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (potions[mid] < target)
                left = mid + 1;
            else
                right = mid;
        }
        return left;
    }
}
