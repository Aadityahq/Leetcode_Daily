Absolutely. This is a **BFS + bitmask + energy-state** problem. The important part is understanding **what information defines a state**.

# LeetCode 3568 — Minimum Moves to Clean the Classroom

## 1. Understand the problem

We have a grid containing:

* `S` → starting position
* `L` → litter we must collect
* `R` → reset area; energy becomes full
* `X` → obstacle
* `.` → empty cell

Every movement costs **1 energy** and **1 move**.

The student must collect **all litter** using the minimum number of moves.

The tricky part is that reaching the same cell with different:

* collected litter
* remaining energy

can lead to completely different possibilities.

So ordinary BFS on just `(row, col)` is **not enough**.

---

# 2. Why ordinary BFS doesn't work

Imagine:

```text
S . L
. R .
```

Suppose we reach some cell `(r,c)`.

If we have:

```text
energy = 5
```

that's very different from reaching the same cell with:

```text
energy = 1
```

because from the first state we can travel much farther.

Also, maybe we've already collected some litter.

For example:

```text
collected = {L1}
```

is different from:

```text
collected = {L1, L2}
```

Therefore, our state needs to contain:

```text
(row, col, energy, collectedLitter)
```

---

# 3. Bitmask for collected litter

There can be at most **10 litter cells**.

That's a huge hint that we should use a **bitmask**.

Suppose there are 4 litter cells:

```text
L0 L1 L2 L3
```

We represent collected litter using 4 bits.

For example:

```text
0000 → nothing collected
0001 → L0 collected
0011 → L0 and L1 collected
1011 → L0, L1 and L3 collected
1111 → all collected
```

If there are `k` litter cells, the total number of possible masks is:

```text
2^k
```

Since:

```text
k <= 10
```

maximum masks:

```text
2^10 = 1024
```

That's very manageable.

---

# 4. What should our BFS state be?

Our state is:

```text
(row, col, energy, mask)
```

where:

* `row`, `col` → student's position
* `energy` → remaining energy
* `mask` → which litter has been collected

We start with:

```text
(startRow, startCol, energy, 0)
```

Then BFS explores all possible movements.

Because every movement costs exactly **1**, BFS guarantees that the **first time we reach a state with all litter collected, we've used the minimum number of moves**.

---

# 5. Handling movement

From `(r,c)` we can move:

```text
up
down
left
right
```

We cannot move outside the grid or into `X`.

Before moving, we need at least:

```text
energy > 0
```

because moving costs 1 energy.

After moving:

```text
newEnergy = energy - 1
```

---

# 6. Handling `R`

This is an important detail.

Suppose:

```text
energy = 1
```

and we move onto `R`.

The move costs 1:

```text
1 - 1 = 0
```

But because we're now standing on `R`, our energy immediately becomes:

```text
energy = maximumEnergy
```

So:

```java
if (classroom[nr][nc] == 'R') {
    newEnergy = energyCapacity;
}
```

---

# 7. Handling litter

Suppose we move onto litter number `i`.

We set its bit:

```java
newMask = mask | (1 << i);
```

For example, if:

```text
mask = 0010
```

and we collect litter `0`:

```text
1 << 0 = 0001
```

then:

```text
0010
|
0001
=
0011
```

Now both litter `0` and `1` are collected.

---

# 8. Important optimization: dominance of energy

There's an interesting optimization we can make.

For a fixed:

```text
(row, col, mask)
```

suppose we've already reached it with:

```text
energy = 10
```

Later we reach the exact same:

```text
(row, col, mask)
```

with:

```text
energy = 6
```

The second state is useless.

Why?

Because the first state is strictly better:

```text
same position
same collected litter
more energy
```

So we only need to remember the **maximum energy** we've had for each:

```text
(row, col, mask)
```

This lets us avoid unnecessary states.

---

# 9. BFS state representation

We can create a small class:

```java
static class State {
    int r;
    int c;
    int energy;
    int mask;

    State(int r, int c, int energy, int mask) {
        this.r = r;
        this.c = c;
        this.energy = energy;
        this.mask = mask;
    }
}
```

Then:

```java
Queue<State> queue = new ArrayDeque<>();
```

---

# 10. `maxEnergy` array

We'll maintain:

```java
int[][][] maxEnergy =
    new int[m][n][1 << litterCount];
```

Meaning:

```text
maxEnergy[r][c][mask]
```

= maximum energy with which we've reached `(r,c)` after collecting exactly the litter represented by `mask`.

Initially:

```java
maxEnergy[startR][startC][0] = energy;
```

---

# 11. Complete Java solution

