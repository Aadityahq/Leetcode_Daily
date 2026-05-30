# 1. What is actually being asked?

For query:

```text
[2, x, sz]
```

we need to know:

> Is there any continuous obstacle-free segment inside `[0, x]` whose length is at least `sz`?

Because a block can touch obstacles, it only needs an interval of length `sz`.

So the problem becomes:

**Find the maximum free gap in `[0,x]`.**

If

```text
maxGap >= sz
```

answer = true.

Otherwise false.

---

# 2. Representing gaps

Suppose obstacles are:

```text
0, 2, 7
```

(0 is treated as a fixed obstacle)

Number line:

```text
0----2---------7
```

Gaps:

```text
2-0 = 2
7-2 = 5
```

Store each gap at its right endpoint:

```text
gap ending at 2 = 2
gap ending at 7 = 5
```

Why?

Because when a new obstacle is inserted, only nearby gaps change.

---

# 3. What happens when inserting an obstacle?

Suppose:

```text
0 ----------- 10
```

Gap:

```text
10
```

Insert obstacle at:

```text
x = 4
```

Now:

```text
0----4------10
```

Old gap:

```text
10
```

becomes:

```text
4
6
```

Only two endpoints change:

```java
st.update(4, 4);
st.update(10, 6);
```

Everything else remains same.

This is why insertion is only `O(log n)`.

---

# 4. Why TreeSet?

We need nearest obstacles.

For obstacle x:

```java
prev = obstacles.floor(x);
nxt  = obstacles.ceiling(x);
```

Example:

```text
0   2   7   12
```

Insert:

```text
x = 5
```

Then:

```java
prev = 2
nxt = 7
```

which lets us split the gap.

TreeSet gives these in:

```text
O(log n)
```

---

# 5. Why Segment Tree?

We need:

> Largest gap among obstacles ≤ x

Many queries ask this.

A segment tree stores:

```text
gap ending at position i
```

and supports:

```java
max gap in prefix [0 ... i]
```

in:

```text
O(log n)
```

---

# 6. Understanding the Segment Tree value

Suppose obstacles:

```text
0, 2, 7, 12
```

Stored values:

| Position | Gap ending here |
| -------- | --------------- |
| 2        | 2               |
| 7        | 5               |
| 12       | 5               |

Segment tree stores max of these.

---

# 7. The tricky part of Type-2 query

Consider:

```text
Obstacles: 0,2,7

Query:
x = 5
```

Inside `[0,5]`:

```text
0----2-----5
```

Maximum gap is:

```text
3
```

But there is no obstacle at 5.

So how do we handle the last partial segment?

---

### Find obstacle just before x

```java
prev = obstacles.floor(x);
```

Example:

```text
prev = 2
```

Then:

```java
x - prev = 3
```

This is the final free segment:

```text
(2,5)
```

---

### Find best complete gap before prev

```java
st.query(prev)
```

returns maximum gap ending at or before `prev`.

For:

```text
0,2,7
```

and

```text
prev = 2
```

we get:

```text
2
```

---

### Maximum free space in [0,x]

```java
mx = max(
        x-prev,
        st.query(prev)
     );
```

---

# Example

Obstacles:

```text
0,2,7
```

Query:

```text
x=5
```

Then:

```java
prev = 2

x-prev = 3

st.query(2)=2
```

Therefore:

```java
mx = max(3,2)=3
```

Correct.

---

# 8. Why this works

Any free segment inside `[0,x]` is one of two types:

### Case 1

Completely between two obstacles

Example:

```text
2-----7
```

These gaps are stored in Segment Tree.

Obtained by:

```java
st.query(prev)
```

---

### Case 2

The last segment ending at x

Example:

```text
7------x
```

Length:

```java
x-prev
```

This gap is not stored in tree because x isn't an obstacle.

So we check it separately.

---

Thus:

```java
mx = max(st.query(prev), x-prev)
```

gives the largest possible free interval in `[0,x]`.

---

# 9. Dry Run (Example 1)

Queries:

```text
[1,2]
[2,3,3]
[2,3,1]
[2,2,2]
```

Initial:

```text
obstacles = {0}
```

---

### Insert 2

```text
prev=0
nxt=null
```

Update:

```java
st[2]=2
```

Obstacles:

```text
{0,2}
```

---

### Query (3,3)

```text
prev=2

x-prev = 1

st.query(2)=2
```

So:

```text
mx=max(1,2)=2
```

Need:

```text
3
```

Result:

```text
false
```

---

### Query (3,1)

```text
mx=2
```

Need:

```text
1
```

Result:

```text
true
```

---

### Query (2,2)

```text
prev=2

x-prev=0

st.query(2)=2
```

```text
mx=2
```

Need:

```text
2
```

Result:

```text
true
```

Output:

```text
[false,true,true]
```

---

# Complexity

For each query:

### TreeSet

```text
floor()
ceiling()
add()
```

all:

```text
O(log N)
```

### Segment Tree

```text
update()
query()
```

all:

```text
O(log N)
```

Therefore:

```text
Time  : O(Q log Q)
Space : O(Q)
```

where `Q ≤ 150000`.

This is fast enough for the constraints.
