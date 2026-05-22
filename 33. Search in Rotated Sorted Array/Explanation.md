# 33. Search in Rotated Sorted Array

## Problem Explanation

You are given a **sorted array** that has been **rotated** at some pivot.

Example:

Original sorted array:

```text
[0,1,2,4,5,6,7]
```

After rotation:

```text
[4,5,6,7,0,1,2]
```

You need to find the index of a target element in **O(log n)** time.

---

## Key Observation

Even after rotation:

* One half of the array is always sorted.
* Using Binary Search, we can identify:

  * which half is sorted
  * whether the target lies in that half

This allows us to discard half of the array every time.

---

# How Binary Search Works Here

At every step:

```text
mid = (low + high) / 2
```

Now check:

* If `nums[mid] == target`
  → return `mid`

Otherwise:

* Either left half is sorted
* Or right half is sorted

---

# Example Walkthrough

Array:

```text
[4,5,6,7,0,1,2]
```

Target:

```text
0
```

---

## Step 1

```text
low = 0
high = 6
mid = 3
nums[mid] = 7
```

Left half:

```text
[4,5,6,7]
```

is sorted because:

```text
nums[low] <= nums[mid]
```

Now check if target lies in this range:

```text
4 <= 0 <= 7 ❌
```

So target is in right half.

Move:

```text
low = mid + 1
```

---

## Step 2

```text
low = 4
high = 6
mid = 5
nums[mid] = 1
```

Left half:

```text
[0,1]
```

is sorted.

Check:

```text
0 <= 0 <= 1 ✅
```

So target is inside left half.

Move:

```text
high = mid - 1
```

---

## Step 3

```text
low = 4
high = 4
mid = 4
nums[mid] = 0
```

Found target.

Answer:

```text
4
```

---

# Intuition

The important trick is:

## If Left Half is Sorted

Condition:

```java
nums[low] <= nums[mid]
```

Then:

* if target lies between `nums[low]` and `nums[mid]`
  → search left
* else
  → search right

---

## Else Right Half is Sorted

Then:

* if target lies between `nums[mid]` and `nums[high]`
  → search right
* else
  → search left

---

# Java Solution

```java
class Solution {
    public int search(int[] nums, int target) {
        
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            // Target found
            if (nums[mid] == target) {
                return mid;
            }

            // Left half is sorted
            if (nums[low] <= nums[mid]) {

                // Target lies in left half
                if (target >= nums[low] && target < nums[mid]) {
                    high = mid - 1;
                } 
                else {
                    low = mid + 1;
                }
            }

            // Right half is sorted
            else {

                // Target lies in right half
                if (target > nums[mid] && target <= nums[high]) {
                    low = mid + 1;
                } 
                else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }
}
```

---

# Why This Works

At every iteration:

* One half is guaranteed to be sorted.
* We check whether the target belongs to that sorted half.
* Then discard the other half.

This is exactly what Binary Search does:

* reduce search space by half every time.

So complexity becomes:

# Time Complexity

```text
O(log n)
```

Because we halve the search space each iteration.

---

# Space Complexity

```text
O(1)
```

No extra space used.

---

# Important Edge Cases

## Single Element

```text
nums = [1], target = 0
```

Returns:

```text
-1
```

---

## No Rotation

```text
[1,2,3,4,5]
```

Still works like normal binary search.

---

## Target Not Present

```text
[4,5,6,7,0,1,2], target = 3
```

Returns:

```text
-1
```

---

# Interview Explanation (Short Version)

You can say:

> Since the array is rotated, one half of the array is always sorted.
> Using binary search, I identify the sorted half and check whether the target lies inside it.
> Based on that, I discard one half of the array each time, achieving O(log n) complexity.
