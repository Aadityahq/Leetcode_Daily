# **LeetCode 3364 – Minimum Positive Sum Subarray**



Easy

Prefix Sum

Array



## **1. Problem Explanation**

You are given:

- An integer array `nums`.
- Two integers `l` and `r`.

You need to find a **contiguous subarray** whose:

1. Length is between `l` and `r` (inclusive).
2. Sum is greater than `0`.
3. Sum is the smallest possible positive sum.

If no such subarray exists, return `-1`.

### **Example 1**

```
Input: nums = [3, -2, 1, 4], l = 2, r = 3

Output: 1
```

Let's check the valid subarrays:

| Subarray   | Length | Sum |
| ---------- | ------ | --- |
| [3, -2]    | 2      | 1   |
| [-2, 1]    | 2      | -1  |
| [1, 4]     | 2      | 5   |
| [3, -2, 1] | 3      | 2   |
| [-2, 1, 4] | 3      | 3   |

The positive sums are `1, 5, 2, 3`.

The smallest positive sum is **1**.

Therefore, answer = `1`.

## **2. Java Solution (Brute Force)**

Since the constraints are small (`nums.length <= 100`), we can solve this problem using two nested loops.

```

class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {

        int n = nums.size();
        int ans = Integer.MAX_VALUE;

        // Try every starting index
        for (int i = 0; i < n; i++) {

            int sum = 0;

            // Try different subarray lengths
            for (int j = i; j < n; j++) {

                sum += nums.get(j);

                int len = j - i + 1;

                // Check if length is within [l, r]
                if (len >= l && len <= r) {

                    // We need the smallest positive sum
                    if (sum > 0) {
                        ans = Math.min(ans, sum);
                    }
                }

                // No need to check longer subarrays
                if (len >= r) {
                    break;
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}

```

*Note: LeetCode's Java method signature uses `List<Integer>` for this problem.*

## **3. Understanding the Approach Step by Step**

Let's understand why we use two loops.

Consider:

```
nums = [3, -2, 1, 4]
l = 2
r = 3
```

**Step 1: Initialize the answer**

```
int ans = Integer.MAX_VALUE;
```

Why?

We need to find the minimum positive sum.

Initially, we don't have any valid answer, so we use the maximum possible integer.

**Step 2: Choose the starting index**

```
for (int i = 0; i < n; i++)
```

The outer loop selects where our subarray starts.

For example:

```
i = 0 → [3, ...]
i = 1 → [-2, ...]
i = 2 → [1, ...]
i = 3 → [4, ...]
```

**Step 3: Calculate the subarray sum**

```
int sum = 0;

for (int j = i; j < n; j++) {
    sum += nums.get(j);
}
```

The inner loop extends the subarray one element at a time.

For `i = 0`:

| j | Subarray      | Sum | Length |
| - | ------------- | --- | ------ |
| 0 | [3]           | 3   | 1      |
| 1 | [3, -2]       | 1   | 2      |
| 2 | [3, -2, 1]    | 2   | 3      |
| 3 | [3, -2, 1, 4] | 6   | 4      |

**Step 4: Check the valid length**

```
if (len >= l && len <= r)
```

Only subarrays whose lengths are between `l` and `r` are considered.

Here:

```
l = 2
r = 3
```

So, length 1 and length 4 are ignored.

**Step 5: Check for the minimum positive sum**

```
if (sum > 0) {
    ans = Math.min(ans, sum);
}
```

Why `sum > 0`?

Because the problem specifically asks for a positive sum.

Why `Math.min()`?

It compares the current valid sum with our previous answer and keeps the smaller one.

**Step 6: Return the answer**

```
return ans == Integer.MAX_VALUE ? -1 : ans;
```

If we never find a valid positive sum, return `-1`.

Otherwise, return the minimum positive sum.

## **4. Dry Run**

Input:

```
nums = [3, -2, 1, 4]
l = 2
r = 3
```

Initially:

```
ans = Integer.MAX_VALUE
```

Valid subarrays:

| Subarray   | Sum | Positive? | Updated answer |
| ---------- | --- | --------- | -------------- |
| [3, -2]    | 1   | Yes       | 1              |
| [3, -2, 1] | 2   | Yes       | 1              |
| [-2, 1]    | -1  | No        | 1              |
| [-2, 1, 4] | 3   | Yes       | 1              |
| [1, 4]     | 5   | Yes       | 1              |

Final result:

```
Output: 1
```

## **5. Complexity Analysis**

Let `n` be the length of the array.

| Complexity | Value                      | Reason                        |
| ---------- | -------------------------- | ----------------------------- |
| Time       | O(n × r), worst case O(n²) | Two nested loops              |
| Space      | O(1)                       | Only a few variables are used |

Since `n <= 100`, this solution is efficient enough.

## **6. Important Concepts to Remember**

- **Nested loops:** Generate all possible subarrays.
- **Subarray:** A contiguous part of an array.
- **Running sum:** Add each new element instead of recalculating the entire sum.
- **Math.min():** Maintain the smallest positive sum.
- **Boundary conditions:** Only consider lengths between `l` and `r`.

### **Interview explanation**

If an interviewer asks you to explain your approach, you can say:

"I use a brute-force approach with two nested loops. The outer loop selects the starting index, and the inner loop expands the subarray while maintaining its running sum. For every subarray, I check whether its length is within the given range and whether its sum is positive. If both conditions are satisfied, I update my answer with the minimum sum. Finally, if no valid subarray is found, I return -1."

**My recommendation:** First understand this brute-force solution properly. For this problem, there is no need to use advanced data structures because the array size is limited to 100.