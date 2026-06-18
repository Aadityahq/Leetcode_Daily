## Understanding the Problem

We need to find the **smaller angle** between the **hour hand** and the **minute hand** of an analog clock for a given time.

For example:

* **3:00** → the hour hand is at 3, the minute hand is at 12 → angle = **90°**
* **3:30** → the hour hand has moved halfway toward 4 → angle = **75°**, not 90°.

The important detail is:

> The hour hand moves continuously, not just once every hour.

---

## Key Observations

### Minute Hand Movement

A clock has **360°** and **60 minutes**.

So the minute hand moves:

[
\frac{360}{60} = 6^\circ
]

per minute.

Therefore:

[
\text{minuteAngle} = minutes \times 6
]

---

### Hour Hand Movement

A clock has **360°** and **12 hours**.

So the hour hand moves:

[
\frac{360}{12} = 30^\circ
]

per hour.

But it also moves gradually as minutes pass.

Since it moves 30° in 60 minutes:

[
\frac{30}{60} = 0.5^\circ
]

per minute.

Therefore:

[
\text{hourAngle} = (hour \bmod 12) \times 30 + minutes \times 0.5
]

We use `hour % 12` because `12` should be treated as `0`.

---

## Find the Smaller Angle

First, compute the absolute difference:

[
diff = |\text{hourAngle} - \text{minuteAngle}|
]

There are always two angles between the hands:

* `diff`
* `360 - diff`

We need the smaller one:

[
answer = \min(diff, 360 - diff)
]

---

## Example Walkthrough

### Example: hour = 3, minutes = 15

Minute hand:

[
15 \times 6 = 90^\circ
]

Hour hand:

[
3 \times 30 + 15 \times 0.5
]

[
= 90 + 7.5
]

[
= 97.5^\circ
]

Difference:

[
|97.5 - 90| = 7.5^\circ
]

Smaller angle:

[
\min(7.5, 352.5) = 7.5^\circ
]

Answer: **7.5**

---

## Formula Summary

---

## Algorithm

1. Calculate the minute hand angle.
2. Calculate the hour hand angle.
3. Find their absolute difference.
4. Return the smaller of:

   * `difference`
   * `360 - difference`

---

## Complexity Analysis

* **Time Complexity:** `O(1)`
* **Space Complexity:** `O(1)`

Only a few arithmetic operations are performed.

---

## Java Solution

```java
class Solution {
    public double angleClock(int hour, int minutes) {

        double minuteAngle = minutes * 6.0;

        double hourAngle = (hour % 12) * 30.0 + minutes * 0.5;

        double diff = Math.abs(hourAngle - minuteAngle);

        return Math.min(diff, 360.0 - diff);
    }
}
```

---

## Why This Works

* The minute hand moves at a constant speed of **6° per minute**.
* The hour hand moves at a constant speed of **0.5° per minute**.
* Computing each hand's exact position gives the true angle at that moment.
* Taking the minimum of the two possible angles ensures we return the **smaller angle**, as required by the problem.
