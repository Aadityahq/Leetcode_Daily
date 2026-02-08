## 🔍 Problem Explanation

A **binary tree** is called **height-balanced** if:

> For **every node** in the tree,
> the **difference between the heights of its left and right subtrees is at most 1**.

### Height of a tree

* Height = number of edges on the longest path from the node to a leaf
* Empty tree (`null`) has height **0**

---

### ✅ Example 1

```
      3
     / \
    9  20
       / \
      15  7
```

* Left height = 1
* Right height = 2
* Difference = 1 → **Balanced**

---

### ❌ Example 2

```
        1
       / \
      2   2
     / \
    3   3
   / \
  4   4
```

* At node `2`, height difference > 1
* Tree becomes **unbalanced**

---

### ✅ Example 3

```
[]
```

* Empty tree is always **balanced**

---

## 🧠 Key Idea Behind the Solution

Instead of:

* Checking balance **and**
* Calculating height separately (which is slow),

We do **both in one DFS traversal**.

### Trick 💡

* If a subtree is **unbalanced**, return `-1`
* Otherwise, return its **height**

This lets us **stop early** as soon as imbalance is found.

---

## 🚀 Solution Explanation (Your Code)

```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        return dfsHeight(root) != -1;
    }

    private int dfsHeight(TreeNode node) {
        if (node == null) return 0;

        int leftHeight = dfsHeight(node.left);
        if (leftHeight == -1) return -1;

        int rightHeight = dfsHeight(node.right);
        if (rightHeight == -1) return -1;

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return 1 + Math.max(leftHeight, rightHeight);
    }
}
```

---

## 🧩 Step-by-Step How It Works

### 1️⃣ `isBalanced(root)`

* Calls `dfsHeight(root)`
* If result is `-1` → tree is **not balanced**
* Otherwise → **balanced**

---

### 2️⃣ `dfsHeight(node)`

#### Base Case

```java
if (node == null) return 0;
```

* Empty subtree has height `0`

---

#### Recursive Height Calculation

```java
int leftHeight = dfsHeight(node.left);
int rightHeight = dfsHeight(node.right);
```

---

#### Early Exit for Unbalanced Tree

```java
if (leftHeight == -1 || rightHeight == -1)
    return -1;
```

* If any subtree is unbalanced, propagate `-1` upward

---

#### Balance Check

```java
if (Math.abs(leftHeight - rightHeight) > 1)
    return -1;
```

* Height difference > 1 → not balanced

---

#### Return Height

```java
return 1 + Math.max(leftHeight, rightHeight);
```

* Current node height = max(left, right) + 1

---

## ⏱️ Complexity Analysis

| Metric               | Value                                       |
| -------------------- | ------------------------------------------- |
| **Time Complexity**  | `O(n)` (each node visited once)             |
| **Space Complexity** | `O(h)` (recursion stack, `h` = tree height) |

---

## ✅ Why This Solution Is Optimal

* Single DFS traversal
* Stops early if imbalance is detected
* No redundant height calculations

Perfect for interview and LeetCode 💯

---

