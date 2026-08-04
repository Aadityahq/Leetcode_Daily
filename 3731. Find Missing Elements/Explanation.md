This is a straightforward **HashSet** problem. Let's understand it step by step.

---

# Problem Explanation

You are given an array of **unique** integers.

For example,

```text
nums = [1,4,2,5]
```

The smallest number is

```text
1
```

The largest number is

```text
5
```

So originally the array should have contained every number from **1 to 5**.

Original Range:

```text
1 2 3 4 5
```

Current array:

```text
1 2 4 5
```

The missing number is

```text
3
```

Return

```text
[3]
```

---

## Example 2

```text
nums = [7,8,6,9]
```

Minimum = 6

Maximum = 9

Complete range

```text
6 7 8 9
```

Everything exists.

Answer

```text
[]
```

---

## Example 3

```text
nums = [5,1]
```

Minimum = 1

Maximum = 5

Complete range

```text
1 2 3 4 5
```

Existing numbers

```text
1 5
```

Missing

```text
2 3 4
```

Return

```text
[2,3,4]
```

---

# Observation

We only need to know

* smallest element
* largest element
* whether each number between them exists

The easiest data structure for checking existence is a **HashSet**.

Because

```java
set.contains(x)
```

takes **O(1)** average time.

---

# Approach

### Step 1

Find

```text
minimum element
maximum element
```

For example

```text
nums = [1,4,2,5]

min = 1
max = 5
```

---

### Step 2

Store every element in a HashSet.

```
Set

1
2
4
5
```

Now checking

```text
Does 3 exist?
```

is

```java
set.contains(3)
```

which is very fast.

---

### Step 3

Loop from

```text
min + 1
```

to

```text
max - 1
```

Why not include min and max?

Because the problem guarantees they are present.

So only middle numbers can be missing.

For each number

```text
if not in set
```

add it to answer.

---

# Dry Run

Input

```text
nums = [1,4,2,5]
```

### Find min & max

```text
min = 1
max = 5
```

HashSet

```text
{1,2,4,5}
```

Loop

```
i = 2
exists ✓

i = 3
missing ✓
answer = [3]

i = 4
exists ✓
```

Return

```
[3]
```

---

# Java Solution

```java
class Solution {
    public List<Integer> findMissingAndRepeatedValues(int[] nums) {
        Set<Integer> set = new HashSet<>();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num : nums) {
            set.add(num);
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        List<Integer> ans = new ArrayList<>();

        for (int i = min + 1; i < max; i++) {
            if (!set.contains(i)) {
                ans.add(i);
            }
        }

        return ans;
    }
}
```

> **Note:** The method name above is incorrect for this problem. On LeetCode 3731, use the correct method name shown below.

### Correct LeetCode Method

```java
class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        Set<Integer> set = new HashSet<>();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int num : nums) {
            set.add(num);
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        List<Integer> ans = new ArrayList<>();

        for (int i = min + 1; i < max; i++) {
            if (!set.contains(i)) {
                ans.add(i);
            }
        }

        return ans;
    }
}
```

---

# Why does this work?

Suppose

```
Range = 1 to 8
```

Every number should exist.

The HashSet tells us instantly whether a number is present.

If

```
set.contains(i)
```

is false,

then that number must be missing.

Since we check every number in increasing order,

the answer is automatically sorted.

---

# Time Complexity

Let

```
n = nums.length
```

Finding min, max, and building the HashSet:

```
O(n)
```

Checking every number in the range:

```
O(max − min)
```

Since the constraints are:

```
1 <= nums[i] <= 100
```

the range is at most 100 numbers.

Overall:

```
O(n + (max − min))
```

With these constraints, this is effectively **O(n)**.

---

# Space Complexity

HashSet stores all numbers:

```
O(n)
```

Answer list stores only the missing numbers.

---

## Intuition to Remember

Whenever a problem asks:

* "Find missing numbers"
* "Check whether a number exists"
* "Frequent membership checks"

think of a **HashSet**, because it provides **O(1)** average-time lookups, making the solution both simple and efficient.
