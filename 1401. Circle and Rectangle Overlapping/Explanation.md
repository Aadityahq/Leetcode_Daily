## LeetCode 1401 — Circle and Rectangle Overlapping

### 💡 Main Idea

We need to check whether a **circle** and an **axis-aligned rectangle** have **at least one common point**.

The easiest way to solve this is:

1. Find the point in the rectangle that is **closest to the center of the circle**.
2. Calculate the distance between this closest point and the circle's center.
3. If that distance is **less than or equal to the radius**, they overlap.

---

## Why does this work?

Suppose:

```text
Circle:
center = (xCenter, yCenter)
radius = r
```

Rectangle:

```text
bottom-left = (x1, y1)
top-right   = (x2, y2)
```

### Step 1: Find the closest X-coordinate

For the circle center's `xCenter`:

* If it is **inside** `[x1, x2]`, then the closest X is `xCenter`.
* If it is to the **left**, closest X is `x1`.
* If it is to the **right**, closest X is `x2`.

We can write this as:

```java
closestX = Math.max(x1, Math.min(xCenter, x2));
```

### Step 2: Find the closest Y-coordinate

Similarly:

```java
closestY = Math.max(y1, Math.min(yCenter, y2));
```

Now `(closestX, closestY)` is the point inside the rectangle that is closest to the circle center.

---

### Step 3: Calculate distance

Distance between:

```text
Circle center = (xCenter, yCenter)
Closest point = (closestX, closestY)
```

is:

```text
distance² = (xCenter - closestX)² + (yCenter - closestY)²
```

We don't actually need the square root.

The circle contains a point if:

```text
distance² <= radius²
```

So:

```java
return distanceSquared <= radius * radius;
```

---

# Java Solution

```java
class Solution {
    public boolean checkOverlap(
            int radius,
            int xCenter,
            int yCenter,
            int x1,
            int y1,
            int x2,
            int y2) {

        // Find the closest point of the rectangle to the circle center
        int closestX = Math.max(x1, Math.min(xCenter, x2));
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Calculate squared distance
        int dx = xCenter - closestX;
        int dy = yCenter - closestY;

        int distanceSquared = dx * dx + dy * dy;

        // If distance is <= radius, they overlap
        return distanceSquared <= radius * radius;
    }
}
```

---

# Let's understand `Math.max` and `Math.min`

This line can look confusing:

```java
int closestX = Math.max(x1, Math.min(xCenter, x2));
```

Let's break it down.

Suppose:

```text
x1 = 2
x2 = 5
```

So the rectangle exists from `x = 2` to `x = 5`.

### Case 1: Circle center is inside

```text
xCenter = 4
```

```java
Math.min(4, 5) = 4
Math.max(2, 4) = 4
```

So:

```text
closestX = 4
```

Correct.

---

### Case 2: Circle center is to the left

```text
xCenter = 0
```

```java
Math.min(0, 5) = 0
Math.max(2, 0) = 2
```

So:

```text
closestX = 2
```

The closest point is the rectangle's left boundary.

---

### Case 3: Circle center is to the right

```text
xCenter = 10
```

```java
Math.min(10, 5) = 5
Math.max(2, 5) = 5
```

So:

```text
closestX = 5
```

The closest point is the rectangle's right boundary.

---

# Example 1

```text
radius = 1
xCenter = 0
yCenter = 0

rectangle:
x1 = 1
y1 = -1
x2 = 3
y2 = 1
```

The rectangle is:

```text
       y
       ↑
       |
   ┌─────────┐
   │         │
---┼─────────┼----→ x
   │         │
   └─────────┘
   1         3
```

Circle center:

```text
(0, 0)
```

Closest rectangle point:

```text
(1, 0)
```

Distance:

```text
dx = 0 - 1 = -1
dy = 0 - 0 = 0

distance² = (-1)² + 0²
           = 1
```

Radius:

```text
r = 1
r² = 1
```

Therefore:

```text
distance² <= radius²

1 <= 1
```

So:

```text
true
```

They touch at `(1, 0)`.

---

# Example 2

```text
radius = 1
xCenter = 1
yCenter = 1

rectangle:
x1 = 1
y1 = -3
x2 = 2
y2 = -1
```

Closest point of rectangle to `(1,1)` is:

```text
(1,-1)
```

Distance:

```text
dx = 1 - 1 = 0
dy = 1 - (-1) = 2

distance² = 0² + 2²
           = 4
```

Radius²:

```text
1² = 1
```

Since:

```text
4 > 1
```

there is no overlap.

```text
false
```

---

# Example 3

```text
radius = 1
xCenter = 0
yCenter = 0

x1 = -1
y1 = 0
x2 = 0
y2 = 1
```

The circle center `(0,0)` is actually a corner of the rectangle.

Closest point:

```text
(0,0)
```

Distance:

```text
0
```

Therefore:

```text
0 <= 1
```

So:

```text
true
```

---

## Why don't we use `Math.sqrt()`?

We could calculate:

```java
double distance = Math.sqrt(dx * dx + dy * dy);

return distance <= radius;
```

But this is unnecessary.

Instead compare:

```text
distance² <= radius²
```

which gives:

```java
return dx * dx + dy * dy <= radius * radius;
```

This is simpler and avoids the square-root operation.

---

## Complexity

### Time

```text
O(1)
```

We perform only a few calculations.

### Space

```text
O(1)
```

No extra data structures are used.

---

## 🧠 The key thing to remember

For **circle vs rectangle overlap**:

> **Find the closest point in the rectangle to the circle's center. Then check whether that point is inside the circle.**

The complete logic is just:

```java
int closestX = Math.max(x1, Math.min(xCenter, x2));
int closestY = Math.max(y1, Math.min(yCenter, y2));

int dx = xCenter - closestX;
int dy = yCenter - closestY;

return dx * dx + dy * dy <= radius * radius;
```

This is a very useful pattern for geometry problems: **closest point + distance check**.
