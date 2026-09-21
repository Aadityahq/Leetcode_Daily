This problem is a **Dynamic Programming on subarrays** problem.

The key observation is:

> Removing a prefix and a suffix while keeping the array non-empty is exactly the same as choosing **one non-empty contiguous subarray**.

So instead of thinking about prefix/suffix removals, we can simply **count all subarrays according to their product modulo `k`**. ([LeetCode][1])

## 1. Understanding the problem

Suppose:

```text
nums = [1, 2, 3, 4, 5]
```

If we keep:

```text
[2, 3, 4]
```

then we removed:

```text
prefix = [1]
suffix = [5]
```

So every possible operation corresponds to exactly one non-empty subarray.

For `[1,2,3,4,5]`, there are:

```text
n * (n + 1) / 2
= 5 * 6 / 2
= 15
```

non-empty subarrays.

We need to classify these 15 subarrays based on:

```text
product % k
```

For example, if `k = 3`:

```text
[1,2] → 2 % 3 = 2
[2,3] → 6 % 3 = 0
[4,5] → 20 % 3 = 2
```

So:

```text
result[0] = number of subarrays whose product % 3 == 0
result[1] = number of subarrays whose product % 3 == 1
result[2] = number of subarrays whose product % 3 == 2
```

---

# 2. Why brute force doesn't work

We could generate every subarray:

```java
for (int i = 0; i < n; i++) {
    for (int j = i; j < n; j++) {
        ...
    }
}
```

There are `O(n²)` subarrays.

With `n = 100000`:

```text
n² = 10¹⁰
```

which is far too large.

We need something close to `O(n)` or `O(n * k)`.

Since:

```text
k <= 5
```

`O(n * k)` is effectively `O(n)`.

---

# 3. DP idea

We process the array from left to right.

Let's define:

```text
dp[r]
```

as:

> Number of subarrays ending at the previous index whose product has remainder `r` when divided by `k`.

For every new number `num`, we have two choices.

### Choice 1: Start a new subarray

The subarray:

```text
[num]
```

has product:

```text
num % k
```

So:

```java
newDp[num % k]++;
```

---

### Choice 2: Extend an existing subarray

Suppose we had a subarray whose product remainder was `r`.

Now we append `num`.

Its new product is:

```text
oldProduct * num
```

Therefore its new remainder is:

```text
(r * num) % k
```

So:

```java
newDp[(r * num) % k] += dp[r];
```

That's the entire DP idea.

---

# 4. Example

Let's use:

```text
nums = [1, 2, 3]
k = 3
```

Initially:

```text
dp = [0, 0, 0]
```

### Process `1`

New subarray:

```text
[1]
```

Remainder:

```text
1 % 3 = 1
```

Therefore:

```text
dp = [0, 1, 0]
```

---

### Process `2`

Start new:

```text
[2] → 2
```

Extend `[1]`:

```text
[1,2]
product = 2
remainder = 2
```

So:

```text
dp = [0, 0, 2]
```

There are two subarrays ending at index 1:

```text
[2]     → 2
[1,2]   → 2
```

---

### Process `3`

Start:

```text
[3] → 0
```

Extend the two previous subarrays:

```text
[2,3]   → 6 % 3 = 0
[1,2,3] → 6 % 3 = 0
```

So:

```text
dp = [3, 0, 0]
```

Now the total answer is:

```text
[3, 1, 2]
```

because we also had earlier subarrays:

```text
[1] → 1
[2] → 2
[1,2] → 2
```

Thus:

```text
remainder 0 → 3
remainder 1 → 1
remainder 2 → 2
```

---

# 5. Java solution

The current LeetCode problem has `n` up to `10^5`, so the counts can exceed `int`; use `long[]` for the DP and answer. ([LeetCode][1])

```java
class Solution {
    public long[] resultArray(int[] nums, int k) {

        long[] result = new long[k];

        // dp[r] = number of subarrays ending at previous index
        // whose product % k == r
        long[] dp = new long[k];

        for (int num : nums) {

            long[] newDp = new long[k];

            int value = num % k;

            // 1. Start a new subarray with nums[i]
            newDp[value]++;

            // 2. Extend all previous subarrays
            for (int r = 0; r < k; r++) {

                if (dp[r] > 0) {
                    int newRemainder = (r * value) % k;

                    newDp[newRemainder] += dp[r];
                }
            }

            // Add all subarrays ending at current index
            // to the final answer
            for (int r = 0; r < k; r++) {
                result[r] += newDp[r];
            }

            dp = newDp;
        }

        return result;
    }
}
```

