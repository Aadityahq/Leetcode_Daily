## Intuition

For every index `i`, we need:

* `leftSum[i]` = sum of all elements before `i`
* `rightSum[i]` = sum of all elements after `i`
* `answer[i] = |leftSum[i] - rightSum[i]|`

A brute-force approach would calculate the left and right sums for every index separately, which would take **O(n²)** time.

Instead, we can use a smarter approach:

1. Compute the **total sum** of the array.
2. Maintain a running `leftSum`.
3. At index `i`:

   * Remove `nums[i]` from the remaining total to get `rightSum`.
   * Calculate the absolute difference.
   * Add `nums[i]` to `leftSum` for the next iteration.

This allows us to find both sums in **O(1)** per index.

---

## Example

### Input

```text
nums = [10,4,8,3]
```

### Step 1: Total Sum

```text
totalSum = 10 + 4 + 8 + 3 = 25
```

### Iteration

| i | nums[i] | leftSum | rightSum   | abs(left-right) |
| - | ------- | ------- | ---------- | --------------- |
| 0 | 10      | 0       | 25-10 = 15 | 15              |
| 1 | 4       | 10      | 15-4 = 11  | 1               |
| 2 | 8       | 14      | 11-8 = 3   | 11              |
| 3 | 3       | 22      | 3-3 = 0    | 22              |

Result:

```text
[15,1,11,22]
```

---

## Algorithm

1. Find the total sum of the array.
2. Initialize `leftSum = 0`.
3. Traverse the array:

   * Subtract current element from `totalSum`.
   * Now `totalSum` represents `rightSum`.
   * Store `abs(leftSum - totalSum)`.
   * Add current element to `leftSum`.
4. Return the answer array.

---

## Why Does This Work?

At any index `i`:

Before processing:

```text
leftSum = sum of elements before i
totalSum = sum of elements from i to n-1
```

After:

```java
totalSum -= nums[i];
```

`totalSum` becomes:

```text
sum of elements after i
```

which is exactly `rightSum`.

Now we have both:

```text
leftSum
rightSum = totalSum
```

So:

```java
answer[i] = Math.abs(leftSum - totalSum);
```

Afterward:

```java
leftSum += nums[i];
```

prepares `leftSum` for the next index.

---

## Java Solution

```java
class Solution {
    public int[] leftRightDifference(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        int leftSum = 0;

        for (int i = 0; i < n; i++) {
            totalSum -= nums[i]; // right sum

            answer[i] = Math.abs(leftSum - totalSum);

            leftSum += nums[i];
        }

        return answer;
    }
}
```

---

## Dry Run

### Input

```text
nums = [10,4,8,3]
```

Initial:

```text
totalSum = 25
leftSum = 0
```

### i = 0

```text
totalSum = 25 - 10 = 15
answer[0] = |0 - 15| = 15
leftSum = 0 + 10 = 10
```

### i = 1

```text
totalSum = 15 - 4 = 11
answer[1] = |10 - 11| = 1
leftSum = 10 + 4 = 14
```

### i = 2

```text
totalSum = 11 - 8 = 3
answer[2] = |14 - 3| = 11
leftSum = 14 + 8 = 22
```

### i = 3

```text
totalSum = 3 - 3 = 0
answer[3] = |22 - 0| = 22
leftSum = 25
```

Output:

```text
[15,1,11,22]
```

---

## Complexity Analysis

* **Time Complexity:** `O(n)`

  * One pass to calculate total sum.
  * One pass to build the answer.

* **Space Complexity:** `O(1)` extra space

  * (Ignoring the output array, which is required by the problem.)
