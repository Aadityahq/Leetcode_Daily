# LeetCode 2958: Length of Longest Subarray With at Most K Frequency

## Intuition

We need the **longest contiguous subarray** such that **every number appears at most `k` times**.

This is a perfect **Sliding Window** problem:

- Expand the window using `right`.
- Count frequencies using a `HashMap`.
- If any number appears more than `k` times, move `left` forward until the window becomes valid again.
- Track the maximum window length.

---

## Java Solution

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxSubarrayLength(int[] nums, int k) {

        Map<Integer, Integer> freq = new HashMap<>();

        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < nums.length; right++) {

            // Add current element
            freq.put(nums[right],
                     freq.getOrDefault(nums[right], 0) + 1);

            // Shrink window if frequency exceeds k
            while (freq.get(nums[right]) > k) {

                freq.put(nums[left],
                         freq.get(nums[left]) - 1);

                left++;
            }

            // Update answer
            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }
}
```

---

## Example

### Input

```text
nums = [1,2,3,1,2,3,1,2]
k = 2
```

Valid longest subarray:

```text
[1,2,3,1,2,3]
```

Frequencies:

- `1 → 2`
- `2 → 2`
- `3 → 2`

All are ≤ `2`, so the answer is:

```text
6
```

---

## Why It Works

For every `right`:

1. Include `nums[right]`.
2. If its frequency becomes **greater than `k`**, remove elements from the left.
3. The window always remains **valid**.
4. Since each element is added and removed at most once, the algorithm is efficient.

---

## Complexity

- **Time:** `O(n)`
- **Space:** `O(n)`

This is the optimal solution for the given constraints (`n ≤ 100000`).
