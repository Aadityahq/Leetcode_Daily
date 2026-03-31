# 🧠 Problem Explanation (Simple Language)

You are given:

* `str1` (length = n) → contains `'T'` and `'F'`
* `str2` (length = m)

You need to build a string `word` of length:

```text
n + m - 1
```

---

## 🔑 What do `'T'` and `'F'` mean?

For every index `i` in `str1`:

### ✅ If `str1[i] == 'T'`

The substring starting at `i` must be equal to `str2`

```text
word[i ... i+m-1] == str2
```

---

### ❌ If `str1[i] == 'F'`

The substring starting at `i` must NOT be equal to `str2`

```text
word[i ... i+m-1] != str2
```

---

## 🎯 Goal

👉 Build the **lexicographically smallest** valid string
👉 If impossible → return `""`

---

# 🔍 Key Challenges

### 1. Overlapping constraints

Substrings overlap → characters may get multiple constraints

---

### 2. Two opposite requirements

* `'T'` → force equality
* `'F'` → force inequality

---

### 3. Lexicographically smallest

Among all valid answers → pick smallest (prefer `'a'`)

---

# 💡 Solution Intuition

We solve this in **3 smart phases**:

---

# ⚙️ Step-by-Step Solution

---

## 🔹 Step 1: Create answer array

```java
char[] ans = new char[n + m - 1];
fill with '?'
```

👉 `'?'` means:

> “This position is still free”

---

## 🔹 Step 2: Apply all `'T'` constraints (MOST IMPORTANT)

For every `'T'`:

👉 Force substring = `str2`

```java
ans[i + j] = str2[j]
```

### ❗ Conflict Check

If already filled with different character → return `""`

---

### 🧠 Why first?

Because `'T'` is **strict condition** (cannot be violated)

---

## 🔹 Step 3: Save original state

```java
oldAns = ans.clone()
```

👉 This helps us know:

> which positions were originally free (`'?'`)

---

## 🔹 Step 4: Fill remaining with `'a'`

```java
if '?' → 'a'
```

👉 Why?

* We want **smallest lexicographic string**
* `'a'` is smallest character

---

## 🔹 Step 5: Handle `'F'` constraints

For every `'F'`:

### Check:

```java
if substring == str2
```

---

### ✔ If NOT equal → OK

---

### ❌ If equal → we must break it

---

## 🔹 Step 6: Break `'F'` match smartly

We modify **only safe positions**:

```java
if (oldAns[j] == '?')
```

👉 Meaning:

* This position was NOT forced by `'T'`
* So we can safely change it

---

### Change logic:

```java
ans[j] = 'b'
```

👉 Why `'b'`?

* `'a'` was default
* `'b'` is smallest different char

---

### Why rightmost position?

```java
for (j = right → left)
```

👉 Because:

* Keeps string lexicographically smallest
* Minimal impact

---

## 🔹 Step 7: If cannot fix

If no `'?'` position exists:

👉 Entire substring fixed by `'T'`

👉 Cannot break → return `""`

---

# 🔥 Final Logic Summary

| Step     | Purpose                          |
| -------- | -------------------------------- |
| Apply T  | Enforce strict constraints       |
| Fill 'a' | Make smallest string             |
| Fix F    | Break unwanted matches minimally |

---

# 🧠 Why This Works

### ✔ T constraints are guaranteed

We apply them first

---

### ✔ Lexicographically smallest

We use `'a'` everywhere possible

---

### ✔ F constraints handled safely

Only modify flexible (`'?'`) positions

---

# ⚡ Complexity

```text
Time:  O(n * m)
Space: O(n + m)
```

---

# 🧩 Example

### Input:

```text
str1 = "TFTF"
str2 = "ab"
```

---

### Step 1: After T

```text
a b a b ?
```

---

### Step 2: Fill

```text
a b a b a
```

---

### Step 3: Check F

* index 1 → "ba" ≠ "ab" ✅
* index 3 → "ba" ≠ "ab" ✅

---

### Final Answer:

```text
ababa
```

---

# 🚀 One-Line Interview Answer

👉
**“We first enforce all required matches (T), then fill remaining positions with 'a' for lexicographic minimality, and finally fix forbidden matches (F) by minimally modifying only flexible positions.”**

---
