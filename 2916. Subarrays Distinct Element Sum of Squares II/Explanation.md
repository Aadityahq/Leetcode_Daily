# LeetCode 2916: Subarrays Distinct Element Sum of Squares II

```
import java.util.Arrays;

class Solution {
    private static final long MOD = 1_000_000_007L;

    private long[] sum;
    private long[] squareSum;
    private long[] lazy;
    private int n;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        squareSum = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        Arrays.fill(last, -1);

        long answer = 0;

        for (int i = 0; i < n; i++) {
            int left = last[nums[i]] + 1;

            update(1, 0, n - 1, left, i, 1);

            answer = (answer + squareSum[1]) % MOD;

            last[nums[i]] = i;
        }

        return (int) answer;
    }

    private void apply(int node, int l, int r, long delta) {
        long len = r - l + 1L;
        long oldSum = sum[node];

        squareSum[node] = (
            squareSum[node]
            + 2L * delta % MOD * oldSum % MOD
            + len * delta % MOD * delta % MOD
        ) % MOD;

        sum[node] = (oldSum + len * delta) % MOD;
        lazy[node] = (lazy[node] + delta) % MOD;
    }

    private void pushDown(int node, int l, int r) {
        if (lazy[node] == 0 || l == r) {
            return;
        }

        int mid = l + (r - l) / 2;
        long delta = lazy[node];

        apply(node * 2, l, mid, delta);
        apply(node * 2 + 1, mid + 1, r, delta);

        lazy[node] = 0;
    }

    private void update(
        int node, int l, int r,
        int ql, int qr, long delta
    ) {
        if (qr < l || r < ql) {
            return;
        }

        if (ql <= l && r <= qr) {
            apply(node, l, r, delta);
            return;
        }

        pushDown(node, l, r);

        int mid = l + (r - l) / 2;

        update(node * 2, l, mid, ql, qr, delta);
        update(node * 2 + 1, mid + 1, r, ql, qr, delta);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        squareSum[node] = (
            squareSum[node * 2] + squareSum[node * 2 + 1]
        ) % MOD;
    }
}
```

## 1. Problem explanation

We are given an integer array `nums`. We need to find the sum of the squares of the distinct element counts of all non-empty subarrays.

For example:

```
nums = [1, 2, 1]
```

The subarrays and their contributions are:

| Subarray    | Distinct count | Square |
| ----------- | -------------- | ------ |
| `[1]`       | 1              | 1      |
| `[2]`       | 1              | 1      |
| `[1]`       | 1              | 1      |
| `[1, 2]`    | 2              | 4      |
| `[2, 1]`    | 2              | 4      |
| `[1, 2, 1]` | 2              | 4      |

Therefore, the answer is:

\\[ 1+1+1+4+4+4=\boxed{15} \\]

The array length can be as large as \\(10^5\\). Checking every subarray individually would be too slow, so we use a segment tree with lazy propagation.

## 2. Main idea

Instead of calculating every subarray separately, process the array from left to right.

At every index `i`, consider all subarrays ending at index `i`.

For `nums = [1, 2, 1]`:

- At index `0`, the subarray is `[1]`, with one distinct element.
- At index `1`, the subarrays are `[2]` and `[1, 2]`, with distinct counts `1` and `2`.
- At index `2`, the subarrays are `[1]`, `[2, 1]`, and `[1, 2, 1]`, with distinct counts `1`, `2`, and `2`.

The contributions from these three indices are `1`, `5`, and `9`.

Thus, the final answer is:

\\[ 1+5+9=15 \\]

Our goal is to maintain these distinct counts efficiently.

## 3. Understanding the `last` array

```
int[] last = new int[100001];
Arrays.fill(last, -1);
```

The `last` array stores the most recent index where each number appeared.

For example:

```
nums = [1, 2, 1]
```

| Index | Value | Previous occurrence |
| ----- | ----- | ------------------- |
| 0     | 1     | -1                  |
| 1     | 2     | -1                  |
| 2     | 1     | 0                   |

Initially, every value is `-1`, meaning that it has not appeared before.

