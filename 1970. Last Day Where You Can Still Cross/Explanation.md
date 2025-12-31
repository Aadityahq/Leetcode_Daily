# Explanation: 1970. Last Day Where You Can Still Cross
## 🔍 Problem Intuition (What is really being asked?)

* You have a `row × col` grid.
* **Day 0**: everything is **land (0)**.
* Each day, **one cell becomes water (1)**.
* You can walk **only on land**, moving **up, down, left, right**.
* You may start from **any cell in the top row** and must reach **any cell in the bottom row**.

👉 You must find the **last day** when such a path **still exists**.

---

## 🧠 Key Insight (The trick)

Instead of:

* ❌ Simulating day-by-day flooding and checking paths (too slow),

We do:

* ✅ **Reverse the process**
* Start with **all water**
* **Add land back one cell at a time**, from the **last day to day 0**
* The moment **top connects to bottom**, that day is the answer

This is where **Union-Find (DSU)** shines.

---

## 🚀 Why Union-Find?

Union-Find efficiently answers:

> “Are these two regions connected?”

We use it to track:

* Connections between land cells
* Whether **any top-row land** is connected to **any bottom-row land**

---

## 🧩 Strategy Overview

### 1. Virtual Nodes

We add:

* `top` → represents **all top-row cells**
* `bottom` → represents **all bottom-row cells**

If `top` and `bottom` are connected → path exists.

---

### 2. Reverse Processing

We iterate **from the last day to the first**:

* Convert the flooded cell back into **land**
* Union it with:

  * Its land neighbors
  * `top` if it’s in the first row
  * `bottom` if it’s in the last row
* The **first day** when `top` and `bottom` connect → answer

---

## 🧪 Walkthrough with Your Code

### Data Structures

```java
int n = row * col;
int top = n, bottom = n + 1;

int[] parent = new int[n + 2];
int[] rank = new int[n + 2];
boolean[][] grid = new boolean[row][col];
```

* Each cell → unique ID: `r * col + c`
* Extra two nodes: `top` and `bottom`
* `grid[r][c] == true` means **land**

---

### Reverse Simulation Loop

```java
for (int d = n - 1; d >= 0; d--) {
```

We start from the **last flooded cell** and go backward.

---

### Restore Land

```java
grid[r][c] = true;
int id = r * col + c;
```

Cell becomes land again.

---

### Connect to Top / Bottom

```java
if (r == 0) union(id, top);
if (r == row - 1) union(id, bottom);
```

If land touches top or bottom row → union it.

---

### Connect with Neighbors

```java
if (grid[nr][nc]) {
    union(id, nr * col + nc);
}
```

Only union with **already restored land**.

---

### Check Connectivity

```java
if (find(top) == find(bottom)) return d;
```

💥 **Boom!**

* This is the **last day crossing is possible**
* Return `d`

---

## 🔧 Union-Find Implementation (Why it’s efficient)

### Path Compression

```java
private int find(int x) {
    if (parent[x] != x)
        parent[x] = find(parent[x]);
    return parent[x];
}
```

Keeps tree shallow → fast lookups.

---

### Union by Rank

```java
private void union(int a, int b) {
    if (rank[a] < rank[b]) parent[a] = b;
    else {
        parent[b] = a;
        if (rank[a] == rank[b]) rank[a]++;
    }
}
```

Avoids tall trees → nearly O(1) operations.

---

## ⏱️ Time & Space Complexity

| Metric | Complexity                     |
| ------ | ------------------------------ |
| Time   | **O(row × col × α(n)) ≈ O(n)** |
| Space  | **O(row × col)**               |

α(n) is inverse Ackermann — practically constant.

---

## 🧠 Why This Solution is Optimal

* Avoids BFS/DFS every day
* Uses reverse logic to detect **first valid connection**
* Union-Find handles **dynamic connectivity perfectly**
* Passes constraints up to **20,000 cells**

---

## ✅ Final Takeaway

* Think **reverse**
* Use **Union-Find** when connectivity changes dynamically
* Virtual nodes simplify boundary conditions
* This is a **classic LeetCode Hard → clean DSU solution**
