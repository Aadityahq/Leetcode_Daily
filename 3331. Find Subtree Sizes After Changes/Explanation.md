# **LeetCode 3331 – Find Subtree Sizes After Changes**



Medium

Tree

DFS

HashMap



## **1. Understanding the problem**

You are given:

- `parent[]` – represents a tree.
- `s` – a string where each character represents the character assigned to a node.
- Node `0` is the root of the tree.

Your task is to **change the parent of some nodes based on matching characters with their closest ancestors**, and then calculate the size of every node's subtree in the modified tree.

### **What does the problem ask?**

For every node `x` (except root):

1. Start from its current parent.
2. Move upwards through its ancestors.
3. Find the closest ancestor `y` whose character is equal to `s[x]`.
4. If such an ancestor exists, make `y` the new parent of `x`.
5. If no matching ancestor exists, keep the original parent.

**Important:** All parent changes happen simultaneously.

This means we must find ancestors using the original tree, not the modified tree.

## **2. Example walkthrough**

**Example 1:**

```
parent = [-1, 0, 0, 1, 1, 1]
s = "abaabc"
```

Initially, the tree looks like this:

## **Original tree**

Each node shows its index; the nearby letter is its character.

Let's check each node:

| Node | Character | Closest matching ancestor | New parent |
| ---- | --------- | ------------------------- | ---------- |
| 1    | b         | None                      | 0          |
| 2    | a         | 0 (a)                     | 0          |
| 3    | a         | 0 (a)                     | 0          |
| 4    | b         | 1 (b)                     | 1          |
| 5    | c         | None                      | 1          |

Node `3` originally had parent `1`, whose character is `b`.

But node `0` has character `a`, matching node `3`.

Therefore, node `3` gets a new parent: `0`.

Final tree:

```
          0
       /  |  \
      1   2   3
     / \
    4   5
```

Subtree sizes:

- Node 0 → 6 nodes
- Node 1 → 3 nodes (1, 4, 5)
- Node 2 → 1 node
- Node 3 → 1 node
- Node 4 → 1 node
- Node 5 → 1 node

**Output:**

```
[6, 3, 1, 1, 1, 1]
```

## **3. Approach – DFS + Last Seen Character**

We can solve this problem efficiently using **DFS (Depth First Search) and an array to track the nearest ancestor for each character.**

The solution has two main steps.

### **Step 1: Find the new parent of every node**

We need to find the closest ancestor having the same character.

Instead of checking all ancestors one by one, we maintain an array:

```
int[] lastSeen = new int[26];
```

Why size 26?

Because the string contains only lowercase English letters (`a` to `z`).

Initially, all positions contain `-1`, meaning no ancestor has been found.

For example:

```
s = "abaabc"
```

Suppose we are currently visiting node `3`.

- `s[3] = 'a'`
- Its original parent is node `1`.
- Node `1` has character `'b'`.
- Move upwards to node `0`.
- Node `0` has character `'a'`.

So, the closest matching ancestor is node `0`.

We can directly find it using:

```
int ancestor = lastSeen[s.charAt(node) - 'a'];
```

If an ancestor exists, update the parent:

```
if (ancestor != -1) {
    newParent[node] = ancestor;
} else {
    newParent[node] = parent[node];
}
```

**But how do we maintain the closest ancestor?**

During DFS:

1. Visit a node.
2. Check `lastSeen` for its character.
3. Save the previous value.
4. Update `lastSeen` with the current node.
5. Visit its children.
6. When leaving the node, restore the previous value.

Why restore the previous value?

Because DFS explores different branches. A node from one branch must not become an ancestor of a node in another branch.

For example:

```
        0(a)
       /    \
    1(b)    2(b)
      |
    3(a)
```

When DFS finishes visiting node `1` and moves to node `2`, the last-seen information for node `1` must be restored. Otherwise, node `2` might incorrectly consider node `1` its ancestor.

### **Step 2: Calculate subtree sizes**

After finding the new parent of every node, construct the modified tree.

Now we need to calculate the size of each subtree.

Initially, every node contributes one to its own subtree:

```
int[] answer = new int[n];
Arrays.fill(answer, 1);
```

Then process nodes from children to parents.

The formula is:

\\[ answer[parent[node]] += answer[node] \\]

This ensures that every parent's subtree size includes all its descendants.

We can achieve this using reverse DFS traversal order.

## **4. Java Solution**

One important implementation detail: the constraints allow up to \\(10^5\\) nodes. A recursive DFS can cause `StackOverflowError` in Java for a deeply skewed tree.

Therefore, we'll use **iterative DFS** to avoid recursion stack problems.

