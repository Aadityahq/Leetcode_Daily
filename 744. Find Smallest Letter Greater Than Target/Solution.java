class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        int left = 0;
        int right = letters.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (letters[mid] <= target) {
                // Move right to find something greater
                left = mid + 1;
            } else {
                // Possible answer, try smaller index
                right = mid - 1;
            }
        }

        // If left goes out of bounds, wrap around
        return left < letters.length ? letters[left] : letters[0];
    }
}
