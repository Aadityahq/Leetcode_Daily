# LeetCode 3942: Minimum Operations to Sort a Permutation

**Difficulty:** Medium  
**Language:** Java  
**Topics:** Array, Math, Permutation

## 1. Problem Explanation

We are given an array `nums` of length `n`, containing a permutation of integers from `0` to `n - 1`.

We can perform only two operations:

1. **Reverse the entire array:** Reverse the order of all elements.
2. **Rotate Left by One:** Move the first element to the end and shift the remaining elements one position to the left.

Our task is to find the minimum number of operations required to sort the array in increasing order.

If it is impossible to sort the array using these operations, return `-1`.

### Example

**Input:** `nums = [0, 2, 1]`

**Output:** `2`

**Explanation:**

1. Rotate left: `[2, 1, 0]`
2. Reverse the array: `[0, 1, 2]`

Therefore, the minimum number of operations is `2`.

## 2. Approach and Intuition

The key observation is that rotating an array changes the starting position but preserves its circular order. Reversing the array changes the order from increasing to decreasing.

Therefore, the array can be sorted only if it is one of these two forms:

- A rotation of `[0, 1, 2, ..., n - 1]`.
- A rotation of `[n - 1, n - 2, ..., 0]`.

We check both possibilities and calculate the minimum operations for each valid case.

If neither pattern matches, we return `-1`.

## 3. Java Solution

```java
class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        int zeroIndex = -1;
        int maxIndex = -1;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zeroIndex = i;
            }
            if (nums[i] == n - 1) {
                maxIndex = i;
            }
        }

        int answer = Integer.MAX_VALUE;

        // Case 1: Increasing rotation
        boolean increasingRotation = true;

        for (int i = 0; i < n; i++) {
            int expected = (nums[0] + i) % n;

            if (nums[i] != expected) {
                increasingRotation = false;
                break;
            }
        }

        if (increasingRotation) {
            answer = Math.min(
                answer,
                Math.min(zeroIndex, n - zeroIndex + 2)
            );
        }

        // Case 2: Decreasing rotation
        boolean decreasingRotation = true;

        for (int i = 0; i < n; i++) {
            int expected = (nums[0] - i + n) % n;

            if (nums[i] != expected) {
                decreasingRotation = false;
                break;
            }
        }

        if (decreasingRotation) {
            int p = (n - maxIndex) % n;

            answer = Math.min(
                answer,
                1 + Math.min(p, n - p)
            );
        }

        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
}
```

## 4. Step-by-Step Explanation

### Step 1: Find the positions of `0` and `n - 1`

```java
int zeroIndex = -1;
int maxIndex = -1;

for (int i = 0; i < n; i++) {
    if (nums[i] == 0) {
        zeroIndex = i;
    }
    if (nums[i] == n - 1) {
        maxIndex = i;
    }
}
```

We find the positions of `0` and the largest element, `n - 1`.

**Why?**

These positions help us calculate the number of rotations required to reach the sorted array.

### Step 2: Check for an increasing rotation

```java
int expected = (nums[0] + i) % n;
```

This formula calculates the expected element at each index.

For example, if `n = 5` and the first element is `2`, the expected sequence is:

`[2, 3, 4, 0, 1]`

The modulo operator `% n` makes the sequence wrap back to `0` after reaching `n - 1`.

If every element matches the expected value, the array is an increasing rotation.

**Calculate the operations:**

```java
Math.min(zeroIndex, n - zeroIndex + 2)
```

We compare two possible operation counts:

- `zeroIndex`: Rotate left until `0` reaches the beginning.
- `n - zeroIndex + 2`: The alternative route using reversal and rotations.

We choose the smaller value.

### Step 3: Check for a decreasing rotation

```java
int expected = (nums[0] - i + n) % n;
```

This formula checks whether the array follows a circularly decreasing sequence.

For example, when `n = 5`, a decreasing rotation can be:

`[2, 1, 0, 4, 3]`

We add `n` before applying modulo to avoid negative values.

If every element matches, the array is a decreasing rotation.

**Calculate the operations:**

```java
int p = (n - maxIndex) % n;

answer = Math.min(answer, 1 + Math.min(p, n - p));
```

We first reverse the array, which costs one operation. Then we calculate the minimum rotations required to sort it.

- `p` represents one possible rotation count.
- `n - p` represents the alternative count.
- `Math.min(p, n - p)` selects the smaller count.
- `1` accounts for the reversal.

### Step 4: Return the answer

```java
return answer == Integer.MAX_VALUE ? -1 : answer;
```

Initially, `answer` is set to `Integer.MAX_VALUE`.

If either pattern matches, we update the answer with the minimum operation count.

If neither pattern matches, the answer remains unchanged, so we return `-1`.

## 5. Dry Run

**Input:** `nums = [0, 2, 1]`

Here, `n = 3`.

**Step 1: Find positions**

- `zeroIndex = 0`
- `maxIndex = 1`

**Step 2: Check increasing rotation**

The expected sequence starts with `0`:

`[0, 1, 2]`

But the actual array is `[0, 2, 1]`.

Therefore, it is not an increasing rotation.

**Step 3: Check decreasing rotation**

Expected values:

- Index `0`: `(0 - 0 + 3) % 3 = 0`
- Index `1`: `(0 - 1 + 3) % 3 = 2`
- Index `2`: `(0 - 2 + 3) % 3 = 1`

All elements match, so it is a decreasing rotation.

Calculate:

- `p = (3 - 1) % 3 = 2`
- Operations = `1 + min(2, 3 - 2)`
- Operations = `1 + 1 = 2`

**Output:** `2`

## 6. Complexity Analysis

- **Time Complexity:** `O(n)` because we traverse the array a constant number of times.
- **Space Complexity:** `O(1)` because we use only a fixed number of variables.

## 7. Key Takeaway

Instead of trying every possible sequence of operations, we use the fact that reversal and rotation preserve either circular increasing or circular decreasing order.

We check both patterns, calculate their minimum operation counts, and return the smallest valid answer.

This solution runs in `O(n)` time and uses `O(1)` extra space, making it efficient for `n` up to `100000`.