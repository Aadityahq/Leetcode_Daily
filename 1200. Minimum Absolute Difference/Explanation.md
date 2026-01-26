## 🧠 Problem Understanding (in simple words)

You’re given an array of **distinct integers**.
Your task is to:

1. Find the **minimum absolute difference** between **any two numbers**
2. Return **all pairs** `[a, b]` such that:

   * `a < b`
   * `b - a` equals that minimum difference
3. Output must be **sorted** (ascending pairs)

---

## ❓ Key Question: How do we find the minimum difference efficiently?

### ❌ Brute Force (WHY NOT)

Compare every pair:

* Time: **O(n²)** → too slow for `n = 10⁵`

### ✅ Smart Observation (WHY THIS WORKS)

If the array is **sorted**, then:

> The **minimum absolute difference must be between adjacent elements**

Why?

* Any two non-adjacent elements will have a **larger gap** because numbers in between exist.

So:

1. **Sort the array**
2. Compare only **neighbors**

---

## 🛠️ Step-by-Step Strategy (HOW)

1. **Sort** the array
2. Find the **minimum difference** between adjacent elements
3. Loop again and **collect all pairs** with that difference

---

## 🧪 Example Walkthrough

### Input

```
[4, 2, 1, 3]
```

### After Sorting

```
[1, 2, 3, 4]
```

### Adjacent Differences

* 2 - 1 = 1
* 3 - 2 = 1
* 4 - 3 = 1

➡️ Minimum difference = **1**

### Valid Pairs

```
[1,2], [2,3], [3,4]
```

---

## ✅ Java Solution (Clean & Efficient)

```java
import java.util.*;

class Solution {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        // Step 1: Sort the array
        Arrays.sort(arr);

        // Step 2: Find the minimum absolute difference
        int minDiff = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            minDiff = Math.min(minDiff, arr[i] - arr[i - 1]);
        }

        // Step 3: Collect all pairs with that minimum difference
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - arr[i - 1] == minDiff) {
                result.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }

        return result;
    }
}
```

---

## ⏱️ Time & Space Complexity

| Aspect      | Complexity                  |
| ----------- | --------------------------- |
| Sorting     | **O(n log n)**              |
| Scanning    | **O(n)**                    |
| Total Time  | **O(n log n)**              |
| Extra Space | **O(1)** (excluding output) |

---

## 💡 Why This Solution Is Optimal

✔ Avoids brute-force comparisons
✔ Leverages sorting insight
✔ Works efficiently for large inputs
✔ Clean and readable logic

---

