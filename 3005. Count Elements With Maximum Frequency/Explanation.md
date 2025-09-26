# 3005. Count Elements With Maximum Frequency

## Problem Statement
You are given an array `nums` consisting of positive integers.

Return the **total frequencies** of elements in `nums` such that those elements all have the **maximum frequency**.

The frequency of an element is the number of times it appears in the array.

---

## Examples

### Example 1
Input: nums = [1,2,2,3,1,4]
Output: 4
Explanation:

Frequency of 1 = 2

Frequency of 2 = 2

Frequency of 3 = 1

Frequency of 4 = 1

Maximum frequency = 2
Elements with max frequency = {1, 2}
Their total occurrences = 2 + 2 = 4



### Example 2
Input: nums = [1,2,3,4,5]
Output: 5
Explanation:

All elements appear once.

Maximum frequency = 1

Elements with max frequency = {1,2,3,4,5}

Their total occurrences = 1 + 1 + 1 + 1 + 1 = 5



---

## Approach

1. **Count frequencies** of each element using a hashmap or array (since values are ≤ 100).
2. Find the **maximum frequency**.
3. Add up the counts of all elements that have this maximum frequency.

---

## Example Walkthrough

Input:
nums = [1,2,2,3,1,4]

Step 1: Count frequencies:
1 → 2 times
2 → 2 times
3 → 1 time
4 → 1 time



Step 2: Maximum frequency = 2  

Step 3: Elements with max frequency = 1 and 2  
Their total frequency = 2 + 2 = 4  

✅ Output = 4

---

## Complexity
- Time: **O(n)** (one pass to count, one pass to sum).
- Space: **O(k)** (k = range of numbers, at most 100).

---