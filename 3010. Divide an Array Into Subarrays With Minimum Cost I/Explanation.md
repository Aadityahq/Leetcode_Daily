## 🔍 Problem Understanding (What is being asked?)

You are given an integer array `nums` of length `n`.

### Rules:

* You must divide `nums` into **exactly 3 disjoint contiguous subarrays**.
* **Cost of a subarray = its first element**.
* Your goal is to **minimize the total cost** of the 3 subarrays.

---

## 🧠 Key Observation (The “Aha!” moment)

Since:

* Subarrays must be **contiguous**
* Cost depends **only on the first element**

👉 The cost is determined by **where you start each subarray**, not by how long it is.

---

## 💡 How many choices do we really have?

Let’s say:

* First subarray always starts at index `0`
* Second subarray starts at index `i`
* Third subarray starts at index `j`

Where:

```
0 < i < j < n
```

### Total Cost:

```
nums[0] + nums[i] + nums[j]
```

So the problem reduces to:

> **Pick two indices `i` and `j` (i < j) such that nums[i] + nums[j] is minimum**

---

## 🛠️ Strategy (How we solve it)

1. Fix the first subarray (it must start at index `0`)
2. Try **all possible pairs** `(i, j)` for the remaining two subarrays
3. Compute the total cost
4. Track the **minimum**

### Constraints help us:

* `n ≤ 50`
* Bruteforce is totally fine (O(n²))

---

## ✅ Java Solution

```java
class Solution {
    public int minimumCost(int[] nums) {
        int n = nums.length;
        int ans = Integer.MAX_VALUE;

        // i = start of 2nd subarray
        for (int i = 1; i < n - 1; i++) {
            // j = start of 3rd subarray
            for (int j = i + 1; j < n; j++) {
                int cost = nums[0] + nums[i] + nums[j];
                ans = Math.min(ans, cost);
            }
        }

        return ans;
    }
}
```

---

## 🧪 Dry Run Example

### Input:

```
nums = [1, 2, 3, 12]
```

Possible starts:

* `(i=1, j=2)` → `1 + 2 + 3 = 6` ✅
* `(i=1, j=3)` → `1 + 2 + 12 = 15`
* `(i=2, j=3)` → `1 + 3 + 12 = 16`

✔ Minimum = **6**

---

## ⏱️ Complexity Analysis

* **Time Complexity:** `O(n²)`
* **Space Complexity:** `O(1)`

Efficient and clean for given constraints 💯

---

## 🎯 Final Takeaway (Why this works)

* Cost depends **only on starting elements**
* Subarray lengths **don’t matter**
* Reduce the problem to **choosing optimal split points**
* Bruteforce is feasible due to small input size