class Solution {
    public boolean areSimilar(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;

        int shift = k % n; // important optimization

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (i % 2 == 0) {
                    // even row → left shift
                    int newCol = (j + shift) % n;
                    if (mat[i][j] != mat[i][newCol]) {
                        return false;
                    }
                } else {
                    // odd row → right shift
                    int newCol = (j - shift + n) % n;
                    if (mat[i][j] != mat[i][newCol]) {
                        return false;
                    }
                }

            }
        }

        return true;
    }
}