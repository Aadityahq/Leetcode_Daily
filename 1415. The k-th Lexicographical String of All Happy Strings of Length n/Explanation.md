### 🧩 Problem Understanding

We need to generate **happy strings** of length **n**.

A **happy string** must satisfy:

1. It only contains characters **'a', 'b', 'c'**.
2. **No two adjacent characters are the same**
   Example:

   * ✅ `"abc"`, `"ac"`, `"cab"`
   * ❌ `"aa"`, `"abbc"`

Then:

* Generate **all happy strings of length n**
* **Sort them lexicographically** (dictionary order)
* Return the **k-th string**
* If there are **less than k strings**, return `""`.

---

# 🔎 Key Idea

We generate strings using **Backtracking (DFS)**.

At each step we try:

```
'a'
'b'
'c'
```

But we **skip the character if it is the same as the previous character**.

Example for `n = 3`

Start building:

```
a
 ├─ b
 │   ├─ a → aba
 │   └─ c → abc
 │
 └─ c
     ├─ a → aca
     └─ b → acb

b
 ├─ a → bab
 ├─ c → bca
 ...
```

This naturally produces **lexicographical order** because we always try:

```
a → b → c
```

---

# 📊 Important Observation

Total happy strings:

```
First position: 3 choices
Next positions: 2 choices (cannot repeat previous)
```

So:

```
Total = 3 × 2^(n-1)
```

Example:

```
n = 3
3 × 2² = 12 strings
```

Which matches the example.

---

# ⚙️ Algorithm

1. Use **backtracking** to build strings.
2. Keep adding characters **a, b, c**.
3. Skip if it equals the previous character.
4. When string length becomes **n**, store it.
5. Stop when we already have **k strings**.

---

# 💻 Java Solution

```java
import java.util.*;

class Solution {

    public String getHappyString(int n, int k) {
        List<String> list = new ArrayList<>();
        backtrack(n, "", list);

        if (k > list.size()) return "";
        return list.get(k - 1);
    }

    private void backtrack(int n, String current, List<String> list) {

        if (current.length() == n) {
            list.add(current);
            return;
        }

        char[] chars = {'a', 'b', 'c'};

        for (char ch : chars) {

            if (current.length() > 0 && current.charAt(current.length() - 1) == ch)
                continue;

            backtrack(n, current + ch, list);
        }
    }
}
```

---

# 🧠 Dry Run

Example:

```
n = 3
k = 9
```

Generated list:

```
1  aba
2  abc
3  aca
4  acb
5  bab
6  bac
7  bca
8  bcb
9  cab   ← answer
10 cac
11 cba
12 cbc
```

So return:

```
cab
```

---

# ⏱ Time Complexity

Generating happy strings:

```
3 × 2^(n-1)
```

For `n = 10`

```
3 × 2^9 = 1536
```

Very small.

So complexity:

```
Time  : O(2^n)
Space : O(2^n)
```

Which easily fits constraints.

---

# 🚀 Optimized Idea (Interview Insight)

Instead of generating all strings, we could **directly compute the k-th string** using math (since count is `3×2^(n-1)`).

But for **n ≤ 10**, backtracking is already optimal and simpler.

---

# 🏆 Why Backtracking Works Well

Because:

* We must explore **all valid combinations**
* Each choice depends on the **previous character**
* Perfect use case for **DFS / recursion**

---

