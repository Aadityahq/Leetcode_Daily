# 🧠 Problem Understanding

You are given:

* `source[]`
* `target[]`
* `allowedSwaps[][]`

👉 You can swap elements in `source` **only at given index pairs**.

👉 Goal: **Minimize Hamming Distance**
(Hamming distance = number of indices where `source[i] != target[i]`)

---

# 💡 Key Insight (VERY IMPORTANT)

If swaps are allowed like:

```
[0,1], [1,2]
```

Then you can swap:

```
0 ↔ 1 ↔ 2  (indirectly)
```

👉 So indices form **connected components**

Inside one component:

* You can rearrange elements **in any order**

---

# 🔥 Strategy

### Step 1: Build connected components

Use **Union-Find (DSU)** to group indices.

---

### Step 2: For each component

* Count frequency of elements in `source`
* Try matching with `target`

👉 If element exists → reduce count
👉 If not → mismatch++

---

### Step 3: Total mismatches = answer

---

# 🚀 Java Solution (Correct in One Go)

```java
import java.util.*;

class Solution {

    class DSU {
        int[] parent;

        DSU(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++)
                parent[i] = i;
        }

        int find(int x) {
            if (parent[x] != x)
                parent[x] = find(parent[x]); // path compression
            return parent[x];
        }

        void union(int x, int y) {
            int px = find(x);
            int py = find(y);
            if (px != py)
                parent[px] = py;
        }
    }

    public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
        int n = source.length;

        DSU dsu = new DSU(n);

        // Step 1: Build components
        for (int[] swap : allowedSwaps) {
            dsu.union(swap[0], swap[1]);
        }

        // Step 2: Group indices by parent
        Map<Integer, List<Integer>> groups = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int parent = dsu.find(i);
            groups.computeIfAbsent(parent, k -> new ArrayList<>()).add(i);
        }

        int result = 0;

        // Step 3: Process each group
        for (List<Integer> group : groups.values()) {

            Map<Integer, Integer> freq = new HashMap<>();

            // count source elements
            for (int idx : group) {
                freq.put(source[idx], freq.getOrDefault(source[idx], 0) + 1);
            }

            // try matching target
            for (int idx : group) {
                if (freq.getOrDefault(target[idx], 0) > 0) {
                    freq.put(target[idx], freq.get(target[idx]) - 1);
                } else {
                    result++; // mismatch
                }
            }
        }

        return result;
    }
}
```

---

# ⚙️ Why This Works

### ✔️ Because:

* Swaps allow **free rearrangement inside connected components**
* So problem becomes:

  > “Can we rearrange source in this component to match target?”

---

### ✔️ Matching Logic:

Inside a component:

```
source: [1,2,2]
target: [2,1,3]
```

* We match:

  * 2 ✔️
  * 1 ✔️
  * 3 ❌ (not present)

👉 So mismatch = 1

---

# ⏱️ Complexity

* Union-Find: **O(n α(n))**
* Grouping + counting: **O(n)**

👉 Overall: **O(n)** (efficient for 1e5)

---

# 🧩 Intuition in One Line

👉 “You can shuffle values inside connected components — so just match frequencies.”

---

# 🧠 Interview Tip

If you see:

* “Swaps allowed”
* “Minimum mismatch / distance”

👉 Think:

> **Graph + Connected Components + Frequency Matching**

---

