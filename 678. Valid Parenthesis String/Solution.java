class Solution {
    public boolean checkValidString(String s) {
        int minOpen = 0;
        int maxOpen = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                minOpen++;
                maxOpen++;
            } 
            else if (ch == ')') {
                minOpen--;
                maxOpen--;
            } 
            else { // '*'
                minOpen--;  // Treat '*' as ')'
                maxOpen++;  // Treat '*' as '('
            }

            // Even the maximum possible opens are negative
            if (maxOpen < 0) {
                return false;
            }

            // Minimum cannot be negative
            minOpen = Math.max(minOpen, 0);
        }

        return minOpen == 0;
    }
}