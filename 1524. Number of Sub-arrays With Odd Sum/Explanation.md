# **LeetCode 1524. Number of Sub-arrays With Odd Sum**



Medium

Prefix Sum

Counting

Math



## **1. Problem Explanation**

You are given an integer array `arr`.

Your task is to count how many **continuous subarrays have an odd sum**.

A subarray is a continuous part of an array. We cannot skip elements.

For example:

```
arr = [1, 3, 5]
```

All possible subarrays are:

| Subarray  | Sum | Odd/Even |
| --------- | --- | -------- |
| [1]       | 1   | Odd      |
| [1, 3]    | 4   | Even     |
| [1, 3, 5] | 9   | Odd      |
| [3]       | 3   | Odd      |
| [3, 5]    | 8   | Even     |
| [5]       | 5   | Odd      |

There are **4 subarrays with odd sums**.

Therefore, the answer is `4`.

## **2. Java Solution (Optimal Approach)**

We will use **Prefix Sum + Counting Odd and Even Prefix Sums**.

```

class Solution {
    public int numOfSubarrays(int[] arr) {

        int MOD = 1_000_000_007;

        int odd = 0;
        int even = 1;

        int prefixSum = 0;
        int result = 0;

        for (int num : arr) {

            prefixSum += num;

            if (prefixSum % 2 == 0) {
                result += odd;
                even++;
            } else {
                result += even;
                odd++;
            }

            result %= MOD;
        }

        return result;
    }
}

```



Time: O(n)

Space: O(1)



## **3. Understanding the Approach**

First, understand one important mathematical concept.

**When do we get an odd subarray sum?**

There are only two possibilities:

| First prefix sum | Second prefix sum | Subarray sum |
| ---------------- | ----------------- | ------------ |
| Even             | Even              | Even         |
| Odd              | Odd               | Even         |
| Even             | Odd               | Odd          |
| Odd              | Even              | Odd          |

In simple words:

- Even - Even = Even
- Odd - Odd = Even
- Odd - Even = Odd
- Even - Odd = Odd

This is the main logic behind our solution.

### **What is Prefix Sum?**

Prefix sum means keeping the sum of all elements from the beginning of the array up to the current index.

For example:

```
arr = [1, 3, 5]
```

Prefix sums:

```
Index:       0    1    2
Array:       1    3    5
Prefix Sum:  1    4    9
Parity:     Odd  Even Odd
```

Now, suppose we want the sum of a subarray from index `i` to index `j`.

We can calculate it using:

\\[ SubarraySum = PrefixSum[j] - PrefixSum[i-1] \\]

We don't actually need to store the entire prefix sum array.

We only need to count how many previous prefix sums are odd and even.

## **4. Understanding the Variables**

Let's understand each variable in the code.

| Variable    | Meaning                                    |
| ----------- | ------------------------------------------ |
| `MOD`       | Prevents integer overflow by taking modulo |
| `odd`       | Number of previous odd prefix sums         |
| `even`      | Number of previous even prefix sums        |
| `prefixSum` | Running prefix sum                         |
| `result`    | Total number of odd-sum subarrays          |

**Important:**

```
int odd = 0;
int even = 1;
```

Why is `even = 1`?

Because before processing any element, the prefix sum is considered `0`.

And `0` is even.

This initial even prefix sum helps us count subarrays starting from index `0`.

## **5. Dry Run with Example 1**

Input:

```
arr = [1, 3, 5]
```

Initial values:

```
prefixSum = 0
odd       = 0
even      = 1
result    = 0
```

Let's process each element.

**Iteration 1: num = 1**

```
prefixSum = 0 + 1 = 1
```

Prefix sum is odd.

So, we add the number of previous even prefix sums to our result.

```
result = result + even
       = 0 + 1
       = 1

odd = 1
```

Current state:

```
odd = 1
even = 1
result = 1
```

**Iteration 2: num = 3**

```
prefixSum = 1 + 3 = 4
```

Prefix sum is even.

So, we add the number of previous odd prefix sums.

```
result = result + odd
       = 1 + 1
       = 2

even = 2
```

Current state:

```
odd = 1
even = 2
result = 2
```

**Iteration 3: num = 5**

```
prefixSum = 4 + 5 = 9
```

Prefix sum is odd.

So, we add the number of previous even prefix sums.

```
result = result + even
       = 2 + 2
       = 4

odd = 2
```

Final state:

```
odd = 2
even = 2
result = 4
```

Final Answer = 4

### **Dry Run Table**

| Element | Prefix Sum | Parity | Odd Count | Even Count | Result |
| ------- | ---------- | ------ | --------- | ---------- | ------ |
| Initial | 0          | Even   | 0         | 1          | 0      |
| 1       | 1          | Odd    | 1         | 1          | 1      |
| 3       | 4          | Even   | 1         | 2          | 2      |
| 5       | 9          | Odd    | 2         | 2          | 4      |

## **6. Why Does This Approach Work?**

Let's understand the logic in simple words.

There are two cases:

**Case 1: Current prefix sum is EVEN**

```
if (prefixSum % 2 == 0) {
    result += odd;
    even++;
}
```

If the current prefix sum is even, subtracting a previous odd prefix sum gives an odd subarray sum.

Therefore, we add `odd` to the result.

Then we increase `even` because the current prefix sum is also even.

**Case 2: Current prefix sum is ODD**

```
else {
    result += even;
    odd++;
}
```

If the current prefix sum is odd, subtracting a previous even prefix sum gives an odd subarray sum.

Therefore, we add `even` to the result.

Then we increase `odd`.

This way, we count every possible odd-sum subarray exactly once.

## **7. Complexity Analysis**

Let `n` be the length of the array.

| Complexity | Value | Reason                      |
| ---------- | ----- | --------------------------- |
| Time       | O(n)  | We traverse the array once  |
| Space      | O(1)  | We use only a few variables |

Why not use O(n²)?

A brute-force solution would generate every possible subarray and calculate its sum.

That would take O(n²) time with prefix sums.

Our optimized approach avoids checking individual subarrays.

Instead, we count how many previous prefix sums can form an odd sum with the current prefix sum.

## **8. Important Interview Takeaways**

Remember these three points:

1. **Prefix Sum:** Subarray sum = Current Prefix Sum - Previous Prefix Sum.
2. **Parity:** Odd + Even = Odd, and Even + Even = Even.
3. **Optimization:** Instead of storing all prefix sums, maintain only the count of odd and even prefix sums.

**Interview question:** Why do we initialize `even = 1` instead of `even = 0`?

Answer: Because the empty prefix before the array has sum `0`, which is even. This allows us to count odd-sum subarrays that start from index `0`.

**My recommendation:** Remember this problem as a classic Prefix Sum + Parity problem. The same technique is useful in many problems involving counting subarrays with even or odd sums.