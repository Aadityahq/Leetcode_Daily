# LeetCode 1385 – Find the Distance Value Between Two Arrays



Easy

Array

Brute Force



## 1. Understand the problem

You are given three things:

- `arr1` – First integer array.
- `arr2` – Second integer array.
- `d` – An integer representing the maximum allowed distance.

Our task: Count how many elements in `arr1` do not have even a single element in `arr2` whose absolute difference is less than or equal to `d`.

In simple words:

For every element of `arr1`, check all elements of `arr2`.

If the absolute difference between the two elements is less than or equal to `d`, that element of `arr1` is NOT counted.

Otherwise, count it.

### 2. Example walkthrough

Input:

```
arr1 = [4, 5, 8]
arr2 = [10, 9, 1, 8]
d = 2
```

Let's check each element of `arr1`.

Step 1: Check arr1[0] = 4

| arr2 element | Absolute difference | Condition |
| ------------ | ------------------- | --------- |
| 10           | \|4 - 10\| = 6      | 6 > 2     |
| 9            | \|4 - 9\| = 5       | 5 > 2     |
| 1            | \|4 - 1\| = 3       | 3 > 2     |
| 8            | \|4 - 8\| = 4       | 4 > 2     |

No element violates the condition.

Count 4

Step 2: Check arr1[1] = 5

| arr2 element | Absolute difference | Condition |
| ------------ | ------------------- | --------- |
| 10           | 5                   | 5 > 2     |
| 9            | 4                   | 4 > 2     |
| 1            | 4                   | 4 > 2     |
| 8            | 3                   | 3 > 2     |

Again, no element violates the condition.

Count 5

Step 3: Check arr1[2] = 8

| arr2 element | Absolute difference | Condition |
| ------------ | ------------------- | --------- |
| 10           | 2                   | 2  2      |

\| 8 | 0 | 0 Not counted

Final answer = 2

## 3. Java Solution – Brute Force Approach

This is the easiest approach to understand and implement.

```

class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {

        int count = 0;

        for (int i = 0; i < arr1.length; i++) {

            boolean valid = true;

            for (int j = 0; j < arr2.length; j++) {

                if (Math.abs(arr1[i] - arr2[j]) <= d) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                count++;
            }
        }

        return count;
    }
}

```

## 4. Understand the code step by step

Let's understand why we use each part.

Step 1: Create a counter

```
int count = 0;
```

We use `count` to store the number of valid elements from `arr1`.

Initially, it is zero.

Step 2: Outer loop

```
for (int i = 0; i < arr1.length; i++)
```

This loop visits every element of `arr1`.

For example:

```
arr1 = [4, 5, 8]
```

The outer loop checks:

- First iteration: 4
- Second iteration: 5
- Third iteration: 8

Step 3: Create a boolean variable

```
boolean valid = true;
```

We initially assume that the current element is valid.

If we find even one element in `arr2` that violates the condition, we change `valid` to `false`.

Step 4: Inner loop

```
for (int j = 0; j < arr2.length; j++)
```

This loop checks the current element of `arr1` against every element of `arr2`.

Step 5: Check the condition

```
if (Math.abs(arr1[i] - arr2[j]) <= d)
```

This is the most important condition.

Let's understand it:

Suppose:

```
arr1[i] = 4;
arr2[j] = 1;
d = 2;
```

Then:

```
Math.abs(4 - 1) = 3
```

Since `3 > 2`, the condition is false.

Now suppose:

```
arr1[i] = 8;
arr2[j] = 10;
```

Then:

```
Math.abs(8 - 10) = 2
```

Since `2 <= 2`, the condition is true.

This means the current element of `arr1` is invalid.

Step 6: Mark invalid and break

```
valid = false;
break;
```

Why do we use `break`?

Because we only need to find one element in `arr2` that makes the current `arr1[i]` invalid.

Once we find it, checking the remaining elements is unnecessary.

Step 7: Increase the counter

```
if (valid) {
    count++;
}
```

If the inner loop finishes and `valid` is still true, it means no element in `arr2` was within distance `d`.

Therefore, we increase the counter.

Step 8: Return the answer

```
return count;
```

Finally, return the total number of valid elements.

## 5. Dry Run

Input:

```
arr1 = [4, 5, 8]
arr2 = [10, 9, 1, 8]
d = 2
```

\| arr1[i] | Found difference Final Output: 2

## 6. Complexity Analysis

Time Complexity: O(n × m)

Where:

- `n` = length of `arr1`.
- `m` = length of `arr2`.

Why?

- Outer loop runs `n` times.
- Inner loop can run `m` times for each outer iteration.

Therefore:

\\[ O(n \times m) \\]

Space Complexity: O(1)

We only use two variables:

- `count`
- `valid`

No additional array or data structure is required.

## 7. Important concepts to remember

| Concept          | Why we use it                                  |
| ---------------- | ---------------------------------------------- |
| Nested loops     | Compare every element of both arrays           |
| `Math.abs()`     | Calculate absolute difference                  |
| Boolean variable | Track whether an element is valid              |
| `break`          | Stop checking after finding an invalid element |
| Counter          | Count valid elements                           |

## 8. Why is this approach correct?

The problem asks us to count an element only when every element in `arr2` has an absolute difference greater than `d`.

Our code does exactly that:

1. Select one element from `arr1`.
2. Compare it with all elements of `arr2`.
3. If even one difference is less than or equal to `d`, reject that element.
4. Otherwise, increase the count.

Thus, we count exactly the elements required by the problem.

My recommendation: Understand this brute-force approach first. It is optimal enough for the given constraints (`arr1.length` and `arr2.length` are at most 500). You don't need sorting or binary search for this problem.