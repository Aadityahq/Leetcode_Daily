# 153. Find Minimum in Rotated Sorted Array

## Problem Explanation

You are given a **sorted array** that has been **rotated**.

Example:

Original sorted array:

```text
[1,2,3,4,5,6,7]
```

After rotating 4 times:

```text
[4,5,6,7,1,2,3]
```

The task is to find the **minimum element** in the array in **O(log n)** time.

---

## Key Observation

In a rotated sorted array:

* One part is always sorted.
* The minimum element is the **pivot point** where rotation happened.

Example:

```text
[4,5,6,7,0,1,2]
           ↑
         minimum
```

We can use **Binary Search** to efficiently find it.

---

# Binary Search Idea

We compare the middle element with the rightmost element.

### Case 1:

If `nums[mid] > nums[right]`

That means the minimum lies in the **right half**.

Example:

```text
[4,5,6,7,0,1,2]
       mid
                 right
```

Since `7 > 2`, minimum must be after mid.

Move:

```java
left = mid + 1;
```

---

### Case 2:

If `nums[mid] < nums[right]`

That means the minimum is in the **left half including mid**.

Example:

```text
[0,1,2,4,5]
     mid
         right
```

Move:

```java
right = mid;
```

---

## Algorithm

1. Initialize:

   * `left = 0`
   * `right = n - 1`

2. While `left < right`

   * Find `mid`
   * Compare `nums[mid]` with `nums[right]`
   * Reduce search space

3. At the end:

   * `left == right`
   * This index contains the minimum element.

---

# Java Solution

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            // Minimum is in right half
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            }
            // Minimum is in left half including mid
            else {
                right = mid;
            }
        }

        return nums[left];
    }
}
```

---

# Dry Run

Input:

```text
nums = [4,5,6,7,0,1,2]
```

### Step 1

```text
left = 0
right = 6
mid = 3

nums[mid] = 7
nums[right] = 2
```

Since `7 > 2`

```text
left = mid + 1 = 4
```

---

### Step 2

```text
left = 4
right = 6
mid = 5

nums[mid] = 1
nums[right] = 2
```

Since `1 < 2`

```text
right = mid = 5
```

---

### Step 3

```text
left = 4
right = 5
mid = 4

nums[mid] = 0
nums[right] = 1
```

Since `0 < 1`

```text
right = 4
```

Now:

```text
left == right == 4
```

Answer:

```text
nums[4] = 0
```

---

# Time Complexity

Binary Search reduces the search space by half each time:

```text
O(log n)
```

---

# Space Complexity

```text
O(1)
```

No extra space used.

---

# Important Interview Point

Why compare with `nums[right]`?

Because:

* The right side helps determine which half is properly sorted.
* If `nums[mid] > nums[right]`, rotation point lies to the right.
* Otherwise, minimum lies on left side including mid.
