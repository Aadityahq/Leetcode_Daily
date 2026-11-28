# **LeetCode 3947 – Maximum Number of Items From Sale II**

**Difficulty:** Medium\
**Language:** Java

Before writing the solution, let's understand the most important part of this problem: buying an item can give us an additional free item, but only a limited number of times.

The challenge is not simply to buy the cheapest item. We need to maximize:

\\[ \text{Purchased Items}+\text{Free Items} \\]

while keeping the total purchase cost within the budget.

## **1. Understanding the problem**

We are given:

- `items[i][0]` → Factor of the item.
- `items[i][1]` → Price of the item.
- `budget` → Maximum money we can spend.

There are unlimited copies of every item.

However, each purchased copy can give us at most one free item of another type, provided:

1. The purchased item and free item are different types.
2. The purchased item's factor divides the free item's factor.
3. Each ordered pair of item types can be used only once.

**Important observation:**

If we purchase multiple copies of the same item, we cannot repeatedly receive free copies of the same other item type.

For example, if item A has factor `1` and items B and C have factors `2` and `3`, purchasing A can give us:

- One free B.
- One free C.

But purchasing A again cannot give us another free B or C.

Therefore, the number of free copies obtainable from an item type is limited by the number of other item types whose factors are divisible by its factor.

## **2. Key observation – Calculate the free items**

Suppose we purchase `x` copies of item `i`.

Let `gain[i]` represent the number of other item types whose factors are divisible by `factor[i]`.

Then:

\\[ \text{Free Items}=\min(x,gain[i]) \\]

Therefore:

\\[ \text{Total Items}=x+\min(x,gain[i]) \\]

This gives us two types of purchases:

| Purchase               | Total value          |
| ---------------------- | -------------------- |
| First `gain[i]` copies | 2 items per purchase |
| Remaining copies       | 1 item per purchase  |

Why?

- Every one of the first `gain[i]` purchases can generate a different free item.
- After that, all eligible item types have already been used.
- Further purchases only give us the purchased item itself.

This observation converts the problem into a greedy problem.

## **3. Calculate&#x20;**`gain[i]`**&#x20;efficiently**

We cannot check every pair of items because:

\\[ N\leq 10^5 \\]

An \\(O(N^2)\\) solution will be too slow.

Instead, use a frequency array.

Suppose:

```
items = [[1, 6], [2, 4], [3, 5]]
```

First, count how many items have each factor.

```
factor:  1  2  3
freq:    1  1  1
```

Now calculate how many item factors are multiples of each factor.

For factor `1`:

- Multiples: 1, 2, 3
- Total items = 3
- Excluding itself = 3 - 1 = 2

For factor `2`:

- Multiples: 2
- Total items = 1
- Excluding itself = 1 - 1 = 0

For factor `3`:

- Multiples: 3
- Total items = 1
- Excluding itself = 1 - 1 = 0

So:

```
factor:  1  2  3
gain:    2  0  0
```

We can calculate this using a multiples-based sieve:

```
for (int i = 1; i <= maxFactor; i++) {
    for (int j = i; j <= maxFactor; j += i) {
        divisible[i] += freq[j];
    }
}
```

Finally:

```
gain = divisible[factor] - 1;
```

## **4. Greedy approach – Why sorting works**

Let `minPrice` be the cheapest item price.

We have two choices:

**Choice A:** Buy a normal item at the cheapest price.

- Cost = `minPrice`
- Value = 1 item.

**Choice B:** Buy an item that generates a free item.

- Cost = `price`
- Value = 2 items.

When is Choice B better?

\\[ \frac{2}{price}>\frac{1}{minPrice} \\]

Rearranging:

\\[ price<2\times minPrice \\]

This is the most important greedy condition.

Therefore:

1. Ignore items whose price is at least twice the cheapest price. They are no better than spending that money on normal items.
2. Sort the remaining items by price.
3. Buy as many bonus-generating copies as possible, starting with the cheapest.
4. Use the remaining budget to purchase the cheapest normal items.

We process bonus copies in batches instead of creating individual entries for every copy.

**Why is this optimal?**

Every bonus-generating purchase gives exactly 2 items. Therefore, among eligible purchases, buying the cheapest ones first maximizes how many bonus purchases fit into the budget.

After exhausting affordable bonus opportunities, spending the remaining budget on the cheapest item gives the maximum number of additional ordinary purchases.

This is the greedy strategy described by the problem's hints.&#x20;

