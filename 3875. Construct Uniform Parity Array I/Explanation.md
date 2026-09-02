## LeetCode 3875 — Construct Uniform Parity Array I

### 🧠 Problem in simple words

You are given an array `nums1` containing **distinct integers**.

For every element `nums1[i]`, you have two choices:

1. Keep it as it is:

   ```text
   nums2[i] = nums1[i]
   ```

2. Subtract **any other element**:

   ```text
   nums2[i] = nums1[i] - nums1[j]   (j != i)
   ```

Your goal is to make **all elements of `nums2` have the same parity**:

* either all **odd**
* or all **even**

We only care about whether a number is odd/even, not its actual value.

---

# 🔑 Key Observation

The entire problem can be solved by looking only at the **parity** of the numbers.

Remember:

| Operation   | Result |
| ----------- | ------ |
| even − even | even   |
| odd − odd   | even   |
| even − odd  | odd    |
| odd − even  | odd    |

So:

* **same parity − same parity = even**
* **different parity − different parity = odd**

Now consider what happens if `nums1` contains both odd and even numbers.

Suppose:

```text
nums1 = [2, 3]
```

For `2`:

```text
2 - 3 = -1  → odd
```

For `3`, simply keep it:

```text
3 → odd
```

So:

```text
[-1, 3]
```

Both are odd. ✅

---

## What if all numbers have the same parity?

For example:

```text
[4, 6, 8]
```

They're already all even, so simply keep every number:

```text
[4, 6, 8]
```

All even. ✅

Similarly:

```text
[1, 3, 7]
```

They're already all odd. ✅

Therefore, **if all elements have the same parity, the answer is immediately `true`.**

---

# 🤔 What if there are both odd and even numbers?

This is the interesting case.

Suppose:

```text
nums1 = [2, 4, 5]
```

We want all elements to become the same parity.

Since we have both parities, we can use subtraction strategically.

For every **even** number, subtract an **odd** number:

```text
even - odd = odd
```

For every **odd** number, simply keep it:

```text
odd = odd
```

Therefore all elements can become odd.

For example:

```text
2 - 5 = -3 → odd
4 - 5 = -1 → odd
5       = 5 → odd
```

So:

```text
[-3, -1, 5]
```

All odd. ✅

Because the array contains distinct integers, if there is an odd number, it is different from every even number, so it can be used as the subtraction partner.

Thus:

> **No matter what combination of odd and even numbers exists, we can always make the whole array odd.**

And if the array already has uniform parity, we can simply keep everything unchanged.

So the answer appears to always be `true`.

---

# ⚠️ Wait — Is the answer really always true?

Yes.

Consider the smallest case:

```text
n = 1
nums1 = [5]
```

We cannot choose another `j`, but we don't need to.

We can simply choose:

```text
nums2[0] = 5
```

It's odd. ✅

For any `n >= 2`:

* If all numbers have the same parity → keep them.
* If both parities exist → choose one number of the opposite parity and subtract it from every number having the undesired parity; keep the desired-parity numbers unchanged.

So **every valid input has an answer of `true`.**

---

# 💡 Simplest Java Solution

```java
class Solution {
    public boolean constructUniformParityArray(int[] nums1) {
        return true;
    }
}
```

That's it.

### Complexity

Since we don't even need to inspect the array:

* **Time:** `O(1)`
* **Space:** `O(1)`

---

# But why would LeetCode make this a problem?

The trick is recognizing that **you don't actually need to construct `nums2`**.

The question asks:

> "Is it possible?"

It doesn't ask us to return the constructed array.

Because we can always construct a valid array, the answer is always `true`.

---

## 🔍 Let's prove it formally

Take any `nums1`.

### Case 1: All numbers have the same parity

If all numbers are odd:

```text
nums2[i] = nums1[i]
```

Then every `nums2[i]` is odd.

If all numbers are even:

```text
nums2[i] = nums1[i]
```

Then every `nums2[i]` is even.

Therefore:

```text
answer = true
```

---

### Case 2: There are both odd and even numbers

Pick any odd number `x`.

For every even number `nums1[i]`, choose:

```text
nums2[i] = nums1[i] - x
```

Since:

```text
even - odd = odd
```

those elements become odd.

For every odd number:

```text
nums2[i] = nums1[i]
```

which is already odd.

Therefore every element of `nums2` is odd.

So again:

```text
answer = true
```

The same argument works if we instead choose an even number as the target parity.

---

# 🧪 Example Walkthrough

### Example 1

```text
nums1 = [2, 3]
```

There is an even and an odd number.

Choose:

```text
2 - 3 = -1
3 = 3
```

So:

```text
nums2 = [-1, 3]
```

Both are odd.

```text
true
```

---

### Example 2

```text
nums1 = [4, 6]
```

Both are even.

Simply keep them:

```text
nums2 = [4, 6]
```

All even.

```text
true
```

---

### Another example

```text
nums1 = [1, 4, 7, 10]
```

Odd numbers:

```text
1, 7
```

Even numbers:

```text
4, 10
```

Choose `1` as the odd subtraction partner.

For even numbers:

```text
4 - 1 = 3
10 - 1 = 9
```

Keep odd numbers:

```text
1
7
```

Therefore:

```text
nums2 = [1, 3, 7, 9]
```

Everything is odd. ✅

---

# 🏆 Final Code

```java
class Solution {
    public boolean constructUniformParityArray(int[] nums1) {
        return true;
    }
}
```

### Why this is the best solution

The important part isn't writing the code—it's proving the property:

> **For every valid `nums1`, a uniform-parity `nums2` can always be constructed.**

Therefore there is nothing to calculate or search.

| Approach                   |       Time |      Space |
| -------------------------- | ---------: | ---------: |
| Actually construct `nums2` |     `O(n)` |     `O(n)` |
| Check parities             |     `O(n)` |     `O(1)` |
| **Return `true` directly** | **`O(1)`** | **`O(1)`** |

**The `return true` solution is optimal.**
