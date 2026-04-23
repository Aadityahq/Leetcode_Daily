# 🧠 Problem Understanding

You are given an array `nums`.

For every index `i`, you need:

[
arr[i] = \sum |i - j| \quad \text{for all } j \text{ where } nums[j] = nums[i] \text{ and } j \neq i
]

👉 In simple words:
For each element, find all **same values** and sum the **distance of indices**.

---

### 🔍 Example

`nums = [1,3,1,1,2]`

For value `1`, indices = `[0,2,3]`

* For index `0`: distances → `|0-2| + |0-3| = 5`
* For index `2`: `|2-0| + |2-3| = 3`
* For index `3`: `|3-0| + |3-2| = 4`

---

# ❌ Brute Force (Wrong Approach)

For each index:

* loop entire array
* check same value
* compute distance

⏱ Complexity = **O(n²)** → too slow for `10^5`

---

# ✅ Optimized Idea (Key Insight)

👉 Group indices of same values

Example:

```
1 → [0,2,3]
3 → [1]
2 → [4]
```

Now solve each group separately.

---

# ⚡ Key Trick (Prefix Sum)

For a group like `[0,2,3]`, instead of recomputing distances again and again:

We use:

* Prefix sum of indices
* Process from left → right
* Then right → left

---

## 🧩 Formula

For index `i` in list:

### Left contribution:

[
i \times count_{left} - sum_{left}
]

### Right contribution:

[
sum_{right} - i \times count_{right}
]

---

# 💡 Why this works

Instead of computing:

```
|i - j|
```

We split into:

* Left side (j < i)
* Right side (j > i)

So absolute value disappears → faster math

---

# 🚀 Java Solution

```java
import java.util.*;

class Solution {
    public long[] distance(int[] nums) {
        int n = nums.length;
        long[] result = new long[n];

        // Step 1: Group indices
        Map<Integer, List<Integer>> map = new HashMap<>();
        
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        // Step 2: Process each group
        for (List<Integer> list : map.values()) {

            int size = list.size();

            // prefix sum
            long[] prefix = new long[size];
            prefix[0] = list.get(0);

            for (int i = 1; i < size; i++) {
                prefix[i] = prefix[i - 1] + list.get(i);
            }

            for (int i = 0; i < size; i++) {

                int index = list.get(i);

                // left side
                long leftSum = (i > 0) ? prefix[i - 1] : 0;
                long leftCount = i;
                long left = index * leftCount - leftSum;

                // right side
                long rightSum = prefix[size - 1] - prefix[i];
                long rightCount = size - i - 1;
                long right = rightSum - index * rightCount;

                result[index] = left + right;
            }
        }

        return result;
    }
}
```

---

# ⏱ Complexity

* Grouping → **O(n)**
* Processing → **O(n)**

✅ Total: **O(n)**
✅ Space: **O(n)**

---

# 🎯 Intuition Recap

Instead of:
❌ Comparing every pair

We:
✔ Group same values
✔ Use prefix sums
✔ Convert absolute distance into math formula

---

# 🧪 Quick Mental Trick

Whenever you see:

> "sum of distances between same elements"

👉 Think:

* grouping
* prefix sum
* left/right split

---

