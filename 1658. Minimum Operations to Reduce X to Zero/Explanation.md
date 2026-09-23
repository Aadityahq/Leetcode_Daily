## 1658. Minimum Operations to Reduce X to Zero

### 💡 Main Idea

At first, the problem looks like we should try removing elements from the **left or right** and find the minimum operations.

But there is a better way to think about it.

Suppose the total sum of the array is:

```text
total = sum(nums)
```

If we remove elements from both ends whose sum is `x`, then the elements **left in the middle** will have sum:

```text
total - x
```

So instead of:

> Find the minimum number of elements to remove whose sum is `x`.

we can solve:

> **Find the longest contiguous subarray whose sum is `total - x`.**

Why longest?

If the longest remaining subarray has length `L`, then the number of removed elements is:

```text
n - L
```

So:

```text
minimum operations = n - longestSubarrayLength
```

---

### Example

```text
nums = [1,1,4,2,3]
x = 5
```

Total:

```text
1 + 1 + 4 + 2 + 3 = 11
```

We need to keep a subarray with:

```text
total - x = 11 - 5 = 6
```

The longest subarray with sum `6` is:

```text
[1, 4, 2] 
```

Length = `3`

Therefore:

```text
operations = 5 - 3 = 2
```

We remove:

```text
3 and 2
```

which gives:

```text
5
```

---

## Why Sliding Window Works

All `nums[i]` are **positive**.

This is very important.

Because all numbers are positive:

* If the current sum is **less than** the target → expand the window.
* If the current sum is **greater than** the target → shrink the window.
* If the current sum equals the target → we found a valid subarray.

This gives us an `O(n)` sliding-window solution.

---

## Java Solution

```java
class Solution {
    public int minOperations(int[] nums, int x) {

        int n = nums.length;

        // Calculate total sum
        long total = 0;

        for (int num : nums) {
            total += num;
        }

        // Sum of the subarray that we want to keep
        long target = total - x;

        // If target is negative, it is impossible
        if (target < 0) {
            return -1;
        }

        // If target is 0, we have to remove every element
        if (target == 0) {
            return n;
        }

        int left = 0;
        long sum = 0;
        int maxLength = -1;

        for (int right = 0; right < n; right++) {

            sum += nums[right];

            // Shrink window if sum becomes greater than target
            while (left <= right && sum > target) {
                sum -= nums[left];
                left++;
            }

            // Found a subarray with required sum
            if (sum == target) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }

        // No valid subarray found
        if (maxLength == -1) {
            return -1;
        }

        // Remove everything outside the longest subarray
        return n - maxLength;
    }
}
```

---

## 🔍 Dry Run

For:

```text
nums = [1,1,4,2,3]
x = 5
```

### Step 1: Calculate total

```text
total = 11
target = total - x
       = 11 - 5
       = 6
```

We need the longest subarray with sum `6`.

### Sliding window

Start:

```text
left = 0
sum = 0
```

Add `1`:

```text
[1]
sum = 1
```

Add another `1`:

```text
[1,1]
sum = 2
```

Add `4`:

```text
[1,1,4]
sum = 6
```

We found:

```text
sum = target
length = 3
```

Continue.

Add `2`:

```text
[1,1,4,2]
sum = 8
```

Too large, so remove from the left:

```text
remove 1
sum = 7
```

Still too large:

```text
remove 1
sum = 6
```

Now:

```text
[4,2]
length = 2
```

Then add `3`:

```text
[4,2,3]
sum = 9
```

Shrink:

```text
remove 4
sum = 5
```

No valid subarray.

The longest valid subarray was:

```text
[1,1,4]
```

with length `3`.

Therefore:

```text
operations = n - maxLength
           = 5 - 3
           = 2
```

---

## 🧠 Why Are We Finding the Longest Subarray?

This is the most important part to understand.

Suppose:

```text
nums = [1,1,4,2,3]
```

We want to remove elements from the **ends**.

If we leave:

```text
[1,4,2]
```

in the middle, then automatically the removed elements are:

```text
[1] + [3,2]
```

So:

```text
removed sum = total - remaining sum
            = 11 - 6
            = 5
```

The remaining elements must always form a **contiguous subarray**, because we can only remove from the left and right.

Therefore:

```text
Remove from both ends
        ↓
Keep a contiguous middle part
        ↓
Find longest middle part with sum = total - x
        ↓
Remove everything outside it
```

---

## ⚠️ Why `long` Instead of `int`?

The constraints allow:

```text
n = 100000
nums[i] = 10000
```

Therefore the total sum can be:

```text
100000 × 10000 = 1,000,000,000
```

Although this fits in `int`, using `long` is safer for the calculations involving `x` and the total sum.

---

## Complexity

### Time

The `right` pointer moves from left to right once.

The `left` pointer also moves from left to right at most once.

Therefore:

```text
O(n)
```

### Space

We only use a few variables:

```text
O(1)
```

### Final Complexity

```text
Time  : O(n)
Space : O(1)
```

### Key Pattern to Remember

Whenever you see:

> **Remove elements from the left/right and minimize the number of removals**

try asking:

> **What contiguous subarray can I keep?**

For this problem:

```text
minimum removals
        ↓
maximum elements kept
        ↓
longest subarray with sum = total - x
        ↓
Sliding Window
```

This transformation is the main trick behind the problem.
