# LeetCode 3789. Minimum Cost to Acquire Required Items

Medium

Greedy

Math

## 1. Understanding the problem

We are given five integers:

```
cost1, cost2, costBoth, need1, need2
```

We have three types of items available.

| Item type | Cost       | Contribution           |
| --------- | ---------- | ---------------------- |
| Type 1    | `cost1`    | Only type 1            |
| Type 2    | `cost2`    | Only type 2            |
| Type 3    | `costBoth` | Both type 1 and type 2 |

Our goal is to satisfy both requirements while spending the minimum amount of money.

Important: We can buy more items than required. We only need to make sure that both requirements are satisfied.

## 2. Think about the possible choices

There are two main strategies.

Strategy 1: Buy items separately

- Buy `need1` type 1 items.
- Buy `need2` type 2 items.

Total cost:

\\[ cost = need1 \times cost1 + need2 \times cost2 \\]

Strategy 2: Buy only type 3 items

Each type 3 item contributes one unit to both requirements.

We can buy:

\\[ \max(need1, need2) \\]

type 3 items.

Total cost:

\\[ cost = \max(need1,need2)\times costBoth \\]

But there is another possibility: use type 3 items for the common requirements and individual items for the remaining requirements.

So, we need to compare the costs.

## 3. Greedy approach

The key observation is:

For every common unit of requirement, we have two choices:

1. Buy one type 3 item.
2. Buy one type 1 item AND one type 2 item.

Therefore, compare:

\\[ costBoth \\]

with:

\\[ cost1+cost2 \\]

There are two cases.

Case 1: `costBoth < cost1 + cost2`

Buying a type 3 item is cheaper than buying one type 1 and one type 2 item.

So, we should use type 3 items for the common requirements.

Number of common requirements:

\\[ common = \min(need1,need2) \\]

After that, one requirement might still be left.

Case 2: `costBoth >= cost1 + cost2`

Buying separate items is cheaper or has the same cost.

Therefore, buying type 1 and type 2 items separately is sufficient.

However, there is an even simpler way to implement both cases.

## 4. Java solution (Greedy)

```

class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth,
                            int need1, int need2) {

        long option1 = (long) need1 * cost1
                     + (long) need2 * cost2;

        long option2 = (long) Math.max(need1, need2) * costBoth;

        long common = Math.min(need1, need2);

        long option3 = common * costBoth
                     + (long) (need1 - common) * cost1
                     + (long) (need2 - common) * cost2;

        return Math.min(option1, Math.min(option2, option3));
    }
}

```

## 5. Dry run with Example 1

Input:

```
cost1 = 3
cost2 = 2
costBoth = 1
need1 = 3
need2 = 2
```

Let's calculate all three options.

Option 1: Buy separately

\\[ 3\times3 + 2\times2 = 13 \\]

Option 2: Buy only type 3

\\[ \max(3,2)\times1 = 3 \\]

Option 3: Buy common requirements using type 3

Common requirements:

\\[ \min(3,2)=2 \\]

We buy:

- 2 type 3 items = 2
- 1 type 1 item = 3

Total:

\\[ 2\times1 + 1\times3 = 5 \\]

Compare all options

| Strategy | Total cost |
| -------- | ---------- |
| Option 1 | 13         |
| Option 2 | 3          |
| Option 3 | 5          |

Minimum cost

# 3

Answer = `3`.

## 6. Dry run with Example 2

Input:

```
cost1 = 5
cost2 = 4
costBoth = 15
need1 = 2
need2 = 3
```

Calculate:

- Option 1 = (2 × 5) + (3 × 4) = 22
- Option 2 = 3 × 15 = 45
- Option 3 = (2 × 15) + (1 × 4) = 34

Minimum = `22`.

Answer = `22`.

## 7. Complexity analysis

| Complexity | Value      | Reason                           |
| ---------- | ---------- | -------------------------------- |
| Time       | \\(O(1)\\) | Only a few arithmetic operations |
| Space      | \\(O(1)\\) | Only a few variables             |

## 8. Important interview concepts

Remember these points:

1. Greedy: Compare the cost of buying a combined item against buying two separate items.
2. Math: Use `min()` and `max()` to calculate common and total requirements.
3. Overflow: Use `long` because `need1` and `need2` can be as large as \\(10^9\\).

Why use `long`?

For example:

\\[ 10^9 \times 10^6 = 10^{15} \\]

Java's `int` cannot store this value. Therefore, we must use `long`.

## 9. Test your understanding

Question: Suppose:

```
cost1 = 6
cost2 = 7
costBoth = 10
need1 = 4
need2 = 2
```

What is the minimum cost?

26

34

40

38

Check answer



Final takeaway: The most important idea in this problem is to compare the cost of a combined item with the cost of buying both individual items. Also consider buying extra combined items when that reduces the overall cost.