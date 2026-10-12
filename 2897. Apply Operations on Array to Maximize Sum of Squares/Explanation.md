# LeetCode 2897: Apply Operations on Array to Maximize Sum of Squares

Hard

Greedy

Bit Manipulation

The main idea is to collect the maximum possible set bits into each of the \\(k\\) selected numbers. We do this by counting the set bits at every position and constructing the best possible numbers.

## 1. Understanding the problem

You are given:

- `nums`: an integer array.
- `k`: the number of elements you must select from the final array.

You can repeatedly choose two different indices and perform these operations simultaneously:

- `nums[i] = nums[i] & nums[j]`
- `nums[j] = nums[i] | nums[j]`

Here, `&` means bitwise AND, and `|` means bitwise OR.

Your goal is to maximize the sum of squares of exactly `k` elements.

\\[ \text{Answer}=\sum\_{i=1}^{k}x_i^2 \\]

Return the answer modulo \\(10^9+7\\).

## 2. Example walkthrough

Input:

```
nums = [2, 6, 5, 8]
k = 2
```

We can transform the array using the given operations:

Initial array

## [2, 6, 5, 8]

After the first operation

## [0, 6, 5, 10]

Combine 2 and 8: AND gives 0, OR gives 10.

After the second operation

## [0, 6, 0, 15]

Combine 5 and 10: AND gives 0, OR gives 15.

Choose the best 2 elements

## 15 and 6

\\[ 15^2+6^2=225+36=\boxed{261} \\]

The challenge is to find the best result without trying every possible sequence of operations.

## 3. The key observation: Set bits can be redistributed

Let's understand what AND and OR do.

Suppose we have:

```
a = 2  = 0010
b = 6  = 0110
```

Applying the operation gives:

```
a & b = 0010 = 2
a | b = 0110 = 6
```

Notice something important:

The total number of set bits at each bit position does not change.

For example, if a bit is present in either number, the OR result keeps that bit. The AND result also keeps it if it was present in both. Therefore, the operation redistributes bits between numbers without losing or creating any bits.

This means we can rearrange the set bits to build numbers with larger values.

## 4. Why should we maximize each number's bits?

Suppose we have two numbers:

\\[ a=7,\qquad b=1 \\]

Their sum of squares is:

\\[ 7^2+1^2=50 \\]

Now compare that with:

\\[ a=4,\qquad b=4 \\]

Their sum of squares is:

\\[ 4^2+4^2=32 \\]

The first arrangement gives a larger sum of squares.

This illustrates why concentrating set bits into fewer numbers is beneficial. In general, for nonnegative numbers, concentrating value increases the sum of squares when the total value is fixed.

So our strategy is:

1. Count the set bits at each bit position across the entire array.
2. Construct each of the `k` selected numbers using all currently available bit positions.
3. Remove the bits used for that number.
4. Repeat until we have constructed `k` numbers.
5. Calculate the sum of their squares.

## 5. Java solution

```

class Solution {
    public int maxSum(List<Integer> nums, int k) {
        final long MOD = 1_000_000_007L;

        int[] bitCount = new int[32];

        // Step 1: Count set bits at every position
        for (int num : nums) {
            for (int bit = 0; bit < 32; bit++) {
                if ((num & (1 << bit)) != 0) {
                    bitCount[bit]++;
                }
            }
        }

        long ans = 0;

        // Step 2: Construct k maximum numbers
        for (int i = 0; i < k; i++) {
            long x = 0;

            for (int bit = 0; bit < 32; bit++) {
                if (bitCount[bit] > 0) {
                    x |= (1L << bit);
                    bitCount[bit]--;
                }
            }

            // Step 3: Add the square modulo MOD
            ans = (ans + x * x % MOD) % MOD;
        }

        return (int) ans;
    }
}

```

This version uses the LeetCode Java signature for this problem. Make sure `import java.util.List;` is present if you are testing it in a standalone Java file.

## 6. Code explanation: How and why

### Step 1: Count the set bits

```
int[] bitCount = new int[32];
```

