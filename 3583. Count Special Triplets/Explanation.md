Below is a **clear, interview-friendly explanation** of:

✔ What the problem is asking
✔ How to think about the solution
✔ Why this code works
✔ Step-by-step breakdown of the given algorithm

---

# 🔍 **Problem Explanation — What Are Special Triplets?**

You are given an array `nums`.

A **special triplet** is a group of indices **(i, j, k)** such that:

1. `0 ≤ i < j < k < n`
2. `nums[i] = nums[j] * 2`
3. `nums[k] = nums[j] * 2`

So the **middle element nums[j]** must be half of both nums[i] and nums[k].

### Example

nums = `[6, 3, 6]`

Triplet (0, 1, 2):

* nums[0] = 6
* nums[1] = 3
* nums[2] = 6

Check conditions:

* 6 = 3 * 2 ✔
* 6 = 3 * 2 ✔
* Increasing indices ✔

Therefore, 1 special triplet exists.

---

# 🧠 Key Insight

For every index **j**, you want:

* **On the left (i < j)** → count how many values equal `2 * nums[j]`
* **On the right (k > j)** → count how many values equal `2 * nums[j]`

If left has `L` matches
and right has `R` matches

Then this `j` contributes:

### ✔ `L * R` special triplets.

---

# 🧠 Why the Optimized Approach is Needed?

n can be **100,000**, so:

❌ brute force O(n³) → impossible
❌ double loop O(n²) → also too slow

We need **O(n)** or **O(n log n)**.

We achieve this using **frequency counting**.

---

# ✅ Understanding Your Code (Optimized O(n))

### Constants

```java
final static int mod=(int)1e9+7, M=(int)1e5+1;
```

* Values range from 0 to 100000 → array of size M enough.

---

## 1️⃣ Count how many times each number appears

```java
int [] freq=new int[M];
for(int x: nums) freq[x]++;
```

`freq[x]` = how many times x appears in entire array.

This will help count elements on the **right side**.

---

## 2️⃣ prev[] to count elements on the **left side**

```java
int [] prev=new int[M];
prev[nums[0]]++;
```

At j = 1, only index 0 is on the left.

---

## 3️⃣ Loop over j from 1 to n-2

```java
for(int i=1; i<n-1; i++) {
    final int x = nums[i], x2 = x << 1;  // x2 = x * 2
```

We treat each index `i` as `j` (the middle element).

---

### 3A️⃣ Check if 2*x is within allowed range

```java
if (x2 < M)
```

---

### 3B️⃣ Count valid i (left side)

`prev[x2]` = number of values equal to `2 * nums[j]` on left

---

### 3C️⃣ Count valid k (right side)

We want values equal to `x2` on the right:

```
freq[x2]     → total occurrences in entire array  
prev[x2]     → those on left  
(x==0?1:0)   → correction for x=0 case  
```

So:

```
freq[x2] - prev[x2] - (x==0 ? 1 : 0)
```

gives number of values **strictly after j**.

---

### 3D️⃣ Multiply left × right = number of triplets for this j

```java
cnt += (long) prev[x2] * (freq[x2] - prev[x2] - (x==0?1:0));
```

This is the core idea:

**(elements on left that match) × (elements on right that match)**

---

### 3E️⃣ Add nums[j] to left map

```java
prev[x]++;
```

Now nums[j] becomes part of left side for the next iteration.

---

# ✔ Why the subtraction `(x==0 ? 1 : 0)`?

Special case:

If `x = nums[j] = 0`
then `2 * x = 0`, same value

When counting right-side values:

```
freq[0] - prev[0]
```

This includes nums[j] itself because freq counted it.

So we remove 1 extra occurrence (nums[j] itself).

---

# 📌 Final Formula

For each j:

```
leftMatches = prev[2 * nums[j]]
rightMatches = freq[2 * nums[j]] - prev[2 * nums[j]] - (nums[j] == 0 ? 1 : 0)
contribute = leftMatches * rightMatches
```

Sum up contributions modulo 1e9+7.

---

# 🟩 Time & Space Complexity

| Operation                | Cost                     |
| ------------------------ | ------------------------ |
| Building freq[]          | O(n)                     |
| Looping through nums[]   | O(n)                     |
| prev[] and freq[] arrays | O(max(nums[i])) = 100000 |

### Total:

### ✔ **O(n) time**, O(1e5) space → extremely fast

---

# 🎉 Final Result

This is an **optimal** solution using:

* Frequency counting
* Two-pointer logic (left counts vs right counts)
* No nested loops
* O(n) performance

---

