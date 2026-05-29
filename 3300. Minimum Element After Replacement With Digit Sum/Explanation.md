# Problem Explanation

You are given an array `nums`.

For every element in the array, you need to:

1. Calculate the **sum of its digits**.
2. Replace the number with this digit sum.
3. Return the **smallest value** among all the digit sums.

---

## Example

### Input

```text
nums = [10, 12, 13, 14]
```

### After Replacement

```text
10 → 1 + 0 = 1
12 → 1 + 2 = 3
13 → 1 + 3 = 4
14 → 1 + 4 = 5
```

New array:

```text
[1, 3, 4, 5]
```

Minimum element:

```text
1
```

Output:

```text
1
```

---

# Key Observation

The problem asks for the **minimum element after replacement**.

We do **not** need to actually create the new array.

Instead:

* Find the digit sum of each number.
* Keep track of the smallest digit sum encountered.
* Return that minimum.

This saves extra space.

---

# How the Solution Works

## Step 1: Initialize Minimum

```java
int min = Integer.MAX_VALUE;
```

We start with the largest possible integer so that any digit sum will be smaller.

---

## Step 2: Traverse the Array

```java
for(int i = 0; i < nums.length; i++)
```

Process each number one by one.

---

## Step 3: Calculate Digit Sum

```java
int temp = nums[i];
int sum = 0;
```

`temp` is used because we don't want to modify the original number.

### Extract Digits

```java
while(temp != 0)
```

For each iteration:

```java
sum = sum + temp % 10;
```

* `temp % 10` gives the last digit.

```text
123 % 10 = 3
```

Then:

```java
temp = temp / 10;
```

removes the last digit.

```text
123 / 10 = 12
12 / 10 = 1
1 / 10 = 0
```

---

### Example: Number = 123

| temp | temp % 10 | sum |
| ---- | --------- | --- |
| 123  | 3         | 3   |
| 12   | 2         | 5   |
| 1    | 1         | 6   |
| 0    | Stop      | 6   |

Digit sum = **6**

---

## Step 4: Update Minimum

```java
if(sum < min)
    min = sum;
```

If the current digit sum is smaller than the minimum found so far, update it.

---

## Step 5: Return Answer

```java
return min;
```

After processing all numbers, `min` contains the smallest digit sum.

---

# Dry Run

### Input

```java
nums = [999, 19, 199]
```

### Initial

```java
min = Integer.MAX_VALUE
```

---

### First Number: 999

```text
9 + 9 + 9 = 27
```

```java
min = 27
```

---

### Second Number: 19

```text
1 + 9 = 10
```

```java
min = 10
```

because:

```text
10 < 27
```

---

### Third Number: 199

```text
1 + 9 + 9 = 19
```

```java
min = 10
```

because:

```text
19 > 10
```

---

### Final Answer

```java
return 10;
```

---

# Why This Approach Works

For every number, we correctly compute its replacement value (digit sum).

Since the problem only asks for the **minimum element after all replacements**, keeping track of the smallest digit sum while iterating is enough.

Thus:

* Every replacement value is considered exactly once.
* The smallest replacement value is stored in `min`.
* The final `min` is the required answer.

---

# Complexity Analysis

Let:

* `n` = number of elements in the array
* `d` = number of digits in a number

For each number, we process each digit once.

### Time Complexity

```text
O(n × d)
```

Since `nums[i] ≤ 10^4`, each number has at most 5 digits, so this is effectively:

```text
O(n)
```

### Space Complexity

```text
O(1)
```

Only a few variables (`min`, `sum`, `temp`) are used. No extra array is created.
