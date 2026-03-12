# 1️⃣ What the Problem is Asking

We have:

* `n` nodes
* `edges[i] = [u, v, s, must]`

Where:

* `u, v` → edge between nodes
* `s` → strength
* `must = 1` → **mandatory edge**
* `must = 0` → **optional edge**

You can also:

* **Upgrade at most `k` optional edges**
* Upgrade means **strength becomes `2 × s`**

---

### Spanning Tree

A **spanning tree**:

* connects all nodes
* has exactly **n − 1 edges**
* **no cycles**

---

### Stability Definition

The **stability of the tree** =
👉 **minimum edge strength in the spanning tree**

Example:

Edges in tree: `2, 6`

Stability =

[
\min(2,6) = 2
]

---

### Goal

Maximize:

[
\text{minimum edge strength in spanning tree}
]

---

# 2️⃣ Key Insight

Instead of directly building the best tree, we **binary search the answer**.

Let:

```
x = minimum strength allowed in spanning tree
```

We ask:

> Is it possible to build a spanning tree where every edge strength ≥ x ?

If yes → try bigger `x`.

If no → reduce `x`.

---

# 3️⃣ Binary Search Range

Edge strengths:

```
1 ≤ s ≤ 100000
```

After upgrade:

```
max = 2 * 100000 = 200000
```

So we binary search:

```
1 → 200000
```

---

# 4️⃣ DSU (Union Find)

DSU helps us:

* connect nodes
* detect cycles
* track number of components

Important property:

```
Spanning tree exists if components == 1
```

---

# 5️⃣ Step 1 — Mandatory Edge Check

Before binary search we check:

```
if mandatory edges form a cycle → impossible
```

Because **spanning tree cannot contain cycles**.

Code:

```java
for (int[] e : edges) {
    if (e[3] == 1) {
        if (!dsu.unite(e[0], e[1])) {
            return -1;
        }
    }
}
```

---

# 6️⃣ Main Idea of `canAchieve(x)`

We check if **minimum edge ≥ x** can be achieved.

Steps:

```
1. Add mandatory edges
2. Add optional edges already ≥ x
3. Upgrade edges if needed
```

---

# 7️⃣ Step 1 — Mandatory Edges

Mandatory edges **must be included**.

If their strength `< x` → impossible.

```java
if (must == 1) {
    if (s < x) return false;
    if (!dsu.unite(u, v)) return false;
}
```

Also detect cycle.

---

# 8️⃣ Step 2 — Use Optional Edges Without Upgrade

If edge already satisfies:

```
s ≥ x
```

We use it freely.

```java
if (must == 0 && s >= x) {
    dsu.unite(u, v);
}
```

---

# 9️⃣ Step 3 — Use Upgrades

For edges:

```
s < x
```

But upgrade possible:

```
2*s ≥ x
```

Then we can upgrade.

```java
if (must == 0 && s < x && 2 * s >= x) {
    if (dsu.unite(u, v)) {
        usedUpgrades++;
    }
}
```

But:

```
usedUpgrades ≤ k
```

---

# 🔟 Final Check

If after all edges:

```
components == 1
```

Graph is connected.

Return:

```
true
```

---

# 1️⃣1️⃣ Binary Search Logic

```java
while (low <= high) {
    mid = (low + high)/2

    if (canAchieve(mid))
        ans = mid
        low = mid + 1
    else
        high = mid - 1
}
```

We maximize `x`.

---

# 1️⃣2️⃣ Example Walkthrough

Example:

```
n = 3
edges = [[0,1,4,0],[1,2,3,0],[0,2,1,0]]
k = 2
```

Possible upgrades:

```
4 → 8
3 → 6
```

Spanning tree:

```
0-1 (8)
1-2 (6)
```

Stability:

```
min(8,6) = 6
```

Answer = **6**

---

# 1️⃣3️⃣ Time Complexity

Binary Search:

```
log(200000) ≈ 18
```

Each check:

```
O(E α(N))
```

Total:

```
O(E log S)
```

Where

```
E = edges
S = max strength
```

Works for:

```
10^5 edges
```

---

# 1️⃣4️⃣ Why This Works

We turn a **max-min optimization problem** into a **decision problem**:

Instead of:

```
maximize min edge
```

We ask:

```
Can we achieve min edge ≥ x ?
```

Binary search finds the **largest valid x**.

---

# 1️⃣5️⃣ Key Techniques Used

This problem combines:

| Concept       | Purpose                      |
| ------------- | ---------------------------- |
| Binary Search | maximize minimum value       |
| Union Find    | build spanning tree          |
| Greedy        | choose strongest edges first |
| Graph Theory  | spanning tree constraints    |

---

✅ **In short**

1. Binary search the stability value
2. Use DSU to check connectivity
3. Use optional edges first
4. Upgrade edges if needed
5. Ensure upgrades ≤ k

---

