# Understanding the Problem

For every number in the range `[num1, num2]`, we define its **waviness** as:

```text
(number of peaks) + (number of valleys)
```

A digit is:

### Peak

```text
left < current > right
```

Example:

```text
484

4 < 8 > 4
```

So `8` is a peak.

---

### Valley

```text
left > current < right
```

Example:

```text
202

2 > 0 < 2
```

So `0` is a valley.

---

### Important Rules

* First digit cannot be a peak/valley.
* Last digit cannot be a peak/valley.
* Numbers with fewer than 3 digits have waviness = 0.

Example:

```text
4848

4 < 8 > 4   -> peak
8 > 4 < 8   -> valley

waviness = 2
```

---

# Why Brute Force Fails

The constraint is:

```text
num2 ≤ 10^15
```

The range can contain trillions of numbers.

A brute-force approach:

```java
for(long x = num1; x <= num2; x++)
```

is impossible.

We need a **Digit DP** solution.

---

# Main Idea

Instead of checking every number individually, let's calculate:

```text
F(x) = total waviness of all numbers from 0 to x
```

Then:

```text
Answer = F(num2) - F(num1 - 1)
```

This is a standard Digit DP pattern.

---

# What Information Must DP Remember?

To determine whether a digit becomes a peak/valley, we need three consecutive digits:

```text
pp, p, d
```

where:

```text
pp = digit before previous
p  = previous digit
d  = current digit
```

Because when we place `d`, we can finally decide whether `p` is:

```text
pp < p > d   (peak)

or

pp > p < d   (valley)
```

Thus we must remember:

```java
(idx, pp, p, tight, lead)
```

---

# DP State

```java
solve(idx, pp, p, tight, lead)
```

Meaning:

At position `idx`:

* `pp` = second last digit
* `p` = last digit
* `tight` = are we still matching the upper bound?
* `lead` = are we still skipping leading zeros?

The function returns:

```java
{countNumbers, totalWaviness}
```

---

# Why Return Two Values?

We need:

### 1. Count of numbers

```java
cnt
```

How many valid numbers can be formed from this state.

---

### 2. Total waviness

```java
wave
```

Total waviness contributed by all those numbers.

---

# Base Case

```java
if (idx == s.length())
    return new long[]{1, 0};
```

We formed exactly one valid number.

```text
count = 1
waviness = 0
```

because no more digits remain.

---

# Choosing Current Digit

```java
int lim = tight == 1 ? s.charAt(idx) - '0' : 9;
```

If tight:

```text
can only go up to bound digit
```

Otherwise:

```text
0..9
```

---

# Updating Tight

```java
int ntight =
    (tight == 1 && d == lim) ? 1 : 0;
```

If we use the maximum allowed digit while tight:

```text
still tight
```

otherwise:

```text
free
```

---

# Handling Leading Zeros

```java
int nlead =
    (lead == 1 && d == 0) ? 1 : 0;
```

Example:

```text
000123
```

Leading zeros should not participate in peak/valley calculations.

---

# Why Use 10 as a Special Value?

Digits are:

```text
0..9
```

So:

```java
10
```

means:

```text
"No actual digit yet"
```

---

Updating previous digits:

```java
np = nlead == 1 ? 10 : d;
```

If still in leading zeros:

```text
previous digit = undefined
```

Otherwise:

```text
previous digit = current digit
```

---

Updating second previous digit:

```java
npp =
    nlead == 1
    ? 10
    : (lead == 1 ? 10 : p);
```

This carefully shifts:

```text
pp <- p
p  <- d
```

once real digits begin.

---

# Detecting Peak or Valley

This is the heart of the solution.

```java
boolean wavy = false;

if (pp != 10 && p != 10) {
    if ((pp < p && p > d) ||
        (pp > p && p < d))
        wavy = true;
}
```

We already know:

```text
pp, p
```

and are placing:

```text
d
```

Therefore we can now determine whether:

```text
p
```

is a peak or valley.

---

Example:

```text
484

pp=4
p =8
d =4

4 < 8 > 4
```

Peak detected.

---

Example:

```text
202

pp=2
p =0
d =2

2 > 0 < 2
```

Valley detected.

---

# Transition

Recursive call:

```java
long[] res =
    solve(idx + 1,
          npp,
          np,
          ntight,
          nlead);
```

Suppose:

```text
res[0] = number count
res[1] = waviness from suffix
```

---

## Counting Numbers

```java
cnt += res[0];
```

All suffix numbers belong to current state.

---

## Counting Waviness

### Existing waviness

```java
wave += res[1];
```

Add waviness generated later.

---

### Current peak/valley

If current digit makes `p` wavy:

```java
wave += res[0];
```

because every suffix generated from this state gets:

```text
+1 waviness
```

So:

```java
wave += res[1]
      + (wavy ? res[0] : 0);
```

---

# Memoization

State:

```java
(idx, pp, p, tight, lead)
```

is cached.

```java
vis[idx][pp][p][tight][lead]
```

so each state is computed only once.

---

# Computing F(x)

```java
long calc(long num)
```

returns:

```text
Total waviness of all numbers in [0, num]
```

---

Numbers below 100:

```java
if (num < 100)
    return 0;
```

because they cannot contain a peak or valley.

---

Start Digit DP:

```java
return solve(0,10,10,1,1)[1];
```

Initially:

```text
idx = 0
pp = undefined
p  = undefined
tight = true
lead = true
```

and we want:

```text
total waviness
```

which is index `[1]`.

---

# Final Answer

Using range DP:

```java
Answer =
    calc(num2)
    - calc(num1 - 1);
```

Because:

```text
[0, num2]
-
[0, num1-1]
=
[num1, num2]
```

---

# Example: 4848

Digits:

```text
4 8 4 8
```

Position 2:

```text
4 < 8 > 4
```

Peak.

Position 3:

```text
8 > 4 < 8
```

Valley.

Therefore:

```text
waviness = 2
```

The DP counts both contributions while building the number.

---

# Complexity Analysis

State count:

```text
idx      : 16
pp       : 11
p        : 11
tight    : 2
lead     : 2
```

Total states:

```text
16 × 11 × 11 × 2 × 2
≈ 7,744
```

Each state tries:

```text
10 digits
```

So:

```text
O(16 × 11 × 11 × 2 × 2 × 10)
≈ 77,000 operations
```

which is extremely fast.

### Time Complexity

```text
O(number_of_states × 10)
≈ O(8 × 10^4)
```

### Space Complexity

```text
O(number_of_states)
≈ O(8 × 10^3)
```

This is why Digit DP can handle numbers up to `10^15` efficiently while brute force cannot.
