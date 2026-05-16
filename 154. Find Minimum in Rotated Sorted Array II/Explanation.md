## Intuition of the Problem

You are given a **sorted array** that has been **rotated**.

Example:

Original sorted array:

```text
[0,1,2,4,5,6,7]
```

After rotation:

```text
[4,5,6,7,0,1,2]
```

The **minimum element** is the point where the sorted order breaks.

---

## What makes this problem hard?

In problem 153 (without duplicates), binary search is straightforward.

But here duplicates exist:

```text
[2,2,2,0,1]
```

or even:

```text
[1,1,1,1,1]
```

Duplicates make it difficult to decide which side is sorted.

That is why worst-case complexity can become **O(n)**.

---

# Main Idea of the Solution

Your code uses this observation:

* Compare every element with the **last element**
* Elements:

  * **greater than last** → belong to left rotated part
  * **less than or equal to last** → belong to right sorted part containing minimum

Example:

```text
nums = [4,5,6,7,0,1,4]
last = 4
```

Classification:

```text
4 <= 4
5 > 4
6 > 4
7 > 4
0 <= 4
1 <= 4
4 <= 4
```

Minimum is the **first element <= last** after rotated section.

---

# Step-by-Step Explanation of Code

```java
class Solution {
    public int findMin(int[] nums) {
```

Function starts.

---

## Step 1: Store Last Element

```java
int n = nums.length - 1;
int last = nums[n];
```

`last` is used as reference for comparisons.

Example:

```text
nums = [4,5,6,7,0,1,4]
last = 4
```

---

## Step 2: Binary Search Range

```java
int left = 0, right = n;
```

We search for minimum between `left` and `right`.

---

## Step 3: Skip Duplicate Values from Beginning

```java
while (left < n && nums[left] == last)
    left++;
```

This is the most important part.

---

### Why do we skip duplicates?

Suppose:

```text
[1,1,1,0,1]
```

Here:

```text
last = 1
```

Now if we compare elements with `last`:

```text
1 == 1
1 == 1
1 == 1
0 < 1
1 == 1
```

We cannot identify rotation properly because duplicates confuse binary search.

So we skip starting duplicates equal to `last`.

After skipping:

```text
left points to 0
```

Now binary search works correctly.

---

# Binary Search Part

```java
while (left < right) {
```

Normal binary search loop.

---

## Mid Calculation

```java
int mid = left + right >> 1;
```

Equivalent to:

```java
int mid = (left + right) / 2;
```

---

# Decision Making

## Case 1:

```java
if (nums[mid] > last)
    left = mid + 1;
```

If current element is greater than `last`,
it belongs to left rotated portion.

So minimum must be on right side.

Example:

```text
[4,5,6,7,0,1,2]
         ^
        mid = 7
```

Since `7 > 2`,
minimum is after mid.

---

## Case 2:

```java
else
    right = mid;
```

If `nums[mid] <= last`,
mid may itself be the minimum.

So we keep it.

---

# Final Answer

```java
return nums[left];
```

At the end, `left` points to smallest element.

---

# Dry Run

## Example

```text
nums = [2,2,2,0,1]
```

---

### Initial

```text
last = 1
left = 0
right = 4
```

No duplicates at start equal to `last`.

---

### Iteration 1

```text
mid = 2
nums[mid] = 2
```

Since:

```text
2 > 1
```

Move right:

```text
left = 3
```

---

### Iteration 2

```text
mid = 3
nums[mid] = 0
```

Since:

```text
0 <= 1
```

Move left boundary:

```text
right = 3
```

Now:

```text
left == right == 3
```

Answer:

```text
nums[3] = 0
```

Correct.

---

# Why This Works

The array has two regions:

```text
Large values | Small values
```

Example:

```text
[4,5,6,7 | 0,1,2]
```

Using `last`:

* left region → `> last`
* right region → `<= last`

Binary search finds first element in right region.

That first element is minimum.

---

# Time Complexity

## Average Case

Binary search:

```text
O(log n)
```

---

## Worst Case

Because of duplicates:

```text
[1,1,1,1,1]
```

We may need to skip many elements.

Worst case:

```text
O(n)
```

---

# Why duplicates affect complexity?

Without duplicates:

* one comparison clearly tells which side is sorted

With duplicates:

```text
nums[mid] == nums[right]
```

You cannot determine direction confidently.

So sometimes we must linearly shrink search space.

That degrades complexity to `O(n)`.

---

# Final Understanding

This solution cleverly:

1. Uses the last element as reference
2. Removes confusing duplicates
3. Applies binary search to find first valid smaller section

It is an optimized version of rotated-array binary search with duplicates handled carefully.
