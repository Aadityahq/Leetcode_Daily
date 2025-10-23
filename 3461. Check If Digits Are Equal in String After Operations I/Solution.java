class Solution {
    public boolean hasSameDigits(String s) {
        // Convert string to list of digits
        List<Integer> digits = new ArrayList<>();
        for (char c : s.toCharArray()) {
            digits.add(c - '0');
        }

        // Keep reducing until only 2 digits remain
        while (digits.size() > 2) {
            List<Integer> next = new ArrayList<>();
            for (int i = 0; i < digits.size() - 1; i++) {
                int val = (digits.get(i) + digits.get(i + 1)) % 10;
                next.add(val);
            }
            digits = next; // Move to next stage
        }

        // Compare the final two digits
        return digits.get(0).equals(digits.get(1));
    }
}