When we process the element `nums[i]`, we calculate:

```
int left = last[nums[i]] + 1;
```

Why?

If the element has never appeared, `last[nums[i]]` is `-1`, so `left = 0`. Every subarray ending at `i` gains a new distinct element.

If the element appeared earlier at index `0`, only subarrays starting after index `0` gain a new distinct element.

For example, at index `2`, the value is `1` and its previous occurrence was at index `0`.

Therefore:

```
left = 0 + 1; // 1
```

We update the range `[1, 2]`, because those are the starting positions whose subarrays gain a new distinct element.

After processing the element, we update its latest occurrence:

```
last[nums[i]] = i;
```

## 4. Understanding the segment tree arrays

```
private long[] sum;
private long[] squareSum;
private long[] lazy;
```

These three arrays are the foundation of the solution.

### `sum[]`

Stores the sum of the distinct counts in a segment.

For example, if the counts are `[1, 2]`:

\\[ sum=1+2=3 \\]

### `squareSum[]`

Stores the sum of the squares of those counts.

For `[1, 2]`:

\\[ squareSum=1^2+2^2=5 \\]

### `lazy[]`

Stores increments that have been applied to an entire segment but have not yet been passed down to its children.

This avoids updating every element individually.

We initialize the segment tree with:

```
sum = new long[4 * n];
squareSum = new long[4 * n];
lazy = new long[4 * n];
```

Allocating `4 * n` entries is a standard way to provide enough space for the segment tree.

## 5. Understanding the `apply()` function

```
private void apply(int node, int l, int r, long delta) {
    long len = r - l + 1L;
    long oldSum = sum[node];

    squareSum[node] = (
        squareSum[node]
        + 2L * delta % MOD * oldSum % MOD
        + len * delta % MOD * delta % MOD
    ) % MOD;

    sum[node] = (oldSum + len * delta) % MOD;
    lazy[node] = (lazy[node] + delta) % MOD;
}
```

This function increments every count in a segment by `delta`.

In our solution, the main loop passes `delta = 1`.

Suppose the distinct counts are:

```
[1, 2]
```

Then:

```
sum = 3
squareSum = 5
```

After increasing each count by one:

```
[2, 3]
```

The new sum is `5`, and the new squared sum is `13`.

We calculate this using the mathematical identity:

\\[ (x+\Delta)^2=x^2+2x\Delta+\Delta^2 \\]

Adding this formula across all elements gives:

\\[ \boxed{newSquareSum=oldSquareSum+2\Delta\cdot oldSum+len\cdot\Delta^2} \\]

For our example, `delta = 1` and `len = 2`:

\\[ newSquareSum=5+2(1)(3)+2(1)^2 \\]

\\[ =5+6+2=\boxed{13} \\]

That explains why we save the old sum before changing it:

```
long oldSum = sum[node];
```

We then update the sum:

```
sum[node] = (oldSum + len * delta) % MOD;
```

Finally, we record the pending increment:

```
lazy[node] = (lazy[node] + delta) % MOD;
```

## 6. Understanding the `pushDown()` function

```
private void pushDown(int node, int l, int r) {
    if (lazy[node] == 0 || l == r) {
        return;
    }

    int mid = l + (r - l) / 2;
    long delta = lazy[node];

    apply(node * 2, l, mid, delta);
    apply(node * 2 + 1, mid + 1, r, delta);

    lazy[node] = 0;
}
```

A segment tree divides a segment into two smaller segments:

- Left child: `node * 2`
- Right child: `node * 2 + 1`

If a whole segment has been updated, we don't immediately update all its children. Instead, we store the pending update in `lazy[]`.

When we need to access its children, `pushDown()` applies the pending increment to both of them.

After passing the update down, we reset:

```
lazy[node] = 0;
```

This technique is called lazy propagation. It reduces unnecessary work during range updates.

## 7. Understanding the `update()` function

