# 🧠 Problem Understanding

You are given an array `nums`.

A **good tuple (i, j, k)** means:

* All indices are **different**
* Values are equal → `nums[i] == nums[j] == nums[k]`

### 📏 Distance Formula

For a tuple `(i, j, k)`:
[
|i - j| + |j - k| + |k - i|
]

---

# 🔍 Key Insight (VERY IMPORTANT)

If you sort indices such that:

```
i < j < k
```

Then:

```
|i - j| = (j - i)
|j - k| = (k - j)
|k - i| = (k - i)
```

So total distance becomes:

```
(j - i) + (k - j) + (k - i)
= 2 * (k - i)
```

👉 **Final simplified formula:**

```
Distance = 2 * (max_index - min_index)
```

So we don’t need all 3 differences — just:
👉 **smallest index and largest index**

---

# 💡 Approach

### Step 1: Store indices for each number

Use a `HashMap<Integer, List<Integer>>`

Example:

```
nums = [1,2,1,1,3]

Map:
1 → [0, 2, 3]
2 → [1]
3 → [4]
```

---

### Step 2: For each number with ≥ 3 occurrences

We need to pick **3 indices**.

To minimize:

```
2 * (k - i)
```

👉 Choose **closest 3 indices**

So instead of checking all triplets (O(n³)), we:

* Sort indices (already sorted while inserting)
* Check **every consecutive group of 3**

---

### Step 3: Compute minimum distance

For indices `[a, b, c]`:

```
distance = 2 * (c - a)
```

---

# 🚀 Java Solution

```java
import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        // Step 1: Store indices
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }
        
        int minDist = Integer.MAX_VALUE;
        
        // Step 2: Process each value
        for (List<Integer> indices : map.values()) {
            if (indices.size() < 3) continue;
            
            // Step 3: Check consecutive triplets
            for (int i = 0; i <= indices.size() - 3; i++) {
                int a = indices.get(i);
                int c = indices.get(i + 2);
                
                int dist = 2 * (c - a);
                minDist = Math.min(minDist, dist);
            }
        }
        
        return minDist == Integer.MAX_VALUE ? -1 : minDist;
    }
}
```

---

# ⚡ Example Walkthrough

### Input:

```
nums = [1,2,1,1,3]
```

Indices of `1`:

```
[0, 2, 3]
```

Triplet:

```
(0, 2, 3)
```

Distance:

```
2 * (3 - 0) = 6
```

---

# 🧮 Complexity Analysis

### Time Complexity:

* Building map → **O(n)**
* Processing → **O(n)**

👉 Overall: **O(n)**

---

### Space Complexity:

* Map storage → **O(n)**

---

# 🎯 Why This Works

* We reduced a **3-variable problem → 2-variable problem**
* Used **mathematical simplification**
* Avoided brute force (**O(n³)** → **O(n)**)

---

# 🔥 Key Takeaways

* Always try to **simplify formulas**
* Sorting or ordering indices often helps in distance problems
* Look for patterns like:

  * consecutive elements
  * minimizing max - min

---

