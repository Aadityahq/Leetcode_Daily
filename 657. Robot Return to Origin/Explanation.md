### 🧠 Problem Understanding

You have a robot starting at **(0,0)**.
Each move changes its position:

* **U** → (y + 1)
* **D** → (y - 1)
* **R** → (x + 1)
* **L** → (x - 1)

👉 After all moves, you need to check:
**Is the robot back at (0,0)?**

---

### 💡 Key Idea (Why this works)

To return to origin:

* Total **U moves = D moves** (vertical balance)
* Total **R moves = L moves** (horizontal balance)

Because:

* Up cancels Down
* Right cancels Left

So instead of tracking full path, we just track **final position (x, y)**.

---

### ✅ Approach

1. Start with `(x = 0, y = 0)`
2. Loop through each character in string:

   * Update x or y accordingly
3. At the end:

   * If `(x == 0 && y == 0)` → return `true`
   * Else → return `false`

---

### 💻 Java Solution

```java
class Solution {
    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;

        for (char move : moves.toCharArray()) {
            if (move == 'U') {
                y++;
            } else if (move == 'D') {
                y--;
            } else if (move == 'R') {
                x++;
            } else if (move == 'L') {
                x--;
            }
        }

        return x == 0 && y == 0;
    }
}
```

---

### 🔍 Dry Run Example

#### Input: `"UDLR"`

| Move | x  | y |
| ---- | -- | - |
| U    | 0  | 1 |
| D    | 0  | 0 |
| L    | -1 | 0 |
| R    | 0  | 0 |

👉 Final position = **(0,0)** → ✅ `true`

---

### ⏱️ Complexity

* **Time:** `O(n)` → we traverse the string once
* **Space:** `O(1)` → no extra space used

---

### 🚀 Alternative (Shortcut Trick)

Instead of tracking coordinates, you can count:

```java
class Solution {
    public boolean judgeCircle(String moves) {
        int up = 0, down = 0, left = 0, right = 0;

        for (char move : moves.toCharArray()) {
            if (move == 'U') up++;
            else if (move == 'D') down++;
            else if (move == 'L') left++;
            else if (move == 'R') right++;
        }

        return up == down && left == right;
    }
}
```

---

### 🧩 Why This Problem is Easy

* No complex logic
* Just **simulation**
* Tests your understanding of:

  * loops
  * conditionals
  * coordinate thinking

---

