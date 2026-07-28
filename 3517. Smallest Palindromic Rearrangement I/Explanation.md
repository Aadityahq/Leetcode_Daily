## 3517. Smallest Palindromic Rearrangement I

### Intuition

We are given a **palindromic string**, which means:

* Every character appears an **even number of times**, except possibly **one** character (for odd-length strings).

Since we can rearrange the characters in any order, we need to build the **lexicographically smallest palindrome**.

The key observation is:

> The first half of a palindrome completely determines the second half.

So instead of arranging the whole string, we only need to create the smallest possible **left half**.

---

## Approach

### Step 1: Count the frequency of every character

Store the frequency of each letter (`a` to `z`).

Example:

```
s = "daccad"

Frequency:
a -> 2
c -> 2
d -> 2
```

---

### Step 2: Build the left half

For every character from `'a'` to `'z'`:

* Add `frequency / 2` copies to the left half.

Why?

Because every character must appear equally on both sides.

Example:

```
a -> 2 → add 1 'a'
c -> 2 → add 1 'c'
d -> 2 → add 1 'd'

Left Half = "acd"
```

Since we're adding characters in alphabetical order, the left half is automatically the smallest possible.

---

### Step 3: Find the middle character

If any character has an odd frequency, place **one copy** in the middle.

Example:

```
"babab"

Frequency:

a -> 2
b -> 3

Middle = "b"
```

Only one character can have an odd frequency because the original string is already a palindrome.

---

### Step 4: Create the right half

The right half is simply the reverse of the left half.

Example:

```
Left  = "acd"

Reverse = "dca"

Palindrome = "acd" + "" + "dca"
           = "acddca"
```

---

## Why does this work?

A palindrome must be symmetric.

So if we place

```
Left = "acd"
```

the right side **must** be

```
"dca"
```

to mirror it.

To make the palindrome lexicographically smallest, we want the earliest letters to appear as early as possible.

That means:

* put all smaller letters first in the left half
* larger letters later

Since the left half determines the beginning of the palindrome, this guarantees the smallest lexicographical order.

---

## Example Walkthrough

### Example 1

```
s = "babab"
```

Frequency

```
a -> 2
b -> 3
```

Build left half

```
a/2 = 1
b/2 = 1

Left = "ab"
```

Middle

```
b
```

Right

```
reverse("ab") = "ba"
```

Answer

```
"ab" + "b" + "ba"

= "abbba"
```

---

### Example 2

```
s = "daccad"
```

Frequency

```
a -> 2
c -> 2
d -> 2
```

Left

```
acd
```

Middle

```
none
```

Right

```
dca
```

Answer

```
acddca
```

---

## Algorithm

1. Count the frequency of every character.
2. Traverse from `'a'` to `'z'`.
3. Append `freq / 2` copies to the left half.
4. Store the odd-frequency character (if any) as the middle.
5. Reverse the left half to create the right half.
6. Return `left + middle + right`.

---

## Complexity Analysis

Let **n** be the length of the string.

* Counting frequencies: **O(n)**
* Building the palindrome: **O(n)**
* Reversing: **O(n)**

### Time Complexity

**O(n)**

### Space Complexity

**O(n)**

(The output string itself requires O(n) space.)

---

# Java Solution

```java
class Solution {
    public String smallestPalindrome(String s) {
        int[] freq = new int[26];

        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        StringBuilder left = new StringBuilder();
        String middle = "";

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < freq[i] / 2; j++) {
                left.append((char) ('a' + i));
            }

            if (freq[i] % 2 == 1) {
                middle = String.valueOf((char) ('a' + i));
            }
        }

        String right = new StringBuilder(left).reverse().toString();

        return left.toString() + middle + right;
    }
}
```

---

## Dry Run

Input:

```text
s = "babab"
```

Frequency:

```text
a → 2
b → 3
```

Build left:

```text
a → append once

Left = "a"

b → append once

Left = "ab"
```

Middle:

```text
b
```

Right:

```text
reverse("ab")

= "ba"
```

Final answer:

```text
Left   = "ab"
Middle = "b"
Right  = "ba"

Result = "abbba"
```

---

## Key Takeaways

* A palindrome is completely determined by its **left half**.
* To obtain the **lexicographically smallest** palindrome, always build the left half using characters from **'a' to 'z'**.
* Mirror the left half to form the right half, and place the odd-frequency character (if any) in the middle.
* This greedy approach is optimal because the earliest characters in the left half determine the lexicographical order of the entire palindrome.
