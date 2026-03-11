# 1009. Complement of Base 10 Integer

## 1️⃣ Problem Understanding

You are given an integer **n**.
You must return the **complement** of its **binary representation**.

**Complement rule**

* Change every **0 → 1**
* Change every **1 → 0**

But this should only be done for the **significant bits** of the number.

---

### Example

#### Example 1

```
n = 5
```

Binary of 5:

```
101
```

Flip bits:

```
010
```

Convert back to decimal:

```
010 → 2
```

So output = **2**

---

#### Example 2

```
n = 7
```

Binary:

```
111
```

Flip:

```
000
```

Decimal:

```
0
```

Output = **0**

---

#### Example 3

```
n = 10
```

Binary:

```
1010
```

Flip:

```
0101
```

Decimal:

```
5
```

Output = **5**

---

# 2️⃣ Key Idea

We should **only flip bits up to the highest bit of n**.

Example:

```
n = 5 → 101
```

We should not flip like:

```
00000000000000000101
```

Instead flip only:

```
101
```

---

# 3️⃣ Efficient Approach (Bit Manipulation)

### Idea

1. Find a **mask** that contains only **1s** for the length of the binary number.
2. XOR the number with the mask.

Because:

```
1 XOR 1 = 0
0 XOR 1 = 1
```

So XOR automatically flips bits.

---

### Example

```
n = 5
binary = 101
```

Create mask:

```
111
```

Now:

```
101
XOR
111
=
010
```

Result = **2**

---

# 4️⃣ Java Solution

```java
class Solution {
    public int bitwiseComplement(int n) {
        
        if (n == 0) return 1;

        int mask = 0;
        int temp = n;

        // create mask with all 1s of same length
        while (temp > 0) {
            mask = (mask << 1) | 1;
            temp >>= 1;
        }

        return n ^ mask;
    }
}
```

---

# 5️⃣ Step-by-Step Execution

Example:

```
n = 10
```

Binary:

```
1010
```

### Step 1: Build mask

Loop builds mask:

```
1
11
111
1111
```

Mask =

```
1111
```

---

### Step 2: XOR

```
 1010
^1111
-----
 0101
```

Decimal:

```
5
```

---

# 6️⃣ Edge Case

```
n = 0
```

Binary:

```
0
```

Complement:

```
1
```

So answer = **1**

---

# 7️⃣ Time & Space Complexity

**Time Complexity**

```
O(log n)
```

Because we process bits of the number.

**Space Complexity**

```
O(1)
```

Only variables used.

---

# 8️⃣ Even Shorter Java Solution (Best)

```java
class Solution {
    public int bitwiseComplement(int n) {
        if (n == 0) return 1;
        
        int mask = Integer.highestOneBit(n) << 1;
        return (mask - 1) ^ n;
    }
}
```

---

✅ **Key Concept Learned**

* Binary representation
* Bit masking
* XOR for flipping bits
* Bit manipulation tricks

---
