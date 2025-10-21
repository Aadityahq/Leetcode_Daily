### 🧠 **Problem Understanding**

We are given:

* An integer array `nums`
* Two integers `k` and `numOperations`

We can perform **`numOperations`** operations, where in each operation:

1. Select an index `i` (which hasn’t been used before)
2. Add any integer in range `[-k, k]` to `nums[i]`

Our goal is to find the **maximum possible frequency** of any element after all operations.

---

### 🔍 **Example**

**Input:**

```
nums = [1, 4, 5]
k = 1
numOperations = 2
```

**Output:**

```
2
```

**Explanation:**

* Add `0` to `nums[1]` → `[1, 4, 5]`
* Add `-1` to `nums[2]` → `[1, 4, 4]`
  Now, the frequency of `4` is **2** → ✅ maximum frequency = 2

---

### ⚙️ **Approach**

We want to make as many numbers as possible equal to some target `x`.

For every element `nums[i]`,
we can transform it to any number in **range** `[nums[i] - k, nums[i] + k]`.

Hence, if a value `x` lies inside these ranges for multiple elements,
it means multiple elements **can be turned into `x`**.

So, for every integer value:

* Count how many numbers can reach it using a **sweep-line (difference array)**.
* Use `freq[x]` = how many are *already* equal to `x`.
* Use `cnt` = how many *could* become `x` after changing.
* We can only change up to `numOperations` elements.

Thus:

```
possible frequency = freq[x] + min(cnt - freq[x], numOperations)
```

We keep track of the maximum possible frequency for any `x`.

---

### 🧩 **Algorithm Steps**

1. Initialize arrays:

   ```java
   freq[N], sweep[N]
   ```

   where `N = 1e5 + 2`.

2. For every `x` in `nums`:

   * `freq[x]++`
   * Mark its convertible range:

     ```
     sweep[max(1, x - k)] += 1
     sweep[min(x + k + 1, N - 1)] -= 1
     ```
   * Track smallest and largest range limits (`mm`, `MM`)

3. Perform prefix sum over `sweep[]`:

   * `cnt += sweep[x]`
     → tells how many numbers can be converted into `x`

4. Calculate maximum possible frequency:

   ```
   ans = max(ans, freq[x] + min(cnt - freq[x], numOperations))
   ```

5. Return `ans`.

---

### 🧮 **Dry Run**

**nums = [1, 4, 5], k = 1, numOperations = 2**

| x | sweep[] change | comment         |
| - | -------------- | --------------- |
| 1 | [1, 2]         | can reach 1,2   |
| 4 | [3, 5]         | can reach 3,4,5 |
| 5 | [4, 6]         | can reach 4,5,6 |

After sweep prefix:

```
cnt[4] = 2   // elements 4 and 5 can reach 4
freq[4] = 1  // one element already 4
possible = 1 + min(2-1, 2) = 2
```

✅ **Answer = 2**

---

### 💻 **Code (Java)**

```java
class Solution {
    public int maxFrequency(int[] nums, int k, int numOperations) {
        int N = 100002;
        int[] freq = new int[N];
        int[] sweep = new int[N];
        int mm = N, MM = 0;

        for (int x : nums) {
            freq[x]++;
            int x0 = Math.max(1, x - k);
            int xN = Math.min(x + k + 1, N - 1);
            sweep[x0]++;
            sweep[xN]--;
            mm = Math.min(mm, x0);
            MM = Math.max(MM, xN);
        }

        int ans = 0, cnt = 0;
        for (int x = mm; x <= MM; x++) {
            cnt += sweep[x];
            ans = Math.max(ans, freq[x] + Math.min(cnt - freq[x], numOperations));
        }

        return ans;
    }
}
```

---

### 🧩 **Complexity Analysis**

| Complexity | Explanation                                |
| ---------- | ------------------------------------------ |
| **Time**   | `O(N)` — single sweep across all elements  |
| **Space**  | `O(N)` — for `freq[]` and `sweep[]` arrays |

---

### 💡 **Why This Works**

* The **sweep-line technique** efficiently counts overlapping ranges (which elements can become each value).
* We avoid checking each pair manually (which would be O(n²)).
* Then, for each possible value, we compute the best achievable frequency under the operation limit.

---

### 🏁 **Final Answer**

✅ **Maximum frequency achievable after operations = 2**

---
