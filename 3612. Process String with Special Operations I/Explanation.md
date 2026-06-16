## Understanding the Problem

You are given a string `s` containing:

* Lowercase letters: `'a'` to `'z'`
* Special characters:

  * `'*'` → remove the last character from the current result (if any)
  * `'#'` → duplicate the current result and append it to itself
  * `'%'` → reverse the current result

You process the string **from left to right** and build a new string called `result`.

At the end, return `result`.

---

## Example Walkthrough

Input:

```text
s = "a#b%*"
```

Process each character:

| Character | Operation             | Result  |
| --------- | --------------------- | ------- |
| `'a'`     | Append `'a'`          | `"a"`   |
| `'#'`     | Duplicate             | `"aa"`  |
| `'b'`     | Append `'b'`          | `"aab"` |
| `'%'`     | Reverse               | `"baa"` |
| `'*'`     | Remove last character | `"ba"`  |

Final answer:

```text
"ba"
```

---

## Key Observation

We need a data structure that efficiently supports:

* Appending characters
* Removing the last character
* Duplicating the entire string
* Reversing the string

In Java, **`StringBuilder`** is ideal because:

* `append()` → efficient
* `deleteCharAt()` → easy removal from the end
* `reverse()` → built-in
* Converting to a string for duplication is straightforward

Since:

```text
s.length <= 20
```

the result size remains manageable, so duplicating the entire string each time is acceptable.

---

## Approach

Iterate through every character in `s`.

### Rules

* If it's a letter → append it.
* If it's `'*'` → remove the last character if the result isn't empty.
* If it's `'#'` → append a copy of the current result.
* If it's `'%'` → reverse the current result.

---

## Java Solution

```java
class Solution {
    public String processStr(String s) {
        StringBuilder result = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch >= 'a' && ch <= 'z') {
                result.append(ch);

            } else if (ch == '*') {
                if (result.length() > 0) {
                    result.deleteCharAt(result.length() - 1);
                }

            } else if (ch == '#') {
                result.append(result.toString());

            } else { // ch == '%'
                result.reverse();
            }
        }

        return result.toString();
    }
}
```

---

## Why Does This Work?

We process the input exactly as described.

For each character:

* Letters directly contribute to the result.
* `'*'` undoes the most recent append (if possible).
* `'#'` duplicates the current state.
* `'%'` transforms the current state by reversing it.

Because operations are applied **in order**, the `StringBuilder` always contains the correct intermediate result.

---

## Dry Run

Input:

```text
s = "z*#"
```

### Start

```text
result = ""
```

### `'z'`

Append:

```text
result = "z"
```

### `'*'`

Remove last character:

```text
result = ""
```

### `'#'`

Duplicate empty string:

```text
result = ""
```

Final answer:

```text
""
```

---

## Complexity Analysis

Let `n` be the length of `s` and `m` be the maximum length of `result`.

* Appending a letter: `O(1)`
* Removing last character: `O(1)`
* Duplicating: `O(m)`
* Reversing: `O(m)`

Overall:

```text
Time Complexity: O(n × m)
```

Since:

```text
n ≤ 20
```

this is easily efficient enough.

```text
Space Complexity: O(m)
```

for storing the resulting string.

---

## Alternative Thought

For much larger constraints, repeatedly duplicating and reversing strings could become expensive, and a more advanced structure (like a deque with lazy reversal) might be needed.

However, with `s.length ≤ 20`, the straightforward `StringBuilder` solution is the simplest and best choice.
