## 🧩 1625. Lexicographically Smallest String After Applying Operations

### 🧠 Problem Understanding

We are given:

* A **string `s`** of **even length**, containing digits `0–9`.
* Two integers:

  * `a`: amount to **add** to digits at **odd indices** (0-indexed).
  * `b`: amount to **rotate right** the string.

We can perform two operations any number of times and in any order:

1. **Add Operation:**
   Add `a` to all digits at **odd indices** (cycling back after 9).
   Example: `"3456"`, `a = 5` → `"3951"`

2. **Rotate Operation:**
   Rotate the string **to the right by `b` positions**.
   Example: `"3456"`, `b = 1` → `"6345"`

We must find the **lexicographically smallest string** possible after performing these operations.

---

### 🔍 Example Walkthrough

**Example 1**

```
s = "5525", a = 9, b = 2
```

Steps:

| Step   | Operation         | Result |
| ------ | ----------------- | ------ |
| Start  | —                 | "5525" |
| Rotate | +2                | "2555" |
| Add    | +9 to odd indices | "2454" |
| Add    | +9 again          | "2353" |
| Rotate | +2                | "5323" |
| Add    | +9                | "5222" |
| Add    | +9                | "5121" |
| Rotate | +2                | "2151" |
| Add    | +9                | "2050" |

✅ Final lexicographically smallest = `"2050"`

---

### ⚙️ How to Approach

This problem can be seen as **a graph traversal problem**:

* Each **state** = a string configuration.
* Each **edge** = applying one of the two operations.
* We need to **explore all reachable states** and return the **smallest lexicographically** string.

---

### 💡 Why BFS / DFS Works

Since each operation transforms the string deterministically, and the total number of unique strings is **finite** (`<= 10 * len(s)` theoretically),
we can **use BFS or DFS** to explore all possible transformations without missing any.

To avoid cycles, we store visited strings in a `Set`.

---

### 🧩 Algorithm Steps

1. Initialize:

   * A queue (for BFS) or stack (for DFS).
   * A `visited` set.
   * A variable `smallest = s`.

2. While queue not empty:

   * Pop current string.
   * If it’s smaller than `smallest`, update it.
   * Generate new strings by applying:

     * **Add operation** on odd indices.
     * **Rotate operation** by `b` positions.
   * Add both new strings to the queue if not visited.

3. After all states explored, return `smallest`.

---

### 🧮 Complexity Analysis

| Type     | Explanation                       | Value                |
| -------- | --------------------------------- | -------------------- |
| ⏰ Time   | Each unique string processed once | `O(N * 10)` ≈ `O(N)` |
| 💾 Space | Visited states + queue            | `O(N)`               |

---

### ✅ Java Solution

```java
import java.util.*;

class Solution {
    public String findLexSmallestString(String s, int a, int b) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        
        String smallest = s;
        queue.offer(s);
        visited.add(s);
        
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.compareTo(smallest) < 0) {
                smallest = curr;
            }
            
            // Operation 1: Add 'a' to odd indices
            StringBuilder sb = new StringBuilder(curr);
            for (int i = 1; i < sb.length(); i += 2) {
                int newVal = (sb.charAt(i) - '0' + a) % 10;
                sb.setCharAt(i, (char) (newVal + '0'));
            }
            String added = sb.toString();
            
            if (visited.add(added)) queue.offer(added);
            
            // Operation 2: Rotate by 'b'
            String rotated = curr.substring(curr.length() - b) + curr.substring(0, curr.length() - b);
            if (visited.add(rotated)) queue.offer(rotated);
        }
        
        return smallest;
    }
}
```

---

### 🧩 Explanation of the Code

* **`visited` set** → avoids infinite loops from repeating states.
* **`queue`** → processes each string variation (BFS ensures we explore all).
* **`compareTo`** → checks lexicographical order.
* **`(charAt(i) - '0' + a) % 10`** → ensures digits wrap correctly (0–9).
* **`substring rotation`** → efficiently handles the rotate operation.

---

### 🏁 Final Answer

**Output for Example 1:**

```
Input: s = "5525", a = 9, b = 2
Output: "2050"
```

---

