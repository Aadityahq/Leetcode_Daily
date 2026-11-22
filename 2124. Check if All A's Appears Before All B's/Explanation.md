# **LeetCode 2124. Check if All A's Appears Before All B's**

Easy

String

## **1. Problem Explanation**

We are given a string `s` containing only two characters:

- `'a'`
- `'b'`

We need to check whether **all occurrences of&#x20;**`'a'`**&#x20;appear before all occurrences of&#x20;**`'b'`**.**

In simple words:

- Once we encounter a `'b'`, we should never encounter an `'a'` again.
- If an `'a'` appears after a `'b'`, return `false`.
- Otherwise, return `true`.

### **Examples**

**Example 1:**

```
Input: s = "aaabbb"
Output: true
```

Explanation:

```
a a a b b b
0 1 2 3 4 5
```

All `a` characters appear before `b`. Therefore, return `true`.

**Example 2:**

```
Input: s = "abab"
Output: false
```

Explanation:

```
a b a b
0 1 2 3
```

At index 1, we encounter `b`, but at index 2, we encounter `a`.

This violates the condition. Therefore, return `false`.

**Example 3:**

```
Input: s = "bbb"
Output: true
```

Explanation:

There is no `a` in the string.

Therefore, the condition is satisfied, and we return `true`.

## **2. Java Solution (Optimal Approach)**

**Approach: Single Traversal**

The main observation is:

Once we encounter the first `'b'`, no `'a'` should appear afterward.

We can solve this problem using a simple loop.

```

class Solution {
    public boolean checkString(String s) {

        boolean foundB = false;

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == 'b') {
                foundB = true;
            }

            if (s.charAt(i) == 'a' && foundB) {
                return false;
            }
        }

        return true;
    }
}

```

## **3. Code Explanation (Line by Line)**

Let's understand why we are using this logic.

### **Step 1: Create a boolean variable**

```
boolean foundB = false;
```

We create a boolean variable to track whether we have encountered `'b'`.

Initially, it is `false` because we haven't visited any character yet.

- `false` → We haven't encountered `b`.
- `true` → We have already encountered `b`.

### **Step 2: Traverse the string**

```
for (int i = 0; i < s.length(); i++)
```

We use a `for` loop to visit every character in the string.

We use:

```
s.charAt(i)
```

to access the character at index `i`.

### **Step 3: Check whether the character is 'b'**

```
if (s.charAt(i) == 'b') {
    foundB = true;
}
```

Whenever we find `'b'`, we update `foundB` to `true`.

This means that we have entered the `'b'` section of the string.

### **Step 4: Check whether 'a' appears after 'b'**

```
if (s.charAt(i) == 'a' && foundB) {
    return false;
}
```

This is the most important condition.

We check two things:

1. Is the current character `'a'`?
2. Have we already encountered `'b'`?

If both conditions are true, it means an `'a'` appears after a `'b'`.

Therefore, we immediately return `false`.

### **Step 5: Return true**

```
return true;
```

If the loop completes without finding an invalid arrangement, we return `true`.

## **4. Dry Run**

Let's take this example:

```
s = "aababb"
```

Initially:

```
foundB = false;
```

Now, let's execute the loop.

| Index | Character | foundB | Action       |
| ----- | --------- | ------ | ------------ |
| 0     | a         | false  | Continue     |
| 1     | a         | false  | Continue     |
| 2     | b         | true   | Mark b found |
| 3     | a         | true   | Return false |

Output: false

Why?

Because we encountered `'b'` at index 2, but another `'a'` appeared at index 3.

## **5. Visual Understanding**

Think of the string as two sections:

**Valid arrangement**

### **a**

### **a**

### **a**

### **b**

### **b**

### **b**

A section

B section

Valid – true

**Invalid arrangement**

### **a**

### **a**

### **b**

### **a**

### **b**

### **b**

The third character is b, but the fourth character is a.

Invalid – false

## **6. Complexity Analysis**

Let `n` be the length of the string.

| Complexity | Value | Explanation                       |
| ---------- | ----- | --------------------------------- |
| Time       | O(n)  | We traverse the string once.      |
| Space      | O(1)  | We use only one boolean variable. |

**Why O(n)?**

In the worst case, we visit every character in the string.

**Why O(1)?**

We don't create another string, array, or collection. We only use a boolean variable.

## **7. Alternative Approach (Using indexOf)**

Java also provides a built-in method called `indexOf()`.

```
class Solution {
    public boolean checkString(String s) {

        return s.indexOf('b') == -1 ||
               s.indexOf('a') > s.indexOf('b');
    }
}
```

Explanation:

- `s.indexOf('b')` gives the first occurrence of `b`.
- `s.indexOf('a')` gives the first occurrence of `a`.

However, checking only the first occurrence of each character is not sufficient for this problem.

For example, `"abba"` has its first `a` before its first `b`, but it is invalid.

So the above alternative is incorrect.

**Correct indexOf approach:**

```
class Solution {
    public boolean checkString(String s) {

        int lastA = s.lastIndexOf('a');
        int firstB = s.indexOf('b');

        return firstB == -1 || lastA < firstB;
    }
}
```

This works because the last `a` must appear before the first `b`.

## **8. Important Interview Takeaways**

Remember these concepts:

1. Use a boolean variable to track whether a character has appeared.
2. Identify the condition that makes the string invalid.
3. Return immediately when an invalid condition is found.
4. Single traversal problems can often be solved in O(n) time and O(1) space.

**The key logic to remember:**

```
if (current == 'b') {
    foundB = true;
}

if (current == 'a' && foundB) {
    return false;
}
```

## **9. Test Your Understanding**

Try answering these without running the code.

**Q1.** `{q.q}`

true

false



**Q2.** `{q.q}`

true

false



**Q3.** `{q.q}`

true

false



**Q4.** `{q.q}`

true

false



0/4 attempted

Reset

**Final takeaway:** The easiest way to solve this problem is to remember one rule: **After encountering the first&#x20;**`b`**, never allow another&#x20;**`a`**.**