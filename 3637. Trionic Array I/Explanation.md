## 🔍 Problem Understanding (What is a Trionic Array?)

You are given an array `nums` of length `n`.

The array is **trionic** if it can be split into **three contiguous parts**:

1. **Strictly increasing** → from index `0` to `p`
2. **Strictly decreasing** → from index `p` to `q`
3. **Strictly increasing again** → from index `q` to `n-1`

With conditions:

```
0 < p < q < n - 1
```

### Example (Valid)

```
[1, 3, 5, 4, 2, 6]
 ↑  ↑  ↑  ↓  ↓  ↑
```

---

## ❌ Common Mistakes

* Equal adjacent values ❌ (must be **strict**, not `<=` or `>=`)
* Missing one of the three phases
* Too short arrays

---

## 🧠 Key Insight (How to Solve)

We **scan once** from left to right and track **three phases**:

1. **Increasing**
2. **Decreasing**
3. **Increasing again**

If all three phases are found **in order**, return `true`.

Otherwise, return `false`.

---

## 🛠️ Step-by-Step Logic (How & Why)

1. Start from index `1`
2. Move forward while values are **strictly increasing**

   * This finds index `p`
3. Continue while values are **strictly decreasing**

   * This finds index `q`
4. Continue while values are **strictly increasing again**
5. If we **reach the end** exactly after step 4 → valid trionic array

---

## ✅ Java Solution

```java
class Solution {
    public boolean isTrionic(int[] nums) {
        int n = nums.length;
        if (n < 3) return false;

        int i = 1;

        // 1️⃣ First strictly increasing part
        while (i < n && nums[i] > nums[i - 1]) {
            i++;
        }
        // Must have at least one increase
        if (i == 1 || i == n) return false;

        // 2️⃣ Strictly decreasing part
        while (i < n && nums[i] < nums[i - 1]) {
            i++;
        }
        // Must have at least one decrease
        if (i == n) return false;

        // 3️⃣ Final strictly increasing part
        while (i < n && nums[i] > nums[i - 1]) {
            i++;
        }

        // If we consumed entire array correctly
        return i == n;
    }
}
```

---

## 🧪 Dry Run Example

### Input:

```
nums = [1, 3, 5, 4, 2, 6]
```

### Execution:

* `1 → 3 → 5` ✔ increasing
* `5 → 4 → 2` ✔ decreasing
* `2 → 6` ✔ increasing
* End reached ✔

### Output:

```
true
```

---

## ⏱️ Complexity Analysis

| Metric | Value    |
| ------ | -------- |
| Time   | **O(n)** |
| Space  | **O(1)** |

Single pass, constant space — **optimal solution**.

---

## 🧠 Why This Works

* We enforce **strict order**
* We ensure **all three phases exist**
* We avoid extra arrays or flags
* We stop immediately if pattern breaks

---
