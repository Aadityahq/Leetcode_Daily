This is a **very famous design + math trick problem**. The difficulty is realizing that we **should not update the whole array every time** `addAll` or `multAll` is called. Otherwise the complexity becomes **O(n) per operation**, which will TLE for (10^5) operations.

Let’s break it down step-by-step. 🚀

---

# 1. What the Problem Wants

We maintain a sequence and support these operations:

| Operation       | Meaning                       |
| --------------- | ----------------------------- |
| `append(val)`   | add element to the sequence   |
| `addAll(inc)`   | add `inc` to every element    |
| `multAll(m)`    | multiply every element by `m` |
| `getIndex(idx)` | return value at index         |

All answers are **mod (10^9+7)**.

Example:

```
append(2)      -> [2]
addAll(3)      -> [5]
append(7)      -> [5,7]
multAll(2)     -> [10,14]
```

---

# 2. Why a Naive Solution Fails

If we do operations literally:

```
addAll -> loop through entire array
multAll -> loop through entire array
```

Worst case:

```
10^5 operations
each operation O(n)
```

This becomes:

```
10^5 * 10^5 = 10^10 operations ❌
```

Too slow.

So we need **lazy math transformation**.

---

# 3. Key Observation

Every operation applied to the sequence can be represented as a **linear transformation**.

Any number in the array can be represented as:

[
value = a \times original + b
]

Where:

```
a → multiplication factor
b → addition factor
```

---

### Example

Start:

```
[2]
```

After `addAll(3)`:

```
[2 + 3]
```

So

```
a = 1
b = 3
```

---

After `multAll(2)`:

```
(2 + 3) * 2 = 4 + 6
```

So

```
a = 2
b = 6
```

Because

[
(1*x + 3) * 2 = 2x + 6
]

---

Thus we maintain two variables:

```
a → multiplication coefficient
b → addition coefficient
```

Current value of element:

[
value = a * storedValue + b
]

---

# 4. Handling Operations

## (1) addAll(inc)

If current formula:

[
value = a * x + b
]

After adding `inc`:

[
value = a * x + (b + inc)
]

So we update:

```
b = b + inc
```

Code:

```java
b = (b + inc) % MOD;
```

---

## (2) multAll(m)

Multiply whole sequence by `m`.

[
(a * x + b) * m
]

[
= (a*m)x + (b*m)
]

So:

```
a = a * m
b = b * m
```

Code:

```java
a = (a * m) % MOD;
b = (b * m) % MOD;
```

---

# 5. The Hard Part — append(val)

When we append, the current transformation is already applied to future values.

We want:

```
storedValue * a + b = val
```

Solve for `storedValue`.

[
storedValue = (val - b) / a
]

But since we are using modulo, we cannot divide.

Instead we use **modular inverse**.

[
storedValue = (val - b) * a^{-1}
]

Where

[
a^{-1} = a^{MOD-2}
]

(using Fermat's theorem).

---

### Code

```java
long x = (val - b + MOD) % MOD;
stored = x * inverse(a)
```

So we store the **normalized value**.

```java
this.val.add((x * modPow(a, MOD - 2, MOD)) % MOD);
```

---

# 6. getIndex(idx)

Stored value is:

```
stored
```

Actual value:

[
value = a * stored + b
]

Code:

```java
return (a * stored + b) % MOD;
```

---

# 7. Walk Through Example

Operations:

```
append(2)
```

Store:

```
a=1 b=0

stored = (2-0)/1 = 2
array=[2]
```

---

```
addAll(3)
```

```
a=1
b=3
```

---

```
append(7)
```

Compute stored:

```
stored = (7 - 3) / 1
stored = 4
```

Array:

```
[2,4]
```

---

```
multAll(2)
```

```
a = 2
b = 6
```

Actual values:

```
2*2+6 = 10
2*4+6 = 14
```

Correct.

---

# 8. Why This Works

Instead of modifying every element, we:

* store **normalized values**
* maintain **global transformation**

All operations become:

```
append → O(1)
addAll → O(1)
multAll → O(1)
getIndex → O(1)
```

Total complexity:

```
O(Q)
```

Perfect for (10^5).

---

# 9. Role of modPow()

We need modular inverse:

[
a^{-1} = a^{MOD-2} \mod MOD
]

This is calculated using **fast exponentiation**.

Time:

```
O(log MOD)
```

But append happens at most (10^5) times → acceptable.

---

# 10. Final Intuition

Think of the sequence as:

```
real_value = a * stored_value + b
```

We never change the array.

We only **change the formula** that maps stored values → real values.

---

✅ **Key Trick**

Lazy linear transformation:

```
value = ax + b
```

---

