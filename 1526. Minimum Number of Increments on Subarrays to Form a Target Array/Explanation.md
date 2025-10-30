### 🔍 Problem Understanding

We are given an array `target`, and we start with another array `initial` filled with zeros of the same length.
In one operation, we can **choose any subarray** (continuous part of the array) and **increment each element in that subarray by 1**.

We must find the **minimum number of such operations** required to make `initial` exactly equal to `target`.

---

### 💡 Key Insight

When you increment a **subarray**, you are increasing a **continuous range** of elements by the same amount.
So, instead of simulating each increment, think about **how much each element increases compared to its previous one**.

---

### 🧠 Intuitive Idea (The “How”)

Let’s analyze step by step:

1. Start with all zeros:
   `initial = [0, 0, 0, 0, 0]`

2. Suppose your `target` is:
   `target = [1, 2, 3, 2, 1]`

   * From `0 → 1`, you must do **at least 1 operation** (increase first element by 1).
   * From `1 → 2`, that’s an **increase of +1**, so another operation is needed to raise the next part.
   * From `2 → 3`, again **+1**, so one more operation.
   * From `3 → 2`, now it’s **decreasing**, which means you don’t need extra operations — you already had enough increments earlier.
   * From `2 → 1`, again a decrease, no new operation needed.

So, total operations =
`target[0]` (for the first element)

* every time the next element is **greater than** the previous one, add the **difference**.

That’s exactly what this formula does:

```java
if (target[i] > target[i - 1]) {
    ans += target[i] - target[i - 1];
}
```

---

### 🔢 Step-by-Step Example

#### Example: `target = [1, 2, 3, 2, 1]`

| i | target[i-1] | target[i] | Difference | Add to ans?         | ans (running total) |
| - | ----------- | --------- | ---------- | ------------------- | ------------------- |
| 0 | -           | 1         | -          | Yes (first element) | 1                   |
| 1 | 1           | 2         | +1         | Yes                 | 2                   |
| 2 | 2           | 3         | +1         | Yes                 | 3                   |
| 3 | 3           | 2         | -1         | No                  | 3                   |
| 4 | 2           | 1         | -1         | No                  | 3                   |

✅ Final answer = 3

---

### 🧩 Why This Works

Each **increase** in the array (`target[i] > target[i - 1]`) represents a new set of subarray operations that must happen — because the previous subarray operations can’t raise elements beyond their earlier limit.

* When numbers go **up**, you need **new operations**.
* When numbers go **down or stay the same**, no new operation is needed — it’s already covered.

That’s why the total operations =
**`target[0] + Σ(max(target[i] - target[i-1], 0))`**

---

### ⚙️ Code Explanation

```java
class Solution {
    public int minNumberOperations(int[] target) {
        if (target == null || target.length == 0) return 0;
        int n = target.length;
        long ans = target[0]; // initial operations for first element
        for (int i = 1; i < n; i++) {
            if (target[i] > target[i - 1]) {
                ans += (long)(target[i] - target[i - 1]);
            }
        }
        return (int) ans;
    }
}
```

✅ **Time Complexity:** `O(n)`
✅ **Space Complexity:** `O(1)`

---

### 🧾 Summary

| Concept              | Explanation                                                         |
| -------------------- | ------------------------------------------------------------------- |
| **What we’re doing** | Counting the total “increases” in the array.                        |
| **Why it works**     | Each increase means a new subarray increment operation is required. |
| **Formula**          | `ans = target[0] + sum(max(target[i] - target[i-1], 0))`            |
| **Efficiency**       | Linear time, constant space — perfect for large inputs.             |

---
