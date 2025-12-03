# 🚀 **Goal of the Problem**

Given **N points (N ≤ 500)**, count how many **unique trapezoids** can be formed.

A **trapezoid** is a convex quadrilateral where **at least one pair of opposite sides are parallel**.

So we must count all sets of **four points** that form a **convex quadrilateral** with **one pair of sides parallel**.

---

# 🌟 **Key Insight**

A quadrilateral is a trapezoid if:

> **It has at least one pair of distinct segments that are parallel and lie on opposite sides (not sharing endpoints).**

This is equivalent to:

### ✔ Find two **disjoint point pairs** (A,B) and (C,D)

such that
**AB and CD are parallel**.

---

# 💡 BUT there is a catch!

Parallel segments can also appear in:

* **Triangles** (collinear cases – invalid)
* **Self-intersecting quadrilaterals** (not convex)
* **Selecting two parallel segments that share an endpoint** (not opposite sides)

So the solution needs to:

### ➤ Count all pairs of parallel segments

### ➤ Subtract invalid combinations that don’t form trapezoids

This is exactly what the implementation does using two hash maps.

---

# 🧠 **Core Idea of the Given Code**

We process every pair of points (i, j):

### For each segment:

* Normalize slope → store as reduced `(dx, dy)`
* Compute “line intercept” value → identifies **which parallel lines** are same or different

Then the code uses two maps:

---

# 🔵 1️⃣ `t` Map → Groups **parallel lines**

```java
HashMap<Integer, HashMap<Integer, Integer>> t
```

* Key = **slope**
* Value = map of **intercepts → count**

This counts every pair of parallel **segments**, even if they share endpoints.

This includes **invalid** ones (like adjacent edges).

---

# 🔴 2️⃣ `v` Map → Groups **parallel segments with exact vector direction**

```java
HashMap<Integer, HashMap<Integer, Integer>> v
```

This prevents counting **two segments that share a point**, because they have:

* the same dx, dy
* same origin → same vector grouping

Dividing `count(v) / 2` removes invalid over-counting.

---

# ⭐ Why we subtract `count(v)/2` ?

`count(t)`
= counts all pairs of parallel segments, including invalid ones.

`count(v)`
= counts segment pairs that share a direction vector (i.e., segments connected or overlapping).

But each such invalid pair appears **twice**, hence:

### ✔ Final valid trapezoids = `count(t) - count(v)/2`

---

# ⚙️ **Understanding the Key Fields**

## 1. **Normalized slope:**

```java
int dx = xj - xi;
int dy = yj - yi;
// ensure unique representation
if (dx < 0 || (dx == 0 && dy < 0)) flip signs
g = gcd(dx, abs(dy));
sx = dx / g;
sy = dy / g;
```

This ensures the slope:

* is reduced (`dx,dy → sx,sy`)
* always stored in a consistent direction

### Important because:

Two lines are parallel ↔ have same `(sx, sy)`.

---

## 2. **Intercept calculation**

We compute something equivalent to:

### ● For slope (sx,sy), the line constant is:

```
des = sx*y - sy*x
```

For any two parallel lines, this value differs uniquely.

This allows grouping all segments lying on **the same infinite line**.

---

## 3. **Encoding slope to an integer key:**

```java
int key1 = (sx << 12) | (sy + 2000);
```

Used for `t`.

```java
int key2 = (dx << 12) | (dy + 2000);
```

Used for `v`.

This prevents collisions and speeds up hashing.

---

# 🟢 **count(map)** Explanation

Given a map:

```
slope → (intercept -> count_of_segments)
```

If one slope group has counts:
`a₁, a₂, a₃ ...`

Total segment pairs =
sum over all i < j:  ai * aj

This is exactly what the code does efficiently:

```java
for each slope group:
    let sum = total segments
    for each intercept group:
        sum -= val
        ans += val * sum
```

---

# 🧩 **Putting everything together**

### Step 1: Count all parallel segment pairs (valid + invalid)

→ using `t`

### Step 2: Count invalid ones (sharing endpoints)

→ using `v`

### Step 3: Final trapezoids =

```java
count(t) - count(v)/2
```

---

# ✔ Example Explanation (Brief)

Input:

```
[-3,2], [3,0], [2,3], [3,2], [2,-3]
```

Two trapezoids exist:

1. `(-3,2), (2,3), (3,2), (2,-3)`
2. `(2,3), (3,2), (3,0), (2,-3)`

The algorithm will find:

* correct parallel segment pairs
* remove pairs that share vertices
* count only valid quadrilaterals → convex trapezoids

---

# ✅ Summary of WHY the Approach Works

### The main challenge:

Counting how many *sets of 4 points* produce a trapezoid without trying all O(N⁴) combinations.

### The solution:

✔ Convert the problem into **counting parallel segments**
✔ Use hash maps to group by slope and line intercept
✔ Remove invalid combinations
✔ Achieve **O(N² log N)** efficiency

This uses geometry + combinatorics + hashing beautifully.

---

