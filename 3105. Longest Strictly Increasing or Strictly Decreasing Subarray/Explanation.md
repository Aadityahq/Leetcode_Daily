# LeetCode 3105 – Longest Strictly Increasing or Strictly Decreasing Subarray



Easy

Array

Two Pointers



## 1. Problem Explanation

You are given an integer array `nums`.

You need to find the length of the longest contiguous subarray that is either:

1. Strictly Increasing: Every next element must be greater than the previous element.
2. Strictly Decreasing: Every next element must be smaller than the previous element.

Return the maximum length among these subarrays.

Important points:

- A subarray must contain continuous elements.
- Equal elements break both increasing and decreasing sequences.
- A single element is always a valid subarray of length 1.

### Example 1

```
Input: nums = [1, 4, 3, 3, 2]
Output: 2
```

Let's understand:

- Increasing: `[1, 4]` → Length = 2
- Decreasing: `[4, 3]` → Length = 2
- Decreasing: `[3, 2]` → Length = 2

The longest valid subarray has length 2.

### Example 2

```
Input: nums = [3, 3, 3, 3]
Output: 1
```

Since all elements are equal, neither increasing nor decreasing sequences are possible.

Therefore, the answer is 1.

### Example 3

```
Input: nums = [3, 2, 1]
Output: 3
```

The entire array is strictly decreasing.

Therefore, the answer is 3.

## 2. Java Solution (Optimal Approach)

We can solve this problem using a single traversal of the array.

```

class Solution {
    public int longestMonotonicSubarray(int[] nums) {
        int inc = 1;
        int dec = 1;
        int ans = 1;

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] > nums[i - 1]) {
                inc++;
                dec = 1;
            }
            else if (nums[i] < nums[i - 1]) {
                dec++;
                inc = 1;
            }
            else {
                inc = 1;
                dec = 1;
            }

            ans = Math.max(ans, Math.max(inc, dec));
        }

        return ans;
    }
}

```

## 3. Explanation of the Approach (How and Why)

We maintain three variables:

| Variable | Purpose                                            |
| -------- | -------------------------------------------------- |
| `inc`    | Length of the current strictly increasing subarray |
| `dec`    | Length of the current strictly decreasing subarray |
| `ans`    | Maximum length found so far                        |

Initially:

```
int inc = 1;
int dec = 1;
int ans = 1;
```

Why initialize with 1?

Because every individual element is itself a valid increasing and decreasing subarray.

### Step-by-step logic

We start the loop from index `1` because we compare the current element with the previous element.

Case 1: Current element is greater

```
if (nums[i] > nums[i - 1]) {
    inc++;
    dec = 1;
}
```

The increasing sequence continues, so we increase `inc`.

We reset `dec` to 1 because the decreasing sequence is broken.

Case 2: Current element is smaller

```
else if (nums[i] < nums[i - 1]) {
    dec++;
    inc = 1;
}
```

The decreasing sequence continues, so we increase `dec`.

We reset `inc` to 1.

Case 3: Both elements are equal

```
else {
    inc = 1;
    dec = 1;
}
```

Both sequences break because the problem requires strictly increasing or strictly decreasing elements.

Finally, update the maximum:

```
ans = Math.max(ans, Math.max(inc, dec));
```

This keeps track of the longest valid subarray.

## 4. Dry Run

Let's take:

```
nums = [1, 4, 3, 3, 2]
```

Initially:

```
inc = 1
dec = 1
ans = 1
```

| i | nums[i-1] | nums[i] | Condition  | inc | dec | ans |
| - | --------- | ------- | ---------- | --- | --- | --- |
| 1 | 1         | 4       | Increasing | 2   | 1   | 2   |
| 2 | 4         | 3       | Decreasing | 1   | 2   | 2   |
| 3 | 3         | 3       | Equal      | 1   | 1   | 2   |
| 4 | 3         | 2       | Decreasing | 1   | 2   | 2   |

Final Answer: 2

## 5. Complexity Analysis

Time Complexity: O(n)

- We traverse the array only once.
- Each iteration performs constant-time operations.
- Therefore, time complexity is O(n).

Space Complexity: O(1)

- We use only three variables: `inc`, `dec`, and `ans`.
- No additional array or data structure is required.

## 6. Why Does This Approach Work?

The main observation is that we only need to compare adjacent elements.

For every pair:

- If the next element is greater, the increasing sequence continues.
- If the next element is smaller, the decreasing sequence continues.
- If both are equal, both sequences break.

We don't need to generate all possible subarrays because we can maintain the lengths of both valid sequences while traversing the array.

This makes the solution efficient with O(n) time and O(1) space.

## 7. Important Takeaways

1. Understand the difference between a subarray and a subsequence.
2. Use adjacent element comparisons for monotonic subarrays.
3. Reset the counter when the current sequence breaks.
4. Maintain the maximum answer during traversal.
5. This is a simple example of a single-pass array / Two Pointers-style approach.

Interview tip: If an interviewer asks why you don't use nested loops, explain that we only need information about the previous element and the current increasing/decreasing lengths. Therefore, one traversal is sufficient.