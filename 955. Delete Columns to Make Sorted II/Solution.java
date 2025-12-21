class Solution {
    public int minDeletionSize(String[] strs) {
        int n = strs.length;
        int m = strs[0].length();

        // sorted[i] = true means strs[i] <= strs[i+1] is already confirmed
        boolean[] sorted = new boolean[n - 1];
        int deletions = 0;

        // Traverse column by column
        for (int col = 0; col < m; col++) {
            boolean needDelete = false;

            // Check all adjacent rows
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row]) {
                    char c1 = strs[row].charAt(col);
                    char c2 = strs[row + 1].charAt(col);

                    // Order violated → must delete column
                    if (c1 > c2) {
                        needDelete = true;
                        break;
                    }
                }
            }

            if (needDelete) {
                deletions++;
                continue; // skip updating sorted[]
            }

            // Update sorted[] where order is now fixed
            for (int row = 0; row < n - 1; row++) {
                if (!sorted[row]) {
                    char c1 = strs[row].charAt(col);
                    char c2 = strs[row + 1].charAt(col);
                    if (c1 < c2) {
                        sorted[row] = true;
                    }
                }
            }
        }

        return deletions;
    }
}
