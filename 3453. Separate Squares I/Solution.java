class Solution {

    public double separateSquares(int[][] squares) {
        double low = Double.MAX_VALUE;
        double high = Double.MIN_VALUE;

        // Determine search bounds
        for (int[] sq : squares) {
            low = Math.min(low, sq[1]);
            high = Math.max(high, sq[1] + sq[2]);
        }

        // Binary search
        for (int i = 0; i < 60; i++) { // sufficient for 1e-5 precision
            double mid = (low + high) / 2.0;
            double below = 0.0;
            double above = 0.0;

            for (int[] sq : squares) {
                double y = sq[1];
                double l = sq[2];

                if (mid <= y) {
                    // Entire square above
                    above += l * l;
                } else if (mid >= y + l) {
                    // Entire square below
                    below += l * l;
                } else {
                    // Square is split
                    below += (mid - y) * l;
                    above += (y + l - mid) * l;
                }
            }

            if (below < above) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return low;
    }
}
