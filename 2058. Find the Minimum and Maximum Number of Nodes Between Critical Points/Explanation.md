## LeetCode 2058 — Find the Minimum and Maximum Number of Nodes Between Critical Points

### 💡 Problem Understanding

A **critical point** is a node that is either:

1. **Local Maximum**

   ```text
   current > previous && current > next
   ```

2. **Local Minimum**

   ```text
   current < previous && current < next
   ```

The first and last nodes **can never be critical points** because they don't have both a previous and next node.

We need to find:

```text
[minDistance, maxDistance]
```

where:

* `minDistance` = smallest distance between any two critical points.
* `maxDistance` = largest distance between any two critical points.
* If there are fewer than 2 critical points → `[-1, -1]`.

---

# 🔍 Example

Consider:

```text
5 → 3 → 1 → 2 → 5 → 1 → 2
```

Index:

```text
1   2   3   4   5   6   7
5   3   1   2   5   1   2
        ↑       ↑   ↑
       min     max min
```

Critical points are at:

```text
3, 5, 6
```

Distances:

```text
5 - 3 = 2
6 - 5 = 1
6 - 3 = 3
```

Therefore:

```text
minimum = 1
maximum = 3
```

Answer:

```text
[1, 3]
```

---

# 🧠 Key Observation

We **don't actually need to store all critical points**.

Suppose critical points occur at:

```text
3, 5, 6, 10
```

For the **minimum distance**, we only need distances between **consecutive critical points**:

```text
5 - 3 = 2
6 - 5 = 1
10 - 6 = 4
```

So:

```text
minDistance = min(2, 1, 4) = 1
```

For the **maximum distance**, we only need:

```text
lastCriticalPoint - firstCriticalPoint
```

So:

```text
10 - 3 = 7
```

Therefore, while traversing the linked list, we only need to remember:

```text
firstCritical = first critical point
previousCritical = most recently found critical point
minDistance
maxDistance
```

This lets us solve the problem in **one traversal** and **O(1) extra space**.

---

# ✅ Java Solution

```java
class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int firstCritical = -1;
        int previousCritical = -1;

        int minDistance = Integer.MAX_VALUE;
        int maxDistance = -1;

        int index = 1;

        ListNode prev = head;
        ListNode curr = head.next;

        while (curr != null && curr.next != null) {

            ListNode next = curr.next;

            // Check whether curr is a critical point
            boolean isCritical =
                    (curr.val > prev.val && curr.val > next.val) ||
                    (curr.val < prev.val && curr.val < next.val);

            if (isCritical) {

                // First critical point
                if (firstCritical == -1) {
                    firstCritical = index;
                }

                // We already have a previous critical point
                if (previousCritical != -1) {

                    int distance = index - previousCritical;

                    minDistance = Math.min(minDistance, distance);
                }

                previousCritical = index;

                // Distance between first and current critical point
                maxDistance = index - firstCritical;
            }

            prev = curr;
            curr = next;
            index++;
        }

        // Fewer than two critical points
        if (firstCritical == previousCritical) {
            return new int[]{-1, -1};
        }

        return new int[]{minDistance, maxDistance};
    }
}
```

---

# 🧩 How the Traversal Works

We use three nodes:

```text
prev → curr → next
```

This is important because to determine whether `curr` is a critical point, we need:

```text
prev.val
curr.val
next.val
```

For example:

```text
3 → 1 → 2
    ↑
   curr
```

We check:

```text
1 < 3
1 < 2
```

Both are true, so `1` is a **local minimum**.

---

# 🔎 Critical Point Detection

This condition handles both cases:

```java
boolean isCritical =
        (curr.val > prev.val && curr.val > next.val) ||
        (curr.val < prev.val && curr.val < next.val);
```

### Local maximum

```text
prev = 3
curr = 5
next = 2
```

We have:

```text
5 > 3
5 > 2
```

Therefore:

```java
curr.val > prev.val && curr.val > next.val
```

is true.

### Local minimum

```text
prev = 5
curr = 2
next = 4
```

We have:

```text
2 < 5
2 < 4
```

Therefore:

```java
curr.val < prev.val && curr.val < next.val
```

is true.

Notice the **strictly** greater/less condition. So if:

```text
1 → 3 → 3
```

