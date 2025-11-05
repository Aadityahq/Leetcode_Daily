**LeetCode 3318. Find X-Sum of All K-Long Subarrays I** 👇

---

## 🧩 Problem Understanding

You’re given:

* An integer array `nums`
* Two integers `k` and `x`

You must find the **x-sum** of every subarray of length `k`.

### ✅ X-sum definition:

1. Count occurrences (frequency) of each element in the subarray.
2. Keep only the top `x` most frequent elements.

   * If two elements have the same frequency → the **bigger value** is considered more frequent.
3. Calculate the sum of all occurrences of these selected `x` elements.

If a subarray has **fewer than `x` distinct elements**, its **x-sum = sum of all elements** in that subarray.

---

## ⚙️ Approach (Step-by-Step)

### Step 1: Sliding Window over `nums`

We need the x-sum for **every subarray of length `k`**, so:

* There will be `nums.length - k + 1` subarrays in total.
* For each starting index `i`, we consider the subarray `nums[i..i + k - 1]`.

Hence:

```java
for (int i = 0; i < result.length; i++) {
    int left = i, right = i + k - 1;
    result[i] = findXSumofSubArray(nums, left, right, x);
}
```

This loop simply extracts all subarrays of length `k` and computes their x-sum individually.

---

### Step 2: Counting Frequency in Each Subarray

Inside the helper function:

```java
int[] freq = new int[51];
```

* Since `1 <= nums[i] <= 50`, an array of size `51` (index 0 unused) efficiently stores frequency counts.
* Then, we iterate through the subarray:

```java
for (int i = left; i <= right; i++) {
    sum += nums[i];
    if (freq[nums[i]] == 0) distinctCount++;
    freq[nums[i]]++;
}
```

This does two things:

* Calculates the **total sum** of the subarray.
* Counts **distinct elements** (to handle the case when distinctCount < x).

---

### Step 3: Handling the "less than x distinct" case

If the number of distinct elements is less than `x`,
→ We **keep all elements**, i.e., the x-sum = total subarray sum.

```java
if (distinctCount < x) {
    return sum;
}
```

---

### Step 4: Finding the top x frequent elements

If we have at least `x` distinct elements:
We must pick **x elements** with the highest frequency.
If two have equal frequency → choose the **larger value**.

To do this:

```java
for (int pick = 0; pick < x; pick++) {
    int bestFreq = -1;
    int bestVal = -1;

    for (int val = 50; val >= 1; val--) {
        if (freq[val] > bestFreq) {
            bestFreq = freq[val];
            bestVal = val;
        }
    }
```

This loop:

* Scans from value `50` down to `1` (ensures higher value wins ties).
* Picks the most frequent (or biggest in tie).
* Adds its total contribution (`val * freq[val]`) to the sum.
* Sets its frequency to `0` (so it won’t be picked again).

```java
sum += bestVal * bestFreq;
freq[bestVal] = 0;
```

---

### Step 5: Store result

After computing for all subarrays, return the result array:

```java
return result;
```

---

## 🧠 Why This Works

1. **Brute Force is okay** – Since `n ≤ 50`, even checking all subarrays and counting frequencies each time is efficient.
2. **Frequency-based selection** ensures we always get the top `x` most frequent (and largest if tied).
3. **Simple array freq[51]** avoids complex maps or sorting, making it clean and efficient.

---

## 📊 Example Walkthrough

### Example:

`nums = [1,1,2,2,3,4,2,3], k = 6, x = 2`

Subarrays and x-sums:

1️⃣ `[1,1,2,2,3,4]`

* Frequencies → {1:2, 2:2, 3:1, 4:1}
* Top 2 frequent: 1 and 2 → (1+1+2+2) = 6
  ✅ answer[0] = 6

2️⃣ `[1,2,2,3,4,2]`

* Frequencies → {1:1, 2:3, 3:1, 4:1}
* Top 2 frequent: 2 and 4 → (2+2+2+4) = 10
  ✅ answer[1] = 10

3️⃣ `[2,2,3,4,2,3]`

* Frequencies → {2:3, 3:2, 4:1}
* Top 2 frequent: 2 and 3 → (2+2+2+3+3) = 12
  ✅ answer[2] = 12

**Output → [6, 10, 12] ✅**

---

## 🕒 Complexity

* For each of the `n - k + 1` subarrays:

  * We scan at most `k` elements → O(k)
  * We pick top x by scanning `50` values → O(50×x) ≈ constant
* So overall → **O(n × k)** (fast enough since n ≤ 50)

---

✅ **Final Takeaway:**
This solution uses **frequency counting + greedy selection of top x elements** to directly compute the x-sum for each k-length subarray.
It’s efficient, clear, and perfectly fits the given constraints.
