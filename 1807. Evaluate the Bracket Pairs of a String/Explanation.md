## LeetCode 1807 — Evaluate the Bracket Pairs of a String

### 1. What is the problem asking?

We have:

* A string `s` containing normal characters and bracket pairs like `(name)`.
* A `knowledge` array containing key-value pairs.

For every `(key)` in `s`:

* If `key` exists in `knowledge`, replace `(key)` with its value.
* If `key` does not exist, replace `(key)` with `"?"`.

### Example

```text
s = "(name)is(age)yearsold"

knowledge = [
    ["name", "bob"],
    ["age", "two"]
]
```

We replace:

```text
(name) → bob
(age)  → two
```

So the answer is:

```text
bobistwoyearsold
```

---

# 2. Main idea

We need two things:

### Step 1: Store the knowledge in a HashMap

Instead of searching the entire `knowledge` array every time we find a key, store it like:

```text
name → bob
age  → two
```

Then we can quickly check:

```java
map.containsKey(key)
```

and get the value using:

```java
map.get(key)
```

### Step 2: Traverse the string

We scan `s` from left to right.

There are two cases:

### Case 1: Normal character

If the current character is not `'('`, simply add it to the answer.

### Case 2: `'('`

We know a bracket pair starts here.

We find the corresponding `')'`, extract the key between them, and look it up in the HashMap.

For example:

```text
(name)
 ^
 i
```

The key is:

```text
name
```

Then:

```text
if key exists → add its value
otherwise     → add "?"
```

After processing the bracket pair, jump directly to the character after `')'`.

---

# 3. Java Solution

```java
import java.util.*;

class Solution {
    public String evaluate(String s, List<List<String>> knowledge) {

        // Store key-value pairs in HashMap
        HashMap<String, String> map = new HashMap<>();

        for (List<String> pair : knowledge) {
            map.put(pair.get(0), pair.get(1));
        }

        StringBuilder result = new StringBuilder();

        int i = 0;

        while (i < s.length()) {

            // Normal character
            if (s.charAt(i) != '(') {
                result.append(s.charAt(i));
                i++;
            }

            // Bracket pair
            else {
                int j = i + 1;

                // Find closing bracket
                while (s.charAt(j) != ')') {
                    j++;
                }

                // Extract key
                String key = s.substring(i + 1, j);

                // If key exists, add its value
                if (map.containsKey(key)) {
                    result.append(map.get(key));
                } 
                // Otherwise add ?
                else {
                    result.append("?");
                }

                // Move after ')'
                i = j + 1;
            }
        }

        return result.toString();
    }
}
```

---

# 4. Let's understand the code step by step

## Step 1 — Create HashMap

```java
HashMap<String, String> map = new HashMap<>();
```

We need to store:

```text
key → value
```

For example:

```text
"name" → "bob"
"age"  → "two"
```

---

## Step 2 — Put knowledge into the HashMap

```java
for (List<String> pair : knowledge) {
    map.put(pair.get(0), pair.get(1));
}
```

Suppose:

```java
knowledge = [
    ["name", "bob"],
    ["age", "two"]
]
```

Then:

```java
pair.get(0)
```

gives:

```text
name
```

and:

```java
pair.get(1)
```

gives:

```text
bob
```

So:

```java
map.put("name", "bob");
```

After the loop:

```text
HashMap

name → bob
age  → two
```

---

# 5. Why do we use `StringBuilder`?

```java
StringBuilder result = new StringBuilder();
```

We are continuously adding characters and strings to the answer.

For example:

```text
b
bo
bob
bobi
bobis
...
```

Using `StringBuilder` is more efficient than repeatedly doing:

```java
result = result + character;
```

So we use:

```java
result.append(...)
```

---

# 6. Traversing the string

```java
int i = 0;

while (i < s.length()) {
```

`i` represents the current position in the string.

For:

```text
(name)is(age)yearsold
```

we process from left to right.

---

# 7. Case 1 — Normal character

```java
if (s.charAt(i) != '(') {
    result.append(s.charAt(i));
    i++;
}
```

Suppose:

```text
(name)is
```

After processing `(name)`, we reach:

```text
i
```

Since:

```java
s.charAt(i) != '('
```

we simply add it:

```java
result.append('i');
```

Then:

```java
i++;
```

---

# 8. Case 2 — We find `'('`

Suppose:

```text
(name)is
^
i
```

Here:

```java
s.charAt(i) == '('
```

So we enter:

```java
else {
```

Now we need to find the closing bracket `')'`.

---

# 9. Find the closing bracket

```java
int j = i + 1;

while (s.charAt(j) != ')') {
    j++;
}
```

For:

```text
(name)
^
i
```

Initially:

```text
i = 0
j = 1
```

Then `j` moves:

```text
( n a m e )
  ↑
  j
```

until:

```java
s.charAt(j) == ')'
```

Now we know:

