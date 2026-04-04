This is a very **pattern-recognition + matrix traversal** problem. Let’s break it in a way you can explain in interviews 👇

---

# 🧠 Key Idea (IMPORTANT)

The encoding process:

1. Write text **diagonally** (top-left → bottom-right) in a matrix
2. Then read the matrix **row by row**

👉 So to **decode**, we reverse this:

> We reconstruct the matrix row-wise, then read it **diagonally**

---

# 🔍 Step-by-Step Understanding

## 1. Find number of columns

We know:

```
encodedText.length = rows * cols
```

So:

```java
cols = encodedText.length() / rows;
```

---

## 2. Build the matrix

Fill row-wise (because encoding was done row-wise at the end):

Example:

```
encodedText = "ch   ie   pr", rows = 3

Matrix:
c h _ _
_ i e _
_ _ p r
```

---

## 3. Traverse diagonally

Start from each column of row `0`:

```
Start at (0,0) → go diagonal
Start at (0,1) → go diagonal
Start at (0,2) → go diagonal
...
```

Move:

```
i++, j++
```

---

## 4. Remove trailing spaces

Important:

* Result may have extra spaces at end → remove them

---

# ✅ Java Solution

```java
class Solution {
    public String decodeCiphertext(String encodedText, int rows) {
        if (rows == 1) return encodedText;

        int n = encodedText.length();
        int cols = n / rows;

        // Step 1: build matrix
        char[][] matrix = new char[rows][cols];
        int index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = encodedText.charAt(index++);
            }
        }

        // Step 2: read diagonally
        StringBuilder result = new StringBuilder();

        for (int startCol = 0; startCol < cols; startCol++) {
            int i = 0;
            int j = startCol;

            while (i < rows && j < cols) {
                result.append(matrix[i][j]);
                i++;
                j++;
            }
        }

        // Step 3: remove trailing spaces
        int end = result.length() - 1;
        while (end >= 0 && result.charAt(end) == ' ') {
            end--;
        }

        return result.substring(0, end + 1);
    }
}
```

---

# ⚡ Dry Run (Example 1)

Input:

```
encodedText = "ch   ie   pr"
rows = 3
```

Matrix:

```
c h _ _
_ i e _
_ _ p r
```

Diagonal traversal:

```
(0,0) → c → i → p
(0,1) → h → e → r
```

Result:

```
"cipher"
```

---

# 🚀 Why This Works

### Encoding did:

* Diagonal write
* Row-wise read

### So decoding:

* Row-wise fill
* Diagonal read ✅

---

# ⏱ Complexity

* Time: **O(n)**
* Space: **O(n)** (matrix)

---

# 🧠 Interview Tip

If interviewer asks *how you thought of this*:

Say:

> "Since encoding writes diagonally but reads row-wise, I reverse it by reconstructing the matrix row-wise and then reading diagonally."

---
