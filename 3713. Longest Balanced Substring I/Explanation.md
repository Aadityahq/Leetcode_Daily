Good 👍 this is a clean brute-force solution.
Let’s break it down properly — **problem → idea → how your code works → why it works → complexity**.

---

# ✅ 1. Problem Understanding

You are given a string `s`.

A substring is **balanced** if:

> All distinct characters in that substring appear the **same number of times**.

---

### 🔎 Example

`s = "abbac"`

Substrings:

* `"a"` → a=1 → ✅ balanced
* `"ab"` → a=1, b=1 → ✅ balanced
* `"abb"` → a=1, b=2 → ❌ not balanced
* `"abba"` → a=2, b=2 → ✅ balanced

Longest balanced substring = `"abba"` → length = **4**

---

# 💡 2. Core Idea

Since:

```
1 <= s.length <= 1000
```

We can try **all substrings**.

Total substrings ≈ `n²`

For each substring:

* Count frequency of characters
* Check if all frequencies are equal

If yes → update answer.

This is exactly what your code does.

---

# 🧠 3. How Your Code Works (Step-by-Step)

```java
for (int i = 0; i < sz; i++) {
```

👉 Fix the starting point of substring.

---

```java
mp.clear();
```

👉 Clear frequency map for new starting index.

---

```java
for (int j = i; j < sz; j++) {
```

👉 Expand substring one character at a time.

So substring is:

```
s[i...j]
```

---

```java
char c = s.charAt(j);
mp.put(c, mp.getOrDefault(c, 0) + 1);
```

👉 Update frequency of current character.

---

```java
int cnt = mp.get(c);
```

👉 Take frequency of current character as reference.

---

### 🔍 Now the Important Check

```java
for (int count : mp.values()) {
    if (count != cnt) {
        flag = false;
        break;
    }
}
```

👉 Check:

Are ALL character frequencies equal?

If yes → balanced substring.

---

```java
if (flag) {
    ans = Math.max(ans, j - i + 1);
}
```

👉 Update maximum length.

---

# 🔎 Example Walkthrough

Let’s trace `"abbac"`.

Start `i = 0`

### j = 0 → "a"

```
a=1
```

All counts same → YES
ans = 1

---

### j = 1 → "ab"

```
a=1, b=1
```

All equal → YES
ans = 2

---

### j = 2 → "abb"

```
a=1, b=2
```

Not equal → NO

---

### j = 3 → "abba"

```
a=2, b=2
```

Equal → YES
ans = 4

---

### j = 4 → "abbac"

```
a=2, b=2, c=1
```

Not equal → NO

Final answer = **4**

---

# 🧠 Why This Approach Works

We are checking **every possible substring**.

For each substring:

* We correctly maintain frequency using HashMap.
* We verify the balanced condition exactly as problem defines.

Since constraints are small (≤ 1000), this brute force is acceptable.

---

# ⏱ Complexity Analysis

### Time Complexity

Outer loop → `O(n)`
Inner loop → `O(n)`
Frequency check → at most 26 characters

So overall:

```
O(n² × 26)
≈ O(n²)
```

Worst case:

```
1000 × 1000 = 1,000,000 operations
```

Perfectly fine.

---

### Space Complexity

HashMap stores at most 26 characters.

```
O(26) ≈ O(1)
```

---

# 🔥 Important Insight

Your solution is:

✔ Correct
✔ Easy to understand
✔ Interview safe
✔ Works within constraints

But slightly slower than the optimized version because:

* You iterate over `mp.values()` every time

Optimized solution avoids that using:

```
length == distinct * maxFrequency
```

---
