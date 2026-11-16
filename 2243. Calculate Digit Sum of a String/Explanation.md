# **LeetCode 2243. Calculate Digit Sum of a String**

Easy

String

Simulation

## **1. Understanding the Problem**

You are given:

- A string `s` containing only digits.
- An integer `k`, which represents the group size.

Your task is to repeatedly divide the string into groups of size `k` and calculate the sum of digits in each group.

**Repeat this process until the length of the string becomes less than or equal to&#x20;**`k`**.**

Finally, return the resulting string.

### **Example**

```
Input: s = "11111222223", k = 3
```

**Round 1:**

Divide the string into groups of 3 characters:

```
"111" | "112" | "222" | "23"
```

Calculate the sum of digits in each group:

```
"111" = 1 + 1 + 1 = 3
"112" = 1 + 1 + 2 = 4
"222" = 2 + 2 + 2 = 6
"23"  = 2 + 3 = 5
```

Combine the results:

```
s = "3465"
```

**Round 2:**

Now the length is 4, which is greater than `k = 3`.

Divide again:

```
"346" | "5"
```

Calculate the digit sums:

```
"346" = 3 + 4 + 6 = 13
"5"   = 5
```

Combine:

```
s = "135"
```

Now the length is 3, which is equal to `k`.

Final Answer: "135"

## **2. Java Solution (Optimal)**

```

class Solution {
    public String digitSum(String s, int k) {

        // Repeat until string length becomes <= k
        while (s.length() > k) {

            StringBuilder result = new StringBuilder();

            // Divide string into groups of size k
            for (int i = 0; i < s.length(); i += k) {

                int sum = 0;

                // Calculate sum of digits in current group
                for (int j = i; j < Math.min(i + k, s.length()); j++) {
                    sum += s.charAt(j) - '0';
                }

                // Append sum to the result
                result.append(sum);
            }

            // Update the original string
            s = result.toString();
        }

        return s;
    }
}

```

## **3. Explanation of the Code (Line by Line)**

Let's understand why we use each part.

**Step 1: The while loop**

```
while (s.length() > k)
```

Why?

We need to repeat the grouping process as long as the string length is greater than `k`.

For example:

- Initially, length = 11.
- After round 1, length = 4.
- After round 2, length = 3.

When length becomes 3, the loop stops because `3 > 3` is false.

**Step 2: StringBuilder**

```
StringBuilder result = new StringBuilder();
```

Why?

We need to construct a new string after calculating the digit sums.

`StringBuilder` is efficient for repeatedly appending characters or numbers.

**Step 3: Divide into groups**

```
for (int i = 0; i < s.length(); i += k)
```

Why do we use `i += k`?

Because each group contains `k` characters.

For `s = "11111222223"` and `k = 3`:

| Iteration | Starting index | Group |
| --------- | -------------- | ----- |
| 1         | 0              | 111   |
| 2         | 3              | 112   |
| 3         | 6              | 222   |
| 4         | 9              | 23    |

**Step 4: Calculate digit sum**

```
int sum = 0;

for (int j = i; j < Math.min(i + k, s.length()); j++) {
    sum += s.charAt(j) - '0';
}
```

Why `Math.min()`?

Because the last group might contain fewer than `k` characters.

For example:

```
s = "11111222223"
k = 3
```

The last group starts at index 9.

- `i + k = 12`
- `s.length() = 11`

We use `Math.min(12, 11) = 11`.

This prevents accessing an index outside the string.

**Why&#x20;**`s.charAt(j) - '0'`**?**

In Java, `charAt()` returns a character, not an integer.

For example:

```
char ch = '5';

int digit = ch - '0';
```

The result is:

```
digit = 5
```

This converts a numeric character into its integer value.

**Step 5: Append the sum**

```
result.append(sum);
```

Why?

We append the calculated sum to our new string.

For example:

```
sum = 13
result = "13"
```

Notice that the sum can contain multiple digits.

**Step 6: Update the string**

```
s = result.toString();
```

Why?

The newly generated string becomes the input for the next round.

Finally:

```
return s;
```

We return the string when its length becomes less than or equal to `k`.

## **4. Dry Run**

Let's trace the complete execution.

Input:

```
s = "11111222223"
k = 3
```

| Round | Groups            | New String |
| ----- | ----------------- | ---------- |
| 1     | 111, 112, 222, 23 | 3465       |
| 2     | 346, 5            | 135        |

**Execution flow**

`11111222223`

Initial string (length = 11)

Round 1

`3465`

Length = 4, greater than 3

Round 2

# **135**

Final Answer

Length = 3, so the loop stops.

## **5. Complexity Analysis**

Let `n` be the initial length of the string.

**Time Complexity: O(n log n)**

- In each round, we visit every character once.
- The length of the string decreases after every round.
- Each digit sum produces at most two digits because `k <= 100` and each digit is at most 9.
- Therefore, the number of rounds is logarithmic in `n`.

More precisely, the total work is bounded by a geometric series, giving **O(n)** time for these constraints, since each round's output length is at most approximately half the previous length for `k >= 2`.

**Space Complexity: O(n)**

We use a `StringBuilder` to construct the new string.

## **6. Important Concepts to Remember**

| Concept         | Why we use it                                    |
| --------------- | ------------------------------------------------ |
| `while` loop    | Repeat until the required condition is satisfied |
| `for` loop      | Divide the string into groups                    |
| `StringBuilder` | Build the resulting string efficiently           |
| `charAt()`      | Access individual digits                         |
| `Math.min()`    | Handle the last incomplete group                 |
| `-'0'`          | Convert character digits into integers           |

## **7. Interview Questions**

Try answering these yourself:

**Q1. Why do we use a while loop instead of a single for loop?**

Reveal answer

**Q2. Why do we use Math.min(i + k, s.length())?**

Reveal answer

**Q3. Why do we subtract '0' from s.charAt(j)?**

Reveal answer

**Q4. Can the sum of a group contain more than one digit?**

Reveal answer

**My recommendation:** Remember this problem as a String + Simulation problem. The most important part is understanding how to process groups of size `k` and repeat until the stopping condition is satisfied.