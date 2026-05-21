# 3043. Find the Length of the Longest Common Prefix

## Problem Explanation

We are given two arrays:

```text
arr1 and arr2
```

We need to find the **maximum length of the common prefix** between any pair of numbers:

```text
x from arr1
y from arr2
```

---

## What is a Prefix?

A prefix means digits starting from the left side.

Example:

```text
Number = 12345

Prefixes:
1
12
123
1234
12345
```

---

## Example

```text
arr1 = [1,10,100]
arr2 = [1000]
```

Compare:

| Pair       | Common Prefix | Length |
| ---------- | ------------- | ------ |
| 1 & 1000   | 1             | 1      |
| 10 & 1000  | 10            | 2      |
| 100 & 1000 | 100           | 3      |

Answer = `3`

---

# Efficient Idea

A brute force solution would compare every pair:

```text
arr1[i] with arr2[j]
```

That would be:

```text
O(n × m)
```

Too slow for `5 * 10^4`.

---

# Optimized Approach (Using HashSet)

## Key Observation

If two numbers share a common prefix, then:

```text
Some prefix of number in arr1
will exist as prefix of number in arr2
```

---

## Step-by-Step Plan

### Step 1:

Store all prefixes of numbers from `arr1` in a HashSet.

Example:

```text
100 -> 100, 10, 1
```

---

### Step 2:

For every number in `arr2`:

Keep removing last digit and check whether it exists in set.

Example:

```text
1000
-> 1000
-> 100
-> 10
-> 1
```

If found:

```text
answer = max(answer, length of prefix)
```

---

# Why This Works

Suppose:

```text
arr1 has 100
arr2 has 1000
```

Prefixes of `100`:

```text
100
10
1
```

While checking `1000`:

```text
1000 ❌
100 ✅
```

So longest common prefix length = `3`.

---

# Time Complexity

Each number has at most 9 digits (`10^8`).

So:

* Building prefixes → `O(n * 9)`
* Checking prefixes → `O(m * 9)`

Overall:

```text
O(n + m)
```

Very efficient.

---

# Java Solution

```java
import java.util.*;

class Solution {
    public int longestCommonPrefix(int[] arr1, int[] arr2) {

        HashSet<Integer> set = new HashSet<>();

        // Store all prefixes of arr1 numbers
        for (int num : arr1) {

            while (num > 0) {
                set.add(num);
                num /= 10;
            }
        }

        int ans = 0;

        // Check prefixes for arr2 numbers
        for (int num : arr2) {

            while (num > 0) {

                if (set.contains(num)) {
                    ans = Math.max(ans, String.valueOf(num).length());
                    break;
                }

                num /= 10;
            }
        }

        return ans;
    }
}
```

---

# Dry Run

## Input

```text
arr1 = [1,10,100]
arr2 = [1000]
```

---

## Step 1: Store prefixes from arr1

From `1`:

```text
1
```

From `10`:

```text
10
1
```

From `100`:

```text
100
10
1
```

Set becomes:

```text
{1, 10, 100}
```

---

## Step 2: Check arr2

Number = `1000`

Check:

```text
1000 ❌
100 ✅
```

Length of `"100"` = `3`

Answer = `3`

---

# Important Concept

We are not comparing digits one by one.

Instead:

* Generate prefixes
* Store them
* Quickly check using HashSet

This reduces the complexity massively.

---

# Alternative Approach

You can also solve this using:

* Trie (Prefix Tree)

But HashSet is simpler and faster here because numbers have very small digit length.

---

# Final Takeaway

The main trick is:

```text
Any common prefix must itself be a number prefix.
```

So:

* Store all prefixes from one array
* Search prefixes from second array efficiently

This avoids checking every pair.
