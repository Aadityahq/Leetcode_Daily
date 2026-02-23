## 🔹 Problem Understanding (LeetCode 1461)

You are given:

* A **binary string** `s` (contains only `'0'` and `'1'`)
* An integer `k`

You must check whether **all possible binary codes of length `k`** exist as substrings of `s`.

---

### 🔸 What does “all binary codes of length k” mean?

For a given `k`, total possible binary strings =

[
2^k
]

Example:

If `k = 2`, possible codes are:

```
00
01
10
11
```

Total = (2^2 = 4)

So we must verify that **all 4 appear inside `s`**.

---

## 🔹 Key Idea

Instead of generating all possible binary strings and checking them one by one ❌ (inefficient),

We do this ✅:

1. Slide a window of size `k` over string `s`
2. Store every substring of length `k` in a `HashSet`
3. If size of set == `2^k`, return `true`
4. Otherwise return `false`

---

## 🔹 Why This Works

* A string of length `n` has at most `n - k + 1` substrings of length `k`
* If we found `2^k` **unique substrings**, that means we found every possible binary code
* Since `k ≤ 20`, `2^k` max is about **1 million**, which is manageable

---

## 🔹 Important Optimization

Before even starting:

If
[
s.length() < 2^k + k - 1
]

It is **impossible** to contain all codes.

But we don’t even need that check explicitly — just comparing set size is enough.

---

# ✅ Java Solution

```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public boolean hasAllCodes(String s, int k) {
        
        // Total number of binary codes of length k
        int totalCodes = 1 << k;   // 2^k
        
        Set<String> seen = new HashSet<>();
        
        // Slide window of size k
        for (int i = 0; i <= s.length() - k; i++) {
            String substring = s.substring(i, i + k);
            seen.add(substring);
            
            // If we already found all possible codes
            if (seen.size() == totalCodes) {
                return true;
            }
        }
        
        return false;
    }
}
```

---

# 🔹 Step-by-Step Example

### Example:

```
s = "00110110"
k = 2
```

Substrings of length 2:

| Index | Substring |
| ----- | --------- |
| 0     | 00        |
| 1     | 01        |
| 2     | 11        |
| 3     | 10        |
| 4     | 01        |
| 5     | 11        |
| 6     | 10        |

Unique values in set:

```
00, 01, 10, 11
```

Size = 4
2^2 = 4

✅ Return `true`

---

# 🔹 Time Complexity

* We generate at most `n` substrings
* Each substring length = `k` (max 20)

So:

```
Time Complexity:  O(n * k)
Space Complexity: O(2^k)
```

Since `k ≤ 20`, this is efficient.

---

# 🔥 Even More Optimized Version (Bit Manipulation)

We can avoid creating substring objects and use rolling hash (bitmask) for faster solution.

If you want, I can also explain that optimized approach for interviews 🚀
