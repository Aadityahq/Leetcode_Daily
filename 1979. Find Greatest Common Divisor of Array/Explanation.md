# 1979. Find Greatest Common Divisor of Array

## Problem Understanding

You are given an array of integers.

Your task is to:

1. Find the **smallest** number in the array.
2. Find the **largest** number in the array.
3. Return the **Greatest Common Divisor (GCD)** of these two numbers.

> **GCD (Greatest Common Divisor)** is the largest positive integer that divides both numbers without leaving a remainder.

---

## Example 1

```text
nums = [2,5,6,9,10]
```

### Step 1: Find smallest and largest

```
Smallest = 2
Largest = 10
```

### Step 2: Find GCD

Factors of 2

```
1, 2
```

Factors of 10

```
1, 2, 5, 10
```

Common factors

```
1, 2
```

Largest common factor = **2**

Answer:

```text
2
```

---

## Example 2

```text
nums = [7,5,6,8,3]
```

Smallest = 3

Largest = 8

Factors

```
3 → 1,3
8 → 1,2,4,8
```

Common factor

```
1
```

Answer

```
1
```

---

## Why do we only need the smallest and largest?

The question **does not ask** for the GCD of every element.

It specifically says:

> Return the GCD of the **smallest number** and the **largest number**.

So all other numbers are irrelevant.

For example

```
nums = [2,5,6,9,10]

Smallest = 2
Largest = 10
```

We completely ignore

```
5
6
9
```

because the problem only wants

```
GCD(2,10)
```

---

# Solution

```java
class Solution {
    public int findGCD(int[] nums) {
        int smallest = 1001;
        int largest = 1;

        for(int i = 0; i < nums.length; i++)
        {
            if(largest < nums[i])
                largest = nums[i];

            if(smallest > nums[i])
                smallest = nums[i];
        }

        while(smallest != 0)
        {
            int temp = smallest;
            smallest = largest % smallest;
            largest = temp;
        }

        return largest;
    }
}
```

---

# Step 1: Find smallest and largest

```java
int smallest = 1001;
int largest = 1;
```

Why?

Constraints say

```
1 <= nums[i] <= 1000
```

So

```
smallest = 1001
```

ensures that the **first element will always be smaller**.

Similarly,

```
largest = 1
```

ensures that any larger value updates it.

---

### Loop

```java
for(int i = 0; i < nums.length; i++)
```

Visit every number.

---

### Find largest

```java
if(largest < nums[i])
    largest = nums[i];
```

Example

```
nums = [2,5,6,9,10]
```

Iteration

```
largest = 1

2 > 1
largest = 2

5 > 2
largest = 5

6 > 5
largest = 6

9 > 6
largest = 9

10 > 9
largest = 10
```

Final

```
largest = 10
```

---

### Find smallest

```java
if(smallest > nums[i])
    smallest = nums[i];
```

Initially

```
smallest = 1001
```

Iteration

```
2 < 1001
smallest = 2

5 < 2 ? No

6 < 2 ? No

9 < 2 ? No

10 < 2 ? No
```

Final

```
smallest = 2
```

---

# Now we know

```
largest = 10
smallest = 2
```

Now we need

```
GCD(10,2)
```

---

# Step 2: Euclidean Algorithm

This part

```java
while(smallest != 0)
{
    int temp = smallest;
    smallest = largest % smallest;
    largest = temp;
}
```

computes the GCD using the **Euclidean Algorithm**.

---

## Why does this algorithm work?

The key mathematical idea is:

> **GCD(a, b) = GCD(b, a % b)**

This means replacing `(a, b)` with `(b, a % b)` does **not** change the GCD.

We keep doing this until the remainder becomes `0`. At that point, the other number is the GCD.

---

## Example

Find GCD(10,2)

Initially

```
largest = 10
smallest = 2
```

### First iteration

```java
temp = smallest;
```

```
temp = 2
```

---

```java
smallest = largest % smallest;
```

```
10 % 2 = 0

smallest = 0
```

---

```java
largest = temp;
```

```
largest = 2
```

Now

```
largest = 2
smallest = 0
```

Loop stops.

Return

```
2
```

---

# Another Example

Find

```
GCD(48,18)
```

Initial

```
largest = 48
smallest = 18
```

---

Iteration 1

```
temp = 18

smallest = 48 % 18
         = 12

largest = 18
```

Now

```
18,12
```

---

Iteration 2

```
temp = 12

smallest = 18 % 12
         = 6

largest = 12
```

Now

```
12,6
```

---

Iteration 3

```
temp = 6

smallest = 12 % 6
         = 0

largest = 6
```

Loop ends.

Answer

```
6
```

---

# Dry Run

Input

```
nums = [2,5,6,9,10]
```

### Finding min and max

| Number | Smallest | Largest |
| ------ | -------- | ------- |
| 2      | 2        | 2       |
| 5      | 2        | 5       |
| 6      | 2        | 6       |
| 9      | 2        | 9       |
| 10     | 2        | 10      |

Now

```
smallest = 2
largest = 10
```

Euclidean Algorithm

| largest | smallest | largest % smallest |
| ------- | -------- | ------------------ |
| 10      | 2        | 0                  |

Loop stops.

Return

```
2
```

---

# Why use the Euclidean Algorithm?

A naive approach would be to test every divisor from `min(smallest, largest)` down to `1`, which can take up to `O(min(a, b))` operations.

The Euclidean Algorithm is much faster because each modulo operation significantly reduces the numbers involved. Its time complexity is **O(log(min(a, b)))**, making it the standard and most efficient way to compute the GCD.

---

# Time Complexity

Finding smallest and largest:

```
O(n)
```

Euclidean Algorithm:

```
O(log(min))
```

Overall

```
O(n)
```

because the array traversal dominates.

---

# Space Complexity

Only a few variables are used.

```
O(1)
```

---

# Key Takeaways

* The problem only requires the **smallest** and **largest** elements from the array.
* Scan the array once to find these two values.
* Use the **Euclidean Algorithm**, based on the identity `GCD(a, b) = GCD(b, a % b)`, to compute the GCD efficiently.
* The solution runs in **O(n)** time with **O(1)** extra space.
