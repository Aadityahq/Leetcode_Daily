public class Solution {
    public int countCollisions(String directions) {
        int n = directions.length();
        int left = 0, right = n - 1;

        // Skip all leading 'L' cars (they will never collide)
        while (left < n && directions.charAt(left) == 'L') {
            left++;
        }

        // Skip all trailing 'R' cars (they will never collide)
        while (right >= 0 && directions.charAt(right) == 'R') {
            right--;
        }

        int collisions = 0;

        // Count all moving cars ('L' or 'R') in the middle
        for (int i = left; i <= right; i++) {
            char c = directions.charAt(i);
            if (c == 'L' || c == 'R') {
                collisions++;
            }
        }

        return collisions;
    }
}
  