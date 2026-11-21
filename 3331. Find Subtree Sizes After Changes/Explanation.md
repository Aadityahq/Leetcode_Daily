# **LeetCode 3331 – Find Subtree Sizes After Changes**



Medium

Tree

DFS

Array



Let's understand the problem and the **corrected Java solution** step by step, including why we use DFS, `lastSeen`, the bitwise NOT operator (`~`), and reverse traversal.

## **1. Understanding the problem**

We are given:

- `parent[]`: An array representing a tree.
- `s`: A string containing a character for each node.
- Node `0` is the root.

We need to change the parent of every node (except root) according to this rule:

**Find the closest ancestor having the same character as the current node.**

If we find such an ancestor:

- Remove the connection with the current parent.
- Connect the node to the matching ancestor.

If no matching ancestor exists, keep its original parent.

Finally, return an array containing the subtree size of every node in the modified tree.

**Important:** All parent changes are based on the original tree. They happen simultaneously, not one after another.

## **2. Example walkthrough**

Consider Example 1:

```
parent = [-1, 0, 0, 1, 1, 1]
s = "abaabc"
```

Original tree:

## **Original tree**

Node number and its assigned character.

Let's check node `3`:

- Current node = `3`
- Character = `a`
- Parent = `1`
- Character of node `1` = `b`

Node `1` does not match.

Now move upwards:

- Node `0` has character `a`.
- It matches node `3`.

Therefore:

```
newParent[3] = 0;
```

The modified tree becomes:

```
          0
       /  |  \
      1   2   3
     / \
    4   5
```

Our final answer will be:

```
[6, 3, 1, 1, 1, 1]
```

## **3. Approach: DFS + Last Seen Array**

We will solve this problem in three major steps.

1. Find the new parent of every node.
2. Build the modified tree.
3. Calculate subtree sizes.

### **Step 1: Build the original tree**

We use an adjacency list to represent the tree.

```
List<Integer>[] tree = new ArrayList[n];

for (int i = 0; i < n; i++) {
    tree[i] = new ArrayList<>();
}

for (int i = 1; i < n; i++) {
    tree[parent[i]].add(i);
}
```

**Why do we need an adjacency list?**

Because the `parent` array only tells us the parent of each node.

But during DFS, we need to visit the children of each node easily.

For example:

```
parent = [-1, 0, 0, 1, 1, 1]
```

The adjacency list looks like:

```
0 → [1, 2]
1 → [3, 4, 5]
2 → []
3 → []
4 → []
5 → []
```

### **Step 2: Find the nearest matching ancestor**

This is the most important part of the problem.

We create:

```
int[] lastSeen = new int[26];
Arrays.fill(lastSeen, -1);
```

**Why 26?**

Because there are 26 lowercase English letters.

Each index represents one character:

| Index | Character |
| ----- | --------- |
| 0     | a         |
| 1     | b         |
| 2     | c         |
| 3     | d         |
| ...   | ...       |
| 25    | z         |

For example:

```
int ch = s.charAt(node) - 'a';
```

If the current character is `'c'`:

```
ch = 'c' - 'a';
ch = 2;
```

So we can access:

```
lastSeen[2];
```

This tells us the nearest ancestor having character `'c'` along the current DFS path.

We also create:

```
int[] newParent = new int[n];
int[] previous = new int[n];
```

- `newParent[]` stores the updated parent of every node.
- `previous[]` stores the previous `lastSeen` value so that we can restore it when DFS finishes a node.

### **How does&#x20;**`lastSeen`**&#x20;work?**

Suppose our tree is:

```
       0(a)
         |
       1(b)
         |
       3(a)
```

When we visit node `0`:

```
lastSeen[0] = 0;
```

When we visit node `1`:

```
lastSeen[1] = 1;
```

When we visit node `3`:

```
int ancestor = lastSeen[0];
```

We get:

```
ancestor = 0;
```

Therefore:

```
newParent[3] = 0;
```

We don't need to traverse all ancestors manually because `lastSeen` already stores the closest matching ancestor.

