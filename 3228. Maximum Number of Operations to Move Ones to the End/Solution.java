class Solution {
    public int maxOperations(String s) {
        int ans = 0;
        int ones = 0;
        int n = s.length();

        for (int i = 0; i < n; ++i) {
            char c = s.charAt(i);
            if (c == '1') {
                ones++;
            } else { // c == '0'
                // If this zero is the last character OR the next char is '1',
                // it's a "terminal" zero where previously seen ones can stop.
                if (i == n - 1 || s.charAt(i + 1) == '1') {
                    ans += ones;
                }
            }
        }

        return ans;
    }
}
