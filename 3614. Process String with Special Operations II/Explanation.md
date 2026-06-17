### Intuition

The direct simulation of the string is impossible because:

* `s.length ≤ 10^5`
* Final string length can be up to `10^15`

Operations like `#` (duplicate) can make the string enormous.

Since we only need the **kth character**, we should avoid constructing the final string.

The key idea is:

1. Compute the **length of the result after each operation**.
2. Traverse the operations **backwards** to determine which original character ends up at index `k`.

This is similar to reversing the operations.

---

## Understanding the Operations

Suppose before processing `s[i]`, the current result length is `prev`.

| Operation | New Length         |
| --------- | ------------------ |
| letter    | `prev + 1`         |
| `*`       | `max(0, prev - 1)` |
| `#`       | `prev * 2`         |
| `%`       | `prev`             |

Notice that `%` only reverses the string, so the length does not change.

We'll store these lengths in an array:

```text
len[i] = length of result after processing s[0...i]
```

---

## Reverse Mapping

Let `k` be the index we want in the final string.

Process `s` from right to left.

### Case 1: Letter

If `s[i]` is a letter:

* Before this letter, length was `len[i] - 1`.
* This letter occupies index `len[i] - 1`.

So:

```text
if k == len[i] - 1 → answer is s[i]
otherwise → k remains unchanged
```

---

### Case 2: `*`

`*` removes the last character.

If after processing `*` the length is `L`, then before `*` the length was `L + 1`.

The remaining characters keep their indices.

So `k` stays unchanged.

---

### Case 3: `#`

Suppose before duplication the string length was `L`.

After duplication:

```text
result = original + original
length = 2L
```

If we want index `k` in the duplicated string:

* If `k < L`, it belongs to the first half.
* Otherwise, it belongs to the second half.

Both halves are identical:

```text
k = k % L
```

---

### Case 4: `%`

Suppose current length is `L`.

Reversal transforms:

```text
index x → L - 1 - x
```

To reverse the operation:

```text
k = L - 1 - k
```

---

## Example

```text
s = "a#b%*"
k = 1
```

### Forward lengths

| i | char | length |
| - | ---- | ------ |
| 0 | a    | 1      |
| 1 | #    | 2      |
| 2 | b    | 3      |
| 3 | %    | 3      |
| 4 | *    | 2      |

Final length = 2.

Now traverse backwards:

```text
k = 1
```

* `*` → k unchanged
* `%` → k = 3 - 1 - 1 = 1
* `b` → position of b is 2, k != 2
* `#` → previous length = 1, k = 1 % 1 = 0
* `a` → position of a is 0, k == 0

Answer = `'a'`

---

## Algorithm

### Forward pass

Compute `len[i]`.

### Check bounds

If:

```text
k >= len[n - 1]
```

return `'.'`.

### Backward pass

For each character from right to left:

* letter → check whether it created index `k`
* `*` → do nothing
* `#` → `k %= previousLength`
* `%` → `k = currentLength - 1 - k`

---

## Complexity Analysis

* Forward pass: `O(n)`
* Backward pass: `O(n)`
* Extra space: `O(n)`

```text
Time Complexity:  O(n)
Space Complexity: O(n)
```

---

## Java Solution

```java
class Solution {
    public char processStr(String s, long k) {
        int n = s.length();
        long[] len = new long[n];

        // Forward pass: compute lengths
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            long prev = (i == 0) ? 0 : len[i - 1];

            if (c >= 'a' && c <= 'z') {
                len[i] = prev + 1;
            } else if (c == '*') {
                len[i] = Math.max(0, prev - 1);
            } else if (c == '#') {
                len[i] = prev * 2;
            } else { // c == '%'
                len[i] = prev;
            }
        }

        // Out of bounds
        if (k >= len[n - 1]) {
            return '.';
        }

        // Backward pass
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);

            long currLen = len[i];
            long prevLen = (i == 0) ? 0 : len[i - 1];

            if (c >= 'a' && c <= 'z') {
                // This character was appended at index prevLen
                if (k == prevLen) {
                    return c;
                }
            } else if (c == '#') {
                if (prevLen > 0) {
                    k %= prevLen;
                }
            } else if (c == '%') {
                k = currLen - 1 - k;
            }
            // '*' does not affect k
        }

        return '.';
    }
}
```

### Why does this work?

The forward pass tells us **how large the string is after every operation**.

The backward pass answers:

> "If I need character `k` after operation `i`, which index did it come from before operation `i`?"

By repeatedly mapping `k` backward, we eventually reach the exact letter that created that position.

This avoids building strings of size up to `10^15`, making the solution efficient enough for the constraints.
