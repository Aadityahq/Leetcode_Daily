# Intuition

For every pair:

[
(nums[i], nums[n-1-i])
]

we want all pairs to finally have the **same sum**.

Let the target sum be:

[
S
]

For one pair `(a,b)`:

* **0 moves** needed if `a+b = S`
* **1 move** needed if we can change either `a` or `b` to make the sum `S`
* **2 moves** otherwise

The challenge is:

* Try all possible sums from `2` to `2 * limit`
* Find minimum total moves

A brute force approach would be too slow:

* up to `10^5` sums
* up to `10^5` pairs

So we need optimization.

---

# Key Observation

For a pair `(a,b)`:

Assume:

[
x = \min(a,b), \quad y = \max(a,b)
]

## Case 1: 2 moves

Initially, every target sum requires `2 moves`.

---

## Case 2: 1 move range

With one modification, we can create sums in range:

[
[x+1,\ y+limit]
]

Why?

* Smallest possible sum after one change:

  * change larger element to `1`
  * `x + 1`

* Largest possible sum after one change:

  * change smaller element to `limit`
  * `y + limit`

So for this entire interval:

[
x+1 \le S \le y+limit
]

only **1 move** is required.

---

## Case 3: 0 move

Exactly at:

[
S = a+b
]

we need `0 moves`.

---

# Efficient Idea — Difference Array

Instead of calculating moves for every sum separately:

We use a **difference array** to mark ranges.

For each pair:

---

## Start with 2 moves everywhere

Every sum initially costs `2`.

---

## Reduce to 1 move in range

For:

[
[x+1,\ y+limit]
]

subtract `1`.

---

## Reduce to 0 at exact sum

For:

[
a+b
]

subtract another `1`.

---

# Range Updates

We maintain a difference array `diff`.

Finally take prefix sum to get moves for each target sum.

---

# Visualization for One Pair

Suppose:

```text
a = 2, b = 4, limit = 5
```

Then:

```text
sum = 6
x = 2
y = 4
```

Possible costs:

| Target Sum | Moves |
| ---------- | ----- |
| 2          | 2     |
| 3          | 1     |
| 4          | 1     |
| 5          | 1     |
| 6          | 0     |
| 7          | 1     |
| 8          | 1     |
| 9          | 1     |
| 10         | 2     |

Notice:

* 1-move range = `[3,9]`
* 0-move at `6`

---

# Step-by-Step Algorithm

For every pair:

```java
a = nums[i]
b = nums[n-1-i]
```

Let:

```java
low = Math.min(a,b) + 1
high = Math.max(a,b) + limit
sum = a + b
```

Update diff array:

---

## Everyone starts with 2 moves

```java
diff[2] += 2
diff[2*limit + 1] -= 2
```

---

## One move range

```java
diff[low] -= 1
diff[high + 1] += 1
```

---

## Zero move at exact sum

```java
diff[sum] -= 1
diff[sum + 1] += 1
```

---

After processing all pairs:

Take prefix sum and find minimum.

---

# Complexity

## Time

[
O(n + limit)
]

Very efficient.

---

## Space

[
O(limit)
]

---

# Java Solution

```java
class Solution {
    public int minMoves(int[] nums, int limit) {

        int n = nums.length;

        // Difference array
        int[] diff = new int[2 * limit + 2];

        for (int i = 0; i < n / 2; i++) {

            int a = nums[i];
            int b = nums[n - 1 - i];

            int low = Math.min(a, b) + 1;
            int high = Math.max(a, b) + limit;

            int sum = a + b;

            // Initially assume 2 moves for all sums
            diff[2] += 2;
            diff[2 * limit + 1] -= 2;

            // One move range
            diff[low] -= 1;
            diff[high + 1] += 1;

            // Zero move at exact sum
            diff[sum] -= 1;
            diff[sum + 1] += 1;
        }

        int answer = Integer.MAX_VALUE;
        int current = 0;

        // Check all possible target sums
        for (int s = 2; s <= 2 * limit; s++) {

            current += diff[s];

            answer = Math.min(answer, current);
        }

        return answer;
    }
}
```

---

# Dry Run

## Input

```text
nums = [1,2,4,3]
limit = 4
```

Pairs:

```text
(1,3)
(2,4)
```

---

## Pair (1,3)

* sum = 4
* one move range = [2,7]

---

## Pair (2,4)

* sum = 6
* one move range = [3,8]

---

After prefix calculation:

Minimum moves becomes:

```text
1
```

---

# Why Difference Array Works

Normally:

* updating every sum in a range would take:

[
O(limit)
]

per pair.

Too slow.

Difference array allows:

* range update in **O(1)**

Then one prefix pass reconstructs all values.

This reduces total complexity to:

[
O(n + limit)
]

which passes constraints easily.
