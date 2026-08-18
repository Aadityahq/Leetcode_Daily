## 3471. Find the Largest Almost Missing Integer

### 1. Understanding the problem

We have:

* An array `nums`
* A window size `k`

We need to look at **every subarray of size `k`**.

An integer `x` is called **almost missing** if it appears in **exactly one** of those subarrays.

Finally, among all such integers, return the **largest** one.

If no integer satisfies the condition, return `-1`.

---

### 2. Example

Suppose:

```text
nums = [3, 9, 2, 1, 7]
k = 3
```

The subarrays of size `3` are:

```text
[3, 9, 2]
[9, 2, 1]
[2, 1, 7]
```

Now count in how many **different subarrays** each number occurs:

```text
3 → 1 subarray
9 → 2 subarrays
2 → 3 subarrays
1 → 2 subarrays
7 → 1 subarray
```

So:

```text
3 and 7
```

are almost missing.

The largest is:

```text
7
```

Therefore answer = `7`.

---

# 3. Important observation

The key phrase is:

> "appears in exactly one subarray of size k"

This is slightly different from simply counting how many times a number occurs in `nums`.

For example:

```text
nums = [7, 2, 1, 7]
k = 3
```

Subarrays:

```text
[7, 2, 1]
[2, 1, 7]
```

`7` occurs twice in the whole array, but it appears in **two different subarrays**.

Therefore `7` is **not** almost missing.

So we need to count:

> In how many windows of size `k` does each number occur?

---

# 4. Simple approach

Because:

```text
nums.length <= 50
nums[i] <= 50
```

we don't need a complicated algorithm.

We can simply:

1. Generate every subarray of size `k`.
2. For each subarray, find which numbers occur in it.
3. Increase the count of that number **once**.
4. At the end, find the largest number whose count is exactly `1`.

### Why only once per subarray?

Suppose:

```text
nums = [7, 7, 2]
k = 3
```

There is only one subarray:

```text
[7, 7, 2]
```

Even though `7` occurs twice inside the subarray, it appears in only **one subarray**.

Therefore we must not increment the count twice.

---

# 5. Java Solution

```java
class Solution {
    public int largestInteger(int[] nums, int k) {

        int[] count = new int[51];

        // Generate every subarray of size k
        for (int i = 0; i <= nums.length - k; i++) {

            boolean[] present = new boolean[51];

            // Traverse the current subarray
            for (int j = i; j < i + k; j++) {
                present[nums[j]] = true;
            }

            // Count this subarray only once for each number
            for (int x = 0; x <= 50; x++) {
                if (present[x]) {
                    count[x]++;
                }
            }
        }

        // Find the largest number appearing in exactly one subarray
        for (int x = 50; x >= 0; x--) {
            if (count[x] == 1) {
                return x;
            }
        }

        return -1;
    }
}
```

---

# 6. How the code works

### Step 1: Create a count array

```java
int[] count = new int[51];
```

Since:

```text
0 <= nums[i] <= 50
```

we can directly use the number as an index.

For example:

```text
count[7]
```

stores the number of subarrays in which `7` appears.

---

### Step 2: Generate every window

```java
for (int i = 0; i <= nums.length - k; i++)
```

If:

```text
nums.length = 5
k = 3
```

then `i` will be:

```text
0
1
2
```

giving us:

```text
i = 0 → [3, 9, 2]
i = 1 → [9, 2, 1]
i = 2 → [2, 1, 7]
```

---

### Step 3: Track numbers present in the current window

```java
boolean[] present = new boolean[51];
```

Initially:

```text
present[0] = false
present[1] = false
...
present[50] = false
```

Then:

```java
for (int j = i; j < i + k; j++) {
    present[nums[j]] = true;
}
```

Suppose the current window is:

```text
[7, 7, 2]
```

After processing:

```text
present[7] = true
present[2] = true
```

Notice that `7` is still only marked `true` once.

That's exactly what we need.

---

### Step 4: Count each number once for this window

```java
for (int x = 0; x <= 50; x++) {
    if (present[x]) {
        count[x]++;
    }
}
```

If the current window contains:

```text
[7, 7, 2]
```

we do:

```text
count[7]++
count[2]++
```

We **don't** do:

```text
count[7]++
count[7]++
```

because we are counting subarrays, not occurrences.

---

# 7. Finding the largest answer

Now suppose after processing all windows:

```text
count[1] = 2
count[2] = 3
count[3] = 1
count[7] = 1
count[9] = 2
```

We need:

```text
count[x] == 1
```

So both `3` and `7` qualify.

Instead of scanning from `0` upward, we scan from `50` downward:

```java
for (int x = 50; x >= 0; x--) {
    if (count[x] == 1) {
        return x;
    }
}
```

The first number we find is automatically the **largest**.

---

# 8. Dry run

For:

```text
nums = [3, 9, 2, 1, 7]
k = 3
```

### Window 1

```text
[3, 9, 2]
```

Numbers present:

```text
3, 9, 2
```

Counts:

```text
3 → 1
9 → 1
2 → 1
```

### Window 2

```text
[9, 2, 1]
```

Counts become:

```text
3 → 1
9 → 2
2 → 2
1 → 1
```

### Window 3

```text
[2, 1, 7]
```

Counts become:

```text
3 → 1
9 → 2
2 → 3
1 → 2
7 → 1
```

Therefore:

```text
3 → exactly 1
7 → exactly 1
```

Largest =

```text
7
```

---

# 9. Complexity

There are approximately:

```text
n - k + 1
```

windows.

For each window, we process `k` elements and then at most `51` possible values.

So the complexity is:

```text
O((n - k + 1) × (k + 51))
```

Since `n <= 50`, this is effectively:

```text
O(n²)
```

Space complexity:

```text
O(51) = O(1)
```

because the arrays have fixed size based on the constraint `nums[i] <= 50`.

---

## 10. The main idea to remember

The most important thing in this problem is:

> **Count how many windows contain a number, not how many times the number occurs.**

That's why we use:

```java
boolean[] present
```

for every window.

The overall pattern is:

```text
Generate every window
        ↓
Find unique values inside that window
        ↓
Increase their window-count
        ↓
Find largest value with count == 1
```

This is a good example of a **brute-force solution that is completely appropriate because the constraints are very small**.
