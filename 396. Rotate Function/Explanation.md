## Intuition

We need to calculate:

[
F(k)=\sum_{i=0}^{n-1} i \times arr_k[i]
]

where `arr_k` is the array rotated clockwise by `k`.

A brute force approach would:

* Rotate the array every time
* Recalculate the whole function

That costs **O(n²)**, which is too slow for `n = 10^5`.

---

# Key Observation

Instead of recomputing every rotation from scratch, we can derive a relation between consecutive rotations.

---

## Example

`nums = [4,3,2,6]`

### F(0)

[
0(4)+1(3)+2(2)+3(6)=25
]

### After 1 clockwise rotation

Array becomes:

`[6,4,3,2]`

[
F(1)=0(6)+1(4)+2(3)+3(2)=16
]

---

# Deriving the Formula

Suppose:

* `sum` = total sum of all elements
* `F(k)` = current rotation value

When rotating once clockwise:

* Every element's index increases by `1`
* Except the last element, which moves to index `0`

So:

* We gain `sum`
* But the moved element loses `n * movedElement`

Thus:

[
F(k) = F(k-1) + sum - n \times movedElement
]

The moved element during kth rotation is:

[
nums[n-k]
]

So:

[
F(k)=F(k-1)+sum-n\times nums[n-k]
]

---

# Why This Works

Imagine all elements shifting right by one:

Before rotation:

```text
0*a + 1*b + 2*c + 3*d
```

After rotation:

```text
0*d + 1*a + 2*b + 3*c
```

Every element except `d` gains `+1` multiplier.

So we add the total sum once.

But `d` jumped from multiplier `(n-1)` to `0`.

So we subtract:

[
n \times d
]

That gives the recurrence relation.

---

# Efficient Approach

1. Compute:

   * total sum of array
   * `F(0)`

2. Use recurrence formula to generate:

   * `F(1), F(2), ... F(n-1)`

3. Keep track of maximum.

Time Complexity becomes:

* **O(n)**

Space Complexity:

* **O(1)**

---

# Java Solution

```java
class Solution {
    public int maxRotateFunction(int[] nums) {

        int n = nums.length;

        long sum = 0;
        long f0 = 0;

        // Calculate total sum and F(0)
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            f0 += (long) i * nums[i];
        }

        long max = f0;
        long curr = f0;

        // Calculate F(1) to F(n-1)
        for (int k = 1; k < n; k++) {

            curr = curr + sum - (long) n * nums[n - k];

            max = Math.max(max, curr);
        }

        return (int) max;
    }
}
```

---

# Dry Run

## Input

```text
nums = [4,3,2,6]
```

### Step 1: Compute sum and F(0)

[
sum = 4+3+2+6 = 15
]

[
F(0)=25
]

---

### Step 2: Compute F(1)

Moved element = `6`

[
F(1)=25+15-4(6)
]

[
=25+15-24
]

[
=16
]

---

### Step 3: Compute F(2)

Moved element = `2`

[
F(2)=16+15-4(2)
]

[
=23
]

---

### Step 4: Compute F(3)

Moved element = `3`

[
F(3)=23+15-4(3)
]

[
=26
]

Maximum = `26`

---

# Important Notes

## Why use `long`?

Because:

[
i \times nums[i]
]

can become large during intermediate calculations.

Even though final answer fits in `int`, intermediate values may overflow.

So use `long` internally.

---

# Complexity

| Complexity | Value |
| ---------- | ----- |
| Time       | O(n)  |
| Space      | O(1)  |
