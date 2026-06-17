class Solution {
    public char processStr(String s, long k) {
        int n = s.length();
        long[] len = new long[n];

        // Forward pass: compute lengths
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            long prev = (i == 0) ? 0 : len[i - 1];

            if (c >= 'a' && c <= 'z') {
                len[i] = prev + 1;
            } else if (c == '*') {
                len[i] = Math.max(0, prev - 1);
            } else if (c == '#') {
                len[i] = prev * 2;
            } else { // c == '%'
                len[i] = prev;
            }
        }

        // Out of bounds
        if (k >= len[n - 1]) {
            return '.';
        }

        // Backward pass
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);

            long currLen = len[i];
            long prevLen = (i == 0) ? 0 : len[i - 1];

            if (c >= 'a' && c <= 'z') {
                // This character was appended at index prevLen
                if (k == prevLen) {
                    return c;
                }
            } else if (c == '#') {
                if (prevLen > 0) {
                    k %= prevLen;
                }
            } else if (c == '%') {
                k = currLen - 1 - k;
            }
            // '*' does not affect k
        }

        return '.';
    }
}