# Understanding the Problem

Each task is:

```text
[actual, minimum]
```

Where:

* `actual` = energy spent after completing the task
* `minimum` = minimum energy needed before starting the task

Example:

```text
[4, 8]
```

means:

* You need at least `8` energy to start
* After finishing, you lose `4` energy

---

# Main Goal

We need the **minimum initial energy** so that all tasks can be completed in some order.

Since order is flexible, the challenge is:

👉 **Which order minimizes the starting energy?**

---

# Important Observation

For a task:

```text
[actual, minimum]
```

The important value is:

[
minimum - actual
]

This tells us:

* how “strict” the task is to start
* compared to how much energy it actually consumes

---

# Greedy Idea

Tasks with larger:

[
(minimum - actual)
]

should be done earlier.

Why?

Because they require high energy to start, but consume less energy.

If we delay them, our energy may become too low later.

---

# Sorting Logic

Your code sorts like this:

```java
Arrays.sort(shop, (a, b) -> b[1] - b[0] - (a[1] - a[0]));
```

This means:

Sort by:

[
(minimum - actual)
]

in descending order.

---

# Example

Tasks:

```text
[[1,3],[2,4],[10,11],[10,12],[8,9]]
```

Compute:

| Task    | minimum - actual |
| ------- | ---------------- |
| [1,3]   | 2                |
| [2,4]   | 2                |
| [10,11] | 1                |
| [10,12] | 2                |
| [8,9]   | 1                |

Tasks with bigger difference come first.

---

# Understanding the Variables

## 1. `start`

```java
int start = shop[0][1];
```

Initial energy needed for first task.

---

## 2. `bal`

```java
int bal = shop[0][1] - shop[0][0];
```

Remaining energy after finishing first task.

Example:

Task = `[4,8]`

Start with `8`

After completing:

[
8 - 4 = 4
]

So:

```text
bal = 4
```

---

## 3. `loan`

```java
int loan = 0;
```

Extra energy we need to add later if current balance becomes insufficient.

Think of it as:

👉 additional borrowed energy

---

# Step-by-Step Working

## Suppose

```text
tasks = [[1,2],[2,4],[4,8]]
```

After sorting:

```text
[[4,8],[2,4],[1,2]]
```

---

# First Task

```java
start = 8
bal = 8 - 4 = 4
```

---

# Second Task `[2,4]`

Need minimum = `4`

Current balance = `4`

Good.

After task:

```text
bal = 4 - 2 = 2
```

---

# Third Task `[1,2]`

Need minimum = `2`

Current balance = `2`

Good.

After task:

```text
bal = 2 - 1 = 1
```

Done.

Answer:

```text
8
```

---

# What if balance is insufficient?

Suppose:

```java
if (bal < thresh)
```

Example:

```text
bal = 3
minimum needed = 7
```

Need extra:

```text
7 - 3 = 4
```

So:

```java
loan += thresh - bal;
bal = thresh;
```

We artificially increase energy to continue.

Then spend cost:

```java
bal -= cost;
```

---

# Why This Greedy Works

We do tasks with the largest:

[
minimum - actual
]

first because:

* they are hardest to start later
* but cheaper to complete

This minimizes extra energy additions.

This is a classic greedy scheduling proof.

---

# Time Complexity

Sorting:

[
O(n \log n)
]

Traversal:

[
O(n)
]

Total:

[
O(n \log n)
]

---

# Cleaner Version of the Code

```java
class Solution {
    public int minimumEffort(int[][] tasks) {

        // Sort by (minimum - actual) descending
        Arrays.sort(tasks,
            (a, b) -> (b[1] - b[0]) - (a[1] - a[0]));

        int energy = 0;
        int current = 0;

        for (int[] task : tasks) {

            int actual = task[0];
            int minimum = task[1];

            // Need more energy
            if (current < minimum) {

                energy += (minimum - current);
                current = minimum;
            }

            // Complete task
            current -= actual;
        }

        return energy;
    }
}
```

---

