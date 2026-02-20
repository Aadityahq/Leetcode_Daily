## 🔹 761. Special Binary String — Detailed Explanation (How & Why)

---

### ✅ What is a Special Binary String?

A binary string is **special** if:

1. The number of `'1'`s equals the number of `'0'`s.
2. Every prefix has **at least as many `'1'`s as `'0'`s**.

This is exactly like **valid parentheses**:

* `'1'` → `'('`
* `'0'` → `')'`

So a special string behaves like a **valid parentheses string**.

Example:

```
"11011000"
```

Check prefix condition:

```
1      → 1 one, 0 zero  ✅
11     → 2 one, 0 zero  ✅
110    → 2 one, 1 zero  ✅
1101   → 3 one, 1 zero  ✅
11011  → 4 one, 1 zero  ✅
110110 → 4 one, 2 zero  ✅
1101100→ 4 one, 3 zero  ✅
11011000→4 one, 4 zero  ✅
```

✔ Valid special string.

---

## 🔹 What Are We Allowed To Do?

We can:

* Choose **two consecutive special substrings**
* Swap them
* Repeat any number of times

Goal → **Make the lexicographically largest string**

Lexicographically larger means:

* More `'1'` earlier in the string
* `'1' > '0'`

So we want bigger chunks first.

---

# 🔥 Core Insight

A special string is made of **multiple special substrings stuck together**.

Example:

```
11011000
```

Break it into balanced parts:

```
11011000
   ↑
When count becomes 0, we found a chunk.
```

Let’s track balance:

| Index | Char | Count |
| ----- | ---- | ----- |
| 0     | 1    | 1     |
| 1     | 1    | 2     |
| 2     | 0    | 1     |
| 3     | 1    | 2     |
| 4     | 1    | 3     |
| 5     | 0    | 2     |
| 6     | 0    | 1     |
| 7     | 0    | 0 ✅   |

Whole string is one big balanced chunk.

But inside it:

```
1 [101100] 0
```

We recursively process inside part:

```
101100
```

---

# 🔹 Why Recursion Works

Because:

* Each special substring is structured like:

  ```
  1 + (another special string) + 0
  ```

* So we:

  1. Remove outer `1` and `0`
  2. Recursively maximize inside
  3. Wrap it back with `1` and `0`

---

# 🔹 Why Sorting in Reverse Order?

After splitting into multiple special chunks:

Example:

```
1100 10
```

These are two special substrings.

We can swap them:

```
10 1100
```

Which is larger?

Compare:

```
110010
101100
```

First is bigger because:

* First character same
* At index 1 → 1 > 0

So we sort substrings in descending lexicographical order.

That guarantees largest possible arrangement.

---

# 🔹 Step-by-Step Code Logic

```java
int count = 0, i = 0;
List<String> res = new ArrayList<>();
```

* `count` tracks balance
* `i` marks start of substring
* `res` stores special chunks

---

### 🔹 Step 1: Find Balanced Chunks

```java
for (int j = 0; j < s.length(); j++) {
    if (s.charAt(j) == '1') count++;
    else count--;
```

When:

```java
if (count == 0)
```

We found a special substring from `i` to `j`.

---

### 🔹 Step 2: Recursively Process Inside

```java
res.add('1' + makeLargestSpecial(s.substring(i + 1, j)) + '0');
```

Why `i + 1` and `j`?

Because:

* Outer characters are `1` and `0`
* Inside part may contain more special chunks

---

### 🔹 Step 3: Sort in Descending Order

```java
Collections.sort(res, Collections.reverseOrder());
```

This ensures:

* Bigger substrings come first
* Final result is lexicographically maximum

---

### 🔹 Step 4: Join All

```java
return String.join("", res);
```

---

# 🔥 Example Walkthrough

### Input:

```
"11011000"
```

### Step 1: Structure

```
1 101100 0
```

Inside:

```
101100
```

Split inside:

```
10 1100
```

Sort descending:

```
1100 10
```

Rebuild:

```
1 110010 0
```

Final:

```
11100100
```

✔ Output: `"11100100"`

---

# 🔹 Why This Always Works

Because:

1. Every special string can be uniquely split into primitive special blocks.
2. We can swap consecutive blocks freely.
3. To maximize lexicographically:

   * Make largest block appear first.
4. Recursively apply same logic to nested blocks.

It’s exactly like:

> “Sort all valid parentheses substrings in descending order at every level.”

---

# 🔹 Time Complexity

* Each level processes string once → O(n)
* Sorting substrings → O(k log k)
* Recursion depth ≤ n

Overall:

```
O(n^2)
```

Since n ≤ 50 → completely safe.

---

