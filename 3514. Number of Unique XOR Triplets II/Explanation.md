## Intuition

We need all **different values** of:

[
nums[i] \oplus nums[j] \oplus nums[k]
]

where

[
i \le j \le k
]

Notice that **the same index can be used multiple times** because `i`, `j`, and `k` are allowed to be equal.

For example:

```
i = j = k
```

is valid.

Since XOR only depends on the **values**, we can think of the problem as:

> Pick **3 numbers** from `nums` **with replacement** and find every possible XOR result.

We only need the **count of distinct XOR values**, not the triplets themselves.

---

# Key Observation

```
nums[i] XOR nums[i] = 0
```

and

```
0 XOR x = x
```

Examples:

```
5 ^ 5 ^ 7 = 7
```

```
3 ^ 3 ^ 3 = 3
```

---

# Why Dynamic Programming?

The constraints are

```
n = 1500
nums[i] <= 1500
```

A brute force solution would try

```
1500³
```

triplets

which is

```
3.375 billion
```

operations.

This is far too slow.

---

# Important Observation

The maximum number is

```
1500
```

which fits inside **11 bits**.

```
2^11 = 2048
```

Therefore every XOR result is between

```
0 ... 2047
```

Only **2048 possible XOR values exist!**

This is the key.

---

# DP Idea

Let

```
dp[t][x]
```

mean:

> Is XOR value `x` possible after choosing exactly `t` numbers?

Initially

```
0 numbers chosen

XOR = 0
```

So

```
dp[0][0] = true
```

Now choose numbers one by one.

For every number

```
v
```

and every existing XOR

```
x
```

we can create

```
x ^ v
```

---

### Transition

```
dp[t+1][x ^ v] = true
```

---

After choosing 3 numbers,

```
dp[3]
```

contains every possible XOR triplet value.

Count the `true` entries.

---

# Example

```
nums = [1,3]
```

Start

```
dp0

{0}
```

---

Choose first number

```
0^1 = 1
0^3 = 3

dp1 = {1,3}
```

---

Choose second

From 1

```
1^1=0
1^3=2
```

From 3

```
3^1=2
3^3=0
```

```
dp2 = {0,2}
```

---

Choose third

From 0

```
0^1=1
0^3=3
```

From 2

```
2^1=3
2^3=1
```

```
dp3 = {1,3}
```

Answer

```
2
```

---

# Correctness Proof

We prove that the DP computes exactly all possible XOR triplet values.

### Base Case

Before choosing any numbers,

```
XOR = 0
```

So

```
dp[0][0] = true
```

is correct.

---

### Induction

Assume

```
dp[t]
```

contains exactly every XOR obtainable after choosing `t` numbers.

When adding another value `v`,

new XOR becomes

```
oldXor ^ v
```

Therefore

```
dp[t+1][oldXor ^ v]
```

is marked.

Every choice of the next number is considered.

Hence every XOR using `t+1` numbers is generated.

No impossible XOR is inserted.

Thus the invariant remains true.

---

After 3 selections,

```
dp[3]
```

contains exactly every XOR triplet value.

Therefore counting all `true` entries gives the answer.

---

# Complexity

There are

```
2048
```

possible XOR states.

For each of the 3 picks,

we iterate over

* every array element (`n`)
* every XOR state (`2048`)

Time:

[
O(3 \times n \times 2048)
= O(n \times 2048)
]

For

```
n = 1500
```

this is about **9 million operations**, which is very fast.

Space:

```
4 × 2048
```

or simply

[
O(2048)
]

---

# Java Solution

```java
class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int pick = 0; pick < 3; pick++) {
            for (int xor = 0; xor < MAX; xor++) {
                if (!dp[pick][xor]) continue;

                for (int num : nums) {
                    dp[pick + 1][xor ^ num] = true;
                }
            }
        }

        int ans = 0;
        for (boolean possible : dp[3]) {
            if (possible) ans++;
        }

        return ans;
    }
}
```

### Why this works

* `dp[0][0]` → no numbers chosen.
* Each iteration chooses one more element from `nums`.
* Reusing the same value is naturally allowed because every pick loops over the entire array again.
* After exactly **3 picks**, `dp[3]` stores all distinct XOR values obtainable from valid triplets.
* Counting the `true` states gives the number of unique XOR triplet values.