## **4. Understanding iterative DFS**

Instead of recursive DFS, we use an explicit stack.

Why?

The constraints allow up to \\(10^5\\) nodes.

A tree could look like a straight line:

```
0
|
1
|
2
|
3
|
...
|
99999
```

Recursive DFS could cause `StackOverflowError` in Java.

So we use iterative DFS.

```
int[] stack = new int[2 * n];
int top = 0;

stack[top++] = 0;
```

Here:

- `stack[]` stores nodes and exit events.
- `top` tracks the current stack position.
- We use `2 * n` because each node has two events: entering and exiting.

### **What is an enter event?**

When we visit a node:

1. Find its character.
2. Find its nearest matching ancestor.
3. Store its new parent.
4. Save the previous `lastSeen` value.
5. Update `lastSeen`.
6. Schedule an exit event.
7. Visit its children.

### **What is an exit event?**

After finishing all children of a node, we must restore the previous `lastSeen` value.

This is important because we should not allow information from one branch to affect another branch.

We identify exit events using the bitwise NOT operator.

```
stack[top++] = ~node;
```

For example:

| Node | `~node` |
| ---- | ------- |
| 0    | -1      |
| 1    | -2      |
| 2    | -3      |
| 3    | -4      |

When we pop a negative value, we know that it is an exit event.

```
if (node < 0) {

    node = ~node;

    int ch = s.charAt(node) - 'a';

    lastSeen[ch] = previous[node];

    continue;
}
```

**Why&#x20;**`node = ~node`**?**

Because applying bitwise NOT twice gives the original number.

```
~(-3) = 2;
```

So we recover the original node index.

**Why did we get the previous error?**

In the earlier code, `s.charAt(node)` was executed before checking whether `node` was negative.

For example:

```
node = -3;
s.charAt(-3); // Error!
```

Now we check the exit event first, so we never access the string with a negative index.

### **Complete DFS logic**

```
int node = stack[--top];

if (node < 0) {

    node = ~node;

    int ch = s.charAt(node) - 'a';

    lastSeen[ch] = previous[node];

    continue;
}

int ch = s.charAt(node) - 'a';

int ancestor = lastSeen[ch];

if (node == 0) {
    newParent[node] = -1;
} else if (ancestor != -1) {
    newParent[node] = ancestor;
} else {
    newParent[node] = parent[node];
}

previous[node] = lastSeen[ch];

lastSeen[ch] = node;

stack[top++] = ~node;

for (int child : tree[node]) {
    stack[top++] = child;
}
```

Let's understand these important lines:

```
int ancestor = lastSeen[ch];
```

Finds the nearest matching ancestor.

```
previous[node] = lastSeen[ch];
```

Saves the previous ancestor information.

```
lastSeen[ch] = node;
```

Makes the current node the latest ancestor for its character.

```
stack[top++] = ~node;
```

Schedules the restoration operation after processing the children.

```
lastSeen[ch] = previous[node];
```

Restores the previous value when exiting the node.

## **5. Step 2: Build the modified tree**

Once DFS is completed, we have the new parent of every node.

Now we create another adjacency list.

```
List<Integer>[] newTree = new ArrayList[n];

for (int i = 0; i < n; i++) {
    newTree[i] = new ArrayList<>();
}

for (int i = 1; i < n; i++) {
    newTree[newParent[i]].add(i);
}
```

**Why do we build another tree?**

Because the original tree and modified tree can have different parent-child relationships.

For Example 1:

Original:

```
0
├── 1
│   ├── 3
│   ├── 4
│   └── 5
└── 2
```

Modified:

```
0
├── 1
│   ├── 4
│   └── 5
├── 2
└── 3
```

Now all subtree calculations must use the modified tree.

## **6. Step 3: Calculate subtree sizes**

Initially, every node has a subtree size of `1` because every node is part of its own subtree.

```
int[] answer = new int[n];
Arrays.fill(answer, 1);
```

Now we need to add each child's subtree size to its parent.

The formula is:

