### Understanding the Problem

You are given the head of a singly linked list and need to **remove the middle node**.

The middle node is defined as:

[
\text{middle index} = \left\lfloor \frac{n}{2} \right\rfloor
]

using **0-based indexing**.

Examples:

* `n = 1` → remove index `0`
* `n = 2` → remove index `1`
* `n = 3` → remove index `1`
* `n = 4` → remove index `2`
* `n = 5` → remove index `2`

---

### Example Walkthrough

Input:

```text
1 → 3 → 4 → 7 → 1 → 2 → 6
```

Length `n = 7`

Middle index:

[
\left\lfloor \frac{7}{2} \right\rfloor = 3
]

Node at index `3` has value `7`.

After deletion:

```text
1 → 3 → 4 → 1 → 2 → 6
```

---

## Efficient Approach: Fast and Slow Pointers

Instead of counting all nodes first, we can find the middle node in **one traversal**.

### Key Idea

Use two pointers:

* `slow` moves **one step** at a time.
* `fast` moves **two steps** at a time.

When `fast` reaches the end:

* `slow` will be at the middle node.

However, to delete the middle node, we also need the node **before** `slow`.

So, maintain another pointer:

* `prev` → tracks the node before `slow`.

---

### Visualization

For:

```text
1 → 3 → 4 → 7 → 1 → 2 → 6
```

Initial state:

```text
prev = null
slow = 1
fast = 1
```

Iteration 1:

```text
prev = 1
slow = 3
fast = 4
```

Iteration 2:

```text
prev = 3
slow = 4
fast = 1
```

Iteration 3:

```text
prev = 4
slow = 7
fast = 6
```

`fast.next == null`, so stop.

Now:

* `slow` points to the middle node (`7`)
* `prev` points to node (`4`)

Delete the middle node:

```java
prev.next = slow.next;
```

Result:

```text
1 → 3 → 4 → 1 → 2 → 6
```

---

## Special Case

If the list contains only one node:

```text
2
```

Removing the middle node leaves an empty list.

Return:

```java
null
```

---

## Java Solution

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
    public ListNode deleteMiddle(ListNode head) {
        // If there is only one node
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        // Remove the middle node
        prev.next = slow.next;

        return head;
    }
}
```

---

## Why Does This Work?

The `fast` pointer moves twice as quickly as the `slow` pointer.

So when `fast` has covered the entire list:

* `slow` has covered exactly half the list.
* Therefore, `slow` points to the middle node.

The `prev` pointer ensures we can reconnect the list after removing `slow`.

```text
prev → slow → nextNode
```

becomes:

```text
prev → nextNode
```

using:

```java
prev.next = slow.next;
```

---

## Complexity Analysis

* **Time Complexity:** `O(n)`

  * We traverse the list only once.

* **Space Complexity:** `O(1)`

  * Only three pointers are used.

---

### Alternative Approach (Two Passes)

1. Count the total number of nodes.
2. Compute `middle = n / 2`.
3. Traverse again to the node before the middle.
4. Delete the middle node.

Complexity:

* Time: `O(n)`
* Space: `O(1)`

The fast-slow pointer approach is preferred because it finds and removes the middle node in a **single traversal**.
