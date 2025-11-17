**1437. Check If All 1's Are at Least Length K Places Away**

---

# ✅ **Problem Understanding**

You are given:

* A binary array `nums` (only 0s and 1s)
* An integer `k`

You need to check:

👉 **For every pair of 1s in the array, the distance between them must be at least `k`**.

Distance means number of *zeros* between them, not index difference.

Example:

```
nums = [1,0,0,0,1], k = 2
Indexes: 0 and 4 → diff = 4 → zeros = 3 ≥ 2 → OK ✔
```

---

# ✅ **Approach**

We simply track the **index of the previous 1**.

* If we find the first 1 → just store its index.
* For every next 1:

  * Check `current_index - previous_index - 1`
  * If this is **less than k**, return false.

Time Complexity: **O(n)**
Space: **O(1)**

---

# ✅ **Code (Java)**

```java
class Solution {
    public boolean kLengthApart(int[] nums, int k) {
        int prev = -1;  // index of previous 1

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                if (prev != -1) {
                    // distance between current 1 and previous 1
                    if (i - prev - 1 < k)
                        return false;
                }
                prev = i; // update position of last 1
            }
        }
        return true;
    }
}
```

---

# ✅ **Example Walkthrough**

### Example 1

`nums = [1,0,0,0,1,0,0,1], k = 2`

1 → prev = 0
Next 1 at index 4 → gap = 4 - 0 - 1 = 3 ≥ 2 ✔
Next 1 at index 7 → gap = 7 - 4 - 1 = 2 ≥ 2 ✔
Return **true**

### Example 2

`nums = [1,0,0,1,0,1], k = 2`

1 at index 0
next 1 at index 3 → gap = 3 - 0 - 1 = 2 ≥ 2 ✔
next 1 at index 5 → gap = 5 - 3 - 1 = 1 < 2 ❌

Return **false**

