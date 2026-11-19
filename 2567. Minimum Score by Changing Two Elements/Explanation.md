# **LeetCode 2567. Minimum Score by Changing Two Elements**



Medium

Java

Sorting + Greedy



## **1. Understanding the problem**

You are given an integer array `nums`.

You need to change **exactly two elements** of the array to any integer values you want.

After changing the elements, calculate two things:

1. **Low Score:** Minimum absolute difference between any two elements.
2. **High Score:** Maximum absolute difference between any two elements.

The final score is:

\\[ Score = LowScore + HighScore \\]

Your task is to make the final score as small as possible.

### **Example 1**

```
nums = [1, 4, 7, 8, 5]
```

We can change two elements:

```
Original: [1, 4, 7, 8, 5]
Changed:  [6, 6, 7, 8, 5]
```

Now:

- Minimum difference = |6 - 6| = 0
- Maximum difference = |8 - 5| = 3

Therefore:

\\[ Score = 0 + 3 = 3 \\]

Output:

```
3
```

### **Example 2**

```
nums = [1, 4, 3]
```

Change two elements to `1`.

```
Original: [1, 4, 3]
Changed:  [1, 1, 1]
```

- Minimum difference = 0
- Maximum difference = 0

Output:

```
0
```

## **2. Approach: Sorting + Greedy**

The key observation is that **we only need to minimize the maximum difference**.

Why?

Because we can always make the minimum difference equal to `0` by changing two elements to the same value.

Therefore:

\\[ MinimumScore = MinimumHighScore + 0 \\]

So, we only need to minimize the maximum difference.

### **Step 1: Sort the array**

Consider:

```
nums = [1, 4, 7, 8, 5]
```

After sorting:

```
[1, 4, 5, 7, 8]
```

Why sort?

Because the maximum difference is:

\\[ MaximumDifference = MaximumElement - MinimumElement \\]

After sorting, the smallest and largest elements are easy to identify.

### **Step 2: Think about changing two elements**

We have two changes available.

There are only three useful possibilities:

**Case 1: Change the two smallest elements**

Remove the first two elements from consideration.

```
[1, 4, 5, 7, 8]
 X  X
```

Remaining range:

\\[ 8-5=3 \\]

**Case 2: Change the smallest and largest elements**

```
[1, 4, 5, 7, 8]
 X           X
```

Remaining range:

\\[ 7-4=3 \\]

**Case 3: Change the two largest elements**

```
[1, 4, 5, 7, 8]
          X  X
```

Remaining range:

\\[ 5-1=4 \\]

We calculate all three possibilities and select the minimum.

## **3. Java Solution**

```

import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {

        // Step 1: Sort the array
        Arrays.sort(nums);

        int n = nums.length;

        // Step 2: Calculate all three possibilities

        int case1 = nums[n - 1] - nums[2];

        int case2 = nums[n - 2] - nums[1];

        int case3 = nums[n - 3] - nums[0];

        // Step 3: Return the minimum score
        return Math.min(case1,
               Math.min(case2, case3));
    }
}

```

## **4. Dry Run: Step-by-step execution**

Let's understand the code using Example 1.

**Input:**

```
nums = [1, 4, 7, 8, 5]
```

**Step 1: Sorting**

```
[1, 4, 5, 7, 8]
```

Array length:

```
n = 5
```

**Step 2: Calculate Case 1**

Change two smallest elements.

```
int case1 = nums[n - 1] - nums[2];
```

Substitute the values:

```
case1 = nums[4] - nums[2]
      = 8 - 5
      = 3
```

**Step 3: Calculate Case 2**

Change one smallest and one largest element.

```
int case2 = nums[n - 2] - nums[1];
```

```
case2 = nums[3] - nums[1]
      = 7 - 4
      = 3
```

**Step 4: Calculate Case 3**

Change two largest elements.

```
int case3 = nums[n - 3] - nums[0];
```

```
case3 = nums[2] - nums[0]
      = 5 - 1
      = 4
```

**Step 5: Find minimum**

```
case1 = 3
case2 = 3
case3 = 4
```

\\[ Answer = \min(3,3,4)=3 \\]

Output: 3

## **5. Why are there only three cases?**

This is the most important part of the problem.

After sorting the array:

```
[a, b, c, d, e, f, g]
```

We can change any two elements.

But to minimize the difference between the largest and smallest remaining unchanged elements, we should eliminate the extreme values.

There are three possible distributions of two changes:

| Changes                    | Remaining range       |
| -------------------------- | --------------------- |
| Two smallest               | `nums[n-1] - nums[2]` |
| One smallest + one largest | `nums[n-2] - nums[1]` |
| Two largest                | `nums[n-3] - nums[0]` |

These cover every useful possibility.

Changing an element somewhere in the middle instead of an extreme cannot improve the range.

Also, we can assign the changed elements the same value, so the minimum difference becomes zero.

That's why these three cases are sufficient.

## **6. Complexity Analysis**

| Complexity | Explanation                                                                                             |
| ---------- | ------------------------------------------------------------------------------------------------------- |
| Time       | \\(O(n \log n)\\)                                                                                       |
| Space      | \\(O(1)\\) auxiliary space for the calculations; Java's sorting implementation may use additional space |

- Sorting takes \\(O(n \log n)\\).
- Calculating three cases takes \\(O(1)\\).
- Finding the minimum takes \\(O(1)\\).

The solution efficiently handles the constraint of up to \\(10^5\\) elements.

## **7. Important interview takeaways**

Remember these three things:

1. Whenever a problem asks you to minimize the difference between the maximum and minimum elements, think about sorting.
2. When you can modify `k` elements, consider removing the `k` smallest, a combination of smallest and largest, or the `k` largest.
3. Always look for a way to simplify the objective. Here, making the minimum difference zero reduces the problem to minimizing the maximum difference.

## **8. Test your understanding**

**Check your understanding**

**1. Why do we sort the array?**

To find the middle element

To identify the smallest and largest elements easily

To remove duplicate elements

**2. Why can we ignore the low score?**

It is always zero after changing two elements

It is always equal to the high score

It is always negative

**3. What is the time complexity?**

O(n)

O(n²)

O(n log n)

Submit answersReset



**My suggestion:** Try implementing this solution yourself without looking at the code. Focus especially on understanding why we have exactly three cases. That greedy observation is the most important interview concept in this problem.