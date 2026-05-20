# 2657. Find the Prefix Common Array of Two Arrays

## Problem Explanation

You are given two permutations `A` and `B`.

A **prefix common array** `C` means:

* `C[i]` = count of numbers that have appeared in **both** arrays from index `0` to `i`.

### Example

```text
A = [1,3,2,4]
B = [3,1,2,4]
```

### At each index:

| i | Prefix of A | Prefix of B | Common Numbers | C[i] |
| - | ----------- | ----------- | -------------- | ---- |
| 0 | [1]         | [3]         | none           | 0    |
| 1 | [1,3]       | [3,1]       | 1,3            | 2    |
| 2 | [1,3,2]     | [3,1,2]     | 1,2,3          | 3    |
| 3 | [1,3,2,4]   | [3,1,2,4]   | 1,2,3,4        | 4    |

Output:

```text
[0,2,3,4]
```

---

# Intuition

We need to know:

* Which numbers have appeared in `A`
* Which numbers have appeared in `B`
* How many numbers are present in both prefixes

Since `A` and `B` are permutations and `n <= 50`, we can use a simple frequency/visited array.

---

# Approach

We maintain:

* `seenA[num]` → whether number appeared in `A`
* `seenB[num]` → whether number appeared in `B`

For every index `i`:

1. Mark `A[i]` as seen in `A`
2. Mark `B[i]` as seen in `B`
3. Check all numbers from `1 → n`
4. Count numbers that are seen in both arrays
5. Store count in answer array

---

# Why This Works

A number is considered common only if:

```text
seenA[num] == true
AND
seenB[num] == true
```

At every index, we recompute how many numbers satisfy this condition.

Because arrays are very small (`n <= 50`), this brute-force counting is efficient.

---

# Java Solution

```java
class Solution {
    public int[] findThePrefixCommonArray(int[] A, int[] B) {
        int n = A.length;

        int[] result = new int[n];

        boolean[] seenA = new boolean[n + 1];
        boolean[] seenB = new boolean[n + 1];

        for (int i = 0; i < n; i++) {

            // Mark current elements as seen
            seenA[A[i]] = true;
            seenB[B[i]] = true;

            int count = 0;

            // Count common elements
            for (int num = 1; num <= n; num++) {
                if (seenA[num] && seenB[num]) {
                    count++;
                }
            }

            result[i] = count;
        }

        return result;
    }
}
```

---

# Dry Run

## Input

```text
A = [2,3,1]
B = [3,1,2]
```

---

## Step 1 → i = 0

```text
seenA = {2}
seenB = {3}

Common = none
result[0] = 0
```

---

## Step 2 → i = 1

```text
seenA = {2,3}
seenB = {3,1}

Common = {3}
result[1] = 1
```

---

## Step 3 → i = 2

```text
seenA = {2,3,1}
seenB = {3,1,2}

Common = {1,2,3}
result[2] = 3
```

Final Output:

```text
[0,1,3]
```

---

# Time Complexity

For every index, we check all numbers from `1 → n`.

```text
Time Complexity: O(n²)
```

Since `n <= 50`, this is perfectly fine.

---

# Space Complexity

```text
O(n)
```

For the two visited arrays.

---

# Optimized Idea (Extra)

We can also solve this in `O(n)` using a frequency counter:

* Increase frequency for `A[i]`
* Increase frequency for `B[i]`
* Whenever frequency becomes `2`, it means the number appeared in both arrays

But the current solution is easier to understand and ideal for interviews and beginners.
