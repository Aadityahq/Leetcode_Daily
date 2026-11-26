# **LeetCode 3360 – Stone Removal Game**



Easy

Java



## **1. Understanding the Problem**

Alice and Bob are playing a stone-removal game.

The rules are:

1. Alice plays first and must remove exactly **10 stones**.
2. Bob must remove exactly **9 stones**.
3. Alice must remove exactly **8 stones**.
4. Bob must remove exactly **7 stones**.
5. This continues, with each player removing one fewer stone than the previous turn.
6. If a player cannot remove the required number of stones, that player loses.

Our task is to return:

- `true` if Alice wins.
- `false` if Bob wins.

## **2. Java Solution – Simulation**

```

class Solution {
    public boolean canAliceWin(int n) {
        int stones = 10;
        boolean aliceTurn = true;

        while (n >= stones) {
            n -= stones;
            stones--;

            aliceTurn = !aliceTurn;
        }

        return !aliceTurn;
    }
}

```

## **3. Explanation – How and Why?**

Let's understand the code step by step.

**Step 1: Initialize variables**

```
int stones = 10;
boolean aliceTurn = true;
```

- `stones = 10`: Alice must remove 10 stones first.
- `aliceTurn = true`: Alice plays first.

**Step 2: Check whether the player can make a move**

```
while (n >= stones)
```

This checks whether enough stones are available.

If the remaining stones are less than the required stones, the current player loses.

**Step 3: Remove stones**

```
n -= stones;
```

Subtract the required stones from the pile.

**Step 4: Decrease the required stones**

```
stones--;
```

Every next player must remove one fewer stone.

For example:

- Alice: 10
- Bob: 9
- Alice: 8
- Bob: 7
- Alice: 6

**Step 5: Change the player's turn**

```
aliceTurn = !aliceTurn;
```

This switches between Alice and Bob.

**Step 6: Return the winner**

```
return !aliceTurn;
```

When the loop stops, the current player cannot make a move.

- If it is Alice's turn, Alice loses.
- If it is Bob's turn, Alice wins.

Therefore, `!aliceTurn` gives the correct answer.

## **4. Dry Run**

Let's take the first example.

**Example 1: n = 12**

| Turn | Player | Removes         | Remaining |
| ---- | ------ | --------------- | --------- |
| 1    | Alice  | 10              | 2         |
| 2    | Bob    | Cannot remove 9 | 2         |

Alice Wins – true

Alice wins because Bob cannot make his move.

**Example 2: n = 1**

| Turn | Player | Removes          | Remaining |
| ---- | ------ | ---------------- | --------- |
| 1    | Alice  | Cannot remove 10 | 1         |

Alice Loses – false

Alice cannot make her first move.

## **5. Another Dry Run**

Let's take `n = 25`.

| Turn | Player | Required stones | Remaining stones |
| ---- | ------ | --------------- | ---------------- |
| 1    | Alice  | 10              | 15               |
| 2    | Bob    | 9               | 6                |
| 3    | Alice  | 8               | 6 (cannot move)  |

Alice loses – false

Bob wins because Alice cannot remove 8 stones.

## **6. Complexity Analysis**

Let `n` be the initial number of stones.

\- **Time Complexity:** \\(O(1)\\)

At most 10 iterations are possible because we start at 10 and decrease the required stones each turn.

\- **Space Complexity:** \\(O(1)\\)

We use only two variables, regardless of the input size.

## **7. Important Concept to Remember**

This problem uses **Simulation**.

Simulation means following the problem's rules step by step in code.

The important things to identify are:

1. What is the starting condition?
2. What changes after every turn?
3. When does the loop stop?
4. Who loses when the loop stops?

**Interview tip:** Whenever a problem describes turns, repeated actions, or changing values, think about simulation first. It is often the simplest approach.