# 📘 **Find Minimum Operations to Make All Elements Divisible by Three**

### Problem Explanation + How & Why the Solution Works

---

# 🧩 **Problem Breakdown**

You are given an array of integers.
In **one operation**, you can:

➡️ **add 1** to any element
➡️ **subtract 1** from any element

Your goal:

### 🎯 **Make every number divisible by 3 using the minimum operations.**

A number is divisible by 3 when:

```
num % 3 == 0
```

---

# 🔍 **HOW to Think About the Problem**

Every integer leaves one of these remainders when divided by 3:

| Number                      | Remainder (mod 3) | Example |
| --------------------------- | ----------------- | ------- |
| divisible by 3              | 0                 | 3,6,9   |
| 1 more than a multiple of 3 | 1                 | 1,4,7   |
| 1 less than a multiple of 3 | 2                 | 2,5,8   |

### Now ask: how far is each remainder from being divisible by 3?

---

# 🎯 **HOW Many Operations Each Needs**

### 1️⃣ **If `num % 3 == 0`**

Already divisible → **0 operations**

### 2️⃣ **If `num % 3 == 1`**

Example: 1, 4, 7
To make them divisible, we can:

* subtract 1 → becomes divisible
  → **1 operation**

### 3️⃣ **If `num % 3 == 2`**

Example: 2, 5, 8
To make them divisible, we can:

* add 1 → becomes divisible
  → **1 operation**

---

# ⭐ **WHY This Always Works**

Because the distance from the nearest multiple of 3 is **always 0 or 1**.

For any number:

* `num % 3 == 0` → already divisible
* `num % 3 == 1` → nearest multiple is `num - 1`
* `num % 3 == 2` → nearest multiple is `num + 1`

No number ever needs **2 or more operations** to reach a multiple of 3.

This makes the problem extremely simple:
👉 Every number with remainder 1 or 2 contributes exactly **1** to the answer.

---

# 🛠️ **Solution Logic**

1. Loop through the array.
2. For each number:

   * compute remainder = num % 3
   * if remainder is 1 or 2 → add 1 to operations
3. Return the total.

Time complexity: **O(n)**
Space complexity: **O(1)**

---

# 💻 **Java Implementation**

```java
class Solution {
    public int minimumOperations(int[] nums) {
        int operations = 0;

        for (int num : nums) {
            int r = num % 3;
            if (r == 1 || r == 2) {
                operations += 1;
            }
        }

        return operations;
    }
}
```

---

# 📌 **Example**

### Input:

```
nums = [1, 2, 3, 4]
```

| Number | Remainder | Ops |
| ------ | --------- | --- |
| 1      | 1         | 1   |
| 2      | 2         | 1   |
| 3      | 0         | 0   |
| 4      | 1         | 1   |

Total = **3 operations**

This matches the sample output.

---

# ✅ **Final Summary**

### **HOW it works:**

* Check the remainder of each number mod 3.
* If the number is 1 or 2 away from a divisible-by-3 number → need 1 operation.

### **WHY this is correct:**

* Every integer is at most **1 unit** away from a multiple of 3.
* So each number with remainder 1 or 2 always costs exactly 1 operation.

Simple, efficient, and optimal.

---

