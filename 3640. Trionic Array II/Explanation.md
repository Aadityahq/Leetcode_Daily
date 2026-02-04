# Trionic Array II — Problem Explanation

You are given an integer array `nums`.

A **trionic subarray** is a **contiguous subarray** that can be split into **three consecutive parts**:

1. **Strictly increasing**
2. **Strictly decreasing**
3. **Strictly increasing**

Graphically, it looks like:

```
    ↑
   / \
  /   \   ↑
```

Your task is to **find the maximum possible sum** of such a subarray.

✔️ At least one trionic subarray always exists
✔️ Array size can be large (`10^5`), so brute force is impossible

---

# 🧠 Key Observation

Every trionic subarray has:

* **one local peak** (end of first increasing part)
* **one local valley** (end of decreasing part)

So instead of checking all subarrays, we:

👉 **Treat each index as a potential middle peak**
👉 Expand **left** for increasing
👉 Expand **right** for decreasing, then increasing again

This guarantees **linear time overall**.

---

# 🧩 Idea Behind This Solution

For every index `i`:

1. Assume `nums[i]` is the **peak**
2. Move right to form the **strictly decreasing** part
3. Expand left to get the **best increasing prefix**
4. Expand right again to get the **best increasing suffix**
5. Combine all three parts to form a trionic subarray
6. Take the **maximum sum**

---

# 🔍 Code Walkthrough (How & Why)

```java
long res = -1 * (long)1e16;
```

We initialize the answer with a very small number to handle negative values.

---

## 🔁 Main Loop

```java
for(int i = 1; i < n - 2; i++)
```

We start from index `1` and stop early to ensure space for all three parts.

---

## ⛰️ Step 1: Build the decreasing middle

```java
int a = i;
int b = i;
long net = nums[a];
```

* `a` and `b` start at the peak
* `net` stores the sum of the **middle decreasing segment**

```java
while (b + 1 < n && nums[b + 1] < nums[b]) {
    net += nums[b + 1];
    b++;
}
```

➡️ Extend right as long as the sequence is **strictly decreasing**

```java
if (b == a) continue;
```

If no decreasing part exists → not trionic → skip

---

## 🔺 Step 2: Best increasing part on the LEFT

```java
long left = 0;
long lx = Long.MIN_VALUE;
```

We don’t take all left elements —
we take the **best increasing sum ending at the peak**.

```java
while (a - 1 >= 0 && nums[a - 1] < nums[a]) {
    left += nums[a - 1];
    lx = Math.max(lx, left);
    a--;
}
```

✔️ Ensures **strictly increasing**
✔️ Keeps the **maximum sum** prefix (`lx`)

```java
if (a == i) continue;
```

If left increasing part doesn’t exist → skip

---

## 🔺 Step 3: Best increasing part on the RIGHT

```java
long right = 0;
long rx = Long.MIN_VALUE;
int c = b;
```

Now we extend **after the valley**.

```java
while (b + 1 < n && nums[b + 1] > nums[b]) {
    right += nums[b + 1];
    rx = Math.max(rx, right);
    b++;
}
```

✔️ Strictly increasing
✔️ Take maximum suffix sum

```java
if (b == c) continue;
```

If no right increasing part → skip

---

## 🧮 Step 4: Combine the 3 parts

```java
res = Math.max(res, lx + net + rx);
```

* `lx` → best left increasing
* `net` → decreasing middle
* `rx` → best right increasing

That’s a **valid trionic subarray** 🎯

---

## 🚀 Optimization Trick

```java
i = b - 1;
```

We **skip already processed indices**, ensuring total complexity stays **O(n)**.

---

# ⏱️ Complexity

| Metric            | Value    |
| ----------------- | -------- |
| Time              | **O(n)** |
| Space             | **O(1)** |
| Handles negatives | ✅        |
| Interview-safe    | ✅        |

---

# 🧪 Example

Input:

```
[1, 4, 2, 7]
```

Pattern:

```
1 < 4 > 2 < 7
```

Sum:

```
1 + 4 + 2 + 7 = 14
```

---

# 🏁 Final Takeaway

* This solution is **greedy + two-directional expansion**
* Avoids DP arrays
* Very efficient and elegant
* Shows strong problem-solving skills in interviews

