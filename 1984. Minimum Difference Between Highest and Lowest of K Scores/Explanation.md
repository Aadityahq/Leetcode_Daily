## 🧩 Problem Understanding (in simple words)

You are given:

* An array `nums` → scores of students
* An integer `k` → number of students you must choose

### Your task:

Pick **any `k` students** such that:

> **(highest score − lowest score)** among those `k` students is as **small as possible**.

Return that **minimum difference**.

---

## 🔍 Key Observations (WHY this approach works)

1. **Order doesn’t matter**, only the values do
   We don’t care *which* students we pick, only their scores.

2. To minimize
   `max − min`,
   the scores should be **as close together as possible**.

3. If the array is **sorted**, then:

   * Any group of `k` **consecutive elements** will have the **smallest possible range** for that group.

👉 So instead of checking all combinations (which is expensive), we:

* **Sort**
* **Slide a window of size `k`**
* Take the minimum difference in all such windows

---

## ✨ Special Case

If `k == 1`:

* Only one score
* `max = min`
* Difference = `0`

So we return `0` immediately.

---

## 🛠️ Step-by-Step Solution (HOW it works)

### Step 1: Handle edge case

```java
if (k == 1) return 0;
```

---

### Step 2: Sort the array

```java
Arrays.sort(nums);
```

Example:

```
nums = [9, 4, 1, 7]
after sorting → [1, 4, 7, 9]
```

---

### Step 3: Sliding window of size `k`

For each window:

* Lowest score = `nums[i]`
* Highest score = `nums[i + k - 1]`
* Difference = `nums[i + k - 1] - nums[i]`

```java
int minDiff = Integer.MAX_VALUE;

for (int i = 0; i <= nums.length - k; i++) {
    minDiff = Math.min(minDiff, nums[i + k - 1] - nums[i]);
}
```

---

### Step 4: Return the minimum difference

```java
return minDiff;
```

---

## 📌 Dry Run Example

### Input:

```
nums = [9, 4, 1, 7], k = 2
```

### Sorted:

```
[1, 4, 7, 9]
```

### Windows of size 2:

| Window | Difference |
| ------ | ---------- |
| [1, 4] | 3          |
| [4, 7] | 3          |
| [7, 9] | **2** ✅    |

### Output:

```
2
```

---

## 🧠 Why This Is Efficient

* Sorting: `O(n log n)`
* Sliding window: `O(n)`
* Total: **`O(n log n)`** ✅

Much faster than checking all combinations.

---

## ✅ Final Takeaway

* **Sort first** → makes closest values neighbors
* **Check only consecutive groups of size `k`**
* **Minimize `max - min`**
* Simple, clean, optimal solution 💯

