# 🧠 Problem Understanding

You are given an array `nums`.

A **good tuple (i, j, k)** must satisfy:

* `i, j, k` are **distinct**
* `nums[i] == nums[j] == nums[k]`

Distance formula:
[
|i - j| + |j - k| + |k - i|
]

---

# 🔍 Key Observation (VERY IMPORTANT)

If indices are sorted:

```
i < j < k
```

Then:
[
|i-j| + |j-k| + |k-i| = (j-i) + (k-j) + (k-i) = 2 * (k - i)
]

👉 So the distance **only depends on the first and last index**!

---

# 🚀 Problem Simplifies To:

For every value:

* Find **3 indices** of that value
* Minimize:

```
2 * (k - i)
```

---

# 💡 Optimal Strategy

Instead of checking all triplets (O(n³) ❌), do:

### Step 1: Group indices by value

Example:

```
nums = [1,2,1,1,3]

1 → [0,2,3]
2 → [1]
3 → [4]
```

---

### Step 2: For each value with ≥ 3 indices

We only need **3 consecutive indices**!

Why?

* To minimize `(k - i)`, pick indices **closest together**

So check:

```
indices[i], indices[i+1], indices[i+2]
```

Distance:

```
2 * (indices[i+2] - indices[i])
```

---

# ⚡ Time Complexity

* Building map: **O(n)**
* Processing each list: **O(n)**

✅ Total: **O(n)**

---

# ✅ Java Solution

```java
import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        // Step 1: Store indices
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
        
        int ans = Integer.MAX_VALUE;
        
        // Step 2: Process each value
        for (List<Integer> list : map.values()) {
            if (list.size() < 3) continue;
            
            // Check consecutive triplets
            for (int i = 0; i + 2 < list.size(); i++) {
                int dist = 2 * (list.get(i + 2) - list.get(i));
                ans = Math.min(ans, dist);
            }
        }
        
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
```

---

# 🔥 Why This Works

### ❌ Brute Force

* Try all `(i, j, k)` → O(n³)

### ✅ Optimized Insight

* Distance reduces to:

```
2 * (k - i)
```

So:

* Only care about **first & last index**
* Middle index just needs to exist

---

# 🧩 Example Walkthrough

### Input:

```
[1,2,1,1,3]
```

Indices of `1`:

```
[0,2,3]
```

Check:

```
i=0, j=2, k=3
distance = 2*(3-0) = 6
```

---

# ⚠️ Common Mistakes

* ❌ Trying all triplets
* ❌ Sorting indices (already sorted by default)
* ❌ Ignoring the math simplification

---

# 🧠 Interview Tip

If you say this in interview:

> “Since i < j < k, the formula simplifies to 2 × (k − i), so I only need to minimize the span of 3 equal elements.”

💯 That’s a strong signal of deep understanding.

---
