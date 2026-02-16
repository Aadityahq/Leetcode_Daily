## 🔹 190. Reverse Bits — Explanation + Java Solution

### 📌 Problem Understanding

You are given a **32-bit signed integer `n`**.

Your task is to **reverse all 32 bits** of this number and return the resulting integer.

👉 Important:

* Even if the number looks small, you must treat it as a **32-bit binary number**.
* Leading zeros matter.
* We reverse bit-by-bit from right to left.

---

### 🧠 Example Understanding

#### Example 1:

```
Input:  43261596
Binary: 00000010100101000001111010011100
```

After reversing:

```
Binary: 00111001011110000010100101000000
Output: 964176192
```

So basically:

* The **last bit becomes first**
* The **second last becomes second**
* Continue for all 32 bits

---

## 🔎 How to Think About It

We need to:

1. Extract the **last bit** of `n`
2. Add it to our result
3. Shift result left
4. Shift `n` right
5. Repeat 32 times

---

## 🔹 Key Bitwise Operators Used

| Operator | Meaning           |
| -------- | ----------------- |
| `&`      | AND (extract bit) |
| `<<`     | Left shift        |
| `>>`     | Right shift       |

---

## 🛠 Step-by-Step Logic

1. Initialize `result = 0`
2. Loop 32 times (since 32 bits)
3. Extract last bit of `n`

   ```
   bit = n & 1
   ```
4. Shift result left

   ```
   result = result << 1
   ```
5. Add extracted bit

   ```
   result = result | bit
   ```
6. Shift `n` right

   ```
   n = n >> 1
   ```

---

## ✅ Java Solution

```java
public class Solution {
    public int reverseBits(int n) {
        int result = 0;
        
        for (int i = 0; i < 32; i++) {
            // Shift result to left to make space
            result = result << 1;
            
            // Add the last bit of n to result
            result = result | (n & 1);
            
            // Shift n to right to process next bit
            n = n >> 1;
        }
        
        return result;
    }
}
```

---

## 🎯 Why This Works

* `n & 1` extracts the last bit.
* `result << 1` makes space for the new bit.
* `|` inserts the bit.
* Repeating 32 times guarantees full reversal.

---

## ⏱ Time & Space Complexity

* **Time:** `O(1)` (Always 32 iterations)
* **Space:** `O(1)`

---

