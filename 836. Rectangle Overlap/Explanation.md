## LeetCode 836 — Rectangle Overlap

### Problem Understanding

We are given two axis-aligned rectangles:

```text
rec = [x1, y1, x2, y2]
```

where:

* `(x1, y1)` → bottom-left corner
* `(x2, y2)` → top-right corner

We need to return `true` only when the rectangles have an **intersection with positive area**.

So, simply touching at an edge or corner **does not count as overlap**.

---

## 💡 Key Idea

For two rectangles to overlap, they must overlap in **both directions**:

1. They must overlap horizontally on the **X-axis**.
2. They must overlap vertically on the **Y-axis**.

Think of the rectangles as two intervals on each axis.

### Horizontal overlap

For `rec1`:

```text
[x1, x2]
```

For `rec2`:

```text
[x1', x2']
```

They have positive horizontal overlap when:

```text
max(x1, x1') < min(x2, x2')
```

Why `<` and not `<=`?

Because if:

```text
max(left edges) == min(right edges)
```

then the rectangles only touch at an edge.

Similarly, for the Y-axis:

```text
max(y1, y1') < min(y2, y2')
```

Therefore:

```text
horizontal overlap && vertical overlap
```

means the rectangles have positive intersection area.

---

## Java Solution

```java
class Solution {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {

        int xOverlap = Math.min(rec1[2], rec2[2])
                     - Math.max(rec1[0], rec2[0]);

        int yOverlap = Math.min(rec1[3], rec2[3])
                     - Math.max(rec1[1], rec2[1]);

        return xOverlap > 0 && yOverlap > 0;
    }
}
```

---

# How Does This Work?

Let's take:

```text
rec1 = [0, 0, 2, 2]
rec2 = [1, 1, 3, 3]
```

### Step 1: Find horizontal overlap

The rightmost left boundary is:

```text
max(0, 1) = 1
```

The leftmost right boundary is:

```text
min(2, 3) = 2
```

So the width of the intersection is:

```text
2 - 1 = 1
```

Positive → they overlap horizontally.

### Step 2: Find vertical overlap

The rightmost bottom boundary is:

```text
max(0, 1) = 1
```

The leftmost top boundary is:

```text
min(2, 3) = 2
```

So the height is:

```text
2 - 1 = 1
```

Positive → they overlap vertically.

Therefore:

```text
1 > 0 && 1 > 0
```

so we return:

```text
true
```

---

# Why `min(right) - max(left)`?

This is the most important part to understand.

Suppose we have two intervals:

```text
Interval 1: [0 -------- 5]
Interval 2:       [2 -------- 7]
```

The intersection is:

```text
       [2 --- 5]
```

The intersection starts at the **larger left boundary**:

```text
max(0, 2) = 2
```

and ends at the **smaller right boundary**:

```text
min(5, 7) = 5
```

Therefore:

```text
overlap = min(right boundaries) - max(left boundaries)
        = 5 - 2
        = 3
```

We apply exactly the same idea to both X and Y axes.

---

# Why `> 0`?

Consider:

```text
rec1 = [0, 0, 1, 1]
rec2 = [1, 0, 2, 1]
```

They look like:

```text
+---+---+
| 1 | 2 |
|   |   |
+---+---+
```

They touch at `x = 1`, but there is no common area.

Horizontal overlap:

```text
min(1, 2) - max(0, 1)
= 1 - 1
= 0
```

Since:

```text
xOverlap > 0
```

is false, the answer is:

```text
false
```

This is why we **cannot use `>= 0`**.

---

# Another Way to Think About It

Instead of calculating the intersection, we can think about when rectangles **do not overlap**.

Two rectangles don't overlap if one is:

* completely to the left of the other
* completely to the right
* completely above
* completely below

For example:

```text
rec1.x2 <= rec2.x1
```

means `rec1` is completely to the left of `rec2`.

Then we could write:

```java
class Solution {
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {

        if (rec1[2] <= rec2[0] ||  // rec1 is left of rec2
            rec2[2] <= rec1[0] ||  // rec2 is left of rec1
            rec1[3] <= rec2[1] ||  // rec1 is below rec2
            rec2[3] <= rec1[1]) {  // rec2 is below rec1

            return false;
        }

        return true;
    }
}
```

This solution is also correct.

### Which approach do I recommend?

I prefer the **overlap-width/height approach**:

```java
int xOverlap = Math.min(rec1[2], rec2[2])
             - Math.max(rec1[0], rec2[0]);

int yOverlap = Math.min(rec1[3], rec2[3])
             - Math.max(rec1[1], rec2[1]);

return xOverlap > 0 && yOverlap > 0;
```

because it directly represents the mathematical definition:

> **Intersection has positive width AND positive height.**

---

## Complexity

There are only a few arithmetic operations.

**Time:** `O(1)`

**Space:** `O(1)`

---

## 🧠 Interview Takeaway

The pattern to remember is:

```text
1D interval overlap:
overlap = min(right1, right2) - max(left1, left2)

2D rectangle overlap:
positive X overlap
        &&
positive Y overlap
```

So whenever you see **axis-aligned rectangles**, try reducing the problem to **interval overlap on X and Y independently**.
