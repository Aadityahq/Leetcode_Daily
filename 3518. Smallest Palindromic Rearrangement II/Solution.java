class Solution {
    private static final int LIMIT = 1_000_000;
    private List<Integer> primes = new ArrayList<>();

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];

        for (char c : s.toCharArray())
            freq[c - 'a']++;

        int[] half = new int[26];
        String mid = "";

        int halfLen = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];

            if ((freq[i] & 1) == 1)
                mid = String.valueOf((char) ('a' + i));
        }

        sieve(halfLen);

        if (countWays(half, halfLen) < k)
            return "";

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0)
                    continue;

                half[c]--;

                long ways = countWays(half, halfLen - pos - 1);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    break;
                } else {
                    k -= ways;
                    half[c]++;
                }
            }
        }

        StringBuilder ans = new StringBuilder(left);
        ans.append(mid);
        ans.append(new StringBuilder(left).reverse());

        return ans.toString();
    }

    private void sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];

        Arrays.fill(isPrime, true);

        for (int i = 2; i <= n; i++) {
            if (!isPrime[i])
                continue;

            primes.add(i);

            if ((long) i * i <= n) {
                for (int j = i * i; j <= n; j += i)
                    isPrime[j] = false;
            }
        }
    }

    private int factExp(int n, int p) {
        int e = 0;

        while (n > 0) {
            n /= p;
            e += n;
        }

        return e;
    }

    private long countWays(int[] cnt, int total) {

        long res = 1;

        for (int p : primes) {

            if (p > total)
                break;

            int exp = factExp(total, p);

            for (int x : cnt)
                exp -= factExp(x, p);

            while (exp-- > 0) {
                res *= p;

                if (res >= LIMIT)
                    return LIMIT;
            }
        }

        return res;
    }
}