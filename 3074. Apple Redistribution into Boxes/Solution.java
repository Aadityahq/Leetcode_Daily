import java.util.Arrays;

class Solution {
    public int minimumBoxes(int[] apple, int[] capacity) {

        // Step 1: Calculate total apples
        int totalApples = 0;
        for (int a : apple) {
            totalApples += a;
        }

        // Step 2: Sort capacities in ascending order
        Arrays.sort(capacity);

        // Step 3: Pick largest boxes first
        int usedBoxes = 0;
        for (int i = capacity.length - 1; i >= 0 && totalApples > 0; i--) {
            totalApples -= capacity[i];
            usedBoxes++;
        }

        return usedBoxes;
    }
}
