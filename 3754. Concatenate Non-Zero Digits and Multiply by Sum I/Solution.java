class Solution {
    public long sumAndMultiply(int n) {
        String s = String.valueOf(n);

        long x = 0;
        long sum = 0;

        for (char ch : s.toCharArray()) {
            int digit = ch - '0';

            if (digit != 0) {
                x = x * 10 + digit;
                sum += digit;
            }
        }

        return x * sum;
    }
}