class Solution {
    public int[] plusOne(int[] digits) {

        // Start from the last digit
        for (int i = digits.length - 1; i >= 0; i--) {

            // If digit is less than 9, just add 1 and return
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }

            // If digit is 9, it becomes 0 (carry continues)
            digits[i] = 0;
        }

        // If all digits were 9, we need a new array
        int[] result = new int[digits.length + 1];
        result[0] = 1; // Remaining digits are already 0 by default

        return result;
    }
}
