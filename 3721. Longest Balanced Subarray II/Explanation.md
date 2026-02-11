## 🔎 Problem Understanding

You are given an integer array `nums`.

A **subarray** is called **balanced** if:

> **Number of distinct even numbers = Number of distinct odd numbers**

⚠️ Important:

* We count **distinct values**, not frequency.
* Duplicates don’t increase the count.
* Subarray must be **continuous**.

---

### 📌 Example 1

`nums = [2,5,4,3]`

Distinct evens → `{2,4}` → 2
Distinct odds → `{5,3}` → 2

Balanced ✅
Length = 4

---

### 📌 Example 3

`nums = [1,2,3,2]`

Longest balanced subarray = `[2,3,2]`

Distinct evens → `{2}` → 1
Distinct odds → `{3}` → 1

Balanced ✅
Length = 3

---

# 💡 Key Idea Behind the Solution

We want:

```
distinct_even == distinct_odd
```

Let’s convert this into something easier to track.

---

## 🔄 Transform the Problem

For each distinct number:

* If it’s **odd** → contribute `+1`
* If it’s **even** → contribute `-1`

Now define:

```
balance = (#distinct odds) - (#distinct evens)
```

A subarray is balanced when:

```
balance = 0
```

---

# 🚨 The Difficulty

This is NOT a normal prefix sum problem because:

* We count each number only **once per subarray**
* When left pointer moves, a number may stop being included
* When right pointer moves, a new distinct number may appear

So the contribution of a number applies:

👉 From its **first occurrence inside the subarray**

This is why a simple sliding window doesn’t work.

---

# 🧠 Core Strategy Used in Code

The solution uses a **Segment Tree with Lazy Propagation**.

Let’s understand why.

---

# 🏗 Step-by-Step Logic

## 1️⃣ Store All Positions of Each Number

```java
HashMap<Integer, ArrayList<Integer>> pos
```

Example:

```
nums = [3,2,2,5,4]

pos:
3 -> [0]
2 -> [1,2]
5 -> [3]
4 -> [4]
```

This helps us track:

* When a number appears
* When its next occurrence is

---

## 2️⃣ Initial Contribution

For each number:

* Take its **first occurrence**
* From that index to end of array,
  apply:

  * `+1` if odd
  * `-1` if even

Why?

Because from that point onward, that number becomes part of any subarray starting before it.

So we maintain a running “distinct balance effect” using range updates.

---

# 🌲 Why Segment Tree?

We need to:

1. Add a value to a **range**
2. Quickly find the **rightmost index where balance = 0**

Segment tree supports:

* Range add (lazy propagation)
* Query for zero efficiently

---

# 🔄 Main Loop (Sliding Left Pointer)

We move `l` from `0 → n-1`.

At each `l`:

### ✅ Step A: Find Rightmost `r`

We query:

```
findRightmostZero(l, n-1)
```

If found:

```
length = r - l + 1
```

Update answer.

---

### ✅ Step B: Remove Contribution of nums[l]

When left pointer moves:

* That number may stop being counted
* We must remove its contribution

How?

1. Find next occurrence of that number
2. From current index `l` to `nextOccurrence-1`
3. Reverse its contribution

```
st.addRange(L, R, -sign);
```

This effectively says:

> "This number is no longer distinct in future subarrays starting here."

---

# 🎯 Why This Works

At any index `i`, the segment tree stores:

```
balance of distinct numbers in subarray starting at current l and ending at i
```

So when balance becomes 0:

```
distinct odd = distinct even
```

That means subarray is balanced.

---

# 📈 Time Complexity

Let:

```
n = nums.length
```

* Building maps → O(n)
* Each index updated once → O(n log n)
* Each query → O(log n)

### ✅ Final Complexity:

```
O(n log n)
```

Efficient for:

```
n ≤ 10^5
```

---

# 🧩 Intuition Summary

Instead of checking all subarrays:

We:

1. Convert condition to balance = 0
2. Use range updates for distinct contributions
3. Use segment tree to:

   * Maintain balances
   * Quickly find valid right boundary

---

# 🔥 Why Problem is Hard

Because:

* It's not frequency-based
* It’s **distinct-based**
* Contribution changes dynamically
* Needs smart data structure

---

# 🏁 Final Understanding

We cleverly convert:

```
distinct_even == distinct_odd
```

into

```
balance == 0
```

And use a segment tree to dynamically:

* Add/remove distinct effects
* Find longest valid subarray efficiently

---

