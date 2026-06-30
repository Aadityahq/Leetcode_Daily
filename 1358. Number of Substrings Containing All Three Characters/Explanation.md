# 1358. Number of Substrings Containing All Three Characters

## Problem Explanation

You are given a string `s` containing only three characters:

* `'a'`
* `'b'`
* `'c'`

Your task is to **count all substrings** that contain **at least one `'a'`, one `'b'`, and one `'c'`.**

A substring is a continuous part of the string.

---

## Example

### Input

```text
s = "abcabc"
```

Possible valid substrings are:

```text
abc
abca
abcab
abcabc
bca
bcab
bcabc
cab
cabc
abc
```

Answer:

```text
10
```

---

## Brute Force Approach

Generate every substring.

For each substring:

* Check whether it contains `a`, `b`, and `c`.
* If yes, increase answer.

### Complexity

There are O(n²) substrings.

Checking each substring costs O(n).

Overall:

```
O(n³)
```

Too slow for `n = 50000`.

---

# Efficient Observation

Instead of checking every substring...

Think about each ending position.

Suppose we are at index `right`.

If we already have at least one `a`, `b`, and `c`, then many substrings ending at `right` are automatically valid.

Example:

```
s = abcabc

          ^
        right = 2
```

Substring

```
abc
```

is valid.

Now if we extend it

```
abca
abcab
abcabc
```

they are also valid.

Even better...

If window `[left...right]` is valid,

then every substring starting before `left` and ending at `right` is also valid.

Instead of counting one-by-one, we count all together.

---

# Sliding Window Idea

Maintain:

```
left
right
```

and counts of

```
a
b
c
```

Expand the window using `right`.

Whenever all three characters are present:

```
count[a] > 0
count[b] > 0
count[c] > 0
```

then

```
Every substring starting at left
and ending from right to end
is valid.
```

How many?

```
n - right
```

because

```
right
right+1
right+2
...
n-1
```

are all valid endings.

After counting,

move `left` forward to find the next valid window.

---

# Why do we add `(n - right)`?

Example

```
s = aaabc

Window:

aaabc
^^^^^
left=0
right=4
```

Window already contains

```
a
b
c
```

Now this substring is valid.

If there were more characters after index 4,

adding them cannot remove existing characters.

So

```
aaabc
aaabcX
aaabcXY
...
```

will also remain valid.

Therefore there are

```
n-right
```

valid substrings beginning at current `left`.

---

## Dry Run

```
s = abcabc

n = 6
```

Initially

```
left = 0
ans = 0
```

---

### right = 0

```
Window = a

a=1
b=0
c=0
```

Not valid.

---

### right = 1

```
Window = ab

a=1
b=1
c=0
```

Not valid.

---

### right = 2

```
Window = abc

a=1
b=1
c=1
```

Valid.

Add

```
6-2 = 4
```

These are

```
abc
abca
abcab
abcabc
```

Answer

```
ans = 4
```

Shrink

Remove

```
a
```

Window becomes invalid.

---

### right = 3

Window

```
bca
```

Again valid.

Add

```
6-3 = 3
```

Answer

```
7
```

---

### right = 4

Window

```
cab
```

Valid.

Add

```
6-4 = 2
```

Answer

```
9
```

---

### right = 5

Window

```
abc
```

Valid.

Add

```
6-5 = 1
```

Answer

```
10
```

Finished.

---

# Algorithm

1. Create frequency array of size 3.
2. Keep `left = 0`.
3. Move `right` from left to right.
4. Add current character into frequency.
5. While window contains all three characters:

   * Add `(n-right)` to answer.
   * Remove `left` character.
   * Move `left`.
6. Return answer.

---

# Java Solution

```java
class Solution {
    public int numberOfSubstrings(String s) {
        int[] count = new int[3];
        int left = 0;
        int ans = 0;
        int n = s.length();

        for (int right = 0; right < n; right++) {

            count[s.charAt(right) - 'a']++;

            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {

                ans += n - right;

                count[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return ans;
    }
}
```

---

# Why This Works

The window always represents the **smallest valid substring** ending at the current `right`.

Once the window contains all three characters:

* Extending it to the right can never remove those characters.
* Therefore every larger substring ending after `right` is also valid.
* Instead of counting each individually, we directly add:

```
n - right
```

Then we shrink the window to find the next smallest valid window.

This ensures:

* Every valid substring is counted exactly once.
* No invalid substring is counted.

---

# Complexity Analysis

Let `n` be the length of the string.

### Time Complexity

Each character is:

* added once,
* removed once.

So both pointers move at most `n` times.

```
O(n)
```

### Space Complexity

The frequency array stores counts for only three characters.

```
O(1)
```

---

# Key Takeaways

* Use a **Sliding Window** because we need contiguous substrings.
* Maintain counts of `'a'`, `'b'`, and `'c'`.
* Whenever the window becomes valid, add **`n - right`** valid substrings at once.
* Shrink the window to find more valid starting positions.
* Overall complexity is **O(n)** with **O(1)** extra space.
