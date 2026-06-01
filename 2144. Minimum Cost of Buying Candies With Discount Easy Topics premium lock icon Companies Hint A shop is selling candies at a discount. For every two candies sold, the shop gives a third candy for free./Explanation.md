## Understanding the Problem

The offer is:

* Buy **2 candies**
* Get **1 candy free**
* The free candy's cost must be **≤ the cheaper of the two candies you bought**

We need to **minimize the total money paid**.

---

### Key Observation

To maximize the discount, we should try to get the **most expensive possible candy for free**.

How?

1. Sort the candies in **descending order**.
2. Take candies in groups of **3**:

   * Pay for the first two (largest).
   * Get the third one free.

Since the array is sorted from largest to smallest:

```
9, 7, 6, 5, 2, 2
```

* Buy 9 and 7
* Get 6 free

because:

```
6 ≤ min(9,7)
```

which satisfies the condition.

Then:

* Buy 5 and 2
* Get 2 free

This gives the maximum possible discount.

---

## Example 1

```text
cost = [1,2,3]
```

Sort descending:

```text
[3,2,1]
```

Group:

```text
Buy 3 and 2
Free 1
```

Total:

```text
3 + 2 = 5
```

Answer:

```text
5
```

---

## Example 2

```text
cost = [6,5,7,9,2,2]
```

Sort descending:

```text
[9,7,6,5,2,2]
```

Groups:

```text
Buy 9 and 7
Free 6

Buy 5 and 2
Free 2
```

Total:

```text
9 + 7 + 5 + 2 = 23
```

Answer:

```text
23
```

---

## Approach

1. Sort array in ascending order.
2. Traverse from the end (largest element first).
3. Count candies.
4. Every third candy is free, so skip adding its cost.

---

## Dry Run

```java
cost = [6,5,7,9,2,2]
```

After sorting:

```java
[2,2,5,6,7,9]
```

Traverse from end:

| Candy | Count | Action | Total |
| ----- | ----- | ------ | ----- |
| 9     | 1     | Pay    | 9     |
| 7     | 2     | Pay    | 16    |
| 6     | 3     | Free   | 16    |
| 5     | 4     | Pay    | 21    |
| 2     | 5     | Pay    | 23    |
| 2     | 6     | Free   | 23    |

Answer:

```java
23
```

---

## Java Solution

```java
import java.util.Arrays;

class Solution {
    public int minimumCost(int[] cost) {
        Arrays.sort(cost);

        int total = 0;
        int count = 0;

        for (int i = cost.length - 1; i >= 0; i--) {
            count++;

            // Every 3rd candy is free
            if (count % 3 == 0) {
                continue;
            }

            total += cost[i];
        }

        return total;
    }
}
```

---

## Why Does This Work?

After sorting in descending order:

```text
largest, second largest, third largest
```

The third candy is always less than or equal to the first two candies, so it can legally be taken for free.

To maximize savings, we always want the **largest possible candy to be the free one**. Grouping candies in descending order ensures that every free candy is as expensive as possible.

This is a classic **Greedy** approach.

---

## Complexity Analysis

Sorting:

```text
O(n log n)
```

Traversal:

```text
O(n)
```

Total:

```text
O(n log n)
```

Space:

```text
O(1)
```

(ignoring the space used by Java's sorting algorithm).
