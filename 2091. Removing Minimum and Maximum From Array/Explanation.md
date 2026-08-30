## LeetCode 2091 — Removing Minimum and Maximum From Array

### 🧠 Problem Explanation

You are given an array of **distinct integers**.

You need to remove **both**:

* the **minimum** element
* the **maximum** element

But there is a restriction:

> In one deletion, you can remove only the **first element** or the **last element** of the array.

Your goal is to find the **minimum number of deletions** required.

---

### 🔍 Key Observation

Suppose:

```text
nums = [2, 10, 7, 5, 4, 1, 8, 6]
```

Minimum = `1`, at index `5`
Maximum = `10`, at index `1`

So we have:

```text
index:  0   1   2   3   4   5   6   7
       [2, 10, 7,  5,  4,  1,  8,  6]
           ↑               ↑
          max             min
```

There are only **three meaningful strategies**.

### Strategy 1: Remove both from the front

To remove the element at the larger index:

```text
max(minIndex, maxIndex) + 1
```

For the example:

```text
max(5, 1) + 1 = 6
```

So, 6 deletions.

---

### Strategy 2: Remove both from the back

If we remove from the back until reaching the element with the smaller index:

```text
n - min(minIndex, maxIndex)
```

For the example:

```text
8 - min(5, 1)
= 8 - 1
= 7
```

So, 7 deletions.

---

### Strategy 3: Remove one from the front and the other from the back

We have two possibilities:

#### Remove min from front, max from back

```text
(minIndex + 1) + (n - maxIndex)
```

#### Remove max from front, min from back

```text
(maxIndex + 1) + (n - minIndex)
```

For the example:

```text
(5 + 1) + (8 - 1) = 13
```

and

```text
(1 + 1) + (8 - 5) = 5
```

Therefore the answer is:

```text
5
```

---

## ✅ Java Solution

```java
class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        // Find indices of minimum and maximum elements
        int minIndex = 0;
        int maxIndex = 0;

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }

            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        // Case 1: Remove both from the front
        int removeFromFront = Math.max(minIndex, maxIndex) + 1;

        // Case 2: Remove both from the back
        int removeFromBack = n - Math.min(minIndex, maxIndex);

        // Case 3: Remove min from front and max from back
        int minFrontMaxBack = (minIndex + 1) + (n - maxIndex);

        // Case 4: Remove max from front and min from back
        int maxFrontMinBack = (maxIndex + 1) + (n - minIndex);

        return Math.min(
            Math.min(removeFromFront, removeFromBack),
            Math.min(minFrontMaxBack, maxFrontMinBack)
        );
    }
}
```

---

# 🚀 How and Why This Works

The important part is understanding **why we only need to check these four cases**.

Let the positions be:

```text
a = minIndex
b = maxIndex
```

Since we can delete only from the **front or back**, eventually we must remove the two target elements in one of these ways:

### Option 1 — Both from front

We keep deleting from the front until we've passed the element that is farther from the front.

Therefore:

```text
max(a, b) + 1
```

---

### Option 2 — Both from back

Similarly, we delete from the back until we've passed the element closer to the front.

Therefore:

```text
n - min(a, b)
```

---

### Option 3 — One from each side

There are two possibilities:

```text
min → front
max → back
```

Cost:

```text
(a + 1) + (n - b)
```

or:

```text
max → front
min → back
```

Cost:

```text
(b + 1) + (n - a)
```

We calculate all four and take the minimum.

---

## 🧪 Example 2

```text
nums = [0, -4, 19, 1, 8, -2, -3, 5]
```

Minimum:

```text
-4 → index 1
```

Maximum:

```text
19 → index 2
```

### Both from front

```text
max(1, 2) + 1
= 3
```

### Both from back

```text
8 - min(1, 2)
= 7
```

### Min front + max back

```text
(1 + 1) + (8 - 2)
= 8
```

### Max front + min back

```text
(2 + 1) + (8 - 1)
= 10
```

Minimum:

```text
3
```

So the answer is `3`.

---

## 🧪 Example 3

```text
nums = [101]
```

There is only one element.

Its index is:

```text
0
```

Both minimum and maximum are the same element.

Our formula gives:

```text
max(0, 0) + 1 = 1
```

So:

```text
Output = 1
```

---

## ⏱️ Complexity

We scan the array once to find the minimum and maximum positions.

**Time:**

```text
O(n)
```

**Space:**

```text
O(1)
```

This easily handles:

```text
n <= 100,000
```

---

### 💡 Interview Takeaway

The main trick is **not** to simulate deletions.

Instead:

1. Find the indices of minimum and maximum.
2. Consider the only possible ways to remove them:

   * both from front
   * both from back
   * min from front + max from back
   * max from front + min from back
3. Return the minimum cost.

This is a classic example where recognizing the **small number of possible strategies** turns what looks like a simulation problem into a simple `O(n)` solution.
