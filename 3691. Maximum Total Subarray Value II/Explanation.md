### Idea

For every subarray:

[
value(l,r)=\max(nums[l..r])-\min(nums[l..r])
]

We need the **sum of the largest `k` distinct subarray values**.

There are (O(n^2)) subarrays, so generating all values is impossible.

---

## Key Observation

Fix a starting index `l`.

Define:

[
v_l(r)=value(l,r)
]

When we extend the right boundary:

```text
[l,l] -> [l,l+1] -> [l,l+2] -> ...
```

* Maximum can only stay the same or increase.
* Minimum can only stay the same or decrease.

Therefore:

[
v_l(l) \le v_l(l+1) \le v_l(l+2) \le ...
]

So for a fixed `l`, the values form a **sorted (non-decreasing) sequence**. ([LeetCode][1])

Example:

```text
nums = [1,3,2]

l = 0

r = 0 -> 0
r = 1 -> 2
r = 2 -> 2

Sequence: [0,2,2]
```

The largest element of this sequence is always at `r = n-1`.

---

## Turning It Into "K Largest Elements From N Sorted Lists"

For each `l`:

```text
v(l,l), v(l,l+1), ... , v(l,n-1)
```

is sorted.

So we have `n` sorted lists.

We need the global largest `k` values among all lists.

This is exactly the classic:

> "Find k largest elements from multiple sorted arrays."

Use a max-heap.

---

## Fast Range Max / Min Query

To compute:

```java
max(nums[l..r])
min(nums[l..r])
```

quickly, build Sparse Tables.

Then each query becomes:

```text
O(1)
```

after

```text
O(n log n)
```

preprocessing. ([LeetCode][1])

---

## Heap Strategy

For every `l`:

1. Insert `(l, n-1)`
2. Its value is:

[
v(l,n-1)
]

(the largest value in that row)

Then repeat `k` times:

* Pop largest `(l,r)`
* Add its value to answer
* Push `(l,r-1)` if `r-1 >= l`

Why?

Because in the row for fixed `l`:

```text
v(l,l) <= ... <= v(l,r-1) <= v(l,r)
```

After removing the largest unused element `v(l,r)`, the next candidate from that row is `v(l,r-1)`.

This guarantees we generate subarray values in descending order without enumerating all (O(n^2)) subarrays. ([LeetCode][1])

---

# Complexity

Sparse Table:

```text
O(n log n)
```

Heap:

```text
O(k log n)
```

Total:

```text
O((n + k) log n)
```

Memory:

```text
O(n log n)
```

which works for:

```text
n = 5 * 10^4
k = 10^5
```

---

# Java Solution

```java
import java.util.*;

class Solution {

    private int[][] maxSt;
    private int[][] minSt;
    private int[] log2;

    private long value(int l, int r) {
        int len = r - l + 1;
        int p = log2[len];

        int mx = Math.max(
                maxSt[p][l],
                maxSt[p][r - (1 << p) + 1]
        );

        int mn = Math.min(
                minSt[p][l],
                minSt[p][r - (1 << p) + 1]
        );

        return (long) mx - mn;
    }

    public long maxTotalValue(int[] nums, int k) {
        int n = nums.length;

        log2 = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            log2[i] = log2[i / 2] + 1;
        }

        int LOG = log2[n] + 1;

        maxSt = new int[LOG][n];
        minSt = new int[LOG][n];

        for (int i = 0; i < n; i++) {
            maxSt[0][i] = nums[i];
            minSt[0][i] = nums[i];
        }

        for (int p = 1; p < LOG; p++) {
            int len = 1 << p;

            for (int i = 0; i + len <= n; i++) {
                maxSt[p][i] = Math.max(
                        maxSt[p - 1][i],
                        maxSt[p - 1][i + (len >> 1)]
                );

                minSt[p][i] = Math.min(
                        minSt[p - 1][i],
                        minSt[p - 1][i + (len >> 1)]
                );
            }
        }

        class Node {
            int l, r;
            long val;

            Node(int l, int r, long val) {
                this.l = l;
                this.r = r;
                this.val = val;
            }
        }

        PriorityQueue<Node> pq =
                new PriorityQueue<>((a, b) -> Long.compare(b.val, a.val));

        for (int l = 0; l < n; l++) {
            pq.offer(new Node(l, n - 1, value(l, n - 1)));
        }

        long ans = 0;

        while (k-- > 0) {
            Node cur = pq.poll();

            ans += cur.val;

            if (cur.r > cur.l) {
                int nr = cur.r - 1;
                pq.offer(new Node(
                        cur.l,
                        nr,
                        value(cur.l, nr)
                ));
            }
        }

        return ans;
    }
}
```

### Dry Run

`nums = [1,3,2], k = 2`

Rows:

```text
l = 0 : [0,2,2]
l = 1 : [0,1]
l = 2 : [0]
```

Heap initially contains largest element of each row:

```text
2 (l=0,r=2)
1 (l=1,r=2)
0 (l=2,r=2)
```

Pop #1:

```text
2
ans = 2
push previous in same row -> 2
```

Heap:

```text
2,1,0
```

Pop #2:

```text
2
ans = 4
```

Result:

```text
4
```

which matches the example.

[1]: https://leetcode.com/problems/maximum-total-subarray-value-ii/?utm_source=chatgpt.com "Maximum Total Subarray Value II"
