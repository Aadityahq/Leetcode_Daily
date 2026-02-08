## 🔍 Problem Understanding

You are given a string `s` with only `'a'` and `'b'`.

### What does **balanced** mean?

A string is **balanced** if:

* **No `'b'` appears before an `'a'`**

So the valid form looks like:

```
aaaa...bbbb
```

✔ `'a'` before `'b'` → OK
❌ `'b'` before `'a'` → NOT balanced

---

## 🎯 Goal

Delete **minimum characters** so that the string becomes balanced.

---

## 🧠 Key Insight

Whenever you see a pattern like:

```
... b ... a ...
```

You **must delete something**:

* either delete that `'a'`
* or delete one of the previous `'b'`

We choose the **cheapest option greedily**.

---

## 💡 Strategy Used in the Code

We scan the string **left to right** and keep track of:

| Variable | Meaning                     |
| -------- | --------------------------- |
| `b`      | Number of `'b'` seen so far |
| `res`    | Minimum deletions needed    |

---

## 🚶 Step-by-Step Logic

```java
int res = 0, b = 0;
```

### Traverse each character:

```java
for (int i = 0; i < s.length(); i++) {
    char c = s.charAt(i);
```

---

### Case 1: Current char is `'b'`

```java
if (c == 'b')
    b++;
```

* No problem yet
* `'b'` can stay **if no `'a'` comes later**

---

### Case 2: Current char is `'a'` AND we have previous `'b'`

```java
else if (b > 0) {
    res++;
    b--;
}
```

⚠ This is the **invalid pattern** (`b → a`)

Now we decide:

* Delete this `'a'` → cost `1`
* OR delete one previous `'b'` → cost `1`

💡 Greedy trick:

* We **delete the `'a'`**
* Decrement `b` to simulate removing a conflicting `'b'`

This ensures **minimum deletions**.

---

## 🧪 Example Walkthrough

### Example 1

`s = "aababbab"`

| Char | b | res | Explanation           |
| ---- | - | --- | --------------------- |
| a    | 0 | 0   | OK                    |
| a    | 0 | 0   | OK                    |
| b    | 1 | 0   | count b               |
| a    | 0 | 1   | conflict → delete `a` |
| b    | 1 | 1   | count b               |
| b    | 2 | 1   | count b               |
| a    | 1 | 2   | conflict → delete `a` |
| b    | 2 | 2   | count b               |

✅ **Answer = 2**

---

### Example 2

`s = "bbaaaaabb"`

* First two `'b'` cause conflicts with many `'a'`
* Best solution: **delete the first two `'b'`**
* Total deletions = `2`

---

## ⏱ Time & Space Complexity

| Metric | Value    |
| ------ | -------- |
| Time   | **O(n)** |
| Space  | **O(1)** |

No extra arrays, super efficient 💨

---

## 🧠 Why This Greedy Works

At every `'a'` that violates balance:

* You make the **locally optimal decision**
* Which leads to the **global minimum deletions**

---

## ✅ Final Takeaway

* Count `'b'`
* When `'a'` appears after `'b'`, delete smartly
* One pass, no DP needed


