# Intuition

We need to find the **GCD (Greatest Common Divisor)** of:

* **sumOdd** = sum of the first `n` odd numbers
* **sumEven** = sum of the first `n` even numbers

Instead of actually adding all the numbers, we can use mathematical formulas.

---

# Step 1: Find the sum of the first `n` odd numbers

The first few odd numbers are:

```
1
1 + 3 = 4
1 + 3 + 5 = 9
1 + 3 + 5 + 7 = 16
1 + 3 + 5 + 7 + 9 = 25
```

Notice the pattern:

```
1  = 1²
4  = 2²
9  = 3²
16 = 4²
25 = 5²
```

So,

[
\boxed{\text{sumOdd} = n^2}
]

---

# Step 2: Find the sum of the first `n` even numbers

Even numbers are

```
2, 4, 6, 8, ...
```

Factor out `2`:

```
2(1 + 2 + 3 + ... + n)
```

We know

[
1+2+3+\cdots+n=\frac{n(n+1)}2
]

Multiply by 2:

[
2 \times \frac{n(n+1)}2 = n(n+1)
]

So,

[
\boxed{\text{sumEven}=n(n+1)}
]

---

# Step 3: Compute the GCD

Now we only need

```
GCD(n², n(n+1))
```

Let's simplify it mathematically.

```
GCD(n², n(n+1))
```

Take out the common factor `n`:

```
= n × GCD(n, n+1)
```

Since two consecutive numbers are always coprime,

```
GCD(n, n+1) = 1
```

Therefore,

```
GCD = n
```

So the answer is always simply:

```
return n;
```

The provided solution also works because it computes the two sums and applies the Euclidean algorithm, but the mathematical simplification shows that the result is always `n`.

---

# Example 1

```
n = 4
```

### Sum of odd numbers

```
1 + 3 + 5 + 7 = 16
```

### Sum of even numbers

```
2 + 4 + 6 + 8 = 20
```

Now

```
GCD(16,20)
```

```
20 % 16 = 4
16 % 4 = 0
```

Answer:

```
4
```

---

# Example 2

```
n = 5
```

Odd sum

```
25
```

Even sum

```
30
```

```
GCD(25,30)=5
```

Answer:

```
5
```

---

# Euclidean Algorithm (How `gcd()` Works)

The Euclidean algorithm repeatedly replaces:

```
(a, b)
```

with

```
(b, a % b)
```

until `b` becomes `0`.

Example:

```
GCD(20,16)

20 % 16 = 4

↓

GCD(16,4)

16 % 4 = 0

↓

Answer = 4
```

This works because

```
GCD(a,b) = GCD(b,a%b)
```

---

# Algorithm

1. Compute

   * `sumOdd = n * n`
   * `sumEven = n * (n + 1)`
2. Find their GCD using the Euclidean algorithm.
3. Return the GCD.

> **Even simpler:** Since `GCD(n², n(n+1)) = n`, you can directly return `n`.

---

# Complexity Analysis

### Given Solution

* **Time Complexity:** `O(log n)` (Euclidean GCD)
* **Space Complexity:** `O(1)`

### Optimized Mathematical Solution

```java
class Solution {
    public int gcdOfOddEvenSums(int n) {
        return n;
    }
}
```

* **Time Complexity:** `O(1)`
* **Space Complexity:** `O(1)`

---

# Key Takeaways

* Sum of first `n` odd numbers = **`n²`**
* Sum of first `n` even numbers = **`n(n+1)`**
* Consecutive numbers are always coprime:

  * `GCD(n, n+1) = 1`
* Therefore,

[
\boxed{\text{GCD}(n^2,;n(n+1))=n}
]

Hence, the answer is always **`n`**.
