# 3014. Minimum Number of Pushes to Type Word I

## Intuition

Since we are allowed to **remap the keypad however we want**, the actual letters don't matter. Only the **number of distinct letters** matters.

There are **8 keys** available (keys **2 to 9**).

To minimize the total pushes:

* Put the first **8 letters** in the **first position** of each key → **1 push each**
* Put the next **8 letters** in the **second position** → **2 pushes each**
* Put the next **8 letters** in the **third position** → **3 pushes each**
* Remaining letters (maximum 2) go to the **fourth position** → **4 pushes each**

Since every letter appears exactly once in the word, assigning letters this way always gives the minimum number of pushes.

---

# Approach

Let `n = word.length()`.

For every character:

* Characters `0` to `7` cost **1 push**
* Characters `8` to `15` cost **2 pushes**
* Characters `16` to `23` cost **3 pushes**
* Characters `24` to `25` cost **4 pushes**

Instead of checking ranges, we can compute the cost directly.

For the `i-th` assigned letter (0-indexed):

[
\text{cost} = \left(\frac{i}{8}\right) + 1
]

where integer division is used.

Add this cost for every letter.

---

# Dry Run

### Example

```
word = "xycdefghij"
```

Length = 10

Assign letters optimally:

| Letter | Position     | Pushes |
| ------ | ------------ | ------ |
| 1st    | 1st on a key | 1      |
| 2nd    | 1st on a key | 1      |
| 3rd    | 1st on a key | 1      |
| 4th    | 1st on a key | 1      |
| 5th    | 1st on a key | 1      |
| 6th    | 1st on a key | 1      |
| 7th    | 1st on a key | 1      |
| 8th    | 1st on a key | 1      |
| 9th    | 2nd on a key | 2      |
| 10th   | 2nd on a key | 2      |

Total

```
8 × 1 + 2 × 2
= 8 + 4
= 12
```

Answer = **12**

---

# Algorithm

1. Let `n = word.length()`.
2. Initialize `pushes = 0`.
3. For every index `i` from `0` to `n-1`:

   * Add `(i / 8) + 1` to the answer.
4. Return the answer.

---

# Correctness Proof

We prove that the algorithm always returns the minimum number of pushes.

* There are exactly **8 keys**, so at most **8 letters** can occupy the first position (cost = 1).
* After filling these positions, any additional letter must occupy at least the second position, costing **2 pushes**.
* Similarly, only **8 letters** can have cost **2**, then the next **8** letters must have cost **3**, and so on.
* Our algorithm assigns letters exactly in this order:

  * first 8 letters → cost 1
  * next 8 letters → cost 2
  * next 8 letters → cost 3
  * remaining → cost 4
* Since every cheaper position is filled before using a more expensive one, no other assignment can produce a smaller total number of pushes.

Hence, the algorithm always produces the minimum possible number of pushes.

---

# Complexity Analysis

* **Time Complexity:** `O(n)`
* **Space Complexity:** `O(1)`

where `n` is the length of the word.

---

# Java Solution

```java
class Solution {
    public int minimumPushes(String word) {
        int pushes = 0;

        for (int i = 0; i < word.length(); i++) {
            pushes += (i / 8) + 1;
        }

        return pushes;
    }
}
```

---

# Why does `(i / 8) + 1` work?

There are **8 keys**, so every group of **8 letters** has the same push count.

```
Indices: 0 1 2 3 4 5 6 7
i/8 = 0
Pushes = 1

Indices: 8 9 10 11 12 13 14 15
i/8 = 1
Pushes = 2

Indices: 16 17 18 19 20 21 22 23
i/8 = 2
Pushes = 3

Indices: 24 25
i/8 = 3
Pushes = 4
```

So,

```
pushes = (i / 8) + 1
```

automatically assigns the optimal push count for every letter.

---

## Key Insight

The important observation is that **the identities of the letters are irrelevant** because we are free to remap the keypad. The only thing that matters is **how many distinct letters** need to be placed. To minimize the total pushes, always fill all **8 one-push positions first**, then the **8 two-push positions**, then the **8 three-push positions**, and finally the remaining positions. This greedy assignment is optimal because every additional position on a key requires one more push.
