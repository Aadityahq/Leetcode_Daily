# **LeetCode 3041 – Maximize Consecutive Elements in an Array After Modification**



Hard

Sorting

Dynamic Programming



## **1. Problem Explanation**

You are given an integer array `nums` containing positive integers.

You are allowed to **increase any element by at most 1**.

This means each element has two possible choices:

- Keep its original value.
- Increase its value by 1.

After modification, you need to select the maximum number of elements such that their values form a consecutive sequence when sorted.

**Consecutive numbers** means every next number must be exactly 1 greater than the previous number.

For example:

```
[1, 2, 3]       → Valid
[3, 4, 5]       → Valid
[1, 2, 4]       → Invalid
[1, 1, 2, 3]    → Invalid
```

Our goal is to return the maximum possible length of such a consecutive sequence.

## **2. Example**

**Input:**

```
nums = [2, 1, 5, 1, 1]
```

**Output:**

```
3
```

We can increase some elements by 1.

For example:

```
Original:  [2, 1, 5, 1, 1]
Modified:  [3, 1, 5, 2, 1]
```

After sorting:

```
[1, 1, 2, 3, 5]
```

We can select:

```
[1, 2, 3]
```

These are consecutive numbers.

Therefore, the answer is `3`.

## **3. Approach – Sorting + Dynamic Programming**

The main idea is to sort the array and maintain two possible consecutive sequences.

We use three important variables:

| Variable | Meaning                                                                   |
| -------- | ------------------------------------------------------------------------- |
| `keep`   | Maximum consecutive sequence ending with the current original value       |
| `bump`   | Maximum consecutive sequence ending with the current value increased by 1 |
| `best`   | Maximum answer found so far                                               |

We also use:

- `last`: Previous original number.
- `x`: Current number.

### **Why do we need two states?**

Suppose the current number is `3`.

We have two choices:

**Choice 1: Keep the number unchanged**

```
1, 2, 3
```

This sequence ends at `3`.

We store its length in `keep`.

**Choice 2: Increase the number by 1**

```
1, 2, 3, 4
```

This sequence ends at `4`.

We store its length in `bump`.

By maintaining both possibilities, we don't lose useful sequences that can be extended by future elements.

## **4. Java Code**

```

import java.util.Arrays;

class Solution {
    public int maxSelectedElements(int[] nums) {

        Arrays.sort(nums);

        int best = 1;
        int keep = 1;
        int bump = 1;
        int last = -10;

        for (int x : nums) {

            switch (x - last) {

                case 0 -> bump = keep + 1;

                case 1 -> {
                    keep++;
                    bump++;
                }

                case 2 -> {
                    keep = bump + 1;
                    bump = 1;
                }

                default -> keep = bump = 1;
            }

            best = Math.max(best, Math.max(keep, bump));

            last = x;
        }

        return best;
    }
}

```

## **5. Understanding the Switch Cases**

This is the most important part of the solution.

We calculate:

```
x - last
```

This tells us the difference between the current number and the previous number.

There are four possible situations.

### **Case 1: Difference = 0**

```
case 0 -> bump = keep + 1;
```

This means the current number is equal to the previous number.

Example:

```
nums = [1, 1]
```

We cannot select both as `1, 1` because duplicates are not consecutive.

But we can increase the current `1` to `2`.

So:

```
Previous: 1
Current:  1 → 2
```

Now we have:

```
[1, 2]
```

Therefore:

```
bump = keep + 1;
```

We extend the sequence represented by `keep` by increasing the duplicate element.

### **Case 2: Difference = 1**

```
case 1 -> {
    keep++;
    bump++;
}
```

This means the current number is exactly one greater than the previous number.

Example:

```
nums = [1, 2]
```

We have two possibilities:

**Keep the current number unchanged:**

```
[1, 2]
```

So `keep` increases.

**Increase the current number by 1:**

```
[1, 3]
```

If the previous element was increased to 2, we can increase the current element to 3.

So `bump` also increases.

Therefore:

```
keep++;
bump++;
```

### **Case 3: Difference = 2**

```
case 2 -> {
    keep = bump + 1;
    bump = 1;
}
```

This means the current number is two greater than the previous number.

Example:

```
nums = [1, 3]
```

We cannot directly extend a sequence ending at 1 with 3.

However, we can increase the previous 1 to 2.

Then:

```
[2, 3]
```

This creates a consecutive sequence.

Therefore:

```
keep = bump + 1;
```

Why `bump`?

Because `bump` represents the sequence where the previous number was increased by 1.

The current number can now extend that sequence without modification.

We reset:

```
bump = 1;
```

Because increasing the current number to 4 starts a different sequence, and we cannot extend the previous `bump` sequence through that choice in this transition.

### **Default Case: Difference greater than 2**

```
default -> keep = bump = 1;
```

This means there is a gap between the current and previous numbers.

Example:

```
nums = [1, 5]
```

We cannot create a consecutive sequence using both numbers.

The current number starts a new sequence.

Therefore:

```
keep = 1;
bump = 1;
```

## **6. Dry Run**

Let's understand the complete execution.

**Input:**

```
nums = [2, 1, 5, 1, 1]
```

After sorting:

```
[1, 1, 1, 2, 5]
```

Initially:

```
best = 1;
keep = 1;
bump = 1;
last = -10;
```

| x | x - last | Case    | keep | bump | best |
| - | -------- | ------- | ---- | ---- | ---- |
| 1 | 11       | Default | 1    | 1    | 1    |
| 1 | 0        | Case 0  | 1    | 2    | 2    |
| 1 | 0        | Case 0  | 1    | 2    | 2    |
| 2 | 1        | Case 1  | 2    | 3    | 3    |
| 5 | 3        | Default | 1    | 1    | 3    |

At the end:

```
return best;
```

Final answer

# **3**

Longest consecutive sequence: `[1, 2, 3]`

## **7. Why does this approach work?**

There are three important reasons:

1. **Sorting:** It allows us to process numbers in increasing order.
2. **Two DP states:** We track both possibilities: keeping a number and increasing it by 1.
3. **Greedy transitions:** Based on the difference between consecutive original values, we decide how the two states can continue.

We don't need to store the complete sequence. We only need its length.

## **8. Complexity Analysis**

| Operation          | Complexity       |
| ------------------ | ---------------- |
| Sorting            | \\(O(n\log n)\\) |
| Traversing array   | \\(O(n)\\)       |
| Updating variables | \\(O(1)\\)       |
| Auxiliary space    | \\(O(1)\\)       |

**Overall:**

- Time Complexity: \\(O(n\log n)\\)
- Auxiliary Space Complexity: \\(O(1)\\), excluding sorting implementation details.

## **9. Interview Revision – Remember These Four Cases**

| Difference (`x - last`) | Action                      | Intuition                       |
| ----------------------- | --------------------------- | ------------------------------- |
| `0`                     | `bump = keep + 1`           | Increase duplicate              |
| `1`                     | `keep++, bump++`            | Both sequences extend           |
| `2`                     | `keep = bump + 1; bump = 1` | Extend previous bumped sequence |
| Default                 | `keep = bump = 1`           | Start new sequence              |

**The most important thing to understand:** We maintain two possible answers at every step because an element can either remain unchanged or be increased by one.

This is a useful DP pattern for problems where each element has two possible states.