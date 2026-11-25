# **LeetCode 3934. Smallest Unique Subarray**

- **Difficulty:** Hard
- **Language:** Java
- **Approach:** Suffix Array + LCP + Kasai's Algorithm
- **Time Complexity:** \\(O(n\log n)\\)
- **Space Complexity:** \\(O(n)\\)

## **1. Problem Understanding**

We are given an integer array `nums`.

We need to find the **minimum length of a subarray that occurs exactly once** in the entire array.

Two subarrays are identical when:

- They have the same length.
- Their elements are in the same order.

### **Example 1**

```
nums = [3, 3, 3]
```

All possible subarrays:

| Length | Subarrays     | Occurrences |
| ------ | ------------- | ----------- |
| 1      | [3], [3], [3] | 3           |
| 2      | [3,3], [3,3]  | 2           |
| 3      | [3,3,3]       | 1           |

The subarray `[3,3,3]` occurs only once.

**Output: 3**

### **Example 2**

```
nums = [2, 1, 2, 3, 3]
```

Subarrays of length 1:

- `[2]` occurs twice.
- `[1]` occurs once.
- `[3]` occurs twice.

The subarray `[1]` is already unique.

**Output: 1**

### **Example 3**

```
nums = [1, 1, 2, 2, 1]
```

Length 1: Every element appears more than once.

Length 2:

- `[1,1]` occurs once.
- `[1,2]` occurs once.
- `[2,2]` occurs once.
- `[2,1]` occurs once.

The minimum unique subarray length is 2.

**Output: 2**

## **2. Why can't we use the brute-force approach?**

The simplest approach would be:

1. Generate all possible subarrays.
2. Store them in a HashMap.
3. Count their occurrences.
4. Find the shortest subarray with frequency 1.

But there is a problem.

The number of possible subarrays is:

\\[ \frac{n(n+1)}{2} \\]

For \\(n = 100000\\):

\\[ \frac{100000 \times 100001}{2} \\]

That's approximately 5 billion subarrays.

This is too expensive.

We need an optimized approach.

## **3. Understanding the approach**

We will use three important concepts:

1. Suffix Array
2. LCP (Longest Common Prefix)
3. Kasai's Algorithm

Let's understand them one by one.

### **Concept 1: Suffix**

A suffix is an array starting from a particular index and continuing until the end.

For example:

```
nums = [1, 2, 1, 3]
```

All suffixes are:

```
Index 0: [1, 2, 1, 3]
Index 1: [2, 1, 3]
Index 2: [1, 3]
Index 3: [3]
```

Notice that every index gives us one suffix.

For an array of size `n`, we have exactly `n` suffixes.

### **Concept 2: Suffix Array**

A Suffix Array stores the starting indices of all suffixes in lexicographical (dictionary) order.

For example:

```
Original suffixes:

[1, 2, 1, 3]
[2, 1, 3]
[1, 3]
[3]
```

After sorting:

```
[1, 2, 1, 3]  → Index 0
[1, 3]        → Index 2
[2, 1, 3]     → Index 1
[3]           → Index 3
```

Therefore:

```
sa = [0, 2, 1, 3]
```

**But why do we sort suffixes?**

Because suffixes having common starting elements will be placed close to each other.

This helps us identify repeated subarrays efficiently.

### **Concept 3: LCP (Longest Common Prefix)**

LCP tells us how many consecutive elements are common at the beginning of two suffixes.

Example:

```
A = [1, 2, 3, 4]
B = [1, 2, 5, 6]
```

Common elements:

```
[1, 2]
```

Therefore:

\\[ LCP(A,B)=2 \\]

Another example:

```
A = [1, 2, 3]
B = [1, 3, 4]
```

Only the first element matches.

\\[ LCP(A,B)=1 \\]

## **4. The most important observation**

This is the main logic of the entire problem.

Suppose we have a suffix:

```
[1, 2, 3, 4, 5]
```

We want to find its shortest prefix that does not appear in any other suffix.

Suppose its maximum LCP with another suffix is `2`.

That means another suffix shares these elements:

