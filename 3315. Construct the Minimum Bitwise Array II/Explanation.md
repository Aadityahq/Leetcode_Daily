## 🧩 Problem Explanation

You are given an array `nums`:

* `nums[i]` is a **prime number**
* You must find the **smallest integer `ans[i]`** such that:

```
ans[i] OR (ans[i] + 1) = nums[i]
```

If no such number exists, return `-1`.

---

## 🔍 Key Bitwise Insight (Why this problem works)

### What happens when we do `x OR (x + 1)`?

When you add `1` to a number:

* All trailing `1`s flip to `0`
* The first `0` becomes `1`

So:

```
x        = ?????0111
x + 1    = ?????1000
OR       = ?????1111
```

📌 **Conclusion:**
`x | (x + 1)` always produces a number with **all lower bits = 1**

---

## 🧠 What numbers can be produced?

Only numbers of the form:

```
111...111   (binary)
```

Which means:

```
2^k - 1
```

Examples:

* `3  -> 11`
* `7  -> 111`
* `31 -> 11111`

---

## ❌ Why `nums[i] = 2` is impossible

```
2 = 10 (binary)
```

It is **not all 1s**, so:

```
ans OR (ans + 1) ≠ 2
```

✔ Therefore:

```
ans = -1
```

This is why your code explicitly checks:

```java
if (x == 2) ans[i] = -1;
```

---

## 💡 Core Idea Behind the Solution

For valid `nums[i]` (like `3, 7, 11, 31...`):

We want to **remove exactly one bit** so that:

* `(ans + 1)` restores it
* OR gives the original number
* AND `ans` is as **small as possible**

---

## 🧠 Bit Trick Used (Very Important)

```java
int bit = (x + 1) & -(x + 1);
```

### What does this do?

This extracts the **lowest set bit** of `(x + 1)`.

Example:

```
x = 11  -> 1011
x + 1 = 12 -> 1100

Lowest set bit = 0100 (4)
```

This bit corresponds to the **first zero bit** in `x`.

---

## 🎯 Why subtract `(bit >> 1)`?

```java
ans[i] = x - (bit >> 1);
```

* `bit` is where `(x + 1)` changes
* `bit >> 1` is the highest `1` bit in `x`
* Removing this bit gives the **smallest possible ans**

### Example: `x = 11`

```
x      = 1011
bit    = 0100
bit>>1 = 0010

ans = 1011 - 0010 = 1001 (9)
```

Check:

```
9 OR 10 = 1001 | 1010 = 1011 (11) ✔
```

---

## 🧪 Step-by-Step Example

### Input:

```
nums = [2, 3, 5, 7]
```

| nums[i] | Binary | ans[i] | Reason     |
| ------- | ------ | ------ | ---------- |
| 2       | 10     | -1     | Impossible |
| 3       | 11     | 1      | 1 OR 2 = 3 |
| 5       | 101    | 4      | 4 OR 5 = 5 |
| 7       | 111    | 3      | 3 OR 4 = 7 |

---

## ✅ Final Java Solution (Your Code)

```java
class Solution {
    public int[] minBitwiseArray(List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        
        for (int i = 0; i < n; i++) {
            int x = nums.get(i);
            
            // Prime number 2 is impossible
            if (x == 2) {
                ans[i] = -1;
            } else {
                // Get lowest set bit of (x + 1)
                int bit = (x + 1) & -(x + 1);
                
                // Remove the highest 1-bit in x
                ans[i] = x - (bit >> 1);
            }
        }
        return ans;
    }
}
```

---

## ⏱️ Complexity

* **Time:** `O(n)`
* **Space:** `O(1)` extra

---

