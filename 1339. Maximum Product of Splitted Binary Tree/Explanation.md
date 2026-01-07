## 🔍 Problem Understanding (WHAT)

You are given a **binary tree**.

You are allowed to:

* **remove exactly one edge** from the tree

This splits the tree into **two smaller trees**.

For each tree:

* calculate the **sum of all node values**

Your goal:

* **maximize the product** of these two sums

Finally:

* return the result modulo **10⁹ + 7**
* but **maximize first**, then apply modulo

---

### 📌 Important Observations

* You **must remove one edge**
* Every removed edge creates:

  * one **subtree**
  * the **rest of the tree**
* Node values are **positive**, so:

  * larger, balanced splits give a **larger product**

---

## 🧠 Key Insight (WHY)

Let’s say:

* `totalSum` = sum of all nodes in the tree
* You cut an edge and get:

  * subtree sum = `x`
  * remaining tree sum = `totalSum - x`

👉 The product becomes:

```
x × (totalSum - x)
```

### 🔑 Math Insight

This product is **maximum** when `x` is **as close as possible to totalSum / 2**.

So the problem becomes:

> “Among all possible subtrees, find the one whose sum is closest to half of the total sum.”

---

## 🛠️ Strategy (HOW)

We use **Depth First Search (DFS)** because:

* Trees are naturally recursive
* We can compute subtree sums efficiently

### Step 1️⃣ — Find Total Sum

We traverse the entire tree once to calculate:

```
totalSum = sum of all node values
```

This is required because every possible split depends on this value.

---

### Step 2️⃣ — Try Every Possible Split

We traverse the tree **again**.

At each node:

* calculate the sum of the subtree rooted at that node
* imagine cutting the edge above this node
* compute the product:

```
subtreeSum × (totalSum - subtreeSum)
```

* keep updating the **maximum product**

This ensures:

* every valid edge removal is considered
* we never miss the optimal split

---

## 🧩 Why Two DFS Passes?

| DFS        | Purpose                | Why needed                   |
| ---------- | ---------------------- | ---------------------------- |
| First DFS  | Compute total sum      | Required for product formula |
| Second DFS | Try all subtree splits | Needed to evaluate all cuts  |

Trying to do both in one pass is possible but less clear and error-prone.

---

## 🧑‍💻 Code Walkthrough (LINE BY LINE)

```java
class Solution {
    private long totalSum = 0;
    private long maxProduct = 0;
    private static final int MOD = 1_000_000_007;
```

* `totalSum`: sum of all nodes
* `maxProduct`: stores best answer before modulo
* use `long` because values can be large

---

### 🧮 Main Function

```java
public int maxProduct(TreeNode root) {
    totalSum = getTotalSum(root);   // Step 1
    getSubtreeSum(root);            // Step 2
    return (int)(maxProduct % MOD);
}
```

---

### 🔁 DFS 1 — Total Sum

```java
private long getTotalSum(TreeNode node) {
    if (node == null) return 0;
    return node.val 
         + getTotalSum(node.left) 
         + getTotalSum(node.right);
}
```

✔ Recursively sums all nodes
✔ Runs in `O(n)`

---

### 🔁 DFS 2 — Subtree Sums & Products

```java
private long getSubtreeSum(TreeNode node) {
    if (node == null) return 0;
```

Base case for recursion.

---

```java
    long leftSum = getSubtreeSum(node.left);
    long rightSum = getSubtreeSum(node.right);
```

Compute sums of left and right subtrees.

---

```java
    long subtreeSum = node.val + leftSum + rightSum;
```

This is the sum if we **cut the edge above this node**.

---

```java
    long product = subtreeSum * (totalSum - subtreeSum);
    maxProduct = Math.max(maxProduct, product);
```

* Try this split
* Update the maximum product

---

```java
    return subtreeSum;
}
```

Return subtree sum for parent computation.

---

## 📊 Example Walkthrough

Tree:

```
        1
       / \
      2   3
     / \   \
    4   5   6
```

* `totalSum = 21`
* Possible subtree sums:

  * 4 → product = 4 × 17
  * 11 → product = 11 × 10 ✅
  * 6 → product = 6 × 15
* **Maximum = 110**

---

## ⏱ Complexity Analysis

| Metric | Value                  |
| ------ | ---------------------- |
| Time   | `O(n)`                 |
| Space  | `O(h)` recursion stack |

---

## 🎯 Final Takeaway

* Every edge removal = one subtree split
* Product depends on subtree sum
* Best product happens near `totalSum / 2`
* DFS lets us explore all possibilities efficiently

---

## ✅ Final Takeaway    
* We efficiently find the maximum product of splitted binary tree sums using two DFS traversals.
* The first DFS computes the total sum of the tree, while the second DFS explores all possible subtree sums to find the optimal split.
