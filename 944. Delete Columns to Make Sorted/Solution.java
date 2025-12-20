class Solution {
    public int minDeletionSize(String[] strs) {
        int rows = strs.length;
        int cols = strs[0].length();
        int deleteCount = 0;

        // Check each column
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < rows - 1; row++) {
                // If column is not sorted
                if (strs[row].charAt(col) > strs[row + 1].charAt(col)) {
                    deleteCount++;
                    break; // No need to check further rows for this column
                }
            }
        }

        return deleteCount;
    }
}
