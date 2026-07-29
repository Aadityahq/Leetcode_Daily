## Intuition

Since `s` is **already a palindrome**, every valid rearrangement must also be a palindrome.

A palindrome is completely determined by its **left half**:

* If a character appears `4` times, we put `2` copies in the left half and `2` in the right half.
* If one character appears an odd number of times, it becomes the middle character.

For example:

```
s = "aabccbaa"

Counts:
a = 4
b = 2
c = 2

Left half = "aabc"
Middle = ""
Right half = reverse(left)
```

So instead of generating whole palindromes, we only need to find the **k-th lexicographically smallest permutation of the left half**.

---

# Key Observation

Suppose the left-half frequencies are

```
a : 2
b : 1
c : 1
```

The number of different left halves is

[
\frac{4!}{2!\times1!\times1!}=12
]

Every left-half permutation produces exactly one palindrome.

Therefore,

> **The number of palindromic rearrangements equals the number of distinct permutations of the left half.**

---

# Greedy Construction

We build the left half from left to right.

At every position:

* Try putting `'a'`
* Count how many palindromes are possible
* If that count is at least `k`, keep `'a'`
* Otherwise subtract that count from `k` and try `'b'`
* Continue until every position is fixed.

This is identical to finding the k-th lexicographical permutation.

---

## Example

```
s = "abba"
k = 2
```

Half counts

```
a : 1
b : 1
```

Left length = 2

### Position 1

Try `a`

Remaining

```
b
```

Only

```
ab
```

1 palindrome.

Since

```
k = 2
```

Skip it.

```
k = 2 - 1 = 1
```

Try `b`

Keep it.

Current left

```
b
```

---

### Position 2

Only `a` remains.

Left half

```
ba
```

Palindrome

```
baab
```

Answer.

---

# Counting Permutations

If remaining frequencies are

```
c1,c2,c3...
```

and total remaining letters are

```
m
```

then

[
\text{count}
============

\frac{m!}
{c_1!c_2!\cdots}
]

The values become enormous (`5000!`), but notice:

```
k ≤ 10^6
```

We never need an exact number larger than `10^6`.

So while multiplying prime powers, we stop as soon as the answer exceeds `k`.

This keeps the computation efficient.

---

# Algorithm

1. Count frequencies.
2. Compute half frequencies.
3. Find the middle character (if any).
4. Build the left half greedily.
5. For every candidate character:

   * temporarily use one copy
   * count remaining permutations
   * compare with `k`
6. Mirror the left half to obtain the palindrome.

---

# Complexity

Let

* `n = s.length`
* Half length = `n/2`

There are only **26 letters**.

* Counting permutations uses all primes up to `5000` (about **669 primes**).

Overall complexity:

* **Time:** `O((n/2) × 26 × number_of_primes)` ≈ `O(n)`
* **Space:** `O(number_of_primes)`

---

# Java Solution

```java
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
```

### Why this works

* A palindrome is uniquely determined by its left half.
* The number of valid palindromes equals the multinomial count of the left-half characters.
* At each position, we greedily test letters in lexicographical order.
* If choosing a letter produces at least `k` palindromes, that letter belongs in the answer.
* Otherwise, we skip all those palindromes by subtracting their count from `k` and try the next letter.

This guarantees that the constructed palindrome is exactly the **k-th lexicographically smallest** palindromic rearrangement.
