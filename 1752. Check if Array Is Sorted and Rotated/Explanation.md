# 1752. Check if Array Is Sorted and Rotated

## Problem Explanation

You are given an array `nums`.

You need to check whether this array was:

1. Originally sorted in **non-decreasing order**
   (means increasing order, duplicates allowed)

2. Then rotated some number of times.

---

## What is Rotation?

If a sorted array is:

```text
[1,2,3,4,5]
```

After rotation, it can become:

```text
[3,4,5,1,2]
```

Here the sorted order breaks only once:

* `5 > 1`

That single breaking point happens because of rotation.

---

# Main Observation

A sorted and rotated array can have **at most one place** where:

```text
nums[i] > nums[(i+1) % n]
```

Why `% n`?

Because we also compare:

* last element with first element

This helps detect circular rotation.

---

# Example Walkthrough

## Example 1

```text
nums = [3,4,5,1,2]
```

Check adjacent elements:

| Pair   | Condition   |
| ------ | ----------- |
| 3 <= 4 | OK          |
| 4 <= 5 | OK          |
| 5 > 1  | Break point |
| 1 <= 2 | OK          |
| 2 <= 3 | OK          |

Only **1 break point** → Valid.

Answer = `true`

---

## Example 2

```text
nums = [2,1,3,4]
```

Check:

| Pair   | Condition   |
| ------ | ----------- |
| 2 > 1  | Break point |
| 1 <= 3 | OK          |
| 3 <= 4 | OK          |
| 4 > 2  | Break point |

2 break points → Not possible.

Answer = `false`

---

# Approach

1. Count how many times order decreases.
2. If decreases are more than 1 → return false.
3. Else → return true.

---

# Java Solution

```java
class Solution {
    public boolean check(int[] nums) {
        int count = 0;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            
            // Compare current with next element
            if (nums[i] > nums[(i + 1) % n]) {
                count++;
            }

            // More than one break means not sorted rotated
            if (count > 1) {
                return false;
            }
        }

        return true;
    }
}
```

---

# Dry Run

## Input

```text
nums = [3,4,5,1,2]
```

## Iteration

| i | nums[i] | next | Condition | count |
| - | ------- | ---- | --------- | ----- |
| 0 | 3       | 4    | false     | 0     |
| 1 | 4       | 5    | false     | 0     |
| 2 | 5       | 1    | true      | 1     |
| 3 | 1       | 2    | false     | 1     |
| 4 | 2       | 3    | false     | 1     |

Final count = 1 → `true`

---

# Why This Works

A sorted rotated array has:

* one increasing sequence
* and one rotation point

So the order can break only once.

If it breaks more than once, then the array cannot come from a sorted array after rotation.

---

# Time Complexity

```text
O(n)
```

We traverse the array once.

---

# Space Complexity

```text
O(1)
```

No extra space used.
