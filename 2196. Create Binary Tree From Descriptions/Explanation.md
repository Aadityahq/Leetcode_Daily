## Understanding the Problem

You are given relationships of a binary tree in the form:

```java
[parent, child, isLeft]
```

where:

* `parent` is the parent node.
* `child` is the child node.
* `isLeft = 1` → child is the left child.
* `isLeft = 0` → child is the right child.

Your task is to **construct the binary tree and return its root node**.

---

### Example

Input:

```java
[[20,15,1],
 [20,17,0],
 [50,20,1],
 [50,80,0],
 [80,19,1]]
```

This means:

```text
20 -> left  = 15
20 -> right = 17

50 -> left  = 20
50 -> right = 80

80 -> left  = 19
```

Tree becomes:

```text
        50
       /  \
     20    80
    / \   /
   15 17 19
```

Root = **50**

---

# Key Observation

To build the tree:

1. Create nodes whenever we see a value.
2. Connect parent and child.
3. Find the root.

### How to find the root?

Every node except the root appears as a **child** at least once.

So:

* Store all child values in a set.
* After constructing the tree, find the node that never appeared as a child.
* That node is the root.

---

# Data Structures Used

### HashMap

```java
Map<Integer, TreeNode>
```

Stores:

```text
value -> TreeNode
```

So we can quickly get/create nodes.

---

### HashSet

```java
Set<Integer> children
```

Stores all child values.

This helps identify the root.

---

# Step-by-Step Algorithm

For each description:

```java
[parent, child, isLeft]
```

### 1. Create parent node if not present

```java
map.putIfAbsent(parent, new TreeNode(parent));
```

---

### 2. Create child node if not present

```java
map.putIfAbsent(child, new TreeNode(child));
```

---

### 3. Connect them

If left child:

```java
parent.left = child;
```

Otherwise:

```java
parent.right = child;
```

---

### 4. Record child

```java
children.add(child);
```

---

After processing all descriptions:

Find node not present in `children`.

That is the root.

---

# Dry Run

Input:

```java
[[1,2,1],
 [2,3,0],
 [3,4,1]]
```

---

### Process [1,2,1]

Create:

```text
1
2
```

Connect:

```text
1
/
2
```

children:

```text
{2}
```

---

### Process [2,3,0]

Connect:

```text
1
/
2
 \
  3
```

children:

```text
{2,3}
```

---

### Process [3,4,1]

Connect:

```text
1
/
2
 \
  3
 /
4
```

children:

```text
{2,3,4}
```

---

Find node not in children:

```text
1
```

Return node 1.

---

# Java Solution

```java
class Solution {
    public TreeNode createBinaryTree(int[][] descriptions) {
        Map<Integer, TreeNode> map = new HashMap<>();
        Set<Integer> children = new HashSet<>();

        for (int[] d : descriptions) {
            int parent = d[0];
            int child = d[1];
            int isLeft = d[2];

            map.putIfAbsent(parent, new TreeNode(parent));
            map.putIfAbsent(child, new TreeNode(child));

            TreeNode parentNode = map.get(parent);
            TreeNode childNode = map.get(child);

            if (isLeft == 1) {
                parentNode.left = childNode;
            } else {
                parentNode.right = childNode;
            }

            children.add(child);
        }

        for (int value : map.keySet()) {
            if (!children.contains(value)) {
                return map.get(value);
            }
        }

        return null;
    }
}
```

---

# Why This Works

Every description tells us exactly:

* who the parent is,
* who the child is,
* whether the child goes left or right.

Using the map ensures:

* only one `TreeNode` object exists for each value,
* we can connect nodes in O(1) time.

The root is the only node that:

* appears as a parent,
* never appears as a child.

Therefore checking the child set correctly identifies the root.

---

# Complexity Analysis

Let:

```text
n = descriptions.length
```

### Time Complexity

Building tree:

```text
O(n)
```

Finding root:

```text
O(n)
```

Total:

```text
O(n)
```

---

### Space Complexity

HashMap + HashSet:

```text
O(n)
```

---

# Interview Explanation (Short)

> I use a HashMap to store value-to-TreeNode mappings so each node is created only once. While processing each description, I connect the parent and child according to the isLeft flag and store every child value in a HashSet. After construction, the root is the only node that never appears as a child, so I return that node. The solution runs in O(n) time and O(n) space.
