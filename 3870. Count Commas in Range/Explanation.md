## LeetCode 3870 — Count Commas in Range

### 🔹 Problem Understanding

We are given an integer `n`.

We have to count the **total number of commas** used when writing every number from `1` to `n` in standard number formatting.

For example:

* `1` → `1` → **0 commas**
* `999` → `999` → **0 commas**
* `1000` → `1,000` → **1 comma**
* `1001` → `1,001` → **1 comma**
* `1002` → `1,002` → **1 comma**

So, if `n = 1002`:

```text
1000 → 1 comma
1001 → 1 comma
1002 → 1 comma
```

Total = **3**

---

## 🔹 Key Observation

The constraint is:

```text
1 <= n <= 10^5
```

That means `n` can be at most `100000`.

Every number from:

```text
1 → 999
```

has fewer than 4 digits, so **none of them contains a comma**.

The **first number that contains a comma is 1000**:

```text
1000 → 1,000
```

And every number from `1000` to `n` contains **exactly one comma** because even `100000` is:

```text
100,000
```

which has only one comma.

Therefore, the problem becomes:

> How many numbers are there from `1000` to `n`?

---

## 🔹 Calculating the Count

The number of integers from `1000` to `n`, inclusive, is:

```text
n - 1000 + 1
```

The `+1` is needed because both `1000` and `n` are included.

### Example

For:

```text
n = 1002
```

Numbers are:

```text
1000, 1001, 1002
```

Count:

```text
1002 - 1000 + 1
= 3
```

So the answer is `3`.

---

# 🔹 Why `n < 1000`?

If:

```text
n = 998
```

then every number is between `1` and `998`.

All have fewer than 4 digits:

```text
1
10
100
998
```

Therefore:

```text
0 commas
```

That's why we first check:

```java
if (n < 1000) return 0;
```

---

# 🔹 Your Solution

```java
class Solution {
    public int countCommas(int n) {

        if (n < 1000) return 0;

        return (n - 1000) + 1;
    }
}
```

### Step-by-step

#### 1. Check whether `n` reaches 1000

```java
if (n < 1000) return 0;
```

If `n` is less than `1000`, there are no commas.

---

#### 2. Count numbers starting from 1000

```java
return (n - 1000) + 1;
```

This counts all numbers in:

```text
[1000, n]
```

Each of these numbers contributes exactly **one comma**.

---

## 🔹 Dry Run

### Example 1

```text
n = 1002
```

Since:

```text
1002 >= 1000
```

calculate:

```text
(1002 - 1000) + 1
= 2 + 1
= 3
```

Answer:

```text
3
```

---

### Example 2

```text
n = 998
```

Since:

```text
998 < 1000
```

return:

```text
0
```

---

### Example 3

```text
n = 1000
```

Calculate:

```text
(1000 - 1000) + 1
= 1
```

Only `1000` has a comma:

```text
1,000
```

Answer:

```text
1
```

---

### Example 4

```text
n = 100000
```

Numbers from `1000` to `100000` all contain one comma.

```text
100000 - 1000 + 1
= 99001
```

Answer:

```text
99001
```

---

# 🔹 Why the Brute Force Approach Works but Is Unnecessary

Your commented approach was:

```java
int commaCount = 0;

for (int i = 1000; i <= n; i++) {
    commaCount++;
}

return commaCount;
```

This works because every number from `1000` to `n` has exactly one comma.

But we don't actually need the loop.

Instead of doing:

```text
1000
1001
1002
1003
...
n
```

we can directly calculate how many numbers are present:

```text
n - 1000 + 1
```

So the optimized solution is simpler and faster.

---

# 🔹 Complexity

### Time Complexity

```text
O(1)
```

There is no loop. We perform only a few operations.

### Space Complexity

```text
O(1)
```

No extra data structure is used.

---

## 🔹 Interview Explanation

If the interviewer asks **"How did you solve it?"**, you can say:

> Numbers below 1000 contain no commas. Starting from 1000, every number up to the given `n` contains exactly one comma because `n` is at most `10^5`. Therefore, instead of iterating through all numbers, I simply count the numbers in the inclusive range `[1000, n]`, which is `n - 1000 + 1`. If `n < 1000`, the answer is zero. This gives an O(1) time and O(1) space solution.

### Final Code

```java
class Solution {
    public int countCommas(int n) {
        if (n < 1000) return 0;

        return n - 1000 + 1;
    }
}
```

**Core idea to remember:**

> **First comma appears at 1000, and because `n ≤ 100000`, every number from 1000 to n has exactly one comma.**
