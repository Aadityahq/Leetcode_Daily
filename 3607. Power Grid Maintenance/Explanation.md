🧩 **3607. Power Grid Maintenance**

---

## 🧠 Problem Recap

We are given:

* `c` power stations (1-indexed).
* `connections` — bidirectional cables between them.
* `queries` — two types:

  * `[1, x]`: Maintenance check.

    * If `x` is online → handled by `x`.
    * If `x` is offline → handled by **smallest online station** in the same grid.
    * If none are online → return `-1`.
  * `[2, x]`: Station `x` goes offline.

Return an array of results for all `[1, x]` queries.

---

## ⚙️ Approach Overview

The graph’s structure never changes — only which nodes are **online/offline** does.

That gives us two key ideas:

1. **Static connectivity** → use **Union-Find (DSU)**
   to group stations into connected components (grids).

2. **Dynamic online tracking** → for each grid,
   maintain a **TreeSet** of *currently online* stations,
   so we can quickly get:

   * the **smallest online ID** (`set.first()`)
   * or remove a station when it goes offline.

---

## 🧩 Step-by-Step Breakdown

### 🔹 Step 1: Build the DSU (Disjoint Set Union)

We use DSU to merge all connected stations into one grid.

* `find(x)` → finds which grid station `x` belongs to.
* `union(u, v)` → merges two grids when there’s a cable between `u` and `v`.

This precomputes the connectivity in `O(c + n)` time.

---

### 🔹 Step 2: Initialize Online Stations per Grid

We maintain a map:

```
Map<Integer, TreeSet<Integer>> gridOnline
```

* **Key:** the grid representative (DSU root).
* **Value:** a sorted set (TreeSet) of all *currently online stations* in that grid.

Initially, all stations are online,
so we add every station `i` to the TreeSet of its grid.

---

### 🔹 Step 3: Process Queries

For each query `[type, x]`:

#### **Type 1 — Maintenance check**

1. If station `x` is online → it handles the request → `add(x)` to result.
2. If `x` is offline:

   * Find its component `comp = find(x)`.
   * Get the TreeSet for that component.
   * If empty → all stations offline → return `-1`.
   * Else → return the **first (smallest)** element.

#### **Type 2 — Station goes offline**

1. If station `x` is currently online:

   * Mark `offline[x] = true`.
   * Remove `x` from its component’s TreeSet.

---

### 🔹 Step 4: Return Results

We collect answers from all `[1, x]` queries in a list,
then convert it to an `int[]` before returning (LeetCode requires this).

---

## 🧮 Example Walkthrough

### Input:

```text
c = 5
connections = [[1,2],[2,3],[3,4],[4,5]]
queries = [[1,3],[2,1],[1,1],[2,2],[1,2]]
```

### Processing:

| Query | Explanation                                               | Output |
| ----- | --------------------------------------------------------- | ------ |
| [1,3] | 3 is online → handled by itself                           | 3      |
| [2,1] | 1 goes offline                                            | —      |
| [1,1] | 1 is offline → same grid = {2,3,4,5}, smallest online = 2 | 2      |
| [2,2] | 2 goes offline                                            | —      |
| [1,2] | 2 is offline → same grid = {3,4,5}, smallest online = 3   | 3      |

✅ Final Output: `[3, 2, 3]`

---

## ⏱️ Time & Space Complexity

| Operation      | Complexity           |
| -------------- | -------------------- |
| DSU setup      | O(c + n α(c))        |
| Query handling | O(q log c)           |
| **Overall**    | O((c + n + q) log c) |
| Space          | O(c + n)             |

* `α(c)` = inverse Ackermann function (≈ constant for all practical input sizes).
* `log c` from TreeSet operations (insertion/removal/min lookup).

---

## 💡 Why This Works

✅ DSU efficiently groups **static connectivity**.
✅ TreeSet keeps **dynamic online stations** in sorted order.
✅ Together, they allow:

* Fast `O(log n)` queries.
* Instant smallest-station lookup.
* Constant-time connectivity checks.

This combination of **Union-Find + TreeSet** gives the perfect balance between speed and flexibility for this problem. ⚡

---