```
private void update(
    int node, int l, int r,
    int ql, int qr, long delta
) {
    if (qr < l || r < ql) {
        return;
    }

    if (ql <= l && r <= qr) {
        apply(node, l, r, delta);
        return;
    }

    pushDown(node, l, r);

    int mid = l + (r - l) / 2;

    update(node * 2, l, mid, ql, qr, delta);
    update(node * 2 + 1, mid + 1, r, ql, qr, delta);

    sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

    squareSum[node] = (
        squareSum[node * 2] + squareSum[node * 2 + 1]
    ) % MOD;
}
```

This function increments a requested range. It handles three cases.

Case 1: No overlap

```
if (qr < l || r < ql) {
    return;
}
```

If the current segment is completely outside the requested range, we do nothing.

Case 2: Complete overlap

```
if (ql <= l && r <= qr) {
    apply(node, l, r, delta);
    return;
}
```

If the requested range completely covers the current segment, we update it directly using `apply()`.

There is no need to visit the children.

Case 3: Partial overlap

```
pushDown(node, l, r);

int mid = l + (r - l) / 2;

update(node * 2, l, mid, ql, qr, delta);
update(node * 2 + 1, mid + 1, r, ql, qr, delta);
```

If only part of the segment needs updating, we pass pending updates down and recursively process the left and right children.

Finally, we recompute the parent's values by adding the values of its children:

```
sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
```

```
squareSum[node] = (
    squareSum[node * 2] + squareSum[node * 2 + 1]
) % MOD;
```

## 8. Understanding the `sumCounts()` function

This is the main function executed by LeetCode.

```
public int sumCounts(int[] nums) {
    n = nums.length;

    sum = new long[4 * n];
    squareSum = new long[4 * n];
    lazy = new long[4 * n];

    int[] last = new int[100001];
    Arrays.fill(last, -1);

    long answer = 0;

    for (int i = 0; i < n; i++) {
        int left = last[nums[i]] + 1;

        update(1, 0, n - 1, left, i, 1);

        answer = (answer + squareSum[1]) % MOD;

        last[nums[i]] = i;
    }

    return (int) answer;
}
```

The main loop performs five operations:

1. Find the previous occurrence of the current number.
2. Calculate the first starting position affected by the new element.
3. Update that range in the segment tree.
4. Add the root's squared sum to the answer.
5. Update the latest occurrence of the current number.

The line

```
answer = (answer + squareSum[1]) % MOD;
```

is especially important.

` squareSum[1]` is the squared sum stored at the root, which represents the entire range of starting positions. After processing index `i`, it gives the sum of squared distinct counts of all subarrays ending at that index.

We add this contribution for every index, so every non-empty subarray is counted exactly once.

Finally:

```
return (int) answer;
```

returns the answer modulo \\(10^9+7\\).

## 9. Dry run

Input:

```
nums = [1, 2, 1]
```

| Index | Value | Updated range | Distinct counts of subarrays ending here | Contribution |
| ----- | ----- | ------------- | ---------------------------------------- | ------------ |
| 0     | 1     | `[0, 0]`      | `[1]`                                    | 1            |
| 1     | 2     | `[0, 1]`      | `[2, 1]`                                 | 5            |
| 2     | 1     | `[1, 2]`      | `[2, 2, 1]`                              | 9            |

The total is:

\\[ answer=1+5+9=\boxed{15} \\]

Output:

```
15
```

## 10. Complexity analysis

- Time complexity: \\(O(n\log n)\\), because each array element causes one segment-tree range update.
- Space complexity: \\(O(n+U)\\), where \\(U=100001\\) is the size of the `last` array. Under the given constraints, this is \\(O(n)\\).

## Key points to remember

- `last[]` identifies which starting positions gain a new distinct element.
- `sum[]` maintains the sum of distinct counts.
- `squareSum[]` maintains the sum of their squares.
- `lazy[]` avoids repeatedly updating individual elements.
- `apply()` uses the square expansion formula to update an entire segment efficiently.
- `squareSum[1]` provides the contribution of all subarrays ending at the current index.

The core idea is to maintain the distinct counts for every possible starting position and update only the range affected by the new element. This is what allows the solution to handle large arrays efficiently.