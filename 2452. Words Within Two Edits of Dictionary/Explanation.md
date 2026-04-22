# 🧠 Problem Understanding

You are given:

* `queries[]` → words you need to check
* `dictionary[]` → valid words

👉 You can change **at most 2 characters** in a query word.

👉 A query is **valid** if it can match *any* dictionary word with ≤ 2 edits.

---

# 🔍 Key Insight (Very Important)

Since:

* All words have the **same length**
* Only **replacement** is allowed (not insert/delete)

👉 The problem becomes:

> Count how many positions are different between two words.

This is called **Hamming Distance**.

---

# 🎯 Rule

If

```
difference count ≤ 2
```

✅ Then it's a valid match

---

# 🚀 Approach

For every word in `queries`:

1. Compare it with every word in `dictionary`
2. Count character differences
3. If any dictionary word has ≤ 2 differences → add query to answer

---

# ⏱ Time Complexity

```
O(Q * D * N)
```

Where:

* Q = queries.length
* D = dictionary.length
* N = length of each word

👉 Max = 100 * 100 * 100 = **1,000,000** → totally fine

---

# ✅ Java Solution

```java
import java.util.*;

class Solution {
    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        List<String> result = new ArrayList<>();

        for (String query : queries) {
            if (isValid(query, dictionary)) {
                result.add(query);
            }
        }

        return result;
    }

    private boolean isValid(String query, String[] dictionary) {
        for (String word : dictionary) {
            int diff = 0;

            for (int i = 0; i < query.length(); i++) {
                if (query.charAt(i) != word.charAt(i)) {
                    diff++;
                }
                if (diff > 2) break;
            }

            if (diff <= 2) return true;
        }
        return false;
    }
}
```

---

# 💡 How It Works (Step-by-step)

### Example:

```
query = "note"
dictionary = ["wood", "joke", "moat"]
```

Compare with `"joke"`:

```
n ≠ j  → diff = 1
o = o  → diff = 1
t ≠ k  → diff = 2
e = e  → diff = 2
```

👉 diff = 2 → VALID ✅

---

# ❌ Why "ants" fails?

Compare with all dictionary words:

Example `"moat"`:

```
a ≠ m → 1
n ≠ o → 2
t ≠ a → 3  ❌ stop here
```

👉 diff > 2 → INVALID

---

# 🧠 Why This Approach Works

* No need for fancy DP or Trie
* Constraints are small → brute force is optimal
* Early break (`diff > 2`) improves performance

---

# ⚡ Optimization Thought (for interviews)

If constraints were large:

* Use **Trie**
* Or **pre-processing with hashing**

But here → **simple comparison is best**

---

# 🎯 Final Takeaway

👉 This problem is just:

> "Find words with Hamming Distance ≤ 2"

---

