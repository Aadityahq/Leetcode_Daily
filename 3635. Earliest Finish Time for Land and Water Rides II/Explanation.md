## Intuition

We need to take:

* Exactly **one land ride**
* Exactly **one water ride**

in **either order**.

A ride can be started at its opening time or any later time.

The key observation is that if we decide to take the **land ride first**, then the only thing that matters about the chosen land ride is **when it finishes**.

Suppose a land ride finishes at time `F`.

For any water ride `j`:

```text
Start water ride = max(F, waterStartTime[j])
Finish time      = max(F, waterStartTime[j]) + waterDuration[j]
```

To get the earliest possible answer, we would obviously like `F` to be as small as possible.

Therefore, when taking **land → water**, we only need the land ride with the **minimum finishing time**.

---

## Key Observation

For a land ride `i`:

```text
finishTime = landStartTime[i] + landDuration[i]
```

Let

```text
mini = minimum finish time among all land rides
```

Then for every water ride:

```text
finish = max(mini, waterStartTime[j]) + waterDuration[j]
```

The minimum of these values gives the best answer when taking:

```text
Land → Water
```

Similarly, we can swap the roles of land and water to compute:

```text
Water → Land
```

Finally:

```text
answer = min(landFirst, waterFirst)
```

---

## Understanding `calFinishTime()`

```java
private int calFinishTime(int[] ls, int[] ld,
                          int[] ws, int[] wd)
```

Here:

* `ls` = first ride start times
* `ld` = first ride durations
* `ws` = second ride start times
* `wd` = second ride durations

The function computes:

```text
Best possible finish time when
(first category) → (second category)
```

---

### Step 1: Find earliest finishing ride in first category

```java
int mini = Integer.MAX_VALUE;

for (int i = 0; i < ls.length; i++) {
    mini = Math.min(mini, ls[i] + ld[i]);
}
```

After this loop:

```text
mini = earliest time at which we can finish
       any ride from the first category
```

---

### Step 2: Try every ride in second category

```java
for (int i = 0; i < ws.length; i++) {
    int finish = Math.max(mini, ws[i]) + wd[i];
    ans = Math.min(ans, finish);
}
```

For ride `i`:

#### If it is already open

```text
ws[i] <= mini
```

we start immediately after finishing the first ride.

```text
finish = mini + wd[i]
```

#### If it opens later

```text
ws[i] > mini
```

we must wait.

```text
finish = ws[i] + wd[i]
```

Both cases are handled by:

```java
Math.max(mini, ws[i])
```

---

## Example

### Input

```java
landStartTime = [2, 8]
landDuration  = [4, 1]

waterStartTime = [6]
waterDuration  = [3]
```

### Land → Water

Land finish times:

```text
2 + 4 = 6
8 + 1 = 9
```

Earliest finish:

```text
mini = 6
```

Water ride:

```text
start = max(6, 6) = 6
finish = 6 + 3 = 9
```

So:

```text
landFirst = 9
```

---

### Water → Land

Water finish:

```text
6 + 3 = 9
```

Land rides:

```text
max(9,2)+4 = 13
max(9,8)+1 = 10
```

Best:

```text
waterFirst = 10
```

---

Final answer:

```text
min(9,10) = 9
```

---

## Why This Works

Let:

```text
F = finish time of the first ride
```

The finish time after taking a second ride is:

```text
max(F, secondRideStartTime) + secondRideDuration
```

This expression is **never smaller when F becomes larger**.

Therefore:

* Among all possible first rides,
* We only need the one with the **smallest finish time**.

Once we know that earliest finish time (`mini`), we can simply try every ride in the second category.

The same logic applies when reversing the order.

---

## Complexity Analysis

Let:

```text
n = number of land rides
m = number of water rides
```

Each call to `calFinishTime()`:

* One pass to find `mini`
* One pass to evaluate second-category rides

```text
O(n + m)
```

We call it twice:

```text
O(n + m)
```

### Space Complexity

```text
O(1)
```

No extra data structures are used.
