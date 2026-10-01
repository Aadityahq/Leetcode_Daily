# LeetCode 20: Valid Parentheses

**Difficulty:** Easy  
**Topics:** String, Stack

## 🧠 Problem Intuition

We are given a string `s` containing only three types of brackets:

- `()` — Parentheses
- `{}` — Curly braces
- `[]` — Square brackets

Our task is to determine whether the brackets are valid.

A string is valid if:

1. Every opening bracket has a corresponding closing bracket of the same type.
2. Brackets are closed in the correct order.
3. Every closing bracket has a matching opening bracket.

### Examples

```text
Input: s = "()"
Output: true
```

```text
Input: s = "()[]{}"
Output: true
```

```text
Input: s = "(]"
Output: false
```

```text
Input: s = "([])"
Output: true
```

```text
Input: s = "([)]"
Output: false
```

---

## 💡 Key Observation

The most recently opened bracket must be closed first.

For example, consider:

```text
s = "([])"
```

The opening brackets appear in this order:

1. `(`
2. `[` 

Therefore, `[` must be closed before `(`.

This follows the **LIFO (Last In, First Out)** principle, which is the main property of a Stack.

A stack allows us to keep track of opening brackets and match each closing bracket with the most recent unmatched opening bracket.

### Why use a Stack?

Consider the string:

```text
s = "([)]"
```

Although the string contains matching numbers of opening and closing brackets, it is invalid.

When we encounter `)`, the most recent opening bracket is `[`, which does not match `)`.

Therefore, the string is invalid.

---

## 🚀 Approach: Using Stack

We use a stack to store opening brackets and match them with closing brackets.

### Algorithm

1. Create an empty stack to store opening brackets.
2. Traverse the string character by character.
3. If the current character is an opening bracket (`(`, `[`, `{`), push it onto the stack.
4. If the current character is a closing bracket:
   - If the stack is empty, return `false` because there is no opening bracket to match it.
   - Pop the top element from the stack.
   - Check whether the popped opening bracket matches the current closing bracket.
   - If they do not match, return `false`.
5. After processing all characters, return `true` if the stack is empty. Otherwise, return `false`.

---

## ☕ Java Solution

```java
import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (char ch : s.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();

                if (ch == ')' && top != '(') {
                    return false;
                }

                if (ch == ']' && top != '[') {
                    return false;
                }

                if (ch == '}' && top != '{') {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
```

---

## 📝 Dry Run

Let's understand the solution using the following example:

```text
Input: s = "{[()]}"
```

Initially, the stack is empty.

| Step | Character | Operation | Stack |
|---|---|---|---|
| 1 | `{` | Push `{` | `{` |
| 2 | `[` | Push `[` | `{ [` |
| 3 | `(` | Push `(` | `{ [ (` |
| 4 | `)` | Matches `(`, so pop | `{ [` |
| 5 | `]` | Matches `[`, so pop | `{` |
| 6 | `}` | Matches `{`, so pop | Empty |

After processing all characters, the stack is empty.

Therefore:

```text
Output: true
```

### Invalid Example

Consider:

```text
Input: s = "([)]"
```

| Step | Character | Operation | Stack |
|---|---|---|---|
| 1 | `(` | Push `(` | `(` |
| 2 | `[` | Push `[` | `( [` |
| 3 | `)` | Pop `[`, but it does not match `)` | `( ` |

At step 3, the current closing bracket is `)`, but the top opening bracket is `[`.

Since the bracket types do not match, the algorithm immediately returns `false`.

---

## 🔍 Why Do We Check `stack.isEmpty()`?

```java
if (stack.isEmpty()) {
    return false;
}
```

Suppose the input is:

```text
s = ")"
```

The string begins with a closing bracket, but no opening bracket exists.

Therefore, the stack is empty when we encounter `)`.

We return `false` because every closing bracket must have a corresponding opening bracket.

This check also prevents us from calling `pop()` on an empty stack.

---

## 🔍 Why Do We Return `stack.isEmpty()` at the End?

```java
return stack.isEmpty();
```

Consider:

```text
s = "((("
```

All three characters are opening brackets, so they are pushed onto the stack.

After processing the entire string, the stack still contains three opening brackets.

No closing brackets were found to match them.

Therefore, the stack is not empty, and the method returns `false`.

**Important:** Matching every closing bracket during traversal is not enough. We must also ensure that no unmatched opening brackets remain.

---

## ✅ Correctness

The algorithm correctly determines whether a string contains valid brackets because:

1. Every opening bracket is pushed onto the stack.
2. Every closing bracket must match the most recent unmatched opening bracket.
3. If the stack is empty when a closing bracket appears, the string is invalid.
4. If a closing bracket does not match the top element, the string is invalid.
5. If the stack is not empty after processing the entire string, some opening brackets remain unmatched.

Therefore, the algorithm returns `true` exactly when all brackets are correctly matched and ordered.

---

## ⏱️ Complexity Analysis

Let `n` be the length of the string.

- **Time Complexity:** `O(n)` — We traverse the string once, and each character is pushed onto or popped from the stack at most once.
- **Space Complexity:** `O(n)` — In the worst case, the stack stores all opening brackets, such as in the string `"((((("`.

---

## 🎯 Key Takeaways

- A Stack follows the **LIFO (Last In, First Out)** principle.
- Opening brackets are pushed onto the stack.
- Closing brackets must match the most recent unmatched opening bracket.
- An empty stack before a closing bracket indicates an invalid string.
- The stack must be empty after processing the entire string.
- The solution runs in `O(n)` time and uses `O(n)` auxiliary space.

**Core concept:** The last bracket opened must be the first bracket closed.
