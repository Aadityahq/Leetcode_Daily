## Understanding the Problem

We are given an array `nums`.

An array is called **good** if it is a permutation of:

[
base[n] = [1,2,3,\dots,n-1,n,n]
]

That means:

* Numbers from `1` to `n-1` appear exactly **once**
* Number `n` appears exactly **twice**
* Length of the array should be `n + 1`

---

### Example

For `n = 3`

[
base[3] = [1,2,3,3]
]

Possible permutations:

* `[1,2,3,3]`
* `[3,1,3,2]`
* `[2,3,1,3]`

All are valid.

---

# Key Observation

If the array is good:

* The **maximum element** must be `n`
* Array size must be `n + 1`

So:

```java
n = maximum element
```

Then we check:

1. Is array length equal to `n + 1`?
2. Does every number `1` to `n-1` appear once?
3. Does `n` appear twice?

---

# Approach

## Step 1:

Sort the array.

After sorting, a valid good array should look like:

```text
[1,2,3,...,n-1,n,n]
```

---

## Step 2:

Check every position.

For array size `m`:

* First `m-2` elements should be:

```text
1,2,3,...,m-1
```

* Last two elements should both be `m-1`

Because:

```text
m = n + 1
=> n = m - 1
```

---

# Dry Run

## Input

```java
nums = [1,3,3,2]
```

### After sorting

```java
[1,2,3,3]
```

Length = 4

So:

```text
n = 3
```

Check:

* nums[0] = 1 ✅
* nums[1] = 2 ✅
* nums[2] = 3 ✅
* nums[3] = 3 ✅

Return `true`

---

# Java Solution

```java
import java.util.Arrays;

class Solution {
    public boolean isGood(int[] nums) {

        Arrays.sort(nums);

        int n = nums.length;

        // Check first n-1 elements
        for (int i = 0; i < n - 1; i++) {

            // Expected value should be i+1
            if (nums[i] != i + 1) {
                return false;
            }
        }

        // Last element should also be n-1
        return nums[n - 1] == n - 1;
    }
}
```

---

# How This Works

Suppose:

```java
nums = [1,2,3,3]
```

Length = 4

So expected good array is:

```text
[1,2,3,3]
```

Loop checks:

| Index | Expected | Actual |
| ----- | -------- | ------ |
| 0     | 1        | 1      |
| 1     | 2        | 2      |
| 2     | 3        | 3      |

Then final check:

```java
nums[3] == 3
```

True ✅

---

# Why `n - 1`?

If array length is:

```text
length = 4
```

Then:

```text
base[n] has length n+1
```

So:

```text
n = 3
```

Thus valid array must contain:

```text
1,2,3,3
```

That is why last number should be:

```java
n - 1
```

because `n` in code is actually the array length.

---

# Time Complexity

Sorting takes:

[
O(n \log n)
]

Loop takes:

[
O(n)
]

Total:

[
O(n \log n)
]

---

# Space Complexity

[
O(1)
]

(ignoring sorting space)
