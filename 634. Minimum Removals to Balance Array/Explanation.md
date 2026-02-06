## 📌 Problem Explanation (What is being asked)

You are given:

* an integer array `nums`
* an integer `k`

An array is called **balanced** if:

```
maximum element ≤ k × minimum element
```

You are allowed to **remove any number of elements**, but the array **cannot be empty**.

👉 Your task is to return the **minimum number of elements to remove** so that the remaining array becomes balanced.

---

## 💡 Key Observation (Why this approach)

Instead of choosing elements to remove, it is **easier and optimal** to:

> Find the **largest possible balanced subarray**
> and remove everything else.

So:

```
minimum removals = total elements − largest balanced subarray size
```

---

## 🧠 Core Idea Behind the Solution

### 1️⃣ Sorting the Array

```java
Arrays.sort(nums);
```

Why sorting?

* After sorting, the **minimum** element is always at the left of a window.
* The **maximum** element is at the right.
* This makes it easy to check the condition:

```
nums[j] ≤ nums[i] × k
```

---

### 2️⃣ Sliding Window / Two Pointers

We use two pointers:

* `i` → start of the window (minimum element)
* `j` → end of the window (maximum element)

The window `[i … j]` represents the current subarray we are checking.

---

### 3️⃣ Expanding and Shrinking the Window

```java
while ((long) nums[j] > (long) nums[i] * k) {
    i++;
}
```

* If the condition breaks, the window is **not balanced**
* We move `i` forward to increase the minimum value
* This restores the balance condition

Using `long` prevents integer overflow when multiplying.

---

### 4️⃣ Track the Largest Balanced Window

```java
maxLen = Math.max(maxLen, j - i + 1);
```

* At every step, we calculate the size of the valid window
* Keep track of the **maximum size found**

---

### 5️⃣ Final Answer

```java
return nums.length - maxLen;
```

* `maxLen` = largest balanced subarray
* Removing everything else gives the **minimum removals**

---

## 🧪 Example Walkthrough

### Example:

```
nums = [1, 6, 2, 9], k = 3
```

After sorting:

```
[1, 2, 6, 9]
```

Balanced window found:

```
[2, 6] → 6 ≤ 2 × 3 ✔
```

Window size = 2
Total elements = 4

```
Answer = 4 - 2 = 2
```

---

## ⏱️ Time and Space Complexity

| Part           | Complexity     |
| -------------- | -------------- |
| Sorting        | O(n log n)     |
| Sliding Window | O(n)           |
| Total Time     | **O(n log n)** |
| Extra Space    | **O(1)**       |

Efficient enough for `n ≤ 10⁵`.

---

## 🎯 Why This Solution Is Optimal

* Avoids checking all subsets (which is impossible for large `n`)
* Uses greedy + two pointers
* Keeps only valid balanced windows
* Guarantees minimum removals

---

## 🏁 Final Summary (Interview Ready)

> We sort the array and use a sliding window to find the largest subarray where the maximum element is at most `k` times the minimum. Since removing the fewest elements means keeping the most elements, we subtract the size of this largest valid window from the total array length to get the answer.

