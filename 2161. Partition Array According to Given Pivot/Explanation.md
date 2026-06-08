## Problem Explanation

You are given:

* An array `nums`
* A value `pivot`

You need to rearrange the array such that:

1. All elements **smaller than pivot** come first.
2. All elements **equal to pivot** come next.
3. All elements **greater than pivot** come last.
4. The **relative order** of elements less than pivot and greater than pivot must remain the same (**stable partition**).

---

### Example

```text
nums = [9,12,5,10,14,3,10]
pivot = 10
```

Separate the elements into three groups:

```text
Less than 10:    [9,5,3]
Equal to 10:     [10,10]
Greater than 10: [12,14]
```

Combine them:

```text
[9,5,3,10,10,12,14]
```

Output:

```text
[9,5,3,10,10,12,14]
```

---

## Key Observation

We must preserve the order of:

* Elements less than pivot
* Elements greater than pivot

This means we cannot simply sort the array.

The easiest approach is:

1. Traverse the array.
2. Store elements in three separate lists:

   * smaller
   * equal
   * greater
3. Merge the three lists into the answer.

---

## Why This Works

Suppose:

```text
nums = [7,3,8,2,9]
pivot = 8
```

During traversal:

```text
smaller = [7,3,2]
equal   = [8]
greater = [9]
```

Notice that:

```text
7 came before 3 came before 2
```

Their order is preserved because we append them in the order we encounter them.

Similarly for greater elements.

Thus the stability requirement is satisfied.

---

## Algorithm

1. Create three lists:

   * less
   * equal
   * greater

2. Traverse `nums`:

   * If `num < pivot`, put into `less`
   * If `num == pivot`, put into `equal`
   * Else put into `greater`

3. Create answer array.

4. Copy all elements from:

   * `less`
   * `equal`
   * `greater`

5. Return answer.

---

## Dry Run

```text
nums = [9,12,5,10,14,3,10]
pivot = 10
```

After traversal:

```text
less    = [9,5,3]
equal   = [10,10]
greater = [12,14]
```

Build answer:

```text
ans = [9,5,3,10,10,12,14]
```

Return answer.

---

## Java Solution

```java
import java.util.*;

class Solution {
    public int[] pivotArray(int[] nums, int pivot) {
        List<Integer> less = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> greater = new ArrayList<>();

        for (int num : nums) {
            if (num < pivot) {
                less.add(num);
            } else if (num == pivot) {
                equal.add(num);
            } else {
                greater.add(num);
            }
        }

        int[] ans = new int[nums.length];
        int idx = 0;

        for (int num : less) {
            ans[idx++] = num;
        }

        for (int num : equal) {
            ans[idx++] = num;
        }

        for (int num : greater) {
            ans[idx++] = num;
        }

        return ans;
    }
}
```

---

## Complexity Analysis

### Time Complexity

We traverse the array once and then copy elements once:

```text
O(n)
```

### Space Complexity

Three lists store all elements:

```text
O(n)
```

where `n` is the length of the array.

---

## Interview Insight

The important keyword in this problem is **"relative order is maintained"**.

Whenever you see:

```text
Maintain relative order
```

think of a **stable partition**.

A common and simple way to achieve stability is:

```text
Less bucket
Equal bucket
Greater bucket
```

and then concatenate them, giving an optimal **O(n)** solution.
