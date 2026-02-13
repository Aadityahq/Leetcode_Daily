# 🔎 Problem Statement

A substring is **balanced** if:

> All distinct characters inside it appear the **same number of times**.

Since the string only contains:

```
'a', 'b', 'c'
```

Balanced substring can be of:

1. Only 1 character (e.g., `"aaa"`)
2. Exactly 2 characters with equal count (e.g., `"abab"`)
3. All 3 characters with equal count (e.g., `"abcabc"`)

Your solution smartly handles **all 3 cases separately** 👏

---

# 🧠 Overall Structure of Your Code

```java
ans = max(
    countOne(),
    countTwo(),
    countThree()
)
```

You divide the problem into:

| Case             | Method         |
| ---------------- | -------------- |
| 1 distinct char  | `countOne()`   |
| 2 distinct chars | `countTwo()`   |
| 3 distinct chars | `countThree()` |

This guarantees that **all possible balanced substrings are checked**.

---

# ✅ 1️⃣ `countOne()` — Single Character Case

### 💡 Idea:

If substring contains only 1 distinct character, it is always balanced.

Example:

```
"aaaa" → balanced
```

### 🔎 What this function does:

It finds the **longest continuous block of same character**.

```java
private int countOne(char[] arr)
```

### Logic:

* Keep track of current character
* Count how long it repeats continuously
* Reset when character changes

### Example:

For `"aaabbccaaa"`
Longest block = `"aaa"` → length = 3

### ⏱ Time Complexity:

O(n)

---

# ✅ 2️⃣ `countTwo()` — Two Character Case

Now we handle substrings containing exactly **2 distinct characters**.

Example:

```
"abab" → a=2, b=2 ✔
```

---

## 💡 Key Idea

If:

```
count(n1) == count(n2)
```

Then:

```
count(n1) - count(n2) = 0
```

We treat:

* n1 → +1
* n2 → -1

So balanced substring means:

```
prefix sum becomes same again
```

This is the classic:

> 🔥 "Longest subarray with sum = 0"

---

## 🔎 How Your Code Works

```java
Map<Integer, Integer> map = new HashMap<>();
map.put(0, -1);
```

We store:

```
sum → first index seen
```

### While traversing:

```java
if (arr[i] == n1) sum++;
else if (arr[i] == n2) sum--;
```

If:

```
sum seen before
```

That means:

```
between those indices → equal number of n1 and n2
```

So:

```
length = i - previousIndex
```

---

## 🚨 Important Part

```java
else {
    map = new HashMap<>();
    sum = 0;
    map.put(0, i);
    continue;
}
```

If we see a third character:

* We reset everything
* Because substring must contain ONLY n1 and n2

Very important logic 👍

---

## ⏱ Time Complexity

Each pair runs in O(n)

You run it 3 times:

* (a,b)
* (b,c)
* (c,a)

Total still O(n)

---

# ✅ 3️⃣ `countThree()` — Three Character Case

Now we handle substrings containing:

```
a, b, c
```

Balanced means:

```
a = b = c
```

---

## 💡 Mathematical Trick

Instead of checking:

```
a == b == c
```

We check:

```
a - b = 0
a - c = 0
```

So we track:

```
diff1 = a - b
diff2 = a - c
```

If at two indices these differences are same:

Then the substring between them has:

```
equal increment in a, b, c
```

Meaning balanced.

---

## 🔎 Code Logic

```java
map.put("0#0", -1);
```

We store:

```
(diff1, diff2) → first index
```

At each step:

```java
int diff1 = a - b;
int diff2 = a - c;
String key = diff1 + "#" + diff2;
```

If:

```
key seen before
```

Then:

```
substring between those indices is balanced
```

---

## 🧠 Why This Works

If two prefix states have same:

```
(a-b, a-c)
```

That means:

Between them:

```
a increment == b increment == c increment
```

So substring is balanced.

---

# 📊 Overall Time & Space Complexity

| Complexity | Value |
| ---------- | ----- |
| Time       | O(n)  |
| Space      | O(n)  |

Because:

* Each function runs linear time
* HashMap stores at most n entries

---

# 🎯 Why Your Approach Is Correct

You covered:

✔ All 1-character substrings
✔ All 2-character balanced substrings
✔ All 3-character balanced substrings

No case is missed.

That’s why this solution is fully correct.

---

# 🚀 Final Summary

Your strategy:

```
Break complex condition
→ into smaller fixed character cases
→ apply prefix + hashmap trick
→ take maximum length
```

This is a **very interview-level clean solution** 👏

---

