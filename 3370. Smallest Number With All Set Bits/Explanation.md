## 🧠 Explanation

### 🔹 Intuition

We need to find the **smallest number ≥ n** whose **binary representation contains only set bits (1s)**.

Numbers with all set bits look like this:

```
1  ->  (1)      -> 2¹ - 1
3  ->  (11)     -> 2² - 1
7  ->  (111)    -> 2³ - 1
15 ->  (1111)   -> 2⁴ - 1
31 ->  (11111)  -> 2⁵ - 1
```

So the task is to find the **smallest (2^k - 1) ≥ n**.

---

### 🔹 Approach (How)

1. Start from `x = 1` (binary `1`).
2. Keep generating the next number that has all bits set by using:

   ```java
   x = (x << 1) | 1;
   ```

   * `x << 1` shifts bits left (adds a zero at the end)
   * `| 1` sets the last bit to `1` again
     → This gives us numbers: `1, 3, 7, 15, 31, ...`
3. Stop when `x ≥ n`.
4. Return `x`.

---

### 🔹 Example Walkthrough

#### Example 1:

```
Input: n = 5
x = 1 → 3 → 7
7 ≥ 5 → return 7
```

#### Example 2:

```
Input: n = 10
x = 1 → 3 → 7 → 15
15 ≥ 10 → return 15
```

#### Example 3:

```
Input: n = 3
x = 1 → 3
3 ≥ 3 → return 3
```

---

### 🔹 Why It Works

Every time we do `(x << 1) | 1`,
we are creating the next number that has **one more bit set to 1** than before.
The moment it reaches or exceeds `n`, that’s the **smallest possible number with all 1s**.

---

### 🔹 Complexity

* **Time Complexity:** O(log n) — because `x` doubles in each step
* **Space Complexity:** O(1)

---

### ✅ Final Code

```java
class Solution {
    public int smallestNumber(int n) {
        int x = 1;
        while (x < n) {
            x = (x << 1) | 1;
        }
        return x;
    }
}
```

---
