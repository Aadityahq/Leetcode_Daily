# ✅ **Problem Explanation (How & Why)**

You are given two integers:

* `low`
* `high`

You must **count how many odd numbers exist between them (including both).**

---

## 🔍 **How do we think about it?**

### ✔ Odd numbers follow a simple pattern:

**Odd = number % 2 == 1**
Examples: 1, 3, 5, 7, 9 …

If we had the range `[1, 10]`, the odd numbers are:

1, 3, 5, 7, 9 → **5 odds**

---

## 🎯 **Key Insight (Why this formula works)**

We do **NOT** want to loop (because high can be up to **1 billion**).

Instead, use a simple formula:

### ⭐ **Number of odds from 1 to X = (X + 1) / 2**

Because every alternate number is odd.

So:

```
countOdds(high) - countOdds(low - 1)
```

This gives odds between low and high.

---

## 🧠 **Even simpler formula**

We can also use a direct formula:

### ✔ If both low and high are even →

Only odd numbers are in between →
→ **(high - low) / 2**

### ✔ If any of them is odd →

You get one extra odd number →
→ **(high - low) / 2 + 1**

---

## 📘 **Final Java Code (Simple & Fast)**

### ✅ **Solution 1: Cleanest approach**

```java
class Solution {
    public int countOdds(int low, int high) {
        return (high + 1) / 2 - (low / 2);
    }
}
```

### 💡 Why this works?

* `(high + 1) / 2` gives count of odd numbers from 1 to high.
* `low / 2` gives count of odd numbers from 1 to low-1.
* Subtract → get odds inside [low, high].

---

# 📝 **Example Walkthrough**

### Example 1

```
low = 3, high = 7
```

Odd numbers → 3, 5, 7 → **3 odds**

Using formula:

```
(high + 1) / 2 = 8 / 2 = 4
(low / 2) = 3 / 2 = 1
Answer = 4 - 1 = 3
```

✔ Correct.

---

### Example 2

```
low = 8, high = 10
```

Odd numbers → 9 → **1 odd**

Using formula:

```
(high + 1) / 2 = 11 / 2 = 5
(low / 2) = 8 / 2 = 4
Answer = 5 - 4 = 1
```

✔ Correct.

---

# ⭐ Final Summary

| Case                 | Odds Count           |
| -------------------- | -------------------- |
| Both low & high even | (high - low) / 2     |
| Otherwise            | (high - low) / 2 + 1 |
| Clean formula        | (high + 1)/2 - low/2 |

Fast, constant time, no loops — perfect for large inputs.

---

\
