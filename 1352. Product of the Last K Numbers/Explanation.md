# **LeetCode 1352 – Product of the Last K Numbers**



Medium

Prefix Sum / Array

Java



## **1. Problem Explanation**

We need to design a class called `ProductOfNumbers` that supports two operations:

1. `add(int num)` – Add a number to the stream.
2. `getProduct(int k)` – Return the product of the last `k` numbers.

The important part is that we need to make both operations work in **O(1) time complexity**.

### **Example**

Input:

```
["ProductOfNumbers", "add", "add", "add", "add", "add",
 "getProduct", "getProduct", "getProduct", "add", "getProduct"]

[[], [3], [0], [2], [5], [4], [2], [3], [4], [8], [2]]
```

Output:

```
[null, null, null, null, null, null, 20, 40, 0, null, 32]
```

Let's understand the operations:

| Operation     | Current numbers    | Result |
| ------------- | ------------------ | ------ |
| add(3)        | [3]                | -      |
| add(0)        | [3, 0]             | -      |
| add(2)        | [3, 0, 2]          | -      |
| add(5)        | [3, 0, 2, 5]       | -      |
| add(4)        | [3, 0, 2, 5, 4]    | -      |
| getProduct(2) | [5, 4]             | 20     |
| getProduct(3) | [2, 5, 4]          | 40     |
| getProduct(4) | [0, 2, 5, 4]       | 0      |
| add(8)        | [3, 0, 2, 5, 4, 8] | -      |
| getProduct(2) | [4, 8]             | 32     |

## **2. Approach: Prefix Product**

We can solve this problem using the **Prefix Product technique**.

First, understand what a prefix product means.

Suppose we have:

```
Numbers = [2, 3, 4, 5]
```

Calculate the cumulative product:

```
Prefix Product = [1, 2, 6, 24, 120]
```

Explanation:

- 1 is the initial value.
- 2 = 1 × 2
- 6 = 2 × 3
- 24 = 6 × 4
- 120 = 24 × 5

Now, suppose we want the product of the last 2 numbers.

Instead of multiplying 4 × 5 again, we can divide:

\\[ \frac{120}{6}=20 \\]

So, the product of the last `k` numbers is:

\\[ \boxed{\frac{prefix[n]}{prefix[n-k]}} \\]

Where:

- `n` = total number of elements.
- `k` = number of last elements we want.
- `prefix[n]` = product of all elements.
- `prefix[n-k]` = product before the last `k` elements.

This gives us O(1) time complexity for `getProduct()`.

## **3. Important Concept: Handling Zero**

There is one problem with prefix products.

Consider:

```
Numbers = [2, 3, 0, 4, 5]
```

The prefix product becomes zero after encountering 0.

```
Prefix = [1, 2, 6, 0, 0, 0]
```

Now, if we calculate:

\\[ \frac{0}{0} \\]

It is invalid.

**How do we solve this?**

Whenever we encounter zero:

1. Reset the prefix product array.
2. Start again with `1`.
3. This means we only store products after the most recent zero.

When `getProduct(k)` is called:

- If `k` is greater than or equal to the size of the prefix array, a zero exists in the requested range. Return `0`.
- Otherwise, divide the latest prefix product by the prefix product before the last `k` elements.

## **4. Java Solution (Optimal – O(1))**

```

import java.util.*;

class ProductOfNumbers {

    private List<Integer> prefix;

    public ProductOfNumbers() {
        prefix = new ArrayList<>();
        prefix.add(1);
    }

    public void add(int num) {

        if (num == 0) {
            prefix.clear();
            prefix.add(1);
        } else {
            int lastProduct = prefix.get(prefix.size() - 1);
            prefix.add(lastProduct * num);
        }
    }

    public int getProduct(int k) {

        int n = prefix.size();

        if (k >= n) {
            return 0;
        }

        return prefix.get(n - 1) / prefix.get(n - 1 - k);
    }
}

```

## **5. Code Explanation: Line by Line**

### **Step 1: Initialize the prefix list**

```
private List<Integer> prefix;

public ProductOfNumbers() {
    prefix = new ArrayList<>();
    prefix.add(1);
}
```

Why do we initialize it with `1`?

