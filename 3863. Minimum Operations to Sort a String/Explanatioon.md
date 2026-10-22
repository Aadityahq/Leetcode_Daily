# 3863. Minimum Operations to Sort a String

**Difficulty:** Medium  
**Language:** Java

## Problem Explanation

You are given a string `s` consisting of lowercase English letters.

In one operation, you can select any substring of `s` that is not the entire string and sort it in non-descending (ascending alphabetical) order.

Return the minimum number of operations required to sort the entire string. If it is impossible, return `-1`.

### Examples

**Example 1**

Input: `s = "dog"`

Output: `1`

Explanation:
Sort the substring `"og"` to get `"go"`. The string becomes `"dgo"`, which is sorted.

**Example 2**

Input: `s = "card"`

Output: `2`

Explanation:
1. Sort `"car"`: `"card"` → `"acrd"`
2. Sort `"rd"`: `"acrd"` → `"acdr"`

**Example 3**

Input: `s = "gf"`

Output: `-1`

Explanation:
The string has length `2`. Every proper substring has length `1`, so no operation can change the order of the characters.

---

## Approach: How and Why

The main idea is to check whether the string is already sorted and then use the positions of the smallest and largest characters to determine the minimum operations.

Let:
- `first` = first character of the string.
- `last` = last character of the string.
- `min` = smallest character in the string.
- `max` = largest character in the string.

### Case 1: The string is already sorted

If every character is less than or equal to the next character, return `0`.

Why? No operation is needed.

### Case 2: The string has length 2 and is unsorted

Return `-1`.

Why? We cannot select the entire string, and sorting a substring of length `1` does not change anything.

### Case 3: The first character is the minimum or the last character is the maximum

Return `1`.

Why? If the minimum is already first, sort the substring after it. If the maximum is already last, sort the substring before it. The selected substring is not the entire string.

### Case 4: The minimum or maximum appears in the middle

Return `2`.

Why? When neither endpoint is already correct, having the minimum or maximum at an interior position allows the extreme characters to be moved into their correct positions using two operations.

### Case 5: Otherwise

Return `3`.

Why? Neither endpoint is already correct, and neither the minimum nor maximum appears in the middle. This means the maximum is at the beginning and the minimum is at the end, requiring three operations.

---

## Java Solution

```java
class Solution {
    public int minOperations(String s) {
        int n = s.length();
        boolean sorted = true;

        char min = s.charAt(0);
        char max = s.charAt(0);

        // Find the minimum and maximum characters
        // and check whether the string is sorted.
        for (int i = 1; i < n; i++) {
            char ch = s.charAt(i);

            min = (char) Math.min(min, ch);
            max = (char) Math.max(max, ch);

            if (s.charAt(i) < s.charAt(i - 1)) {
                sorted = false;
            }
        }

        // Case 1: Already sorted.
        if (sorted) {
            return 0;
        }

        // Case 2: An unsorted string of length 2
        // cannot be changed.
        if (n == 2) {
            return -1;
        }

        char first = s.charAt(0);
        char last = s.charAt(n - 1);

        // Case 3: One operation is enough.
        if (first == min || last == max) {
            return 1;
        }

        // Case 4: Check whether min or max
        // appears in the middle.
        for (int i = 1; i < n - 1; i++) {
            if (s.charAt(i) == min || s.charAt(i) == max) {
                return 2;
            }
        }

        // Case 5: Three operations are required.
        return 3;
    }
}
```

---

## Dry Run

Consider:

```text
s = "cba"
```

**Step 1: Check whether the string is sorted.**

`"cba"` is not sorted.

**Step 2: Check the length.**

`n = 3`, so we continue.

**Step 3: Find the minimum and maximum.**

- `first = 'c'`
- `last = 'a'`
- `min = 'a'`
- `max = 'c'`

**Step 4: Check whether one operation is enough.**

- `first == min` → `false`
- `last == max` → `false`

**Step 5: Check the middle character.**

The middle character is `'b'`. It is neither the minimum nor the maximum.

Therefore, the answer is:

```text
Output: 3
```

---

## Complexity Analysis

- **Time Complexity:** `O(n)` — We scan the string a constant number of times.
- **Space Complexity:** `O(1)` — We use only a fixed number of variables.

---

## Key Takeaways

1. Return `0` if the string is already sorted.
2. Return `-1` if the string has length `2` and is unsorted.
3. Return `1` if the first character is the minimum or the last character is the maximum.
4. Return `2` if the minimum or maximum appears in the middle.
5. Otherwise, return `3`.

````

Note: The logic above needs verification against the official problem constraints before submission, since the previous versions produced wrong answers.