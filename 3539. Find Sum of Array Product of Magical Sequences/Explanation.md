# Magical Sum of Array Product of Magical Sequences
## 🧠 Problem Overview

You are given:

* An array `nums` of integers.
* Two integers: `m` (sequence length) and `k` (required number of set bits in the sum of powers).

### A sequence is called **magical** if:

1. It has **exactly `m` elements**, where each element is an **index** (not value) in `nums`.
2. The **sum** of `2^seq[0] + 2^seq[1] + ... + 2^seq[m-1]` has **exactly `k` set bits** (1s in binary).
3. The product of the sequence is `nums[seq[0]] * nums[seq[1]] * ... * nums[seq[m-1]]`.

You must **return the sum of products** of all such valid magical sequences modulo `10^9 + 7`.

---

## 💡 Intuition

Explicitly iterating over all `n^m` possible sequences is computationally infeasible for `m=30`, `n=50`.

Instead, we:

* Use **combinatorics** to count how many sequences use each index a certain number of times.
* Track the bit formation via **binary addition logic with carry**.
* Use **Dynamic Programming (DP)** to build up the total sum of valid products without enumerating all sequences.

---

## 🧱 Approach

### 🔢 Preprocessing

#### 1. **Binomial Coefficients Table (`C`)**

`C[i][j]` stores **number of ways to choose `j` positions from `i` available**.

Built using Pascal's Triangle:

```java
C[i][j] = C[i-1][j-1] + C[i-1][j]
```

#### 2. **Power Table (`pow`)**

`pow[i][cnt] = nums[i]^cnt mod MOD`

Avoids recalculating powers multiple times.

---

### 🚀 Dynamic Programming (`dp`)

We use a 4D DP:

```java
dp[pos][bits][carry][chosen]
```

Where:

* `pos`: current index in `nums` being processed
* `bits`: number of 1s (set bits) in the binary sum so far
* `carry`: current binary carry from the sum
* `chosen`: number of elements picked so far

#### Initial State

```java
dp[0][0][0][0] = 1
```

This means: before picking any number, with 0 bits, 0 carry, and 0 selections, there is **1 empty way** to start.

---

### 🔄 State Transitions

For each state:

1. Try taking current `nums[pos]` from `0` to `remaining = m - chosen` times.
2. Compute:

   * **New carry**: `(carry + cnt) >> 1`
   * **New set bit** (from LSB): `(carry + cnt) & 1`
   * **New bits** = previous + set bit
3. Contribution of this transition:

```java
dp[pos][bits][carry][chosen]
* C[remaining][cnt]    // Ways to place cnt items
* pow[pos][cnt]        // nums[pos]^cnt
```

All mod `MOD`.

Add it to:

```java
dp[pos + 1][new_bits][new_carry][chosen + cnt]
```

---

### ✅ Final Result Calculation

After all `n` indices:

```java
for (carry = 0 to m):
    carryBits = Integer.bitCount(carry)
    if carryBits <= k:
        result += dp[n][k - carryBits][carry][m]
```

**Why?** Because leftover `carry` contributes more 1s to the final binary sum.

We count total bits as:

```java
finalBits = bits_so_far + bitCount(carry)
```

We need total to be `k`, so:

```java
bits_so_far = k - bitCount(carry)
```

---

## 🧪 Examples

### Example 1

```text
Input: m = 5, k = 5, nums = [1,10,100,10000,1000000]
Output: 991600007
```

All permutations of `[0, 1, 2, 3, 4]` form magical sequences.

---

### Example 2

```text
Input: m = 2, k = 2, nums = [5,4,3,2,1]
Output: 170
```

Valid sequences: all pairs with exactly 2 set bits in their 2^index sum.

---

## ⏱ Time & Space Complexity

* **Time**: `O(n * k * m^2 * m)`

  * For each index, up to `k` bits, `m` carry, `m` chosen elements.
* **Space**: `O(n * k * m * m)`

---

## 🧩 Why This Works

By treating each index as a **digit in a binary number**, we simulate the building of a binary sum through **carry propagation** — a trick inspired by **binary addition mechanics**. We track how many 1s we have in the final binary number and how many elements we picked using DP, without brute-force enumeration.

---

## ✅ Code Implementation

[See Java code in `Solution.java`](#)

---

