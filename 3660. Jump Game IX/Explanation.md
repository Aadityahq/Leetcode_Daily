## Understanding the Problem

You are standing at index `i`.

You can move:

* **Right (`j > i`)** only if:
  `nums[j] < nums[i]`
  → you can jump to a **smaller** number on the right.

* **Left (`j < i`)** only if:
  `nums[j] > nums[i]`
  → you can jump to a **bigger** number on the left.

For every index, we need the **maximum value** we can eventually reach after making any number of valid jumps.

---

# Key Observation

The jumps create a very interesting property.

Suppose:

```text
nums = [2,3,1]
```

From `2`:

* can go right to `1`
* from `1` can go left to `3`

So:

```text
2 -> 1 -> 3
```

Thus answer for index 0 becomes `3`.

---

# Main Insight

If:

```text
max on left side > min on right side
```

then those two regions become **connected**.

Why?

Because:

* Some larger value exists on left
* Some smaller value exists on right

So:

* larger can jump right to smaller
* smaller can jump back left to larger

This means all indices in those regions can eventually reach the same maximum value.

---

# What are `pre` and `suf`?

## Prefix Maximum

```java
pre[i]
```

= maximum value from `0...i`

Example:

```text
nums = [2,3,1,5]

pre:
[2,3,3,5]
```

---

## Suffix Minimum

```java
suf[i]
```

= minimum value from `i...n-1`

Example:

```text
nums = [2,3,1,5]

suf:
[1,1,1,5]
```

---

# Important Condition

```java
if (pre[i] > suf[i + 1])
```

This means:

* left side contains some larger value
* right side contains some smaller value

So a jump connection exists between both sides.

Hence:

* both parts belong to the same connected component
* answer becomes same as right side

---

# Dry Run

## Example

```text
nums = [2,3,1]
```

---

## Step 1: Prefix max

```text
pre[0] = 2
pre[1] = max(2,3)=3
pre[2] = max(3,1)=3

pre = [2,3,3]
```

---

## Step 2: Suffix min

```text
suf[2] = 1
suf[1] = min(3,1)=1
suf[0] = min(2,1)=1

suf = [1,1,1]
```

---

## Step 3: Build answer from right

```text
res[2] = pre[2] = 3
```

---

### i = 1

Check:

```text
pre[1] > suf[2]
3 > 1 -> true
```

So connected.

```text
res[1] = res[2] = 3
```

---

### i = 0

Check:

```text
pre[0] > suf[1]
2 > 1 -> true
```

Connected again.

```text
res[0] = res[1] = 3
```

Final:

```text
[3,3,3]
```

---

# Why Does This Work?

Think of the array as being divided into segments.

If:

```text
pre[i] > suf[i+1]
```

then a bridge exists between:

* left segment
* right segment

So they merge into one larger connected component.

All indices inside a connected component can eventually reach the same maximum value.

That maximum is simply:

```text
pre[i]
```

for that component.

---

# Why `res[i] = res[i+1]`?

Because when segments merge:

```text
[i part] + [i+1 part]
```

they now share the same reachable maximum.

The right segment already knows its final maximum:

```java
res[i+1]
```

So current index gets the same value.

---

# Why `res[i] = pre[i]` in else?

If:

```text
pre[i] <= suf[i+1]
```

then:

* no larger-left and smaller-right pair exists
* no bridge exists
* new connected component starts

So maximum reachable is only inside current prefix.

Thus:

```java
res[i] = pre[i];
```

---

# Time Complexity

We traverse array a few times:

* Prefix max → `O(n)`
* Suffix min → `O(n)`
* Build result → `O(n)`

Total:

```text
O(n)
```

Space:

```text
O(n)
```

---

# Code Intuition Summary

```java
pre[i]
```

= biggest value on left side

```java
suf[i]
```

= smallest value on right side

If:

```java
pre[i] > suf[i+1]
```

then left and right can connect through jumps.

So:

```java
res[i] = res[i+1]
```

Otherwise:

* no connection
* start new component

So:

```java
res[i] = pre[i]
```
