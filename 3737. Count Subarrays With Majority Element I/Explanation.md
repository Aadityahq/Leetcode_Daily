Let's understand this problem from scratch and then see why the brute-force solution works.

---

# Problem Understanding

You are given:

* An array `nums`
* A number `target`

You have to count **all subarrays** in which **target is the majority element**.

### What is a majority element?

A majority element appears **strictly more than half** the length of the subarray.

Mathematically,

```
targetCount > subarrayLength / 2
```

Notice it is **greater than**, not **greater than or equal to**.

---

# Example 1

```
nums = [1,2,2,3]
target = 2
```

Let's check every subarray.

```
[1]
target = 0
Length = 1
0 > 0 ? No
```

---

```
[1,2]
target = 1
Length = 2

Need >1
1>1 ? No
```

---

```
[1,2,2]

target =2
Length =3

Need >1

2>1 ✔
```

Valid.

---

```
[1,2,2,3]

target =2
Length =4

Need >2

2>2 ?
No
```

Not valid.

---

Starting from index 1

```
[2]

count=1
length=1

1>0 ✔
```

Valid.

---

```
[2,2]

count=2
length=2

2>1 ✔
```

Valid.

---

```
[2,2,3]

count=2
length=3

2>1 ✔
```

Valid.

---

Starting from index 2

```
[2]

count=1
length=1

1>0 ✔
```

Valid.

---

```
[2,3]

count=1
length=2

Need >1

1>1 ?
No
```

---

So total valid subarrays

```
[2]
[2]
[2,2]
[1,2,2]
[2,2,3]
```

Answer = **5**

---

# Brute Force Idea

A subarray is defined by

```
Left index
Right index
```

So try every possible

```
Left
   Right
```

For each subarray,

* Count occurrences of target
* Find subarray length
* Check majority condition

---

# Dry Run

```
nums=[1,2,2,3]
```

Initially

```
ans=0
```

---

## Left =0

```
targetCount=0
```

### Right=0

Subarray

```
[1]
```

```
targetCount=0

length=1

0>0

No
```

---

### Right=1

Subarray

```
[1,2]
```

Found target

```
targetCount=1

length=2

1>1

No
```

---

### Right=2

Subarray

```
[1,2,2]
```

Another target

```
targetCount=2

length=3

2>1

Yes

ans=1
```

---

### Right=3

```
[1,2,2,3]
```

```
targetCount=2

length=4

Need>2

No
```

---

## Left=1

Reset

```
targetCount=0
```

### Right=1

```
[2]

count=1

length=1

1>0

Yes

ans=2
```

---

### Right=2

```
[2,2]

count=2

length=2

2>1

Yes

ans=3
```

---

### Right=3

```
[2,2,3]

count=2

length=3

2>1

Yes

ans=4
```

---

## Left=2

Reset

```
count=0
```

### Right=2

```
[2]

count=1

length=1

Yes

ans=5
```

---

### Right=3

```
[2,3]

count=1

length=2

Need>1

No
```

---

## Left=3

```
[3]
```

No target

No

Final answer

```
5
```

---

# Understanding the Code

```java
class Solution {
    public int countMajoritySubarrays(int[] nums, int target) {
```

Function receives the array and target.

---

## Step 1

```java
int n = nums.length;
```

Store array size.

---

## Step 2

```java
int ans = 0;
```

Stores the number of valid subarrays.

---

## Step 3

Outer loop

```java
for (int l = 0; l < n; l++)
```

Choose every possible starting index.

Example

```
l=0

[1]
[1,2]
[1,2,2]
[1,2,2,3]
```

Then

```
l=1

[2]
[2,2]
[2,2,3]
```

and so on.

---

## Step 4

```java
int targetCount = 0;
```

Whenever the left index changes, we start a new subarray.

So reset the count.

---

## Step 5

```java
for (int r = l; r < n; r++)
```

Increase the right end one element at a time.

```
l fixed

r=l

r++

r++

r++
```

This generates all subarrays starting from `l`.

---

## Step 6

```java
if (nums[r] == target)
    targetCount++;
```

Whenever the newly added element equals `target`, increase its count.

Notice we **don't recount the entire subarray** each time. Since the right end grows by one element, we only inspect that new element and update `targetCount`.

Example

```
[1]

count=0
```

Extend

```
[1,2]

new element=2

count=1
```

Extend

```
[1,2,2]

new element=2

count=2
```

Very efficient.

---

## Step 7

```java
int len = r - l + 1;
```

Find current subarray length.

Example

```
l=2

r=5

length

=5-2+1

=4
```

---

## Step 8

```java
if (targetCount > len / 2)
```

Check the majority condition.

Examples

### Length = 5

```
len/2=2

Need >2

Need at least 3
```

---

### Length = 6

```
len/2=3

Need >3

Need at least 4
```

---

### Length = 1

```
len/2=0

Need >0

Need 1
```

---

## Step 9

```java
ans++;
```

Found one valid subarray.

Increase the answer.

---

## Step 10

```java
return ans;
```

Return the total number of valid subarrays.

---

# Why does `targetCount` not reset inside the inner loop?

Suppose

```
nums=[1,2,2,3]
```

For `l=0`

Initially

```
targetCount=0
```

As `r` moves:

```
r=0

[1]

count=0
```

```
r=1

[1,2]

count=1
```

```
r=2

[1,2,2]

count=2
```

```
r=3

[1,2,2,3]

count=2
```

The subarray only grows by one element each time, so we only need to check whether that newly added element is the target. This avoids recounting the entire subarray repeatedly.

---

# Time Complexity

* Outer loop runs `n` times.
* Inner loop runs up to `n` times for each outer iteration.

So the total time complexity is:

[
O(n^2)
]

---

# Space Complexity

We only use a few variables (`ans`, `targetCount`, `len`, etc.), so the extra space is:

[
O(1)
]

---

## Key Takeaways

* Fix the left boundary of the subarray.
* Expand the right boundary one element at a time.
* Maintain the count of `target` incrementally.
* Compute the current subarray length.
* If `targetCount > length / 2`, count the subarray as valid.
* This brute-force approach runs in **O(n²)** time and **O(1)** extra space, which is acceptable for `n ≤ 1000`.
