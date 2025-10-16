# 🧩 2598. Smallest Missing Non-negative Integer After Operations

## 📘 Problem Statement

You are given a 0-indexed integer array `nums` and an integer `value`.

In one operation, you can **add or subtract `value`** from any element of `nums`.

> Example:
> If `nums = [1,2,3]` and `value = 2`, you can subtract 2 from `nums[0]` to make it `[-1,2,3]`.

The **MEX (minimum excluded)** of an array is the **smallest missing non-negative integer** in it.

Return the **maximum MEX** of `nums` after applying the operation **any number of times**.

---

### 🧩 Example 1

**Input:**
`nums = [1, -10, 7, 13, 6, 8]`, `value = 5`

**Output:**
`4`

**Explanation:**
By applying the following operations:

* Add `value` to `nums[1]` twice → `[-10 + 10 = 0]`
* Subtract `value` from `nums[2]` once → `[7 - 5 = 2]`
* Subtract `value` from `nums[3]` twice → `[13 - 10 = 3]`

Final array = `[1, 0, 2, 3, 6, 8]`
MEX = 4 ✅ (smallest non-negative integer not present)

---

### 🧩 Example 2

**Input:**
`nums = [1, -10, 7, 13, 6, 8]`, `value = 7`

**Output:**
`2`

---

## 💡 Intuition — *How & Why*

### 🧠 Why does this work?

Each number can be increased or decreased by `value` **any number of times**.
That means we can transform `nums[i]` into **any number having the same remainder modulo `value`**.

For instance,
If `value = 5` and `nums[i] = 7`,
Then we can make `nums[i]` become `…, -3, 2, 7, 12, 17, …`
All of these numbers are **congruent to 2 mod 5**.

So, **the remainder (mod value)** determines which numbers we can represent.

---

### ⚙️ How do we use that?

1. Compute the **remainder** for each element:
   `r = ((num % value) + value) % value` (handles negatives too).

2. Count how many numbers belong to each remainder group.

3. To form numbers starting from `0`:

   * For number `x`, check if we have an available number with remainder `x % value`.
   * If yes → we can form `x`, so we use one instance of that remainder.
   * If not → `x` is missing → **MEX = x**

This works because each remainder group cycles every `value` numbers,
allowing us to fill numbers like `0, 1, 2, 3, …` until one runs out.

---

## 🔍 Example Walkthrough

**nums = [1, -10, 7, 13, 6, 8], value = 5**

| nums[i] | nums[i] % 5 | remainder |
| ------- | ----------- | --------- |
| 1       | 1           | 1         |
| -10     | 0           | 0         |
| 7       | 2           | 2         |
| 13      | 3           | 3         |
| 6       | 1           | 1         |
| 8       | 3           | 3         |

**Remainder frequency:**

```
0 → 1  
1 → 2  
2 → 1  
3 → 2  
4 → 0
```

Now form numbers from 0 upward:

| x | x % 5 | Available? | After Use | Continue? |
| - | ----- | ---------- | --------- | --------- |
| 0 | 0     | ✅          | 0 left    | yes       |
| 1 | 1     | ✅          | 1 left    | yes       |
| 2 | 2     | ✅          | 0 left    | yes       |
| 3 | 3     | ✅          | 1 left    | yes       |
| 4 | 4     | ❌          |           | stop      |

So, the **MEX = 4** ✅

---

## 🧮 Complexity Analysis

| Type      | Complexity                             |
| --------- | -------------------------------------- |
| **Time**  | `O(n + M)` (M = final MEX value, ≤ n)  |
| **Space** | `O(value)` (to store remainders count) |

---

## 💻 Java Solution

```java
class Solution {
    public int findSmallestInteger(int[] nums, int value) {
        int[] modCount = new int[value];

        for (int num : nums) {
            int mod = ((num % value) + value) % value; // handle negatives
            modCount[mod]++;
        }

        int mex = 0;
        while (true) {
            int mod = mex % value;
            if (modCount[mod] == 0) break; // can't form this number
            modCount[mod]--; // use one from this remainder group
            mex++;
        }

        return mex;
    }
}
```

---

## 🏁 Summary

| Step | Description                                                    |
| ---- | -------------------------------------------------------------- |
| 1️⃣  | Reduce all numbers to their positive remainders modulo `value` |
| 2️⃣  | Count how many numbers fall into each remainder class          |
| 3️⃣  | Try forming numbers from `0` upward                            |
| 4️⃣  | The first number you can’t form → **Maximum MEX**              |
| ✅    | Return that number as the answer                               |


