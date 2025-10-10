Here’s a clean and well-structured **`README.md`** file for the LeetCode problem **3147. Taking Maximum Energy From the Mystic Dungeon**, written in a way that looks professional and readable for your GitHub repository 👇

---

````markdown
# 🧙‍♂️ 3147. Taking Maximum Energy From the Mystic Dungeon

**Level:** Medium  
**Topics:** Dynamic Programming, Arrays  

---

## 🧩 Problem Statement

In a mystic dungeon, `n` magicians stand in a line. Each magician has an energy value that could be **positive** (giving energy) or **negative** (taking energy away).  

You can start absorbing energy from **any magician**. Once you absorb energy from magician `i`, you are **instantly teleported** to magician `(i + k)`, and this process continues until you can no longer move forward (i.e., when `(i + k)` exceeds the length of the array).

Your goal is to determine the **maximum total energy** you can gain by choosing the best starting point.

---

## 💡 Example

### Example 1

**Input:**
```text
energy = [5, 2, -10, -5, 1]
k = 3
````

**Output:**

```text
3
```

**Explanation:**

* If you start at index `1` (0-based), the journey is:
  → energy[1] = 2
  → teleport to energy[4] = 1
  **Total energy = 2 + 1 = 3**

This is the **maximum possible** energy gain.

---

### Example 2

**Input:**

```text
energy = [-2, -3, -1]
k = 2
```

**Output:**

```text
-1
```

**Explanation:**

* Possible paths:

  * Start at index 0 → -2 + (-1) = -3
  * Start at index 1 → -3
  * Start at index 2 → -1
    The maximum among them is **-1**.

---

## 🧠 Approach (How and Why)

### 🔍 Intuition

When you start from any magician `i`, you collect energy:

```
energy[i] + energy[i + k] + energy[i + 2k] + ...
```

until you go out of bounds.

So the total energy you gain **depends only on the sum of values spaced by k steps**.

We can think **backwards**:

* If you know the maximum energy you can get starting from `i + k`,
  then the total energy from `i` will be:

  ```
  dp[i] = energy[i] + dp[i + k]
  ```
* If `i + k` is out of bounds, then `dp[i] = energy[i]`.

---

### ⚙️ Steps (Dynamic Programming)

1. Create a DP array `dp` of size `n`.
2. Traverse from the **end** of the array to the start.
3. For each index `i`:

   * If `i + k < n`:
     `dp[i] = energy[i] + dp[i + k]`
   * Else:
     `dp[i] = energy[i]`
4. The answer will be the **maximum value** in the `dp` array.

---

### 🧩 Example Walkthrough

Let’s take:

```
energy = [5, 2, -10, -5, 1], k = 3
```

| Index | energy[i] | i + k | dp[i + k] | dp[i] = energy[i] + dp[i+k] |
| :---- | :-------- | :---- | :-------- | :-------------------------- |
| 4     | 1         | 7 ❌   | -         | 1                           |
| 3     | -5        | 6 ❌   | -         | -5                          |
| 2     | -10       | 5 ❌   | -         | -10                         |
| 1     | 2         | 4 ✅   | 1         | 3                           |
| 0     | 5         | 3 ✅   | -5        | 0                           |

**Result:**
`dp = [0, 3, -10, -5, 1]`
✅ **Maximum energy = 3**

---

## ⏱️ Complexity Analysis

| Type      | Complexity                             |
| --------- | -------------------------------------- |
| **Time**  | O(n) — single pass from right to left  |
| **Space** | O(n) — for DP array (can be optimized) |

---

## 🚀 Optimized Version

Since we only need `dp[i + k]` to compute `dp[i]`, we can reuse the same array `energy` for in-place computation, reducing space to **O(1)**.

---

## 💻 Code (Java)

```java
class Solution {
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int maxEnergy = Integer.MIN_VALUE;

        for (int i = n - 1 - k; i >= 0; i--) {
            energy[i] += energy[i + k];
        }

        for (int val : energy) {
            maxEnergy = Math.max(maxEnergy, val);
        }

        return maxEnergy;
    }
}
```

---

## 🏁 Summary

## 🏁 Summary

| Concept          | Description                                                                         |
| ---------------- | ----------------------------------------------------------------------------------- |
| **Core Idea**    | Use DP to accumulate energy jumps backwards.                                        |
| **Why It Works** | Each magician's total energy depends only on the next reachable magician `(i + k)`. |
| **Result**       | Efficiently finds the best starting point in `O(n)` time.                          |

---

**⭐ Final Thought:**
This problem beautifully demonstrates how *"thinking backwards"* simplifies dynamic problems involving step-based transitions.
