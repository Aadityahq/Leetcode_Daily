### 🔹 Problem: **1980. Find Unique Binary String**

You are given an array `nums` containing **n unique binary strings**, and **each string has length n**.

Your task is to **return any binary string of length n that is NOT present in `nums`**.

* Each string contains only `'0'` and `'1'`
* All strings in `nums` are **unique**
* `1 ≤ n ≤ 16`

---

## 🔹 Example

```
Input: nums = ["01","10"]

Possible binary strings of length 2:
00
01
10
11
```

Already present in array:

```
01
10
```

Missing strings:

```
00
11
```

So we can return **"00" or "11"**.

---

# 🔹 Key Idea (Diagonal Trick – Cantor's Diagonalization)

Since we have **n strings of length n**, we can build a new string by **flipping the diagonal bits**.

### Steps

For each index `i`:

* Look at `nums[i].charAt(i)`
* If it is `'0'` → put `'1'`
* If it is `'1'` → put `'0'`

This guarantees the new string differs from **every string in at least one position**.

Therefore it **cannot exist in the array**.

---

## 🔹 Example Walkthrough

```
nums = ["111","011","001"]
```

Look at diagonal characters:

```
nums[0][0] = '1'
nums[1][1] = '1'
nums[2][2] = '1'
```

Flip them:

```
1 → 0
1 → 0
1 → 0
```

Result:

```
"000"
```

This string is **not in nums**, so it is valid.

---

# 🔹 Java Solution (Optimal O(n))

```java
class Solution {
    public String findDifferentBinaryString(String[] nums) {
        int n = nums.length;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            char ch = nums[i].charAt(i);
            
            if (ch == '0') {
                result.append('1');
            } else {
                result.append('0');
            }
        }

        return result.toString();
    }
}
```

---

# 🔹 Complexity Analysis

| Complexity       | Value    |
| ---------------- | -------- |
| Time Complexity  | **O(n)** |
| Space Complexity | **O(n)** |

We only iterate once through the array.

---

# 🔹 Why This Works

The generated string differs from:

* `nums[0]` at index `0`
* `nums[1]` at index `1`
* `nums[2]` at index `2`
* ...

So it **cannot match any existing string**.

This is a classic **Cantor Diagonalization technique**.

---

# 🔹 Alternative Approach (Brute Force)

1. Generate all numbers from `0 → 2^n`
2. Convert to binary
3. Check if it exists in a `HashSet`

But this is slower **O(2ⁿ)**.

---

✅ **Best solution:** Diagonal trick (**O(n)**).

---

