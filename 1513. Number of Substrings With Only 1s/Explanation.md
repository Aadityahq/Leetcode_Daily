**1513. Number of Substrings With Only 1s**

---

# ✅ **Understanding the Problem**

You are given a binary string `s` containing only `'0'` and `'1'`.

You must count **how many substrings consist only of ‘1’**.

Example:

If a segment of consecutive 1s has length **k**, then:

* Substrings of length 1 → k
* Substrings of length 2 → k−1
* Substrings of length 3 → k−2
* …
* Substrings of length k → 1

Total substrings =
[
\frac{k \cdot (k+1)}{2}
]

So the trick is:

### ✔ Find consecutive groups of ‘1’s

### ✔ For each group of length k, add k*(k+1)/2

### ✔ Return modulo (1e9+7)

---

# 🧠 **Why this works**

You don't need to generate substrings.
You only need the number of continuous **runs of 1s**.

Example:
`s = "0110111"`

Runs of ‘1’:

* `"1"` → length = 1 → contributes 1
* `"11"` → length = 2 → contributes 3 (2 + 1)
* `"111"` → length = 3 → contributes 6 (3 + 2 + 1)

Total = 1 + 3 + 6 = **9**

---

# ⭐ Time and Space Complexity

* **Time:** O(n)
* **Space:** O(1)

---

# ✅ **Java Code (Clean & Fast — O(n))**

```java
class Solution {
    public int numSub(String s) {
        long MOD = 1000000007;
        long count = 0; // count continuous 1s
        long ans = 0;

        for (char c : s.toCharArray()) {
            if (c == '1') {
                count++;
            } else {
                ans = (ans + (count * (count + 1) / 2) % MOD) % MOD;
                count = 0;
            }
        }

        // Add last segment
        ans = (ans + (count * (count + 1) / 2) % MOD) % MOD;

        return (int) ans;
    }
}
```

---

# 📝 Explanation of the Code

### We keep a variable `count`:

* It counts how many continuous `'1'` characters we have seen so far.

### When we hit a `'0'`:

* We compute contribution from the previous run:
  [
  \frac{count \cdot (count + 1)}{2}
  ]
* Reset `count = 0`.

### At end of string:

* Add any remaining run.

---

