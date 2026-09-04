## 3903. Smallest Stable Index I — Explanation

### 🧠 What is the problem asking?

For every index `i`, we need to calculate:

```text
instability score =
max(nums[0 ... i]) - min(nums[i ... n-1])
```

An index is **stable** if:

```text
instability score <= k
```

We need to return the **smallest/first** stable index.

If no index satisfies the condition, return `-1`.

---

### 🔍 Example

For:

```text
nums = [5, 0, 1, 4]
k = 3
```

At `i = 0`:

```text
left  = [5]          → max = 5
right = [5,0,1,4]    → min = 0

score = 5 - 0 = 5
```

`5 > 3`, so not stable.

At `i = 1`:

```text
left  = [5,0]        → max = 5
right = [0,1,4]      → min = 0

score = 5 - 0 = 5
```

Not stable.

At `i = 2`:

```text
left  = [5,0,1]      → max = 5
right = [1,4]        → min = 1

score = 5 - 1 = 4
```

Not stable.

At `i = 3`:

```text
left  = [5,0,1,4]    → max = 5
right = [4]          → min = 4

score = 5 - 4 = 1
```

Since:

```text
1 <= 3
```

index `3` is stable.

So answer = **3**.

---

# 💡 How your solution works

Your code uses two nested loops for every index:

```java
for(int i = 0; i < nums.length; i++) {
```

This represents checking every possible index `i`.

For each `i`, you need two things:

1. Maximum from `0` to `i`
2. Minimum from `i` to `n - 1`

---

## 1. Find the maximum on the left

You initialize:

```java
int maxVal = Integer.MIN_VALUE;
```

Then:

```java
for(int j = 0; j <= i; j++) {
    if(nums[j] > maxVal)
        maxVal = nums[j];
}
```

This checks:

```text
nums[0], nums[1], ..., nums[i]
```

and stores the largest value in `maxVal`.

For example, when:

```text
nums = [5,0,1,4]
i = 2
```

the loop checks:

```text
5 → maxVal = 5
0 → maxVal = 5
1 → maxVal = 5
```

So:

```text
maxVal = 5
```

---

## 2. Find the minimum on the right

For every `i`, you reset:

```java
int minVal = Integer.MAX_VALUE;
```

Then:

```java
for(int j = i; j < nums.length; j++) {
    if(nums[j] < minVal)
        minVal = nums[j];
}
```

This checks:

```text
nums[i], nums[i+1], ..., nums[n-1]
```

and finds the minimum.

For example, when:

```text
i = 2
```

we check:

```text
[1, 4]
```

so:

```text
minVal = 1
```

---

## 3. Calculate instability score

Once we have both values:

```java
instabilityScore = maxVal - minVal;
```

For example:

```text
maxVal = 5
minVal = 1

instabilityScore = 5 - 1
                 = 4
```

---

## 4. Check whether the index is stable

```java
if(instabilityScore <= k)
    return i;
```

This is important because we're looking for the **smallest** stable index.

We're checking indices in order:

```text
0 → 1 → 2 → 3 → ...
```

Therefore, the **first time** we find:

```text
score <= k
```

we can immediately return that index.

There is no need to check later indices.

---

# 🧩 Complete code with comments

```java
class Solution {
    public int firstStableIndex(int[] nums, int k) {

        int instabilityScore = 0;

        // Stores the maximum value from nums[0...i]
        int maxVal = Integer.MIN_VALUE;

        // Check every possible index
        for (int i = 0; i < nums.length; i++) {

            // Find minimum from nums[i...n-1]
            int minVal = Integer.MAX_VALUE;

            // Find maximum from nums[0...i]
            for (int j = 0; j <= i; j++) {
                if (nums[j] > maxVal)
                    maxVal = nums[j];
            }

            // Find minimum from nums[i...n-1]
            for (int j = i; j < nums.length; j++) {
                if (nums[j] < minVal)
                    minVal = nums[j];
            }

            // Calculate instability score
            instabilityScore = maxVal - minVal;

            // First stable index
            if (instabilityScore <= k)
                return i;
        }

        // No stable index found
        return -1;
    }
}
```

---

# ⚠️ One important observation about `maxVal`

You declared:

```java
int maxVal = Integer.MIN_VALUE;
```

**outside** the outer loop.

That's actually useful here.

Suppose:

```text
nums = [5, 0, 1, 4]
```

At `i = 0`:

```text
maxVal = 5
```

At `i = 1`, you check `[5,0]`. `maxVal` is already `5`, so it remains `5`.

At `i = 2`, you check `[5,0,1]`. It remains `5`.

This works because the left range keeps expanding:

```text
i=0 → [5]
i=1 → [5,0]
i=2 → [5,0,1]
i=3 → [5,0,1,4]
```

So once `maxVal` has been found, it never needs to decrease.

You could actually make your code slightly more efficient by updating `maxVal` directly for each `i` instead of running the first inner loop.

---

# 🚀 Better version — O(n²)

Because the constraints are only:

```text
n <= 100
```

your `O(n²)` approach is completely fine.

But we can simplify it:

```java
class Solution {
    public int firstStableIndex(int[] nums, int k) {

        int maxVal = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {

            // Include nums[i] in the left-side maximum
            maxVal = Math.max(maxVal, nums[i]);

            // Find minimum from i to n-1
            int minVal = Integer.MAX_VALUE;

            for (int j = i; j < nums.length; j++) {
                minVal = Math.min(minVal, nums[j]);
            }

            if (maxVal - minVal <= k)
                return i;
        }

        return -1;
    }
}
```

### Why is this better?

Your original code does:

```text
for every i:
    find maximum → O(n)
    find minimum → O(n)
```

The improved version does:

```text
for every i:
    update maximum → O(1)
    find minimum → O(n)
```

So it is still:

```text
Time:  O(n²)
Space: O(1)
```

but with less unnecessary work.

---

## 🧠 The key idea to remember

Think of each index `i` as dividing the array into two overlapping parts:

```text
        i
        ↓
[ 0 ... i | i ... n-1 ]
     LEFT       RIGHT
       ↓          ↓
      MAX        MIN
         \        /
          \      /
           score
```

Then simply check:

```text
MAX(left) - MIN(right) <= k
```

Since we check `i` from left to right, **the first index satisfying the condition is automatically the smallest stable index**.

### Complexity of your submitted solution

|       | Complexity |
| ----- | ---------- |
| Time  | **O(n²)**  |
| Space | **O(1)**   |

For `n <= 100`, this is easily fast enough.