---

# 6. Let's understand the code carefully

### Step 1

```java
long[] result = new long[k];
```

This stores the final answer.

For `k = 3`:

```text
result[0] → number of subarrays with product % 3 = 0
result[1] → number of subarrays with product % 3 = 1
result[2] → number of subarrays with product % 3 = 2
```

---

### Step 2

```java
long[] dp = new long[k];
```

This stores information about **only the subarrays ending at the previous index**.

This is important.

We don't need to remember every subarray.

We only need to know:

```text
How many previous subarrays have each remainder?
```

For example:

```text
dp = [5, 2, 3]
```

means:

```text
5 subarrays → remainder 0
2 subarrays → remainder 1
3 subarrays → remainder 2
```

---

# 7. Why do we create `newDp`?

```java
long[] newDp = new long[k];
```

Suppose we're processing:

```text
nums[i]
```

`dp` represents subarrays ending at `i - 1`.

`newDp` will represent subarrays ending at `i`.

We don't want to modify `dp` while iterating over it, because that could accidentally use newly created subarrays multiple times.

So:

```text
dp     → previous index
newDp  → current index
```

---

# 8. Starting a new subarray

```java
int value = num % k;

newDp[value]++;
```

Every element itself forms a valid subarray.

For:

```text
num = 8
k = 3
```

we have:

```text
[8]
8 % 3 = 2
```

So:

```java
newDp[2]++;
```

---

# 9. Extending previous subarrays

This is the most important part:

```java
for (int r = 0; r < k; r++) {

    if (dp[r] > 0) {
        int newRemainder = (r * value) % k;

        newDp[newRemainder] += dp[r];
    }
}
```

Suppose:

```text
k = 3
value = 2
```

and:

```text
dp = [1, 2, 1]
```

That means:

```text
1 previous subarray has remainder 0
2 previous subarrays have remainder 1
1 previous subarray has remainder 2
```

When we append `2`:

### Remainder 0

```text
(0 * 2) % 3 = 0
```

So:

```text
newDp[0] += 1
```

### Remainder 1

```text
(1 * 2) % 3 = 2
```

So:

```text
newDp[2] += 2
```

### Remainder 2

```text
(2 * 2) % 3 = 1
```

So:

```text
newDp[1] += 1
```

This efficiently represents all possible subarrays ending at the current element.

---

# 10. Why does this count every subarray exactly once?

This is the key DP reasoning.

Every non-empty subarray has a unique ending index.

For example:

```text
[1,2]
[2,3]
[1,2,3]
```

Each has a different ending position except when they share an ending position.

For a fixed ending index `i`, every subarray ending there is either:

### Case 1: Only `nums[i]`

```text
[nums[i]]
```

or

### Case 2: Some previous subarray + `nums[i]`

For example:

```text
[1,2,3]
```

comes from:

```text
[1,2] + 3
```

Therefore, our DP generates **every possible subarray exactly once**.

---

# 11. Why can we use modulo during multiplication?

We don't need the actual product.

We only need:

```text
product % k
```

Mathematically:

```text
(a * b) % k
=
((a % k) * (b % k)) % k
```

Therefore, instead of storing huge products, we only store their remainders.

This is particularly useful because `nums[i]` can be as large as `10^9`. ([LeetCode][1])

---

# 12. Complexity

There are `n` elements.

For every element, we loop through `k` remainders.

Therefore:

```text
Time = O(n * k)
```

Since:

```text
k <= 5
```

this is effectively:

```text
O(n)
```

Space:

```text
O(k)
```

because we only maintain:

```text
dp
newDp
result
```

So:

```text
Time:  O(n * k)
Space: O(k)
```

---

## 13. One important thing to remember

The biggest conceptual trick in this problem is:

```text
Remove prefix + remove suffix
             ↓
Choose one non-empty subarray
             ↓
Count subarrays by product % k
             ↓
DP by remainder
```

And the DP state is simply:

```text
dp[r] = number of subarrays ending at current position
        whose product % k == r
```

The transition is:

```text
new remainder = (old remainder × nums[i]) % k
```

plus the new one-element subarray:

```text
newDp[nums[i] % k]++;
```

This is a very useful pattern to remember for problems involving **subarray products modulo a small `k`**.

[1]: https://leetcode.com/problems/find-x-value-of-array-i/?utm_source=chatgpt.com "Find X Value of Array I - LeetCode"
