## Problem Understanding

You are given a linked list and an integer `k`.

You need to **rotate the list to the right by `k` places**.

### What does “rotate right” mean?

Take the last node and move it to the front.

Example:

`1 → 2 → 3 → 4 → 5`

Rotate once:

`5 → 1 → 2 → 3 → 4`

Rotate twice:

`4 → 5 → 1 → 2 → 3`

---

# Example Walkthrough

## Example 1

Input:

```text
head = [1,2,3,4,5], k = 2
```

### Rotation 1

```text
5 → 1 → 2 → 3 → 4
```

### Rotation 2

```text
4 → 5 → 1 → 2 → 3
```

Output:

```text
[4,5,1,2,3]
```

---

# Important Observation

If the list length is `n`, then:

```text
k % n
```

actual rotations are enough.

Because after `n` rotations, the list becomes the same again.

Example:

Length = 5

```text
k = 12
12 % 5 = 2
```

So only 2 rotations matter.

---

# Efficient Idea

Instead of rotating one by one:

## Step 1: Find length of list

Example:

```text
1 → 2 → 3 → 4 → 5
```

Length = 5

---

## Step 2: Connect tail to head

Make it circular.

```text
1 → 2 → 3 → 4 → 5
↑                 ↓
← ← ← ← ← ← ← ← ←
```

---

## Step 3: Find new tail

For right rotation by `k`:

New tail position:

```text
length - (k % length) - 1
```

Why?

Because the last `k` nodes move to the front.

---

## Step 4: Break the circle

The node after new tail becomes new head.

---

# Dry Run

## Input

```text
1 → 2 → 3 → 4 → 5
k = 2
```

Length = 5

Effective rotation:

```text
k = 2 % 5 = 2
```

New tail index:

```text
5 - 2 - 1 = 2
```

Node at index 2 = `3`

So:

* New tail = `3`
* New head = `4`

Break connection after `3`

Result:

```text
4 → 5 → 1 → 2 → 3
```

---

# Java Solution

```java
class Solution {
    public ListNode rotateRight(ListNode head, int k) {

        // Edge cases
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        // Step 1: Find length and tail
        int length = 1;
        ListNode tail = head;

        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Step 2: Reduce k
        k = k % length;

        // If no rotation needed
        if (k == 0) {
            return head;
        }

        // Step 3: Make circular list
        tail.next = head;

        // Step 4: Find new tail
        int stepsToNewTail = length - k - 1;

        ListNode newTail = head;

        for (int i = 0; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }

        // Step 5: New head
        ListNode newHead = newTail.next;

        // Break the circle
        newTail.next = null;

        return newHead;
    }
}
```

---

# Why This Works

## Core Logic

When rotating right by `k`:

* Last `k` nodes come to front
* Remaining nodes stay behind

By making the list circular:

* We can easily “shift” the starting point

Then we break the circle at the correct place.

---

# Time Complexity

## Finding length

```text
O(n)
```

## Finding new tail

```text
O(n)
```

Total:

```text
O(n)
```

---

# Space Complexity

```text
O(1)
```

No extra data structures used.

---

# Visualization

Original:

```text
1 → 2 → 3 → 4 → 5
```

Circular:

```text
1 → 2 → 3 → 4 → 5
↑                 ↓
← ← ← ← ← ← ← ← ←
```

Break after `3`:

```text
4 → 5 → 1 → 2 → 3
```

---

# Key Learning

This problem teaches:

* Linked list manipulation
* Circular linked list concept
* Using modulo to optimize rotations
* Finding new head/tail efficiently
