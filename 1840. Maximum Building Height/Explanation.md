## Intuition

This problem looks difficult because `n` can be as large as `10^9`, so constructing the heights of all buildings is impossible.

The key observation is:

* The height difference between adjacent buildings is at most `1`.
* Therefore, if building `a` has height `ha`, then building `b` can have height at most:

[
ha + (b-a)
]

because the height can increase by only `1` per step.

So every restriction affects nearby buildings as well.

The solution has three main steps:

1. Add implicit restrictions.
2. Propagate constraints from left to right and right to left.
3. Find the maximum possible peak between every pair of consecutive restricted buildings.

---

## Step 1: Add implicit restrictions

We know:

* Building `1` must have height `0`.
* Building `n` has no explicit limit, but from building `1` it cannot exceed `n - 1`.

So add:

```text
[1, 0]
[n, n - 1]
```

to the restrictions list.

---

## Step 2: Sort restrictions

Sort by building index.

Example:

```text
n = 10
restrictions = [[5,3],[2,5],[7,4],[10,3]]
```

After adding building `1`:

```text
[1,0], [2,5], [5,3], [7,4], [10,3]
```

---

## Step 3: Left-to-right pass

Suppose we have:

```text
[id1, h1]
[id2, h2]
```

The maximum possible height at `id2` due to `id1` is:

[
h1 + (id2 - id1)
]

Therefore:

```java
h2 = min(h2, h1 + distance)
```

This ensures the right building is reachable from the left.

---

## Step 4: Right-to-left pass

Similarly, the left building cannot be too high compared to the right:

[
h1 \le h2 + (id2 - id1)
]

So:

```java
h1 = min(h1, h2 + distance)
```

After both passes, all restrictions become mutually consistent.

---

## Step 5: Find the highest peak between adjacent restrictions

Consider two consecutive restricted buildings:

```text
(id1, h1)
(id2, h2)
```

Let:

```text
d = id2 - id1
```

The tallest possible peak occurs by increasing from the smaller side and decreasing toward the other side.

Example:

```text
h1 = 1, h2 = 3, d = 4
```

Possible heights:

```text
1, 2, 3, 4, 3
```

The maximum peak is:

[
\frac{h1 + h2 + d}{2}
]

Why?

The extra distance after bridging the height difference can be used to climb and descend symmetrically.

More formally:

* Height difference already consumes:

[
|h1 - h2|
]

steps.

* Remaining steps:

[
d - |h1 - h2|
]

Half can be used to climb higher.

Equivalent formula:

[
\maxHeight = \left\lfloor \frac{h1 + h2 + d}{2} \right\rfloor
]

Compute this for every adjacent pair and take the maximum.

---

## Example Walkthrough

```text
n = 10
restrictions = [[5,3],[2,5],[7,4],[10,3]]
```

After sorting:

```text
[1,0], [2,5], [5,3], [7,4], [10,3]
```

### Left → Right

```text
[1,0]
[2,1]
[5,3]
[7,4]
[10,3]
```

### Right → Left

No further changes:

```text
[1,0]
[2,1]
[5,3]
[7,4]
[10,3]
```

Now compute peaks:

Between `(1,0)` and `(2,1)`:

[
(0+1+1)/2 = 1
]

Between `(2,1)` and `(5,3)`:

[
(1+3+3)/2 = 3
]

Between `(5,3)` and `(7,4)`:

[
(3+4+2)/2 = 4
]

Between `(7,4)` and `(10,3)`:

[
(4+3+3)/2 = 5
]

Answer:

```text
5
```

---

## Java Solution

```java
import java.util.*;

class Solution {
    public int maxBuilding(int n, int[][] restrictions) {
        int m = restrictions.length;

        int[][] arr = new int[m + 2][2];

        arr[0] = new int[]{1, 0};

        for (int i = 0; i < m; i++) {
            arr[i + 1] = restrictions[i];
        }

        arr[m + 1] = new int[]{n, n - 1};

        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        // Left to right
        for (int i = 1; i < arr.length; i++) {
            int dist = arr[i][0] - arr[i - 1][0];
            arr[i][1] = Math.min(arr[i][1], arr[i - 1][1] + dist);
        }

        // Right to left
        for (int i = arr.length - 2; i >= 0; i--) {
            int dist = arr[i + 1][0] - arr[i][0];
            arr[i][1] = Math.min(arr[i][1], arr[i + 1][1] + dist);
        }

        long ans = 0;

        for (int i = 1; i < arr.length; i++) {
            long id1 = arr[i - 1][0];
            long h1 = arr[i - 1][1];

            long id2 = arr[i][0];
            long h2 = arr[i][1];

            long dist = id2 - id1;

            long peak = (h1 + h2 + dist) / 2;

            ans = Math.max(ans, peak);
        }

        return (int) ans;
    }
}
```

---

## Why does this work?

After the two propagation passes:

* Every restriction is reachable from its neighbors.
* The restriction heights are the **maximum valid heights** for those positions.

Between two consecutive restricted buildings, there are no other constraints.

Therefore, the optimal arrangement is:

* Increase by `1` each step,
* Reach a peak,
* Decrease by `1` each step.

The peak height is exactly:

[
\left\lfloor \frac{h1 + h2 + d}{2} \right\rfloor
]

Taking the maximum over all segments gives the answer.

---

## Complexity Analysis

Let `k = restrictions.length`.

* Sorting: `O(k log k)`
* Left pass: `O(k)`
* Right pass: `O(k)`
* Peak computation: `O(k)`

Overall:

[
O(k \log k)
]

Space complexity:

[
O(k)
]

This works because `k ≤ 10^5`, even though `n` can be `10^9`.
