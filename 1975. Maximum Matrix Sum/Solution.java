class Solution {
    public long maxMatrixSum(int[][] matrix) {
        long totalSum = 0;
        int negativeCount = 0;
        int minAbs = Integer.MAX_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                int val = matrix[i][j];
                
                if (val < 0) {
                    negativeCount++;
                }
                
                int absVal = Math.abs(val);
                totalSum += absVal;
                minAbs = Math.min(minAbs, absVal);
            }
        }

        // If odd number of negatives, one smallest absolute value stays negative
        if (negativeCount % 2 == 1) {
            totalSum -= 2L * minAbs;
        }

        return totalSum;
    }
}
