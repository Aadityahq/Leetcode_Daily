## 🧠 Problem Understanding

We’re given:

* `stations[i]` → number of power stations in city `i`.
* Each power station covers cities within distance `r`.
* We can build up to `k` new stations anywhere.

We need to **maximize the minimum power among all cities** after optimally placing the `k` new stations.

So we’re trying to find the **largest value `x`** such that *every city* can have power ≥ `x`.

---

## ⚙️ Core Idea: Binary Search + Sliding Window + Greedy

We can’t directly compute how to distribute stations (since `n` ≤ 1e5 and `k` ≤ 1e9).
So, we use **binary search on the answer** — on the possible value of the *minimum power*.

### Step 1: Binary Search Range

We know:

* The minimum power could be as low as `0`.
* The maximum possible power (if we put all `k` stations and count all existing ones) is roughly the total number of all stations + `k`.

Hence:

```java
long low = 0;
long high = sum(stations) + k;
```

We will binary search between `[low, high]`.

For each mid-value `mid`, we check:

> ❓ “Can we make every city have at least `mid` power with ≤ k new stations?”

That’s what the helper function `canAchieve()` does.

---

## 🔍 Step 2: The `canAchieve()` Function Explained

### Goal:

Check if it’s possible to make **all cities have ≥ target power** with at most `k` new stations.

---

### 🧩 Sliding Window Setup

We first need to know each city’s **current power**, which is the sum of all stations within radius `r`.

Instead of recomputing this sum for each city (which would be O(n*r)),
we use a **sliding window** technique — `windowSum` — that efficiently tracks the total power affecting each city.

* Start with power for city `0`: sum of stations `[0 ... r]`
* Then as we slide to city `i+1`,

  * Remove the contribution from city `i - r - 1`
  * Add contribution from city `i + r`

This keeps the complexity O(n).

---

### 🧮 Adding New Stations Greedily

Now, as we slide across each city `i`:

1. If `windowSum < target`, the city `i` doesn’t have enough power.
2. We calculate how many **extra stations** are needed:

   ```java
   long need = target - windowSum;
   ```
3. We “build” these `need` stations as close to the **rightmost end of their coverage**,
   i.e., at position `i + r` (or at `n - 1` if that’s out of bounds).

Why?
👉 Because placing them to the right helps not only the current city `i` but also future cities (due to overlapping coverage).
That’s the **greedy** part.

4. We update:

   * `used += need`
   * If `used > k`, then it’s impossible → return false.
   * Add them to an `add[]` array to track “new stations” added.
   * Increase `windowSum` by `need`.

---

### ✅ If We Survive the Loop

If we manage to make all cities ≥ target power using ≤ k stations,
we return **true**, meaning that target power is achievable.

---

## 🔁 Step 3: Binary Search Decision

In the main function:

```java
if (canAchieve(..., mid)) {
    ans = mid;        // possible, so record it
    low = mid + 1;    // try to find a higher min power
} else {
    high = mid - 1;   // not possible, lower the target
}
```

We keep doing this until we find the maximum achievable minimum power.

---

## 🧩 Example Walkthrough

Input:

```
stations = [1, 2, 4, 5, 0], r = 1, k = 2
```

Binary search tries possible “minimum powers”: 5, 6, 7, etc.

* For `mid = 5`, `canAchieve()` returns `true` (as explained in the example).
* For `mid = 6`, it returns `false`.
* So answer = 5.

---

## ⏱️ Time Complexity

* Binary search → O(log(maxPower))
* Each `canAchieve()` → O(n)
  ✅ **Overall:** O(n · log(sum(stations) + k)) — efficient for n ≤ 1e5.

---

## 💡 Summary — “How and Why”

| Step | What                             | Why                                         |
| ---- | -------------------------------- | ------------------------------------------- |
| 1    | Binary search on `min power`     | To find max possible minimum efficiently    |
| 2    | Sliding window                   | To compute coverage in O(n)                 |
| 3    | Greedy add new stations          | To fix low-power cities optimally           |
| 4    | Track additions using `add[]`    | To maintain effects of newly added stations |
| 5    | Check feasibility for each `mid` | Guides binary search direction              |

---

