class Solution {
    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        int n = bottomLeft.length;
        long maxArea = 0;

        // Check all pairs of rectangles
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                // Intersection boundaries
                int left = Math.max(bottomLeft[i][0], bottomLeft[j][0]);
                int right = Math.min(topRight[i][0], topRight[j][0]);
                int bottom = Math.max(bottomLeft[i][1], bottomLeft[j][1]);
                int top = Math.min(topRight[i][1], topRight[j][1]);

                int width = right - left;
                int height = top - bottom;

                if (width > 0 && height > 0) {
                    long side = Math.min(width, height);
                    long area = side * side;   // use long
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }
}
