Since **n, m ≤ 100**, we can simply try **every possible pair** of land ride and water ride.

---

# Understanding the Problem

You must take:

* Exactly **one land ride**
* Exactly **one water ride**

You can choose the order:

1. Land → Water
2. Water → Land

A ride can start:

* At its opening time, or
* Any time after it opens

If you finish the first ride before the second ride opens, you must wait.

We need the **minimum possible finishing time**.

---

# Key Observation

Suppose we choose:

* Land ride `i`
* Water ride `j`

### Case 1: Land → Water

Land starts at:

```java
landStartTime[i]
```

Land finishes at:

```java
landStartTime[i] + landDuration[i]
```

Water can start only when:

* Land is finished
* Water ride is open

Therefore:

```java
waterStart = max(
    landFinish,
    waterStartTime[j]
);
```

Final finishing time:

```java
waterStart + waterDuration[j]
```

---

### Case 2: Water → Land

Water finishes at:

```java
waterStartTime[j] + waterDuration[j]
```

Land starts at:

```java
max(
    waterFinish,
    landStartTime[i]
)
```

Final finishing time:

```java
landStart + landDuration[i]
```

Take the minimum among all possibilities.

---

# Example

```text
landStartTime = [2,8]
landDuration  = [4,1]

waterStartTime = [6]
waterDuration  = [3]
```

Choose:

```text
land0 -> water0
```

Land finishes:

```text
2 + 4 = 6
```

Water starts:

```text
max(6, 6) = 6
```

Finish:

```text
6 + 3 = 9
```

Answer = **9**

---

# Algorithm

For every land ride:

For every water ride:

### Land → Water

```java
landFinish = landStartTime[i] + landDuration[i]

finish1 =
max(landFinish, waterStartTime[j])
+ waterDuration[j]
```

### Water → Land

```java
waterFinish = waterStartTime[j] + waterDuration[j]

finish2 =
max(waterFinish, landStartTime[i])
+ landDuration[i]
```

Update answer with:

```java
min(answer, finish1, finish2)
```

---

# Time Complexity

There are:

```text
n land rides
m water rides
```

Checking each pair:

```text
O(n × m)
```

With constraints:

```text
100 × 100 = 10,000
```

which is very small.

---

# Java Solution

```java
class Solution {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration,
                                  int[] waterStartTime, int[] waterDuration) {

        int n = landStartTime.length;
        int m = waterStartTime.length;

        int ans = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                // Land -> Water
                int landFinish = landStartTime[i] + landDuration[i];

                int finish1 =
                        Math.max(landFinish, waterStartTime[j])
                        + waterDuration[j];

                // Water -> Land
                int waterFinish = waterStartTime[j] + waterDuration[j];

                int finish2 =
                        Math.max(waterFinish, landStartTime[i])
                        + landDuration[i];

                ans = Math.min(ans, Math.min(finish1, finish2));
            }
        }

        return ans;
    }
}
```

### Why this works

For every possible `(land ride, water ride)` pair, we evaluate:

* Taking land first
* Taking water first

For each order, we correctly account for waiting using:

```java
Math.max(finishTimeOfFirstRide, openingTimeOfSecondRide)
```

Since all valid combinations are checked, the minimum finishing time found is guaranteed to be the earliest possible answer.
