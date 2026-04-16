## 🔍 Problem Understanding

You are given:

* A string array `words`
* A `target` string
* A `startIndex`

The array is **circular**, meaning:

* From last index → you can go to index `0`
* From index `0` → you can go to last index

👉 From `startIndex`, you can:

* Move **right (i + 1)**
* Move **left (i - 1)**
  Each move = **1 step**

---

## 🎯 Goal

Find the **minimum number of steps** required to reach any index where:

```
words[i] == target
```

If target not present → return `-1`

---

## 💡 Key Idea (IMPORTANT)

Instead of simulating movement…

👉 Just check **every index where target exists**
and compute distance using:

### Circular Distance Formula:

```
distance = min(|i - startIndex|, n - |i - startIndex|)
```

Why?

* `|i - startIndex|` → direct distance
* `n - |i - startIndex|` → wrap-around distance

Take minimum of both.

---

## 🧠 Example Walkthrough

```
words = ["hello","i","am","leetcode","hello"]
startIndex = 1
target = "hello"
```

Target at indices: `0` and `4`

For i = 0:

```
|0 - 1| = 1
n - 1 = 4
→ min = 1
```

For i = 4:

```
|4 - 1| = 3
n - 3 = 2
→ min = 2
```

👉 Answer = **1**

---

## ✅ Java Solution

```java
class Solution {
    public int closestTarget(String[] words, String target, int startIndex) {
        int n = words.length;
        int minDist = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (words[i].equals(target)) {
                int direct = Math.abs(i - startIndex);
                int circular = n - direct;
                int dist = Math.min(direct, circular);

                minDist = Math.min(minDist, dist);
            }
        }

        return minDist == Integer.MAX_VALUE ? -1 : minDist;
    }
}
```

---

## ⚙️ Complexity

* **Time:** `O(n)` → single loop
* **Space:** `O(1)`

---

## 🤔 Why This Works

Instead of moving step-by-step (which is messy in circular arrays):

👉 We directly compute shortest distance mathematically.

Because:

* In circular structure, shortest path is either:

  * direct path
  * or wrap-around path

---

## 🧩 Pattern You Should Learn

This problem teaches a **very important DSA pattern**:

> 🔁 **Circular Array Distance Trick**

Whenever you see:

* circular array
* shortest distance

👉 Always try:

```
min(|i - j|, n - |i - j|)
```

---
