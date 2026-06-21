## Problem Understanding

You are given:

* `costs[i]` → price of the `i-th` ice cream bar.
* `coins` → total money available.

You can buy the bars in **any order**, and your goal is to buy the **maximum number of bars**.

### Example

```text
costs = [1,3,2,4,1]
coins = 7
```

To maximize the number of bars, you should always buy the **cheapest bars first**:

```text
Sorted costs: [1,1,2,3,4]

Buy 1 → coins = 6
Buy 1 → coins = 5
Buy 2 → coins = 3
Buy 3 → coins = 0

Total bars bought = 4
```

---

## Why Greedy Works

Suppose you buy an expensive bar before a cheaper one.

Example:

```text
coins = 5
costs = [4,1,2]
```

If you buy `4` first:

```text
Remaining coins = 1
Total bars = 1
```

If you buy `1` and `2` first:

```text
Remaining coins = 2
Total bars = 2
```

To maximize the **count**, always spend the least amount possible on each purchase.

So the strategy is:

1. Buy the cheapest bars first.
2. Continue until you don't have enough coins.

---

## Why Counting Sort?

The problem explicitly requires solving it using **counting sort**.

Notice the constraint:

```text
1 <= costs[i] <= 100000
```

The maximum possible cost is only `100000`, which is small enough to create a frequency array.

Instead of sorting `n` elements using comparison sort (`O(n log n)`), we can count how many bars exist at each price.

---

## Counting Sort Approach

### Step 1: Find the maximum cost

```text
maxCost = maximum value in costs
```

### Step 2: Build frequency array

```text
freq[price] = number of bars with that price
```

Example:

```text
costs = [1,3,2,4,1]

freq:

1 → 2
2 → 1
3 → 1
4 → 1
```

### Step 3: Buy bars from cheapest to most expensive

For every price:

* Determine how many bars you can afford.
* Take the minimum of:

  * available bars at that price
  * bars affordable with current coins

---

## Dry Run

```text
costs = [1,3,2,4,1]
coins = 7
```

Frequency:

```text
1 → 2
2 → 1
3 → 1
4 → 1
```

### Price = 1

```text
Can afford 7 / 1 = 7 bars
Available = 2

Buy 2

coins = 7 - (2 × 1) = 5
count = 2
```

### Price = 2

```text
Can afford 5 / 2 = 2 bars
Available = 1

Buy 1

coins = 3
count = 3
```

### Price = 3

```text
Can afford 3 / 3 = 1 bar
Available = 1

Buy 1

coins = 0
count = 4
```

No coins left.

Answer:

```text
4
```

---

## Java Solution

```java
class Solution {
    public int maxIceCream(int[] costs, int coins) {
        int maxCost = 0;

        // Find maximum cost
        for (int cost : costs) {
            maxCost = Math.max(maxCost, cost);
        }

        // Frequency array for counting sort
        int[] freq = new int[maxCost + 1];

        for (int cost : costs) {
            freq[cost]++;
        }

        int count = 0;

        // Buy bars from cheapest to most expensive
        for (int price = 1; price <= maxCost && coins >= price; price++) {

            if (freq[price] == 0) {
                continue;
            }

            int canBuy = Math.min(freq[price], coins / price);

            count += canBuy;
            coins -= canBuy * price;
        }

        return count;
    }
}
```

---

## Complexity Analysis

Let:

* `n = costs.length`
* `m = maximum cost value`

### Time Complexity

```text
O(n + m)
```

* Finding max cost → `O(n)`
* Building frequency array → `O(n)`
* Traversing frequency array → `O(m)`

Since:

```text
m ≤ 100000
```

this is efficient.

---

### Space Complexity

```text
O(m)
```

for the frequency array.

---

## Why This Is Better Than Regular Sorting

A normal sorting approach would take:

```text
O(n log n)
```

Counting sort takes:

```text
O(n + m)
```

Because the cost values are bounded (`≤ 100000`), counting sort is faster and satisfies the problem requirement.

The key observation is:

> We do not need the exact order of all elements—we only need to know how many bars exist at each price.
