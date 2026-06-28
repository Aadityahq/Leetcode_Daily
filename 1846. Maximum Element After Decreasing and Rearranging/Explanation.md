# 1846. Maximum Element After Decreasing and Rearranging

## Intuition

We can:

* Rearrange the array in any order.
* Decrease any element to any smaller positive value.

Since we want the **maximum possible largest element**, we should try to increase the values **gradually**.

The best strategy is:

1. Sort the array.
2. Make the first element `1`.
3. For every next element:

   * It can be **at most previous + 1**, otherwise the adjacent difference becomes greater than `1`.
   * If the current number is already small enough, keep it.
   * Otherwise decrease it to `previous + 1`.

---

## Key Observation

After sorting:

* The smallest element should always become `1`.
* Suppose the previous adjusted value is `prev`.
* The current element can become

```text
min(original value, prev + 1)
```

Why?

* We cannot increase numbers.
* We can only decrease them.
* To maximize the final answer, we should make every element as large as possible while satisfying the condition.

---

## Example

### Input

```text
arr = [100,1,1000]
```

### Step 1: Sort

```text
[1,100,1000]
```

### Step 2: First element = 1

```text
[1,100,1000]
```

Current maximum = 1

### Step 3: Process remaining elements

Second element

```text
min(100, 1+1)
= 2
```

Array becomes

```text
[1,2,1000]
```

Third element

```text
min(1000,2+1)
=3
```

Final array

```text
[1,2,3]
```

Answer = **3**

---

## Another Example

```text
arr = [2,2,1,2,1]
```

Sort

```text
[1,1,2,2,2]
```

First element

```text
1
```

Second

```text
min(1,2)=1
```

Third

```text
min(2,2)=2
```

Fourth

```text
min(2,3)=2
```

Fifth

```text
min(2,3)=2
```

Final

```text
[1,1,2,2,2]
```

Maximum = **2**

---

# Algorithm

1. Sort the array.
2. Set first element to `1`.
3. Traverse from left to right.
4. Update

```java
arr[i] = Math.min(arr[i], arr[i-1] + 1);
```

5. Return the last element.

---

# Why does this work?

Suppose the previous adjusted value is

```text
prev
```

The next value cannot exceed

```text
prev + 1
```

Otherwise

```text
abs(current - prev) > 1
```

which violates the condition.

If the original value is smaller, we keep it because we cannot increase numbers.

Thus

```text
current = min(original, prev + 1)
```

gives the **largest valid value**.

Since every element is made as large as possible, the final maximum element is also as large as possible.

---

# Java Solution

```java
class Solution {
    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
        Arrays.sort(arr);

        arr[0] = 1;

        for (int i = 1; i < arr.length; i++) {
            arr[i] = Math.min(arr[i], arr[i - 1] + 1);
        }

        return arr[arr.length - 1];
    }
}
```

---

# Dry Run

Input

```text
arr = [2,5,10]
```

Sort

```text
[2,5,10]
```

Make first element

```text
[1,5,10]
```

i = 1

```text
min(5,2)=2
```

Array

```text
[1,2,10]
```

i = 2

```text
min(10,3)=3
```

Array

```text
[1,2,3]
```

Answer

```text
3
```

---

# Correctness Proof

We prove that the algorithm always returns the maximum possible element.

### Base Case

The first element must be `1` according to the problem statement.

Our algorithm sets it to `1`, satisfying the condition.

### Inductive Step

Assume the first `i-1` elements have been adjusted optimally.

For element `i`:

* It cannot be greater than `arr[i-1] + 1`, otherwise adjacent difference exceeds `1`.
* It also cannot exceed its original value since increasing is not allowed.

Therefore, the largest valid value is

```text
min(original value, previous + 1)
```

Our algorithm assigns exactly this value.

Hence each prefix is optimal.

By induction, every element is maximized while maintaining validity, so the final (largest) element is also maximized.

Thus the algorithm is correct.

---

# Complexity Analysis

* Sorting: **O(n log n)**
* Single traversal: **O(n)**
* Extra Space: **O(1)** (excluding the sorting algorithm's internal space)

**Overall Time Complexity:** **O(n log n)**

**Overall Space Complexity:** **O(1)**
