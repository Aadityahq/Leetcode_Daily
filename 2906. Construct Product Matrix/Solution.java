class Solution {
    public int[][] constructProductMatrix(int[][] grid) {
        int mod = 12345;
        int n = grid.length;
        int m = grid[0].length;
        int size = n * m;

        // Step 1: Flatten
        int[] arr = new int[size];
        int idx = 0;
        for (int[] row : grid) {
            for (int val : row) {
                arr[idx++] = val % mod;
            }
        }

        // Step 2: Prefix
        int[] prefix = new int[size];
        prefix[0] = 1;
        for (int i = 1; i < size; i++) {
            prefix[i] = (prefix[i - 1] * arr[i - 1]) % mod;
        }

        // Step 3: Suffix
        int[] suffix = new int[size];
        suffix[size - 1] = 1;
        for (int i = size - 2; i >= 0; i--) {
            suffix[i] = (suffix[i + 1] * arr[i + 1]) % mod;
        }

        // Step 4: Build result
        int[][] result = new int[n][m];
        idx = 0;

        for (int i = 0; i < size; i++) {
            int val = (prefix[i] * suffix[i]) % mod;
            result[i / m][i % m] = val;
        }

        return result;
    }
}