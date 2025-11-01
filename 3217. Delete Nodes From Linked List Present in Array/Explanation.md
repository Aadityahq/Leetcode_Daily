## 🧩 Problem Understanding

You are given:

* An **array `nums`** that contains some integer values.
* A **linked list `head`**.

Your task:
Remove **all nodes** from the linked list whose values are present in the `nums` array.

Then return the **head of the modified linked list**.

---

### ✨ Example 1

```
nums = [1, 2, 3]
head = [1, 2, 3, 4, 5]
```

We remove nodes whose values are in `nums` (1, 2, 3).
So, remaining list is: `[4, 5]`

✅ **Output:** `[4, 5]`

---

### ✨ Example 2

```
nums = [1]
head = [1, 2, 1, 2, 1, 2]
```

Remove all nodes with value 1 → `[2, 2, 2]`

✅ **Output:** `[2, 2, 2]`

---

### ✨ Example 3

```
nums = [5]
head = [1, 2, 3, 4]
```

No nodes have value 5 → list remains `[1, 2, 3, 4]`

✅ **Output:** `[1, 2, 3, 4]`

---

## 🧠 How to Think About It

We need to efficiently check whether a node’s value exists in `nums`.

* Since `nums` can be up to `10⁵` in size, using a **HashSet** is ideal.

  * Checking membership (`nums.contains(x)`) will be **O(1)** average time.

Then we just:

1. Traverse the linked list.
2. Skip nodes whose values are in the set.
3. Build the new linked list.

---

## ✅ Step-by-Step Approach

1. **Convert `nums` → HashSet** for fast lookup.

   ```java
   Set<Integer> removeSet = new HashSet<>();
   for (int num : nums) removeSet.add(num);
   ```

2. **Use a dummy node** before head to simplify deletion logic.

   * If the first few nodes need to be removed, dummy helps.

3. Traverse the list using two pointers:

   * `prev` → points to the last *kept* node
   * `curr` → points to the current node being checked

4. If `curr.val` is in `removeSet`, skip it:

   ```java
   prev.next = curr.next;
   ```

   else, move `prev` forward.

5. Return `dummy.next` as the new head.

---

## 💻 Java Solution

```java
import java.util.*;

class Solution {
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> removeSet = new HashSet<>();
        for (int num : nums) removeSet.add(num);

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr = head, prev = dummy;

        while (curr != null) {
            if (removeSet.contains(curr.val)) {
                // Skip the current node
                prev.next = curr.next;
            } else {
                prev = curr;
            }
            curr = curr.next;
        }

        return dummy.next;
    }
}
```

---

## 🧾 Dry Run Example

### Input

```
nums = [1, 2, 3]
head = [1, 2, 3, 4, 5]
```

### Steps

| Step | curr.val | In removeSet? | Action | Resulting List |
| ---- | -------- | ------------- | ------ | -------------- |
| 1    | 1        | ✅ Yes         | remove | [2, 3, 4, 5]   |
| 2    | 2        | ✅ Yes         | remove | [3, 4, 5]      |
| 3    | 3        | ✅ Yes         | remove | [4, 5]         |
| 4    | 4        | ❌ No          | keep   | [4, 5]         |
| 5    | 5        | ❌ No          | keep   | [4, 5]         |

✅ Output: `[4, 5]`

---

## ⚙️ Complexity Analysis

| Type                 | Complexity                                            |
| -------------------- | ----------------------------------------------------- |
| **Time Complexity**  | O(n + m) — where n = number of nodes, m = nums.length |
| **Space Complexity** | O(m) — for the HashSet                                |

---

## 🧩 Summary

| Concept              | Explanation                                                |
| -------------------- | ---------------------------------------------------------- |
| **Goal**             | Remove nodes from linked list whose values exist in `nums` |
| **Data structure**   | HashSet for O(1) lookup                                    |
| **Helper technique** | Dummy node for easier deletion                             |
| **Time complexity**  | O(n + m)                                                   |
| **Space complexity** | O(m)                                                       |
| **Edge cases**       | All nodes removed / none removed / head node removed       |

---

