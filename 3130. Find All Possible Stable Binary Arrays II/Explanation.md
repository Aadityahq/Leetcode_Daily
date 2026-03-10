# 1. Understanding the Problem

You are given:

* `zero` → number of **0s**
* `one` → number of **1s**
* `limit` → **maximum allowed consecutive identical elements**

We must build a **binary array** such that:

1️⃣ It contains exactly

* `zero` zeros
* `one` ones

2️⃣ **No subarray longer than `limit` contains only one type.**

Which means:

❌ Not allowed

```
0000  (if limit = 3)
1111
```

✔ Allowed

```
00101
11010
```

So the rule is:

> You cannot place more than **limit consecutive 0s or limit consecutive 1s**.

---

# 2. Key Idea

When building the array, we only care about **what the last element was**.

Because if the last element was `0`, we must ensure we **don't exceed `limit` zeros in a row**.

So we maintain **two DP states**.

---

# 3. DP Definition

We create two DP tables:

```
dp0[o][z]
```

Number of ways to build an array using:

* `o` ones
* `z` zeros

**ending with 0**

---

```
dp1[o][z]
```

Number of ways using:

* `o` ones
* `z` zeros

**ending with 1**

---

# 4. Base Cases

### Only 1s

If we only place `1`s and no `0`s:

```
dp1[i][0] = 1
```

for

```
i <= limit
```

Example:

```
1
11
111
```

But not

```
1111 (if limit = 3)
```

So:

```java
for (int i = 1; i <= Math.min(one, limit); i++) {
    dp1[i][0] = 1;
}
```

---

### Only 0s

Similarly:

```
dp0[0][j] = 1
```

for

```
j <= limit
```

```java
for (int j = 1; j <= Math.min(zero, limit); j++) {
    dp0[0][j] = 1;
}
```

---

# 5. Transition Logic

Now we fill the DP table.

We consider adding:

* a **0**
* a **1**

---

# Case 1: Ending with 0

We want:

```
dp0[o][z]
```

This means we add a **0** at the end.

Before adding 0, the array could end with:

### 1️⃣ A `1`

```
dp1[o][z-1]
```

Example

```
101 + 0
```

---

### 2️⃣ A `0`

```
dp0[o][z-1]
```

Example

```
100 + 0
```

So:

```
dp0[o][z] = dp1[o][z-1] + dp0[o][z-1]
```

---

### But there is a problem ⚠️

This counts sequences where we might exceed `limit`.

Example (limit = 2)

```
000
```

We must remove cases where we created **limit+1 consecutive 0s**.

Those sequences look like:

```
.....1 000
```

Which means before the last `(limit+1)` zeros, there was a `1`.

That state is:

```
dp1[o][z-limit-1]
```

So we **subtract them**.

---

### Final Formula

```
dp0[o][z] =
dp1[o][z-1]
+ dp0[o][z-1]
- dp1[o][z-limit-1]
```

Code:

```java
dp0[o][z] = dp1[o][z - 1];
dp0[o][z] = (dp0[o][z] + dp0[o][z - 1]) % MOD;

if (z - limit - 1 >= 0) {
    dp0[o][z] = (dp0[o][z] - dp1[o][z - limit - 1] + MOD) % MOD;
}
```

---

# Case 2: Ending with 1

Now we append a **1**.

Before adding 1, array could end with:

### 1️⃣ A `0`

```
dp0[o-1][z]
```

Example

```
010 + 1
```

---

### 2️⃣ A `1`

```
dp1[o-1][z]
```

Example

```
011 + 1
```

So:

```
dp1[o][z] = dp0[o-1][z] + dp1[o-1][z]
```

---

### Remove invalid sequences

If we added `(limit+1)` ones.

We subtract:

```
dp0[o-limit-1][z]
```

---

### Final Formula

```
dp1[o][z] =
dp0[o-1][z]
+ dp1[o-1][z]
- dp0[o-limit-1][z]
```

Code:

```java
dp1[o][z] = dp0[o - 1][z];
dp1[o][z] = (dp1[o][z] + dp1[o - 1][z]) % MOD;

if (o - limit - 1 >= 0) {
    dp1[o][z] = (dp1[o][z] - dp0[o - limit - 1][z] + MOD) % MOD;
}
```

---

# 6. Final Answer

The array can end with either:

* `0`
* `1`

So:

```
answer =
dp0[one][zero]
+
dp1[one][zero]
```

Code:

```java
return (dp0[one][zero] + dp1[one][zero]) % MOD;
```

---

# 7. Why This Works

This DP ensures:

✔ Exact number of `0`s and `1`s
✔ No more than `limit` consecutive same elements
✔ Efficient counting of all valid arrays

The **subtraction trick** avoids explicitly tracking consecutive counts.

This reduces complexity from **4D DP → 2D DP**.

---

# 8. Time Complexity

```
O(zero × one)
```

Max:

```
1000 × 1000 = 10^6
```

Very manageable.

---

# 9. Space Complexity

```
O(zero × one)
```

For:

```
dp0
dp1
```

---

# 10. Intuition Summary

Instead of tracking **how many consecutive numbers we placed**, we:

1️⃣ Count normally
2️⃣ **Subtract invalid sequences that exceed the limit**

This is a **classic DP + prefix subtraction trick**.

---

