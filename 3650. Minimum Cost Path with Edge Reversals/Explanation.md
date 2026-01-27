## 🧩 Problem Explanation (in simple words)

You are given:

* A **directed weighted graph**
* `n` nodes numbered `0` to `n-1`
* You start at node `0`, want to reach node `n-1`

### Special rule (the twist):

* Every node has a **switch**
* You can use a node’s switch **at most once**
* When you are at node `u`, you may:

  * Pick **one incoming edge** `v → u`
  * Temporarily reverse it to `u → v`
  * Immediately move through it
  * Cost = `2 × original weight`

Your goal is to find the **minimum cost path** from `0` to `n-1`.

---

## 🧠 High-Level Idea of the Solution

This is a **shortest path problem**, but with a catch:

> The ability to reverse an edge depends on whether you’ve already used a switch.

So reaching the same node can mean **different future possibilities**.

👉 Therefore, we use **Dijkstra’s algorithm with state tracking**.

---

## 🏗️ How the Graph is Built in This Code

```java
List<List<int[]>> out = new ArrayList<>();
List<List<int[]>> in = new ArrayList<>();
```

Two adjacency lists are created:

### 1️⃣ `out`

* Stores normal outgoing edges
* `out[u]` → all edges `u → v`

### 2️⃣ `in`

* Stores incoming edges
* `in[u]` → all edges `v → u`
* Needed so we can **reverse edges when using the switch**

---

## 📦 State Representation

```java
dist[node][used]
```

* `node` → current node
* `used` → whether the switch has been used

  * `0` = not used
  * `1` = used

This allows the algorithm to distinguish:

* reaching a node **with the switch still available**
* reaching a node **after consuming the switch**

---

## 🚦 Priority Queue (Dijkstra)

```java
PriorityQueue<long[]> pq
```

Each entry in the queue is:

```java
[cost, node, used]
```

The queue always expands the **minimum cost state first**.

---

## 🔁 Dijkstra Transitions Explained

### 1️⃣ Normal edges

```java
for (int[] edge : out.get(u)) {
    int v = edge[0];
    int w = edge[1];
    if (dist[v][0] > cost + w) {
        dist[v][0] = cost + w;
        pq.add(new long[]{dist[v][0], v, 0});
    }
}
```

**Meaning:**

* Move along `u → v` normally
* Cost increases by `w`
* Switch state should stay the same

👉 Conceptually correct idea:
Normal edges **don’t consume the switch**

---

### 2️⃣ Reversed edges (using switch)

```java
if (used == 0) {
    for (int[] edge : in.get(u)) {
        int v = edge[0];
        int w = edge[1];
        if (dist[v][0] > cost + 2L * w) {
            dist[v][0] = cost + 2L * w;
            pq.add(new long[]{dist[v][0], v, 0});
        }
    }
}
```

**Meaning:**

* If switch is unused:

  * Take any incoming edge `v → u`
  * Reverse it to `u → v`
  * Cost = `2 × w`
  * Switch is now consumed

---

## ❗ Important Issue in This Code (Very Important)

### 🚨 Bug / Logical Mistake

The code **always writes to `dist[v][0]`**, regardless of whether the switch is used or not.

```java
dist[v][0] = ...
pq.add(..., 0)
```

### ❌ What’s wrong?

* The switch usage state is **never preserved**
* `dist[][1]` is **never updated**
* This means:

  * The algorithm allows **multiple reversals**
  * Which violates the problem constraint

---

## ✅ What Should Happen Conceptually

| Move Type     | Next State  |
| ------------- | ----------- |
| Normal edge   | `(v, used)` |
| Reversed edge | `(v, 1)`    |

But in this code:

* Everything incorrectly goes to `(v, 0)`

---

## 🎯 Final Answer Calculation

```java
long ans = Math.min(dist[n - 1][0], dist[n - 1][1]);
```

Correct idea:

* Reach destination **with or without** using switch
* Take minimum

But since `dist[][1]` is never filled, this line becomes meaningless.

---

## 🧠 Why the Overall Approach Is Still Correct

Even though the implementation has a flaw, the **idea is 100% correct**:

* Use **Dijkstra**
* Track **extra state**
* Model edge reversal as a temporary move
* Use incoming edges for reversal

This is a **classic “state-augmented shortest path” problem**, and this pattern appears often in LeetCode Medium/Hard.

---



