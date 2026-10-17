# LeetCode 3551: Minimum Swaps to Sort by Digit Sum

Difficulty: Medium Approach: Sorting + HashMap + Cycle Detection Language: Java

## 1. Problem Explanation

We are given an array of distinct positive integers. We need to sort the array using two rules:

1. Sort numbers in increasing order of their digit sum.
2. If two numbers have the same digit sum, sort them in increasing order of their actual values.

We must return the minimum number of swaps needed to obtain the sorted array.

### Example

```
Input:  nums = [18, 43, 34, 16]
Output: 2
```

Calculate the digit sum of each number:

| Number | Digit sum |
| ------ | --------- |
| 18     | 9         |
| 43     | 7         |
| 34     | 7         |
| 16     | 7         |

The sorted array becomes:

```
Original: [18, 43, 34, 16]
Sorted:   [16, 34, 43, 18]
```

We need 2 swaps to obtain the sorted array.

## 2. Java Solution

```

import java.util.Arrays;
import java.util.HashMap;

class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = digitSum(nums[i]);
        }

        Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }
            return Integer.compare(a[0], b[0]);
        });

        int[] sortedPosition = new int[n];

        for (int i = 0; i < n; i++) {
            sortedPosition[i] = arr[i][0];
        }

        HashMap<Integer, Integer> originalIndex = new HashMap<>();

        for (int i = 0; i < n; i++) {
            originalIndex.put(nums[i], i);
        }

        boolean[] visited = new boolean[n];
        int swaps = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i] || nums[i] == sortedPosition[i]) {
                continue;
            }

            int cycleSize = 0;
            int j = i;

            while (!visited[j]) {
                visited[j] = true;
                j = originalIndex.get(sortedPosition[j]);
                cycleSize++;
            }

            swaps += cycleSize - 1;
        }

        return swaps;
    }

    private int digitSum(int num) {
        int sum = 0;

        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }

        return sum;
    }
}

```

## 3. Step-by-step explanation

### Step 1: Store numbers and their digit sums

```
int n = nums.length;
int[][] arr = new int[n][2];

for (int i = 0; i < n; i++) {
    arr[i][0] = nums[i];
    arr[i][1] = digitSum(nums[i]);
}
```

We create a two-dimensional array called `arr`.

- `arr[i][0]` stores the original number.
- `arr[i][1]` stores its digit sum.

For our example, it looks like this:

| Number | Digit sum |
| ------ | --------- |
| 18     | 9         |
| 43     | 7         |
| 34     | 7         |
| 16     | 7         |

Why do we do this?

We need both the number and its digit sum to apply the sorting rules correctly.

### Step 2: Sort the numbers

```
Arrays.sort(arr, (a, b) -> {
    if (a[1] != b[1]) {
        return Integer.compare(a[1], b[1]);
    }
    return Integer.compare(a[0], b[0]);
});
```

This comparator applies both conditions:

- If digit sums differ, compare digit sums.
- If digit sums are equal, compare the original numbers.

After sorting:

```
[16, 7]
[34, 7]
[43, 7]
[18, 9]
```

The numbers are now ordered as:

```
[16, 34, 43, 18]
```

Why use `Integer.compare()`?

It safely compares two integers without relying on subtraction, which can overflow in general.

### Step 3: Create the target sorted array

```
int[] sortedPosition = new int[n];

for (int i = 0; i < n; i++) {
    sortedPosition[i] = arr[i][0];
}
```

We extract the numbers from the sorted two-dimensional array.

```
sortedPosition = [16, 34, 43, 18]
```

Why is this necessary?

We need to know which number belongs at each index in the final array.

### Step 4: Store each number's original index

```
HashMap<Integer, Integer> originalIndex = new HashMap<>();

for (int i = 0; i < n; i++) {
    originalIndex.put(nums[i], i);
}
```

The HashMap stores each number and the index where it appeared in the original array.

For example:

| Number (key) | Original index (value) |
| ------------ | ---------------------- |
| 18           | 0                      |
| 43           | 1                      |
| 34           | 2                      |
| 16           | 3                      |

Why do we use a HashMap?

We need to find the original index of a number quickly. A HashMap provides average \\(O(1)\\) lookup time, avoiding repeated linear searches that could cause TLE.

### Step 5: Detect cycles and count swaps

```
boolean[] visited = new boolean[n];
int swaps = 0;

for (int i = 0; i < n; i++) {
    if (visited[i] || nums[i] == sortedPosition[i]) {
        continue;
    }

    int cycleSize = 0;
    int j = i;

    while (!visited[j]) {
        visited[j] = true;
        j = originalIndex.get(sortedPosition[j]);
        cycleSize++;
    }

    swaps += cycleSize - 1;
}
```

This is the most important part of the solution.

What is a cycle?

A cycle occurs when elements need to move between positions in a circular sequence to reach their correct sorted positions.

For example, in our input:

```
Original: [18, 43, 34, 16]
Target:   [16, 34, 43, 18]
```

The elements form a cycle:

- `18` needs the position currently occupied by `16`.
- `16` needs the position currently occupied by `18`.

Actually, these two numbers form a cycle of length 2, requiring one swap. The remaining `43` and `34` form another cycle of length 2, requiring one more swap.

### Cycle 1

# 18

Index 0

# 16

Index 3

Cycle size = 2, so swaps = 2 − 1 = 1.

### Cycle 2

# 43

Index 1

# 34

Index 2

Cycle size = 2, so swaps = 2 − 1 = 1.

Total minimum swaps

# 2

The formula is:

\\[ \text{Swaps for a cycle} = \text{Cycle size} - 1 \\]

Why subtract 1?

For a cycle of length \\(k\\), we can put one element in its correct position with each swap, leaving one final element to fall into place. Therefore, exactly \\(k-1\\) swaps are sufficient and necessary.

The `visited` array prevents us from processing the same cycle multiple times.

## 4. How the `digitSum()` function works

```
private int digitSum(int num) {
    int sum = 0;

    while (num > 0) {
        sum += num % 10;
        num /= 10;
    }

    return sum;
}
```

For `num = 123`:

| Operation       | Result |
| --------------- | ------ |
| `123 % 10`      | 3      |
| `12 % 10`       | 2      |
| `1 % 10`        | 1      |
| Total digit sum | 6      |

- `% 10` extracts the last digit.
- `/ 10` removes the last digit.
- The loop continues until the number becomes zero.

## 5. Complexity analysis

Let \\(n\\) be the length of the array.

| Operation            | Complexity         |
| -------------------- | ------------------ |
| Calculate digit sums | \\(O(n)\\)         |
| Sort numbers         | \\(O(n \log n)\\)  |
| Build HashMap        | \\(O(n)\\) average |
| Detect cycles        | \\(O(n)\\)         |
| Total time           | \\(O(n \log n)\\)  |
| Extra space          | \\(O(n)\\)         |

## 6. Key takeaways

1. Sorting: Determine the exact target order using digit sum and then numeric value.
2. HashMap: Quickly locate the original index of each number.
3. Cycle detection: Find the minimum swaps without repeatedly searching or swapping elements.
4. Minimum swaps formula: A cycle of length \\(k\\) contributes \\(k-1\\) swaps.

This approach is efficient enough for the constraint \\(n \leq 10^5\\) and avoids the TLE caused by repeatedly searching the array.