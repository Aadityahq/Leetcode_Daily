## 🧠 Problem Explanation (In Simple Words)

You are given:

* `apple[]`: Each element represents how many apples are in a pack.
* `capacity[]`: Each element represents how many apples a box can hold.

### Rules:

* Apples from **any pack can go into any box**.
* A single pack’s apples can be split across multiple boxes.
* You must store **all apples**.
* You want to use the **minimum number of boxes**.

---

## 🎯 What Is Actually Being Asked?

You are **not matching packs to boxes one-to-one**.

Instead:

1. Count **total apples**.
2. Choose some boxes such that their **total capacity ≥ total apples**.
3. Minimize the **number of boxes chosen**.

---

## 💡 Key Insight (Most Important Part)

Since:

* Apples are flexible (can be split),
* The only thing that matters is **total capacity**,

👉 To minimize the number of boxes, you should **use the largest capacity boxes first**.

This is a **greedy strategy**.

---

## ✅ Step-by-Step Solution (How & Why)

### Step 1: Calculate total apples

```text
totalApples = sum of apple[]
```

### Step 2: Sort box capacities in descending order

Why?

* Bigger boxes fill more apples
* Fewer boxes needed

### Step 3: Keep adding boxes until capacity ≥ total apples

* Count how many boxes you used
* Stop as soon as apples are fully stored

---

## 🧪 Example Walkthrough

### Example 1:

```
apple = [1, 3, 2] → totalApples = 6
capacity = [4, 3, 1, 5, 2]
```

Sort capacity descending:

```
[5, 4, 3, 2, 1]
```

Pick boxes:

* Take 5 → remaining apples = 1
* Take 4 → remaining apples = -3 (done)

✅ Boxes used = **2**

---

## ⏱️ Time & Space Complexity

* Sorting capacities: **O(m log m)**
* Summation loop: **O(n + m)**
* Overall: **O(m log m)**

Efficient and safe for constraints ≤ 50.

---

## ☕ Java Solution (LeetCode-Ready)

```java
import java.util.Arrays;

class Solution {
    public int minimumBoxes(int[] apple, int[] capacity) {

        // Step 1: Calculate total apples
        int totalApples = 0;
        for (int a : apple) {
            totalApples += a;
        }

        // Step 2: Sort capacities in ascending order
        Arrays.sort(capacity);

        // Step 3: Pick largest boxes first
        int usedBoxes = 0;
        for (int i = capacity.length - 1; i >= 0 && totalApples > 0; i--) {
            totalApples -= capacity[i];
            usedBoxes++;
        }

        return usedBoxes;
    }
}
```

---

## 🧠 Why This Solution Is Correct

* Apples are divisible → no packing constraints.
* Only total capacity matters.
* Greedy choice (largest box first) guarantees **minimum count**.
* Guaranteed solvable by problem constraints.

---

