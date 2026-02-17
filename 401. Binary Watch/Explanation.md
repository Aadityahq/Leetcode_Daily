## 🔢 401. Binary Watch — Explanation + Java Solution

### 🧠 Problem Understanding

A **binary watch** has:

* **4 LEDs for hours** → values from **0 to 11**
* **6 LEDs for minutes** → values from **0 to 59**

Each LED represents a binary digit (bit).
If a LED is ON → value = 1
If OFF → value = 0

We are given an integer `turnedOn`, which represents the **total number of LEDs that are ON**.

👉 We must return **all possible valid times** such that:

* Total number of ON bits in hour + minute = `turnedOn`
* Hour must be between `0–11`
* Minute must be between `0–59`
* Hour should NOT have leading zero (e.g., `"01:00"` ❌)
* Minute MUST have two digits (e.g., `"10:02"` ✅)

---

## 💡 Key Idea

Instead of manually simulating LEDs, we use a smart observation:

The number of LEDs ON = number of **set bits (1s)** in binary representation.

So:

```
countSetBits(hour) + countSetBits(minute) == turnedOn
```

We:

1. Try all possible hours (0–11)
2. Try all possible minutes (0–59)
3. Check if total set bits == turnedOn
4. If yes → format and add to answer

---

## ❓ Why This Works

* Hours range is small → only 12 values
* Minutes range is small → only 60 values
* Total combinations → 12 × 60 = 720 (very small)
* So brute force is efficient and clean

Time Complexity: **O(720)** ≈ constant
Space Complexity: depends on output

---

## ✅ Java Solution

```java
import java.util.*;

class Solution {
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> result = new ArrayList<>();
        
        for (int hour = 0; hour < 12; hour++) {
            for (int minute = 0; minute < 60; minute++) {
                
                // Count total set bits in hour + minute
                int totalBits = Integer.bitCount(hour) + Integer.bitCount(minute);
                
                if (totalBits == turnedOn) {
                    // Format minute to always have 2 digits
                    String time = hour + ":" + (minute < 10 ? "0" + minute : minute);
                    result.add(time);
                }
            }
        }
        
        return result;
    }
}
```

---

## 🔍 Example Walkthrough

### Example 1:

```
Input: turnedOn = 1
```

We need exactly **1 LED ON**.

Possible cases:

* Hour has 1 bit → minute has 0 bits
* Hour has 0 bits → minute has 1 bit

Valid outputs:

```
0:01
0:02
0:04
0:08
0:16
0:32
1:00
2:00
4:00
8:00
```

---

### Example 2:

```
Input: turnedOn = 9
```

Maximum LEDs = 4 (hour) + 6 (minute) = 10
But:

* Max bits in hour (11) = 3
* Max bits in minute (59) = 5
* Maximum practical total = 8

So 9 is impossible → return empty list.

---

## 🎯 Why Use `Integer.bitCount()`?

Java provides:

```java
Integer.bitCount(number)
```

It efficiently counts number of `1`s in binary representation.

Example:

```
Integer.bitCount(5)
5 = 101 → 2
```

This makes our solution clean and optimal.

---

## 🚀 Summary

✔ Try all valid hour-minute combinations
✔ Count set bits
✔ If equals `turnedOn`, add formatted time
✔ Use `Integer.bitCount()` for simplicity

---
