This problem looks hard because of the substring condition, but there is a very nice **26-letter interval + greedy** solution.

## 1. Understand the condition

A substring is valid if:

> If a character appears inside the substring, **all occurrences of that character in the entire string must also be inside the substring.**

For example:

```text
s = "adefaddaccc"
```

For character `a`:

```text
a d e f a d d a
^             ^
```

So if our substring contains `a`, it must include **all 3 `a`s**.

Therefore:

```text
"e"       → valid
"f"       → valid
"ccc"     → valid
"ef"      → valid
"adefadda"→ valid
```

But:

```text
"adefadd" 
```

is NOT valid because it contains `a` but misses the last `a`.

---

# 2. Key observation

There are only **26 lowercase characters**.

For every character, we can find:

```text
first occurrence
last occurrence
```

For example:

```text
s = "adefaddaccc"

a → [0, 7]
d → [1, 5]
e → [2, 2]
f → [3, 3]
c → [8, 10]
```

Initially, these intervals look like possible substrings.

But there is an important problem.

Suppose we start with:

```text
[a's first occurrence ... a's last occurrence]
```

If another character occurs inside this interval, **all occurrences of that character must also be inside the interval**.

Otherwise, we need to expand the interval.

---

# 3. Example of expanding an interval

Take:

```text
s = "adefaddaccc"
```

Start with character `a`:

```text
[a........a]
0         7
```

Inside `[0,7]` we have:

```text
a d e f a d d a
```

For `d`:

```text
first[d] = 1
last[d]  = 5
```

Both are inside `[0,7]`.

For `e`:

```text
first[e] = 2
last[e]  = 2
```

Fine.

For `f`:

```text
first[f] = 3
last[f] = 3
```

Fine.

So `[0,7]` is a valid substring:

```text
"adefadda"
```

But notice that `[2,2]` (`"e"`) and `[3,3]` (`"f"`) are also valid.

We want the **maximum number of substrings**, so smaller independent intervals are preferable.

---

# 4. Generate all possible valid intervals

For each character, try to create the **smallest valid interval starting at its first occurrence**.

We can do this by scanning from `first[c]` to `last[c]`.

Suppose:

```text
s = "abbaccd"
```

Initial intervals:

```text
a → [0,3]
b → [1,2]
c → [4,5]
d → [6,6]
```

Check `a`:

```text
a b b a
0     3
```

All `b`s are inside, so `[0,3]` is valid.

`b`:

```text
b b
1 2
```

Valid.

`c`:

```text
c c
4 5
```

Valid.

`d`:

```text
d
6
```

Valid.

So candidates are:

```text
[0,3] → "abba"
[1,2] → "bb"
[4,5] → "cc"
[6,6] → "d"
```

---

# 5. Why greedy works

Now we have intervals.

For:

```text
abbaccd
```

we have:

```text
[0,3]   "abba"
[1,2]   "bb"
[4,5]   "cc"
[6,6]   "d"
```

We need the maximum number of **non-overlapping** intervals.

Choosing:

```text
[1,2] "bb"
[4,5] "cc"
[6,6] "d"
```

gives:

```text
3 substrings
```

Choosing:

```text
[0,3] "abba"
[4,5] "cc"
[6,6] "d"
```

also gives:

```text
3 substrings
```

But the first solution has smaller total length:

```text
2 + 2 + 1 = 5
```

while the second has:

```text
4 + 2 + 1 = 7
```

So we need:

> Maximum number first, and among those, minimum total length.

The easiest way to achieve this is:

### Sort valid intervals by their ending position and greedily select them.

This is the classic **interval scheduling** strategy.

Because every selected interval gives exactly **one substring**, selecting the interval that ends earliest leaves the most space for future intervals.

And because we construct each interval as the smallest valid interval for its starting character, the resulting maximum-count solution also has minimum total length.

---

# 6. Java Solution

```java
class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();

        // first[c] = first occurrence of character c
        // last[c]  = last occurrence of character c
        int[] first = new int[26];
        int[] last = new int[26];

        Arrays.fill(first, n);
        Arrays.fill(last, -1);

        // Find first and last occurrence
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';

            first[c] = Math.min(first[c], i);
            last[c] = i;
        }

        List<int[]> intervals = new ArrayList<>();

        // Try to create a valid interval for every character
        for (int c = 0; c < 26; c++) {

            if (last[c] == -1) {
                continue; // character doesn't exist
            }

            int left = first[c];
            int right = last[c];

            boolean valid = true;

            for (int i = left; i <= right; i++) {

                int current = s.charAt(i) - 'a';

                // This character has an occurrence before 'left'
                if (first[current] < left) {
                    valid = false;
                    break;
                }

                // We need to include all occurrences of this character
                right = Math.max(right, last[current]);
            }

            if (valid) {
                intervals.add(new int[]{left, right});
            }
        }

        // Sort by ending position
        intervals.sort((a, b) -> Integer.compare(a[1], b[1]));

        List<String> result = new ArrayList<>();

        int previousEnd = -1;

        // Greedy interval scheduling
        for (int[] interval : intervals) {

            int left = interval[0];
            int right = interval[1];

            if (left > previousEnd) {
                result.add(s.substring(left, right + 1));
                previousEnd = right;
            }
        }

        return result;
    }
}
```

