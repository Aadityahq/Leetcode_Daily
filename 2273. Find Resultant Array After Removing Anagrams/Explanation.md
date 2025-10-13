# 2273. Find Resultant Array After Removing Anagrams

**Difficulty:** Easy  
**Topics:** Array, String, Hashing  

---

## 🧩 Problem Description

You are given a 0-indexed string array `words`, where `words[i]` consists of lowercase English letters.

In one operation, select any index `i` such that `0 < i < words.length` and `words[i - 1]` and `words[i]` are **anagrams**, and delete `words[i]` from `words`.  
Keep performing this operation as long as you can select an index that satisfies the conditions.

Return `words` after performing all operations.  
It can be shown that selecting the indices for each operation in any arbitrary order will lead to the same result.

> 🔹 An **Anagram** is a word formed by rearranging all the letters of another word using them exactly once.  
> For example, `"baba"` and `"abba"` are anagrams since both contain two `'a'`s and two `'b'`s.

---

## 🧠 Intuition

If two **adjacent words** are anagrams, we must **remove the later one**.  
To identify anagrams, the simplest method is to **sort the characters** in both words — if their sorted versions are equal, they are anagrams.

---

## 💭 Why This Works

- Sorting a word gives its **canonical (standardized) form** — e.g.  
  `"baba" → "aabb"`, `"abba" → "aabb"`, `"bbaa" → "aabb"`.
- So, all anagrams of the same letters will have **identical sorted strings**.
- Therefore, we just need to keep one representative word per group of **consecutive anagrams**.

This guarantees that the final array contains:
1. The first word from each anagram group.
2. All other distinct words in order.

---

## ⚙️ How It Works (Step-by-Step)

1. Start from the first word and add it to the result.
2. For every next word:
   - Sort the word alphabetically.
   - Compare it with the **sorted version of the last kept word**.
   - If it’s **different**, add it to the result.
   - If it’s **same**, skip it (since it’s an anagram of the previous one).
3. Continue until the end.

---

## 💻 Code Implementation (Java)

```java
import java.util.*;

class Solution {
    public List<String> removeAnagrams(String[] words) {
        List<String> result = new ArrayList<>();
        String prevSorted = "";

        for (String word : words) {
            char[] chars = word.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);

            if (!sorted.equals(prevSorted)) {
                result.add(word);
                prevSorted = sorted;
            }
        }

        return result;
    }
}
````

---

## 🧾 Example Walkthrough

**Input:**
`["abba", "baba", "bbaa", "cd", "cd"]`

| Step | Word | Sorted | Prev Sorted | Action |
| ---- | ---- | ------ | ----------- | ------ |
| 1    | abba | aabb   | ""          | Keep   |
| 2    | baba | aabb   | aabb        | Skip   |
| 3    | bbaa | aabb   | aabb        | Skip   |
| 4    | cd   | cd     | aabb        | Keep   |
| 5    | cd   | cd     | cd          | Skip   |

✅ **Result:** `["abba", "cd"]`

---

## 🔍 Example 2

**Input:**
`["a", "b", "c", "d", "e"]`

There are no two adjacent anagrams, so no deletions occur.

**Output:**
`["a","b","c","d","e"]`

---

## ⏱️ Complexity Analysis

| Operation          | Complexity     |
| ------------------ | -------------- |
| Sorting each word  | O(k log k)     |
| Processing n words | O(n × k log k) |
| Space              | O(n × k)       |

where:

* **n** = number of words
* **k** = maximum length of a word

Since `n ≤ 100` and `k ≤ 10`, this approach is very efficient.

---

## 🧩 Key Takeaways

* Sorting each word provides a simple way to identify anagrams.
* Only keep the first occurrence among consecutive anagrams.
* The result is independent of deletion order — any order gives the same final array.

---

## 🏁 Final Output

**Resultant Array:**
`["abba", "cd"]`

```

---

