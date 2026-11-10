# LeetCode 1267. Count Servers that Communicate



Medium

Array

Matrix



Let's understand this problem step by step, including what the question is asking, how your code works, why we use each condition, and how to optimise it.

## 1. Understand the problem

You are given a 2D matrix (`grid`) representing a server center.

- `1` means a server is present.
- `0` means no server is present.

Two servers can communicate if they are present in the same row OR the same column.

Our task is to count the servers that can communicate with at least one other server.

Important: A server should not communicate with itself. There must be another server in the same row or column.

### Example 1

```
grid = [[1, 0],
        [0, 1]]
```

Visual representation:

# 1

Server 1

# 0

Empty

# 0

Empty

# 1

Server 2

Output: 0

Neither server has another server in its row or column.

### Example 2

```
grid = [[1, 0],
        [1, 1]]
```

Visual representation:

# 1

Server 1

# 0

# 1

Server 2

# 1

Server 3

Output: 3

- Server 1 communicates with Server 2 (same column).
- Server 2 communicates with Server 1 and Server 3.
- Server 3 communicates with Server 2 (same row).

All three servers can communicate, so the answer is `3`.

## 2. Understand your approach (Brute Force)

Your solution uses three nested loops.

The main idea is:

For every server (`grid[row][col] == 1`):

1. Check whether another server exists in the same row.
2. If found, count this server.
3. Otherwise, check whether another server exists in the same column.
4. If found, count this server.
5. If neither exists, ignore this server.

Let's understand the code section by section.

## 3. Code explanation

### Step 1: Initialise variables

```
int numRows = grid.length;
int numCols = numRows > 0 ? grid[0].length : 0;

int communicableServersCount = 0;
```

- `numRows`: Total number of rows.
- `numCols`: Total number of columns.
- `communicableServersCount`: Stores the number of servers that can communicate.

The ternary operator:

```
numRows > 0 ? grid[0].length : 0;
```

means:

- If at least one row exists, get the number of columns.
- Otherwise, return `0`.

### Step 2: Traverse the matrix

```
for (int row = 0; row < numRows; ++row) {
    for (int col = 0; col < numCols; ++col) {
```

These two loops visit every cell in the matrix.

For example:

```
grid = [[1, 0],
        [1, 1]]
```

The traversal order is:

| row | col | Value |
| --- | --- | ----- |
| 0   | 0   | 1     |
| 0   | 1   | 0     |
| 1   | 0   | 1     |
| 1   | 1   | 1     |

### Step 3: Check whether a server exists

```
if (grid[row][col] == 1) {
    boolean canCommunicate = false;
```

We only need to check cells containing `1`.

Initially:

```
canCommunicate = false;
```

Why?

Because we haven't found another server in the same row or column yet.

### Step 4: Check the same row

```
for (int otherCol = 0; otherCol < numCols; ++otherCol) {

    if (otherCol != col && grid[row][otherCol] == 1) {
        canCommunicate = true;
        break;
    }
}
```

This is the most important part of your solution.

Let's understand these conditions.

Condition 1:

```
otherCol != col
```

Why do we need this?

Because we must not compare a server with itself.

For example:

```
grid = [[1, 0],
        [0, 1]]
```

When checking `grid[0][0]`, we don't want to consider `grid[0][0]` itself as another server.

Condition 2:

```
grid[row][otherCol] == 1
```

This checks whether another server exists in the same row.

If both conditions are true:

```
canCommunicate = true;
break;
```

- `canCommunicate = true`: We found another server.
- `break`: No need to check the remaining columns.

Remember: `break` improves efficiency because we only need to find one other server, not all servers.

### Step 5: Count if another server exists in the same row

```
if (canCommunicate) {
    communicableServersCount++;
}
```

If we find another server in the same row, we immediately increment the answer.

But what if we don't find another server in the same row?

Then we check the column.

### Step 6: Check the same column

```
else {
    for (int otherRow = 0; otherRow < numRows; ++otherRow) {

        if (otherRow != row && grid[otherRow][col] == 1) {
            canCommunicate = true;
            break;
        }
    }

    if (canCommunicate) {
        communicableServersCount++;
    }
}
```

This is similar to the row-checking logic.

The difference is:

- `otherRow != row`: Don't compare the server with itself.
- `grid[otherRow][col] == 1`: Check whether another server exists in the same column.

If another server is found, increment the count.

