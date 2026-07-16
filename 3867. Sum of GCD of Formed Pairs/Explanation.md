## Intuition

The problem has **three independent steps**:

1. Build the `prefixGcd` array.
2. Sort it.
3. Pair the smallest with the largest and compute the GCD of each pair.

The only challenge is understanding how `prefixGcd` is constructed.

---

# Step 1: Construct `prefixGcd`

For every index `i`:

* Find the **maximum element from index 0 to i**.
* Compute

[
prefixGcd[i] = gcd(nums[i],; prefixMaximum)
]

### Example

```
nums = [3,6,2,8]
```

| i | nums[i] | Maximum so far | gcd(nums[i], max) | prefixGcd |
| - | ------- | -------------- | ----------------- | --------- |
| 0 | 3       | 3              | 3                 | 3         |
| 1 | 6       | 6              | 6                 | 6         |
| 2 | 2       | 6              | 2                 | 2         |
| 3 | 8       | 8              | 8                 | 8         |

So,

```
prefixGcd = [3,6,2,8]
```

Notice we don't recompute the maximum every time.

Instead we keep

```
mx = max(mx, nums[i])
```

which makes this step **O(n)**.

---

# Step 2: Sort

```
prefixGcd = [3,6,2,8]

↓

[2,3,6,8]
```

Sorting takes

```
O(n log n)
```

---

# Step 3: Form Pairs

Always pair

* smallest
* largest

Then move inward.

```
2 3 6 8
↑     ↑

gcd(2,8)=2

Now

3 6
↑ ↑

gcd(3,6)=3
```

Answer

```
2+3=5
```

---

# Why Two Pointers?

After sorting,

```
smallest ----- largest
```

are at the ends.

Instead of removing elements repeatedly (which is expensive),

just use

```
left = 0
right = n-1
```

After every pair

```
left++
right--
```

This is **O(n)**.

---

# Complete Algorithm

```
mx = nums[0]

for every element
    mx = max(mx, nums[i])
    prefixGcd[i] = gcd(nums[i], mx)

sort(prefixGcd)

left = 0
right = n-1

answer = 0

while(left < right)
    answer += gcd(prefixGcd[left], prefixGcd[right])
    left++
    right--

return answer
```

---

# Why does this work?

The problem **explicitly instructs** us to:

1. Build `prefixGcd`
2. Sort it
3. Pair smallest with largest
4. Sum their GCDs

So there is no optimization or greedy proof needed—the pairing strategy is fixed by the problem statement. We simply implement the required steps efficiently.

---

# Complexity Analysis

### Building prefixGcd

```
O(n)
```

### Sorting

```
O(n log n)
```

### Pairing

```
O(n)
```

### Total

```
O(n log n)
```

### Space

```
O(n)
```

for the `prefixGcd` array.

---

# Java Solution

```java
import java.util.*;

class Solution {

    public long gcdSum(int[] nums) {
        int n = nums.length;
        int[] prefixGcd = new int[n];

        int mx = 0;

        for (int i = 0; i < n; i++) {
            mx = Math.max(mx, nums[i]);
            prefixGcd[i] = gcd(nums[i], mx);
        }

        Arrays.sort(prefixGcd);

        long ans = 0;

        int left = 0;
        int right = n - 1;

        while (left < right) {
            ans += gcd(prefixGcd[left], prefixGcd[right]);
            left++;
            right--;
        }

        return ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}
```

---

# Dry Run

### Input

```
nums = [2,6,4]
```

### Build `prefixGcd`

```
mx = 2
gcd(2,2)=2

mx = 6
gcd(6,6)=6

mx = 6
gcd(4,6)=2
```

```
prefixGcd = [2,6,2]
```

### Sort

```
[2,2,6]
```

### Pair

```
2 2 6
↑   ↑

gcd(2,6)=2
```

Middle element is ignored.

```
Answer = 2
```

---

## Key Takeaways

* Maintain the running maximum while traversing the array.
* Compute `prefixGcd[i] = gcd(nums[i], runningMaximum)`.
* Sort the `prefixGcd` array.
* Use two pointers (`left` and `right`) to pair the smallest and largest elements.
* Sum the GCD of each pair; if the array length is odd, the middle element is naturally left unpaired because the loop stops when `left >= right`.
