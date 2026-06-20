import java.util.*;

class Solution {
    public int maxBuilding(int n, int[][] restrictions) {
        int m = restrictions.length;

        int[][] arr = new int[m + 2][2];

        arr[0] = new int[]{1, 0};

        for (int i = 0; i < m; i++) {
            arr[i + 1] = restrictions[i];
        }

        arr[m + 1] = new int[]{n, n - 1};

        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        // Left to right
        for (int i = 1; i < arr.length; i++) {
            int dist = arr[i][0] - arr[i - 1][0];
            arr[i][1] = Math.min(arr[i][1], arr[i - 1][1] + dist);
        }

        // Right to left
        for (int i = arr.length - 2; i >= 0; i--) {
            int dist = arr[i + 1][0] - arr[i][0];
            arr[i][1] = Math.min(arr[i][1], arr[i + 1][1] + dist);
        }

        long ans = 0;

        for (int i = 1; i < arr.length; i++) {
            long id1 = arr[i - 1][0];
            long h1 = arr[i - 1][1];

            long id2 = arr[i][0];
            long h2 = arr[i][1];

            long dist = id2 - id1;

            long peak = (h1 + h2 + dist) / 2;

            ans = Math.max(ans, peak);
        }

        return (int) ans;
    }
}