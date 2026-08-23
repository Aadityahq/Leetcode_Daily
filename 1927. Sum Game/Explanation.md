p
# LeetCode 1927. Sum Game — Java Solution

## Problem Explanation

The string has an even length and is divided into two equal halves.

* **Bob wins** if both halves have the same digit sum.
* **Alice wins** if the sums are different.
* Alice moves first.
* On every move, a player replaces one `?` with a digit from `0` to `9`.
* Both players play optimally.

We need to return:

* `true` → Alice wins
* `false` → Bob wins

---

## Key Observation

Let:

* `leftSum` = sum of existing digits in the left half
* `rightSum` = sum of existing digits in the right half
* `leftQuestion` = number of `?` in the left half
* `rightQuestion` = number of `?` in the right half

### Case 1: Total number of `?` is odd

Alice makes the first move, so she also gets **one more move than Bob**.

That extra move allows Alice to force the final sums to be different.

Therefore:

```text
If total number of '?' is odd → Alice wins
```

---

## Case 2: Total number of `?` is even

Now both players get the same number of moves.

The important question is:

> Can Bob force both halves to have equal sums?

Each pair of unmatched `?` between the two halves can create a maximum difference of:

```text
9
```

Suppose one half has more `?` than the other.

The extra question marks are:

```text
abs(leftQuestion - rightQuestion)
```

Since the total number of `?` is even, this difference is also even.

For Bob to balance the existing difference between the sums, the following must hold:

```text
leftSum - rightSum
    =
(rightQuestion - leftQuestion) / 2 * 9
```

If this equality is true, Bob can force the sums to become equal.

Otherwise, Alice can prevent equality.

---

# Java Code

```java
class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;

        int leftSum = 0;
        int rightSum = 0;

        int leftQuestion = 0;
        int rightQuestion = 0;

        for (int i = 0; i < n; i++) {
            char ch = num.charAt(i);

            if (i < half) {
                if (ch == '?') {
                    leftQuestion++;
                } else {
                    leftSum += ch - '0';
                }
            } else {
                if (ch == '?') {
                    rightQuestion++;
                } else {
                    rightSum += ch - '0';
                }
            }
        }

        // Alice gets one extra move
        if ((leftQuestion + rightQuestion) % 2 == 1) {
            return true;
        }

        // Bob wins only when he can perfectly balance both halves
        return leftSum - rightSum !=
                (rightQuestion - leftQuestion) / 2 * 9;
    }
}
```

---

# How Does the Formula Work?

The formula is:

```text
leftSum - rightSum ==
(rightQuestion - leftQuestion) / 2 * 9
```

Let's understand it with an example.

### Example: `"25??"`

```text
Left half  = "25"
Right half = "??"
```

So:

```text
leftSum = 2 + 5 = 7
rightSum = 0

leftQuestion = 0
rightQuestion = 2
```

Total `?` is even.

Now calculate:

```text
(rightQuestion - leftQuestion) / 2 * 9
= (2 - 0) / 2 * 9
= 9
```

But:

```text
leftSum - rightSum = 7
```

Since:

```text
7 != 9
```

Bob cannot guarantee equal sums.

Therefore:

```text
Alice wins → true
```

---

## Example 3: `"?3295???"`

Split into two halves:

```text
Left  = "?329"
Right = "5???"
```

Calculate:

```text
leftSum = 3 + 2 + 9 = 14
rightSum = 5

leftQuestion = 1
rightQuestion = 3
```

Total `?`:

```text
1 + 3 = 4
```

Even, so we check the formula.

```text
leftSum - rightSum = 14 - 5 = 9
```

And:

```text
(rightQuestion - leftQuestion) / 2 * 9
= (3 - 1) / 2 * 9
= 9
```

Both are equal:

```text
9 == 9
```

So Bob can force the final sums to be equal.

Therefore:

```text
false
```

---

# Why Multiply by `9`?

Each `?` can be replaced with any digit from:

```text
0 to 9
```

The maximum impact of one move on the difference between the two halves is `9`.

When the question marks are unevenly distributed, the extra question marks give one side a potential difference of `9` each. Since Alice and Bob take turns and get equal moves when the total number of `?` is even, only **half of the extra question marks** effectively matter in determining whether Bob can neutralize the difference.

That's why:

```text
(rightQuestion - leftQuestion) / 2 * 9
```

---

# Simple Intuition

You can think of the game in just two steps:

### Step 1: Is the number of `?` odd?

```text
Yes → Alice wins
```

Alice gets one extra move and can force inequality.

### Step 2: If it is even

Check whether the current digit-sum difference is exactly balanced by the difference in the number of `?`.

```text
leftSum - rightSum ==
(rightQuestion - leftQuestion) / 2 * 9
```

* Equal → Bob wins → return `false`
* Not equal → Alice wins → return `true`

---

## Complexity

```text
Time Complexity:  O(n)
Space Complexity: O(1)
```

We scan the string only once and use a few variables.
