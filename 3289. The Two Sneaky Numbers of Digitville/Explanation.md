## 🧩 Problem Understanding

You’re given an array `nums` that *should* contain all integers from `0` to `n - 1` exactly once.
However, **two numbers** appear *twice* — these are the *“sneaky”* numbers.

Your job:
➡️ Find those two numbers and return them in any order.

---

### ✨ Example 1

```
nums = [0, 1, 1, 0]
```

Here,

* `0` appears twice
* `1` appears twice

✅ Output: `[0, 1]`

---

### ✨ Example 2

```
nums = [0, 3, 2, 1, 3, 2]
```

Here,

* `2` appears twice
* `3` appears twice

✅ Output: `[2, 3]`

---

## 🧠 How to Think About It

There are a few easy ways to solve this:

### 🪄 Approach 1: Using a Frequency Array (Best for Constraints)

Since we know that:

* `0 <= nums[i] < n`
* and `n ≤ 100`

We can easily make a **count array** to record how many times each number appears.

**Steps:**

1. Create an integer array `count` of size `n` (all zeros).
2. Traverse `nums` → increment `count[num]` each time you see a number.
3. Any number whose count is **2** is one of the sneaky numbers.

---

### ✅ Java Solution

```java
import java.util.*;

class Solution {
    public int[] getSneakyNumbers(int[] nums) {
        int n = nums.length - 2;  // since two extra numbers are added
        int[] count = new int[n];
        List<Integer> sneaky = new ArrayList<>();

        for (int num : nums) {
            count[num]++;
            if (count[num] == 2) {
                sneaky.add(num);
            }
        }

        return new int[]{sneaky.get(0), sneaky.get(1)};
    }
}
```

---

### 🧾 Explanation

Let’s walk through with an example 👇

#### Input:

```
nums = [0, 3, 2, 1, 3, 2]
n = 4
```

#### Step 1: Initialize `count = [0, 0, 0, 0]`

#### Step 2: Traverse the array

| num | count array after update | Comments                     |
| --- | ------------------------ | ---------------------------- |
| 0   | [1, 0, 0, 0]             | first time                   |
| 3   | [1, 0, 0, 1]             | first time                   |
| 2   | [1, 0, 1, 1]             | first time                   |
| 1   | [1, 1, 1, 1]             | first time                   |
| 3   | [1, 1, 1, 2]             | **3 appears twice → sneaky** |
| 2   | [1, 1, 2, 2]             | **2 appears twice → sneaky** |

✅ Sneaky numbers = `[3, 2]`

---

### ⏱️ Complexity

| Type                 | Value |
| -------------------- | ----- |
| **Time Complexity**  | O(n)  |
| **Space Complexity** | O(n)  |

Perfect for `n ≤ 100`.

---

### 🧠 Alternate Approaches (for understanding)

* **Using HashMap** — similar logic but more general.
* **Using Set** — if you see a number that’s already in the set → it’s sneaky.

  ```java
  Set<Integer> seen = new HashSet<>();
  List<Integer> sneaky = new ArrayList<>();
  for (int num : nums) {
      if (!seen.add(num)) sneaky.add(num);
  }
  return new int[]{sneaky.get(0), sneaky.get(1)};
  ```

---

### ✅ Final Answer

```java
class Solution {
    public int[] getSneakyNumbers(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        List<Integer> sneaky = new ArrayList<>();
        for (int num : nums) {
            if (!seen.add(num)) sneaky.add(num);
        }
        return new int[]{sneaky.get(0), sneaky.get(1)};
    }
}
```

---

### 🧩 Summary

| Concept                  | Explanation                      |
| ------------------------ | -------------------------------- |
| **Goal**                 | Find two numbers appearing twice |
| **Idea**                 | Track frequency or use a set     |
| **Best for small n**     | Frequency array                  |
| **Best for general use** | HashSet                          |
| **Time complexity**      | O(n)                             |
| **Space complexity**     | O(n)                             |

---

