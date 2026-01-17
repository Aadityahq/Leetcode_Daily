## 🔍 Problem Restatement (In Simple Words)

You are given **n rectangles** on a 2D plane.

Each rectangle:

* has sides parallel to x-axis and y-axis
* is defined by its **bottom-left** and **top-right** coordinates

👉 Your task is to find the **maximum possible area of a square** that can be placed **completely inside the overlapping region of at least two rectangles**.

If **no two rectangles overlap**, return `0`.

---

## 🧠 Key Observation (WHY this approach works)

A square that fits inside the intersection of **multiple rectangles** must also fit inside the intersection of **some pair of rectangles**.

✔ Because:

* Any common intersection region is formed by overlapping rectangle pairs
* The square’s size is limited by the **width and height of the overlap**

So we only need to:

> **Check every pair of rectangles**

---

## 📐 How to Find Intersection of Two Rectangles

For rectangles `i` and `j`:

### Intersection boundaries

```text
left   = max(x1_i, x1_j)
right  = min(x2_i, x2_j)
bottom = max(y1_i, y1_j)
top    = min(y2_i, y2_j)
```

### Valid intersection condition

```text
width  = right - left
height = top - bottom

if width > 0 and height > 0 → rectangles overlap
```

---

## ⬛ Largest Square Inside the Intersection

* A square must fit in **both width and height**
* So the **largest possible square side** is:

```text
side = min(width, height)
```

* Square area:

```text
area = side × side
```

---

## ⚠️ Important Detail (WHY `long` is needed)

### Constraints:

* Coordinates up to **10⁷**
* Side length up to **10⁷**
* Area can be as large as **10¹⁴**

❌ `int` cannot store `10¹⁴`
✅ `long` can

That’s why:

* side
* area
* answer
  must be of type `long`

---

## 🧩 Final Algorithm (Step-by-Step)

1. Initialize `maxArea = 0`
2. Loop through all rectangle pairs `(i, j)`
3. Compute intersection width and height
4. If they overlap:

   * Compute square side
   * Compute area using `long`
   * Update `maxArea`
5. Return `maxArea`

---

## ✅ Correct Java Solution (Explained)

```java
class Solution {
    public long largestSquareArea(int[][] bottomLeft, int[][] topRight) {
        int n = bottomLeft.length;
        long maxArea = 0;   // must be long

        // Check all pairs of rectangles
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                // Intersection boundaries
                int left = Math.max(bottomLeft[i][0], bottomLeft[j][0]);
                int right = Math.min(topRight[i][0], topRight[j][0]);
                int bottom = Math.max(bottomLeft[i][1], bottomLeft[j][1]);
                int top = Math.min(topRight[i][1], topRight[j][1]);

                int width = right - left;
                int height = top - bottom;

                // If intersection exists
                if (width > 0 && height > 0) {
                    long side = Math.min(width, height);
                    long area = side * side;   // avoid overflow
                    maxArea = Math.max(maxArea, area);
                }
            }
        }
        return maxArea;
    }
}
```

---

## 🧪 Dry Run (Example)

### Input

```text
Rectangles:
[1,1] to [3,3]
[2,2] to [4,4]
```

### Intersection

```text
width  = 3 - 2 = 1
height = 3 - 2 = 1
side   = 1
area   = 1
```

✔ Max area = `1`

---

## ⏱ Complexity Analysis

* **Time:** `O(n²)` → checking all pairs
* **Space:** `O(1)` → no extra memory

Efficient for `n ≤ 1000`.

---

