# **LeetCode 2914. Minimum Number of Changes to Make Binary String Beautiful**



Medium

Java

Greedy



## **1. Understanding the problem**

We are given a binary string `s` containing only `0` and `1`.

Our task is to make the string **beautiful** using the minimum number of character changes.

A string is beautiful if:

1. We can divide it into one or more substrings.
2. Every substring must have an even length.
3. Every substring must contain only the same character (`0` or `1`).

In simple words, we need to divide the string into groups of consecutive equal characters, and every group must have an even length.

We can change any `0` to `1` or any `1` to `0`.

Our goal is to return the minimum number of changes.

## **2. Important observation**

Since every substring must have an even length, we can always divide the string into pairs of characters:

- Index `0` and index `1`
- Index `2` and index `3`
- Index `4` and index `5`
- And so on.

Therefore, each pair must contain identical characters.

For example:

`"11000011"`

We can divide it into:

`"11 | 00 | 00 | 11"`

Every substring has an even length and contains identical characters.

So, the string is already beautiful.

**Key observation:** We only need to check every two consecutive characters. If they are different, we need exactly one change to make them equal.

## **3. Java solution (Greedy approach)**

```

class Solution {
    public int minChanges(String s) {

        int changes = 0;

        for (int i = 0; i < s.length(); i += 2) {

            if (s.charAt(i) != s.charAt(i + 1)) {
                changes++;
            }
        }

        return changes;
    }
}

```

## **4. Dry run (step-by-step)**

Let's understand using Example 1.

**Input:**

```
s = "1001"
```

Initially:

`changes = 0`

We check the string in pairs.

| Pair | Characters | Same? | Changes |
| ---- | ---------- | ----- | ------- |
| 1    | `1 0`      | No    | 1       |
| 2    | `0 1`      | No    | 1       |

Total changes = 2.

**Visual representation**

**1 0**

Different

**0 1**

Different

**1 1**

Same

**0 0**

Same

**Final string: 1100**

# **2 changes**

The final string is:

`"1100"`

We can partition it as:

`"11 | 00"`

Both substrings have even lengths and contain identical characters.

Therefore, the answer is `2`.

## **5. Why does this approach work?**

Let's understand the logic behind the greedy approach.

Since every valid substring must have an even length, its starting and ending positions must have the same parity.

That means every valid partition can be represented by pairs of positions:

- `(0, 1)`
- `(2, 3)`
- `(4, 5)`
- `(6, 7)`

If both characters in a pair are equal, no changes are required.

If they are different, at least one character must be changed.

For example:

- `00` → 0 changes.
- `11` → 0 changes.
- `01` → 1 change.
- `10` → 1 change.

There is no benefit in changing more than one character in a pair.

Therefore, counting the pairs with different characters gives the minimum number of changes.

**Why greedy?**

We independently make every pair equal using the minimum possible changes. Since pairs do not overlap, making one pair equal does not affect another pair.

Hence, this greedy approach always gives the optimal answer.

## **6. Complexity analysis**

Let `n` be the length of the string.

| Complexity | Explanation                      |
| ---------- | -------------------------------- |
| Time       | O(n) – We visit each pair once.  |
| Space      | O(1) – We use only one variable. |

Optimal Solution

## **7. More examples**

| Input      | Pairs            | Output |
| ---------- | ---------------- | ------ |
| `"1001"`   | `10`, `01`       | 2      |
| `"10"`     | `10`             | 1      |
| `"0000"`   | `00`, `00`       | 0      |
| `"1100"`   | `11`, `00`       | 0      |
| `"010101"` | `01`, `01`, `01` | 3      |
| `"111000"` | `11`, `10`, `00` | 1      |

## **8. Key takeaways**

- This problem can be solved using a Greedy approach.
- Always process the string two characters at a time.
- If both characters are different, increment `changes`.
- No need for DP, recursion, or extra data structures.
- The solution runs in O(n) time and O(1) space.

**Remember this simple logic:**

```
for (int i = 0; i < s.length(); i += 2) {
    if (s.charAt(i) != s.charAt(i + 1)) {
        changes++;
    }
}
```

**The main trick:** Because every valid substring has an even length, checking fixed consecutive pairs is sufficient to find the minimum changes.