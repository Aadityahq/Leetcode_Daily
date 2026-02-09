## 🧠 Problem Understanding

You are given the **root of a Binary Search Tree (BST)**.

* The BST property already holds:

  * Left subtree values `< root`
  * Right subtree values `> root`
* But the tree may be **skewed** (like a linked list).

Your task:
👉 **Return a balanced BST** using the **same node values**.

### What does “balanced” mean?

For **every node**:

```
|height(left subtree) - height(right subtree)| ≤ 1
```

---

## ❌ Problem with the Given Tree

Example 1:

```
1
 \
  2
   \
    3
     \
      4
```

This is a BST ✔
But it’s **not balanced** ❌
Height difference is too large → operations degrade to **O(n)** instead of **O(log n)**.

---

## 💡 Key Insight (HOW & WHY)

### 🔑 Observation 1: Inorder traversal of a BST gives sorted values

If you do an **inorder traversal**, you get:

```
sorted array of node values
```

### 🔑 Observation 2: A balanced BST can be built from a sorted array

Classic trick:

* Pick the **middle element** as root
* Recursively build left subtree from left half
* Recursively build right subtree from right half

This guarantees:

* Height is minimized
* Tree is balanced

---

## 🛠️ Strategy (Step by Step)

### Step 1: Inorder traversal → sorted list

Store all node values in increasing order.

### Step 2: Build balanced BST from sorted list

Use divide-and-conquer:

* Mid element → root
* Left part → left subtree
* Right part → right subtree

---

## ✅ Java Solution

```java
class Solution {

    // List to store inorder traversal
    private List<Integer> inorder = new ArrayList<>();

    public TreeNode balanceBST(TreeNode root) {
        // Step 1: Get sorted values via inorder traversal
        inorderTraversal(root);

        // Step 2: Build balanced BST from sorted list
        return buildBalancedBST(0, inorder.size() - 1);
    }

    // Inorder traversal to collect values
    private void inorderTraversal(TreeNode root) {
        if (root == null) return;

        inorderTraversal(root.left);
        inorder.add(root.val);
        inorderTraversal(root.right);
    }

    // Build balanced BST from sorted list
    private TreeNode buildBalancedBST(int left, int right) {
        if (left > right) return null;

        int mid = left + (right - left) / 2;
        TreeNode node = new TreeNode(inorder.get(mid));

        node.left = buildBalancedBST(left, mid - 1);
        node.right = buildBalancedBST(mid + 1, right);

        return node;
    }
}
```

---

## ⏱️ Time & Space Complexity

### Time Complexity

* Inorder traversal: **O(n)**
* Building BST: **O(n)**
  ✅ **Total: O(n)**

### Space Complexity

* Inorder list: **O(n)**
* Recursion stack: **O(log n)** (balanced tree)
  ✅ **Total: O(n)**

---

## 🎯 Why This Solution Is Optimal

* Uses BST properties efficiently
* Guarantees balance
* Simple and reliable
* Commonly expected in **interviews**

---