```
[1, 2]
```

So:

```
Length 1: [1]       → Not unique
Length 2: [1, 2]    → Not unique
Length 3: [1, 2, 3] → Unique
```

Therefore:

\\[ \boxed{RequiredLength = maxLCP + 1} \\]

But how do we find the maximum LCP?

**Important property of Suffix Arrays:**

For any suffix, its maximum LCP with another suffix is always found among its immediate previous or next suffix in lexicographical order.

Therefore:

\\[ maxLCP = \max(prevLCP,nextLCP) \\]

We don't need to compare every suffix with every other suffix.

This saves a lot of time.

## **5. Corrected Java Solution**

Here is the complete corrected implementation.

```

import java.util.*;

class Solution {

    public int smallestUniqueSubarray(int[] nums) {

        int n = nums.length;

        // Step 1: Build Suffix Array
        int[] sa = buildSuffixArray(nums);

        // Step 2: Build LCP Array
        int[] lcp = buildLCP(nums, sa);

        // Step 3: Build inverse rank array
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int answer = n;

        // Step 4: Find minimum unique subarray length
        for (int i = 0; i < n; i++) {

            int pos = rank[i];

            int prevLCP = pos > 0 ? lcp[pos] : 0;

            int nextLCP = pos < n - 1 ? lcp[pos + 1] : 0;

            int maxLCP = Math.max(prevLCP, nextLCP);

            int requiredLength = maxLCP + 1;

            // Check whether the prefix fits inside the array
            if (requiredLength <= n - i) {

                answer = Math.min(answer, requiredLength);
            }
        }

        return answer;
    }

    // Build Suffix Array using Doubling Algorithm
    private int[] buildSuffixArray(int[] nums) {

        int n = nums.length;

        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] newRank = new int[n];
        int[] temp = new int[n];

        // Coordinate Compression
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int[] unique = new int[n];
        int uniqueCount = 0;

        for (int x : sorted) {

            if (uniqueCount == 0 ||
                unique[uniqueCount - 1] != x) {

                unique[uniqueCount++] = x;
            }
        }

        int classes = uniqueCount;

        for (int i = 0; i < n; i++) {

            rank[i] = Arrays.binarySearch(
                unique, 0, uniqueCount, nums[i]
            );

            sa[i] = i;
        }

        // Doubling Algorithm
        for (int k = 1; k < n; k *= 2) {

            // Sort using second half
            countingSort(sa, temp, rank, k, classes, n);

            // Sort using first half
            countingSort(temp, sa, rank, 0, classes, n);

            int newClasses = 1;

            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {

                int a = sa[i - 1];
                int b = sa[i];

                int aSecond = a + k < n
                    ? rank[a + k] : -1;

                int bSecond = b + k < n
                    ? rank[b + k] : -1;

                if (rank[a] != rank[b] ||
                    aSecond != bSecond) {

                    newClasses++;
                }

                newRank[b] = newClasses - 1;
            }

            int[] swap = rank;
            rank = newRank;
            newRank = swap;

            classes = newClasses;

            if (classes == n) {
                break;
            }
        }

        return sa;
    }

    // Stable Counting Sort
    private void countingSort(
        int[] input,
        int[] output,
        int[] rank,
        int offset,
        int classes,
        int n
    ) {

        int[] count = new int[classes + 1];

        for (int i = 0; i < n; i++) {

            int index = input[i] + offset;

            int key = index < n
                ? rank[index] + 1 : 0;

            count[key]++;
        }

        // Prefix Sum
        for (int i = 1; i < count.length; i++) {

            count[i] += count[i - 1];
        }

        // Place elements
        for (int i = n - 1; i >= 0; i--) {

            int value = input[i];

            int index = value + offset;

            int key = index < n
                ? rank[index] + 1 : 0;

            output[--count[key]] = value;
        }
    }

    // Build LCP Array using Kasai's Algorithm
    private int[] buildLCP(int[] nums, int[] sa) {

        int n = nums.length;

        int[] rank = new int[n];
        int[] lcp = new int[n];

        // Build inverse suffix array
        for (int i = 0; i < n; i++) {

            rank[sa[i]] = i;
        }

        int common = 0;

        for (int i = 0; i < n; i++) {

            int pos = rank[i];

            if (pos == 0) {

                common = 0;
                continue;
            }

            int j = sa[pos - 1];

            while (i + common < n &&
                   j + common < n &&
                   nums[i + common] == nums[j + common]) {

                common++;
            }

            lcp[pos] = common;

            if (common > 0) {
                common--;
            }
        }

        return lcp;
    }
}

```

