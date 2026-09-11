## 1. First understand what the problem is asking

You are given an array of digits.

For example:

```text
digits = [1, 2, 3, 4]
```

You need to form **3-digit numbers** such that:

1. It must actually have 3 digits → **first digit cannot be 0**
2. It must be **even** → last digit must be `0, 2, 4, 6, 8`
3. You can use each **copy** of a digit only once.

And the question asks for **distinct numbers**.

For `[1,2,3,4]`, for example:

```text
124
132
134
142
214
...
```

There are 12 distinct numbers.

---

# 2. The key idea

There are exactly **3 positions** in the number:

```text
Hundreds    Tens    Units
   ↓          ↓        ↓
  digits[i] digits[j] digits[k]
```

So the natural solution is:

> Try every possible digit for the first position, every possible digit for the second position, and every possible digit for the third position.

That's why we use **3 nested loops**.

```java
for (int i = 0; i < digits.length; i++) {
    for (int j = 0; j < digits.length; j++) {
        for (int k = 0; k < digits.length; k++) {
```

---

# 3. Why do we need a HashSet?

```java
Set<Integer> uniqueNumbers = new HashSet<>();
```

The problem doesn't ask:

> How many arrangements can you make?

It asks:

> How many **distinct numbers** can you make?

Consider:

```text
digits = [2, 2, 0]
```

We can create:

```text
202
220
```

But there are two copies of `2`.

When we use the first `2` and second `2`, we might generate the same number through different indices.

For example:

```text
index:    0  1  2
digit:    2  2  0
```

`202` can be formed using:

```text
index 0 → 2
index 2 → 0
index 1 → 2
```

and also:

```text
index 1 → 2
index 2 → 0
index 0 → 2
```

These are different **index combinations**, but the number is the same:

```text
202
```

A `HashSet` automatically removes this duplication.

So:

```java
uniqueNumbers.add(number);
```

means:

> "Store this number, but if it's already there, don't store another copy."

---

# 4. First loop — choosing the first digit

```java
for (int i = 0; i < digits.length; i++) {
```

Here `i` represents the **hundreds digit**.

For:

```text
digits = [1,2,3,4]
```

we might choose:

```text
i = 0 → 1
i = 1 → 2
i = 2 → 3
i = 3 → 4
```

But there's one restriction.

### First digit cannot be 0

That's why:

```java
if (digits[i] == 0) {
    continue;
}
```

Suppose:

```text
digits = [0,2,4]
```

We cannot create:

```text
024
```

because `024` is actually just `24`, which isn't a 3-digit number.

So if the first digit is zero:

```java
continue;
```

means:

> Skip this choice and move to the next `i`.

---

# 5. Second loop — choosing the middle digit

```java
for (int j = 0; j < digits.length; j++) {
```

Now `j` represents the **tens digit**.

For example, suppose:

```text
i = 0
digits[i] = 1
```

We can try:

```text
1 _ _
```

The second digit could be:

```text
1 1 _
1 2 _
1 3 _
1 4 _
```

But we cannot use the **same array element** twice.

That's why:

```java
if (j == i) {
    continue;
}
```

---

# 6. Why compare indices instead of digits?

This is very important.

Suppose:

```text
digits = [2, 2, 3]
```

There are two `2`s:

```text
index:  0  1  2
digit:  2  2  3
```

If:

```text
i = 0
```

we can still choose:

```text
j = 1
```

because we're using the **second copy of 2**.

So this would be wrong:

```java
if (digits[j] == digits[i])
```

because it would incorrectly say:

> "You can't use another 2."

Instead we say:

```java
if (j == i)
```

because we're asking:

> "Are we trying to use the exact same copy of the digit?"

That's exactly what the problem means by:

> Each copy of a digit can only be used once.

---

# 7. Third loop — choosing the last digit

```java
for (int k = 0; k < digits.length; k++) {
```

Now `k` represents the **units digit**.

So our number looks like:

```text
digits[i] digits[j] digits[k]
   ↑          ↑          ↑
hundreds     tens       units
```

Again, we can't reuse either of the previous positions:

```java
if (k == i || k == j) {
    continue;
}
```

This means:

```text
k == i
```

Don't use the same copy as the first digit.

And:

```text
k == j
```

Don't use the same copy as the middle digit.

---

# 8. Why must the last digit be even?

A number is even if its **last digit is even**.

For example:

```text
124 → even
132 → even
314 → even
```

But:

