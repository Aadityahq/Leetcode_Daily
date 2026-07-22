This is one of those problems where the statement is much harder than the actual idea. Let's break it down carefully.

---

# Step 1: Understanding the Trade

For every query `[l,r]`, we only look at

```
substring = s[l...r]
```

Then we imagine adding `1` to both sides.

```
t = 1 + substring + 1
```

These added `1`s are **fake**. They only help determine whether a block is "surrounded".

---

## What trade is allowed?

Exactly **one** trade.

It has two steps.

### Step A

Choose a block of consecutive `1`s that is surrounded by `0`s.

Example

```
0011100
 111
```

Turn it into

```
0000000
```

---

### Step B

Now choose a block of consecutive `0`s that is surrounded by `1`s.

Example

```
111000111
   000
```

Turn it into

```
111111111
```

---

Notice the order.

```
Remove one island of 1s
↓

Create a larger island of 0s

↓

Convert that entire island to 1s
```

---

# Why remove a 1-block first?

Because removing it merges two neighboring zero blocks.

Example

```
000111000
```

Without removing

```
000
111
000
```

There are **two** zero blocks.

After removing

```
000000000
```

Now they become **one huge zero block**.

That large block can now be converted into ones.

---

# Example

Substring

```
0100
```

Augmented

```
101001
```

Runs are

```
1
0
1
00
1
```

There is

```
0
1
00
```

The middle `1` is surrounded by zeros.

Remove it.

```
100001
```

Now

```
0000
```

is surrounded by ones.

Convert it.

```
111111
```

Ignoring fake boundaries,

```
1111
```

Answer = 4.

---

# Important Observation

Suppose we have

```
ZeroLeft
OneMiddle
ZeroRight
```

Like

```
0001110000
```

Lengths

```
left zeros = L

middle ones = M

right zeros = R
```

Initially

Active sections

```
=M
```

After trade

We lose

```
-M
```

because we deleted them.

Then gain

```
L + M + R
```

because merged zeros become ones.

Net increase

```
(L+M+R)-M

=L+R
```

Amazing!

The middle ones cancel.

---

## This is the key observation.

Removing a 1-block doesn't hurt.

Its size disappears mathematically.

The improvement depends only on

```
left zero length

+

right zero length
```

---

# Therefore

Suppose substring already has

```
countOnes
```

ones.

If we pick

```
0...0
111
0000
```

Answer becomes

```
countOnes

+

leftZero

+

rightZero
```

That's it.

---

# So every candidate is

```
existing ones

+

left zero block

+

right zero block
```

Take maximum.

---

# Example

```
00011100
```

Ones

```
3
```

Left zeros

```
3
```

Right zeros

```
2
```

Result

```
3+3+2=8
```

Entire substring becomes ones.

---

# Why augmentation?

Suppose substring

```
010
```

Without augmentation

```
0
1
0
```

The middle one already has zeros on both sides.

Good.

Now consider

```
10
```

Without augmentation

```
1
0
```

The first one is not surrounded.

But the problem wants boundaries treated like

```
1101
```

So the first one has

```
left = fake 1

right = 0
```

Not surrounded.

Similarly

```
01
```

becomes

```
1011
```

The last one isn't surrounded.

Adding fake ones makes boundary cases easy.

---

# What information do we need?

For every query

```
countOnes
```

inside substring.

Easy with prefix sums.

---

Then

Find every pattern

```
zero block

one block

zero block
```

inside the substring.

Compute

```
leftZero+rightZero
```

Take maximum.

---

Final answer

```
ones

+

bestGain
```

---

# Efficient Solution

Since

```
n ≤ 100000

queries ≤100000
```

We cannot scan every substring.

Need preprocessing.

---

## Precompute runs

Compress string.

Instead of

```
0001110011
```

store

```
0 length 3

1 length 3

0 length 2

1 length 2
```

Now every run knows

```
start

end

length
```

---

## Prefix Ones

Build

```
prefix[i]
```

Then

```
ones(l,r)

=

prefix[r+1]-prefix[l]
```

O(1).

---

## Gain for every 1-run

If

```
Zero

One

Zero
```

exists

store

```
gain

=

leftZero+rightZero
```

---

Now each query only needs to consider the 1-runs completely inside it (after augmentation rules). Data structures such as a segment tree over runs (or equivalent preprocessing) let us return the maximum valid gain in **O(log n)** per query.

---

# Complexity

Preprocessing

```
O(n)
```

Each query

```
O(log n)
```

Total

```
O((n+q)log n)
```

which fits

```
100000
```

constraints.

---

# Intuition to Remember

The whole trick is this identity:

```
0001110000

↓

(remove 111)

↓

0000000000

↓

(convert all zeros)

↓

1111111111
```

The number of active sections changes by

```
Gain
=
(left zeros + removed ones + right zeros)
− removed ones
=
left zeros + right zeros
```

So **the size of the removed `1` block is irrelevant**. The only thing that matters is **how many zeros it connects together**. Once you see this observation, the problem becomes one of finding the best `zero → one → zero` pattern inside each queried substring and adding its `leftZero + rightZero` gain to the original number of `1`s.
