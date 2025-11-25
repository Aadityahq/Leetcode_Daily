# 📌 1015. Smallest Integer Divisible by K — Explained (How & Why)

## 🧩 **Problem Summary**

You are given an integer **k**.
You must find the **length** of the smallest positive integer that:

* consists **only** of the digit **1**
  (examples: `1`, `11`, `111`, `1111`, ...)
* is **divisible by k**

If no such integer exists, return **-1**.

⚠️ **Important:** The number can be extremely large and cannot fit in any standard integer type.
So we must **not** create the actual number — only simulate it.

---

# ❗ Why could the answer be -1?

Any number made only of **1s**:

* is **odd** → cannot be divisible by **2**
* does **not end in 0 or 5** → cannot be divisible by **5**

👉 So if **k is divisible by 2 or 5**, the answer is **impossible** → return `-1`.

---

# 💡 Key Idea (How & Why)

## 🔸 **How to build numbers of only 1s?**

You can think of them in this order:

| Length | Number |
| ------ | ------ |
| 1      | 1      |
| 2      | 11     |
| 3      | 111    |
| 4      | 1111   |

If the previous number is `x`, the next number is:

```
next = x * 10 + 1
```

But **we cannot store this number** — it will grow too big.

---

# 🔸 The Trick: Work with **remainders only**

Let’s use modular arithmetic.

If you know:

```
r = current_number % k
```

Then the next number’s remainder is:

```
new_r = (r * 10 + 1) % k
```

This way:

* we never store the huge number
* we only store the remainder (which is ≤ k)

---

# 🔸 Why will the loop run at most k times?

Possible remainders are:
`0, 1, 2, ..., k-1` → total of **k remainders**.

If we generate more than **k** numbers and never hit remainder **0**,
remainders will start repeating → a cycle forms → impossible to reach 0 later.

So after **k iterations**, if remainder is not zero → answer is `-1`.

---

# 🔥 Final Algorithm (Step-by-Step)

1. If `k % 2 == 0` or `k % 5 == 0` → return **-1**
2. Set `remainder = 0`
3. Loop `length` from 1 to `k`:

   * Update:

     ```
     remainder = (remainder * 10 + 1) % k
     ```
   * If `remainder == 0` → return `length`
4. Return `-1`

---

# ✅ Example Walkthrough

### Example: `k = 3`

| Length | Number | Remainder |
| ------ | ------ | --------- |
| 1      | 1      | 1         |
| 2      | 11     | 2         |
| 3      | 111    | 0         |

Since remainder becomes `0` at length **3**, answer = **3**.

---

# 💻 Code Implementation (Java)

```java
class Solution {
    public int smallestRepunitDivByK(int k) {
        // If divisible by 2 or 5, impossible
        if (k % 2 == 0 || k % 5 == 0) return -1;

        int remainder = 0;
        for (int length = 1; length <= k; length++) {
            remainder = (remainder * 10 + 1) % k;
            if (remainder == 0) return length;
        }
        return -1;
    }
}
```

---

# 🎯 Final Intuition

We are mathematically constructing numbers like `1`, `11`, `111`, etc.,
**without** ever storing them, by tracking only their remainders mod `k`.

* If we ever reach remainder `0`, we found our answer.
* If we loop `k` times without hitting `0`, it is impossible.

This uses **modular arithmetic** and **pigeonhole principle** to solve the problem efficiently.

---
