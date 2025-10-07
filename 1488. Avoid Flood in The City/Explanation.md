# 🌧️ 1488. Avoid Flood in The City

**Difficulty:** Medium  
**Topics:** Greedy, Simulation, HashMap, TreeSet  

---

## 🧠 Problem Summary

Given an array `rains`:

- `rains[i] > 0`: It rains over lake `rains[i]` on day `i`.
- `rains[i] == 0`: No rain; you can dry **one lake** that day.

If it rains on a lake that's already full and hasn't been dried, a **flood** occurs.

**Goal:**  
Plan which lakes to dry on dry days so that **no floods occur**. Return an array `ans`:
- `ans[i] = -1` if it rained that day.
- `ans[i] = lake number` if you dry that lake on day `i`.
- If impossible, return `[]`.

---

## 🧩 Examples

### Example 1
```text
Input:  rains = [1,2,3,4]
Output: [-1,-1,-1,-1]
Explanation: Each lake is filled once; no repeats, so no floods.
```

### Example 2
```text
Input:  rains = [1,2,0,0,2,1]
Output: [-1,-1,2,1,-1,-1]
Explanation:
Day 1: Fill lake 1  
Day 2: Fill lake 2  
Day 3: Dry lake 2  
Day 4: Dry lake 1  
Day 5: Fill lake 2  
Day 6: Fill lake 1  
No flood occurs.
([-1,-1,1,2,-1,-1] is also valid.)
```

### Example 3
```text
Input:  rains = [1,2,0,1,2]
Output: []
Explanation: Impossible to prevent flooding.
```

---

## 💡 Approach & Reasoning

When you see a dry day (`0`), you must choose carefully which lake to dry, based on future rains.

### What to Track
- Which lakes are currently full.
- The indices of dry days available.
- The last day each lake was filled.

### Data Structures
- **HashMap:** Tracks last day each lake got rain (`lake → lastRainDay`).
- **TreeSet:** Stores indices of dry days in sorted order (to find the next dry day efficiently).

### Algorithm Steps

1. **Rainy Day (`rains[i] > 0`):**
    - If lake is empty, mark as full.
    - If lake is already full, find the earliest dry day after its last rain to dry it before today.
      - Use `TreeSet.higher(lastRainDay)` to find the next dry day.
      - If none exists, return `[]` (flood).
      - Assign that dry day to dry this lake.

2. **Dry Day (`rains[i] == 0`):**
    - Store the day in `dryDays` (TreeSet).
    - Assign a lake to dry when needed.

3. **Update:**
    - After each rain, update `lakeMap` with the day the lake was filled.
    - After each dry day, assign the lake number when used.

---

## 🧰 Java Solution

```java
import java.util.*;

class Solution {
     public int[] avoidFlood(int[] rains) {
          int n = rains.length;
          int[] ans = new int[n];
          Map<Integer, Integer> lakeMap = new HashMap<>();
          TreeSet<Integer> dryDays = new TreeSet<>();

          for (int i = 0; i < n; i++) {
                if (rains[i] == 0) {
                     dryDays.add(i);
                     ans[i] = 1; // default
                } else {
                     int lake = rains[i];
                     ans[i] = -1;
                     if (lakeMap.containsKey(lake)) {
                          int lastRainDay = lakeMap.get(lake);
                          Integer dryDay = dryDays.higher(lastRainDay);
                          if (dryDay == null) {
                                return new int[0];
                          }
                          ans[dryDay] = lake;
                          dryDays.remove(dryDay);
                     }
                     lakeMap.put(lake, i);
                }
          }
          return ans;
     }
}
```

---

## ⏱️ Complexity Analysis

| Type  | Complexity | Explanation                                 |
|-------|------------|---------------------------------------------|
| Time  | O(n log n) | TreeSet operations (add, higher, remove).   |
| Space | O(n)       | HashMap and TreeSet storage.                |

---

## ✅ Summary

| Concept         | Key Idea                                              |
|-----------------|------------------------------------------------------|
| What we’re doing| Preventing floods by drying lakes at the right time. |
| How we do it    | Track full lakes and use future dry days efficiently.|
| Why it works    | Greedy choice ensures minimal, precise drying.       |

**Result Example:**
```text
Input:  [1,2,0,0,2,1]
Output: [-1,-1,2,1,-1,-1]
```
