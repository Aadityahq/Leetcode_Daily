# Problem Explanation

You are given an integer array `nums`.

Your task is to:

* take every number
* separate all its digits
* store those digits in the same order
* return the final array

---

## Example

Input:

```text id="mt1nfx"
nums = [13,25,83,77]
```

### Separate each number

```text id="8qzjqg"
13 -> [1,3]
25 -> [2,5]
83 -> [8,3]
77 -> [7,7]
```

Combine all digits:

```text id="zhg6c7"
[1,3,2,5,8,3,7,7]
```

Output:

```text id="k7w68h"
[1,3,2,5,8,3,7,7]
```

---

# What Is the Main Idea?

We need to extract digits from every number.

There are two ways:

1. Using math (`% 10`, `/ 10`)
2. Using strings

For this problem, the **string approach is easier and cleaner**.

---

# How the Solution Works

---

## Step 1: Create a List

```java id="n0v8bz"
List<Integer> result = new ArrayList<>();
```

### Why?

We do not know beforehand how many digits the final answer will contain.

Example:

```text id="9pr2c4"
[7, 12345]
```

Total digits = 1 + 5 = 6

So we use a dynamic list.

---

# Step 2: Traverse Every Number

```java id="7dfm9m"
for (int num : nums)
```

### Why?

We must process every number one by one.

Example:

```text id="3ljlwm"
13
25
83
77
```

---

# Step 3: Convert Number to String

```java id="v9e4kj"
String digits = String.valueOf(num);
```

### Why?

A string lets us access each digit easily as characters.

Example:

```text id="v6pd8z"
13 -> "13"
```

Now we can access:

* `'1'`
* `'3'`

---

# Step 4: Traverse Each Character

```java id="7hwl2h"
for (int j = 0; j < digits.length(); j++)
```

### Why?

We need every digit individually.

Example:

```text id="q11g3l"
"83"
```

Characters:

* `'8'`
* `'3'`

---

# Step 5: Convert Character to Integer

```java id="l1n2ka"
int digit = digits.charAt(j) - '0';
```

---

## Why `- '0'`?

`charAt()` returns a character.

Example:

```java id="6u7v3r"
digits.charAt(0)
```

returns:

```text id="4s9xua"
'8'
```

NOT integer `8`.

Characters are stored using ASCII values:

```text id="sv2hyk"
'0' = 48
'8' = 56
```

So:

```text id="vlm44w"
'8' - '0'
= 56 - 48
= 8
```

This converts character digit into actual integer digit.

---

# Step 6: Store Digit

```java id="ow54lc"
result.add(digit);
```

### Why?

We collect all digits in order.

Example:

```text id="b5q1tp"
13 -> add 1, add 3
25 -> add 2, add 5
```

Final list:

```text id="o7qjpw"
[1,3,2,5]
```

---

# Step 7: Convert List to Array

LeetCode requires an `int[]`.

So:

```java id="vh6x2t"
int[] answer = new int[result.size()];
```

Then copy elements:

```java id="xvbv4d"
for (int i = 0; i < result.size(); i++) {
    answer[i] = result.get(i);
}
```

---

# Complete Solution

```java id="c3y6zq"
class Solution {
    public int[] separateDigits(int[] nums) {

        List<Integer> result = new ArrayList<>();

        for (int num : nums) {

            String digits = String.valueOf(num);

            for (int j = 0; j < digits.length(); j++) {

                int digit = digits.charAt(j) - '0';

                result.add(digit);
            }
        }

        int[] answer = new int[result.size()];

        for (int i = 0; i < result.size(); i++) {
            answer[i] = result.get(i);
        }

        return answer;
    }
}
```

---

# Dry Run

Input:

```text id="emk6m3"
nums = [13,25]
```

---

## First Number = 13

Convert to string:

```text id="l95a4t"
"13"
```

Characters:

* `'1'`
* `'3'`

Convert:

* `'1' - '0' = 1`
* `'3' - '0' = 3`

List:

```text id="2r0wop"
[1,3]
```

---

## Second Number = 25

Characters:

* `'2'`
* `'5'`

Convert:

* 2
* 5

List:

```text id="nt6q4o"
[1,3,2,5]
```

---

# Final Output

```text id="s9tvgz"
[1,3,2,5]
```

---

# Time Complexity

Suppose total digits across all numbers = `D`

We visit every digit once.

```text id="3svjmo"
Time Complexity = O(D)
```

---

# Space Complexity

We store all digits in the result array.

```text id="x9h7yh"
Space Complexity = O(D)
```

---

# Why This Solution Is Good

✅ Simple
✅ Easy to understand
✅ Maintains correct order
✅ Efficient for constraints given in problem
