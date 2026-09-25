## LeetCode 1096 — Brace Expansion II

The main difficulty in this problem is understanding **two different operations**:

1. `,` means **union** → take all possible words.
2. Putting expressions next to each other means **concatenation** → combine every word from the left with every word from the right.

For example:

```text
{a,b}{c,d}
```

means:

```text
{a,b} × {c,d}
```

So the answers are:

```text
ac
ad
bc
bd
```

### Approach: Recursive Parsing

We can recursively parse the expression.

For every expression, we return a `Set<String>` containing all possible words.

We need to handle:

* **letters** → `{letter}`
* **`{ ... }`** → parse everything inside the braces
* **`,`** → union the results
* **adjacent expressions** → Cartesian-product concatenation

A convenient way is to use a recursive function:

```java
parse()
```

It parses an expression until it reaches either:

```text
,
```

or

```text
}
```

Then another function handles the contents inside `{}`.

---

## Java Solution

```java
import java.util.*;

class Solution {

    private int index = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression);

        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);

        return ans;
    }

    private Set<String> parse(String s) {

        Set<String> result = new HashSet<>();

        while (index < s.length() && s.charAt(index) != '}' 
                && s.charAt(index) != ',') {

            Set<String> current;

            // If we find a '{', recursively parse its contents
            if (s.charAt(index) == '{') {
                index++; // skip '{'

                current = parse(s);

                index++; // skip '}'
            }

            // Otherwise, it is a single lowercase letter
            else {
                current = new HashSet<>();
                current.add(String.valueOf(s.charAt(index)));
                index++;
            }

            // Concatenate current with result
            if (result.isEmpty()) {
                result.addAll(current);
            } else {
                result = multiply(result, current);
            }
        }

        // Handle comma-separated expressions
        while (index < s.length() && s.charAt(index) == ',') {

            index++; // skip ','

            Set<String> next = parse(s);

            result.addAll(next);
        }

        return result;
    }

    private Set<String> multiply(Set<String> a, Set<String> b) {

        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}
```

---

# Let's understand the code step by step

Consider:

```text
{a,b}{c,{d,e}}
```

We want:

```text
["ac","ad","ae","bc","bd","be"]
```

---

## 1. Why do we use `Set<String>`?

The problem says duplicate words should appear only once.

For example:

```text
{{a,z},a{b,c},{ab,z}}
```

can produce duplicate values.

Using:

```java
Set<String>
```

automatically removes duplicates.

For example:

```java
Set<String> set = new HashSet<>();

set.add("a");
set.add("a");
set.add("b");
```

The set contains:

```text
a
b
```

So we don't need to manually check for duplicates.

---

# 2. The `index` variable

We use:

```java
private int index = 0;
```

This tells us where we currently are in the expression.

For example:

```text
{a,b}{c,d}
^
```

Initially:

```text
index = 0
```

After reading `{`:

```text
index = 1
```

After reading `a`:

```text
index = 2
```

And so on.

The important part is that **recursive calls use the same `index`**.

This allows the parser to continue exactly where the previous recursive call stopped.

---

# 3. Parsing a letter

Suppose we have:

```text
a
```

We execute:

```java
else {
    current = new HashSet<>();
    current.add(String.valueOf(s.charAt(index)));
    index++;
}
```

So:

```text
"a"
```

becomes:

```text
{"a"}
```

Then:

```java
index++;
```

moves to the next character.

---

# 4. Parsing `{...}`

Suppose we encounter:

```text
{a,b}
```

We see:

```java
if (s.charAt(index) == '{') {
    index++;

    current = parse(s);

    index++;
}
```

First:

```java
index++;
```

skips `{`.

Then:

```java
current = parse(s);
```

recursively processes:

```text
a,b
```

The recursive call returns:

```text
{"a", "b"}
```

Then:

```java
index++;
```

skips the closing:

```text
}
```

So:

```text
{a,b}
```

becomes:

```text
{"a","b"}
```

---

# 5. Handling concatenation

This is the most important part.

Suppose we have:

```text
{a,b}{c,d}
```

The first part gives:

```text
{"a","b"}
```

The second part gives:

```text
{"c","d"}
```

Since they are next to each other, we need:

```text
a + c = ac
a + d = ad
b + c = bc
b + d = bd
```

