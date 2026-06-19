# 1732. Find the Highest Altitude

## Problem Explanation

Imagine a biker traveling through different points on a road trip.

* The biker starts at **point 0** with an altitude of **0**.
* The `gain` array tells us how the altitude changes between consecutive points.

  * `gain[i]` represents the change in altitude from point `i` to point `i + 1`.

Your task is to find the **highest altitude** the biker reaches during the trip.

### Example

```text
gain = [-5, 1, 5, 0, -7]
```

Starting altitude:

```text
Point 0 → 0
```

Calculate each next altitude:

```text
Point 1 → 0 + (-5) = -5
Point 2 → -5 + 1 = -4
Point 3 → -4 + 5 = 1
Point 4 → 1 + 0 = 1
Point 5 → 1 + (-7) = -6
```

Altitudes:

```text
[0, -5, -4, 1, 1, -6]
```

The highest altitude is:

```text
1
```

---

## Key Observation

We do **not** need to store all altitudes.

We only need:

1. The **current altitude**.
2. The **maximum altitude** seen so far.

Since each altitude depends only on the previous altitude, we can compute it on the fly.

---

## Approach

1. Initialize:

   * `currentAltitude = 0`
   * `maxAltitude = 0`

2. Traverse the `gain` array:

   * Add the current gain to `currentAltitude`.
   * Update `maxAltitude` if `currentAltitude` is greater.

3. Return `maxAltitude`.

---

## Why Does This Work?

At every step:

```text
currentAltitude = altitude at the current point
```

By comparing `currentAltitude` with `maxAltitude`, we ensure that we never miss the highest point reached during the journey.

Since we process every gain exactly once, the solution is efficient.

---

## Dry Run

### Input

```text
gain = [-5, 1, 5, 0, -7]
```

| Gain  | Current Altitude | Maximum Altitude |
| ----- | ---------------- | ---------------- |
| Start | 0                | 0                |
| -5    | -5               | 0                |
| 1     | -4               | 0                |
| 5     | 1                | 1                |
| 0     | 1                | 1                |
| -7    | -6               | 1                |

Answer:

```text
1
```

---

## Java Solution

```java
class Solution {
    public int largestAltitude(int[] gain) {
        int currentAltitude = 0;
        int maxAltitude = 0;

        for (int g : gain) {
            currentAltitude += g;
            maxAltitude = Math.max(maxAltitude, currentAltitude);
        }

        return maxAltitude;
    }
}
```

---

## Complexity Analysis

* **Time Complexity:** `O(n)`

  * We traverse the `gain` array once.

* **Space Complexity:** `O(1)`

  * We use only two extra variables.

---

## Intuition to Remember

This is a **running sum (prefix sum)** problem.

The altitude at any point is simply:

```text
Altitude = Sum of all gains up to that point
```

Instead of storing all prefix sums, we maintain:

* the current prefix sum (`currentAltitude`)
* the maximum prefix sum (`maxAltitude`)

This pattern appears frequently in array problems involving cumulative values.
