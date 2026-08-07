This is one of the hardest greedy + number theory problems on LeetCode. The main difficulty is that:

* `num.length` can be **200,000**, so checking numbers one by one is impossible.
* `t` can be **10¹⁴**, so the product of digits cannot be computed directly.
* We need the **smallest** valid number **≥ num**.

---

# Key Observation 1

The product of digits can only contain the prime factors of digits **1–9**.

| Digit | Prime Factors |
| ----- | ------------- |
| 2     | 2             |
| 3     | 3             |
| 4     | 2²            |
| 5     | 5             |
| 6     | 2×3           |
| 7     | 7             |
| 8     | 2³            |
| 9     | 3²            |

Therefore the product of any zero-free number can contain **only**

* 2
* 3
* 5
* 7

If after factorizing `t` there is any remaining prime factor (11,13,...), the answer is impossible.

Example

```
t = 26

26 = 2 × 13
```

13 cannot be produced by any digit.

Answer:

```
-1
```

This is the first pruning step. ([WalkCCC][1])

---

# Key Observation 2

Instead of storing the product of digits,

store

```
count of 2
count of 3
count of 5
count of 7
```

Example

```
1488

1 -> nothing
4 -> 2²
8 -> 2³
8 -> 2³
```

Total

```
2 : 8
3 : 0
5 : 0
7 : 0
```

So product is divisible by

```
2⁸
```

---

# Key Observation 3

Suppose

```
t = 2⁵ × 3²
```

We don't care about the exact product.

We only care whether our digits contain

```
2 : at least 5
3 : at least 2
```

---

# Greedy Idea

If `num` already satisfies the condition

return it.

Otherwise,

change the **rightmost possible digit**.

Why?

Changing later digits keeps the number smaller.

Exactly the same idea as "Next Permutation".

---

Suppose

```
num = 1234
```

Instead of changing

```
2
```

first,

try changing

```
4
```

If impossible,

then

```
3

then

2

then

1
```

This guarantees minimum answer.

---

# Prefix Factor Count

We precompute

```
prime factors contributed by every prefix.
```

Example

```
12368
```

Prefix after

```
123

contains

2 :1
3 :1
```

Then when removing suffix,

we instantly know remaining factors.

No recomputation.

Complexity becomes O(n).

---

# Constructing the Smallest Suffix

Suppose after fixing prefix we still need

```
2³
3²
5¹
```

Instead of placing many

```
2223335
```

compress them.

Example

```
2³ -> 8

3² -> 9

2² -> 4

2×3 -> 6
```

because

```
8 < 222
```

uses fewer digits.

The algorithm greedily compresses:

```
2³ → 8
3² → 9
2² → 4
2×3 → 6
```

and performs a few additional merges to minimize the total number of digits, producing the lexicographically smallest suffix. ([WalkCCC][1])

---

# Algorithm

### Step 1

Factorize

```
t
```

into

```
2
3
5
7
```

If remaining number ≠1

return

```
-1
```

---

### Step 2

If required digits exceed current length

directly construct smallest valid number.

---

### Step 3

If current number already works

return it.

---

### Step 4

Traverse from right to left.

For every position

try increasing the digit.

---

### Step 5

If remaining suffix can satisfy needed factors

construct smallest suffix

return answer.

---

### Step 6

If same length impossible

increase length by one

fill leading ones

append required digits.

---

# Complexity

Let

```
n = length of num
```

Factorization

```
O(log t)
```

Scan

```
O(n)
```

Construction

```
O(n)
```

Overall

```
Time : O(n + log t)

Space : O(n)
```

which satisfies the constraints. ([WalkCCC][1])

---

# Java Solution

