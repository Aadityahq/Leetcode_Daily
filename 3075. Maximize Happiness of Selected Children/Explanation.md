## 🔍 Problem Intuition (Very Important)

You are allowed to pick **k children** one by one.

* When you pick a child:

  * You gain **that child’s current happiness**
  * All **unpicked children lose 1 happiness** (but not below 0)

Your goal is to **maximize the total happiness gained**.

---

## 🧠 Key Observation

* Since happiness of *remaining* children decreases after every pick,
* **Children picked later will have reduced happiness**.
* So we should:
  👉 **Pick the happiest children first**

This is a **greedy problem**.

---

## ✅ Optimal Strategy

1. **Sort the happiness array in descending order**
2. For each pick `i` (from `0` to `k-1`):

   * Effective happiness =
     `max(happiness[i] - i, 0)`
   * Add it to the answer
3. Stop after `k` picks

Why subtract `i`?

* Because after `i` turns, each remaining child has lost `i` happiness.

---

## ✨ Why This Works (Proof Intuition)

* Any delay in picking a high-happiness child reduces its value.
* Picking the maximum available happiness **earliest** always gives the best result.
* Once happiness becomes `0`, further picks add nothing.

Time Complexity is efficient and works for large constraints.

---

## ⏱️ Complexity

* **Time:** `O(n log n)` (due to sorting)
* **Space:** `O(1)` extra (in-place sort)

---

## 🧪 Example Walkthrough

### Input

```
happiness = [1, 2, 3]
k = 2
```

### After sorting:

```
[3, 2, 1]
```

### Picks:

| Pick | Value | Effective | Sum |
| ---- | ----- | --------- | --- |
| 0    | 3     | 3 - 0 = 3 | 3   |
| 1    | 2     | 2 - 1 = 1 | 4   |

✅ Answer = **4**

---

## 💻 Java Solution (Interview Ready)

```java
import java.util.*;

class Solution {
    public long maximumHappinessSum(int[] happiness, int k) {
        // Sort in ascending order
        Arrays.sort(happiness);

        long sum = 0;
        int n = happiness.length;
        int decrease = 0;

        // Pick from the end (largest values)
        for (int i = n - 1; i >= 0 && k > 0; i--) {
            int effectiveHappiness = happiness[i] - decrease;
            if (effectiveHappiness <= 0) break;

            sum += effectiveHappiness;
            decrease++;
            k--;
        }

        return sum;
    }
}
```

---

