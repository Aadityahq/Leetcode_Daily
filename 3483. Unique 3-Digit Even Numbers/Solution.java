class Solution {
    public int totalNumbers(int[] digits) {

        Set<Integer> uniqueNumbers = new HashSet<>();

        for (int i = 0; i < digits.length; i++) {

            // First digit cannot be 0
            if (digits[i] == 0) {
                continue;
            }

            for (int j = 0; j < digits.length; j++) {

                // Cannot reuse the same copy
                if (j == i) {
                    continue;
                }

                for (int k = 0; k < digits.length; k++) {

                    // Cannot reuse the same copy
                    if (k == i || k == j) {
                        continue;
                    }

                    // Last digit must be even
                    if (digits[k] % 2 != 0) {
                        continue;
                    }

                    int number = digits[i] * 100
                              + digits[j] * 10
                              + digits[k];

                    uniqueNumbers.add(number);
                }
            }
        }

        return uniqueNumbers.size();
    }
}