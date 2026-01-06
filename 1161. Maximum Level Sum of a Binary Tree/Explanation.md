## 🔍 Problem Understanding

You are given the **root of a binary tree**.

* The **root is at level 1**
* Its children are at **level 2**
* Their children are at **level 3**, and so on

### Your Task

👉 Find the **level number** that has the **maximum sum of node values**.
👉 If multiple levels have the same maximum sum, return the **smallest level number**.

---

## 🧠 Key Insight (How to Think)

This is a **level-by-level problem**, which is a classic use case for:

### ✅ **Breadth-First Search (BFS)**

Why BFS?

* BFS naturally processes nodes **level by level**
* We can calculate the **sum of each level**
* Track the **maximum sum** and its **level index**

---

## 🧪 Example Breakdown

### Example 1

```
Tree: [1,7,0,7,-8]

Level 1 → 1        → sum = 1
Level 2 → 7 + 0    → sum = 7  ✅ max
Level 3 → 7 + (-8) → sum = -1
```

✔️ Maximum sum is **7** at **level 2**
✔️ Answer = **2**

---

## 🧩 Step-by-Step Approach

1. Use a **queue** for BFS
2. Start from **level = 1**
3. For each level:

   * Process all nodes in the queue
   * Add their values to `levelSum`
   * Push their children into the queue
4. Compare `levelSum` with `maxSum`
5. If greater, update:

   * `maxSum`
   * `answerLevel`
6. Increment level and continue

---

## 💡 Why This Works

* Every node is visited **exactly once** → O(n)
* BFS guarantees **correct level grouping**
* We only store one level at a time → efficient memory usage

---

## 🧾 Java Solution

```java
class Solution {
    public int maxLevelSum(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int level = 1;
        int answerLevel = 1;
        int maxSum = Integer.MIN_VALUE;

        while (!queue.isEmpty()) {
            int size = queue.size();
            int levelSum = 0;

            // Process all nodes at the current level
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                levelSum += node.val;

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            // Update max sum and level
            if (levelSum > maxSum) {
                maxSum = levelSum;
                answerLevel = level;
            }

            level++;
        }

        return answerLevel;
    }
}
```

---

## ⏱️ Complexity Analysis

| Metric               | Value                        |
| -------------------- | ---------------------------- |
| **Time Complexity**  | `O(n)`                       |
| **Space Complexity** | `O(n)` (queue in worst case) |

---

## ✅ Final Takeaway

* This problem is about **level-order traversal**
* **BFS** is the most natural and optimal solution
* Track **sum per level**, not individual nodes
* Always return the **smallest level** with max sum
