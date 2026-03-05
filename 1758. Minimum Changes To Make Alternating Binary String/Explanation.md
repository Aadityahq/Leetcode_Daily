## 🧩 Problem Understanding

You are given a binary string `s` containing only **'0'** and **'1'**.

An **alternating string** means:

* No two adjacent characters are the same.

Valid alternating patterns are only **two possible forms**:

1. Starting with **0** → `"010101..."`
2. Starting with **1** → `"101010..."`

Example:

* `"0101"` ✅ alternating
* `"1010"` ✅ alternating
* `"0100"` ❌ because `"00"` appears together

In **one operation**, you can flip a character:

* `'0' → '1'`
* `'1' → '0'`

Goal:
👉 Find the **minimum number of flips** needed to convert `s` into an alternating string.

---

# 🔎 Key Idea

Since only **two valid alternating patterns** exist:

1️⃣ Pattern A: `"010101..."`
2️⃣ Pattern B: `"101010..."`

We calculate:

* flips required to convert `s` → Pattern A
* flips required to convert `s` → Pattern B

Then take:

```
min(flipsA, flipsB)
```

---

# 🧠 Why This Works

Because:

* Every alternating string must match **one of these two patterns**.
* Any valid solution must be one of them.
* Checking both guarantees the **minimum changes**.

Time Complexity:

```
O(n)
```

Space Complexity:

```
O(1)
```

---

# 🪜 Step-by-Step Example

### Example

```
s = "0100"
```

### Pattern A → `"0101"`

| Index | s[i] | Expected | Flip? |
| ----- | ---- | -------- | ----- |
| 0     | 0    | 0        | No    |
| 1     | 1    | 1        | No    |
| 2     | 0    | 0        | No    |
| 3     | 0    | 1        | Yes   |

Flips = **1**

---

### Pattern B → `"1010"`

| Index | s[i] | Expected | Flip? |
| ----- | ---- | -------- | ----- |
| 0     | 0    | 1        | Yes   |
| 1     | 1    | 0        | Yes   |
| 2     | 0    | 1        | Yes   |
| 3     | 0    | 0        | No    |

Flips = **3**

---

Minimum:

```
min(1,3) = 1
```

Answer = **1**

---

# 💻 Java Solution

```java
class Solution {
    public int minOperations(String s) {
        int flipsStart0 = 0; // pattern 010101
        int flipsStart1 = 0; // pattern 101010
        
        for (int i = 0; i < s.length(); i++) {
            
            char expected0 = (i % 2 == 0) ? '0' : '1';
            char expected1 = (i % 2 == 0) ? '1' : '0';
            
            if (s.charAt(i) != expected0) {
                flipsStart0++;
            }
            
            if (s.charAt(i) != expected1) {
                flipsStart1++;
            }
        }
        
        return Math.min(flipsStart0, flipsStart1);
    }
}
```

---

# ⚙️ Code Explanation

### 1️⃣ Two Counters

```
flipsStart0
flipsStart1
```

They represent:

* changes needed for `"010101..."` pattern
* changes needed for `"101010..."` pattern

---

### 2️⃣ Loop through string

```
for (int i = 0; i < s.length(); i++)
```

Check every position.

---

### 3️⃣ Expected characters

If pattern starts with `0`:

```
even index → '0'
odd index → '1'
```

If pattern starts with `1`:

```
even index → '1'
odd index → '0'
```

That is why we use:

```
i % 2
```

---

### 4️⃣ Compare with original string

```
if (s.charAt(i) != expected)
```

If different → we must flip → increment counter.

---

### 5️⃣ Return minimum

```
Math.min(flipsStart0, flipsStart1)
```

Because we want **minimum operations**.

---

# 📊 Complexity Analysis

| Complexity       | Value    |
| ---------------- | -------- |
| Time Complexity  | **O(n)** |
| Space Complexity | **O(1)** |

We scan the string **only once**.

---

# 🎯 Intuition in One Line

👉 A valid alternating string can **only be `0101...` or `1010...`**, so count flips for both and choose the minimum.

---

I