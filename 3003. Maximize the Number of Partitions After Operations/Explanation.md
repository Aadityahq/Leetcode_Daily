# 🧩 3003. Maximize the Number of Partitions After Operations

**Difficulty:** Hard
**Language:** Java
**Topic Tags:** Greedy, Bitmasking, Prefix-Suffix, Sliding Window

---

## 🧠 Problem Understanding

We are given:

* A string `s` of lowercase letters.
* An integer `k`, representing the maximum number of **distinct characters** allowed in each partition.

We can **change at most one character** in the string before starting the partition process.

Then, we repeatedly:

1. Choose the **longest prefix** of `s` that contains **at most `k` distinct characters**.
2. Remove it from `s` and count it as one partition.
3. Continue until `s` becomes empty.

We must return the **maximum number of partitions possible** after making **at most one character change**.

---

### 🧩 Example 1

**Input:**
`s = "accca"`, `k = 2`

**Optimal Change:** Change `s[2] = 'c'` → `'b'` → `s = "acbca"`

**Partitions:**

* `"ac"` → 1st partition
* `"bc"` → 2nd partition
* `"a"` → 3rd partition

✅ **Output:** `3`

---

### 🧩 Example 2

**Input:**
`s = "aabaab"`, `k = 3`

Since the entire string already contains only 2 distinct characters, changing one letter doesn’t help.

✅ **Output:** `1`

---

## 🧮 Approach

### 🔹 Step 1: Prefix and Suffix Precomputation

We precompute **two arrays**:

* `pref[i]`: Data from the **left** up to index `i`
* `suff[i]`: Data from the **right** starting at index `i`

Each entry in these arrays contains:

* `segCnt`: Number of full partitions so far
* `hasC`: Bitmask representing which characters are used
* `cnt`: Current count of distinct characters in the current segment

This is efficiently stored using a small helper class `Data`.

---

### 🔹 Step 2: Bitmasking for Fast Character Tracking

Each character is represented as one bit in a 26-bit integer.

* To **add** a character: `mask |= (1 << idx)`
* To **check** if character exists: `(mask & (1 << idx)) != 0`
* To **count distinct**: `Integer.bitCount(mask)`

This allows **O(1)** operations for checking and updating distinct character counts.

---

### 🔹 Step 3: Combine Prefix and Suffix Around Each Index

For every index `i` (the potential position we might change a character):

1. Get left (`pref[i]`) and right (`suff[i]`) data.
2. Merge them:

   * `mergedMask = Lmask | Rmask`
   * `bitCount = Integer.bitCount(mergedMask)`
3. Calculate how many partitions we can form:

   ```java
   int segs = segL + segR + 1;
   ```
4. Then, determine the **extra gain (`add`)** we might get by changing one character:

   ```java
   int add;
   if (Math.min(bitCount + 1, 26) <= k)
       add = 0;
   else if (Lcnt == k && Rcnt == k && bitCount < 26)
       add = 2;
   else
       add = 1;
   ```
5. Update answer:

   ```java
   ans = Math.max(ans, segs + add);
   ```

---

## ⚙️ Code Implementation (Java)

```java
class Solution {

    static class Data {
        int segCnt;
        int hasC; // bitmask for 26 lowercase letters
        int cnt;

        Data(int segCnt, int hasC, int cnt) {
            this.segCnt = segCnt;
            this.hasC = hasC;
            this.cnt = cnt;
        }
    }

    public int maxPartitionsAfterOperations(String s, int k) {
        int n = s.length();
        Data[] pref = new Data[n];
        Data[] suff = new Data[n];

        int seg = 0, cnt = 0, mask = 0;

        // prefix computation
        for (int i = 0; i < n - 1; i++) {
            int idx = s.charAt(i) - 'a';
            if ((mask & (1 << idx)) == 0) {
                cnt++;
                if (cnt > k) {
                    seg++;
                    cnt = 1;
                    mask = 0;
                }
                mask |= (1 << idx);
            }
            pref[i + 1] = new Data(seg, mask, cnt);
        }

        // suffix computation
        seg = cnt = 0;
        mask = 0;
        for (int i = n - 1; i > 0; i--) {
            int idx = s.charAt(i) - 'a';
            if ((mask & (1 << idx)) == 0) {
                cnt++;
                if (cnt > k) {
                    seg++;
                    cnt = 1;
                    mask = 0;
                }
                mask |= (1 << idx);
            }
            suff[i - 1] = new Data(seg, mask, cnt);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            Data left = (i < n ? pref[i] : null);
            Data right = (i < n ? suff[i] : null);

            int segL = (left != null ? left.segCnt : 0);
            int Lmask = (left != null ? left.hasC : 0);
            int Lcnt = (left != null ? left.cnt : 0);

            int segR = (right != null ? right.segCnt : 0);
            int Rmask = (right != null ? right.hasC : 0);
            int Rcnt = (right != null ? right.cnt : 0);

            int segs = segL + segR + 1;
            int mergedMask = Lmask | Rmask;
            int bitCount = Integer.bitCount(mergedMask);

            int add;
            if (Math.min(bitCount + 1, 26) <= k) {
                add = 0;
            } else if (Lcnt == k && Rcnt == k && bitCount < 26) {
                add = 2;
            } else {
                add = 1;
            }

            ans = Math.max(ans, segs + add);
        }

        return ans;
    }
}
```

---

## 🧩 Why This Approach Works

* **Prefix–suffix combination** allows us to efficiently simulate what happens if we “cut” and “merge” around any index.
* Using **bitmasks** instead of sets or maps gives a huge speed advantage.
* The logic smartly checks how changing one character might:

  * Reduce the distinct count (allowing longer partitions), or
  * Introduce a new unique character (splitting partitions more).

---

## 🕒 Time & Space Complexity

| Operation                     | Complexity |
| ----------------------------- | ---------- |
| Preprocessing prefix & suffix | **O(n)**   |
| Iterating all positions       | **O(n)**   |
| Bitmask operations            | **O(1)**   |
| **Total Time**                | ✅ **O(n)** |
| **Space**                     | ✅ **O(n)** |

---

## ✅ Summary

| Aspect           | Description                                                               |
| ---------------- | ------------------------------------------------------------------------- |
| **Core Idea**    | Use prefix–suffix partitions and bitmask merging                          |
| **Optimization** | Constant-time bit operations                                              |
| **Key Trick**    | Track how many segments form before and after any change                  |
| **Result**       | Efficient O(n) algorithm that simulates one-character modification impact |

---