\\[ answer[parent[node]] += answer[node] \\]

But there is one important condition:

**We must process children before their parents.**

For that, we first generate a traversal order.

```
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
```

This generates a parent-before-child order.

For example:

```
order = [0, 3, 2, 1, 5, 4]
```

The exact order may differ depending on child insertion order.

Now process it in reverse:

```
for (int i = n - 1; i > 0; i--) {

    int node = order[i];

    answer[newParent[node]] += answer[node];
}
```

Why reverse?

Because children must contribute their complete subtree sizes before their parents are processed.

For example:

```
       0
       |
       1
      / \
     4   5
```

Initially:

```
answer = [1, 1, 1, 1, 1, 1];
```

After processing node `5`:

```
answer[1] += answer[5];
```

After processing node `4`:

```
answer[1] += answer[4];
```

Now:

```
answer[1] = 3;
```

Finally:

```
answer[0] += answer[1];
```

Now:

```
answer[0] = 4;
```

This way, every node gets the correct subtree size.

## **7. Complete corrected Java code**

```

import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {

        int n = parent.length;

        // Step 1: Build original tree
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        // Step 2: Track nearest matching ancestor
        int[] lastSeen = new int[26];
        Arrays.fill(lastSeen, -1);

        int[] newParent = new int[n];
        int[] previous = new int[n];

        int[] stack = new int[2 * n];
        int top = 0;

        stack[top++] = 0;

        while (top > 0) {

            int node = stack[--top];

            // Exit event
            if (node < 0) {

                node = ~node;

                int ch = s.charAt(node) - 'a';

                lastSeen[ch] = previous[node];

                continue;
            }

            int ch = s.charAt(node) - 'a';

            // Find closest matching ancestor
            int ancestor = lastSeen[ch];

            if (node == 0) {
                newParent[node] = -1;
            } else if (ancestor != -1) {
                newParent[node] = ancestor;
            } else {
                newParent[node] = parent[node];
            }

            // Save previous value
            previous[node] = lastSeen[ch];

            // Update latest ancestor
            lastSeen[ch] = node;

            // Schedule exit event
            stack[top++] = ~node;

            // Visit original children
            for (int child : tree[node]) {
                stack[top++] = child;
            }
        }

        // Step 3: Build modified tree
        List<Integer>[] newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            newTree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        // Step 4: Get traversal order
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

        for (int i = n - 1; i > 0; i--) {

            int node = order[i];

            answer[newParent[node]] += answer[node];
        }

        return answer;
    }
}

```

## **8. Complexity Analysis**

Let `n` be the number of nodes.

| Operation                | Time |
| ------------------------ | ---- |
| Build original tree      | O(n) |
| DFS to find new parents  | O(n) |
| Build modified tree      | O(n) |
| Generate traversal order | O(n) |
| Calculate subtree sizes  | O(n) |

**Time Complexity**

O(n)

**Space Complexity**

O(n)

## **9. Important interview takeaways**

Remember these five things from this problem:

1. **Adjacency List:** Used to represent the tree and access children.
2. **DFS:** Used to explore the original tree.
3. **Last Seen Array:** Finds the closest ancestor with the same character in O(1).
4. **Backtracking:** Restores the previous ancestor information when leaving a node.
5. **Reverse Traversal:** Calculates subtree sizes from children to parents.

### **Quick revision**

| Question               | Answer                                                     |
| ---------------------- | ---------------------------------------------------------- |
| Why `lastSeen[26]`?    | To track the latest ancestor for each lowercase character. |
| Why `previous[]`?      | To restore the previous ancestor during DFS exit.          |
| Why `~node`?           | To represent an exit event in the stack.                   |
| Why iterative DFS?     | To avoid stack overflow for up to 100,000 nodes.           |
| Why build a new tree?  | Parent relationships have changed.                         |
| Why reverse traversal? | Children must contribute before their parents.             |

**The main idea:** First find all new parents using the original tree. Then build the modified tree and calculate subtree sizes using reverse traversal.

This is an O(n) solution suitable for the given constraints.