the middle `3` is **not** a critical point.

---

# 📌 Why `index` Starts at 1

We use:

```java
int index = 1;
```

and treat the first node as position `1`.

For example:

```text
5 → 3 → 1 → 2 → 5
1   2   3   4   5
```

Critical points might be:

```text
3 and 5
```

Their distance is:

```text
5 - 3 = 2
```

Whether we use 0-based or 1-based indexing doesn't matter for the distance, but 1-based indexing makes the explanation match the problem's node numbering.

---

# 🧠 The Most Important Part

When we find a critical point:

```java
if (previousCritical != -1) {

    int distance = index - previousCritical;

    minDistance = Math.min(minDistance, distance);
}
```

Suppose critical points are:

```text
3 → 5 → 6 → 10
```

When we reach `5`:

```text
distance = 5 - 3 = 2
```

When we reach `6`:

```text
distance = 6 - 5 = 1
```

When we reach `10`:

```text
distance = 10 - 6 = 4
```

So we continuously maintain:

```text
minDistance = min(2, 1, 4)
            = 1
```

We don't need to store:

```text
[3, 5, 6, 10]
```

---

# 📏 Finding Maximum Distance

This is even simpler.

The maximum distance between any two critical points will always be between:

```text
first critical point
        ↓
3 → 5 → 6 → 10
↑             ↑
first         last
```

Therefore:

```text
maxDistance = lastCritical - firstCritical
```

That's why we do:

```java
maxDistance = index - firstCritical;
```

every time we discover another critical point.

---

# 🛑 Why This Condition?

```java
while (curr != null && curr.next != null)
```

Because `curr` must have:

```text
previous + current + next
```

For example:

```text
5 → 3 → 1 → 2
    ↑
   curr
```

We can inspect `3` because it has both:

```text
previous = 5
next = 1
```

But we cannot inspect the last node:

```text
5 → 3 → 1 → 2
            ↑
           last
```

because it has no `next`.

Hence:

```java
curr != null && curr.next != null
```

---

# 🧪 Dry Run

Input:

```text
5 → 3 → 1 → 2 → 5 → 1 → 2
```

Initial:

```text
firstCritical = -1
previousCritical = -1
minDistance = ∞
maxDistance = -1
```

### Node 3

```text
5 → 3 → 1
    ↑
```

`3` is neither max nor min.

Nothing changes.

---

### Node 1

```text
3 → 1 → 2
    ↑
```

`1 < 3` and `1 < 2`.

Critical point!

```text
firstCritical = 3
previousCritical = 3
```

No distance yet because we only have **one** critical point.

---

### Node 5

```text
2 → 5 → 1
    ↑
```

Critical point.

Distance from previous critical point:

```text
5 - 3 = 2
```

Therefore:

```text
minDistance = 2
maxDistance = 2
previousCritical = 5
```

---

### Node 1

```text
5 → 1 → 2
    ↑
```

Critical point.

Distance:

```text
6 - 5 = 1
```

Update:

```text
minDistance = min(2, 1)
            = 1

maxDistance = 6 - 3
            = 3
```

Final:

```text
[1, 3]
```

---

# ⏱️ Complexity

### Time

We traverse the linked list once:

```text
O(n)
```

where `n` is the number of nodes.

### Space

We only store a few variables:

```text
firstCritical
previousCritical
minDistance
maxDistance
```

Therefore:

```text
O(1)
```

extra space.

---

# 🎯 Interview Mindset

The important trick in this problem isn't linked-list traversal itself. It's recognizing **what information actually needs to be remembered**.

You might initially think:

```text
Find all critical points
        ↓
Store them in ArrayList
        ↓
Calculate all distances
```

That works, but uses **O(n)** extra space.

Instead:

```text
Traverse
   ↓
Find critical point
   ↓
Compare with previous critical point → minimum
   ↓
Compare with first critical point    → maximum
```

So the entire solution becomes:

```text
             Critical Point
                    │
          ┌─────────┴─────────┐
          ↓                   ↓
   index - previous      index - first
          │                   │
          ↓                   ↓
     minDistance         maxDistance
```

**Core pattern to remember:**

> When processing special positions in a sequence, if minimum distance only depends on consecutive positions and maximum distance depends on the first/last positions, you don't need to store all positions.