That's why we have:

```java
result = multiply(result, current);
```

The `multiply()` method:

```java
private Set<String> multiply(Set<String> a, Set<String> b) {

    Set<String> result = new HashSet<>();

    for (String x : a) {
        for (String y : b) {
            result.add(x + y);
        }
    }

    return result;
}
```

It performs the **Cartesian product**.

### Example

```text
a = {"a", "b"}
b = {"c", "d"}
```

The nested loops generate:

```text
a + c = ac
a + d = ad
b + c = bc
b + d = bd
```

Therefore:

```text
{"ac", "ad", "bc", "bd"}
```

---

# 6. Handling `,`

Comma means **union**.

For example:

```text
{a,b,c}
```

means:

```text
{"a"} ∪ {"b"} ∪ {"c"}
```

The code handles this using:

```java
while (index < s.length() && s.charAt(index) == ',') {

    index++;

    Set<String> next = parse(s);

    result.addAll(next);
}
```

The important operation is:

```java
result.addAll(next);
```

For example:

```text
result = {"a", "b"}
next   = {"c", "d"}
```

After:

```java
result.addAll(next);
```

we get:

```text
{"a", "b", "c", "d"}
```

That's exactly what union means.

---

# 7. Why does `parse()` stop here?

Look at:

```java
while (index < s.length() && s.charAt(index) != '}' 
        && s.charAt(index) != ',')
```

We stop when we encounter:

```text
}
```

or:

```text
,
```

because these characters have special meaning to the current recursive level.

For example:

```text
{a,b}
```

When parsing `a`, we reach:

```text
,
```

So the current expression:

```text
a
```

is complete.

Then we handle the comma.

Similarly, when parsing:

```text
{a,{b,c}}
```

the inner parser stops when it reaches the inner:

```text
}
```

and returns its result to the outer parser.

---

# Full Dry Run

Let's take:

```text
{a,b}{c,{d,e}}
```

### First part

```text
{a,b}
```

produces:

```text
{"a","b"}
```

Then:

```text
{c,{d,e}}
```

Inside it:

```text
c
```

gives:

```text
{"c"}
```

and:

```text
{d,e}
```

gives:

```text
{"d","e"}
```

Because they are separated by a comma:

```text
{"c"} ∪ {"d","e"}
```

gives:

```text
{"c","d","e"}
```

Now we have:

```text
{"a","b"}
```

and:

```text
{"c","d","e"}
```

They are adjacent, so we multiply:

```text
a × c = ac
a × d = ad
a × e = ae

b × c = bc
b × d = bd
b × e = be
```

Result:

```text
{"ac","ad","ae","bc","bd","be"}
```

Finally:

```java
Collections.sort(ans);
```

gives:

```text
["ac","ad","ae","bc","bd","be"]
```

---

# Why this approach works

There are only **two operations** that we really need to understand:

### Union

```text
A,B
```

means:

```text
A ∪ B
```

Code:

```java
result.addAll(next);
```

### Concatenation

```text
AB
```

means:

```text
{a + b | a ∈ A, b ∈ B}
```

Code:

```java
multiply(result, current);
```

Everything in the grammar can be reduced to these two operations.

---

## Complexity

Let `N` be the number of distinct words produced.

The potentially expensive operation is concatenation:

```java
for (String x : a) {
    for (String y : b) {
        result.add(x + y);
    }
}
```

If there are `A` and `B` possibilities, it can generate up to:

```text
A × B
```

combinations.

Also, constructing strings costs time proportional to their lengths.

So the complexity is best described in terms of the **number and lengths of generated words**, rather than simply `O(n)`. The expression length is at most 60, but the number of resulting words can be much larger.

---

## The key idea to remember

For this problem, remember just this:

```text
,       → UNION
AB      → CONCATENATION
{...}   → GROUP / RECURSION
Set     → REMOVE DUPLICATES
Sort    → FINAL ANSWER
```

And the most important function is:

```java
multiply(A, B)
```

which means:

```text
Every word in A + every word in B
```

For example:

```text
A = {a,b}
B = {x,y}

A × B

= {ax, ay, bx, by}
```

This **union + Cartesian-product + recursion** pattern is the core of LeetCode 1096.
