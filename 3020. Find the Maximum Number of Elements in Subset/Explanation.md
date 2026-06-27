# 3020. Find the Maximum Number of Elements in Subset

## Intuition

We need to choose a subset that can be rearranged into the form:

```
[x, x², x⁴, ..., xᵏ, ..., x⁴, x², x]
```

Notice two important properties:

* Every element **except the middle one** appears **twice** (once on the left and once on the right).
* The middle element appears **once**.

For example,

```
[2, 4, 16, 4, 2]

2 appears twice
4 appears twice
16 appears once (middle)
```

So if we want to build such a sequence:

* `x` must exist at least **2 times**
* `x²` must exist at least **2 times**
* `x⁴` must exist at least **2 times**
* ...
* The last value (middle element) only needs **1 occurrence**.

---

## Key Observation

Suppose we start with some number `x`.

The sequence is completely fixed.

```
x
↓
x²
↓
x⁴
↓
x⁸
↓
...
```

We cannot choose arbitrary numbers.

Each next value is simply the square of the previous value.

For example,

Starting from `2`

```
2
4
16
256
65536
```

Starting from `3`

```
3
9
81
6561
43046721
```

Numbers grow **extremely fast**.

Since

```
nums[i] ≤ 10^9
```

there can only be a handful of squaring operations (less than 6).

So brute-force expansion from every starting number is completely fine.

---

# Special Case: Number 1

`1² = 1`

So the sequence becomes

```
1
1
1
1
...
```

Every element is still 1.

Since the required sequence length must be **odd**, if there are

```
5 ones
```

answer can be

```
5
```

If there are

```
4 ones
```

we can only use

```
3
```

because the sequence length must stay odd.

Hence

```java
(oneCount - 1) | 1
```

This converts the count into the largest odd number not exceeding it.

Examples

```
5 -> 5
4 -> 3
3 -> 3
2 -> 1
1 -> 1
```

---

# Main Idea

Count frequency of every number.

Suppose we start from some base `x`.

We repeatedly check

```
x
x²
x⁴
x⁸
...
```

As long as a value appears **at least twice**, it can be placed on both sides.

So every such value contributes

```
+2
```

to the answer.

Eventually we'll reach a number that either

* appears once
* doesn't exist

If it appears once

```
+1
```

because it becomes the middle element.

If it doesn't exist

we have already counted one extra pair,

so we subtract one.

---

# Why skip some numbers?

Consider

```
2
4
16
```

If we start from

```
4
```

we get

```
4
16
```

But actually

```
4
```

should belong to the chain starting from

```
2
```

So starting from `4` cannot give a better answer.

How do we detect this?

Suppose

```
4 = 2²
```

and

```
2 appears at least twice
```

Then the chain should start from `2`.

So we skip `4`.

Same for

```
16 = 4²
```

if `4` appears twice.

This prevents checking duplicate chains.

---

# Dry Run

```
nums = [5,4,1,2,2]
```

Frequency

```
1 -> 1
2 -> 2
4 -> 1
5 -> 1
```

Answer from ones

```
1
```

---

Start from

```
2
```

Need

```
2 -> frequency 2
```

Good

```
length = 2
```

Next

```
4
```

frequency

```
1
```

Can become middle

```
+1
```

Final

```
3
```

Sequence

```
[2,4,2]
```

---

Start from

```
5
```

Only one occurrence

```
1
```

Maximum remains

```
3
```

---

Answer

```
3
```

---

# Why does the loop stop at 31622?

The code contains

```java
while (x < 31623)
```

because

```
31623² > 10⁹
```

Once `x` becomes larger than `31622`, its square would exceed the maximum possible value in the array, so no further squaring can exist in `nums`. This safely limits the loop.

---

# Java Solution

```java
class Solution {
    private static final int MAX_BASE = 31622;

    public int maximumLength(int[] nums) {

        Map<Integer, Integer> freq = new HashMap<>();

        // Count frequency of every number
        for (int num : nums)
            freq.merge(num, 1, Integer::sum);

        // Handle number 1 separately
        int one = freq.getOrDefault(1, 0);
        int ans = (one - 1) | 1;

        freq.remove(1);

        // Try every possible starting value
        for (int start : freq.keySet()) {

            // Skip if this number can belong to a smaller chain
            int root = (int) Math.sqrt(start);
            if (root * root == start &&
                freq.getOrDefault(root, 0) > 1)
                continue;

            int len = 0;
            int x = start;

            // Every value with frequency >=2 contributes two elements
            while (x < MAX_BASE + 1 &&
                   freq.containsKey(x) &&
                   freq.get(x) > 1) {

                len += 2;
                x *= x;
            }

            // Last value becomes the center if present
            if (freq.containsKey(x))
                ans = Math.max(ans, len + 1);
            else
                ans = Math.max(ans, len - 1);
        }

        return ans;
    }
}
```

---

# Why `len + 1` and `len - 1`?

Suppose

```
2 -> 2 copies
4 -> 2 copies
16 -> 1 copy
```

We counted

```
2 contributes 2
4 contributes 2

len = 4
```

Since

```
16 exists
```

it becomes the center.

```
Total = 5
```

So

```
len + 1
```

---

Now suppose

```
2 -> 2 copies
4 -> 2 copies
16 -> absent
```

We counted

```
2
4
```

But without a center, the last counted pair cannot form a valid symmetric sequence.

So

```
len = 4

Answer = 3
```

Hence

```
len - 1
```

---

# Complexity Analysis

* **Building frequency map:** `O(n)`
* **Checking each unique number:** `O(m)`, where `m` is the number of distinct values.
* Each chain grows by repeated squaring, so its length is at most `O(log log 10⁹)` (fewer than 6–7 iterations).

**Time Complexity:** **O(n)**

**Space Complexity:** **O(n)** (for the frequency map)

---

## Key Takeaways

* Count frequencies using a hash map.
* Treat `1` separately because `1² = 1`.
* Every non-center element must appear **twice**.
* The center needs only **one** occurrence.
* Build chains by repeatedly squaring (`x → x² → x⁴ → ...`).
* Skip numbers that are squares of another value with frequency ≥ 2 to avoid processing the same chain multiple times.
* Repeated squaring grows very quickly, making the overall solution effectively linear.