An integer has 32 bit positions. We create an array to count how many numbers contain a `1` at each position.

For every number:

```
for (int num : nums) {
    for (int bit = 0; bit < 32; bit++) {
        if ((num & (1 << bit)) != 0) {
            bitCount[bit]++;
        }
    }
}
```

Let's use the example:

```
nums = [2, 6, 5, 8]
```

Their binary representations are:

| Number | Binary |
| ------ | ------ |
| 2      | `0010` |
| 6      | `0110` |
| 5      | `0101` |
| 8      | `1000` |

Counting the set bits gives:

| Bit position | Set bits found | Count |
| ------------ | -------------- | ----- |
| 0            | 5              | 1     |
| 1            | 2, 6           | 2     |
| 2            | 6, 5           | 2     |
| 3            | 8              | 1     |

The important information is the count at each position, not which original number contains the bit.

### Step 2: Construct the largest possible number

```
for (int i = 0; i < k; i++) {
    long x = 0;
```

We need to construct `k` numbers. We start each number at zero.

Next:

```
for (int bit = 0; bit < 32; bit++) {
    if (bitCount[bit] > 0) {
        x |= (1L << bit);
        bitCount[bit]--;
    }
}
```

For every bit position that still has a set bit available:

- Set that bit in the current number.
- Decrease its count because we have used one occurrence.

We do this for every bit position before constructing the next number.

For our example, the first constructed number is:

```
Bit 3: available → use it
Bit 2: available → use it
Bit 1: available → use it
Bit 0: available → use it
```

Therefore:

```
x = 1111 (binary) = 15
```

The remaining counts allow us to construct the next number as `0110`, which equals `6`.

The two numbers are `15` and `6`, giving the maximum sum of squares of `261`.

### Step 3: Calculate the square

```
ans = (ans + x * x % MOD) % MOD;
```

For every constructed number, we calculate its square and add it to the answer.

We apply modulo during each addition to prevent the result from becoming too large.

We use `long` because `x * x` can exceed the range of a Java `int`.

## 7. Dry run

For `nums = [2, 6, 5, 8]` and `k = 2`:

### First number

Available bit counts initially: bit 3 = 1, bit 2 = 2, bit 1 = 2, bit 0 = 1

## 1111₂ = 15

\\[ 15^2=225 \\]

### Second number

The remaining counts are bit 3 = 0, bit 2 = 1, bit 1 = 1, bit 0 = 0.

## 0110₂ = 6

\\[ 6^2=36 \\]

### Final answer

\\[ 225+36=\boxed{261} \\]

## 8. Why does this greedy approach work?

There are two key facts:

1\. Bit counts are preserved. The allowed operation can redistribute bits, but it cannot change how many occurrences of a particular bit exist across the array.

2\. A higher-valued number benefits more from combining bits. If we can put more available set bits into a number, its value increases. Concentrating the available bits into each constructed number gives the maximum possible sum of squares.

Therefore, at each step, we build a number using every bit position that still has a set bit available. We repeat this for exactly `k` numbers.

This achieves the maximum possible sum of squares.

## 9. Complexity analysis

Let \\(n\\) be the length of `nums`.

- Time complexity: \\(O(32n+32k)=O(n+k)\\), because we examine 32 bit positions for each input number and each constructed number.
- Auxiliary space complexity: \\(O(32)=O(1)\\), because the bit-count array always has a fixed size of 32.

## 10. Important points to remember

- `&` performs bitwise AND.
- `|` performs bitwise OR.
- `(num & (1 << bit)) != 0` checks whether a particular bit is set.
- `x |= (1L << bit)` sets that bit in `x`.
- `bitCount[bit]--` marks one occurrence of that bit as used.
- Use `long` for the constructed number and its square.
- Apply modulo \\(10^9+7\\) while accumulating the answer.

Interview takeaway: When a problem allows bitwise operations that redistribute bits and asks you to maximize the sum of squares, consider counting each bit position and greedily concentrating the available bits into the selected numbers.