## Understanding the Problem

You are given:

* `words[]` → an array of lowercase words.
* `weights[]` → weight of each letter:

  * `weights[0]` = weight of `'a'`
  * `weights[1]` = weight of `'b'`
  * ...
  * `weights[25]` = weight of `'z'`

### Step 1: Calculate Word Weight

For each word, add the weights of all its characters.

Example:

`word = "abcd"`

```
a -> 5
b -> 3
c -> 12
d -> 14

Total = 5 + 3 + 12 + 14 = 34
```

---

### Step 2: Take Modulo 26

```
34 % 26 = 8
```

---

### Step 3: Reverse Alphabet Mapping

The problem uses reverse alphabetical order:

| Mod | Letter |
| --- | ------ |
| 0   | z      |
| 1   | y      |
| 2   | x      |
| ... | ...    |
| 25  | a      |

Notice:

```
0 -> z
1 -> y
2 -> x
...
25 -> a
```

This can be computed as:

```java
(char)('z' - mod)
```

Example:

```
mod = 8

'z' - 8 = 'r'
```

So:

```
8 -> r
```

---

## Approach

For every word:

1. Compute total weight.
2. Compute `mod = totalWeight % 26`.
3. Convert using reverse mapping:

   ```java
   char mapped = (char)('z' - mod);
   ```
4. Append to answer.

---

## Dry Run

### Input

```java
words = ["a", "b", "c"]

weights = [1,1,1,...]
```

### Word "a"

```
weight = 1
mod = 1

'z' - 1 = 'y'
```

### Word "b"

```
weight = 1
mod = 1

'y'
```

### Word "c"

```
weight = 1
mod = 1

'y'
```

Answer:

```
"yyy"
```

---

## Java Solution

```java
class Solution {
    public String mapWordWeights(String[] words, int[] weights) {
        StringBuilder ans = new StringBuilder();

        for (String word : words) {
            int totalWeight = 0;

            for (char ch : word.toCharArray()) {
                totalWeight += weights[ch - 'a'];
            }

            int mod = totalWeight % 26;
            ans.append((char)('z' - mod));
        }

        return ans.toString();
    }
}
```

---

## Why Does `'z' - mod` Work?

Reverse mapping is:

```
0 -> z
1 -> y
2 -> x
...
25 -> a
```

ASCII values:

```
z = 122
y = 121
x = 120
...
a = 97
```

So:

```
mod = 0  => 'z' - 0 = z
mod = 1  => 'z' - 1 = y
mod = 2  => 'z' - 2 = x
...
mod = 25 => 'z' - 25 = a
```

Exactly the mapping required.

---

## Complexity Analysis

Let:

* `n = words.length`
* `m = average length of a word`

### Time Complexity

We visit every character once:

[
O(\text{total characters})
]

At most:

[
100 \times 10 = 1000
]

So effectively **O(N × M)**.

### Space Complexity

Only the output string builder is used.

[
O(n)
]

(where `n` characters are stored in the answer).
