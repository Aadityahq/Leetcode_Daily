# 🧮 2048. Next Greater Numerically Balanced Number

### 🧠 Problem Statement

An integer `x` is **numerically balanced** if **for every digit `d` in `x`, there are exactly `d` occurrences of `d`**.

You are given an integer `n`.
Your task is to **return the smallest numerically balanced number strictly greater than `n`**.

---

### 📘 Example

#### Example 1

**Input:** `n = 1`
**Output:** `22`

**Explanation:**

* `22` → digit `2` appears **2 times** ✅
* It’s numerically balanced and **smallest > 1**.

---

#### Example 2

**Input:** `n = 1000`
**Output:** `1333`

**Explanation:**

* `1333` →

  * `1` appears 1 time ✅
  * `3` appears 3 times ✅
* So `1333` is numerically balanced and **just greater than 1000**.

---

#### Example 3

**Input:** `n = 3000`
**Output:** `3133`

**Explanation:**

* `3133` →

  * `1` appears 1 time ✅
  * `3` appears 3 times ✅
* The next balanced number after 3000.

---

## 🧩 Understanding the Concept

### ❓What is a Numerically Balanced Number?

A number is **numerically balanced** if:

> Each digit `d` appears exactly `d` times in the number.

| Number | Digit Counts            | Balanced? | Why?                         |
| :----- | :---------------------- | :-------- | :--------------------------- |
| 22     | 2 → 2 times             | ✅         | count[2] = 2                 |
| 1333   | 1 → 1 time, 3 → 3 times | ✅         | count[1]=1, count[3]=3       |
| 122    | 1 → 1 time, 2 → 2 times | ✅         | valid                        |
| 1223   | 1→1, 2→2, 3→1           | ❌         | 3 appears once (not 3 times) |
| 1022   | 0 appears 1 time        | ❌         | 0 must appear 0 times        |

---

### ⚙️ How to Solve

We need the **smallest numerically balanced number greater than `n`**.

#### 🧩 Step-by-step approach

1. **Start from `n + 1`**
   Because we need the number strictly greater than `n`.

2. **Check each number one by one**
   For each number, we verify whether it’s numerically balanced.

3. **Count digits**

   * Extract each digit using `% 10`.
   * Use an array `count[10]` to store how many times each digit occurs.

4. **Validate the count**
   For each digit `d` (1–9):

   * If `count[d] != 0` and `count[d] != d`,
     → Not balanced ❌
   * Else if all satisfy the condition,
     → It’s balanced ✅

5. **Return the first valid number**

---

## 💻 Java Code Implementation

```java
class Solution {
    public int nextBeautifulNumber(int n) {
        int num = n + 1;
        while (true) {
            if (isBeautiful(num)) return num;
            num++;
        }
    }

    private boolean isBeautiful(int num) {
        int[] count = new int[10];
        int temp = num;

        // Count occurrences of each digit
        while (temp > 0) {
            int digit = temp % 10;
            count[digit]++;
            temp /= 10;
        }

        // Check if the number is numerically balanced
        for (int d = 0; d <= 9; d++) {
            if (count[d] != 0 && count[d] != d) return false;
        }

        return true;
    }
}
```

---

## 🧠 Why This Works

* We directly **simulate the definition** of a numerically balanced number.
* Since the constraint is small (`n ≤ 10⁶`), it’s okay to check numbers sequentially.
* The validation step ensures every digit `d` appears exactly `d` times.

---

## ⏱️ Time and Space Complexity

| Type      | Complexity | Explanation                                                                                             |
| :-------- | :--------- | :------------------------------------------------------------------------------------------------------ |
| **Time**  | `O(k * d)` | We might check several numbers `k`, and each check takes `O(digits)` time. Since digits ≤ 7, it’s fast. |
| **Space** | `O(1)`     | Only an array of size 10 is used.                                                                       |

---

## ⚡ Example Walkthrough

### Input:

```
n = 1000
```

### Execution Steps:

| Step | Number | Digit Count     | Balanced?             |
| :--- | :----- | :-------------- | :-------------------- |
| 1    | 1001   | {1:2, 0:2}      | ❌ (1 appears 2 times) |
| 2    | 1022   | {1:1, 0:1, 2:2} | ❌ (0 invalid)         |
| 3    | 1333   | {1:1, 3:3}      | ✅ Found!              |

### ✅ Output:

```
1333
```

---

## 💡 Optional Optimization

Since the total count of numerically balanced numbers below 10⁷ is **very small**,
we can **precompute** them and simply find the next greater one using a static list.

Example list:
`[1, 22, 122, 333, 1333, 14444, 55555, 122333, 224444, 126666, 155555, 1224444, 224466, 266666, 333555, 1333555, 444666, 444666, 122333444, …]`

Then just:

```java
for (int num : precomputed)
    if (num > n) return num;
```

This reduces runtime to **O(1)**.

---

### ✅ Final Takeaway

| Concept                  | Meaning                                               |
| :----------------------- | :---------------------------------------------------- |
| **Numerically Balanced** | Each digit appears exactly as many times as its value |
| **Approach**             | Brute-force checking from `n + 1` upward              |
| **Why it works**         | Directly applies the definition; input limit is small |
| **Complexity**           | Efficient for given constraints                       |

---