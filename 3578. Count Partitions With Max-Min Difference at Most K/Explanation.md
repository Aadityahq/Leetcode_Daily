# ✅ **Problem Summary (in simple words)**

You must cut the array into **contiguous segments**, and each segment must satisfy:

```
max(segment) − min(segment) ≤ k
```

Return how many ways you can partition the entire array.

---

# 🔍 **Key Insight**

Let:

* `dp[i]` = number of valid ways to partition prefix `nums[0..i-1]`
  (i.e., first `i` elements)

Thus:

```
dp[0] = 1   // empty array has 1 valid way
dp[n] = answer
```

To compute `dp[r+1]`, we need to consider all valid segments ending at position `r`.

A segment `[l..r]` is valid only when:

```
max(nums[l..r]) - min(nums[l..r]) ≤ k
```

So we need to quickly maintain max & min values in a moving window.

This is done using **two monotonic deques**.

---

# 🧠 **How the Deques Work**

### Max deque `mx`

Maintains decreasing values → `nums[mx.peek()]` is the maximum in the window.

### Min deque `mn`

Maintains increasing values → `nums[mn.peek()]` is the minimum in the window.

Each time we extend the window to the right (`r++`), we update deques so that:

* `mx.peek()` = index of max in `[l..r]`
* `mn.peek()` = index of min in `[l..r]`

Both operations run in **amortized O(1)**.

---

# 🚨 **When the window becomes invalid**

If:

```
max - min > k
```

then the segment `[l..r]` is **not valid**, so we must move the left boundary:

```
l++
```

While doing this, if the deleted index matches deque head, we remove it.

---

# 📌 **Understanding the DP Transition**

### Let’s define:

```
dp[r] = ways to partition nums[0..r-1]
dp[r+1] = ways to partition nums[0..r]
```

For fixed `r`, valid segment `[l..r]` means:

```
dp[r+1] += dp[l]
```

Because if last segment is `[l..r]`, then everything before `l` must be a valid partition — counted in `dp[l]`.

But instead of summing dp[l] one by one, we maintain a **running sum** of dp values:

```
sum = dp[l] + dp[l+1] + ... + dp[r]
```

This `sum` always equals:

```
dp[r+1]
```

This is why your code sets:

```
dp[r + 1] = sum;
```

---

# 🧮 **Flow of the Code Explained Step-by-Step**

---

### **1️⃣ Initialize**

```java
dp[0] = 1;
long sum = 0;
int l = 0;
```

Meaning:

* There is **1 way** to partition an empty prefix.
* Window starts at `l = 0`.

---

### **2️⃣ Expand window with r**

For each `r`:

* push nums[r] to max deque
* push nums[r] to min deque

Removing smaller/larger elements to keep monotonicity.

---

### **3️⃣ If window is invalid → move l**

```java
while (nums[mx.peek()] - nums[mn.peek()] > k) {
    // remove dp[l] from the active sum (invalidating)
    sum = (sum - dp[l] + MOD) % MOD;
    l++;
}
```

Why remove `dp[l]`?

Because when segment `[l..r]` becomes invalid, `dp[l]` should NOT be counted anymore for `dp[r+1]`.

---

### **4️⃣ Add dp[r] into the window sum**

```java
sum = (sum + dp[r]) % MOD;
```

Why?

* dp[r] corresponds to the partition count for the prefix ending before `r`
* Segment `[r..r]` is always valid (single element)
* So `dp[r]` is always a possible contributor

This ensures `sum` always holds:

```
dp[l] + dp[l+1] + ... + dp[r]
```

Which is exactly `dp[r+1]`.

---

### **5️⃣ Set dp[r+1]**

```java
dp[r + 1] = sum;
```

This is correct because:

* Each valid segment ending at r begins at some index `i ∈ [l..r]`
* For each such i, number of ways = dp[i]
* Sum of all dp[i] gives dp[r+1]

---

# 🎉 Final Answer

The final returned value:

```java
return dp[n];
```

is the count of valid partitions for the whole array.

---

# 🚀 Why This Solution Is Optimal

| Feature             | Explanation                          |
| ------------------- | ------------------------------------ |
| Sliding window      | ensures each index is processed once |
| Monotonic deques    | maintain max/min in O(1) amortized   |
| DP with rolling sum | avoids O(n²) transitions             |
| Total complexity    | **O(n)** time & O(n) space           |

Fully efficient for `n = 50,000`.

---