```

import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        // Step 1: Build the original tree
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        // Step 2: Track the nearest ancestor
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);

        int[] newParent = new int[n];
        int[] previous = new int[n];

        // Iterative DFS using enter/exit events
        int[] stack = new int[2 * n];
        int top = 0;

        stack[top++] = 0;

        while (top > 0) {

            int node = stack[--top];
            int ch = s.charAt(node) - 'a';

            // Exit event
            if (node < 0) {
                node = ~node;
                int c = s.charAt(node) - 'a';

                lastSeen[c] = previous[node];
                continue;
            }

            // Find closest matching ancestor
            int ancestor = lastSeen[ch];

            if (node == 0) {
                newParent[node] = -1;
            } else if (ancestor != -1) {
                newParent[node] = ancestor;
            } else {
                newParent[node] = parent[node];
            }

            // Save previous value before updating
            previous[node] = lastSeen[ch];

            // Current node becomes the latest ancestor
            lastSeen[ch] = node;

            // Schedule exit event
            stack[top++] = ~node;

            // Visit original children
            for (int child : tree[node]) {
                stack[top++] = child;
            }
        }

        // Step 3: Build the modified tree
        List<Integer>[] newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        // Step 4: Get parent-before-child traversal order
        int[] order = new int[n];
        int[] dfsStack = new int[n];

        int top2 = 0;
        int idx = 0;

        dfsStack[top2++] = 0;

        while (top2 > 0) {

            int node = dfsStack[--top2];

            order[idx++] = node;

            for (int child : newTree[node]) {
                dfsStack[top2++] = child;
            }
        }

        // Step 5: Calculate subtree sizes
        int[] answer = new int[n];
        Arrays.fill(answer, 1);

        // Process children before parents
        for (int i = n - 1; i > 0; i--) {

            int node = order[i];

            answer[newParent[node]] += answer[node];
        }

        return answer;
    }
}

```

## **5. Dry Run**

Let's understand how the code works with Example 1.

```
parent = [-1, 0, 0, 1, 1, 1]
s = "abaabc"
```

**Original tree:**

```
          0(a)
         /    \
       1(b)   2(a)
      / | \
    3(a) 4(b) 5(c)
```

**Finding new parents:**

| Node | Character | Last seen matching ancestor | New parent |
| ---- | --------- | --------------------------- | ---------- |
| 0    | a         | None                        | -1         |
| 1    | b         | None                        | 0          |
| 2    | a         | 0                           | 0          |
| 3    | a         | 0                           | 0          |
| 4    | b         | 1                           | 1          |
| 5    | c         | None                        | 1          |

Notice that node `3` gets node `0` as its new parent.

**Modified tree:**

```
          0
       /  |  \
      1   2   3
     / \
    4   5
```

Now initialize:

```
answer = [1, 1, 1, 1, 1, 1];
```

Process children before parents:

| Node | Parent | Operation      | Answer        |
| ---- | ------ | -------------- | ------------- |
| 5    | 1      | answer[1] += 1 | [1,2,1,1,1,1] |
| 4    | 1      | answer[1] += 1 | [1,3,1,1,1,1] |
| 3    | 0      | answer[0] += 1 | [2,3,1,1,1,1] |
| 2    | 0      | answer[0] += 1 | [3,3,1,1,1,1] |
| 1    | 0      | answer[0] += 3 | [6,3,1,1,1,1] |

Final output:

```
[6, 3, 1, 1, 1, 1]
```

## **6. Complexity Analysis**

Let `n` be the number of nodes.

| Operation                  | Time Complexity |
| -------------------------- | --------------- |
| Build original tree        | O(n)            |
| Find new parents using DFS | O(n)            |
| Build modified tree        | O(n)            |
| Get traversal order        | O(n)            |
| Calculate subtree sizes    | O(n)            |

**Overall complexity**

Time Complexity

O(n)

Space Complexity

O(n)

We visit every node a constant number of times. Also, checking the nearest matching ancestor takes O(1) because we use the `lastSeen` array.

## **7. Important concepts to remember**

For interviews and future tree problems, remember these concepts:

| Concept             | Why we use it                             |
| ------------------- | ----------------------------------------- |
| Tree representation | To build adjacency lists                  |
| DFS                 | To traverse the original tree             |
| Last seen array     | To find the nearest matching ancestor     |
| Backtracking        | To restore ancestor information           |
| Modified tree       | To represent updated parent relationships |
| Reverse traversal   | To calculate subtree sizes                |

### **Why is this approach efficient?**

The brute-force approach would check all ancestors for every node. In a skewed tree, this can take \\(O(n^2)\\) time.

Our approach maintains the latest ancestor for each character, so we don't need to repeatedly search upwards.

**The key idea to remember:**

- First DFS → Find the new parent using the original tree.
- Second traversal → Build the modified tree.
- Reverse traversal → Calculate subtree sizes.

This gives us an efficient **O(n) solution**.