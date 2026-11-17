# **LeetCode 2911 – Minimum Changes to Make K Semi-palindromes**

Hard

Java

Dynamic Programming

Let's understand this problem from the basics, including **what we need to do, how the algorithm works, and why we use each step.**

There are two main concepts involved:

1. Two Pointers + Divisors – To calculate the minimum changes required to make a substring semi-palindromic.
2. Dynamic Programming – To divide the string into exactly `k` substrings with minimum total changes.

## **1. Understanding the problem**

You are given:

- A string `s`.
- An integer `k`.

Your task is to divide the string into exactly `k` non-empty substrings.

Each substring must be converted into a semi-palindrome by changing the minimum number of characters.

Return the minimum total number of character changes.

### **What is a semi-palindrome?**

A string is called a semi-palindrome if we can divide its characters into groups using a valid divisor `d`, such that every group is a palindrome.

A valid divisor must satisfy:

- `d` divides the length of the string.
- `d` must be smaller than the string length.

For example:

```
s = "abcabc"
Length = 6
```

The valid divisors are:

```
d = 1, 2, 3
```

Let's choose `d = 3`.

We create groups by taking characters at intervals of 3.

**Original string**

**a**

1

**b**

2

**c**

3

**a**

4

**b**

5

**c**

6

**Groups for d = 3**

Group 1\
**a → a**

Palindrome

Group 2\
**b → b**

Palindrome

Group 3\
**c → c**

Palindrome

Semi-palindrome

Therefore, `"abcabc"` is already a semi-palindrome.

**Important:** A string of length 1 cannot be a semi-palindrome because it has no valid divisor.

## **2. Approach – Break the problem into two parts**

Instead of directly trying every possible partition and character change, we divide the problem into two smaller problems.

**Part 1: Calculate substring costs**

Find minimum changes required to make every substring semi-palindromic.

**Part 2: Dynamic Programming**

Use the calculated costs to divide the string into exactly k substrings.

Minimum total changes

Let's understand both parts.

## **3. Part 1 – Precompute the cost of every substring**

First, we need to calculate how many changes are required to make each substring a semi-palindrome.

We create a 2D array:

```
int[][] cost = new int[n][n];
```

Here:

`cost[l][r]` represents the minimum number of character changes required to make substring `s[l...r]` a semi-palindrome.

### **How do we calculate the cost?**

For every substring:

1. Calculate its length.
2. Find all proper divisors of its length.
3. Create groups using each divisor.
4. Compare symmetric characters in every group.
5. Count mismatches.
6. Store the minimum mismatch count.

Let's understand this with an example.

Consider:

```
s = "abc"
```

Its length is 3.

The only valid divisor is:

```
d = 1
```

For `d = 1`, we get one group:

```
a b c
```

To make this a palindrome:

```
a b a
```

We need to change `c` into `a`.

Therefore:

```
cost("abc") = 1
```

### **Why do we use two pointers?**

Because every group must become a palindrome.

To make a string a palindrome, we compare characters from both ends.

For example:

```
a b c d a
↑       ↑
left   right
```

If both characters are different, we need one change.

If they are equal, no change is required.

We move towards the center:

```
while (left < right) {

    if (s.charAt(left) != s.charAt(right)) {
        changes++;
    }

    left += d;
    right -= d;
}
```

**Why&#x20;**`left += d`**&#x20;and&#x20;**`right -= d`**?**

Because the characters in a group are separated by `d` positions.

For example, if `d = 2`, a group might contain:

```
a → c → e → g
```

So we move by 2 instead of 1.

### **Correct divisor and pointer calculation**

```
for (int d = 1; d < len; d++) {

    if (len % d != 0) {
        continue;
    }

    int changes = 0;

    for (int start = 0; start < d; start++) {

        int left = l + start;

        int right = l + start
                  + ((len - 1 - start) / d) * d;

        while (left < right) {

            if (s.charAt(left) != s.charAt(right)) {
                changes++;
            }

            left += d;
            right -= d;
        }
    }
}
```

**Why do we try every divisor?**

Because a substring might not be a palindrome for `d = 1`, but it could be a semi-palindrome for another valid divisor.

We need the minimum changes among all valid divisors.

```
minCost = Math.min(minCost, changes);
```

Finally:

```
cost[l][r] = minCost;
```

We repeat this for every substring.

## **4. Part 2 – Dynamic Programming**

Now we have the minimum cost of converting every substring into a semi-palindrome.

Our remaining task is to divide the entire string into exactly `k` substrings.

For this, we use Dynamic Programming.

### **Step 1: Define the DP state**

```
int[][] dp = new int[n + 1][k + 1];
```

We define:

\\[ dp[i][j] \\]

as the minimum changes required to divide the first `i` characters into exactly `j` semi-palindromic substrings.

For example:

```
s = "abcac"
```

If:

```
i = 3
j = 1
```

It means we need to convert the first three characters `"abc"` into exactly one semi-palindrome.

### **Step 2: Initialize the DP array**

```
for (int[] row : dp) {
    Arrays.fill(row, INF);
}

dp[0][0] = 0;
```

**Why?**

- `dp[0][0] = 0`: An empty string divided into zero substrings requires zero changes.
- All other states are initialized to infinity because we haven't found a valid partition yet.

