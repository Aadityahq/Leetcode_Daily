# 1935. Maximum Number of Words You Can Type

## Problem
There is a malfunctioning keyboard where some letter keys do not work.  
Given a string `text` of words separated by a single space and a string `brokenLetters` of all distinct broken keys, return the number of words in `text` you can fully type.

---

## Examples

### Example 1
Input: text = "hello world", brokenLetters = "ad"
Output: 1
Explanation: We cannot type "world" because the 'd' key is broken.



### Example 2
Input: text = "leet code", brokenLetters = "lt"
Output: 1
Explanation: We cannot type "leet" because the 'l' and 't' keys are broken.


### Example 3
Input: text = "leet code", brokenLetters = "e"
Output: 0
Explanation: Both words contain 'e', so neither can be typed.


---

## Approach
1. Store all broken letters in a **HashSet** for quick lookup.  
2. Split the given `text` into words.  
3. For each word:
   - Traverse its characters.  
   - If any character is broken, mark the word as not typeable.  
   - Otherwise, count it as valid.  
4. Return the total number of typeable words.

---

## Complexity
- **Time:** O(n), where `n` is the total number of characters in `text`.  
- **Space:** O(1), since broken letters are at most 26.  

---

## Snippet
The core logic checks whether a word contains any broken letter:

```java

boolean canType = true;
for (char ch : word.toCharArray()) {
    if (brokenSet.contains(ch)) {
        canType = false;
        break;
    }
}
if (canType) count++;