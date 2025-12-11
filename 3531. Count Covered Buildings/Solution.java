import java.util.*;

class Solution {
    public int countCoveredBuildings(int n, int[][] buildings) {
        // Maps to store all y's in each row and all x's in each column
        Map<Integer, List<Integer>> rowMap = new HashMap<>();
        Map<Integer, List<Integer>> colMap = new HashMap<>();

        for (int[] b : buildings) {
            rowMap.computeIfAbsent(b[0], k -> new ArrayList<>()).add(b[1]);
            colMap.computeIfAbsent(b[1], k -> new ArrayList<>()).add(b[0]);
        }

        // Sort all lists for binary search
        for (List<Integer> list : rowMap.values()) Collections.sort(list);
        for (List<Integer> list : colMap.values()) Collections.sort(list);

        int covered = 0;

        for (int[] b : buildings) {
            int x = b[0], y = b[1];

            List<Integer> rowList = rowMap.get(x);
            List<Integer> colList = colMap.get(y);

            // Check left: y' < y
            int posY = Collections.binarySearch(rowList, y);
            // posY gives index of y → left exists if index > 0
            boolean left = posY > 0;

            // Right: element > y exists
            boolean right = posY < rowList.size() - 1;

            // Check above: x' < x
            int posX = Collections.binarySearch(colList, x);
            boolean above = posX > 0;

            // Below: element > x exists
            boolean below = posX < colList.size() - 1;

            if (left && right && above && below) {
                covered++;
            }
        }

        return covered;
    }
}
