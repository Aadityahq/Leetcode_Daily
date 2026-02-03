## 🔍 Problem Restated (in simple words)

You’re given:

* an array `nums`
* integers `k` (number of subarrays)
* integer `dist`

### 🔹 Rules

1. You must divide `nums` into **k contiguous subarrays**
2. **Cost of a subarray = its first element**
3. Let starting indices of subarrays be:

   ```
   0, i1, i2, ..., i(k-1)
   ```
4. Constraint:

   ```
   i(k-1) - i1 ≤ dist
   ```

### 🎯 Goal

Minimize:

```
nums[0] + nums[i1] + nums[i2] + ... + nums[i(k-1)]
```

---

## 🧠 Key Observation (VERY IMPORTANT)

* The **first subarray always starts at index 0**, so:

  ```
  nums[0] is always included
  ```
* We only need to **choose k−1 more starting points**
* All these starting points must lie in a **window of size `dist + 1`**

So the problem becomes:

> **From a sliding window of indices, pick the smallest `k-1` values to minimize cost**

---

## 🔄 Window Insight

We slide a window of valid starting indices:

```
[1 ... 1 + dist]
[2 ... 2 + dist]
[3 ... 3 + dist]
...
```

For each window:

* We choose the **smallest `k−1` numbers**
* Add their sum
* Take the minimum over all windows

---

## 🚀 Why This Is Hard

* `n` can be up to **100,000**
* Each window can be large
* We must **add, remove, and query smallest K values fast**

Brute force ❌
Sorting every window ❌

So we need a **data structure that supports**:

* Insert
* Remove
* Get sum of smallest `k` elements
  → **in `O(log n)`**

---

## 💡 Solution Strategy

We use a **two-multiset sliding window technique**

### Data Structure: `SmartWindow`

We split elements into two groups:

| Set    | Meaning               |
| ------ | --------------------- |
| `low`  | Smallest `k` elements |
| `high` | Remaining elements    |

We maintain:

* `sumLow` = sum of elements in `low`
* Balanced sizes so `low` always holds the **smallest `k` elements**

---

## 🧱 How `SmartWindow` Works

### 1️⃣ Add an element

```java
window.add(x)
```

* If `x` is small → goes to `low`
* Else → goes to `high`
* Rebalance so `low` keeps exactly `k` smallest values

---

### 2️⃣ Remove an element

```java
window.remove(x)
```

* Remove from whichever set contains it
* Rebalance again

---

### 3️⃣ Query minimum cost

```java
window.query()
```

Returns:

```
sum of smallest k elements
```

---

## 🧪 Walking Through the Main Function

```java
k -= 1;
```

Because:

* `nums[0]` is fixed
* We only choose **k−1** more starts

---

### 🪟 Initial Window

```java
for(int i = 1; i <= 1 + dist; i++)
    window.add(nums[i]);
```

This creates the **first valid range of start positions**

---

### 🔄 Sliding the Window

```java
for(int i = 2; i + dist < n; i++) {
    window.remove(nums[i - 1]);
    window.add(nums[i + dist]);
    ans = Math.min(ans, window.query());
}
```

Each step:

* Remove the element leaving the window
* Add the new element entering
* Query the smallest `k−1` values

---

### ➕ Final Answer

```java
return ans + nums[0];
```

Because:

* `nums[0]` is **always included**

---

## ⏱ Time & Space Complexity

| Aspect | Complexity     |
| ------ | -------------- |
| Time   | **O(n log n)** |
| Space  | **O(n)**       |

Efficient enough for `n = 100,000` ✅

---

## 🧠 Why This Solution Is Brilliant

* Converts the problem into **sliding window + k smallest elements**
* Uses **balanced TreeMaps** to simulate a multiset
* Avoids recomputation
* Works for large inputs

---

## 🏁 Final Intuition Summary

> Fix the first cost →
> Slide a valid window →
> Always take the cheapest `k−1` starts →
> Maintain efficiently →
> Pick the minimum sum

---

