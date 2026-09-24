## 3550. Smallest Index With Digit Sum Equal to Index

### 1. What is the problem asking?

We are given an integer array `nums`.

For every index `i`, we need to:

1. Take the number `nums[i]`.
2. Calculate the **sum of its digits**.
3. Check whether the digit sum is equal to the index `i`.
4. Return the **smallest index** where this condition is true.
5. If no index satisfies it, return `-1`.

### Example

```text
nums = [1, 10, 11]
```

Let's check each index:

```text
i = 0
nums[0] = 1
digit sum = 1
1 != 0 ❌

i = 1
nums[1] = 10
digit sum = 1 + 0 = 1
1 == 1 ✅
```

So we immediately return:

```text
1
```

We don't need to check the remaining elements because the problem asks for the **smallest index**.

---

# 2. Main idea

The important part is calculating the **sum of digits**.

For example:

```text
123
```

We need:

```text
1 + 2 + 3 = 6
```

We can extract digits from the **right side** using:

```java
num % 10
```

### Why `% 10`?

The remainder when a number is divided by `10` gives its **last digit**.

For example:

```text
123 % 10 = 3
45 % 10 = 5
7 % 10 = 7
```

So:

```java
int digit = num % 10;
```

gets the last digit.

---

# 3. Why divide by 10?

After taking the last digit, we need to remove it.

We use:

```java
num = num / 10;
```

Because integer division removes the decimal part.

For example:

```text
123 / 10 = 12
12 / 10 = 1
1 / 10 = 0
```

So for `123`:

```text
123
 ↓ %10 → 3
12
 ↓ %10 → 2
1
 ↓ %10 → 1
0
```

We have extracted:

```text
3 + 2 + 1 = 6
```

This is the same idea you were asking about earlier with digit extraction.

---

# 4. Understanding the code

```java
class Solution {
    public int smallestIndex(int[] nums) {
        int n = nums.length;
```

`n` stores the number of elements in the array.

For example:

```text
nums = [1, 10, 11]
n = 3
```

---

### Loop through every index

```java
for(int i = 0; i < n; i++) {
```

We start from index `0` and move forward:

```text
0 → 1 → 2 → 3 → ...
```

Why do we start from `0`?

Because array indexing starts from `0`.

Also, because we check indices from left to right, **the first matching index is automatically the smallest index**.

---

### Create digit sum

```java
int sum = 0;
```

Initially, the digit sum is zero.

For example, for `123`:

```text
sum = 0
```

Then we gradually add:

```text
3 → sum = 3
2 → sum = 5
1 → sum = 6
```

---

# 5. Extract every digit

```java
while(nums[i] != 0) {
```

We keep extracting digits until the number becomes `0`.

Suppose:

```text
nums[i] = 123
```

### First iteration

```java
int digit = nums[i] % 10;
```

```text
123 % 10 = 3
```

So:

```text
digit = 3
```

Then:

```java
sum = sum + digit;
```

```text
sum = 0 + 3
    = 3
```

Then:

```java
nums[i] = nums[i] / 10;
```

```text
123 / 10 = 12
```

---

### Second iteration

```text
12 % 10 = 2
```

So:

```text
sum = 3 + 2 = 5
```

Then:

```text
12 / 10 = 1
```

---

### Third iteration

```text
1 % 10 = 1
```

So:

```text
sum = 5 + 1 = 6
```

Then:

```text
1 / 10 = 0
```

Now the loop stops.

Therefore:

```text
digit sum of 123 = 6
```

---

# 6. Check the condition

After calculating the digit sum:

```java
if(sum == i) {
    return i;
}
```

We compare:

```text
digit sum == index
```

For example:

```text
i = 2
nums[i] = 2

digit sum = 2

2 == 2
```

Therefore:

```java
return 2;
```

---

# 7. Why can we return immediately?

This is very important.

The problem asks for the **smallest index**.

We are checking indices in increasing order:

```text
0 → 1 → 2 → 3 → ...
```

Suppose:

```text
index 1 → doesn't match
index 2 → matches ✅
index 3 → matches
```

We don't care about index `3`, because `2` is smaller.

Therefore, as soon as we find a match:

```java
return i;
```

is correct.

---

# 8. What happens if nothing matches?

After the loop finishes:

```java
return -1;
```

This means:

> No index was found whose digit sum is equal to the index.

For:

```text
nums = [1, 2, 3]
```

Check:

```text
index 0 → digit sum 1 → 1 != 0
index 1 → digit sum 2 → 2 != 1
index 2 → digit sum 3 → 3 != 2
```

Nothing matches.

Therefore:

```text
-1
```

---

# 9. Complete dry run

Consider:

```text
nums = [1, 10, 11]
```

### Index 0

```text
i = 0
nums[0] = 1

1 % 10 = 1
sum = 1

1 / 10 = 0
```

Check:

```text
sum == i
1 == 0 ❌
```

Continue.

---

### Index 1

```text
i = 1
nums[1] = 10
```

Extract digits:

```text
10 % 10 = 0
sum = 0

10 / 10 = 1

1 % 10 = 1
sum = 1

1 / 10 = 0
```

Now:

```text
sum = 1
i = 1
```

Therefore:

```text
1 == 1 ✅
```

Return:

```text
1
```

---

# 10. One important issue in your code

Your solution is logically correct, but there is one thing I would improve.

You are directly modifying the array:

```java
nums[i] = nums[i] / 10;
```

For example:

```text
nums = [1, 10, 11]
```

After processing `10`, `nums[1]` becomes `0`.

So the original array gets changed.

A better approach is to create a temporary variable:

```java
int num = nums[i];
```

Then modify `num` instead.

### Improved solution

```java
class Solution {
    public int smallestIndex(int[] nums) {

        for (int i = 0; i < nums.length; i++) {

            int num = nums[i];
            int sum = 0;

            while (num != 0) {
                int digit = num % 10;
                sum += digit;
                num /= 10;
            }

            if (sum == i) {
                return i;
            }
        }

        return -1;
    }
}
```

### Why is this better?

Instead of changing:

```java
nums[i]
```

we change:

```java
num
```

So the original array remains unchanged.

---

## 11. What about `nums[i] = 0`?

The constraints allow:

```text
0 <= nums[i] <= 1000
```

If:

```text
nums[i] = 0
```

then:

```java
while (num != 0)
```

doesn't execute.

Therefore:

```text
sum = 0
```

Then we check:

```text
sum == i
```

So if the number is `0` at index `0`:

```text
nums = [0]
i = 0
digit sum = 0

0 == 0 ✅
```

Answer:

```text
0
```

---

# 12. Complexity

Let:

* `n` = number of elements
* `d` = number of digits in each number

We visit every array element:

```text
O(n)
```

For each number, we process its digits:

```text
O(d)
```

So overall:

```text
Time: O(n × d)
```

Since `nums[i] <= 1000`, there are at most 4 digits, so practically this is very close to:

```text
O(n)
```

Space:

```text
O(1)
```

because we only use a few variables.

---

## The key things to remember

For digit problems, remember these two operations:

```java
num % 10
```

👉 **Get the last digit**

and

```java
num / 10
```

👉 **Remove the last digit**

So the standard pattern is:

```java
while (num != 0) {
    int digit = num % 10;  // get last digit
    sum += digit;           // use the digit
    num /= 10;              // remove last digit
}
```

Once you understand this pattern, many digit-based LeetCode problems become much easier.
