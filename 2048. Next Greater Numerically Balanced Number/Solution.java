class Solution {
    public int nextBeautifulNumber(int n) {
        int num = n + 1;
        while (true) {
            if (isBeautiful(num)) return num;
            num++;
        }
    }

    private boolean isBeautiful(int num) {
        int[] count = new int[10];
        int temp = num;

        // Count occurrences of digits
        while (temp > 0) {
            int digit = temp % 10;
            count[digit]++;
            temp /= 10;
        }

        // Check if numerically balanced
        for (int d = 0; d <= 9; d++) {
            if (count[d] != 0 && count[d] != d) return false;
        }

        return true;
    }
}
