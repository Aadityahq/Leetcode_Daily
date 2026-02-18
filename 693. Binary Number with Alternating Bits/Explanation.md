## 🔢 693. Binary Number with Alternating Bits

### 📘 Problem Explanation

You are given a **positive integer `n`**.

You must check whether its **binary representation** has **alternating bits**.

👉 Alternating bits means:

* No two adjacent bits are the same.
* Pattern should look like:

  * `101010...` ✅
  * `010101...` ✅
  * `111000` ❌
  * `101100` ❌

---

### 🧠 Examples

| n  | Binary | Alternating? |
| -- | ------ | ------------ |
| 5  | 101    | ✅ Yes        |
| 7  | 111    | ❌ No         |
| 11 | 1011   | ❌ No         |

---

## 💡 Understanding the Solution (Step-by-Step)

### 🔹 Idea

If a number has alternating bits:

```
n       = 101010
n >> 1  = 010101
```

If we XOR them:

```
101010
010101
-------
111111
```

So we get **all 1s**.

---

### 🔹 Why is that useful?

A number that is **all 1s** (like `1111`) has a special property:

```
x & (x + 1) == 0
```

Example:

```
x = 111 (7)
x+1 = 1000 (8)

111
1000
-----
0000
```

So if the result after XOR is all 1s → the number had alternating bits.

---

## 🚀 Optimized Java Solution

```java
class Solution {
    public boolean hasAlternatingBits(int n) {
        int x = n ^ (n >> 1);
        return (x & (x + 1)) == 0;
    }
}
```

---

## 🔍 Step-by-Step Example (n = 5)

```
n = 5
Binary = 101

Step 1: n >> 1
101 → 010

Step 2: XOR
101
010
---
111

Step 3: Check
111 & 1000 = 0

✔ True
```

---

## 🔁 Beginner-Friendly Approach (Loop)

If bit manipulation trick feels confusing, use this:

```java
class Solution {
    public boolean hasAlternatingBits(int n) {
        int prev = n & 1;
        n >>= 1;
        
        while (n > 0) {
            int curr = n & 1;
            if (curr == prev) return false;
            prev = curr;
            n >>= 1;
        }
        
        return true;
    }
}
```

### How it works:

* Extract last bit.
* Compare with previous bit.
* If same → return false.
* If different → continue.

---

## ⏱ Complexity

* **Time:** O(1) (max 31 bits in int)
* **Space:** O(1)

---

