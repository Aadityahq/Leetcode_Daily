# 1888. Minimum Number of Flips to Make the Binary String Alternating

## 1. Understanding the Problem

You are given a **binary string `s`**.

You can perform two operations:

### Operation 1 — Rotation

Remove the first character and append it to the end.

Example

```
s = "111000"

After 1 rotation:
"110001"

After 2 rotations:
"100011"
```

So we can **rotate the string any number of times**.

---

### Operation 2 — Flip

Change a character.

```
0 -> 1
1 -> 0
```

Goal:
Make the string **alternating** with **minimum flips**.

Valid alternating strings look like:

```
010101...
101010...
```

There are **only two possible alternating patterns**.

---

# 2. Key Observations

For length `n`, only **two patterns** exist:

### Pattern A

Starts with `0`

```
010101010...
```

### Pattern B

Starts with `1`

```
101010101...
```

Example for n = 6:

```
A = 010101
B = 101010
```

We want the **minimum flips to convert `s` into either A or B**.

---

# 3. Why Rotation Matters

Rotation changes alignment.

Example:

```
s = "111000"
```

Possible rotations:

```
111000
110001
100011
000111
001110
011100
```

Each rotation might require **different flips**.

So we must check **all rotations**.

But checking every rotation directly would cost:

```
O(n²)
```

Which is **too slow for n = 10^5**.

---

# 4. Smart Trick (Important)

Instead of rotating, we **duplicate the string**.

```
s = "111000"

ss = "111000111000"
```

Now **every rotation becomes a substring of length n**.

Example:

```
rotation1 = ss[0..5]
rotation2 = ss[1..6]
rotation3 = ss[2..7]
```

So we can use a **sliding window of size n**.

---

# 5. Build Alternating Patterns

For the duplicated string length `2n`:

Pattern A:

```
010101010101...
```

Pattern B:

```
101010101010...
```

---

# 6. Sliding Window Idea

We move a window of size `n` across `ss`.

For each window we count:

```
mismatch with pattern A
mismatch with pattern B
```

Minimum of both is the flips required.

We keep the **global minimum**.

Time Complexity becomes:

```
O(n)
```

---

# 7. Algorithm Steps

1. Duplicate string → `ss = s + s`
2. Build patterns for length `2n`
3. Use sliding window of size `n`
4. Maintain mismatch counts
5. Update minimum flips

---

# 8. Java Solution

```java
class Solution {
    public int minFlips(String s) {
        int n = s.length();
        String ss = s + s;

        int res = Integer.MAX_VALUE;

        int diff1 = 0; // mismatch with pattern 0101
        int diff2 = 0; // mismatch with pattern 1010

        for (int i = 0; i < ss.length(); i++) {
            
            char expected1 = (i % 2 == 0) ? '0' : '1';
            char expected2 = (i % 2 == 0) ? '1' : '0';

            if (ss.charAt(i) != expected1) diff1++;
            if (ss.charAt(i) != expected2) diff2++;

            if (i >= n) {
                int j = i - n;

                char prevExpected1 = (j % 2 == 0) ? '0' : '1';
                char prevExpected2 = (j % 2 == 0) ? '1' : '0';

                if (ss.charAt(j) != prevExpected1) diff1--;
                if (ss.charAt(j) != prevExpected2) diff2--;
            }

            if (i >= n - 1) {
                res = Math.min(res, Math.min(diff1, diff2));
            }
        }

        return res;
    }
}
```

---

# 9. Example Walkthrough

Input:

```
s = "111000"
n = 6
```

Duplicate:

```
ss = "111000111000"
```

Sliding windows:

```
111000
110001
100011
000111
001110
011100
```

For each window we compute flips.

Minimum flips found = **2**.

---

# 10. Complexity Analysis

### Time Complexity

```
O(n)
```

We traverse `2n` once.

---

### Space Complexity

```
O(n)
```

For duplicated string.

---

# 11. Why This Problem is Medium

Because it combines **three concepts**:

1. **Rotation trick (`s + s`)**
2. **Alternating pattern matching**
3. **Sliding window**

Recognizing the **rotation → duplicate string trick** is the key insight.

---

# 12. Interview Insight (Very Important)

This pattern appears in many problems:

```
Circular array / rotation problems
```

Common trick:

```
circular → duplicate array/string
```

Then apply **sliding window**.

---

✅ **Core idea**

```
rotation + alternating pattern
→ duplicate string
→ sliding window
→ count mismatches
```

---

