
This is a **Hard** problem because generating all pairs is impossible.

* `n = 10^5`
* Number of pairs = `n(n-1)/2 ≈ 5 × 10^9`
* We cannot calculate every GCD.

The trick is to **count how many pairs have GCD = x** instead of finding every pair individually.

---

# Step 1: Understand the Problem

For every pair `(i, j)` where `i < j`

Find

```
gcd(nums[i], nums[j])
```

Store them.

Sort them.

For every query `k`

Return the `k-th` element.

Example

```
nums = [2,3,4]

Pairs

(2,3) -> 1
(2,4) -> 2
(3,4) -> 1

gcdPairs

[1,2,1]

Sorted

[1,1,2]

queries = [0,2]

Answer

1
2
```

---

# Step 2: Why Brute Force Doesn't Work

Suppose

```
n = 100000
```

Number of pairs

```
100000 × 99999 /2

≈ 5,000,000,000
```

Five billion pairs.

Impossible.

Need another idea.

---

# Step 3: Think in Reverse

Instead of asking

> What is gcd of every pair?

Ask

> How many pairs have gcd = 1?

How many have gcd = 2?

How many have gcd = 3?

...

Maximum number is only

```
50000
```

So we only need answers for values

```
1...50000
```

---

# Step 4: Count Frequency

Example

```
nums = [2,3,4]
```

Frequency array

```
1 ->0
2 ->1
3 ->1
4 ->1
```

---

# Step 5: Count Multiples

Suppose we want GCD = 2.

First count numbers divisible by 2.

```
2
4

count =2
```

Possible pairs

```
C(2,2)=1
```

So one pair has GCD divisible by 2.

---

Suppose GCD =1

Numbers divisible by1

```
2
3
4

count=3

Pairs

C(3,2)=3
```

These are pairs whose gcd is

```
1
or2
or3
or4...
```

Not exactly 1.

Need inclusion-exclusion.

---

# Step 6: Inclusion-Exclusion

Suppose

```
pairs divisible by2 =1
pairs divisible by1 =3
```

Among those 3,

one actually has gcd 2.

Remove it.

```
exact gcd1

=

3-1

=2
```

Exactly correct.

Those are

```
(2,3)
(3,4)
```

---

General formula

Start from biggest number.

For every d

```
pairsHavingGcdMultipleOfD

=

C(countMultiples[d],2)
```

Subtract all larger multiples.

```
exact[d]

=

pairs

-

exact[2d]

-

exact[3d]

...
```

This is classic inclusion-exclusion.

---

# Step 7: Example

```
nums

2 4 6
```

Frequency

```
2->1
4->1
6->1
```

For d=2

Multiples

```
2
4
6

count=3

pairs=3
```

But

```
gcd4

0

gcd6

0

gcd2

3
```

No subtraction needed.

Answer

```
gcd2=3
```

Indeed

```
(2,4)=2
(2,6)=2
(4,6)=2
```

---

Another example

```
nums

4
8
```

For

```
d=4

count=2

pairs=1
```

For

```
d=8

count=1

pairs=0
```

Answer

```
gcd4=1
```

Correct.

---

# Step 8: Build Sorted GCD Array Virtually

Suppose

```
exact

1 ->5 pairs

2 ->3 pairs

4 ->2 pairs
```

Sorted array would be

```
1
1
1
1
1
2
2
2
4
4
```

We never create this array.

Instead make prefix sums.

```
prefix

1 ->5

2 ->8

4 ->10
```

Meaning

```
first 5 numbers are1

next3 numbers are2

last2 numbers are4
```

---

# Step 9: Answer Query

Suppose query

```
6
```

Need 7th element.

Binary search on prefix.

```
prefix

5

8

10
```

First prefix greater than6

```
8
```

Corresponds to value

```
2
```

Answer

```
2
```

---

# Algorithm

```
Count frequency

↓

For every divisor

Count multiples

↓

Compute pairs divisible by divisor

↓

Subtract multiples
(Inclusion Exclusion)

↓

Now exact[g] known

↓

Build prefix counts

↓

Binary search for every query
```

---

# Complexity

Let

```
M = max(nums)
```

Maximum

```
50000
```

Counting multiples

```
M/1
+
M/2
+
M/3
...

=

M log M
```

Approximately

```
50000 log50000
```

Very fast.

Binary search

```
Q log M
```

Overall

```
O(M log M + Q log M)
```

Memory

```
O(M)
```

Perfect for constraints.

---

# Java Solution

```java
import java.util.*;

class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums) {
            max = Math.max(max, x);
        }

        // Frequency of each value
        int[] freq = new int[max + 1];
        for (int x : nums) {
            freq[x]++;
        }

        // exactPairs[g] = number of pairs with gcd exactly g
        long[] exactPairs = new long[max + 1];

        // Process from largest gcd to smallest
        for (int g = max; g >= 1; g--) {

            long count = 0;

            // Count numbers divisible by g
            for (int multiple = g; multiple <= max; multiple += g) {
                count += freq[multiple];
            }

            // Total pairs divisible by g
            long pairs = count * (count - 1) / 2;

            // Remove pairs whose gcd is multiple of g
            for (int multiple = g * 2; multiple <= max; multiple += g) {
                pairs -= exactPairs[multiple];
            }

            exactPairs[g] = pairs;
        }

        // Prefix counts
        long[] prefix = new long[max + 1];
        for (int g = 1; g <= max; g++) {
            prefix[g] = prefix[g - 1] + exactPairs[g];
        }

        int[] answer = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            long target = queries[i] + 1;

            int left = 1;
            int right = max;

            while (left < right) {
                int mid = left + (right - left) / 2;

                if (prefix[mid] >= target)
                    right = mid;
                else
                    left = mid + 1;
            }

            answer[i] = left;
        }

        return answer;
    }
}
```

---

# Key Ideas to Remember

1. **Never generate all pairs** (`O(n²)` is too slow).
2. **Count frequencies** of numbers.
3. For each possible GCD `g`, count how many numbers are divisible by `g`.
4. Compute pairs divisible by `g` using the combination formula `C(count, 2)`.
5. Use **inclusion-exclusion** (subtract counts for larger multiples) to get pairs whose **GCD is exactly `g`**.
6. Build a **prefix sum** of exact pair counts to represent the sorted `gcdPairs` array implicitly.
7. Answer each query with **binary search** on the prefix sums, without ever constructing the huge sorted array.
