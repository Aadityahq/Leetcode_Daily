# 🔐 2092. Find All People With Secret

## Explanation of the Given Solution (How & Why)

---

## 🧠 Core Idea (WHY this approach works)

* The secret spreads **only through meetings**
* Meetings happen at **specific times**
* At the **same time**, the secret can spread **instantly through a chain of people**
* But connections **must not carry over to future times** unless someone already knows the secret

👉 So we:

* Process meetings **time by time**
* Use **Union-Find (DSU)** to connect people meeting at the same time
* **Reset connections** for people who did not learn the secret

---

## 🧩 Data Structures Used (WHAT & WHY)

### 1. `timeArray`

```java
List<int[]>[] timeArray
```

* Groups meetings by their **time**
* `timeArray[t]` contains all meetings happening at time `t`
* This allows us to process meetings **chronologically**

✅ Why?
Because the secret cannot travel backward or forward in time incorrectly.

---

### 2. Union-Find (DSU)

```java
parent[]
```

* Each person starts as their **own parent**
* Union connects people who meet
* Find checks if someone is connected to **person 0** (the original secret holder)

✅ Why DSU?
Because:

* People can receive and share the secret **instantly within the same time**
* DSU efficiently connects chains like `1 → 2 → 3` in the same timestamp

---

## 🔁 Step-by-Step Execution (HOW)

---

### ✅ Step 1: Group meetings by time

```java
for (int[] meet : meetings) {
    timeArray[meet[2]].add(new int[]{meet[0], meet[1]});
}
```

📌 Meaning:

* All meetings at the same time are grouped together
* This ensures instant secret sharing within the same time

---

### ✅ Step 2: Process each time independently

```java
for (int i = 1; i < timeArray.length; i++) {
```

We process times **in increasing order**.

---

### ✅ Step 3: Union all meetings at the same time

```java
union(u, v, parent);
```

📌 Meaning:

* Everyone meeting at this time becomes connected
* This allows secret spread **within the same time**

Example:

```
Time = 1
1 — 2 — 3
```

All become one connected component.

---

### ✅ Step 4: Reset connections that are not linked to person 0

```java
if (find(u, parent) != 0) parent[u] = u;
```

📌 Meaning:

* After processing this time:

  * If a person is **not connected to 0**, they did NOT learn the secret
  * Their connection is reset
* Only people connected to `0` keep their links

✅ Why reset?
Because:

* Meetings at time `t` should not affect meetings at `t+1`
* Prevents **false secret spreading**

---

### ✅ Step 5: Collect final answer

```java
if (parent[i] == 0)
```

📌 Meaning:

* Anyone whose parent is `0` knows the secret
* Add them to the result list

---

## 🧪 Example Walkthrough

### Input

```text
n = 4
meetings = [[3,1,3],[1,2,2],[0,3,3]]
firstPerson = 3
```

### Execution

* Time 0 → `0` shares secret with `3`
* Time 2 → `1` and `2` meet (no secret → reset)
* Time 3 → `0`, `1`, `3` get connected → secret spreads

### Output

```text
[0, 1, 3]
```

---

## ⏱️ Time & Space Complexity

### Time

* Grouping meetings: `O(m)`
* DSU operations: `O(m α(n))`
* Overall: **O(m log m)** (due to time grouping)

### Space

* Parent array + time buckets: **O(n + m)**

---

## 🎯 Final Interview Summary

> “We group meetings by time and process them sequentially.
> At each time, we union all meeting participants so the secret can spread instantly.
> Afterward, we reset any connections not linked to person 0 to prevent future leakage.
> This ensures correct, time-based secret propagation.”

---

