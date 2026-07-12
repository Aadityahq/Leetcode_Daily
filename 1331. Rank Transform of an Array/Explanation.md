You can add this as the next entry in your **LeetCode Daily** repository.

```md
- ✅ [Day 299 – LeetCode 1331: Rank Transform of an Array](https://leetcode.com/problems/rank-transform-of-an-array/) – 12 Jul 2026
```

---

# Day 299 – LeetCode 1331: Rank Transform of an Array

## 🟢 Problem Difficulty

**Easy**

## 📝 Problem Statement

You are given an integer array `arr`.

Replace every element with its **rank**.

The rank follows these rules:

* Rank starts from **1**.
* Larger values have larger ranks.
* Equal values must have the **same rank**.
* Ranks should be as **small as possible**.

### Example

**Input**

```text
arr = [40,10,20,30]
```

**Output**

```text
[4,1,2,3]
```

**Explanation**

Sorted unique numbers are:

```text
10 20 30 40
```

Assign ranks:

```text
10 → 1
20 → 2
30 → 3
40 → 4
```

So the transformed array becomes:

```text
[4,1,2,3]
```

---

# 🤔 Understanding the Problem

The question is **not asking us to sort the array**.

Instead, we need to replace every number with its position among the **unique sorted values**.

For example,

```text
arr = [100,100,50,20]
```

Unique sorted values:

```text
20,50,100
```

Ranks become

```text
20  -> 1
50  -> 2
100 -> 3
```

Final answer:

```text
[3,3,2,1]
```

Notice that duplicate values receive the **same rank**.

---

# 💡 Intuition

To know each number's rank, we first need the numbers in sorted order.

Steps:

1. Copy the original array.
2. Sort the copied array.
3. Traverse the sorted array.
4. Assign ranks only to unique numbers.
5. Store the mapping:

   ```
   value → rank
   ```
6. Traverse the original array again and replace each element with its mapped rank.

---

# 🚀 Approach

### Step 1

Copy the original array.

```text
Original:
40 10 20 30

Copy:
40 10 20 30
```

---

### Step 2

Sort the copy.

```text
10 20 30 40
```

---

### Step 3

Assign ranks.

```text
10 → 1
20 → 2
30 → 3
40 → 4
```

Store them in a HashMap.

```java
{
10=1,
20=2,
30=3,
40=4
}
```

---

### Step 4

Traverse the original array.

```text
40 → 4
10 → 1
20 → 2
30 → 3
```

Answer:

```text
[4,1,2,3]
```

---

# 🌳 Dry Run

Input

```text
arr = [37,12,28,9,100,56,80,5,12]
```

### Sorted copy

```text
5
9
12
12
28
37
56
80
100
```

Assign ranks

```text
5   → 1
9   → 2
12  → 3
28  → 4
37  → 5
56  → 6
80  → 7
100 → 8
```

HashMap

```text
{
5=1,
9=2,
12=3,
28=4,
37=5,
56=6,
80=7,
100=8
}
```

Replace values

```text
37 → 5
12 → 3
28 → 4
9  → 2
100→ 8
56 → 6
80 → 7
5  → 1
12 → 3
```

Final answer

```text
[5,3,4,2,8,6,7,1,3]
```

---

# ✅ Java Solution

```java
import java.util.*;

class Solution {
    public int[] arrayRankTransform(int[] arr) {

        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Map<Integer, Integer> rankMap = new HashMap<>();
        int rank = 1;

        for (int num : sorted) {
            if (!rankMap.containsKey(num)) {
                rankMap.put(num, rank++);
            }
        }

        int[] result = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = rankMap.get(arr[i]);
        }

        return result;
    }
}
```

---

# 🔍 Code Explanation

### Copy the array

```java
int[] sorted = arr.clone();
```

We keep the original array unchanged.

---

### Sort the copied array

```java
Arrays.sort(sorted);
```

Sorting helps us determine the order of values.

---

### Create a HashMap

```java
Map<Integer, Integer> rankMap = new HashMap<>();
```

Stores

```text
value → rank
```

Example

```text
20 → 2
40 → 4
```

---

### Assign ranks

```java
for (int num : sorted) {
    if (!rankMap.containsKey(num)) {
        rankMap.put(num, rank++);
    }
}
```

If the number appears for the first time,

assign the current rank.

Duplicates are skipped, ensuring they receive the same rank.

---

### Build the answer

```java
for (int i = 0; i < arr.length; i++) {
    result[i] = rankMap.get(arr[i]);
}
```

Replace every value with its corresponding rank.

---

# ✅ Why Does This Work?

Sorting the copied array gives numbers in increasing order.

The first unique number receives rank **1**, the next unique number receives rank **2**, and so on.

Using a **HashMap**, every occurrence of the same value gets the same rank in **O(1)** lookup time.

Thus, the final transformed array satisfies all ranking rules.

---

# ⏱ Complexity Analysis

### Time Complexity

Sorting:

```text
O(n log n)
```

Assigning ranks:

```text
O(n)
```

Building result:

```text
O(n)
```

Overall:

```text
O(n log n)
```

---

### Space Complexity

Sorted copy:

```text
O(n)
```

HashMap:

```text
O(n)
```

Result array:

```text
O(n)
```

Overall:

```text
O(n)
```

---

# 🎯 Key Takeaways

* Sort a **copy** of the array.
* Assign ranks only to **unique** values.
* Use a **HashMap** for quick value-to-rank mapping.
* Traverse the original array to replace each element with its rank.
* **Time Complexity:** **O(n log n)**
* **Space Complexity:** **O(n)**
