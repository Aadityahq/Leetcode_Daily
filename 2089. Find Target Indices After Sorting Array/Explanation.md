# LeetCode 2089: Find Target Indices After Sorting Array

Difficulty: Easy Language: Java Approach: Sorting + Linear Search

## 1. Understand the problem

You are given an integer array `nums` and an integer `target`.

Your task is to:

1. Sort the array in non-decreasing order (smallest to largest).
2. Find all indices where the element is equal to `target`.
3. Return those indices in increasing order.
4. If the target does not exist, return an empty list.

### Example

INPUT

`nums = [1, 2, 5, 2, 3]`

`target = 2`

STEP 1 — SORT THE ARRAY

## 1

0

## 2

1

## 2

2

## 3

3

## 5

4

Numbers below each element are their indices.

STEP 2 — FIND THE TARGET

The target is `2`. It appears at index `1` and index `2`.

# Output: [1, 2]

Important: We return indices after sorting, not the indices from the original array.

## 2. Java solution

```

import java.util.*;

class Solution {
    public List<Integer> targetIndices(int[] nums, int target) {
        Arrays.sort(nums);

        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                result.add(i);
            }
        }

        return result;
    }
}

```

This code uses the exact method name required by LeetCode: `targetIndices`.

## 3. Explanation: How and why does it work?

### Step 1: Sort the array

```
Arrays.sort(nums);
```

Why? The problem asks us to find the target's indices after sorting the array.

For example:

`[1, 2, 5, 2, 3]` becomes `[1, 2, 2, 3, 5]`.

Sorting places smaller numbers before larger numbers.

### Step 2: Create an empty list

```
List<Integer> result = new ArrayList<>();
```

Why? There may be multiple occurrences of the target. We need a list to store all their indices.

Initially:

`result = []`

### Step 3: Traverse the array

```
for (int i = 0; i < nums.length; i++) {
```

Why? We need to check every index in the sorted array.

Here, `i` represents the current index, starting from `0`.

### Step 4: Check whether the element equals the target

```
if (nums[i] == target) {
    result.add(i);
}
```

Why? If the current element equals the target, its index is a valid answer.

For example, when `target = 2`:

- At index `0`, the value is `1`. Ignore it.
- At index `1`, the value is `2`. Add index `1`.
- At index `2`, the value is `2`. Add index `2`.
- At index `3`, the value is `3`. Ignore it.
- At index `4`, the value is `5`. Ignore it.

The list becomes `[1, 2]`.

### Step 5: Return the result

```
return result;
```

We return the list containing all matching indices. If no element matches the target, the list remains empty.

## 4. Dry run

Input:

```
nums = [1, 2, 5, 2, 3]
target = 2
```

After sorting:

```
nums = [1, 2, 2, 3, 5]
```

| Index `i` | `nums[i]` | Is it equal to `2`? | Result   |
| --------- | --------- | ------------------- | -------- |
| 0         | 1         | No                  | `[]`     |
| 1         | 2         | Yes                 | `[1]`    |
| 2         | 2         | Yes                 | `[1, 2]` |
| 3         | 3         | No                  | `[1, 2]` |
| 4         | 5         | No                  | `[1, 2]` |

Final answer: `[1, 2]`

## 5. Complexity analysis

Let \\(n\\) be the length of the array.

- Time complexity: \\(O(n\log n)\\) for sorting, followed by \\(O(n)\\) for traversal. Overall, \\(O(n\log n)\\).
- Auxiliary space: The sorting implementation may use additional space; the result list requires up to \\(O(n)\\) space.

## 6. Can we solve it without sorting?

Yes! Since the problem only asks for the indices after sorting, we can count two things in the original array:

- How many elements are smaller than `target`?
- How many elements are equal to `target`?

The number of smaller elements tells us the first index of the target after sorting. We then add the next consecutive indices for each occurrence of the target.

For example, if there are two elements smaller than `target = 3` and three occurrences of `3`, the answer is `[2, 3, 4]`.

The sorting solution is easier to understand and is perfectly suitable for this problem. The counting solution is a useful optimization to learn next.