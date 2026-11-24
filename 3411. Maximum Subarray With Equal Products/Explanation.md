# **LeetCode 3411: Maximum Subarray With Equal Products**



Easy

Java

Brute Force



## **1. Understanding the Problem**

We are given an array of positive integers `nums`.

We need to find the **length of the longest contiguous subarray** that satisfies the product equivalent condition.

An array is called *product equivalent* when:

- `prod(arr)` = Product of all elements.
- `gcd(arr)` = Greatest Common Divisor of all elements.
- `lcm(arr)` = Least Common Multiple of all elements.

The condition is:

\\[ prod(arr) = gcd(arr) \times lcm(arr) \\]

Our task is to return the maximum length of a subarray satisfying this condition.

**Important:** A subarray must contain consecutive elements. We cannot skip elements.

## **2. Java Solution**

```

class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            int product = 1;
            int gcd = 0;
            int lcm = 1;

            for (int j = i; j < n; j++) {
                product *= nums[j];

                gcd = findGCD(gcd, nums[j]);

                lcm = (lcm * nums[j]) / findGCD(lcm, nums[j]);

                if (product == gcd * lcm) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    private int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}

```

## **3. Understanding the Approach**

We will use **Brute Force**.

Why?

Because the array length is at most 100, so checking all possible subarrays is efficient enough.

We use three variables:

| Variable  | Purpose                             |
| --------- | ----------------------------------- |
| `product` | Stores product of subarray elements |
| `gcd`     | Stores GCD of subarray elements     |
| `lcm`     | Stores LCM of subarray elements     |

### **Step 1: Outer loop**

```
for (int i = 0; i < n; i++)
```

This selects the starting index of our subarray.

For example:

```
nums = [2, 3, 4, 5, 6]

i = 0 → Start from 2
i = 1 → Start from 3
i = 2 → Start from 4
```

### **Step 2: Initialize variables**

```
int product = 1;
int gcd = 0;
int lcm = 1;
```

Why these initial values?

- `product = 1` because 1 is the identity for multiplication.
- `gcd = 0` because GCD of 0 and any positive number is that number.
- `lcm = 1` because 1 is the identity for LCM.

### **Step 3: Inner loop**

```
for (int j = i; j < n; j++)
```

This expands the subarray one element at a time.

For every new element, we update the product, GCD and LCM.

**Update product:**

```
product *= nums[j];
```

**Update GCD:**

```
gcd = findGCD(gcd, nums[j]);
```

**Update LCM:**

```
lcm = (lcm * nums[j]) / findGCD(lcm, nums[j]);
```

Why this formula?

\\[ LCM(a,b)=\frac{a \times b}{GCD(a,b)} \\]

We use this formula to calculate LCM without directly finding multiples.

## **4. Dry Run**

Let's understand using Example 2.

```
Input: nums = [2, 3, 4, 5, 6]
Output: 3
```

Consider the subarray `[3, 4, 5]`.

**Subarray: [3, 4, 5]**

Product

# **60**

GCD

# **1**

LCM

# **60**

**Check the condition**

\\[ Product = GCD \times LCM \\]

\\[ 60 = 1 \times 60 \\]

Condition satisfied

**Subarray length = 3**

Let's see how the values change:

| Subarray  | Product | GCD | LCM | Valid? |
| --------- | ------- | --- | --- | ------ |
| [2]       | 2       | 2   | 2   | Yes    |
| [2,3]     | 6       | 1   | 6   | Yes    |
| [2,3,4]   | 24      | 1   | 12  | No     |
| [3]       | 3       | 3   | 3   | Yes    |
| [3,4]     | 12      | 1   | 12  | Yes    |
| [3,4,5]   | 60      | 1   | 60  | Yes    |
| [3,4,5,6] | 360     | 1   | 60  | No     |

The longest valid subarray is `[3, 4, 5]`, with length `3`.

## **5. Understanding the GCD Function**

We use the Euclidean Algorithm to calculate GCD.

```
private int findGCD(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}
```

Example:

```
GCD(12, 8)

12 % 8 = 4
8 % 4 = 0

GCD = 4
```

This algorithm repeatedly calculates the remainder until the remainder becomes zero.

## **6. Complexity Analysis**

Let `n` be the length of the array.

| Complexity | Value       | Reason                         |
| ---------- | ----------- | ------------------------------ |
| Time       | O(n² log M) | Two loops and GCD calculations |
| Space      | O(1)        | Only a few variables           |

Here, `M` represents the maximum value involved in GCD calculations.

Since `nums.length <= 100` and `nums[i] <= 10`, this approach is efficient.

## **7. Important Interview Questions**

**Q1. Why are we using two loops?**

Because we need to generate every possible contiguous subarray and check whether it satisfies the condition.

**Q2. Why do we initialize GCD with 0?**

Because:

\\[ GCD(0,x)=x \\]

This makes it easy to calculate GCD incrementally.

**Q3. Why do we initialize LCM with 1?**

Because 1 is the identity value for multiplication, and:

\\[ LCM(1,x)=x \\]

**Q4. Why do we use&#x20;**`j - i + 1`**?**

Because both starting and ending indices are included.

For example, if `i = 1` and `j = 3`:

\\[ Length = 3-1+1=3 \\]

**Q5. Can we use a single loop?**

Not easily with this brute-force approach, because we need to consider different starting positions.

## **8. Final Takeaway**

Remember these three things:

1. Use nested loops to generate all contiguous subarrays.
2. Update product, GCD and LCM incrementally instead of recalculating them from scratch.
3. Whenever `product == gcd * lcm`, update the maximum length.

**Pattern learned:** Array + Nested Loops + GCD + LCM.

This is a useful problem for practising subarray generation and mathematical operations in Java.