## **6. Code Explanation: Step by Step**

Let's understand what every important method does and why we need it.

### **Step 1:&#x20;**`smallestUniqueSubarray()`

```
int[] sa = buildSuffixArray(nums);
int[] lcp = buildLCP(nums, sa);
```

First, we generate:

- `sa`: Sorted suffix starting positions.
- `lcp`: Common prefix lengths between neighboring suffixes.

Next:

```
int[] rank = new int[n];

for (int i = 0; i < n; i++) {
    rank[sa[i]] = i;
}
```

This creates an inverse suffix array.

Why?

Suppose:

```
sa = [4, 0, 1, 3, 2]
```

Then:

```
Suffix starting at index 4 → sorted position 0
Suffix starting at index 0 → sorted position 1
Suffix starting at index 1 → sorted position 2
Suffix starting at index 3 → sorted position 3
Suffix starting at index 2 → sorted position 4
```

So:

```
rank = [1, 2, 4, 3, 0]
```

This helps us quickly find the sorted position of any suffix.

### **Step 2: Find previous and next LCP**

```
int pos = rank[i];

int prevLCP = pos > 0 ? lcp[pos] : 0;

int nextLCP = pos < n - 1 ? lcp[pos + 1] : 0;
```

Remember how our LCP array is constructed?

```
lcp[0] = 0
lcp[1] = LCP(sa[1], sa[0])
lcp[2] = LCP(sa[2], sa[1])
lcp[3] = LCP(sa[3], sa[2])
```

Therefore:

- `lcp[pos]` gives the common prefix with the previous suffix.
- `lcp[pos + 1]` gives the common prefix with the next suffix.

We use boundary checks because the first suffix has no previous suffix and the last suffix has no next suffix.

### **Step 3: Calculate required length**

```
int maxLCP = Math.max(prevLCP, nextLCP);

int requiredLength = maxLCP + 1;
```

Why `+1`?

If another suffix shares the first 3 elements, a subarray of length 3 is not unique.

We need at least one more element to make it unique.

Therefore:

\\[ requiredLength = maxLCP + 1 \\]

### **Step 4: Check array boundary**

```
if (requiredLength <= n - i) {

    answer = Math.min(answer, requiredLength);
}
```

Why this condition?

Suppose:

```
nums = [3, 3, 3]
```

At the last index, only one element remains.

If the required length is 2, we cannot take a subarray of length 2 from that position.

Therefore, we check whether enough elements remain.

Finally:

```
return answer;
```

We return the smallest valid unique subarray length.

## **7. Understanding the Suffix Array Construction**

Now let's understand the most complicated part.

```
buildSuffixArray(nums)
```

We use the **Doubling Algorithm**.

The idea is simple:

Instead of comparing complete suffixes immediately, we compare increasingly larger portions.

### **What is the doubling technique?**

Initially, we know the rank of each individual element.

Then we compare groups of:

| Iteration | Elements considered |
| --------- | ------------------- |
| Initial   | 1                   |
| 1         | 2                   |
| 2         | 4                   |
| 3         | 8                   |
| 4         | 16                  |

Each iteration doubles the number of elements considered.

For example:

```
First iteration:

Compare first 1 element.

Second iteration:

Compare first 2 elements.

Third iteration:

Compare first 4 elements.
```

We continue until every suffix has a distinct rank or the required length covers the suffixes.

This takes \\(O(\log n)\\) iterations.

### **Why do we use Coordinate Compression?**

Our constraints are:

```
1 <= nums[i] <= 100000
```

