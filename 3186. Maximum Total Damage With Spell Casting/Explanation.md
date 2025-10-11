# 3186. Maximum Total Damage With Spell Casting

## Problem Statement

You are given an array `power`, where `power[i]` represents the damage of the `i`-th spell. You can cast any spell, and the total damage is the sum of the damages of the spells you cast. However, there's a constraint: if you cast a spell with damage `p`, you are forbidden from casting any spells with damage `p - 2`, `p - 1`, `p + 1`, or `p + 2`.

Your task is to find the maximum total damage you can achieve by casting a selection of spells that adheres to this rule.

## Why: The Dynamic Programming Approach

This problem asks for a maximum value given a series of choices with constraints, which is a classic indicator for **Dynamic Programming**.

At each step, when considering a certain spell damage value, we face a fundamental choice:
1.  **Take it:** Cast all spells of this damage value and gain points, but this restricts our future choices.
2.  **Skip it:** Don't cast spells of this damage value, which means we don't gain points from it, but we are free to choose other nearby spell values.

The optimal solution for the entire set of spells depends on the optimal solutions for smaller subsets. By building up from the smallest damage value, we can make the best decision at each step based on previously calculated optimal results. This structure of overlapping subproblems and optimal substructure makes DP a perfect fit.

## How: Step-by-Step Implementation

The core idea is to treat this as a variation of the "House Robber" problem. Instead of not being able to rob adjacent houses, we cannot "cast" spells with damage values within a distance of 2.

### 1. Preprocessing

First, we don't need to consider each spell individually. Spells with the same damage value are indistinguishable from a rules perspective. If we decide to use a spell of damage `p`, we should use all of them.

-   **Count Frequencies:** Use a hash map to count the occurrences of each unique damage value.
-   **Sort Unique Damages:** Extract the unique damage values from the map and sort them in ascending order. This allows us to process spells from lowest to highest damage, making the DP transitions logical.

### 2. Dynamic Programming State

Let `dp[i]` be the maximum total damage we can achieve by considering only the first `i` unique damage values (from our sorted list).

### 3. DP Transition (The Choice)

For each unique damage `d = unique_damages[i]`, we have two choices:

-   **Choice 1: Skip `d`**
    If we don't cast spells with damage `d`, our maximum damage is simply the best we could do with the previous unique damage values.
    `damage_if_skipped = dp[i-1]`

-   **Choice 2: Take `d`**
    If we cast all spells with damage `d`, we gain `d * frequency[d]` damage. However, we cannot have cast spells with damage `d-1` or `d-2`. We need to find the maximum damage from a "safe" point before this conflict. We look for the largest unique damage `d'` in our sorted list that is less than `d - 2`.
    -   We can use **binary search** on our sorted unique damages array to find the index `j` of the rightmost damage value that is less than `d - 2`.
    -   The damage from this choice is `(d * frequency[d]) + dp[j]`. If no such `j` exists, we just take `d * frequency[d]`.

### 4. The Recurrence Relation

The maximum damage `dp[i]` is the best of these two choices:
`dp[i] = max(damage_if_skipped, damage_if_taken)`

The final answer will be the last value in our `dp` array, `dp[n-1]`, where `n` is the number of unique damage values.

## Complexity

-   **Time Complexity:** `O(N + K log K)`, where `N` is the number of spells and `K` is the number of unique spells.
    -   `O(N)` to count frequencies.
    -   `O(K log K)` to sort the unique damage values.
    -   `O(K log K)` for the DP loop, as each of the `K` steps involves a binary search.
    This simplifies to `O(N + K log K)`.

-   **Space Complexity:** `O(K)` to store the frequencies, the sorted unique damages, and the `dp` array.

## Code Implementation (Java)

````java
// filepath: 3186. Maximum Total Damage With Spell Casting/Solution.java
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public long maximumTotalDamage(int[] power) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int p : power) {
            freq.put(p, freq.getOrDefault(p, 0) + 1);
        }

        List<Integer> uniqueDamages = new ArrayList<>(freq.keySet());
        Collections.sort(uniqueDamages);

        int n = uniqueDamages.size();
        long[] dp = new long[n];

        for (int i = 0; i < n; i++) {
            int currentDamage = uniqueDamages.get(i);
            long damageFromTaking = (long) currentDamage * freq.get(currentDamage);

            // Find the last valid index j such that uniqueDamages[j] < currentDamage - 2
            int j = -1;
            int low = 0, high = i - 1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (uniqueDamages.get(mid) < currentDamage - 2) {
                    j = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            if (j != -1) {
                damageFromTaking += dp[j];
            }

            long damageFromSkipping = (i > 0) ? dp[i - 1] : 0;

            dp[i] = Math.max(damageFromTaking, damageFromSkipping);
        }

        return dp[n - 1];
    }
}