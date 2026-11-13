# **LeetCode 3420 – Count Non-Decreasing Subarrays After K Operations**



Hard

Java

Sliding Window + Monotonic Deque



Let's understand this problem from scratch. We will first understand what the question is asking, then why we need a deque, how the algorithm works, and finally dry-run the Java solution.

## **1. Understanding the problem**

You are given:

- An integer array `nums`.
- An integer `k`, representing the maximum number of operations allowed.

In one operation, you can increase any element by `1`.

Your task is to count the number of subarrays that can be converted into a **non-decreasing array** using at most `k` operations.

A non-decreasing array means:

\\[ nums[i] \leq nums[i+1] \\]

In simple words, elements should be in increasing order or equal.

### **Example 1**

```
nums = [6, 3, 1, 2, 4, 4]
k = 7
```

Consider this subarray:

```
[3, 1, 2, 4]
```

It is not non-decreasing because:

- `1` is smaller than `3`.
- `2` is smaller than `3`.

We can increase the elements to get:

```
[3, 3, 3, 4]
```

Operations required:

- `3` → 0 operations.
- `1` → 2 operations.
- `2` → 1 operation.
- `4` → 0 operations.

Total:

\\[ 0+2+1+0=3 \\]

Since `3 <= 7`, this subarray is valid.

Now consider:

```
[6, 3, 1]
```

To make it non-decreasing:

```
[6, 6, 6]
```

Operations:

\\[ (6-6)+(6-3)+(6-1) \\]

\\[ =0+3+5=8 \\]

Since `8 > 7`, this subarray is invalid.

Our job is to count all valid subarrays.

**Expected output**

# **17**

Out of 21 total subarrays, 4 are invalid.

## **2. How do we calculate the operations?**

This is the most important observation.

We can only increase elements. We cannot decrease them.

Therefore, while moving from left to right inside a subarray, every element must be at least as large as the maximum element encountered before it.

For example:

```
[6, 3, 1, 2, 4]
```

Let's calculate the required values:

| Original | Required value | Operations |
| -------- | -------------- | ---------- |
| 6        | 6              | 0          |
| 3        | 6              | 3          |
| 1        | 6              | 5          |
| 2        | 6              | 4          |
| 4        | 6              | 2          |

The resulting array:

```
[6, 6, 6, 6, 6]
```

Total operations:

\\[ 0+3+5+4+2=14 \\]

**Important formula:**

For a subarray starting at index `l`:

\\[ cost(l,r)= \sum\_{i=l}^{r} \left(\max(nums[l],...,nums[i])-nums[i]\right) \\]

We need to count subarrays where:

\\[ cost(l,r)\leq k \\]

## **3. Which approach should we use?**

A brute-force approach would be:

1. Generate every possible subarray.
2. Calculate the operations required.
3. Check whether the cost is at most `k`.
4. Count valid subarrays.

But there are:

\\[ \frac{n(n+1)}{2} \\]

possible subarrays.

For \\(n=100000\\), there are more than 5 billion subarrays.

Brute force is too slow.

We need an optimized approach.

We will use:

- Sliding Window
- Monotonic Deque
- Greedy grouping

Let's understand why each is needed.

## **4. Sliding Window – Why?**

Think about this property:

If a subarray is valid, removing some elements from its right side cannot increase its required operation count.

For example:

```
[6, 3, 1, 2]
```

Its required cost is:

\\[ 0+3+5+4=12 \\]

If we remove the last element:

```
[6, 3, 1]
```

Cost becomes:

\\[ 0+3+5=8 \\]

If we remove another element:

```
[6, 3]
```

Cost becomes:

\\[ 0+3=3 \\]

So the cost never increases when we remove elements from the right.

This gives us an important property:

**For a fixed starting index, valid subarrays form a continuous range of ending positions.**

We can use a sliding window to maintain the longest valid subarray starting at each index.

We process the array from right to left.

We maintain two pointers:

- `i`: Current starting index.
- `j`: Rightmost valid ending index.

If the current window requires more than `k` operations, we remove elements from the right until it becomes valid.

The number of valid subarrays starting at `i` is:

