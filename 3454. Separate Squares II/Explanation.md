# 🧩 Problem Explanation (Separate Squares II)

You are given several **axis-aligned squares** on a 2D plane.

Each square is described by:

```
[xi, yi, li]
```

* `(xi, yi)` → bottom-left corner
* `li` → side length

### Important rules

* Squares **may overlap**
* Overlapping area must be counted **only once**
* You can draw a **horizontal line** at any real `y` value
* The line may **cut through squares**

---

## 🎯 Goal

Find the **minimum y-coordinate** of a horizontal line such that:

```
Total area above the line
=
Total area below the line
```

Answer is accepted if it’s within **1e-5** accuracy.

---

## 🧠 Key Insight

This is **not** about summing square areas.

Because squares overlap:

* You must work with the **union area**
* Area changes continuously as the line moves up
* We need **geometry + precision**

So we need to:

1. Compute **total union area**
2. Find where half of that area lies vertically

---

# 💡 Solution Idea (High Level)

### Use a **Sweep Line** from bottom to top

Think of moving a horizontal line upward:

* Between two Y-coordinates, the set of squares covering that region is **constant**
* In that region, the area is:

  ```
  (vertical height) × (total covered width on X-axis)
  ```

So the plan is:

---

## 🪜 Step 1: Sweep Line on Y-axis

Each square generates **two events**:

| Event         | Meaning       |
| ------------- | ------------- |
| `y = yi`      | square starts |
| `y = yi + li` | square ends   |

Each event carries the X-interval `[x, x + l]`.

---

## 🪜 Step 2: Process Vertical Strips

While sweeping:

* Maintain a list of **active X-intervals**
* Between two Y events:

  * Merge overlapping X-intervals
  * Compute **union width**
  * Multiply by strip height → area

Store each strip as:

```
(bottomY, height, unionWidth)
```

Also accumulate **total union area**.

---

## 🪜 Step 3: Find the Split Line

Once total area is known:

1. Target = `totalArea / 2`
2. Accumulate strip areas bottom → top
3. When the target lies **inside a strip**:

   * Interpolate inside that strip

   ```
   y = bottomY + (missingArea / width)
   ```

This gives the **minimum y** that splits the area equally.

---

# 🔍 How Your Code Implements This

---

## 1️⃣ Event Creation

```java
sweepEvents.add(new Event(y, 1, x, x + l));
sweepEvents.add(new Event(y + l, -1, x, x + l));
```

* `+1` → interval starts
* `-1` → interval ends
* Sorting by `y` enables vertical sweep

---

## 2️⃣ Active X-Intervals

```java
List<Interval> activeIntervals = new ArrayList<>();
```

* Stores all X-ranges active at current Y
* Updated when squares start/end

---

## 3️⃣ Vertical Strip Processing

```java
double unionWidth = getUnionWidth(activeIntervals);
double height = event.y - prevY;
```

* Active intervals don’t change inside a strip
* Union width is computed by merging overlaps
* Area added: `width × height`

---

## 4️⃣ Union Width Calculation

```java
Collections.sort(sorted);
```

Then merge intervals:

* Disjoint → add full length
* Overlapping → add only extra part

✔ Ensures overlapping squares are **counted once**

---

## 5️⃣ Finding the Split Point

```java
if (accumulatedArea + stripArea >= targetArea) {
    return bottomY + (missingArea / width);
}
```

* Target falls inside this strip
* Area grows linearly → simple division
* Gives precise answer

---

# 🧪 Example

### Input

```
[[0,0,2],[1,1,1]]
```

### What happens

* Overlapping area counted once
* Total area = 4
* Half = 2
* Line at `y = 1` splits area equally

### Output

```
1.00000
```

---

# 🏁 Final Summary

✔ Handles **overlapping squares**
✔ Uses **sweep line + interval union**
✔ Computes area **accurately and efficiently**
✔ Finds **minimum y** satisfying the condition

This is a **textbook Hard geometry solution** and **interview-ready**.

---
