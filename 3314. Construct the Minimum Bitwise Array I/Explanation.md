# Explanation: Construct the Minimum Bitwise Array I
## 🔍 Problem Explanation 

You are given an array `nums` of **prime numbers**.

For each number `nums[i]`, you must find the **smallest integer `ans[i]`** such that:

```
ans[i] | (ans[i] + 1) = nums[i]
```

If **no such number exists**, return `-1` for that index.

---

## 🧠 Key Bitwise Observation

Let’s understand this expression:

```
x | (x + 1)
```

### What happens when we add 1?

* All **trailing 1s** in `x` become `0`
* The **first 0 bit** becomes `1`

### Then OR (`|`) does this:

* All bits **below the first zero** become `1`
* Higher bits remain unchanged

👉 So the result always looks like:

```
(prefix bits) + all 1s at the end
```

---

## ❌ When is it Impossible?

### If `nums[i]` is **even**

* Even number → last bit is `0`
* But `x | (x+1)` **always ends with 1**

📌 Therefore:

```
Even number ≠ x | (x + 1)
```

👉 **Answer = -1**

That’s why:

```java
if (val % 2 == 0) {
    ans[i] = -1;
}
```

---

## ✅ When is it Possible?

### If `nums[i]` is **odd**

All primes except `2` are odd, so solutions usually exist.

Now the challenge is to find the **minimum possible `x`**.

---

## 🧩 Core Trick (THE HEART OF THE SOLUTION)

```java
long t = (long) val + 1;
long lowbit = t & -t;
ans[i] = val - (int)(lowbit >> 1);
```

Let’s break this down.

---

### Step 1: `t = val + 1`

Adding `1` flips trailing `1`s to `0` until the first `0`.

Example:

```
val = 11  (1011)
t   = 12  (1100)
```

---

### Step 2: `lowbit = t & -t`

This extracts the **lowest set bit** of `t`.

Example:

```
t = 1100
lowbit = 0100
```

This bit tells us **where the carry stopped**.

---

### Step 3: `lowbit >> 1`

This gives the **largest power of two we can safely subtract** to keep OR intact.

```
lowbit >> 1 = 0010
```

---

### Step 4: `ans = val - (lowbit >> 1)`

```
val = 1011 (11)
ans = 1001 (9)
```

Check:

```
9 | 10 = 1011 = 11 ✅
```

✔️ And this is the **minimum possible value**

---

## 🧪 Example Walkthrough

### Example 1

```
nums = [2, 3, 5, 7]
```

| val | result      |
| --- | ----------- |
| 2   | even → `-1` |
| 3   | `1`         |
| 5   | `4`         |
| 7   | `3`         |

Output:

```
[-1, 1, 4, 3]
```

---

### Example 2

```
nums = [11, 13, 31]
```

| val | ans |
| --- | --- |
| 11  | 9   |
| 13  | 12  |
| 31  | 15  |

Output:

```
[9, 12, 15]
```

✔ Matches expected output

---

## 🧾 Final Java Code (Correct)

```java
class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        
        for (int i = 0; i < n; i++) {
            int val = nums.get(i);
            
            if (val % 2 == 0) {
                ans[i] = -1;
            } else {
                long t = (long) val + 1;
                long lowbit = t & -t;
                ans[i] = val - (int)(lowbit >> 1);
            }
        }
        
        return ans;
    }
}
```

---

## ⏱ Complexity

* **Time:** `O(n)`
* **Space:** `O(1)` (excluding output)

---

