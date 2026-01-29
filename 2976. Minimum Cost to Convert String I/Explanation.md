## 🔍 Problem Understanding (in simple words)

You are given:

* **source** string → where you start
* **target** string → where you want to reach
* Some **allowed character conversions**:

  * `original[i] → changed[i]` with cost `cost[i]`

You can:

* Change **any character** in the string
* Use **multiple steps** (e.g. `a → c → b`)
* Apply operations **any number of times**

🎯 **Goal:**
Convert `source` into `target` with **minimum total cost**.
If it’s impossible, return `-1`.

---

## 🧠 Key Insight

Each character conversion is **independent** of others.

So for every position `i`:

* Convert `source[i]` → `target[i]`
* Pay the **minimum cost** to do that conversion

👉 This becomes a **shortest path problem between characters** (`a` to `z`).

---

## 🗺️ Graph Interpretation

* Think of **each character (`a`–`z`) as a node**
* A rule like `a → b (cost 2)` is a **directed edge**
* We want the **cheapest path** between any two characters

Since there are only **26 characters**, we can compute **all-pairs shortest paths**.

✔️ Best algorithm here: **Floyd–Warshall**

---

## 🛠️ Solution Strategy

### 1️⃣ Create a distance matrix

```java
long[][] dist = new long[26][26];
```

* `dist[i][j]` = minimum cost to convert character `i` → `j`
* Initialize:

  * `dist[i][i] = 0`
  * All others = `infinity`

---

### 2️⃣ Fill direct conversions

```java
dist[u][v] = Math.min(dist[u][v], cost[i]);
```

Why `Math.min`?
Because there may be **multiple rules** for the same conversion.

---

### 3️⃣ Run Floyd–Warshall

```java
for (int k = 0; k < 26; k++)
    for (int i = 0; i < 26; i++)
        for (int j = 0; j < 26; j++)
            dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
```

📌 This finds:

* Cheapest way to convert **any character to any other**
* Even using **intermediate characters**

Example:

```
a → c → b
```

---

### 4️⃣ Calculate total cost

```java
for each index i:
    totalCost += dist[source[i]][target[i]]
```

❌ If any `dist[source[i]][target[i]]` is unreachable → return `-1`

---

## ✅ Why This Works Efficiently

* Floyd–Warshall runs in `26³ = 17576` operations → **very fast**
* String length can be `10⁵`, but per character lookup is `O(1)`
* Total complexity: **O(26³ + n)** ✔️

---

## 🧪 Example Walkthrough (Example 2)

```
source = "aaaa"
target = "bbbb"

Rules:
a → c (1)
c → b (2)
```

* Cheapest `a → b` = `1 + 2 = 3`
* 4 characters → `3 × 4 = 12`

✔️ Output = **12**

---

## 🧩 Final Summary

* Model characters as graph nodes
* Use **Floyd–Warshall** to find cheapest conversions
* Add up costs for each character position
* If any conversion is impossible → `-1`

This is a **classic graph + DP hybrid problem** 🔥
Once you spot the graph idea, everything clicks.