Because 1 is the identity element of multiplication.

It helps us calculate the first prefix product without requiring special handling.

Initially:

```
prefix = [1]
```

### **Step 2: Implement add()**

```
public void add(int num) {

    if (num == 0) {
        prefix.clear();
        prefix.add(1);
    } else {
        int lastProduct = prefix.get(prefix.size() - 1);
        prefix.add(lastProduct * num);
    }
}
```

There are two cases.

**Case 1: Number is zero**

```
if (num == 0) {
    prefix.clear();
    prefix.add(1);
}
```

We reset the prefix list because multiplication by zero makes every previous cumulative product unusable for division.

Example:

```
Before adding zero:

Numbers = [2, 3]
Prefix  = [1, 2, 6]

After adding zero:

Numbers = [2, 3, 0]
Prefix  = [1]
```

**Case 2: Number is non-zero**

```
int lastProduct = prefix.get(prefix.size() - 1);
prefix.add(lastProduct * num);
```

We multiply the new number with the previous prefix product.

Example:

```
Numbers = [2, 3, 4]

Prefix = [1, 2, 6, 24]
```

We don't need to traverse the entire array.

### **Step 3: Implement getProduct()**

```
public int getProduct(int k) {

    int n = prefix.size();

    if (k >= n) {
        return 0;
    }

    return prefix.get(n - 1) / prefix.get(n - 1 - k);
}
```

First, we calculate the size of the prefix list.

```
int n = prefix.size();
```

Then we check:

```
if (k >= n) {
    return 0;
}
```

Why?

Because the prefix list only contains numbers added after the latest zero, along with the initial `1`.

If `k >= n`, the requested range includes a zero.

Otherwise:

```
return prefix.get(n - 1) / prefix.get(n - 1 - k);
```

We divide the latest prefix product by the prefix product before the requested range.

## **6. Dry Run**

Let's understand the complete execution.

Operations:

```
add(3)
add(0)
add(2)
add(5)
add(4)
getProduct(2)
getProduct(3)
getProduct(4)
```

**Execution step by step**

1. **add(3)**

   Prefix: `[1, 3]`
2. **add(0)**

   Reset prefix.

   Prefix: `[1]`
3. **add(2)**

   Prefix: `[1, 2]`
4. **add(5)**

   Prefix: `[1, 2, 10]`
5. **add(4)**

   Prefix: `[1, 2, 10, 40]`
6. **getProduct(2)**

   \\(40 / 2 = 20\\)

   Output: 20
7. **getProduct(3)**

   \\(40 / 1 = 40\\)

   Output: 40
8. **getProduct(4)**

   `4 >= 4`, so return 0.

   Output: 0

Notice that we never multiply the last `k` elements individually.

We simply use division.

## **7. Complexity Analysis**

| Operation     | Time Complexity |
| ------------- | --------------- |
| Constructor   | O(1)            |
| add(num)      | O(1)            |
| getProduct(k) | O(1)            |

**Space Complexity:** O(n), where `n` is the number of non-zero elements added since the most recent zero (plus the initial 1).

Why is it O(1) for `getProduct()`?

Because we access two positions directly in the prefix list and perform one division. No loop is required.

## **8. Important Interview Concepts**

Remember these points:

1. **Prefix Product:** Stores cumulative multiplication of elements.
2. **Division:** Helps retrieve the product of the last `k` elements in constant time.
3. **Zero Handling:** Reset the prefix list whenever zero arrives.
4. **Identity Element:** Initialize the prefix list with `1`.
5. **Optimization:** Avoid iterating over the last `k` elements.

### **Why not use a normal array?**

A normal array approach would require multiplying the last `k` elements every time `getProduct(k)` is called.

Its time complexity would be O(k).

Using prefix products reduces this to O(1).

## **9. Final Takeaway**

Optimal Solution

The main idea is:

- Maintain cumulative products.
- Reset when zero appears.
- Use division to get the product of the last `k` numbers.

\\[ \boxed{Product = \frac{prefix[n-1]}{prefix[n-1-k]}} \\]

**Interview tip:** Whenever a problem asks for the sum or product of a range, think about prefix sums or prefix products. They can often eliminate repeated calculations.