\\[ j-i+1 \\]

We add this to our answer.

But one question remains:

How can we efficiently calculate the operation cost when the window changes?

That's where the monotonic deque comes in.

## **5. Monotonic Deque – Why?**

We use a deque to maintain groups of elements.

Each group stores:

```
[value, count]
```

Where:

- `value` = adjusted value of the group.
- `count` = number of elements in that group.

The groups are maintained in non-increasing order from front to back.

### **Example**

Consider:

```
nums = [6, 3, 1, 2, 4, 4]
```

We process from right to left.

Initially:

```
[4]
```

Add another `4`:

```
[4, 4]
```

Add `2`:

```
[4, 4, 2]
```

Add `1`:

```
[4, 4, 2, 1]
```

Now add `3`.

The elements smaller than `3` immediately to its right need to be increased.

```
2 → 3
1 → 3
```

The deque groups become:

```
[4, 2]
[3, 2]
```

Here:

- `[4, 2]` represents two elements with value `4`.
- `[3, 2]` represents two elements with adjusted value `3`.

The additional operations are:

\\[ (3-2)\times1+(3-1)\times1=3 \\]

The deque allows us to calculate this efficiently without visiting every element individually.

### **Why do we multiply by count?**

Suppose we have:

```
[3, 3, 3]
```

We need to increase all three elements to `6`.

Operations:

\\[ (6-3)\times3=9 \\]

Therefore, our update formula is:

```
cost += (long) (num - nextNum) * nextCount;
```

This is the main mathematical operation in the solution.

## **6. Complete Java solution**

```

import java.util.*;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {

        int n = nums.length;

        // Stores [value, count]
        Deque<int[]> dq = new ArrayDeque<>();

        long cost = 0;
        long ans = 0;

        int j = n - 1;

        // Traverse from right to left
        for (int i = n - 1; i >= 0; i--) {

            int num = nums[i];
            int count = 1;

            // Merge smaller groups
            while (!dq.isEmpty() && dq.peekLast()[0] < num) {

                int[] last = dq.removeLast();

                int nextNum = last[0];
                int nextCount = last[1];

                // Calculate additional operations
                cost += (long) (num - nextNum) * nextCount;

                count += nextCount;
            }

            // Add the current group
            dq.offerLast(new int[]{num, count});

            // Shrink window if cost exceeds k
            while (cost > k) {

                int[] first = dq.removeFirst();

                int rightmostNum = first[0];
                int rightmostCount = first[1];

                // Remove contribution of the rightmost element
                cost -= (long) (rightmostNum - nums[j]);

                j--;

                // Keep remaining elements in the group
                if (rightmostCount > 1) {
                    dq.offerFirst(
                        new int[]{rightmostNum, rightmostCount - 1}
                    );
                }
            }

            // Count valid subarrays starting at i
            ans += j - i + 1;
        }

        return ans;
    }
}

```

## **7. Understanding the code line by line**

### **Step 1: Initialize variables**

```
Deque<int[]> dq = new ArrayDeque<>();

long cost = 0;
long ans = 0;

int j = n - 1;
```

- `dq` stores groups of adjusted values.
- `cost` stores the total operations required by the current window.
- `ans` stores the number of valid subarrays.
- `j` represents the right boundary of the current window.

Why use `long`?

Because the number of subarrays can exceed the maximum value of Java `int`.

### **Step 2: Traverse backwards**

```
for (int i = n - 1; i >= 0; i--)
```

Why right to left?

Because when we add a new element on the left, it can increase the required values of smaller elements to its right.

This makes it possible to merge groups using the deque.

### **Step 3: Merge smaller groups**

```
while (!dq.isEmpty() && dq.peekLast()[0] < num)
```

We check whether the last group has a smaller value than the current number.

If yes, its elements must be increased.

```
int[] last = dq.removeLast();

int nextNum = last[0];
int nextCount = last[1];
```

We remove the last group and retrieve its value and count.

Then:

```
cost += (long) (num - nextNum) * nextCount;

count += nextCount;
```

We calculate the extra operations and merge the group into the current group.

### **Step 4: Add the current group**