```java
import java.util.*;

class Solution {

    private static final Map<Integer, Map<Integer, Integer>> FACTOR_COUNTS = new HashMap<>();

    static {
        FACTOR_COUNTS.put(0, Map.of());
        FACTOR_COUNTS.put(1, Map.of());
        FACTOR_COUNTS.put(2, Map.of(2, 1));
        FACTOR_COUNTS.put(3, Map.of(3, 1));
        FACTOR_COUNTS.put(4, Map.of(2, 2));
        FACTOR_COUNTS.put(5, Map.of(5, 1));
        FACTOR_COUNTS.put(6, Map.of(2, 1, 3, 1));
        FACTOR_COUNTS.put(7, Map.of(7, 1));
        FACTOR_COUNTS.put(8, Map.of(2, 3));
        FACTOR_COUNTS.put(9, Map.of(3, 2));
    }

    public String smallestNumber(String num, long t) {

        Pair primeInfo = getPrimeCount(t);

        if (!primeInfo.ok)
            return "-1";

        Map<Integer, Integer> need = primeInfo.map;

        Map<Integer, Integer> factorCount = getFactorCount(need);

        if (sum(factorCount) > num.length())
            return construct(factorCount);

        Map<Integer, Integer> prefix = getPrimeCount(num);

        int firstZero = num.indexOf('0');

        if (firstZero == -1) {
            firstZero = num.length();

            if (contains(prefix, need))
                return num;
        }

        for (int i = num.length() - 1; i >= 0; i--) {

            int d = num.charAt(i) - '0';

            prefix = subtract(prefix, FACTOR_COUNTS.get(d));

            int remain = num.length() - i - 1;

            if (i > firstZero)
                continue;

            for (int bigger = d + 1; bigger <= 9; bigger++) {

                Map<Integer, Integer> after =
                        getFactorCount(
                                subtract(
                                        subtract(need, prefix),
                                        FACTOR_COUNTS.get(bigger)));

                if (sum(after) <= remain) {

                    int ones = remain - sum(after);

                    return num.substring(0, i)
                            + bigger
                            + "1".repeat(ones)
                            + construct(after);
                }
            }
        }

        factorCount = getFactorCount(need);

        return "1".repeat(num.length() + 1 - sum(factorCount))
                + construct(factorCount);
    }

    static class Pair {
        Map<Integer, Integer> map;
        boolean ok;

        Pair(Map<Integer, Integer> m, boolean b) {
            map = m;
            ok = b;
        }
    }

    private Pair getPrimeCount(long t) {

        Map<Integer, Integer> cnt = new HashMap<>();

        cnt.put(2, 0);
        cnt.put(3, 0);
        cnt.put(5, 0);
        cnt.put(7, 0);

        int[] p = {2, 3, 5, 7};

        for (int x : p) {

            while (t % x == 0) {

                cnt.put(x, cnt.get(x) + 1);

                t /= x;
            }
        }

        return new Pair(cnt, t == 1);
    }

    private Map<Integer, Integer> getPrimeCount(String s) {

        Map<Integer, Integer> ans = new HashMap<>();

        ans.put(2, 0);
        ans.put(3, 0);
        ans.put(5, 0);
        ans.put(7, 0);

        for (char c : s.toCharArray()) {

            Map<Integer, Integer> m = FACTOR_COUNTS.get(c - '0');

            for (var e : m.entrySet())
                ans.put(e.getKey(), ans.get(e.getKey()) + e.getValue());
        }

        return ans;
    }

    private Map<Integer, Integer> subtract(Map<Integer, Integer> a,
                                           Map<Integer, Integer> b) {

        Map<Integer, Integer> res = new HashMap<>(a);

        for (var e : b.entrySet()) {

            int k = e.getKey();

            res.put(k, Math.max(0, res.get(k) - e.getValue()));
        }

        return res;
    }

    private boolean contains(Map<Integer, Integer> have,
                             Map<Integer, Integer> need) {

        for (int p : List.of(2, 3, 5, 7))

            if (have.get(p) < need.get(p))
                return false;

        return true;
    }

    private int sum(Map<Integer, Integer> m) {

        int s = 0;

        for (int v : m.values())
            s += v;

        return s;
    }

    private Map<Integer, Integer> getFactorCount(Map<Integer, Integer> cnt) {

        int c8 = cnt.get(2) / 3;
        int rem2 = cnt.get(2) % 3;

        int c9 = cnt.get(3) / 2;
        int rem3 = cnt.get(3) % 2;

        int c4 = rem2 / 2;
        int c2 = rem2 % 2;

        int c6 = 0;

        if (c2 == 1 && rem3 == 1) {
            c2 = 0;
            rem3 = 0;
            c6 = 1;
        }

        if (rem3 == 1 && c4 == 1) {
            c2 = 1;
            c6 = 1;
            rem3 = 0;
            c4 = 0;
        }

        Map<Integer, Integer> ans = new HashMap<>();

        ans.put(2, c2);
        ans.put(3, rem3);
        ans.put(4, c4);
        ans.put(5, cnt.get(5));
        ans.put(6, c6);
        ans.put(7, cnt.get(7));
        ans.put(8, c8);
        ans.put(9, c9);

        return ans;
    }

    private String construct(Map<Integer, Integer> m) {

        StringBuilder sb = new StringBuilder();

        for (int d = 2; d <= 9; d++) {

            int cnt = m.getOrDefault(d, 0);

            while (cnt-- > 0)
                sb.append(d);
        }

        return sb.toString();
    }
}
```

This solution follows the optimal **greedy + number theory** approach with **O(n + log t)** time complexity, which is required for the problem's large constraints. ([WalkCCC][1])

