Aditya, let's understand LeetCode 1111: Maximum Nesting Depth of Two Valid Parentheses Strings step by step. We'll first understand the problem with a simple example, then learn the Java solution and why it works.

# 1. Understand the problem

You are given a valid parentheses string `seq`, such as:

```
seq = "(()())"
```

Your task is to divide its parentheses into two groups: A and B.

* `0` means the parenthesis belongs to group A.

* `1` means the parenthesis belongs to group B.

* Both groups must contain valid parentheses strings.

* You must minimize the maximum nesting depth of the two groups.

In simple words, we want to distribute the nested parentheses between two groups so that neither group becomes too deeply nested.

## What is nesting depth?

Nesting depth means how many opening parentheses are active at the same time.

For example:

Example 1

## `()`

Depth = 1

Example 2

## `(())`

Depth = 2

Example 3

## `((()))`

Depth = 3

Notice that each new opening parenthesis increases the depth by one, while each closing parenthesis decreases it by one.

# 2. Understand the example

Input:

```
seq = "(()())"
```

Let's number every character.

|
Index `i`

|

0

|

1

|

2

|

3

|

4

|

5

|
| --- | --- | --- | --- | --- | --- | --- |
|

Character

|

`(`

|

`(`

|

`)`

|

`(`

|

`)`

|

`)`

|
|

Answer

|

0

|

1

|

1

|

1

|

1

|

0

|

The output is:

```
[0, 1, 1, 1, 1, 0]
```

This divides the original string into:

* Group A (`0`): characters at indices 0 and 5 → `()`

* Group B (`1`): characters at indices 1, 2, 3 and 4 → `()()`

Both groups have a maximum nesting depth of 1.

Therefore:

max⁡(depth⁡(A),depth⁡(B))=1\max(\operatorname{depth}(A),\operatorname{depth}(B))=1max(depth(A),depth(B))=1

This is the minimum possible depth because a non-empty valid parentheses string cannot have a depth below 1.

# 3. Java solution

Here is the solution using a simple depth counter.

Java

```
class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int n = seq.length();
        int[] ans = new int[n];
        int depth = 0;

        for (int i = 0; i < n; i++) {
            if (seq.charAt(i) == '(') {
                depth++;
                ans[i] = depth % 2;
            } else {
                ans[i] = depth % 2;
                depth--;
            }
        }

        return ans;
    }
}
```

Let's understand how and why this works.

# 4. The main idea

The key observation is:

Assign parentheses at alternating nesting levels to different groups.

* Odd depth → group `1`

* Even depth → group `0`

Why does this help?

Imagine the original string has a nesting depth of 4:

```
(((( ))))
```

Instead of putting all four levels into one group, we distribute them:

* Group A receives the even-numbered levels.

* Group B receives the odd-numbered levels.

Each group then has a maximum depth of about half the original depth.

More generally, if the original string has maximum depth DDD, this strategy limits the maximum depth of either group to at most:

⌈D2⌉\left\lceil \frac{D}{2} \right\rceil⌈2D⌉

This is the minimum possible maximum depth.

## 5. Dry run of the code

Let's trace the code for:

```
seq = "(()())"
```

We start with:

Java

```
int depth = 0;
```

The `depth` variable represents the current nesting depth as we scan the string from left to right.

|
Index

|

Character

|

Action

|

Depth after action

|

`ans[i]`

|
| --- | --- | --- | --- | --- |
|

0

|

`(`

|

Increase depth to 1

|

1

|

1

|
|

1

|

`(`

|

Increase depth to 2

|

2

|

0

|
|

2

|

`)`

|

Assign using depth 2, then decrease

|

1

|

0

|
|

3

|

`(`

|

Increase depth to 2

|

2

|

0

|
|

4

|

`)`

|

Assign using depth 2, then decrease

|

1

|

0

|
|

5

|

`)`

|

Assign using depth 1, then decrease

|

0

|

1

|

Output:

```
[1, 0, 0, 0, 0, 1]
```

This differs from the example's output, but that is perfectly fine. LeetCode accepts any valid split with the minimum maximum nesting depth.

Let's verify our groups:

* Group A (`0`): indices 1, 2, 3, 4 → `()()`

* Group B (`1`): indices 0, 5 → `()`

Both are valid, with depth 1.

## 6. Why do we handle opening and closing brackets differently?

Look carefully at the code:

Java

```
if (seq.charAt(i) == '(') {
    depth++;
    ans[i] = depth % 2;
} else {
    ans[i] = depth % 2;
    depth--;
}
```

### For an opening parenthesis `(`

First, increase the depth:

Java

```
depth++;
ans[i] = depth % 2;
```

The opening parenthesis starts a new nesting level. We assign it according to that new level.

For example, if `depth` changes from 0 to 1, then `1 % 2 = 1`.

### For a closing parenthesis `)`

First, assign the group:

Java

```
ans[i] = depth % 2;
depth--;
```

The closing parenthesis must belong to the same group as its matching opening parenthesis.

At the moment we process the closing bracket, `depth` still represents the level being closed. So we assign its group before decreasing the depth.

If we decreased `depth` first, we might assign the closing bracket to a different group from its matching opening bracket, breaking the validity of the subsequence.

# 7. An easier version to remember

There is also a shorter solution using the same alternating-depth idea:

Java

```
class Solution {
    public int[] maxDepthAfterSplit(String seq) {
        int[] ans = new int[seq.length()];
        int depth = 0;

        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == '(') {
                ans[i] = depth % 2;
                depth++;
            } else {
                depth--;
                ans[i] = depth % 2;
            }
        }

        return ans;
    }
}
```

This version assigns a group based on the depth before an opening bracket and the depth after a closing bracket. It is equivalent to the first solution except that the group labels `0` and `1` are swapped.

Both solutions work because the group assigned to each matching pair is consistent.

# 8. Complexity analysis

Let nnn be the length of `seq`.

* Time complexity: O(n)O(n)O(n) — we visit every character exactly once.

* Space complexity: O(n)O(n)O(n) — the answer array stores one integer per character. The algorithm itself uses only O(1)O(1)O(1) extra space apart from the output array.

# 9. Interview revision

Remember these three points:

1. `depth` tracks the nesting level.

2. `% 2` alternates between group `0` and group `1`.

3. The opening and matching closing parentheses must receive the same group assignment.

The most important lesson is that we do not need to construct the two strings. We only need to decide which group each character belongs to.
