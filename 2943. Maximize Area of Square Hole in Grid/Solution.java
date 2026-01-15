import java.util.Arrays;

class Solution {
    public int maximizeSquareHoleArea(int n, int m, int[] hBars, int[] vBars) {
        int maxHeight = getMaxGap(hBars);
        int maxWidth = getMaxGap(vBars);

        int side = Math.min(maxHeight, maxWidth);
        return side * side;
    }

    // Finds the maximum space formed by removing consecutive bars
    private int getMaxGap(int[] bars) {
        Arrays.sort(bars);

        int longest = 1;
        int current = 1;

        for (int i = 1; i < bars.length; i++) {
            if (bars[i] == bars[i - 1] + 1) {
                current++;
            } else {
                longest = Math.max(longest, current);
                current = 1;
            }
        }

        longest = Math.max(longest, current);
        return longest + 1; // +1 because x bars create x+1 space
    }
}
