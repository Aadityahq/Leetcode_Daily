This problem is one of the **most important applications of the Monotonic Stack**. Let's understand it from the beginning.

---

# Problem Understanding

You are given two arrays:

```text
nums1 = [4,1,2]
nums2 = [1,3,4,2]
```

`nums1` is a subset of `nums2`.

For every element in `nums1`, find its **Next Greater Element** in `nums2`.

### What is Next Greater Element?

It is the **first element to the right that is larger**.

Example

```
nums2 = [1,3,4,2]
```

For every number

```
1 → 3
3 → 4
4 → -1
2 → -1
```

because

```
1
 \
  3  ← first greater

3
 \
  4 ← first greater

4
 \
  2 (smaller)
Nothing greater
→ -1

2
Nothing on right
→ -1
```

Now answer for nums1

```
4 → -1
1 → 3
2 → -1
```

Output

```
[-1,3,-1]
```

---

# Brute Force

For every element of nums1

1. Find it inside nums2
2. Move right
3. Find first greater number

Example

```
nums1 = [2,4]

nums2

1 2 3 4
```

For 2

```
2
 \
 3 ← answer
```

For 4

```
Nothing greater
```

Time Complexity

```
O(n × m)
```

Not efficient.

---

# Better Idea

Instead of searching again and again,

Let's first compute the next greater element for **every number in nums2**.

Then answering nums1 becomes very easy.

Suppose after preprocessing we have

```
1 → 3
2 → 3
3 → 4
4 → -1
```

Then

```
nums1

2 → lookup → 3

4 → lookup → -1
```

Just array lookup.

---

# But how do we preprocess efficiently?

This is where **Monotonic Stack** comes.

---

# Why Traverse from Right?

Suppose

```
nums2

1 3 4 2
```

If we stand at

```
1
```

We don't know future elements.

But if we stand from the **right**, then everything to the right has already been processed.

That is why we traverse

```
← ← ←
```

instead of

```
→ → →
```

---

# Stack Idea

The stack stores **possible next greater elements**.

It always remains in **strictly decreasing order** from bottom to top.

Example

Start

```
nums2

1 3 4 2
```

Go from right.

---

## Step 1

Current = 2

Stack

```
[]
```

No greater element

Answer

```
2 → -1
```

Push 2

```
Stack

2
```

---

## Step 2

Current = 4

Stack

```
2
```

Can 2 be next greater?

No

```
2 < 4
```

Pop it.

```
[]
```

Now stack empty.

```
4 → -1
```

Push 4

```
4
```

---

## Step 3

Current = 3

Stack

```
4
```

Top is

```
4 > 3
```

Perfect.

```
3 → 4
```

Push 3

```
4
3
```

Notice

Stack remains decreasing

```
4
3
```

---

## Step 4

Current = 1

Stack

```
4
3
```

Top

```
3 > 1
```

Answer

```
1 → 3
```

Push

```
4
3
1
```

Done.

Final mapping

```
1 → 3
3 → 4
4 → -1
2 → -1
```

Exactly what we wanted.

---

# Why Pop Smaller Elements?

Suppose stack is

```
6
5
2
```

Current is

```
4
```

Can 2 ever become answer?

```
2 < 4
```

No.

If current itself is bigger,

every element left of current will meet 4 before reaching 2.

So

```
2
```

is useless forever.

Remove it.

This is why

```java
while(stack.peek() <= current)
    stack.pop();
```

---

# Dry Run of Code

```java
nums2 = [1,3,4,2]
```

Initial

```
Stack = []

nextGreater[]
```

---

### i = 3

Current = 2

```
Stack empty

nextGreater[2] = -1
```

Push

```
2
```

---

### i = 2

Current = 4

```
Stack

2
```

Pop

```
2
```

Empty

```
nextGreater[4] = -1
```

Push

```
4
```

---

### i = 1

Current = 3

Stack

```
4
```

Top

```
4 > 3
```

```
nextGreater[3]=4
```

Push

```
4
3
```

---

### i = 0

Current = 1

Stack

```
4
3
```

Top

```
3 >1
```

```
nextGreater[1]=3
```

Push

```
4
3
1
```

Done.

Mapping

```
1→3
2→-1
3→4
4→-1
```

---

# Second Loop

Now

```java
nums1 = [4,1,2]
```

Simply replace

```
4 → -1

1 → 3

2 → -1
```

Return

```
[-1,3,-1]
```

---

# Code Explanation Line by Line

```java
int[] nextGreater = new int[10001];
```

Since

```
0 ≤ value ≤ 10000
```

we can directly use the value as an index.

Example

```
nextGreater[5]
```

stores answer for value 5.

---

```java
Stack<Integer> stack = new Stack<>();
```

Monotonic decreasing stack.

---

```java
for(int i = nums2.length-1; i>=0; i--)
```

Traverse from right.

---

```java
while(!stack.isEmpty() && stack.peek() <= nums2[i])
```

Remove all smaller or equal elements because they can never become the next greater element.

---

```java
nextGreater[nums2[i]] =
        stack.isEmpty() ? -1 : stack.peek();
```

If stack empty

```
No greater element
```

Otherwise

Top is the first greater element.

---

```java
stack.push(nums2[i]);
```

Current element may become next greater for future elements on its left.

---

```java
for(int i=0;i<nums1.length;i++)
```

Answer each query.

---

```java
nums1[i]=nextGreater[nums1[i]];
```

Direct lookup.

---

```java
return nums1;
```

Done.

---

# Why Does This Work?

The stack always contains elements to the **right** of the current element.

Before adding the current element, we remove all smaller elements because:

* They cannot be the next greater element for the current number.
* They also cannot help any element further to the left, since the current (larger) element blocks them.

After removing smaller elements:

* If the stack is empty, no greater element exists.
* Otherwise, the top of the stack is the **nearest greater element to the right**.

---

# Time Complexity

Building next greater array:

```
O(n)
```

Each element is:

* Pushed once
* Popped at most once

Processing nums1:

```
O(m)
```

Total:

```
O(n + m)
```

where:

* `n = nums2.length`
* `m = nums1.length`

---

# Space Complexity

* Stack: **O(n)** (in the worst case)
* `nextGreater` array: **O(10001)**, which is effectively **O(1)** because the value range is fixed (`0` to `10000`).

---

## Key Takeaways

* Traverse **from right to left** so all right-side information is already available.
* Use a **monotonic decreasing stack** to efficiently find the next greater element.
* Pop all smaller (or equal) elements because they can never be useful again.
* Store the next greater element for every value in `nums2`, then answer each query from `nums1` in **O(1)** time.
