

Great question Adii 👍 This problem looks complicated at first, but once you understand the pattern, it becomes very logical.

---

# 🔹 1545. Find Kth Bit in Nth Binary String

## 🧠 Understanding the Pattern (Very Important)

We are given:

```
S1 = "0"

Si = Si-1 + "1" + reverse(invert(Si-1))
```

So every new string:
1. Takes previous string
2. Adds `"1"` in the middle
3. Adds reversed + inverted previous string

---

### 📌 Observe the First Few Strings

```
S1 = 0
S2 = 0 1 1
S3 = 011 1 001
S4 = 0111001 1 0110001
```

Length of Sn:

```
Length = 2^n - 1
```

Example:
- S1 → 1
- S2 → 3
- S3 → 7
- S4 → 15

So:

```
len = (1 << n) - 1
mid = len / 2 + 1
```

The **middle character is always '1'**.

---

# 🔥 Key Observations (The Trick)

For Sn:

- Left half = Sn-1
- Middle = '1'
- Right half = reverse(invert(Sn-1))

So:

### Case 1:  
If `k == middle`  
→ Answer is `'1'`

---

### Case 2:  
If `k < middle`  
→ It lies in Sn-1  
→ Just solve for `(n-1, k)`

---

### Case 3:  
If `k > middle`

It is in:

```
reverse(invert(Sn-1))
```

So:

1. Mirror the index:
   ```
   newK = len - k + 1
   ```

2. Recursively find bit in `(n-1, newK)`

3. Invert the result

---

# 💡 Why This Works

Because:

```
Right half = reverse(invert(Sn-1))
```

So:
- Position k in right half
- Corresponds to mirrored position in Sn-1
- Then inverted

This avoids building the entire string (which would be huge).

Time Complexity: **O(n)**  
Space Complexity: **O(n)** (recursive stack)

---

# ✅ Java Solution (Recursive)

```java
class Solution {
    public char findKthBit(int n, int k) {
        if (n == 1) {
            return '0';
        }

        int len = (1 << n) - 1;     // 2^n - 1
        int mid = len / 2 + 1;

        if (k == mid) {
            return '1';
        } 
        else if (k < mid) {
            return findKthBit(n - 1, k);
        } 
        else {
            int newK = len - k + 1;
            char bit = findKthBit(n - 1, newK);
            return bit == '0' ? '1' : '0';
        }
    }
}
```

---

# 🧩 Dry Run Example

### Example 2:
```
n = 4
k = 11
```

Length:
```
2^4 - 1 = 15
mid = 8
```

Since:
```
k = 11 > 8
```

So:

```
newK = 15 - 11 + 1 = 5
```

Now solve:
```
findKthBit(3, 5)
```

Keep reducing recursively until base case.

Final result = `'1'`

---

# 🎯 Why Not Build the Whole String?

Because:

Max n = 20

```
2^20 - 1 = 1,048,575 characters
```

Building full string repeatedly would be slow.

This recursive approach is **mathematically optimal**.

---

# 🏆 Final Understanding

The entire problem is about:

- Recognizing symmetry
- Using mirror index
- Understanding recursive structure

Once you see that Sn is symmetric around the middle, the problem becomes simple recursion.

---

If you want, I can also show you:
- 🔥 Iterative solution
- 🔥 Bit manipulation trick
- 🔥 Mathematical pattern explanation (even deeper)

Just tell me 😄