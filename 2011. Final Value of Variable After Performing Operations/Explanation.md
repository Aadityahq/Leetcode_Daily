## 🧠 Explanation

### **Problem Understanding**

We are given a list of operations performed on a variable `X`.
Initially, `X = 0`.

Each operation can be one of the following:

* `"++X"` → Increment `X` by 1
* `"X++"` → Increment `X` by 1
* `"--X"` → Decrement `X` by 1
* `"X--"` → Decrement `X` by 1

We have to find the final value of `X` after performing all operations sequentially.

---

### **How It Works**

Each string represents a single operation that **either increases or decreases** the value of `X` by 1.

* If the operation contains `"++"`, we **add 1** to `X`.
* If the operation contains `"--"`, we **subtract 1** from `X`.

It doesn’t matter whether `++` or `--` comes before or after `X`, because **both have the same effect on the variable**.

---

### **Why This Approach Works**

We only care about **how many increments and decrements** happen — not the order or syntax (`++X` vs `X++`).
Hence, we can iterate through all operations and:

* Increase `X` by 1 if `"+"` is found.
* Decrease `X` by 1 if `"-"` is found.

This gives an **O(n)** time complexity, where `n` is the number of operations, which is optimal for this problem.

---

### **Example Walkthrough**

**Example:**
`operations = ["--X", "X++", "X++"]`

| Step | Operation | Effect    | X after operation |
| ---- | --------- | --------- | ----------------- |
| 1    | `--X`     | Decrement | 0 → -1            |
| 2    | `X++`     | Increment | -1 → 0            |
| 3    | `X++`     | Increment | 0 → 1             |

✅ **Final Output = 1**

---

## 💻 Java Solution

```java
class Solution {
    public int finalValueAfterOperations(String[] operations) {
        int X = 0;
        
        for (String op : operations) {
            if (op.contains("++")) {
                X++; // increment operation
            } else {
                X--; // decrement operation
            }
        }
        
        return X;
    }
}
```

---

### 🕒 **Complexity Analysis**

* **Time Complexity:** O(n) → we loop once through all operations.
* **Space Complexity:** O(1) → we use only one variable `X`.

---

### ✅ **Summary**

* **How:** Check each operation and update `X` accordingly.
* **Why:** Each operation affects `X` by either +1 or -1, so counting increments/decrements gives the final value.

---

