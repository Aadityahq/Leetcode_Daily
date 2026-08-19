## LeetCode 1386 — Cinema Seat Allocation

### 1. Problem Understanding

There are `n` rows, and **each row has 10 seats**.

A group of 4 people needs one of these three blocks:

* `[2, 3, 4, 5]`
* `[4, 5, 6, 7]`
* `[6, 7, 8, 9]`

The important observation is that **we only care about seats 2 to 9**. Seats `1` and `10` don't affect whether a group can sit.

For each row, we want to find the maximum number of groups that can be placed.

A row can contain at most **2 groups**, because:

```text
2 3 4 5    6 7 8 9
└─ group ─┘ └─ group ─┘
```

So the answer is basically:

```text
2 × (number of completely empty rows)
+
best possible groups in rows having reservations
```

---

## 2. Key Observation

There are only three possible blocks:

```text
A = 2 3 4 5
B = 4 5 6 7
C = 6 7 8 9
```

Notice:

* `A` and `C` **do not overlap**, so both can be used together.
* `A` and `B` overlap at `4,5`.
* `B` and `C` overlap at `6,7`.

Therefore, for a row:

| Available blocks  | Groups |
| ----------------- | -----: |
| A and C available |      2 |
| A available       |      1 |
| B available       |      1 |
| C available       |      1 |
| None available    |      0 |

We don't actually need to process all `n` rows because `n` can be as large as:

```text
10^9
```

while the number of reserved seats is only:

```text
10^4
```

So we only process rows that actually appear in `reservedSeats`.

---

# 3. Efficient Approach

We can represent the relevant seats using a **bitmask**.

For every reserved seat, set its corresponding bit.

For example, consider:

```text
Seats: 2 3 4 5 6 7 8 9
Bits:  1 1 1 1 1 1 1 1
```

We only need 8 bits, so an `int` is more than enough.

Define masks for the three possible groups:

```text
A = seats 2,3,4,5
B = seats 4,5,6,7
C = seats 6,7,8,9
```

Then we can simply check:

```java
(mask & groupMask) == 0
```

This means none of the group's seats are reserved.

---

# 4. Why HashMap?

We use:

```java
Map<Integer, Integer>
```

where:

```text
row -> reserved seat bitmask
```

For example:

```text
reservedSeats = [[1,2], [1,3], [1,8], [2,6]]
```

could become:

```text
row 1 -> seats 2,3,8 reserved
row 2 -> seat 6 reserved
```

Rows that aren't present in the map have **no reserved seats**.

A completely empty row can always accommodate **2 groups**.

So after processing all reserved rows:

```text
emptyRows = n - numberOfRowsWithReservations
```

and:

```text
answer += emptyRows * 2
```

---

# 5. Java Solution

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        Map<Integer, Integer> map = new HashMap<>();

        // Store reserved seats of each row using a bitmask
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            // We only care about seats 2 to 9
            if (col >= 2 && col <= 9) {
                int bit = 1 << col;
                map.put(row, map.getOrDefault(row, 0) | bit);
            }
        }

        // Masks for the three possible groups
        int left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);
        int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);
        int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

        long answer = 0;

        // Rows having at least one relevant reserved seat
        for (int mask : map.values()) {

            boolean canUseLeft = (mask & left) == 0;
            boolean canUseMiddle = (mask & middle) == 0;
            boolean canUseRight = (mask & right) == 0;

            if (canUseLeft && canUseRight) {
                // Two non-overlapping groups
                answer += 2;
            } else if (canUseLeft || canUseMiddle || canUseRight) {
                // Only one group can be placed
                answer += 1;
            }
        }

        // Rows with no relevant reservations can always fit 2 groups
        answer += (long) (n - map.size()) * 2;

        return (int) answer;
    }
}
```

---

# 6. How the Bitmask Works

Suppose a row has:

```text
reservedSeats = [1, 2]
```

We calculate:

```java
1 << 2
```

which sets the bit corresponding to seat `2`.

If seat `3` is also reserved:

```java
1 << 3
```

Then:

```java
map.put(row, oldMask | (1 << col));
```

combines the reserved seats.

For example:

```text
seat 2 reserved → 00000100
seat 3 reserved → 00001000

OR              → 00001100
```

So the mask remembers that seats 2 and 3 are occupied.

---

# 7. Checking a Group

Suppose:

```java
left = seats 2,3,4,5
```

and the row has:

```text
reserved: 2,3
```

Then:

```java
(mask & left) != 0
```

because there is an overlap.

Therefore:

```java
canUseLeft = false;
```

If none of the seats overlap:

```java
(mask & left) == 0
```

then the group can sit there.

---

# 8. Example 1

```text
n = 3

reservedSeats =
[
    [1,2],
    [1,3],
    [1,8],
    [2,6],
    [3,1],
    [3,10]
]
```

### Row 1

Reserved:

```text
2, 3, 8
```

Possible blocks:

```text
2 3 4 5 → ❌
4 5 6 7 → ✅
6 7 8 9 → ❌
```

So:

```text
1 group
```

### Row 2

Reserved:

```text
6
```

Possible:

```text
2 3 4 5 → ✅
4 5 6 7 → ❌
6 7 8 9 → ❌
```

So:

```text
1 group
```

### Row 3

Reserved:

```text
1, 10
```

These seats don't affect the three blocks.

Therefore:

```text
2 groups
```

Total:

```text
1 + 1 + 2 = 4
```

Answer:

```text
4
```

---

# 9. Why We Don't Iterate From `1` to `n`

This is one of the most important parts of the problem.

Imagine:

```text
n = 1,000,000,000
```

We cannot efficiently do:

```java
for (int row = 1; row <= n; row++)
```

because that would require **one billion iterations**.

But suppose only:

```text
10,000
```

seats are reserved.

Only a maximum of `10,000` rows can contain reservations.

Therefore, we process only those rows using a `HashMap`.

All other rows are automatically:

```text
2 groups each
```

This reduces the work dramatically.

---

# 10. Complexity

Let:

```text
R = reservedSeats.length
```

We process every reservation once.

### Time

```text
O(R)
```

The second loop processes at most `R` rows, so overall:

```text
O(R)
```

### Space

The HashMap stores at most `R` rows:

```text
O(R)
```

Since:

```text
R <= 10^4
```

this is very efficient.

---

## 11. One Important Detail

I used:

```java
long answer
```

instead of `int` while calculating.

Why?

Because:

```text
n <= 10^9
```

and every completely empty row can contain 2 groups.

So the answer can be as large as:

```text
2 × 10^9
```

which is still within Java's `int` range, but using `long` during multiplication:

```java
(long) (n - map.size()) * 2
```

is safer and avoids accidental integer-overflow issues in similar problems.

---

## Final Idea to Remember

The entire problem can be reduced to **three checks per affected row**:

```text
Can I use [2,3,4,5]?
Can I use [4,5,6,7]?
Can I use [6,7,8,9]?
```

Then:

```text
empty row       → 2 groups
left + right    → 2 groups
any one block   → 1 group
no block        → 0 groups
```

And because `n` can be huge, **HashMap + Bitmask** lets us ignore all the completely empty rows and process only the rows that matter.