### **Step 3: Understand the DP transition**

This is the most important part of the problem.

Suppose we want to divide the first `i` characters into `j` substrings.

We need to decide where the last substring starts.

Let's assume its starting position is `p`.

Then we have:

**String: abcac**

**a**

0

**b**

1

**c**

2

**a**

3

**c**

4

Previous part: abc

Last part: ac

The split position p determines the beginning of the last substring.

We need two things:

1. Minimum cost to divide the first `p` characters into `j-1` substrings.
2. Minimum cost to convert the last substring `s[p...i-1]` into a semi-palindrome.

Therefore:

\\[ dp[i][j] = \min_p \left( dp[p][j-1]+cost[p][i-1] \right) \\]

This is our DP transition.

### **Why do we use this formula?**

Because every valid partition must have some starting position for its last substring.

We try every possible starting position `p` and choose the one that gives the minimum total cost.

```
for (int p = 0; p < i; p++) {

    if (i - p < 2) {
        continue;
    }

    if (dp[p][j - 1] == INF) {
        continue;
    }

    dp[i][j] = Math.min(
        dp[i][j],
        dp[p][j - 1] + cost[p][i - 1]
    );
}
```

**Why&#x20;**`i - p < 2`**?**

Because every substring must have at least two characters.

A substring of length 1 cannot be a semi-palindrome.

**Why check&#x20;**`dp[p][j-1] == INF`**?**

Because if the previous part cannot be divided into `j-1` valid substrings, we cannot use that partition.

### **Step 4: Return the answer**

```
return dp[n][k];
```

Why?

Because:

- `n` represents the entire string.
- `k` represents the exact number of required substrings.

So `dp[n][k]` gives our final minimum changes.

## **5. Complete corrected Java solution**

```

class Solution {

    public int minimumChanges(String s, int k) {

        int n = s.length();

        // Part 1: Precompute substring costs
        int[][] cost = new int[n][n];

        for (int l = 0; l < n; l++) {

            for (int r = l + 1; r < n; r++) {

                int len = r - l + 1;
                int minCost = Integer.MAX_VALUE;

                // Try all proper divisors
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    // Check every group
                    for (int start = 0; start < d; start++) {

                        int left = l + start;

                        int right = l + start
                                + ((len - 1 - start) / d) * d;

                        // Compare symmetric characters
                        while (left < right) {

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    minCost = Math.min(minCost, changes);
                }

                cost[l][r] = minCost;
            }
        }

        // Part 2: Dynamic Programming
        int INF = 1_000_000_000;

        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            java.util.Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= k; j++) {

                for (int p = 0; p < i; p++) {

                    // Substring length must be >= 2
                    if (i - p < 2) {
                        continue;
                    }

                    if (dp[p][j - 1] == INF) {
                        continue;
                    }

                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[p][j - 1] + cost[p][i - 1]
                    );
                }
            }
        }

        return dp[n][k];
    }
}

```

## **6. Dry Run – Example 1**

Example 1

```
Input:
s = "abcac"
k = 2
```

We need exactly two semi-palindromic substrings.

One optimal partition is:

**First substring**

# **ab → aa**

- Length = 2
- Valid divisor = 1
- `a` and `b` are different.
- Required changes = 1.

**Second substring**

# **cac → cac**

- Length = 3
- Valid divisor = 1
- Already a palindrome.
- Required changes = 0.

**Total changes**

# **1**

Therefore:

```
Output = 1
```

## **7. Complexity Analysis**

Let `n` be the length of the string.

| Part                       | Time complexity     |
| -------------------------- | ------------------- |
| Precompute substring costs | \\(O(n^3 \log n)\\) |
| Dynamic Programming        | \\(O(kn^2)\\)       |

Overall:

\\[ \boxed{O(n^3\log n+kn^2)} \\]

Space complexity:

\\[ \boxed{O(n^2+nk)} \\]

We use:

- `cost[n][n]` to store substring costs.
- `dp[n+1][k+1]` to store DP states.

These are efficient enough for `n <= 200`.

## **8. Important things to remember for interviews**

| Concept        | What to remember                                                 |
| -------------- | ---------------------------------------------------------------- |
| Divisors       | Try every proper divisor of substring length                     |
| Two Pointers   | Compare symmetric characters in each group                       |
| Precomputation | Calculate substring costs only once                              |
| DP State       | `dp[i][j]` = minimum cost for first `i` characters and `j` parts |
| DP Transition  | Previous partition cost + last substring cost                    |

### **Test your understanding**

Try answering these three questions without looking at the explanation.

**Q1. Why do we try every proper divisor d?**

To find the longest palindrome

Because different divisors create different groups

To reduce the string length

**Q2. Why do we use left += d and right -= d?**

To move through the same group

To skip invalid characters

To move to the next substring

**Q3. What does dp[i][j] represent?**

Cost of the first j characters

Minimum changes for the first i characters divided into j substrings

Number of possible partitions

Submit answers

**My advice:** Focus especially on understanding the DP transition. The divisor and two-pointer logic calculates the cost of one substring; the DP decides where to split the complete string. Once you understand these two independent parts, similar partition DP problems become much easier.