```text
123 → odd
135 → odd
```

So we don't need to calculate the entire number to know whether it's even.

We can simply check:

```java
if (digits[k] % 2 != 0) {
    continue;
}
```

If the last digit is odd:

```text
1 % 2 = 1
3 % 2 = 1
5 % 2 = 1
7 % 2 = 1
9 % 2 = 1
```

we skip it.

If it's:

```text
0, 2, 4, 6, 8
```

then:

```text
digit % 2 == 0
```

and it's valid.

---

# 9. Constructing the number

Now we've selected:

```text
i → hundreds
j → tens
k → units
```

Suppose:

```text
digits[i] = 3
digits[j] = 1
digits[k] = 4
```

We want:

```text
314
```

Mathematically:

```text
3 × 100 + 1 × 10 + 4
```

So:

```java
int number = digits[i] * 100
           + digits[j] * 10
           + digits[k];
```

The result is:

```text
300 + 10 + 4
= 314
```

---

# 10. Add it to the Set

```java
uniqueNumbers.add(number);
```

Now suppose we generate:

```text
202
```

The set becomes:

```text
[202]
```

If we generate `202` again:

```java
uniqueNumbers.add(202);
```

the set remains:

```text
[202]
```

because sets don't allow duplicates.

---

# 11. Finally return the size

```java
return uniqueNumbers.size();
```

If the set contains:

```text
[124, 132, 134, 142, 214, ...]
```

then:

```java
uniqueNumbers.size()
```

gives the number of **distinct valid numbers**.

---

# Let's trace an example

Take:

```text
digits = [1, 2, 3, 4]
```

Suppose:

```text
i = 0
```

So:

```text
digits[i] = 1
```

Our number starts:

```text
1 _ _
```

Now:

```text
j = 1
```

So:

```text
1 2 _
```

Now try `k`.

### k = 0

```text
k == i
```

We're trying to use the same `1` twice.

❌ Skip.

### k = 1

```text
k == j
```

We're trying to use the same `2` twice.

❌ Skip.

### k = 2

```text
digits[k] = 3
```

But:

```text
3 % 2 != 0
```

❌ Odd → skip.

### k = 3

```text
digits[k] = 4
```

Even ✅

So:

```text
1 2 4
```

Construct:

```java
1 * 100 + 2 * 10 + 4
```

=>

```text
124
```

Add:

```java
uniqueNumbers.add(124);
```

---

# The whole algorithm in one picture

Think of it like this:

```text
                Choose i
             (hundreds digit)
                    │
                    ▼
             Is it 0?
             /       \
           YES        NO
            │          │
          skip         ▼
                  Choose j
                (tens digit)
                      │
                      ▼
                 j == i?
                 /     \
               YES      NO
                │         │
              skip        ▼
                      Choose k
                    (units digit)
                          │
                          ▼
                  k == i or j?
                    /       \
                  YES        NO
                   │           │
                 skip          ▼
                         Is k even?
                          /       \
                        NO         YES
                        │            │
                      skip           ▼
                              Create number
                                    │
                                    ▼
                              Add to HashSet
```

---

# Why your original code failed

Your original approach was doing this:

```java
lDigit = digits[i];
fDigit = digits[j];
mDigit = digits[k];
```

The problem was that you were **overwriting the variables**.

For example:

```text
j = 1 → fDigit = 2
j = 2 → fDigit = 3
j = 3 → fDigit = 4
```

At the end, only:

```text
fDigit = 4
```

remained.

You weren't actually creating:

```text
2xx
3xx
4xx
```

for every possibility.

The corrected approach says:

> **Each loop represents one position, and every combination of the three positions gets evaluated.**

That's the core insight of this problem.

---

## Complexity

There are three loops:

```java
for i
    for j
        for k
```

So:

**Time:**

```text
O(n³)
```

Since `n <= 10`:

```text
10 × 10 × 10 = 1000
```

Only about 1000 combinations—extremely small.

**Space:**

```text
O(n³)
```

for the `HashSet`, although the actual number of possible 3-digit numbers is at most 900.

### The main pattern to remember

For this type of problem, when you see:

> "Form a number using elements from an array, without reusing an element"

think:

**"Nested loops over indices + conditions + Set for uniqueness."**

And especially remember the distinction:

```text
same DIGIT ≠ same COPY
```

`[2,2,3]` means the two `2`s are separate copies, so comparing **indices** is the correct way to enforce the "use once" rule.
