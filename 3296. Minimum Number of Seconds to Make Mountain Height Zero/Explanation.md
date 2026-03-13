# 1. Key Observation

For worker `i` with time `t = workerTimes[i]`

To reduce height `x`, time required is:

[
t + 2t + 3t + ... + xt
]

Factor out `t`:

[
t(1 + 2 + 3 + ... + x)
]

The sum of the first `x` natural numbers is:

1+2+3+\dots+x = \frac{x(x+1)}{2}

So total time becomes:

[
t \times \frac{x(x+1)}{2}
]

Thus a worker can reduce height `x` if:

[
t \times \frac{x(x+1)}{2} \le T
]

---

# 2. Binary Search Idea

We search for the **minimum time `T`** such that:

```
total height reduced by all workers >= mountainHeight
```

For each worker, we compute the **maximum `x`** they can reduce within time `T`.

---

# 3. Finding Maximum Height per Worker

From

[
t \times \frac{x(x+1)}{2} \le T
]

[
x(x+1) \le \frac{2T}{t}
]

Solve quadratic:

[
x^2 + x - \frac{2T}{t} \le 0
]

Using quadratic formula:

x = \frac{-1 + \sqrt{1 + 8T/t}}{2}

We take the **floor value** of this `x`.

---

# 4. Algorithm

1. Binary search `time` between

```
left = 0
right = very large value (1e18)
```

2. For each `mid` time:

   * Calculate height each worker can reduce.
   * Sum them.

3. If total height ≥ mountainHeight
   → time is enough → search smaller.

4. Else
   → increase time.

---

# 5. Java Solution

```java
class Solution {

    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {

        long left = 0;
        long right = (long)1e18;
        long ans = right;

        while (left <= right) {

            long mid = left + (right - left) / 2;

            if (canFinish(mid, mountainHeight, workerTimes)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private boolean canFinish(long time, int height, int[] workers) {

        long total = 0;

        for (int t : workers) {

            long val = (2 * time) / t;

            long x = (long)((Math.sqrt(1 + 4 * val) - 1) / 2);

            total += x;

            if (total >= height)
                return true;
        }

        return false;
    }
}
```

---

# 6. Dry Run (Example 1)

```
mountainHeight = 4
workerTimes = [2,1,1]
```

Binary search eventually tests **T = 3**

Worker calculations:

### Worker 0 (t = 2)

```
2 * (1) = 2 <= 3
2 * (1+2) = 6 > 3
```

Height = **1**

---

### Worker 1 (t = 1)

```
1 + 2 = 3
```

Height = **2**

---

### Worker 2 (t = 1)

Height = **1**

---

Total height reduced:

```
1 + 2 + 1 = 4
```

Mountain height becomes **0**.

Answer:

```
3 seconds
```

---

# 7. Time Complexity

Binary search:

```
log(1e18) ≈ 60
```

Each check:

```
O(n)
```

Total:

```
O(n log T)
```

Where

```
n = number of workers (≤ 10^4)
```

Very efficient.

---

# 8. Why Binary Search Works

The function

```
time → total height reduced
```

is **monotonic**.

* If workers can finish in **T seconds**
* They can also finish in **T+1 seconds**

So we can **binary search the minimum valid time**.

---

💡 **Interview Insight**

This problem combines:

* Binary Search on Answer
* Math (Arithmetic Series)
* Quadratic solving
* Greedy counting

This pattern appears often in **LeetCode hard scheduling problems**.

---
