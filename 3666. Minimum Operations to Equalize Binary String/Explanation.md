## 🧠 3666. Minimum Operations to Equalize Binary String

You are given:

* A binary string `s`
* An integer `k`

In **one operation**, you must:

* Choose **exactly `k` different indices**
* Flip each selected bit (`0 ↔ 1`)

Goal → Make **all characters equal to `'1'`** using minimum operations.

If impossible → return `-1`.

---

# 🔎 Key Observations

Let:

* `n = s.length()`
* `zero = number of '0's`
* `one = n - zero`

Each operation:

* Flips exactly `k` bits.
* So total number of flips after `x` operations = `x × k`.

---

# ⚡ Important Logical Facts

### 1️⃣ Parity (Even/Odd) Condition

To turn all bits into `1`:

* Each `0` must be flipped **odd number of times**
* Each `1` must be flipped **even number of times**

Because:

* Flipping once changes bit
* Flipping twice cancels out

So the **parity (even/odd nature)** of flips matters.

---

### 2️⃣ If `k == n`

You must flip the whole string every time.

* If all bits are `0` → one operation makes all `1`
* Otherwise → impossible

That explains:

```java
if (len == k)
    return (zero == len ? 1 : -1);
```

---

### 3️⃣ Why Count Zeros?

```java
zero += ~s.charAt(i) & 1;
```

This is a bit trick:

* `'0'` ASCII = 48
* `'1'` ASCII = 49

`~char & 1` cleverly extracts whether it’s zero.

So this loop simply counts number of zeros.

---

# 🧮 Understanding the Core Math

Let:

```
base = len - k
```

Why?

Because:

* In one operation, you flip `k` bits
* The remaining `base = n - k` bits are untouched

We calculate two possible answers:

---

# 🟣 CASE 1: Odd number of operations

```java
int odd = Math.max(
    ceil(zero / k),
    ceil(one / base)
);
```

Why?

If total operations = `x`:

* Each zero must be flipped odd times
* Each one must be flipped even times

This creates two constraints:

1. Enough operations to flip all zeros
   → need at least `ceil(zero / k)`

2. Enough space to keep ones even-flipped
   → `ceil(one / base)`

Then:

```java
odd += ~odd & 1;
```

Forces result to be **odd number**.

---

# 🟢 CASE 2: Even number of operations

```java
int even = Math.max(
    ceil(zero / k),
    ceil(zero / base)
);
```

Now structure changes because total flips are even.

Then:

```java
even += even & 1;
```

Forces result to be **even**.

---

# 🎯 Why Final Conditions?

```java
if ((k & 1) == (zero & 1))
```

This checks parity compatibility:

Total flips = `operations × k`

If `k` and `zero` parity mismatch → impossible.

---

```java
if ((~zero & 1) == 1)
```

Checks another parity feasibility case.

---

Finally:

```java
return res == Integer.MAX_VALUE ? -1 : res;
```

If no valid parity case → impossible.

---

# 🧪 Example Walkthrough

## Example:

```
s = "0101"
k = 3
```

n = 4
zero = 2
one = 2

Answer = 2

Because:

* Each operation flips 3 bits
* Total flips = 6
* Carefully distributing flips makes all bits = 1

---

## Example:

```
s = "101"
k = 2
```

n = 3
zero = 1

We must flip exactly 2 bits per operation.

But:

* One zero needs 1 flip
* But each operation flips 2 bits
* Parity mismatch

Impossible → -1

---

# 🧠 Intuition Summary

This is NOT a greedy problem.

It is a **mathematical parity + counting problem**:

We are solving:

* Each zero needs odd flips
* Each one needs even flips
* Each operation flips exactly k bits
* Total flips = x × k
* Parity constraints must match

That’s why the solution works in:

```
O(n)
```

Only counting zeros + constant math.

---

# 🏆 Why This Problem is Hard

Because:

* It looks like simulation
* But actual solution is mathematical
* Requires parity reasoning
* Requires thinking globally, not locally

---
