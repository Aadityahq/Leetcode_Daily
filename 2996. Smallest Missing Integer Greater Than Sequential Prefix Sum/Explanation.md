### 2996. Smallest Missing Integer Greater Than Sequential Prefix Sum

### Understanding the Problem

We need to do two things:

### Step 1: Find the longest sequential prefix

A prefix is sequential if every next element is exactly previous + 1.

Example:

`[1,2,3,2,5]`

* `1 → 2` ✅

* `2 → 3` ✅

* `3 → 2` ❌

So the longest sequential prefix is:

`[1,2,3]`

Its sum is:

`1 + 2 + 3 = 6`

### Step 2: Find the smallest missing integer ≥ prefix sum

Array contains:

`[1,2,3,2,5]`

Start checking from `6`:

* `6` is not in the array → answer is 6

### Example 2

Input:

`[3,4,5,1,12,14,13]`

Sequential prefix:

`[3,4,5]`

Sum:

`3 + 4 + 5 = 12`

Check numbers starting from `12`:

* `12` → present

* `13` → present

* `14` → present

* `15` → missing ✅

Answer = 15

### Why This Works

The problem asks for:

the smallest missing integer that is greater than or equal to the sum of the longest sequential prefix

So:

* Compute the sequential prefix sum.

* Store all array elements in a HashSet for fast lookup.

* Start from the sum and increase until we find a number not present in the set.

Time complexity is very small because `n ≤ 50`.

### Java Solution

Java

```
import java.util.*;

class Solution {
    public int missingInteger(int[] nums) {
        int sum = nums[0];

        // Find longest sequential prefix and its sum
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }

        // Store all numbers for quick lookup
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        // Find the smallest missing integer >= sum
        while (set.contains(sum)) {
            sum++;
        }

        return sum;
    }
}
```

### Dry Run

Input:

```
nums = [1,2,3,2,5]
```

### Find sequential prefix

```
sum = 1

i = 1 → 2 == 1+1 → sum = 3
i = 2 → 3 == 2+1 → sum = 6
i = 3 → 2 != 3+1 → stop
```

Now:

```
sum = 6
```

### Build HashSet

```
{1,2,3,5}
```

### Find missing number

```
6 → not in set
```

Return:

```
6
```

### Complexity Analysis

### Time Complexity

* Finding prefix: O(n)

* Building set: O(n)

* Searching missing number: at most a few iterations, O(n) worst case

Overall:

```
O(n)
```

Since `n ≤ 50`, this is very efficient.

### Space Complexity

```
O(n)
```

for the `HashSet`.

### Key Idea to Remember

Think of the problem as:

Find longest consecutive-from-start part

```
a, a+1, a+2, ...
```

Add them

```
prefixSum
```

Find the first number starting from `prefixSum` that does not exist in the array

That directly leads to the simple HashSet + linear scan solution above.
