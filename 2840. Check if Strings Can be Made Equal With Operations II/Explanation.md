# 🧩 Problem Understanding (Simple Words)

You are given two strings:

```
s1 and s2 (same length)
```

You can perform this operation:

👉 Pick indices `i < j` such that
👉 `(j - i)` is **even**
👉 Swap `s[i]` and `s[j]`

---

# 🧠 Key Insight (CORE IDEA)

If `(j - i)` is even:

* even index ↔ even index ✅
* odd index ↔ odd index ✅
* even ↔ odd ❌ NOT possible

---

# 🔥 Big Conclusion

👉 The string is divided into **2 independent groups**:

### 1. Even indices group

`0, 2, 4, 6, ...`

### 2. Odd indices group

`1, 3, 5, 7, ...`

---

# 🎯 What operations allow?

Inside each group:
👉 You can swap **any positions freely**

So effectively:

* Even indices → can be rearranged in any order
* Odd indices → can be rearranged in any order

---

# ❗ Important Restriction

👉 You **CANNOT** move a character:

* from even → odd
* from odd → even

---

# ✅ Final Condition

To make `s1 == s2`, we need:

### ✔ Even positions match (as a multiset)

### ✔ Odd positions match (as a multiset)

---

# 🔍 Example

### Input:

```
s1 = "abcdba"
s2 = "cabdab"
```

### Even indices:

* s1 → a, c, b
* s2 → c, b, a ✅ same characters

### Odd indices:

* s1 → b, d, a
* s2 → a, d, b ✅ same characters

👉 So we can rearrange → ✅ TRUE

---

# ❌ Example (Fail Case)

```
s1 = "abe"
s2 = "bea"
```

Even indices:

* s1 → a, e
* s2 → b, a ❌ mismatch

👉 Cannot fix → FALSE

---

# 🚀 Solution Approach

### Step 1:

Create frequency arrays:

* even positions for s1 & s2
* odd positions for s1 & s2

### Step 2:

Compare both:

* even freq must match
* odd freq must match

---

# 💻 Java Code

```java
class Solution {
    public boolean checkStrings(String s1, String s2) {
        int n = s1.length();
        
        int[] even1 = new int[26];
        int[] odd1 = new int[26];
        int[] even2 = new int[26];
        int[] odd2 = new int[26];
        
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                even1[s1.charAt(i) - 'a']++;
                even2[s2.charAt(i) - 'a']++;
            } else {
                odd1[s1.charAt(i) - 'a']++;
                odd2[s2.charAt(i) - 'a']++;
            }
        }
        
        for (int i = 0; i < 26; i++) {
            if (even1[i] != even2[i] || odd1[i] != odd2[i]) {
                return false;
            }
        }
        
        return true;
    }
}
```

---

# ⏱️ Complexity

* Time → **O(n)**
* Space → **O(1)** (fixed 26 letters)

---

# 🧠 Interview One-Liner (VERY IMPORTANT)

👉
**"Since swaps are allowed only between indices with even distance, even and odd positions form independent groups. So we just compare frequency of characters in both groups."**

---
