# 2904. Shortest and Lexicographically Smallest Beautiful String

## Understanding the problem

You are given:

* A binary string `s` containing only `'0'` and `'1'`
* An integer `k`

A substring is **beautiful** if it contains **exactly `k` ones**.

Among all beautiful substrings, we need to:

1. Find the **shortest length**.
2. If multiple beautiful substrings have that same shortest length, return the **lexicographically smallest** one.
3. If no substring contains exactly `k` ones, return `""`.

### Example

```text
s = "1011", k = 2
```

Beautiful substrings containing exactly 2 ones include:

```text
"101"
"011"
"11"
```

The shortest one is:

```text
"11"
```

So the answer is:

```text
"11"
```

---

# Approach: Sliding Window

Since we need a substring containing exactly `k` ones, we can use a **sliding window**.

We maintain:

* `left` → starting index of the window
* `right` → ending index of the window
* `ones` → number of `'1'` characters currently in the window

## How it works

### Step 1: Expand the window

Move `right` from left to right.

Whenever:

```java
s.charAt(right) == '1'
```

increment `ones`.

---

### Step 2: Too many ones?

If:

```text
ones > k
```

the current window cannot be beautiful because it contains more than `k` ones.

So, move `left` forward until the window has at most `k` ones.

---

### Step 3: Exactly `k` ones

When:

```text
ones == k
```

we have a beautiful substring.

But we want the **shortest possible substring**, so any unnecessary zeros at the beginning should be removed.

For example:

```text
window = "00101"
k = 2
```

It contains exactly 2 ones, but the starting zeros are unnecessary.

We can move `left` forward while:

```java
s.charAt(left) == '0'
```

After removing leading zeros:

```text
"101"
```

This is the shortest beautiful substring ending at the current `right`.

Now compare it with our answer.

---

# Java Solution

```java
class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int left = 0;
        int ones = 0;
        String answer = "";

        for (int right = 0; right < s.length(); right++) {

            // Add current character
            if (s.charAt(right) == '1') {
                ones++;
            }

            // Too many ones, shrink the window
            while (ones > k) {
                if (s.charAt(left) == '1') {
                    ones--;
                }
                left++;
            }

            // Exactly k ones
            if (ones == k) {

                // Remove unnecessary leading zeros
                while (s.charAt(left) == '0') {
                    left++;
                }

                String current = s.substring(left, right + 1);

                // Update the answer
                if (answer.isEmpty() ||
                    current.length() < answer.length() ||
                    (current.length() == answer.length() &&
                     current.compareTo(answer) < 0)) {

                    answer = current;
                }
            }
        }

        return answer;
    }
}
```

---

# Why do we remove leading zeros?

This is the most important part.

Suppose:

```text
s = "0010110"
k = 2
```

At some point, our window might be:

```text
"00101"
```

Number of ones:

```text
0 0 1 0 1
    ↑   ↑
```

There are exactly 2 ones.

But we want the **shortest** beautiful substring.

The first two zeros don't contribute to the number of ones.

So we remove them:

```text
"101"
```

Still exactly 2 ones, but shorter.

Therefore, whenever we have exactly `k` ones, we should remove all leading zeros.

---

# Dry Run

Let's take:

```text
s = "100011001"
k = 3
```

### Initially

```text
left = 0
ones = 0
answer = ""
```

### `right = 0`

Character:

```text
"1"
```

```text
ones = 1
```

Not enough ones yet.

---

### `right = 1`

Character:

```text
"0"
```

```text
ones = 1
```

Still not enough.

---

### `right = 2`

```text
ones = 1
```

---

### `right = 3`

```text
ones = 1
```

---

### `right = 4`

Character is `'1'`:

```text
ones = 2
```

Still not enough.

---

### Continue until the third `1`

Once:

```text
ones == 3
```

we have a beautiful substring.

Suppose the current window has unnecessary zeros at the beginning:

```text
"100011001"
 ↑
 left
```

We cannot remove the first `'1'`, because then we would have only 2 ones.

So this is the shortest valid window ending at that position.

As we continue moving `right`, whenever there are more than 3 ones, we move `left` until only 3 ones remain. Then we again remove any leading zeros and compare the resulting substring with the current answer.

Eventually, the shortest candidate is:

```text
"11001"
```

---

# How does lexicographical comparison work?

In Java:

```java
current.compareTo(answer)
```

returns:

* Negative value → `current` is lexicographically smaller
* `0` → both strings are equal
* Positive value → `current` is lexicographically larger

For example:

```java
"011".compareTo("101") < 0
```

because:

```text
0 < 1
```

Therefore:

```java
current.compareTo(answer) < 0
```

means `current` should be preferred when both substrings have the same length.

---

# Why does this approach work?

For every `right`:

1. We expand the window.
2. If there are more than `k` ones, we remove characters from the left.
3. When there are exactly `k` ones, we remove all unnecessary leading zeros.

Therefore, the resulting window is the **shortest possible beautiful substring ending at `right`**.

We check every such candidate and keep:

* the globally shortest one, or
* if lengths are equal, the lexicographically smallest one.

---

# Complexity

Let `n = s.length()`.

### Time Complexity

```text
O(n²)
```

In the worst case, creating substrings and comparing strings can take `O(n)`. Since `n ≤ 100`, this is easily efficient enough.

The sliding-window movement itself is `O(n)`.

### Space Complexity

```text
O(n)
```

for storing candidate substrings.

---

## Key idea to remember

> **Find a window with exactly `k` ones, then remove all unnecessary zeros from the beginning. This gives the shortest beautiful substring ending at the current position. Compare all such candidates to get the final answer.**
