# ✅ **Explanation of the Problem (Simple & Clear)**

You must find:

> **The maximum possible sum of a subarray whose length is divisible by k.**

A subarray is continuous.

Example:
nums = `[-5,1,2,-3,4]`, k = 2
Valid subarray lengths: 2, 4, 6… (here only 2 or 4 possible)

The best subarray = `[1, 2, -3, 4]` with sum = **4**
Its length 4 is divisible by 2.

---

# 🧠 Why This Prefix-Sum Trick Works

Let prefix sum be:

```
prefix[i] = nums[0] + nums[1] + … + nums[i-1]
```

Subarray sum from l to r:

```
sum(l, r) = prefix[r+1] - prefix[l]
```

### Condition for length divisible by k:

Subarray length = (r - l + 1)

We want:

```
(r - l + 1) % k == 0
```

Rewrite:

```
(r + 1) % k == l % k
```

This is the key.

---

# 🎯 What the code does

The code keeps:

```
minPrefix[r] = smallest prefix sum where index % k == r
```

For the current position `i`:

* current prefix = `prefix`
* current remainder = `(i + 1) % k`

All valid subarrays ending at `i` must start at index `l` where:

```
l % k == (i + 1) % k
```

So:

```
best sum ending at i = prefix - minPrefix[(i + 1) % k]
```

We maximize this.

And update the smallest prefix for that remainder.

---

# 📌 Step-by-Step Explanation of The Code

### 1. Initialize minPrefix with ∞

Means no prefix sum recorded yet.

### 2. Set minPrefix[0] = 0

Because before any elements:

```
prefix = 0 at index 0 → remainder = 0
```

### 3. Build prefix sums

`prefix += nums[i]`.

### 4. Compute mod = (i+1) % k

Because `prefix` array is 1-indexed conceptually.

### 5. If we have seen any prefix with same remainder…

…then we can form a valid subarray whose length is divisible by k.

Compute:

```
prefix - minPrefix[mod]
```

This is a candidate answer.

### 6. Update minPrefix for future use.

---

# 🚀 Why This Code is Optimal

* Every index is processed once → **O(n)**
* Using modulo ensures the length condition → **length % k == 0**
* Using minimum prefix gives maximum subarray sum.

It’s the exact required pattern for this problem.

---

