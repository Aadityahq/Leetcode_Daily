# LeetCode 1569: Number of Ways to Reorder Array to Get Same BST

Hard

Java

Recursion + Combinatorics

## 1. Understanding the problem

You are given an array of distinct numbers. You insert these numbers into a Binary Search Tree (BST) in the same order as they appear in the array.

Your task is to find how many different reorderings of the array produce exactly the same BST as the original array.

The original ordering itself is included in the total count, so the final answer is `total ways - 1`.

Let's understand with an example.

### Example: `nums = [2,1,3]`

The BST we must preserve

Valid insertion orders

`[2,1,3]`

`[2,3,1]`

Both orders create the same BST because `2` remains the root, `1` is inserted into the left subtree, and `3` into the right subtree.

Total valid orders = 2

Answer = 2 − 1 = 1

The main observation: The root must stay first. However, the elements belonging to the left and right subtrees can be interleaved in different ways, provided the relative order within each subtree is preserved.

That is where combinatorics comes in.

## 2. Java solution

This solution uses recursion and combinations (`nCr`).

```

import java.util.*;

class Solution {
    private static final long MOD = 1_000_000_007L;
    private long[][] comb;

    public int numOfWays(int[] nums) {
        int n = nums.length;

        // Precompute combinations: C(n, r)
        comb = new long[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            comb[i][0] = 1;
            comb[i][i] = 1;

            for (int j = 1; j < i; j++) {
                comb[i][j] =
                    (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
            }
        }

        List<Integer> arr = new ArrayList<>();
        for (int num : nums) {
            arr.add(num);
        }

        // Subtract 1 to exclude the original ordering
        return (int) ((solve(arr) - 1 + MOD) % MOD);
    }

    private long solve(List<Integer> nums) {
        int n = nums.size();

        // Empty tree or a single node has one possible ordering
        if (n <= 2) {
            return 1;
        }

        int root = nums.get(0);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        // Separate elements belonging to left and right subtrees
        for (int i = 1; i < n; i++) {
            if (nums.get(i) < root) {
                left.add(nums.get(i));
            } else {
                right.add(nums.get(i));
            }
        }

        int leftSize = left.size();

        // Choose positions for the left subtree elements
        long ways = comb[n - 1][leftSize];

        // Multiply by the valid orderings of both subtrees
        ways = (ways * solve(left)) % MOD;
        ways = (ways * solve(right)) % MOD;

        return ways;
    }
}

```

## 3. How does the solution work?

Let's break it into four simple steps.

### Step 1: Find the root

The first element of the array is always the root of the BST.

For example:

`nums = [3,4,5,1,2]`

The root is `3`.

Every smaller number goes into the left subtree, and every larger number goes into the right subtree.

Root = 3 · Left subtree = [1,2] · Right subtree = [4,5]

The original array is divided into:

- Left: `[1,2]`
- Right: `[4,5]`

Notice that the left and right elements do not need to appear together in the reordered array.

### Step 2: Understand the combination formula

Suppose there are:

- \\(n\\) total elements.
- \\(L\\) elements in the left subtree.
- \\(R\\) elements in the right subtree.

Since the root must remain first, we have \\(n-1\\) remaining positions.

We need to choose exactly \\(L\\) positions for the left subtree elements. The remaining \\(R\\) positions belong to the right subtree.

The number of ways to choose these positions is:

\\[ \binom{n-1}{L}=\frac{(n-1)!}{L!\\,R!} \\]

For `[3,4,5,1,2]`:

- Remaining positions = 4
- Left elements = 2
- Right elements = 2

Therefore:

\\[ \binom{4}{2}=\frac{4!}{2!\\,2!}=6 \\]

There are six ways to arrange the left/right positions. However, not all six are necessarily valid after accounting for the subtree orderings.

In this example, both subtrees contain two elements in increasing order, so each subtree has only one valid ordering. The total number of valid arrays, including the original, is six, and the answer is five.

### Step 3: Why do we use recursion?

The same problem exists inside both subtrees.

For any subtree, we must:

1. Take its first element as its root.
2. Separate its left and right elements.
3. Count how many ways we can interleave the two subtrees.
4. Multiply by the number of valid orderings inside each subtree.

Therefore, the recurrence is:

\\[ \begin{aligned} W(nums)={}&\binom{n-1}{L}\\\ &\times W(left)\\\ &\times W(right) \end{aligned} \\]

Here, \\(W\\) means the number of valid reorderings including the original ordering.

We multiply because each valid interleaving can be combined with any valid ordering of the left subtree and any valid ordering of the right subtree.

### Step 4: Why do we precompute combinations?

Calculating factorials directly can lead to very large numbers. The answer must also be calculated modulo \\(10^9+7\\).

Instead, we use Pascal's identity:

\\[ \binom{i}{j}= \binom{i-1}{j-1}+\binom{i-1}{j} \\]

The base cases are:

\\[ \binom{i}{0}=\binom{i}{i}=1 \\]

This lets us precompute every required combination in \\(O(n^2)\\) time.

In the code:

```
comb[i][j] =
    (comb[i - 1][j - 1] + comb[i - 1][j]) % MOD;
```

We use `long` because multiplication can exceed the range of an `int`.

## 4. Dry run: `nums = [2,1,3]`

Initially, the root is `2`.

Left subtree

### [1]

Right subtree

### [3]

Calculate the number of ways

\\[ \binom{2}{1}=2 \\]

Both subtrees contain just one element, so each has one possible ordering.

\\[ W=2\times1\times1=2 \\]

Exclude the original array

\\[ Answer=(2-1+MOD)\bmod MOD \\]

# Answer = 1

The two valid arrays are `[2,1,3]` and `[2,3,1]`. Since the original array is not counted as a different ordering, the answer is `1`.

## 5. Why is the base case `n <= 2`?

```
if (n <= 2) {
    return 1;
}
```

- If there are zero elements, there is one empty ordering.
- If there is one element, there is only one ordering.
- If there are two elements, the first element must be the root, so there is only one ordering that preserves the BST.

The recursion stops here because there is nothing more to calculate.

## 6. Complexity analysis

Let \\(n\\) be the length of `nums`.

- Time complexity: \\(O(n^2)\\) in the worst case. Precomputing combinations takes \\(O(n^2)\\), and repeated subtree partitioning can also take \\(O(n^2)\\).
- Space complexity: \\(O(n^2)\\). The combination table takes \\(O(n^2)\\) space and the recursion and temporary lists use additional space.

Both are suitable for the constraint \\(n\leq1000\\).

## 7. Key takeaways for interviews

Remember these three ideas:

1. BST property: The first element is the root; smaller values belong to the left subtree and larger values to the right.
2. Combinatorics: Choose positions for left subtree elements using \\(\binom{n-1}{L}\\).
3. Recursion: Multiply the interleaving count by the valid ordering counts of both subtrees, then subtract one to exclude the original array.

The most important formula is:

\\[ \boxed{ W(nums)= \binom{n-1}{L} \times W(left) \times W(right) } \\]

This pattern is useful in problems where you count ways to interleave two groups while preserving the internal order of each group.