```text
i = position of '('
j = position of ')'
```

---

# 10. Extract the key

```java
String key = s.substring(i + 1, j);
```

This is important.

Suppose:

```text
(name)
```

Positions:

```text
0 1 2 3 4 5
( n a m e )
```

We don't want:

```text
(name)
```

We only want:

```text
name
```

Therefore:

```java
substring(i + 1, j)
```

means:

```java
substring(1, 5)
```

which gives:

```text
name
```

Remember:

> `substring(start, end)` includes `start` but excludes `end`.

---

# 11. Check whether the key exists

```java
if (map.containsKey(key)) {
    result.append(map.get(key));
}
```

Suppose:

```text
key = "name"
```

HashMap contains:

```text
name → bob
```

So:

```java
map.containsKey("name")
```

returns:

```text
true
```

Then:

```java
map.get("name")
```

returns:

```text
bob
```

So:

```java
result.append("bob");
```

---

# 12. What if the key doesn't exist?

Suppose:

```text
s = "hi(name)"
```

and:

```text
knowledge = [["a", "b"]]
```

The HashMap contains:

```text
a → b
```

but not:

```text
name
```

Therefore:

```java
map.containsKey("name")
```

returns:

```text
false
```

So:

```java
result.append("?");
```

The result becomes:

```text
hi?
```

---

# 13. Why do we write `i = j + 1`?

This is a very important part.

After processing:

```text
(name)
```

we have already processed the entire bracket pair.

So we don't want to process:

```text
n
a
m
e
)
```

again.

Therefore:

```java
i = j + 1;
```

moves `i` directly to the character after `')'`.

For:

```text
(name)is
     ^
     j
```

after:

```java
i = j + 1;
```

we get:

```text
(name)is
       ^
       i
```

So now we continue processing from:

```text
i
```

---

# 14. Complete dry run

Consider:

```text
s = "(name)is(age)yearsold"

knowledge = [
    ["name", "bob"],
    ["age", "two"]
]
```

HashMap:

```text
name → bob
age  → two
```

### First iteration

```text
(name)is(age)yearsold
^
i
```

Find `)`.

Key:

```text
name
```

HashMap:

```text
name → bob
```

Add:

```text
bob
```

Result:

```text
bob
```

Move `i` after `)`.

---

### Next characters

```text
(name)is(age)yearsold
      ^
      i
```

Character:

```text
i
```

Add:

```text
i
```

Result:

```text
bobi
```

Then process:

```text
s
```

Result:

```text
bobis
```

---

### Next bracket

```text
(name)is(age)yearsold
        ^
        (
```

Extract:

```text
age
```

HashMap:

```text
age → two
```

Add:

```text
two
```

Result:

```text
bobistwo
```

Then append:

```text
yearsold
```

Final:

```text
bobistwoyearsold
```

---

# 15. Another important example

```text
s = "(a)(a)(a)aaa"

knowledge = [
    ["a", "yes"]
]
```

HashMap:

```text
a → yes
```

We process:

```text
(a) → yes
(a) → yes
(a) → yes
```

But notice the final:

```text
aaa
```

These are **not inside brackets**, so they remain unchanged.

Therefore:

```text
yesyesyesaaa
```

This is why we only replace characters when we encounter `'('`.

---

# 16. Why HashMap is needed?

Without a HashMap, for every key we would have to search through the entire `knowledge` array.

For example:

```text
s contains 100,000 bracket pairs
knowledge contains 100,000 keys
```

If we search the array every time, it could become approximately:

```text
100,000 × 100,000
```

operations in the worst case.

With a HashMap, lookup is approximately:

```text
O(1)
```

on average.

So the HashMap makes the solution efficient.

---

# 17. Time Complexity

Let:

* `N` = length of `s`
* `K` = number of knowledge pairs

### Building HashMap

```java
for (...)
```

takes:

```text
O(K)
```

### Processing string

We scan the string from left to right.

Although we have a nested-looking:

```java
while (s.charAt(j) != ')')
```

we don't repeatedly scan the same characters. `j` moves through each bracket pair once.

Therefore:

```text
O(N)
```

### Total

```text
O(N + K)
```

### Space

HashMap stores all knowledge:

```text
O(K)
```

and the resulting string can take:

```text
O(N)
```

So overall:

```text
O(N + K)
```

---

## 18. The core pattern to remember

For this problem, remember this simple pattern:

```text
1. Put knowledge into HashMap
              ↓
2. Traverse string
              ↓
3. Normal character?
       ↓ yes
   append it
              ↓
4. '(' found?
              ↓
5. Find ')'
              ↓
6. Extract key
              ↓
7. HashMap lookup
       ↓              ↓
    found          not found
       ↓              ↓
    value            "?"
              ↓
8. Jump after ')'
```

The most important ideas are **HashMap + StringBuilder + two pointers (`i` and `j`)**.
