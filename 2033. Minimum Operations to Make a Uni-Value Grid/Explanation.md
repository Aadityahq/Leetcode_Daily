# Problem Explanation

You are given:

* A 2D grid of integers
* A number `x`

In **one operation**, you can:

* add `x` to any element
  OR
* subtract `x` from any element

Your task is to make **all elements equal** using the minimum number of operations.

If it is impossible, return `-1`.

---

# Understanding the Operation

Suppose:

```text id="x1h90v"
x = 2
```

If a number is `2`, then after operations it can become:

```text id="n2zzdg"
2 → 4 → 6 → 8
2 → 0 → -2
```

Notice something important:

* Even numbers always remain even.
* Odd numbers always remain odd.

Because adding/subtracting `2` never changes parity.

---

# Core Idea

For all numbers to become equal:

## Their remainder when divided by `x` must be same.

Why?

Because adding/subtracting `x` does not change remainder.

---

# Example 1

```text id="fy40gc"
grid = [[2,4],
        [6,8]]

x = 2
```

Remainders:

```text id="n5w8z7"
2 % 2 = 0
4 % 2 = 0
6 % 2 = 0
8 % 2 = 0
```

All same → possible.

---

# Example 2

```text id="fctn8z"
grid = [[1,2],
        [3,4]]

x = 2
```

Remainders:

```text id="92ef7h"
1 % 2 = 1
2 % 2 = 0
```

Different remainders.

Impossible.

Return:

```text id="9up85z"
-1
```

---

# Goal After Feasibility

Now suppose it is possible.

We need:

## Minimum operations

That means we must choose a target value carefully.

---

# Why Median?

Suppose numbers are:

```text id="vhn7ol"
[2,4,6,8]
```

Possible targets:

* 2
* 4
* 6
* 8

Let's test.

---

## Target = 2

Operations:

```text id="c5grj4"
2 → 2 = 0
4 → 2 = 1
6 → 2 = 2
8 → 2 = 3
```

Total:

```text id="5i2xw9"
6
```

---

## Target = 4

```text id="4a4mbg"
2 → 4 = 1
4 → 4 = 0
6 → 4 = 1
8 → 4 = 2
```

Total:

```text id="5t4tfc"
4
```

---

## Target = 6

```text id="yzlyu4"
2 → 6 = 2
4 → 6 = 1
6 → 6 = 0
8 → 6 = 1
```

Total:

```text id="4ckhpi"
4
```

Minimum is achieved near the middle values.

That middle value is called the:

# Median

---

# Important Mathematical Property

Median minimizes:

[
|a_1 - t| + |a_2 - t| + ... + |a_n - t|
]

Since operations are based on absolute difference:

[
\frac{|num - target|}{x}
]

median gives minimum operations.

---

# Full Solution Approach

---

# Step 1: Convert 2D grid into 1D array

Example:

```text id="4r61hc"
[[2,4],
 [6,8]]
```

becomes:

```text id="kz2pof"
[2,4,6,8]
```

Why?

Because handling 1D array is easier.

---

# Step 2: Check if solution is possible

Take remainder of first element:

```java id="kkpr3x"
remainder = nums.get(0) % x;
```

Check every number:

```java id="h2z0ai"
num % x == remainder
```

If not:

```java id="j0zy50"
return -1;
```

---

# Step 3: Sort the array

```text id="7klbd8"
[2,4,6,8]
```

Sorting helps find median.

---

# Step 4: Find median

```java id="c4w22j"
median = nums.get(nums.size()/2);
```

For:

```text id="08c9s1"
[2,4,6,8]
```

median becomes:

```text id="8m4lnm"
6
```

---

# Step 5: Count operations

Formula:

[
operations = \frac{|num - median|}{x}
]

Code:

```java id="6im1ki"
operations += Math.abs(num - median) / x;
```

---

# Dry Run

## Input

```text id="2tckpq"
grid = [[2,4],[6,8]]
x = 2
```

Flatten:

```text id="5ccwln"
[2,4,6,8]
```

Check remainder:

```text id="8q3x4j"
all % 2 = 0
```

Possible.

Sort:

```text id="9wmu3g"
[2,4,6,8]
```

Median:

```text id="k5z3lq"
6
```

Operations:

```text id="x6pq1x"
|2-6|/2 = 2
|4-6|/2 = 1
|6-6|/2 = 0
|8-6|/2 = 1
```

Total:

```text id="g8dzcm"
4
```

Answer:

```text id="4e1t74"
4
```

---

# Java Code

```java id="glt86p"
import java.util.*;

class Solution {
    public int minOperations(int[][] grid, int x) {

        List<Integer> nums = new ArrayList<>();

        // Step 1: Flatten grid
        for (int[] row : grid) {
            for (int num : row) {
                nums.add(num);
            }
        }

        // Step 2: Check feasibility
        int remainder = nums.get(0) % x;

        for (int num : nums) {
            if (num % x != remainder) {
                return -1;
            }
        }

        // Step 3: Sort array
        Collections.sort(nums);

        // Step 4: Find median
        int median = nums.get(nums.size() / 2);

        // Step 5: Count operations
        int operations = 0;

        for (int num : nums) {
            operations += Math.abs(num - median) / x;
        }

        return operations;
    }
}
```

---

# Time Complexity

## Flattening

[
O(m \times n)
]

## Sorting

[
O(N \log N)
]

where:

[
N = m \times n
]

Overall:

[
O(N \log N)
]

---

# Space Complexity

We store all elements in array/list:

[
O(N)
]
