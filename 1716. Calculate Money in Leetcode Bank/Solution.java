class Solution {
    public int totalMoney(int n) {
        int weeks = n / 7;   // full weeks
        int days = n % 7;    // remaining days
        int total = 0;
        
        // Add money from full weeks
        for (int i = 0; i < weeks; i++) {
            // Each full week = 7*(starting value) + 21
            total += 7 * (1 + i) + 21;
        }
        
        // Add money for remaining days
        int start = 1 + weeks;  // starting amount for next week
        for (int i = 0; i < days; i++) {
            total += start + i;
        }
        
        return total;
    }
}
