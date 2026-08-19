import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        Map<Integer, Integer> map = new HashMap<>();

        // Store reserved seats of each row using a bitmask
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            // We only care about seats 2 to 9
            if (col >= 2 && col <= 9) {
                int bit = 1 << col;
                map.put(row, map.getOrDefault(row, 0) | bit);
            }
        }

        // Masks for the three possible groups
        int left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);
        int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);
        int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

        long answer = 0;

        // Rows having at least one relevant reserved seat
        for (int mask : map.values()) {

            boolean canUseLeft = (mask & left) == 0;
            boolean canUseMiddle = (mask & middle) == 0;
            boolean canUseRight = (mask & right) == 0;

            if (canUseLeft && canUseRight) {
                // Two non-overlapping groups
                answer += 2;
            } else if (canUseLeft || canUseMiddle || canUseRight) {
                // Only one group can be placed
                answer += 1;
            }
        }

        // Rows with no relevant reservations can always fit 2 groups
        answer += (long) (n - map.size()) * 2;

        return (int) answer;
    }
}