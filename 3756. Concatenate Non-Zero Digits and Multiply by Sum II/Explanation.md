
# Intuition

For every query **[l, r]**, we need to:

1. Take the substring `s[l...r]`.
2. Remove all `0`s.
3. Concatenate the remaining digits to form a number `x`.
4. Find the sum of digits of `x`.
5. Return `(x × sum) % (10^9 + 7)`.

A brute-force solution scans every substring for every query.

* Time Complexity = **O(N × Q)**.
* Here, `N, Q ≤ 10^5`, so this becomes **10^10 operations**, which will **TLE**.

We need a faster approach.

---

# Key Observation

Suppose the non-zero digits in the entire string are

```
1 2 3 4 5
```

Instead of working on the original string every time, we preprocess these digits.

For each non-zero digit we store:

* its original index
* prefix sum of digits
* prefix value (concatenated number)

Example

```
s = "10203004"

Original Index : 0 1 2 3 4 5 6 7
Character      : 1 0 2 0 3 0 0 4

Non-zero digits

Position in list : 0 1 2 3
Digit            : 1 2 3 4
Original Index   : 0 2 4 7
```

Now every query only needs the non-zero digits whose original indices lie inside `[l,r]`.

---

# How to get them quickly?

Store the original indices in an array.

```
indices = [0,2,4,7]
```

For query `[l,r]`

Use Binary Search.

```
left = first index >= l
right = last index <= r
```

This immediately tells us which non-zero digits belong to the query.

Binary Search costs only

```
O(log N)
```

---

# Computing x efficiently

Suppose selected digits are

```
2 3 4
```

We need

```
234
```

instead of rebuilding digit by digit.

During preprocessing compute

```
prefixNumber[i]
```

where

```
prefixNumber[i]
=
number formed by first i non-zero digits
(mod MOD)
```

Example

```
Digits

1
12
123
1234
```

Formula

```
prefixNumber[i+1]
=
(prefixNumber[i] * 10 + digit) % MOD
```

---

## Extracting any middle portion

Suppose

```
123456
```

is stored.

Need

```
345
```

If

```
A = prefix before start

B = prefix till end
```

Then

```
345
=
B − A × 10^(length)
```

Exactly like substring hashing.

Formula

```
x =
prefix[end]
-
prefix[start] * 10^(len)
```

Take modulo properly.

---

# Computing digit sum

Store another prefix array.

```
prefixSum
```

Example

Digits

```
1 2 3 4

Prefix Sum

0
1
3
6
10
```

Then

```
sum =
prefixSum[right+1]
-
prefixSum[left]
```

O(1).

---

# Algorithm

### Preprocessing

Traverse string once.

Whenever digit ≠ 0

* store original index
* store digit
* update prefix digit sum
* update prefix number

Also precompute

```
10^i % MOD
```

---

### For every query

Binary Search

```
left = first index >= l
right = first index > r
```

If

```
left == right
```

No non-zero digits.

Answer = 0.

Otherwise

```
len = right-left

sum =
prefixSum[right]-prefixSum[left]

x =
prefixNumber[right]
-
prefixNumber[left]*10^len

answer =
x * sum
```

---

# Dry Run

```
s = "10203004"

Queries

[1,3]
```

Non-zero digits

```
1 2 3 4
```

Indices

```
0 2 4 7
```

Binary Search

```
left = 1
right = 2
```

Digits selected

```
2
```

```
sum = 2

x = 2

ans = 4
```

Correct.

---

# Java Solution

```java
import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;

    public int[] concatNonZeroDigits(String s, int[][] queries) {
        int n = s.length();

        ArrayList<Integer> idx = new ArrayList<>();
        ArrayList<Integer> digits = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c != '0') {
                idx.add(i);
                digits.add(c - '0');
            }
        }

        int m = digits.size();

        long[] prefixNum = new long[m + 1];
        long[] prefixSum = new long[m + 1];
        long[] pow10 = new long[m + 1];

        pow10[0] = 1;
        for (int i = 1; i <= m; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        for (int i = 0; i < m; i++) {
            prefixNum[i + 1] = (prefixNum[i] * 10 + digits.get(i)) % MOD;
            prefixSum[i + 1] = prefixSum[i] + digits.get(i);
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int left = lowerBound(idx, l);
            int right = lowerBound(idx, r + 1);

            if (left == right) {
                ans[i] = 0;
                continue;
            }

            int len = right - left;

            long sum = prefixSum[right] - prefixSum[left];

            long x = (prefixNum[right]
                    - (prefixNum[left] * pow10[len]) % MOD
                    + MOD) % MOD;

            ans[i] = (int) ((x * sum) % MOD);
        }

        return ans;
    }

    private int lowerBound(ArrayList<Integer> list, int target) {
        int lo = 0, hi = list.size();

        while (lo < hi) {
            int mid = (lo + hi) / 2;

            if (list.get(mid) < target)
                lo = mid + 1;
            else
                hi = mid;
        }

        return lo;
    }
}
```

---

# Why does the formula work?

Suppose the prefix number is:

```
123456
```

and we want

```
345
```

Split it as:

```
123456
=
123 × 10³ + 345
```

Therefore,

```
345
=
123456 − 123 × 10³
```

Generalizing:

```
substring =
prefixEnd − prefixStart × 10^(length)
```

This is the same principle used in rolling hash/string hashing, allowing us to extract any contiguous sequence of digits in **O(1)** after preprocessing.

---

# Complexity Analysis

* **Preprocessing:** `O(N)`
* **Each Query:** `O(log N)` (binary search) + `O(1)` calculations
* **Total:** `O(N + Q log N)`

### Space Complexity

* Arrays for non-zero digits, indices, prefix sums, prefix numbers, and powers of 10:

  * **O(N)**
