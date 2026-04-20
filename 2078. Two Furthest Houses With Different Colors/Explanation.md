# 🔍 Problem Understanding

You are given an array `colors[]`, where:

* Each index = house position
* Each value = color of that house

👉 You need to find **two houses with different colors** such that:

* Distance = `|i - j|`
* This distance is **maximum**

---

# 🧠 Key Observation (IMPORTANT)

Instead of checking all pairs (which is O(n²)), think smart:

👉 The **maximum distance** will always involve:

* Either the **first house (index 0)** OR
* The **last house (index n-1)**

Why?

Because distance is largest when indices are far apart.

---

# 💡 Strategy

We try two things:

### Case 1: Compare from left (index 0)

Find the **farthest house from right** whose color ≠ `colors[0]`

---

### Case 2: Compare from right (index n-1)

Find the **farthest house from left** whose color ≠ `colors[n-1]`

---

👉 Take the **maximum of both**

---

# ⚡ Why this works

Because:

* The maximum distance must include an endpoint (0 or n-1)
* Any pair inside will have smaller distance

So we reduce problem from:
❌ O(n²) brute force
✅ to O(n) optimal

---

# ✅ Java Solution

```java
class Solution {
    public int maxDistance(int[] colors) {
        int n = colors.length;
        
        int maxDist = 0;
        
        // Case 1: compare with first house
        for (int i = n - 1; i >= 0; i--) {
            if (colors[i] != colors[0]) {
                maxDist = i;  // distance = i - 0
                break;
            }
        }
        
        // Case 2: compare with last house
        for (int i = 0; i < n; i++) {
            if (colors[i] != colors[n - 1]) {
                maxDist = Math.max(maxDist, (n - 1 - i));
                break;
            }
        }
        
        return maxDist;
    }
}
```

---

# 🔄 Dry Run Example

### Input:

```
colors = [1,1,1,6,1,1,1]
```

---

### Step 1: Compare with first (1)

From right:

* index 6 → 1 ❌
* index 5 → 1 ❌
* index 4 → 1 ❌
* index 3 → 6 ✅

👉 distance = `3 - 0 = 3`

---

### Step 2: Compare with last (1)

From left:

* index 0 → 1 ❌
* index 1 → 1 ❌
* index 2 → 1 ❌
* index 3 → 6 ✅

👉 distance = `6 - 3 = 3`

---

### Final Answer:

```
max(3, 3) = 3
```

---

# ⏱️ Complexity

* Time: **O(n)**
* Space: **O(1)**

---

# 🧠 Interview Insight (VERY IMPORTANT)

If interviewer asks:
👉 *"Why not brute force?"*

You say:

> “Since maximum distance involves endpoints, I optimize by checking differences from first and last elements only, reducing time complexity from O(n²) to O(n).”

---
