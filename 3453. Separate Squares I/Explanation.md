## 🧠 Problem Understanding (Intuition)

You are given several **axis-aligned squares**.
Each square is defined as:

```
[xi, yi, li]
```

* `(xi, yi)` → bottom-left corner
* `li` → side length

Each square spans vertically from:

```
yi  to  yi + li
```

---

### 🎯 Goal

Find the **minimum y-coordinate** of a **horizontal line** such that:

```
Total area ABOVE the line == Total area BELOW the line
```

Important notes:

* Squares **can overlap**
* Overlapping areas are counted **multiple times**
* Partial square areas count proportionally
* Answer must be accurate within `1e-5`

---

## 🔍 Key Observation

For a chosen horizontal line at height `y = h`:

For **each square**:

* If `h ≤ yi` → entire square is **above**
* If `h ≥ yi + li` → entire square is **below**
* Otherwise → square is **split**

For a split square:

* Below area = `(h - yi) * li`
* Above area = `(yi + li - h) * li`

So, for any `h`, we can compute:

```
areaBelow(h)
areaAbove(h)
```

We want:

```
areaBelow(h) == areaAbove(h)
```

---

## 📈 Why Binary Search Works

Define a function:

```
f(h) = areaBelow(h) - areaAbove(h)
```

Properties:

* `f(h)` is **monotonic increasing**
* At very low `h` → mostly area above → `f(h) < 0`
* At very high `h` → mostly area below → `f(h) > 0`

✅ Therefore, there is a **unique balance point**
👉 We can find it using **binary search on y-coordinate**

---

## 🛠️ Algorithm Steps

1. **Search space**

   * `low = minimum yi`
   * `high = maximum (yi + li)`

2. **Binary search**

   * Midpoint `mid`
   * Compute `areaBelow(mid)` and `areaAbove(mid)`
   * Adjust range based on comparison

3. **Stop when precision ≤ 1e-6**

---

## ⏱️ Complexity

* Each area calculation → `O(n)`
* Binary search iterations → ~60 (for precision)
* **Total:** `O(n log precision)`
  Works efficiently for `n ≤ 50,000`

---

## ✅ Java Solution (Clean & Efficient)

```java
class Solution {

    public double separateSquares(int[][] squares) {
        double low = Double.MAX_VALUE;
        double high = Double.MIN_VALUE;

        // Determine search bounds
        for (int[] sq : squares) {
            low = Math.min(low, sq[1]);
            high = Math.max(high, sq[1] + sq[2]);
        }

        // Binary search
        for (int i = 0; i < 60; i++) { // sufficient for 1e-5 precision
            double mid = (low + high) / 2.0;
            double below = 0.0;
            double above = 0.0;

            for (int[] sq : squares) {
                double y = sq[1];
                double l = sq[2];

                if (mid <= y) {
                    // Entire square above
                    above += l * l;
                } else if (mid >= y + l) {
                    // Entire square below
                    below += l * l;
                } else {
                    // Square is split
                    below += (mid - y) * l;
                    above += (y + l - mid) * l;
                }
            }

            if (below < above) {
                low = mid;
            } else {
                high = mid;
            }
        }

        return low;
    }
}
```

---

## 🧪 Example Walkthrough (Example 2)

```
squares = [[0,0,2],[1,1,1]]
```

Total area = `4 + 1 = 5`

Binary search finds:

```
y = 7/6 ≈ 1.16667
```

At this height:

* Below area = 2.5
* Above area = 2.5

✔ Balanced perfectly

---

## 🏁 Final Takeaways

* Convert geometry into **area functions**
* Recognize **monotonic behavior**
* Use **binary search on answer**
* Carefully handle **partial overlaps**

