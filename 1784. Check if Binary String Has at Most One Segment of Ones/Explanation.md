### 1784. Check if Binary String Has at Most One Segment of Ones

**Goal:**
Check if the binary string contains **only one continuous block of `1`s**.

Valid examples:

* `"111"` ✅
* `"11000"` ✅

Invalid examples:

* `"101"` ❌ (two segments of `1`)
* `"1001"` ❌ (two segments of `1`)

Because the string **does not have leading zeros**, it always starts with `'1'`.

---

# Key Idea

If there is **more than one segment of `1`s**, the pattern will look like:

```
...1 0 1...
```

That means after a `0`, we see another `1`.

So we simply check:

> **If `"01"` exists in the string → there are two segments of `1`s → return false**

---

# Java Solution (Simple Approach)

```java
class Solution {
    public boolean checkOnesSegment(String s) {
        return !s.contains("01");
    }
}
```

---

# Explanation

### Step 1: Understand the pattern

For **one segment of ones**, the string must look like:

```
11111100000
```

Once `0` starts, **`1` should never appear again**.

---

### Step 2: Detect the invalid pattern

If we find:

```
01
```

This means:

* first segment of `1`s ended
* another `1` started later

Example:

```
1001
  ↑
  01 found → two segments of ones
```

So we return **false**.

---

### Step 3: Code logic

```
if string contains "01"
    return false
else
    return true
```

Using Java built-in method:

```
s.contains("01")
```

---

# Example Walkthrough

### Example 1

Input

```
s = "1001"
```

Check:

```
1001
 ↑
 01 found
```

Another `1` appears after `0`.

Output

```
false
```

---

### Example 2

Input

```
s = "110"
```

Check pattern

```
110
```

No `"01"` appears.

All `1`s are together.

Output

```
true
```

---

# Alternative Approach (Manual Check)

```java
class Solution {
    public boolean checkOnesSegment(String s) {
        boolean zeroSeen = false;

        for (char c : s.toCharArray()) {
            if (c == '0') {
                zeroSeen = true;
            } 
            else if (zeroSeen) {
                return false;
            }
        }

        return true;
    }
}
```

### Logic

1. Traverse the string.
2. Once we see `0`, mark `zeroSeen = true`.
3. If we later see `1` again → there are **two segments** → return false.

---

# Complexity

| Metric           | Value    |
| ---------------- | -------- |
| Time Complexity  | **O(n)** |
| Space Complexity | **O(1)** |

---

