## ✅ 1356. Sort Integers by The Number of 1 Bits

### 🔎 Problem Understanding (Simple Words)

You are given an integer array `arr`.

You must sort the array:

1. **First priority** → By number of `1`s in binary representation (ascending).
2. **Second priority** → If two numbers have same number of `1`s, sort them in normal ascending order.

---

### 🧠 Example Understanding

Example:

```
Input: [0,1,2,3,4,5,6,7,8]
```

Binary representations:

| Number | Binary | Count of 1s |
|--------|--------|-------------|
| 0      | 0000   | 0 |
| 1      | 0001   | 1 |
| 2      | 0010   | 1 |
| 3      | 0011   | 2 |
| 4      | 0100   | 1 |
| 5      | 0101   | 2 |
| 6      | 0110   | 2 |
| 7      | 0111   | 3 |
| 8      | 1000   | 1 |

Now sort:

- 0 → (0 ones)
- 1,2,4,8 → (1 one)
- 3,5,6 → (2 ones)
- 7 → (3 ones)

Final Answer:
```
[0,1,2,4,8,3,5,6,7]
```

---

# 💡 Key Idea

We need **custom sorting**:

- Count number of 1 bits
- Sort based on:
  ```
  first -> bit count
  second -> number itself
  ```

---

# 🔢 How to Count 1 Bits?

Java provides:

```java
Integer.bitCount(number)
```

This directly gives number of set bits (1s).

Example:
```
Integer.bitCount(5)  // 5 = 101 → returns 2
```

---

# 🚀 Java Solution

```java
import java.util.*;

class Solution {
    public int[] sortByBits(int[] arr) {
        
        // Convert int[] to Integer[] for custom sorting
        Integer[] temp = new Integer[arr.length];
        for(int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        
        // Custom sort
        Arrays.sort(temp, (a, b) -> {
            
            int countA = Integer.bitCount(a);
            int countB = Integer.bitCount(b);
            
            // First sort by bit count
            if(countA != countB) {
                return countA - countB;
            }
            
            // If bit count same, sort by number
            return a - b;
        });
        
        // Convert back to int[]
        for(int i = 0; i < arr.length; i++) {
            arr[i] = temp[i];
        }
        
        return arr;
    }
}
```

---

# 🧠 Why We Convert int[] to Integer[]?

Because:

- `Arrays.sort(int[])` does NOT support custom comparator.
- `Arrays.sort(Integer[], Comparator)` supports lambda sorting.

Primitive `int` cannot use comparator → so we use `Integer`.

---

# ⏱ Time Complexity

- Sorting takes **O(n log n)**
- `bitCount()` takes O(1) (constant time)
  
So overall:

```
O(n log n)
```

---

# 🏆 Why This Works

We are defining a **custom comparison rule**:

```
Compare(a, b):

If bitCount(a) < bitCount(b) → a first
If bitCount(a) > bitCount(b) → b first
If equal → smaller number first
```

This exactly matches the problem requirement.

---

# 🔥 Cleaner Short Version (Interview Style)

```java
class Solution {
    public int[] sortByBits(int[] arr) {
        return Arrays.stream(arr)
                .boxed()
                .sorted((a, b) -> {
                    int diff = Integer.bitCount(a) - Integer.bitCount(b);
                    return diff == 0 ? a - b : diff;
                })
                .mapToInt(i -> i)
                .toArray();
    }
}
```

---
