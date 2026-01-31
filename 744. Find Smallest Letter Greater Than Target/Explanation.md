## 🔍 Problem Understanding (in simple words)

You are given:

* A **sorted array of letters**
* A **target letter**

Your task:
👉 **Find the smallest letter that is strictly greater than the target**

Special rule:

* If **no letter is greater than the target**, return the **first letter** of the array (because letters wrap around).

---

## 🧠 Key Observations (WHY this works)

1. The array is **sorted** → perfect use case for **binary search**
2. We need a letter **greater than** the target (not equal)
3. If target is **greater than or equal to all letters**, answer wraps to `letters[0]`

---

## 🧪 Example Walkthrough

### Example:

```text
letters = ["c","f","j"]
target = "c"
```

We want:

* letters **> 'c'**
* That is `"f"` ✅

---

### Another Example:

```text
letters = ["x","x","y","y"]
target = "z"
```

* No letter is greater than `'z'`
* So we **wrap around**
* Return `"x"` (first element)

---

## 🚀 Approach (HOW we solve it)

We use **Binary Search**:

### Steps:

1. Set `left = 0`, `right = letters.length - 1`
2. While `left <= right`

   * Find `mid`
   * If `letters[mid] <= target`
     → move right (`left = mid + 1`)
   * Else
     → move left (`right = mid - 1`)
3. After loop:

   * If `left` is out of bounds → wrap to `letters[0]`
   * Else → `letters[left]` is the answer

---

## ✅ Java Solution (Optimized & Clean)

```java
class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        int left = 0;
        int right = letters.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (letters[mid] <= target) {
                // Move right to find something greater
                left = mid + 1;
            } else {
                // Possible answer, try smaller index
                right = mid - 1;
            }
        }

        // If left goes out of bounds, wrap around
        return left < letters.length ? letters[left] : letters[0];
    }
}
```

---

## ⏱ Time & Space Complexity

* **Time:** `O(log n)` → Binary search
* **Space:** `O(1)` → No extra memory

---

## 🎯 Why Binary Search is the Best Choice

* Linear scan would work, but `O(n)` ❌
* Sorted data → Binary search gives **fast & efficient** solution ✅
* This pattern appears **a LOT** in interviews

---

## 🧩 Pattern to Remember (Very Important)

> **“Find the smallest element greater than X in a sorted array”**
> 👉 Immediately think: **Binary Search**

---