---

# 7. Let's understand the important part

This is the heart of the solution:

```java
for (int i = left; i <= right; i++) {

    int current = s.charAt(i) - 'a';

    if (first[current] < left) {
        valid = false;
        break;
    }

    right = Math.max(right, last[current]);
}
```

Suppose we start with:

```text
left = 0
right = 3
```

and:

```text
s = "abbaccd"
```

We inspect:

```text
0 → a
1 → b
2 → b
3 → a
```

For `b`:

```text
first[b] = 1
last[b] = 2
```

Both are inside `[0,3]`.

So the interval remains:

```text
[0,3]
```

---

## What does this check mean?

```java
if (first[current] < left)
```

Suppose our current interval is:

```text
[left ........ right]
```

and we encounter character `x`.

If:

```text
first[x] < left
```

then `x` occurred **before our interval**.

That means our substring contains `x`, but cannot contain all occurrences of `x` without moving `left`.

However, `left` is already the first occurrence of our starting character, so we cannot move it left while keeping this candidate minimal.

Therefore:

```java
valid = false;
```

---

# 8. Why do we update `right`?

This line is extremely important:

```java
right = Math.max(right, last[current]);
```

Suppose:

```text
s = "abca"
```

For `a`:

```text
first[a] = 0
last[a] = 3
```

So:

```text
[0,3]
```

Already includes everything.

But imagine an interval starts with a character whose last occurrence is earlier, and while scanning we discover another character whose last occurrence is further away.

Then our interval must expand.

For example:

```text
a ... b ........ b
^                 ^
left              new right
```

Since our substring contains `b`, it must include the second `b`.

So:

```java
right = Math.max(right, last[b]);
```

This can cause us to scan additional characters.

---

# 9. Why sort by ending position?

After generating valid intervals:

```text
[0,3]
[1,2]
[4,5]
[6,6]
```

Sort:

```text
[1,2]
[0,3]
[4,5]
[6,6]
```

because their ending positions are:

```text
2
3
5
6
```

Then:

```java
int previousEnd = -1;

for (int[] interval : intervals) {
    if (interval[0] > previousEnd) {
        result.add(...);
        previousEnd = interval[1];
    }
}
```

First:

```text
[1,2]
```

select it.

Then:

```text
[0,3]
```

overlaps, so skip.

Then:

```text
[4,5]
```

doesn't overlap.

Then:

```text
[6,6]
```

doesn't overlap.

Result:

```text
["bb", "cc", "d"]
```

---

# 10. Why maximum number + minimum length?

This is a subtle part of the problem.

Suppose two valid intervals have the same ending position:

```text
[0,5]
[2,5]
```

The second is shorter.

But can both be valid?

For a given ending position, if `[0,5]` is valid and `[2,5]` is also valid, choosing `[2,5]` is preferable because it leaves positions `0,1` available.

The interval construction ensures that the candidates we generate are the **minimal valid intervals**.

So greedy selection by earliest ending position naturally favors the shorter compatible interval and therefore satisfies the tie-break.

---

# 11. Complexity

There are only 26 possible starting characters.

For each character, we may scan the string in the worst case.

Therefore:

### Time

```text
O(26 × n)
```

Since `26` is constant:

```text
O(n)
```

The sorting is only over at most 26 intervals:

```text
O(26 log 26)
```

which is effectively constant.

### Space

```text
O(26)
```

apart from the returned result.

So overall:

```text
Time:  O(n)
Space: O(1)
```

because the alphabet is fixed at 26 characters.

---

## The main idea to remember

When you see:

> **"If a substring contains a character, it must contain all occurrences of that character."**

Think:

```text
first occurrence + last occurrence
              ↓
       create interval
              ↓
 expand interval if needed
              ↓
    keep only valid intervals
              ↓
 sort by ending position
              ↓
      greedy selection
```

For this problem, **don't try all substrings**. There are `O(n²)` substrings, but there are only **26 characters**, which is the key that makes the problem manageable.
