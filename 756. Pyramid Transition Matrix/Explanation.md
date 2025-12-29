## 🧱 Problem Intuition

You are given:

* A **bottom row** of blocks (string `bottom`)
* A list of **allowed triangles** (`allowed`), where
  `"ABC"` means:
  **A (left)** + **B (right)** ⟶ **C (top)**

Your goal is to check whether you can **build the pyramid up to a single block at the top**, such that **every triangle used is in `allowed`**.

---

## 🔍 Key Observations

1. The pyramid is built **level by level** from bottom to top.
2. Each upper level is **1 block shorter** than the level below.
3. For every **adjacent pair** in a row, there may be **multiple possible top blocks**.
4. Since:

   * `bottom.length ≤ 6`
   * Characters are limited (`A`–`F`)

   👉 We can safely use **DFS + Backtracking**.

---

## 🧠 Strategy (DFS + Backtracking)

### Step 1: Preprocess `allowed`

Convert `allowed` into a **map**:

```
"AB" → list of possible top blocks
```

Example:

```
["BCC","CDE","CEA"]
becomes:
"BC" → [C]
"CD" → [E]
"CE" → [A]
```

---

### Step 2: Recursive Building

* Try to build the **next row** from the current row.
* For each adjacent pair:

  * Try **all possible valid top blocks**
* If any path reaches **length = 1**, return `true`
* If all paths fail → `false`

---

## 📊 Visual Understanding

![Image](https://assets.leetcode.com/uploads/2021/08/26/pyramid2-grid.jpg)

![Image](https://i.sstatic.net/zXxbj.png)

---

## ✅ Java Solution

```java
class Solution {

    // Map to store pair -> possible top blocks
    private Map<String, List<Character>> map = new HashMap<>();

    public boolean pyramidTransition(String bottom, List<String> allowed) {

        // Build mapping
        for (String s : allowed) {
            String key = s.substring(0, 2);
            char top = s.charAt(2);
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(top);
        }

        // Start DFS
        return dfs(bottom);
    }

    private boolean dfs(String current) {

        // If only one block left, pyramid is built
        if (current.length() == 1) {
            return true;
        }

        // Generate all possible next rows
        return buildNextRow(current, 0, new StringBuilder());
    }

    private boolean buildNextRow(String current, int index, StringBuilder nextRow) {

        // If next row is fully built, recurse
        if (index == current.length() - 1) {
            return dfs(nextRow.toString());
        }

        String pair = current.substring(index, index + 2);

        // If no allowed triangle exists, fail early
        if (!map.containsKey(pair)) {
            return false;
        }

        // Try all possible top blocks
        for (char c : map.get(pair)) {
            nextRow.append(c);
            if (buildNextRow(current, index + 1, nextRow)) {
                return true;
            }
            nextRow.deleteCharAt(nextRow.length() - 1); // backtrack
        }

        return false;
    }
}
```

---

## ⏱️ Time & Space Complexity

* **Time:** Exponential in worst case
  (limited by small constraints)
* **Space:**

  * Recursion stack + mapping
  * `O(allowed.length)`

---

## 🧪 Example Walkthrough

### Example 1

```
bottom = "BCD"
allowed = ["BCC","CDE","CEA"]
```

Steps:

```
BCD
 ↓
CE
 ↓
A
```

✅ Valid pyramid → `true`

---

### Example 2

```
bottom = "AAAA"
allowed = ["AAB","AAC","BCD","BBE","DEF"]
```

All paths fail before reaching the top
❌ → `false`

---

## 💡 Final Takeaway

* This problem is a **classic DFS + backtracking** question.
* Constraints are small → brute-force with pruning is safe.
* Preprocessing `allowed` is **key to efficiency**.
