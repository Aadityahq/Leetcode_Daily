## 🔹 1022. Sum of Root To Leaf Binary Numbers (LeetCode)

### 📌 Problem Understanding

You are given a **binary tree** where:

* Each node contains only `0` or `1`.
* Every **root → leaf path** forms a binary number.
* The root is the **most significant bit (MSB)**.

You must:

👉 Convert each root-to-leaf path into a decimal number
👉 Return the **sum** of all those numbers

---

### 🔎 Example

Input:

```
        1
       / \
      0   1
     / \ / \
    0  1 0  1
```

Paths:

* 1 → 0 → 0  = `100` (binary) = 4
* 1 → 0 → 1  = `101` (binary) = 5
* 1 → 1 → 0  = `110` (binary) = 6
* 1 → 1 → 1  = `111` (binary) = 7

Sum = 4 + 5 + 6 + 7 = **22**

---

## 🧠 Key Idea (How to Think)

When moving down the tree:

At every step:

```
currentNumber = currentNumber * 2 + node.val
```

### ❓ Why multiply by 2?

Because:

Binary shifting left = multiply by 2

Example:

```
1 → 0 → 1

Start: 0
After 1: 0*2 + 1 = 1
After 0: 1*2 + 0 = 2
After 1: 2*2 + 1 = 5
```

Binary `101` = Decimal `5`

---

## 🚀 Approach (DFS - Depth First Search)

We:

1. Start from root
2. Keep forming the binary number
3. When we reach a **leaf**, return the number
4. Add left subtree sum + right subtree sum

---

## ✅ Java Solution

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

class Solution {
    
    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }
    
    private int dfs(TreeNode node, int current) {
        if (node == null) return 0;
        
        // Form the binary number
        current = current * 2 + node.val;
        
        // If it's a leaf node, return the value
        if (node.left == null && node.right == null) {
            return current;
        }
        
        // Otherwise, sum left and right subtree
        return dfs(node.left, current) + dfs(node.right, current);
    }
}
```

---

## 🔍 Step-by-Step Execution

For path `1 → 0 → 1`:

| Node | Current Value Calculation | Decimal |
| ---- | ------------------------- | ------- |
| 1    | 0*2 + 1                   | 1       |
| 0    | 1*2 + 0                   | 2       |
| 1    | 2*2 + 1                   | 5       |

Return 5 at leaf.

---

## ⏱ Time & Space Complexity

* **Time Complexity:** O(N)
  (Visit each node once)

* **Space Complexity:** O(H)
  (Recursion stack, H = height of tree)

Worst case: O(N) if skewed tree
Best case: O(log N) if balanced

---

## 🎯 Why This Is Optimal

* We avoid storing all paths.
* We compute values on the go.
* No extra conversions from binary string to decimal.
* Just simple math (multiply by 2 + add bit).

---
