**LeetCode 865: Smallest Subtree with all the Deepest Nodes** 
## 🔍 Problem Understanding (Plain English)

You are given a **binary tree**.

* Every node has a **depth** = distance from the root.
* Some nodes are the **deepest nodes** (they have the maximum depth in the tree).
* Your task is to return **the smallest subtree** that contains **all** these deepest nodes.

👉 “Smallest subtree” means:

* The subtree must include *all deepest nodes*
* Among all such subtrees, choose the one **lowest (deepest) in the tree**

---

## 📌 Key Insight

The answer is actually the **Lowest Common Ancestor (LCA)** of **all deepest nodes**.

Why?

* Any subtree containing *all* deepest nodes must include their common ancestor.
* The *lowest* such ancestor gives the *smallest* subtree.

---

## 🧠 How to Think About the Solution

Instead of:

1. Finding all deepest nodes first
2. Then finding their LCA

We do it **in one DFS traversal**.

At every node, we ask:

* What is the **maximum depth** reachable from here?
* Which node should be the **answer subtree root**?

---

## 🔄 DFS Strategy (Core Idea)

For each node, DFS returns **two things**:

1. `depth` → maximum depth below this node
2. `node` → the subtree root that contains all deepest nodes below

### Rules at each node:

| Left depth    | Right depth                 | What it means          | What we return                 |
| ------------- | --------------------------- | ---------------------- | ------------------------------ |
| left > right  | deepest nodes only in left  | propagate left result  | `(left_depth+1, left_node)`    |
| right > left  | deepest nodes only in right | propagate right result | `(right_depth+1, right_node)`  |
| left == right | deepest nodes on both sides | current node is LCA    | `(left_depth+1, current_node)` |

---

## 🖼 Visual Example (Example 1)

![Image](https://s3-lc-upload.s3.amazonaws.com/uploads/2018/07/01/sketch1.png)

![Image](https://miro.medium.com/v2/resize%3Afit%3A1400/0%2Ab60X1VbA8H424hAM.png)

**Deepest nodes:** `7` and `4`
**Their LCA:** `2`
✅ Smallest subtree rooted at `2`

---

## ✨ Why This Works

* DFS ensures **post-order traversal**, so children are processed before parent.
* If deepest nodes exist in **both subtrees**, current node must include both → it becomes the answer.
* If only one side is deeper, no need to include the other side → propagate the deeper subtree upward.
* Single traversal → **O(n)** time, **O(h)** space (recursion stack).

---

## 🧩 Step-by-Step Algorithm

1. Define DFS that returns `(depth, subtree_root)`
2. If node is `null` → return `(0, null)`
3. Recursively compute left and right
4. Compare depths:

   * Equal → return current node
   * Else → return deeper side
5. The final `subtree_root` is the answer

---

## 🧪 Edge Cases

* **Single node tree** → root is the deepest node
* **Skewed tree** → deepest node itself is the answer
* **Multiple deepest nodes across branches** → their LCA

---

## ⏱ Complexity

* **Time:** `O(n)` (visit each node once)
* **Space:** `O(h)` (recursion stack, `h` = tree height)

---

## 🧠 One-Line Summary

> The smallest subtree containing all deepest nodes is the **lowest node where the deepest depth from left and right subtrees becomes equal**.

