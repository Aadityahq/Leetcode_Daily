i# 3069. Distribute Elements Into Two Arrays I

## Understanding the Problem

We are given an array `nums`. We need to distribute its elements into two arrays:

* `arr1`
* `arr2`

### First two operations

* Put `nums[0]` into `arr1`.
* Put `nums[1]` into `arr2`.

After that, for every remaining element:

* If the **last element of `arr1`** is greater than the **last element of `arr2`**, put the current number into `arr1`.
* Otherwise, put it into `arr2`.

Finally, concatenate `arr1` and `arr2`.

---

## Example

```text
nums = [5, 4, 3, 8]
```

Initially:

```text
arr1 = [5]
arr2 = [4]
```

### Process `3`

Last element of `arr1` = `5`
Last element of `arr2` = `4`

Since:

```text
5 > 4
```

Put `3` into `arr1`.

```text
arr1 = [5, 3]
arr2 = [4]
```

### Process `8`

Last element of `arr1` = `3`
Last element of `arr2` = `4`

Since:

```text
3 > 4  → false
```

Put `8` into `arr2`.

```text
arr1 = [5, 3]
arr2 = [4, 8]
```

Now concatenate them:

```text
result = [5, 3, 4, 8]
```

---

# Java Solution

```java
import java.util.*;

class Solution {
    public int[] resultArray(int[] nums) {
        List<Integer> arr1 = new ArrayList<>();
        List<Integer> arr2 = new ArrayList<>();

        // First two elements
        arr1.add(nums[0]);
        arr2.add(nums[1]);

        // Process remaining elements
        for (int i = 2; i < nums.length; i++) {
            int last1 = arr1.get(arr1.size() - 1);
            int last2 = arr2.get(arr2.size() - 1);

            if (last1 > last2) {
                arr1.add(nums[i]);
            } else {
                arr2.add(nums[i]);
            }
        }

        // Create result array
        int[] result = new int[nums.length];
        int index = 0;

        // Add arr1 elements
        for (int num : arr1) {
            result[index++] = num;
        }

        // Add arr2 elements
        for (int num : arr2) {
            result[index++] = num;
        }

        return result;
    }
}
```

# How the Solution Works

We use two `ArrayList`s because their sizes can change dynamically.

```java
List<Integer> arr1 = new ArrayList<>();
List<Integer> arr2 = new ArrayList<>();
```

### Step 1: Add the first two elements

```java
arr1.add(nums[0]);
arr2.add(nums[1]);
```

This is required by the problem statement.

---

### Step 2: Traverse from the third element

```java
for (int i = 2; i < nums.length; i++)
```

For every current element, find the last elements:

```java
int last1 = arr1.get(arr1.size() - 1);
int last2 = arr2.get(arr2.size() - 1);
```

Then compare them:

```java
if (last1 > last2) {
    arr1.add(nums[i]);
} else {
    arr2.add(nums[i]);
}
```

### Why do we use `size() - 1`?

Array indices start from `0`.

For example:

```text
arr1 = [5, 3, 7]
size = 3

Indices:
5 → 0
3 → 1
7 → 2
```

The last index is:

```text
size - 1 = 3 - 1 = 2
```

So:

```java
arr1.get(arr1.size() - 1)
```

gives us the last element.

---

### Step 3: Concatenate both arrays

The problem wants:

```text
result = arr1 + arr2
```

So we first copy all elements from `arr1`:

```java
for (int num : arr1) {
    result[index++] = num;
}
```

Then all elements from `arr2`:

```java
for (int num : arr2) {
    result[index++] = num;
}
```

---

# Dry Run

```text
nums = [2, 1, 3]
```

Initially:

```text
arr1 = [2]
arr2 = [1]
```

Process `3`:

```text
last(arr1) = 2
last(arr2) = 1

2 > 1 → true
```

Therefore:

```text
arr1 = [2, 3]
arr2 = [1]
```

Concatenate:

```text
[2, 3] + [1]
```

Result:

```text
[2, 3, 1]
```

---

# Complexity Analysis

### Time Complexity

```text
O(n)
```

We process every element once.

### Space Complexity

```text
O(n)
```

We use `arr1`, `arr2`, and the result array to store the elements.

---

## Main Idea to Remember

This problem is simply a **simulation problem**.

You do exactly what the problem says:

1. Put the first number in `arr1`.
2. Put the second number in `arr2`.
3. Compare the last elements of both arrays.
4. Add the current number according to the comparison.
5. Concatenate both arrays.

There is no complex algorithm required because the constraints are small and the distribution rule is directly given.
