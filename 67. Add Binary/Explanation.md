## 🔹 67. Add Binary — Explanation & Java Solution

### 📌 Problem Understanding

You are given **two binary strings** `a` and `b`.

* Each string contains only `'0'` and `'1'`
* You must return their **sum as a binary string**
* You **cannot convert directly to integer** (because length can be up to 10⁴ → too large for normal integer types)

---

### 🔎 What Is Binary Addition?

Binary addition works just like decimal addition, but with base 2.

| A | B | Carry In | Sum | Carry Out |
| - | - | -------- | --- | --------- |
| 0 | 0 | 0        | 0   | 0         |
| 0 | 1 | 0        | 1   | 0         |
| 1 | 1 | 0        | 0   | 1         |
| 1 | 1 | 1        | 1   | 1         |

💡 Important:

* `1 + 1 = 0` (carry 1)
* `1 + 1 + 1 = 1` (carry 1)

---

## 🧠 How To Think About It

We simulate **manual addition** from right to left:

1. Start from the last character of both strings
2. Add digits + carry
3. Append result % 2
4. Update carry = sum / 2
5. Continue until both strings and carry are finished

---

## 🧩 Why We Use StringBuilder?

* Strings are immutable in Java
* We build result in reverse
* Then reverse at end

Time Complexity: **O(n)**
Space Complexity: **O(n)**

---

## ✅ Java Solution

```java
class Solution {
    public String addBinary(String a, String b) {
        StringBuilder result = new StringBuilder();
        
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        
        while (i >= 0 || j >= 0 || carry != 0) {
            int sum = carry;
            
            if (i >= 0) {
                sum += a.charAt(i) - '0';
                i--;
            }
            
            if (j >= 0) {
                sum += b.charAt(j) - '0';
                j--;
            }
            
            result.append(sum % 2);   // remainder
            carry = sum / 2;          // update carry
        }
        
        return result.reverse().toString();
    }
}
```

---

## 🔍 Step-by-Step Example

### Example:

```
a = "1010"
b = "1011"
```

Process from right to left:

```
  1010
+ 1011
-------
```

Step 1:

```
0 + 1 = 1
```

Step 2:

```
1 + 1 = 0 carry 1
```

Step 3:

```
0 + 0 + carry(1) = 1
```

Step 4:

```
1 + 1 = 0 carry 1
```

Final carry = 1

Result (reverse built) → `10101`

---

## 🎯 Why This Works

* We process digits like real addition
* No integer overflow
* Works for very large binary strings
* Single pass → efficient

---
