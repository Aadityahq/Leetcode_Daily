# 2540. Minimum Common Value

## Problem Explanation

You are given **two sorted arrays** `nums1` and `nums2`.

Your task is to find the **smallest number that appears in both arrays**.

If no common number exists, return `-1`.

---

### Example

```text
nums1 = [1,2,3]
nums2 = [2,4]
```

Common element = `2`

So output is:

```text
2
```

---

# Key Observation

Both arrays are already **sorted in non-decreasing order**.

Because of sorting, we can use the **Two Pointer Technique** efficiently.

---

# Why Two Pointers?

Suppose:

```text
nums1[i] < nums2[j]
```

Then `nums1[i]` can never be the answer because:

* arrays are sorted
* all next elements in `nums2` will be even bigger

So we move `i` forward.

Similarly:

```text
nums1[i] > nums2[j]
```

Move `j` forward.

If both become equal:

```text
nums1[i] == nums2[j]
```

We found the smallest common element immediately.

---

# Step-by-Step Dry Run

## Input

```text
nums1 = [1,2,3,6]
nums2 = [2,3,4,5]
```

Start:

```text
i = 0 → 1
j = 0 → 2
```

### Compare

```text
1 < 2
```

Move `i`

---

```text
i = 1 → 2
j = 0 → 2
```

Now:

```text
2 == 2
```

Found common value.

Return:

```text
2
```

---

# Java Solution

```java
class Solution {
    public int getCommon(int[] nums1, int[] nums2) {
        int i = 0;
        int j = 0;

        while (i < nums1.length && j < nums2.length) {

            // Common element found
            if (nums1[i] == nums2[j]) {
                return nums1[i];
            }

            // Move pointer of smaller element
            if (nums1[i] < nums2[j]) {
                i++;
            } else {
                j++;
            }
        }

        // No common element
        return -1;
    }
}
```

---

# How This Works

We compare elements from both arrays:

* If equal → answer found
* Smaller element pointer moves ahead
* Continue until one array ends

Because arrays are sorted, this guarantees the **first common element found is the minimum common element**.

---

# Time Complexity

## Time

```text
O(n + m)
```

Where:

* `n = nums1.length`
* `m = nums2.length`

Each pointer moves at most once through its array.

---

## Space

```text
O(1)
```

No extra space used.

---

# Why This Is Optimal

A brute-force solution would compare every pair:

```text
O(n × m)
```

Too slow for `10^5` constraints.

Using sorting property + two pointers gives:

```text
O(n + m)
```

which is optimal for this problem.
