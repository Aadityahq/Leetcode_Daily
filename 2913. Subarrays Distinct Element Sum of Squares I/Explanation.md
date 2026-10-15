# LeetCode 2913: Subarrays Distinct Element Sum of Squares I

## 1. Understanding the problem

You are given a list of integers `nums`. Your task is to find all possible non-empty contiguous subarrays.

For each subarray:

1. Count the number of distinct elements.
2. Calculate the square of that count.
3. Add the square to the total sum.

Finally, return the total sum.

Example:

```
nums = [1, 2, 1]
```

The distinct elements of `[1, 2, 1]` are `1` and `2`.

- Distinct count = 2
- Square = \\(2 \times 2 = 4\\)

We repeat this process for every subarray.

## 2. Java solution

```

import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    public int sumCounts(List<Integer> nums) {
        int sum = 0;
        int n = nums.size();

        for (int i = 0; i < n; i++) {
            Set<Integer> set = new HashSet<>();

            for (int j = i; j < n; j++) {
                set.add(nums.get(j));

                int count = set.size();
                sum += count * count;
            }
        }

        return sum;
    }
}

```

## 3. Explanation of the solution: How and why?

### Step 1: Initialize variables

```
int sum = 0;
int n = nums.size();
```

- `sum` stores the final answer. Initially, it is `0`.
- `n` stores the total number of elements in the list.

Why? We need to process all subarrays and add their squared distinct counts.

Because `nums` is a `List<Integer>`, we use `nums.size()` instead of `nums.length`.

### Step 2: Choose the starting index

```
for (int i = 0; i < n; i++) {
    Set<Integer> set = new HashSet<>();
```

The outer loop selects the starting index of each subarray.

For `[1, 2, 1]`, the starting indices are `0`, `1`, and `2`.

We create a new `HashSet` for every starting index because each starting position must have its own distinct elements.

Why use a HashSet?

A `HashSet` stores only unique values. If the same value is inserted again, it does not increase the set's size.

For example, inserting `1`, `2`, and `1` results in `{1, 2}`, whose size is `2`.

### Step 3: Generate every subarray

```
for (int j = i; j < n; j++) {
    set.add(nums.get(j));
```

The inner loop extends the subarray one element at a time.

For example, when `i = 0`, the generated subarrays are:

```
[1]
[1, 2]
[1, 2, 1]
```

We use `nums.get(j)` because `nums` is a list.

Each time the loop runs, the current element is added to the set.

Why? This allows us to update the distinct elements without creating a new set for every subarray.

### Step 4: Count distinct elements and calculate the square

```
int count = set.size();
sum += count * count;
```

The `set.size()` method gives the number of distinct elements in the current subarray.

For example:

| Subarray    | Distinct count | Square | Running sum |
| ----------- | -------------- | ------ | ----------- |
| `[1]`       | 1              | 1      | 1           |
| `[1, 2]`    | 2              | 4      | 5           |
| `[1, 2, 1]` | 2              | 4      | 9           |

We add the square of the distinct count to `sum`.

Why `count * count`? The problem asks for the square of the distinct count, not just the count.

### Step 5: Return the final answer

```
return sum;
```

Once both loops finish, every possible subarray has been processed.

For `nums = [1, 2, 1]`, the remaining subarrays are `[2]`, `[2, 1]`, and `[1]`.

Their squared counts are `1`, `4`, and `1`.

Therefore, the final answer is:

\\[ 1+4+4+1+4+1=\boxed{15} \\]

## 4. Complete dry run

| `i` | `j` | Subarray    | Set      | Count² | Sum |
| --- | --- | ----------- | -------- | ------ | --- |
| 0   | 0   | `[1]`       | `{1}`    | 1      | 1   |
| 0   | 1   | `[1, 2]`    | `{1, 2}` | 4      | 5   |
| 0   | 2   | `[1, 2, 1]` | `{1, 2}` | 4      | 9   |
| 1   | 1   | `[2]`       | `{2}`    | 1      | 10  |
| 1   | 2   | `[2, 1]`    | `{1, 2}` | 4      | 14  |
| 2   | 2   | `[1]`       | `{1}`    | 1      | 15  |

Output: `15`

## 5. Complexity analysis

- Time complexity: \\(O(n^2)\\), because we use two nested loops and HashSet operations take \\(O(1)\\) on average.
- Space complexity: \\(O(n)\\), because the HashSet can store up to `n` distinct elements.

## 6. Key takeaway

Remember these four steps for similar subarray problems:

1. Use the outer loop to select the starting index.
2. Use the inner loop to extend the subarray.
3. Use a `HashSet` to track distinct elements.
4. Square the set's size and add it to the result.

The main idea is to maintain the distinct elements while extending each subarray, rather than recounting them from scratch every time.