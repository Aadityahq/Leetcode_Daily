# Explanation for "960. Delete Columns to Make Sorted III"## 🔍 Problem Understanding (What is asked?)

You are given **n strings**, all of the **same length `m`**.

You can:

* Delete **entire columns** (same index in every string).

Your goal:

* After deleting some columns, **each individual string (row)** must be **lexicographically sorted**
  (i.e., characters are non-decreasing from left to right).

❗ Important:

* You **do NOT** need `strs[0] <= strs[1] <= ...`
* Each row must be sorted **independently**

👉 Return the **minimum number of columns to delete**.

---

## 🧠 Key Insight (Reframing the problem)

Instead of thinking:

> “Which columns should I delete?”

Think:

> “Which columns should I **keep** so that all rows stay sorted?”

If we can **maximize the number of columns kept**, then:

```
minimum deletions = total columns - columns kept
```

---

## 🔁 When can we keep two columns `j` and `i`?

For columns `j < i`, they can both be kept **only if**:

For **every string**:

```
strs[k][j] <= strs[k][i]
```

This ensures:

* Characters don’t decrease when moving right
* Row remains lexicographically sorted

---

## 💡 This becomes a Longest Increasing Subsequence (LIS) problem

Each column is like an element.
We want the **longest sequence of columns** such that:

* Column indices increase
* Characters across all rows are non-decreasing

---

## 🧮 Dynamic Programming Approach

### DP Definition

```
dp[i] = maximum number of columns we can keep
        if the last kept column is i
```

### Initialization

```
dp[i] = 1   // each column can stand alone
```

---

### Transition

For every previous column `j < i`:

* If column `j` can come before `i` (i.e., valid for all rows):

```
dp[i] = max(dp[i], dp[j] + 1)
```

---

### Final Answer

```
maxKept = max(dp[i] for all i)
answer = totalColumns - maxKept
```

---

## ✅ Why `isSorted(j, i)` works

```java
private boolean isSorted(String[] strs, int j, int i) {
    for (String s : strs) {
        if (s.charAt(j) > s.charAt(i)) {
            return false;
        }
    }
    return true;
}
```

This ensures:

* For **every row**, character at column `j` is ≤ character at column `i`
* So keeping both columns does **not break row sorting**

---

## 🧩 Walkthrough Example

### Example 1

```
strs = ["babca", "bbazb"]
```

Try to keep columns:

* Column 0 → b, b
* Column 2 → b, a ❌ (row 2 breaks)
* Column 3 → c, z ✅

Best we can keep = 2 columns
Total columns = 5

```
minimum deletions = 5 - 2 = 3
```

✔️ Matches output

---

## ⏱️ Time & Space Complexity

* **Time:**
  `O(m² × n)`

  * `m` columns
  * `n` strings
* **Space:**
  `O(m)` for DP array

Given constraints (`≤ 100`), this is efficient.

---

## 🧠 Why this solution is optimal

* Tries **all valid column orderings**
* Uses DP to avoid recomputation
* Converts a deletion problem into a **maximum keep problem**
* Guarantees minimum deletions

---
