## 1️⃣ Problem Understanding (What is being asked?)

You are given:

* **n employees** arranged in a **tree hierarchy**

  * Employee `1` is the CEO (root).
  * Each employee has exactly one boss (except CEO).
* For each employee `i`:

  * `present[i]` → cost to buy stock today
  * `future[i]` → selling price tomorrow
* **Budget** → maximum money you can spend today
* **Discount rule**:

  * If a boss buys their stock, **direct subordinates** can buy at
    `floor(present[v] / 2)`

### Goal

👉 **Choose which employees’ stocks to buy** (each at most once)
👉 **Total buying cost ≤ budget**
👉 **Maximize total profit**

⚠️ Important constraints:

* You **cannot reuse future profit** to buy more stocks
* Buying a boss affects the price of **only direct children**

---

## 2️⃣ Why this is a Tree DP + Knapsack problem

### Observations

1. **Hierarchy is a tree**

   * Decisions for a boss affect its children
2. **Each employee has two states**

   * Bought or not bought
3. **Budget is limited**

   * Classic **knapsack constraint**
4. **Local decisions affect subtree**

   * Buying a node affects pricing of its children

👉 This naturally leads to:

> **Tree DP + Knapsack on each subtree**

---

## 3️⃣ DP State Definition (Very Important)

```java
dp[node][parentBought][budget]
```

### Meaning

* `node` → current employee
* `parentBought`:

  * `0` → parent did NOT buy stock
  * `1` → parent DID buy stock (discount applies)
* `budget` → money spent so far
* **Value** → maximum profit from the subtree rooted at `node`

📌 This state fully captures:

* Discount availability
* Budget usage
* Best profit possible

---

## 4️⃣ DFS Traversal (Why postorder?)

```java
dfs(0); // start from CEO
```

We use **postorder DFS** because:

* A manager’s decision depends on **children’s results**
* Children must be processed first

---

## 5️⃣ Two Choices at Every Employee (Core Logic)

For each employee `u` and `parentBought` state:

---

### ❌ Option 1: Skip buying this employee

* No cost
* Children **do not get discount**
* Merge children results using `dp[v][0]`

```java
int[] skip = new int[B + 1];
for (int v : tree[u]) {
    skip = merge(skip, dp[v][0]);
}
```

📌 Why `dp[v][0]`?

* Because if `u` doesn’t buy, child’s parent is considered **not bought**

---

### ✅ Option 2: Buy this employee

#### Step 1: Compute price & profit

```java
int price = parentBought == 1 ? present[u] / 2 : present[u];
int profit = future[u] - price;
```

📌 Discount applies only if parent bought

---

#### Step 2: Children get discount

```java
base = merge(base, dp[v][1]);
```

📌 Why `dp[v][1]`?

* Because buying `u` makes `u` the parent → children get discount

---

#### Step 3: Apply knapsack shift

```java
take[b] = base[b - price] + profit;
```

* Spend `price`
* Gain `profit`
* Remaining budget used by children

---

## 6️⃣ Choosing Best of Buy vs Skip

```java
dp[u][parentBought][b] = Math.max(skip[b], take[b]);
```

This ensures:

* For every budget
* We pick the **maximum profit possible**

---

## 7️⃣ Knapsack Merge Function (Why needed?)

```java
private int[] merge(int[] A, int[] B2)
```

### Purpose

* Combine results of **multiple children**
* Maintain budget constraint

### Why convolution?

* Each child independently consumes budget
* Total budget = sum of children budgets

This is classic **tree knapsack merging**.

---

## 8️⃣ Final Answer

```java
for (int b = 0; b <= B; b++) {
    ans = Math.max(ans, dp[0][0][b]);
}
```

📌 Why `dp[0][0]`?

* CEO has **no parent**
* So `parentBought = 0`

---

## 9️⃣ Time & Space Complexity (Interview-ready)

### Time Complexity

```
O(n × B²)
```

* `n ≤ 160`
* `B ≤ 160`
* Acceptable for constraints

### Space Complexity

```
O(n × B)
```

---

## 🔟 Why this solution is correct

✔ Handles hierarchical dependency
✔ Correctly applies discount logic
✔ Ensures budget is never exceeded
✔ Explores all valid buy/skip combinations
✔ Uses DP to avoid recomputation

---

## 1️⃣1️⃣ One-Line Interview Summary

> “This problem is solved using **tree DP with knapsack**, where each node decides whether to buy or skip the stock, and that decision affects children via discounts. We use DFS and budget-based DP to compute the maximum profit efficiently.”

---


