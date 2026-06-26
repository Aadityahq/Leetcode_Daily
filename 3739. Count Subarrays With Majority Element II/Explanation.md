# Intuition

The condition says that **`target` must appear more than half the time in a subarray**.

Instead of directly checking every subarray (which would take `O(n²)` time), we transform the problem into one involving **prefix sums**.

The key observation is:

* If an element is equal to `target`, treat it as **+1**.
* Otherwise, treat it as **-1**.

Now, for any subarray:

* Let `x` = number of `target` elements.
* Let `y` = number of non-target elements.

The sum of the transformed subarray is:

```text
(+1 × x) + (-1 × y)
= x - y
```

The target is the majority element if:

```text
x > (x + y) / 2
```

Multiplying both sides by 2:

```text
2x > x + y
```

Rearranging:

```text
x - y > 0
```

Since the transformed subarray sum is `x - y`, the condition becomes:

> **A subarray is valid if its transformed sum is greater than 0.**

So the problem is reduced to:

> **Count the number of subarrays whose transformed sum is positive.**

---

# Approach

## Step 1: Transform the array

Convert every element as follows:

* `target` → `+1`
* Other elements → `-1`

Example:

```text
nums = [1,2,2,3]
target = 2

Converted array:

[-1, +1, +1, -1]
```

---

## Step 2: Use Prefix Sum

Let

```text
prefix[i]
```

represent the sum of the transformed array up to index `i`.

For a subarray `(l...r)`:

```text
sum = prefix[r] - prefix[l-1]
```

We need

```text
sum > 0
```

which means

```text
prefix[l-1] < prefix[r]
```

So, while processing each prefix sum, we need to know:

> **How many previous prefix sums are smaller than the current prefix sum?**

Each such prefix corresponds to one valid subarray ending at the current position.

---

## Step 3: Why Fenwick Tree?

The prefix sums can range from:

```text
-n to +n
```

For every prefix sum, we need two operations:

* Insert the current prefix sum.
* Count how many previous prefix sums are smaller.

Both operations should be efficient.

A **Fenwick Tree (Binary Indexed Tree)** supports:

* Update → `O(log n)`
* Prefix query → `O(log n)`

making it ideal for this problem.

---

# Dry Run

### Input

```text
nums = [1,2,2,3]
target = 2
```

### Converted array

```text
[-1, +1, +1, -1]
```

### Initial state

```text
prefix = 0
answer = 0

Fenwick contains prefix sum 0.
```

---

### Element 1

```text
value = -1

prefix = -1
```

Previous prefix sums smaller than `-1`:

```text
None
```

```text
answer = 0
```

Insert `-1`.

---

### Element 2

```text
value = +1

prefix = 0
```

Previous prefix sums:

```text
0
-1
```

Smaller than `0`:

```text
-1
```

```text
answer += 1
```

Insert `0`.

---

### Element 3

```text
value = +1

prefix = 1
```

Previous prefix sums:

```text
0
-1
0
```

All three are smaller than `1`.

```text
answer += 3
```

Current answer:

```text
4
```

Insert `1`.

---

### Element 4

```text
value = -1

prefix = 0
```

Previous prefix sums:

```text
0
-1
0
1
```

Only `-1` is smaller than `0`.

```text
answer += 1
```

Final answer:

```text
5
```

---

# Code Explanation

### Initialize Fenwick Tree

```java
int offset = n + 2;
Fenwick bit = new Fenwick(2 * n + 5);
```

Since prefix sums can be negative, an **offset** is added so every index becomes positive before storing it in the Fenwick Tree.

---

### Insert Initial Prefix Sum

```java
bit.update(offset, 1);
```

Before processing any element, the prefix sum is `0`.

This represents the empty prefix and allows counting subarrays starting from index `0`.

---

### Process Every Element

```java
for (int num : nums) {
```

Traverse the array once.

---

### Update Prefix Sum

```java
if (num == target)
    prefix++;
else
    prefix--;
```

* `target` contributes `+1`
* Other elements contribute `-1`

---

### Count Valid Previous Prefix Sums

```java
ans += bit.query(prefix - 1 + offset);
```

We need previous prefix sums that are **strictly smaller** than the current prefix.

`query(prefix - 1)` returns exactly that count.

Each such prefix forms one valid subarray ending at the current index.

---

### Store Current Prefix Sum

```java
bit.update(prefix + offset, 1);
```

After processing the current element, insert the current prefix sum into the Fenwick Tree so future elements can use it.

---

# Why `prefix - 1`?

We need:

```text
previousPrefix < currentPrefix
```

The Fenwick Tree performs prefix queries (≤ index).

So querying

```java
query(prefix - 1)
```

counts all prefix sums **strictly less** than the current prefix.

---

# Time Complexity

* Each Fenwick update takes `O(log n)`.
* Each Fenwick query takes `O(log n)`.
* There are `n` elements.

**Overall Time Complexity:**

```text
O(n log n)
```

---

# Space Complexity

* Fenwick Tree stores at most `2n + 5` values.

**Space Complexity:**

```text
O(n)
```

This approach efficiently counts all subarrays where `target` is the majority element while satisfying the constraints (`n ≤ 10^5`).