```
dq.offerLast(new int[]{num, count});
```

Now the deque contains the updated groups.

### **Step 5: Maintain the valid window**

```
while (cost > k)
```

If the required operations exceed `k`, we must shrink the window.

```
int[] first = dq.removeFirst();
```

We remove the rightmost group.

Why the first group?

Because we are processing from right to left, and the deque's front represents the rightmost group.

Next:

```
cost -= (long) (rightmostNum - nums[j]);
j--;
```

We remove the contribution of the rightmost element from the operation cost.

If the group contains multiple elements, we keep the remaining elements:

```
if (rightmostCount > 1) {
    dq.offerFirst(
        new int[]{rightmostNum, rightmostCount - 1}
    );
}
```

### **Step 6: Count valid subarrays**

```
ans += j - i + 1;
```

This is the final counting trick.

For every starting index `i`, we have found the rightmost valid ending index `j`.

Therefore, all subarrays ending at:

```
i, i+1, i+2, ..., j
```

are valid.

Their total count is:

\\[ j-i+1 \\]

We add this to our answer.

## **8. Complete dry run**

Let's take:

```
nums = [6, 3, 1, 2, 4, 4]
k = 7
```

We start from the last index.

| i | Current number | Cost | j | Added to answer |
| - | -------------- | ---- | - | --------------- |
| 5 | 4              | 0    | 5 | 1               |
| 4 | 4              | 0    | 5 | 2               |
| 3 | 2              | 0    | 5 | 3               |
| 2 | 1              | 0    | 5 | 4               |
| 1 | 3              | 3    | 5 | 5               |
| 0 | 6              | 3    | 1 | 2               |

Let's understand the most important iteration.

**When i = 0:**

The current number is `6`.

Before adding it, the deque represents:

```
[4, 2]
[2, 1]
[1, 1]
```

We merge all smaller groups into `6`.

Additional cost:

\\[ (6-4)\times2=4 \\]

\\[ (6-2)\times1=4 \\]

\\[ (6-1)\times1=5 \\]

Total cost:

\\[ 4+4+5=13 \\]

Wait: the first group actually represents two `4`s, so the complete initial cost is:

\\[ 13 \\]

Since `13 > 7`, we remove elements from the right until the window becomes valid.

The window eventually becomes:

```
[6, 3]
```

Cost:

\\[ 6-3=3 \\]

Now:

\\[ j=1 \\]

Valid subarrays starting at index `0`:

```
[6]
[6, 3]
```

Count:

\\[ 1-0+1=2 \\]

Final answer:

\\[ 1+2+3+4+5+2=\boxed{17} \\]

Expected output: 17

## **9. Complexity analysis**

| Complexity | Value      | Explanation                                      |
| ---------- | ---------- | ------------------------------------------------ |
| Time       | \\(O(n)\\) | Each group is inserted and removed at most once. |
| Space      | \\(O(n)\\) | Deque can store up to n groups.                  |

Why is the time complexity O(n)?

Although we have nested `while` loops, every group is pushed into the deque once and removed at most once. Therefore, the total number of deque operations is linear.

## **10. What you should remember for interviews**

| Concept         | Purpose                                     |
| --------------- | ------------------------------------------- |
| Sliding Window  | Maintains a valid subarray                  |
| Monotonic Deque | Maintains groups of adjusted values         |
| Greedy          | Increases elements only when necessary      |
| Grouping        | Calculates multiple elements' cost together |
| Two pointers    | Finds valid boundaries efficiently          |

**The entire problem comes down to one observation:**

When we add a larger element to the left, smaller elements on its right must be increased to match the required non-decreasing order. We group those elements in a deque to calculate the additional operations efficiently.

## **11. Test your understanding**

**Q1. Why do we traverse the array from right to left?**

To sort the array

To merge smaller groups when a larger element is added on the left

To avoid using a deque

**Q2. What does each deque pair [value, count] represent?**

Original index and value

Maximum and minimum

Adjusted value and number of elements in that group

**Q3. Why do we add j - i + 1 to the answer?**

It counts all valid subarrays starting at i

It counts all elements in the array

It counts invalid subarrays

Check answers