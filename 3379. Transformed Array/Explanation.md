## 🧠 Problem Understanding (in simple words)

You’re given an integer array `nums` that behaves like a **circle**.

For every index `i`:

* If `nums[i] > 0` → move **right** `nums[i]` steps
* If `nums[i] < 0` → move **left** `abs(nums[i])` steps
* If `nums[i] == 0` → stay there, copy `0`

Because the array is **circular**:

* Going past the last index wraps to the start
* Going before index `0` wraps to the end

Each index is processed **independently**, and the value you land on goes into `result[i]`.

---

## 🔑 Key Insight (Most Important Part)

Circular movement can be handled using **modulo (`%`)**.

For an array of size `n`:

* Moving right:

```
newIndex = (i + nums[i]) % n
```

* Moving left (nums[i] is negative):

```
newIndex = (i + nums[i]) % n
```

But Java `%` can give **negative indices**, so we fix it using:

```
(newIndex + n) % n
```

This guarantees the index is always between `0` and `n-1`.

---

## ✅ Approach

1. Create a result array of the same size
2. Loop through each index `i`
3. If `nums[i] == 0`, directly assign `0`
4. Otherwise:

   * Calculate the new index using circular math
   * Assign `nums[newIndex]` to `result[i]`
5. Return `result`

---

## 💡 Why This Works

* We never modify the original array
* Each element is processed independently (as required)
* Modulo ensures circular wrapping
* Time complexity is **O(n)** — efficient for small constraints

---

## 🧪 Example Walkthrough (quick)

For `nums = [3, -2, 1, 1]`, `n = 4`

At `i = 1`, `nums[1] = -2`:

```
newIndex = (1 + (-2)) % 4 = -1
fixedIndex = (-1 + 4) % 4 = 3
result[1] = nums[3] = 1
```

---

## 🧩 Java Solution

```java
class Solution {
    public int[] constructTransformedArray(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                result[i] = 0;
            } else {
                int newIndex = (i + nums[i]) % n;
                // handle negative index
                newIndex = (newIndex + n) % n;
                result[i] = nums[newIndex];
            }
        }
        return result;
    }
}
```

---

## ⏱️ Complexity Analysis

* **Time Complexity:** `O(n)`
* **Space Complexity:** `O(n)` (for result array)

---

## 🧠 Final Takeaway

This problem is mainly about:

* Understanding **circular arrays**
* Using **modulo correctly (especially with negatives)**
* Keeping logic simple and clean