We convert distinct values into consecutive ranks.

For example:

```
nums = [100, 500, 100, 900]
```

After compression:

```
100 → 0
500 → 1
900 → 2
```

Result:

```
rank = [0, 1, 0, 2]
```

This allows Counting Sort to work efficiently.

### **Why do we use Counting Sort?**

Normal sorting takes:

\\[ O(n\log n) \\]

We need to sort suffixes in every doubling iteration.

Counting Sort can sort the rank values in:

\\[ O(n) \\]

Therefore, the doubling algorithm takes:

\\[ O(n\log n) \\]

## **8. Understanding Kasai's Algorithm**

The `buildLCP()` method calculates the LCP array.

A straightforward approach would compare every pair of suffixes, which would be expensive.

Kasai's Algorithm calculates the LCP array in linear time:

\\[ O(n) \\]

The important optimization is:

```
if (common > 0) {
    common--;
}
```

Why do we decrease `common`?

Suppose two suffixes share 4 elements.

When we move to the next starting position, we already know that at least 3 elements will match.

We don't need to start comparing from zero again.

This makes the algorithm efficient.

## **9. Dry Run**

Let's take Example 3:

```
nums = [1, 1, 2, 2, 1]
```

**Step 1: Generate suffixes**

```
Index 0: [1, 1, 2, 2, 1]
Index 1: [1, 2, 2, 1]
Index 2: [2, 2, 1]
Index 3: [2, 1]
Index 4: [1]
```

**Step 2: Sort suffixes**

```
SA = [4, 0, 1, 3, 2]
```

**Step 3: Calculate LCP**

| Position | Suffix index | LCP with previous |
| -------- | ------------ | ----------------- |
| 0        | 4            | 0                 |
| 1        | 0            | 1                 |
| 2        | 1            | 1                 |
| 3        | 3            | 1                 |
| 4        | 2            | 1                 |

Therefore:

```
LCP = [0, 1, 1, 1, 1]
```

**Step 4: Calculate the shortest unique prefix**

| Original index | Previous LCP | Next LCP | Required length |
| -------------- | ------------ | -------- | --------------- |
| 0              | 1            | 1        | 2               |
| 1              | 1            | 1        | 2               |
| 2              | 1            | 0        | 2               |
| 3              | 1            | 1        | 2               |
| 4              | 0            | 1        | 2               |

At index 4, only one element remains, so length 2 is invalid.

At index 0, length 2 is valid.

For example:

```
[1, 1]
```

This subarray occurs only once.

Output: 2

## **10. Complexity Analysis**

| Operation              | Time Complexity  |
| ---------------------- | ---------------- |
| Coordinate Compression | \\(O(n\log n)\\) |
| Suffix Array           | \\(O(n\log n)\\) |
| LCP Array              | \\(O(n)\\)       |
| Finding minimum length | \\(O(n)\\)       |

Overall:

**Time Complexity:**

\\[ \boxed{O(n\log n)} \\]

**Space Complexity:**

\\[ \boxed{O(n)} \\]

## **11. What you should remember for interviews**

There are four important takeaways from this problem:

1. **Suffix Array:** Helps arrange suffixes in lexicographical order.
2. **LCP:** Helps identify how much of a suffix is repeated.
3. **Previous and Next:** The maximum LCP can be found by checking only neighboring suffixes.
4. **Unique Prefix:** The shortest unique prefix length is `maxLCP + 1`.

### **Test your understanding**

**Check your understanding**

**1. Why do we sort suffixes?**

To remove duplicate elements

To place suffixes with common prefixes close together

To reverse the array



**2. If maximum LCP is 4, what is the shortest unique prefix length?**

3

4

5



**3. Why do we check the previous and next suffix?**

They give the maximum possible LCP with any other suffix

They are always the shortest suffixes

They eliminate the need for coordinate compression



0/3 answered

Reset

**My advice:** Don't try to memorize the entire code immediately. First understand why `maxLCP + 1` gives the answer. That is the central observation of this Hard problem. Then learn how the Suffix Array and Kasai's Algorithm help us calculate it efficiently.