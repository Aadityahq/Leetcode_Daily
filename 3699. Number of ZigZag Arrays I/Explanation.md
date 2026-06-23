# 3699. Number of ZigZag Arrays I – Explanation & Java Solution

## Problem Understanding

We need to build arrays of length `n` where:

1. Every element is in the range `[l, r]`.
2. Adjacent elements cannot be equal.
3. No three consecutive elements can be:

   * strictly increasing (`a < b < c`)
   * strictly decreasing (`a > b > c`)

---

### What does condition 3 mean?

For any three consecutive elements:

```text
a, b, c
```

The sequence must **change direction** at the middle element.

Allowed:

```text
1 3 2   (up then down)
3 1 2   (down then up)
```

Not Allowed:

```text
1 2 3   (strictly increasing)
3 2 1   (strictly decreasing)
```

So after choosing two elements, the next element must make the trend alternate.

---

## Key Observation

Let:

```text
m = r - l + 1
```

(number of available values)

For every position, we only care about the relationship between the last two elements:

* previous value < current value (UP)
* previous value > current value (DOWN)

Since adjacent elements cannot be equal, these are the only possibilities.

---

## DP State

Define:

```java
up[x]
```

= number of ways where current value is `x`
and the last movement was UP.

Meaning:

```text
previous < x
```

Similarly:

```java
down[x]
```

= number of ways where current value is `x`
and the last movement was DOWN.

Meaning:

```text
previous > x
```

---

## Initial State (Length = 2)

For every pair `(a,b)`:

```text
a < b  → up[b]
a > b  → down[b]
```

Instead of checking all pairs, count them directly.

For value `x`:

### UP

Choose any smaller value before it.

```text
count = x values before it
```

(after shifting values to `0...m-1`)

So:

```java
up[x] = x
```

### DOWN

Choose any larger value before it.

```java
down[x] = m - 1 - x
```

---

## Transition

Suppose we are extending the array.

### To create an UP state

Last move must have been DOWN.

```text
a > b < c
```

So:

```text
c > b
```

If current value is `x`:

```java
newUp[x] =
sum of down[y] where y < x
```

---

### To create a DOWN state

Last move must have been UP.

```text
a < b > c
```

So:

```text
c < b
```

If current value is `x`:

```java
newDown[x] =
sum of up[y] where y > x
```

---

## Prefix/Suffix Optimization

Naively:

```java
O(m²)
```

per position.

Since:

```text
n,m ≤ 2000
```

that would be too slow.

Use:

### Prefix sums for DOWN

```java
prefixDown[i]
```

Then:

```java
newUp[x] = prefixDown[x-1]
```

---

### Suffix sums for UP

```java
suffixUp[i]
```

Then:

```java
newDown[x] = suffixUp[x+1]
```

Each transition becomes:

```java
O(m)
```

Total:

```text
O(n × m)
```

which is:

```text
2000 × 2000 = 4,000,000
```

and works comfortably.

---

## Dry Run

### Example

```text
n = 3
l = 1
r = 3
```

```text
m = 3
values = {1,2,3}
```

Length 2:

```text
up   = [0,1,2]
down = [2,1,0]
```

Build length 3:

```text
newUp   = [0,2,3]
newDown = [3,2,0]
```

Total:

```text
0+2+3+3+2+0 = 10
```

Answer:

```text
10
```

---

## Java Solution

```java
class Solution {
    private static final long MOD = 1_000_000_007L;

    public int zigZagArrays(int n, int l, int r) {
        int m = r - l + 1;

        long[] up = new long[m];
        long[] down = new long[m];

        // Length = 2 initialization
        for (int x = 0; x < m; x++) {
            up[x] = x;              // smaller values before x
            down[x] = m - 1 - x;    // larger values before x
        }

        // Build lengths 3..n
        for (int len = 3; len <= n; len++) {

            long[] prefixDown = new long[m];
            long[] suffixUp = new long[m];

            prefixDown[0] = down[0];
            for (int i = 1; i < m; i++) {
                prefixDown[i] = (prefixDown[i - 1] + down[i]) % MOD;
            }

            suffixUp[m - 1] = up[m - 1];
            for (int i = m - 2; i >= 0; i--) {
                suffixUp[i] = (suffixUp[i + 1] + up[i]) % MOD;
            }

            long[] newUp = new long[m];
            long[] newDown = new long[m];

            for (int x = 0; x < m; x++) {

                // Need previous trend DOWN and previous value < x
                newUp[x] = (x > 0) ? prefixDown[x - 1] : 0;

                // Need previous trend UP and previous value > x
                newDown[x] = (x + 1 < m) ? suffixUp[x + 1] : 0;
            }

            up = newUp;
            down = newDown;
        }

        long ans = 0;

        if (n == 2) {
            for (int x = 0; x < m; x++) {
                ans = (ans + up[x] + down[x]) % MOD;
            }
            return (int) ans;
        }

        for (int x = 0; x < m; x++) {
            ans = (ans + up[x] + down[x]) % MOD;
        }

        return (int) ans;
    }
}
```

## Complexity Analysis

* `m = r - l + 1`
* `m ≤ 2000`

### Time Complexity

```text
O(n × m)
```

### Space Complexity

```text
O(m)
```

The solution works efficiently within the given constraints (`n, m ≤ 2000`).
