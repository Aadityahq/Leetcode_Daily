class Solution {
    public int gcd(int a, int b) {
        // Standard Euclidean algorithm
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public int gcdOfOddEvenSums(int n) {
        // Sum of first n odd numbers: n^2
        int sumOdd = n * n;
        // Sum of first n even numbers: n * (n + 1)
        int sumEven = n * (n + 1);
        return gcd(sumOdd, sumEven);
    }
}
