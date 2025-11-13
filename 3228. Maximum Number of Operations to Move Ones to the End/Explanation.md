**LeetCode 3228. Maximum Number of Operations to Move Ones to the End**

---

## 🧩 Problem Recap

You are given a binary string `s`.
You can repeatedly choose an index `i` such that:

* `s[i] == '1'`
* `s[i + 1] == '0'`

and **move `s[i]` to the right** until it reaches:

* the **end of the string**, or
* the **position just before another `'1'`**.

You need to return the **maximum number of operations** you can perform.

---

## 🧠 Intuitive Understanding

The tricky part is that **a single operation moves a `'1'` across multiple `'0'`s** —
it’s *not just a single swap*.

That means we **cannot** just count all “inversions” (where `'1'` appears before `'0'`) — that would **overcount** operations.

---

## 🎯 Key Observation

Let’s look at how operations happen:

### Example:

`s = "1001101"`

We perform the operations step by step:

| Step | Operation      | Resulting String | Explanation                                                     |
| ---- | -------------- | ---------------- | --------------------------------------------------------------- |
| 1    | Choose `i = 0` | `0011101`        | `'1'` at index 0 moves past 2 zeros until it hits another `'1'` |
| 2    | Choose `i = 4` | `0011011`        | `'1'` at index 4 moves past 1 zero                              |
| 3    | Choose `i = 3` | `0010111`        | `'1'` moves one more step                                       |
| 4    | Choose `i = 2` | `0001111`        | Last `'1'` moves, and we’re done                                |

✅ Total operations = **4**

---

## 🔍 What’s really happening?

Notice something:

Every time we do an operation, a `'1'` “lands” just before another `'1'` or at the end.

That means:

* Every `'1'` can potentially contribute to multiple operations **only when there are “boundaries” of zeros** before another `'1'`.

So, instead of simulating the process, we can **count these opportunities** directly.

---

## 💡 Core Idea

👉 Let’s traverse the string **from left to right**:

We’ll keep two variables:

* `ones` → how many `'1'`s we’ve seen so far.
* `ans` → total operations we can perform.

Now, for each character:

* If it’s `'1'`, we increase `ones` by 1.
* If it’s `'0'`, we check:

  * if it’s the **last character**, OR
  * the **next character is `'1'`**,
    → then this `'0'` is a **“terminal zero”** (a zero block ending point).

Every `'1'` before this “terminal zero” can perform one operation involving this zero block.

So, we add all previous `'1'`s to `ans`.

---

## 🧮 Example Walkthrough

Let’s dry-run `s = "1001101"`

| Index | Char | ones | Terminal zero? | Add to ans | ans |
| :---: | :--: | :--: | :------------: | :--------: | :-: |
|   0   |   1  |   1  |        —       |      —     |  0  |
|   1   |   0  |   1  |  ❌ (next is 0) |      —     |  0  |
|   2   |   0  |   1  |  ✅ (next is 1) |     +1     |  1  |
|   3   |   1  |   2  |        —       |      —     |  1  |
|   4   |   1  |   3  |        —       |      —     |  1  |
|   5   |   0  |   3  |  ✅ (next is 1) |     +3     |  4  |
|   6   |   1  |   4  |        —       |      —     |  4  |

✅ Final `ans = 4` → matches the expected output.

---

## ✅ Final Java Code

```java
class Solution {
    public int maxOperations(String s) {
        int ans = 0;
        int ones = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (c == '1') {
                ones++;
            } else {
                // Check if this '0' is a "terminal zero"
                if (i == n - 1 || s.charAt(i + 1) == '1') {
                    ans += ones;
                }
            }
        }

        return ans;
    }
}
```

---

## 🧾 Step-by-Step Explanation (How & Why)

| Step | Explanation                                                                                                                                            |
| ---- | ------------------------------------------------------------------------------------------------------------------------------------------------------ |
| 1️⃣  | Loop through the string from left to right                                                                                                             |
| 2️⃣  | Keep counting `'1'`s — these are the ones that can move                                                                                                |
| 3️⃣  | When we find a `'0'` that ends a zero block (either before a `'1'` or at the end), each previous `'1'` can perform an operation reaching this boundary |
| 4️⃣  | Add the count of `'1'`s to `ans` for every such zero block                                                                                             |
| 5️⃣  | Return `ans` as the total maximum operations                                                                                                           |

---

## 🕒 Complexity

* **Time:** O(n) — single pass
* **Space:** O(1) — uses only two counters

---

## 🧪 Example Tests

| Input       | Output | Explanation                |
| :---------- | :----- | :------------------------- |
| `"1001101"` | `4`    | Matches step-by-step moves |
| `"00111"`   | `0`    | No `'1'` followed by `'0'` |
| `"010010"`  | `3`    | Three valid operations     |

---