[image](https://www.google.com/s2/favicons?domain=https://leetcode.com\&sz=32)

LeetCode+2



## **5. Java solution**

```

import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;
        int maxFactor = 0;
        int minPrice = Integer.MAX_VALUE;

        // Step 1: Find maximum factor and minimum price
        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
            minPrice = Math.min(minPrice, item[1]);
        }

        // Step 2: Count frequency of each factor
        int[] freq = new int[maxFactor + 1];

        for (int[] item : items) {
            freq[item[0]]++;
        }

        // Step 3: Count items whose factors are multiples
        int[] divisible = new int[maxFactor + 1];

        for (int i = 1; i <= maxFactor; i++) {
            for (int j = i; j <= maxFactor; j += i) {
                divisible[i] += freq[j];
            }
        }

        // Step 4: Sort items by price
        Arrays.sort(items, (a, b) ->
            Integer.compare(a[1], b[1])
        );

        long remaining = budget;
        long ans = 0;

        // Step 5: Buy bonus-generating items first
        for (int[] item : items) {

            int factor = item[0];
            int price = item[1];

            // Skip items that are not profitable
            if (price >= 2L * minPrice) {
                break;
            }

            // Number of available free-item opportunities
            int gain = divisible[factor] - 1;

            if (gain == 0) {
                continue;
            }

            // Maximum bonus purchases possible
            long take = Math.min(
                (long) gain,
                remaining / price
            );

            ans += take * 2;
            remaining -= take * price;
        }

        // Step 6: Spend remaining budget on cheapest items
        ans += remaining / minPrice;

        return (int) ans;
    }
}

```

## **6. Dry run – Example 1**

**Input:**

```
items = new int[][] {
    {1, 6},
    {2, 4},
    {3, 5}
};

budget = 19;
```

**Step 1: Find minimum price**

```
minPrice = 4
```

**Step 2: Calculate gain**

| Factor | Divisible item factors | Gain |
| ------ | ---------------------- | ---- |
| 1      | 1, 2, 3                | 2    |
| 2      | 2                      | 0    |
| 3      | 3                      | 0    |

**Step 3: Calculate the profitable price limit**

\\[ 2\times minPrice=2\times4=8 \\]

Only items with price less than 8 are considered.

**Step 4: Sort by price**

```
Factor    Price    Gain
  2         4        0
  3         5        0
  1         6        2
```

**Step 5: Process bonus purchases**

Initially:

```
remaining = 19
ans = 0
```

| Item     | Price | Gain | Take | Remaining | Answer |
| -------- | ----- | ---- | ---- | --------- | ------ |
| Factor 2 | 4     | 0    | 0    | 19        | 0      |
| Factor 3 | 5     | 0    | 0    | 19        | 0      |
| Factor 1 | 6     | 2    | 2    | 7         | 4      |

For factor 1:

```
take = min(gain, remaining / price)
     = min(2, 19 / 6)
     = 2
```

Each purchase generates two items.

```
ans = 2 * 2 = 4

remaining = 19 - (2 * 6)
          = 7
```

**Step 6: Buy normal items**

```
ans += remaining / minPrice

ans += 7 / 4

ans = 4 + 1

ans = 5
```

Output: 5

## **7. Complexity analysis**

Let:

- \\(N\\) = number of items.
- \\(M\\) = maximum factor.

| Operation                             | Time complexity  |
| ------------------------------------- | ---------------- |
| Find minimum price and maximum factor | \\(O(N)\\)       |
| Build frequency array                 | \\(O(N)\\)       |
| Count divisible factors               | \\(O(M\log M)\\) |
| Sort items                            | \\(O(N\log N)\\) |
| Process purchases                     | \\(O(N)\\)       |

**Overall time complexity:**

\\[ \boxed{O(N\log N+M\log M)} \\]

Since \\(M\leq N\\):

\\[ \boxed{O(N\log N)} \\]

**Space complexity:**

\\[ \boxed{O(N)} \\]

We use frequency and divisible arrays, requiring \\(O(M)\\) space. Sorting may also require additional internal space.

## **8. Important concepts to remember**

This problem tests four important concepts:

1. **Frequency Array:** Count how many items have each factor.
2. **Multiples Sieve:** Efficiently count all factors divisible by a given factor.
3. **Greedy Algorithm:** Prioritize purchases that provide two items for a lower cost.
4. **Sorting:** Process cheaper bonus-generating purchases first.

**Most important formula:**

\\[ \boxed{gain[i]=divisible[factor_i]-1} \\]

And the greedy condition:

\\[ \boxed{price<2\times minPrice} \\]

**Interview question:** Why do we use `long` for `remaining` and `ans` even though `budget` is an integer?

Think about the maximum possible number of items when the budget is \\(10^9\\) and the cheapest item costs just 1.