class Solution {
    public boolean rotateString(String s, String goal) {
        
        // Lengths must be same
        if (s.length() != goal.length()) {
            return false;
        }

        // Combine string with itself
        String doubled = s + s;

        // Check if goal exists inside
        return doubled.contains(goal);
    }
}