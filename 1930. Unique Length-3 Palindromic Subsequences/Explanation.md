## 🎯 **Problem Summary**

We need to count **unique palindromic subsequences of length 3**.

A palindrome of length 3 has the form:

```
x y x
```

So:

* The **first** and **last** character must be **same** → call it `c`.
* The **middle** character can be **anything** (from 'a' to 'z').

We must count **unique** palindromes — meaning:

* Even if a palindrome appears multiple times as subsequences, we count it **once**.

---

## 💡 **Key Insight**

For a palindrome of form `c ? c`, we only care about:

1. **Leftmost occurrence** of character `c`
2. **Rightmost occurrence** of character `c`

For each character `c`:

* Let `L = first index of c`
* Let `R = last index of c`
* If `L < R`, the substring `s[L+1 … R−1]` is the middle region.
* Every *distinct* character inside this middle region forms a valid palindrome `c x c`.

Example
`s = "aabca"`

For `'a'`:

* First `'a'` at index 0
* Last `'a'` at index 4
  Middle substring → `"abc"`
  Distinct characters → `{a, b, c}`
  So palindromes → `aaa`, `aba`, `aca`

---

## 🧠 **Why This Works**

We don’t need to generate subsequences!

We only need:

* Where each character starts and ends
* Which characters appear between their boundary region

Since there are only **26 characters**, algorithm is extremely fast.

---

## ⏱️ Time Complexity

* Finding L/R for 26 chars → `O(N)`
* Checking middle region for each char → `O(26 * N)` worst case
* Total ≈ **O(N)**

---

# ✅ **Java Solution**

```java
class Solution {
    public int countPalindromicSubsequence(String s) {
        int n = s.length();
        
        // Store first and last occurrence of each character
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, -1);
        Arrays.fill(last, -1);
        
        // Fill first & last occurrence
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            if (first[c] == -1) first[c] = i;
            last[c] = i;
        }
        
        int count = 0;
        
        // For every character as boundary
        for (int c = 0; c < 26; c++) {
            int L = first[c];
            int R = last[c];
            if (L == -1 || L >= R) continue; // no valid middle region
            
            boolean[] seen = new boolean[26];
            
            // Collect distinct middle characters
            for (int i = L + 1; i < R; i++) {
                seen[s.charAt(i) - 'a'] = true;
            }
            
            // Add number of distinct middle characters
            for (boolean b : seen) {
                if (b) count++;
            }
        }
        
        return count;
    }
}
```

---

# 📝 **Summary**

### What we do:

1. Find **first** and **last** index of each character `'a'` to `'z'`.
2. For each character `c`, look at substring between its boundaries.
3. Count distinct characters in that region → these create unique `c x c` palindromes.
4. Sum them.

### Why it works:

* A palindromic subsequence of length 3 must be `c x c`.
* Boundaries decide possible middle characters.
* Distinct middle characters produce unique palindromes.

---


