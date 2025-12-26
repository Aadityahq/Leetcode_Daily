class Solution {
    public int bestClosingTime(String customers) {
        int n = customers.length();

        // Step 1: initial penalty = number of 'Y'
        int penalty = 0;
        for (char c : customers.toCharArray()) {
            if (c == 'Y') {
                penalty++;
            }
        }

        int minPenalty = penalty;
        int bestHour = 0;

        // Step 2: move closing hour from 1 to n
        for (int i = 0; i < n; i++) {
            if (customers.charAt(i) == 'Y') {
                penalty--; // shop open, customer came
            } else {
                penalty++; // shop open, no customer
            }

            if (penalty < minPenalty) {
                minPenalty = penalty;
                bestHour = i + 1;
            }
        }

        return bestHour;
    }
}
