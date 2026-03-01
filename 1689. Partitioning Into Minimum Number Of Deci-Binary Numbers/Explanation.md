## 🔢 1689. Partitioning Into Minimum Number Of Deci-Binary Numbers

### 📘 Problem Understanding

A **deci-binary number** is:

* Contains only digits **0 or 1**
* Has **no leading zeros**
* Example: `101`, `1100` ✅
* Not allowed: `112`, `3001` ❌

You are given a large decimal number as a **string** `n`.

👉 You must find the **minimum number of positive deci-binary numbers** whose sum equals `n`.

---

## 🧠 Key Observation (Very Important)

Each deci-binary number can contribute **at most 1 per digit position**.

So if a digit in `n` is:

* `3` → you need **at least 3 deci-binary numbers**
* `8` → you need **at least 8 deci-binary numbers**

### 🔥 Therefore:

> ✅ The answer is simply the **maximum digit** in the string.

---

## 💡 Why Is This Correct?

Let’s understand step-by-step.

Suppose:

```
n = "32"
```

Digits are `3` and `2`.

To form `3` at the tens place:

* You need 3 deci-binary numbers contributing `1` in that place.

To form `2` at ones place:

* You need 2 deci-binary numbers contributing `1` there.

But since we already need 3 for the tens place, those same 3 numbers can also help build the ones place.

So:

```
10
11
11
----
32
```

Minimum needed = **3**

---

### Another Example

```
n = "82734"
```

Digits → `8, 2, 7, 3, 4`

Largest digit = **8**

So answer = **8**

Because:

* To build digit `8`, you need at least 8 deci-binary numbers.
* That is the limiting factor.

---

## 🎯 Final Logic

The minimum number of deci-binary numbers required =

```
maximum digit in n
```

---

## 💻 Java Solution

```java
class Solution {
    public int minPartitions(String n) {
        int maxDigit = 0;
        
        for (char c : n.toCharArray()) {
            int digit = c - '0';
            maxDigit = Math.max(maxDigit, digit);
            
            // Optimization: if we find 9, we can stop early
            if (maxDigit == 9) {
                return 9;
            }
        }
        
        return maxDigit;
    }
}
```

---

## ⏱ Time & Space Complexity

* **Time Complexity:** O(n)
  (We scan the string once)

* **Space Complexity:** O(1)
  (Only one variable used)

---

## 🏆 Why This Is Optimal?

Because:

* Each deci-binary number can only contribute **1 per digit position**
* So the highest digit determines the minimum count
* We cannot do better than that

---

