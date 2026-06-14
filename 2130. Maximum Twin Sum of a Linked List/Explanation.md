## Understanding the Problem

You are given an **even-length linked list**.

For every node at position `i`, its **twin** is the node at position `n - 1 - i`.

Example:

```text
Index:  0   1   2   3
List : [5,  4,  2,  1]

Twin pairs:
0 ↔ 3  => 5 + 1 = 6
1 ↔ 2  => 4 + 2 = 6
```

The **twin sum** is the sum of a node and its twin.

We need to return the **maximum twin sum**.

---

## Brute Force Idea

Store all values in an array.

```text
[5,4,2,1]

arr[0] + arr[3] = 6
arr[1] + arr[2] = 6
```

Take the maximum.

### Complexity

* Time: `O(n)`
* Space: `O(n)`

But we can do better in terms of space.

---

# Optimal Approach (O(1) Extra Space)

## Key Observation

To calculate twin sums efficiently:

```text
First Half      Second Half
5 -> 4          2 -> 1
```

If we reverse the second half:

```text
5 -> 4
1 -> 2
```

Now corresponding twin nodes are aligned:

```text
5 + 1
4 + 2
```

We can traverse both halves together and find the maximum sum.

---

## Steps

### Step 1: Find the middle

Use **slow and fast pointers**.

```java
slow = head;
fast = head;
```

Move:

```java
slow = slow.next;
fast = fast.next.next;
```

When fast reaches the end:

```text
slow points to start of second half
```

Example:

```text
5 -> 4 -> 2 -> 1

slow = 2
```

---

### Step 2: Reverse the second half

Original:

```text
5 -> 4 -> 2 -> 1
          ↑
        slow
```

After reversing from `slow`:

```text
5 -> 4

1 -> 2
```

---

### Step 3: Calculate twin sums

Use two pointers:

```java
first = head;
second = reversedSecondHalf;
```

Compute:

```java
first.val + second.val
```

Move both pointers until second becomes null.

Keep track of maximum.

---

## Dry Run

### Input

```text
5 -> 4 -> 2 -> 1
```

### Find Middle

```text
slow = 2
```

### Reverse Second Half

```text
1 -> 2
```

### Compare

```text
5 + 1 = 6
4 + 2 = 6
```

Maximum = `6`

Answer = `6`

---

# Java Solution

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) {
 *         this.val = val;
 *         this.next = next;
 *     }
 * }
 */
class Solution {
    public int pairSum(ListNode head) {

        // Step 1: Find middle
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse second half
        ListNode prev = null;
        ListNode curr = slow;

        while (curr != null) {
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }

        // prev is head of reversed second half
        ListNode first = head;
        ListNode second = prev;

        // Step 3: Find maximum twin sum
        int maxSum = 0;

        while (second != null) {
            maxSum = Math.max(maxSum, first.val + second.val);
            first = first.next;
            second = second.next;
        }

        return maxSum;
    }
}
```

---

## Why Does This Work?

For an even-length list:

```text
a0 -> a1 -> a2 -> a3 -> a4 -> a5
```

Twin pairs are:

```text
a0 + a5
a1 + a4
a2 + a3
```

After reversing the second half:

```text
a5 -> a4 -> a3
```

Now:

```text
First Half :  a0 -> a1 -> a2
Reversed   :  a5 -> a4 -> a3
```

The twin nodes appear at the same positions, so one traversal is enough to compute all twin sums.

---

## Complexity Analysis

| Operation      | Time |
| -------------- | ---- |
| Find middle    | O(n) |
| Reverse half   | O(n) |
| Calculate sums | O(n) |

**Total Time:** `O(n)`

**Extra Space:** `O(1)`

This is the optimal solution expected in interviews and on LeetCode.