Why do we use `else` here?

Because if a server has already been found in the same row, we have already counted the current server.

We don't need to check its column again.

This also prevents counting the same server twice.

### Step 7: Return the answer

```
return communicableServersCount;
```

After checking every cell, return the total number of servers that can communicate.

## 4. Dry run with Example 2

Input:

```
grid = [[1, 0],
        [1, 1]]
```

Let's trace the execution.

| Cell  | Row check       | Column check | Count |
| ----- | --------------- | ------------ | ----- |
| (0,0) | No other server | Finds (1,0)  | 1     |
| (0,1) | Empty cell      | Skipped      | 1     |
| (1,0) | No other server | Finds (0,0)  | 2     |
| (1,1) | Finds (1,0)     | Not needed   | 3     |

Final Answer: 3

## 5. Time and space complexity of your solution

Let:

- `m` = Number of rows.
- `n` = Number of columns.

Time Complexity:

Your outer two loops visit all cells:

\\[ O(m \times n) \\]

For every server, you may scan its entire row and column:

\\[ O(m+n) \\]

Therefore, the worst-case time complexity is:

\\[ \boxed{O(m \times n \times (m+n))} \\]

Space Complexity:

You only use a few variables. No additional arrays or data structures.

\\[ \boxed{O(1)} \\]

## 6. Optimised approach – Row and Column Counting

Your solution is correct, but we can optimise it.

Instead of checking every server's entire row and column repeatedly, we can count the servers in each row and column beforehand.

Idea:

Create two arrays:

```
int[] rowCount = new int[m];
int[] colCount = new int[n];
```

- `rowCount[i]` stores the number of servers in row `i`.
- `colCount[j]` stores the number of servers in column `j`.

For every cell containing `1`:

```
rowCount[i]++;
colCount[j]++;
```

Then, a server can communicate if:

```
rowCount[i] > 1 || colCount[j] > 1
```

Why?

Because if a row or column contains more than one server, the current server has at least one other server to communicate with.

### Optimised Java solution



Recommended

Two-pass approach



```
class Solution {
    public int countServers(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        int[] rowCount = new int[m];
        int[] colCount = new int[n];

        // First pass: Count servers in each row and column
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 1) {
                    rowCount[i]++;
                    colCount[j]++;
                }
            }
        }

        int count = 0;

        // Second pass: Count communicating servers
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 1 &&
                    (rowCount[i] > 1 || colCount[j] > 1)) {

                    count++;
                }
            }
        }

        return count;
    }
}
```

### Optimised complexity

| Complexity | Value               |
| ---------- | ------------------- |
| Time       | \\(O(m \times n)\\) |
| Space      | \\(O(m+n)\\)        |

We traverse the matrix only twice. Therefore, the solution is much more efficient than repeatedly scanning rows and columns.

## 7. Compare both approaches

| Feature          | Your solution     | Optimised solution |
| ---------------- | ----------------- | ------------------ |
| Approach         | Brute force       | Counting           |
| Matrix traversal | Repeated scanning | Two passes         |
| Time             | \\(O(mn(m+n))\\)  | \\(O(mn)\\)        |
| Space            | \\(O(1)\\)        | \\(O(m+n)\\)       |
| Correctness      | Correct           | Correct            |

## 8. Important concepts to remember

1. Matrix traversal: Using nested loops to visit every cell.
2. Row checking: Finding another server in the same row.
3. Column checking: Finding another server in the same column.
4. Boolean variable: Using `canCommunicate` to track whether a server has a communication partner.
5. Break: Stop searching once another server is found.
6. Frequency counting: Precompute the number of servers in every row and column to avoid repeated scanning.

## 9. Interview check – Test your understanding

Q1. Why do we use \`otherCol != col\` in your solution?

To skip empty cells

To avoid comparing a server with itself

To check the next row

Q2. When can a server communicate in the optimised solution?

rowCount[i] > 1 OR colCount[j] > 1

rowCount[i] == 0 AND colCount[j] == 0

rowCount[i] == 1 AND colCount[j] == 1

Q3. What is the time complexity of the optimised solution?

O(m + n)

O(m × n)

O(m × n × (m + n))

Check answers

My recommendation: Your original solution is logically correct. Understand it properly first, then learn the optimised two-pass approach. In interviews, explaining why repeated row/column scanning can be replaced with precomputed counts demonstrates good problem-solving skills.