```java
import java.util.*;

class Solution {

    static class State {
        int r;
        int c;
        int energy;
        int mask;

        State(int r, int c, int energy, int mask) {
            this.r = r;
            this.c = c;
            this.energy = energy;
            this.mask = mask;
        }
    }

    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int startR = 0;
        int startC = 0;

        // Map each litter cell to a bit index.
        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        int litterCount = 0;

        // Find start position and assign IDs to litter cells.
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (classroom[i].charAt(j) == 'S') {
                    startR = i;
                    startC = j;
                }

                if (classroom[i].charAt(j) == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        int allCollected = (1 << litterCount) - 1;

        // No litter to collect.
        if (litterCount == 0) {
            return 0;
        }

        /*
         * maxEnergy[r][c][mask] =
         * maximum energy with which we have reached
         * (r, c) after collecting litter represented by mask.
         */
        int[][][] maxEnergy = new int[m][n][1 << litterCount];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(maxEnergy[i][j], -1);
            }
        }

        Queue<State> queue = new ArrayDeque<>();

        queue.offer(new State(startR, startC, energy, 0));
        maxEnergy[startR][startC][0] = energy;

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            // Process one BFS level.
            while (size-- > 0) {

                State current = queue.poll();

                int r = current.r;
                int c = current.c;
                int currEnergy = current.energy;
                int mask = current.mask;

                // All litter collected.
                if (mask == allCollected) {
                    return moves;
                }

                // Cannot make another move without energy.
                if (currEnergy == 0) {
                    continue;
                }

                for (int d = 0; d < 4; d++) {

                    int nr = r + dr[d];
                    int nc = c + dc[d];

                    // Outside grid.
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    // Obstacle.
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    // Moving costs one energy.
                    int newEnergy = currEnergy - 1;

                    // Collect litter if this cell contains one.
                    int newMask = mask;

                    if (classroom[nr].charAt(nc) == 'L') {
                        int id = litterId[nr][nc];
                        newMask |= (1 << id);
                    }

                    // Reset energy if we arrive at R.
                    if (classroom[nr].charAt(nc) == 'R') {
                        newEnergy = energy;
                    }

                    /*
                     * If we have already reached this
                     * (position, mask) with at least as much energy,
                     * this state is useless.
                     */
                    if (maxEnergy[nr][nc][newMask] >= newEnergy) {
                        continue;
                    }

                    maxEnergy[nr][nc][newMask] = newEnergy;

                    queue.offer(
                        new State(nr, nc, newEnergy, newMask)
                    );
                }
            }

            moves++;
        }

        return -1;
    }
}
```

---

# 12. Walk through Example 1

```text
classroom = [
    "S.",
    "XL"
]

energy = 2
```

Grid:

```text
S .
X L
```

Start:

```text
position = (0,0)
energy = 2
mask = 00
```

### Move 1

We cannot go down because:

```text
X
```

So go right:

```text
(0,0) → (0,1)
```

Energy:

```text
2 → 1
```

State:

```text
(0,1,1,00)
```

### Move 2

Go down:

```text
(0,1) → (1,1)
```

Energy:

```text
1 → 0
```

We collect the litter.

If this is litter `0`:

```text
mask = 01
```

Now:

```text
mask == allCollected
```

So answer:

```text
2
```

---

# 13. Walk through Example 2

```text
classroom = [
    "LS",
    "RL"
]

energy = 4
```

Grid:

```text
L S
R L
```

Start:

```text
(0,1)
energy = 4
```

### Move 1

Go left:

```text
S → L
```

Energy:

```text
4 → 3
```

Collect first litter.

### Move 2

Go down:

```text
L → R
```

Energy:

```text
3 → 2
```

But we're on `R`, so:

```text
energy = 4
```

### Move 3

Go right:

```text
R → L
```

Collect second litter.

All litter collected.

Answer:

```text
3
```

---

# 14. Why BFS gives the minimum

Every move has exactly the same cost:

```text
1 move
```

BFS explores states in this order:

```text
0 moves
↓
1 move
↓
2 moves
↓
3 moves
↓
...
```

Therefore, when we first encounter:

```text
mask == allCollected
```

we know there cannot be a solution requiring fewer moves.

So BFS gives the minimum.

---

# 15. Why the state needs the mask

Consider:

```text
       L1
       |
S ---- A ---- L2
```

Suppose we reach `A`.

State 1:

```text
A + L1 collected + energy 5
```

State 2:

```text
A + nothing collected + energy 5
```

Even though position and energy are identical, these states are **not equivalent**.

From State 1, only `L2` remains.

From State 2, both `L1` and `L2` remain.

That's why we need:

```text
mask
```

in our state.

---

# 16. Why energy is also necessary

Suppose we have:

```text
State A:
position = (2,3)
mask = 0101
energy = 10
```

and:

```text
State B:
position = (2,3)
mask = 0101
energy = 2
```

The states have the same position and collected litter, but State A is much better because it can travel farther before needing a reset.

That's why energy matters.

However, we don't need to store **every** energy value separately. We only keep the maximum energy for each `(r,c,mask)`.

---

# 17. Complexity

Let:

```text
m = number of rows
n = number of columns
k = number of litter cells
```

We know:

```text
k <= 10
```

There are at most:

```text
m × n × 2^k
```

position/mask combinations.

For each state, we try 4 directions.

So the time complexity is approximately:

**Time:**

```text
O(m × n × 2^k × 4)
= O(m × n × 2^k)
```

Since `k <= 10`:

```text
2^10 = 1024
```

and:

```text
m,n <= 20
```

so this is manageable.

**Space:**

```text
O(m × n × 2^k)
```

for `maxEnergy` and the BFS queue.

---

# ⭐ The main idea to remember

This problem looks like a simple grid BFS, but it isn't.

The key progression is:

```text
Grid shortest path
       ↓
Energy affects future movement
       ↓
Collected litter affects future goal
       ↓
State = (row, col, energy, mask)
       ↓
At most 10 litter
       ↓
Use bitmask
       ↓
All moves cost 1
       ↓
Use BFS
       ↓
For same (row, col, mask),
keep only maximum energy
```

### Interview/LeetCode pattern

Whenever you see something like:

> "Move through a grid while collecting items, and your future choices depend on which items you've collected"

think:

**`BFS + Bitmask + State`**

And whenever the problem says there are at most **10/15/20 special items**, immediately consider a **bitmask**.
