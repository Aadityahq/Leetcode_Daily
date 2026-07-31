
# 3016. Minimum Number of Pushes to Type Word II

## Intuition

We are allowed to **remap the letters to any of the 8 keys (2–9)** in any way we want.

Our goal is to minimize the total number of key presses.

### Key Observation

* There are **8 keys**.
* The **first letter** assigned to any key costs **1 push**.
* The **second letter** on the same key costs **2 pushes**.
* The **third letter** costs **3 pushes**, and so on.

Since we can arrange the letters however we like:

* The **most frequently occurring letters** should get the **smallest push count**.
* The least frequent letters should receive larger push counts.

This is a classic **Greedy** problem.

---

# Approach

### Step 1: Count frequency

Count how many times every character appears.

Example:

```
word = "aabbccddeeffgghhiiiiii"

Frequency:

i -> 6
a -> 2
b -> 2
c -> 2
d -> 2
e -> 2
f -> 2
g -> 2
h -> 2
```

---

### Step 2: Sort frequencies in descending order

```
6 2 2 2 2 2 2 2 2
```

---

### Step 3: Assign cheapest positions first

There are 8 keys.

So,

* First 8 most frequent letters → cost = 1
* Next 8 letters → cost = 2
* Next 8 letters → cost = 3
* ...

Cost formula:

```
cost = (index / 8) + 1
```

because

```
index 0-7   -> 1 push
index 8-15  -> 2 pushes
index 16-23 -> 3 pushes
...
```

Multiply:

```
frequency × pushCost
```

and add them.

---

# Example Walkthrough

### Input

```
word = "aabbccddeeffgghhiiiiii"
```

Frequency

```
i = 6
others = 2
```

Sorted

```
6 2 2 2 2 2 2 2 2
```

Assignments

| Index | Frequency | Push Cost | Contribution |
| ----- | --------- | --------- | ------------ |
| 0     | 6         | 1         | 6            |
| 1     | 2         | 1         | 2            |
| 2     | 2         | 1         | 2            |
| 3     | 2         | 1         | 2            |
| 4     | 2         | 1         | 2            |
| 5     | 2         | 1         | 2            |
| 6     | 2         | 1         | 2            |
| 7     | 2         | 1         | 2            |
| 8     | 2         | 2         | 4            |

Total

```
6+2+2+2+2+2+2+2+4 = 24
```

Answer = **24**

---

# Why Greedy Works?

Suppose

```
Letter A appears 100 times
Letter B appears 2 times
```

If we give

```
A -> cost 2
B -> cost 1
```

Total

```
100×2 + 2×1 = 202
```

If we swap

```
A -> cost 1
B -> cost 2
```

Total

```
100×1 + 2×2 = 104
```

Much smaller.

Therefore,

**Higher frequency letters should always receive lower costs.**

Hence sorting frequencies in descending order is optimal.

---

# Algorithm

1. Count frequency of each letter.
2. Sort frequencies in descending order.
3. Traverse sorted frequencies.
4. Cost = `(index / 8) + 1`
5. Add `frequency × cost`.
6. Return answer.

---

# Java Solution

```java
import java.util.*;

class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];

        // Count frequency of each letter
        for (char ch : word.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Sort frequencies
        Arrays.sort(freq);

        int pushes = 0;
        int index = 0;

        // Traverse from highest frequency to lowest
        for (int i = 25; i >= 0; i--) {
            if (freq[i] == 0) continue;

            int cost = (index / 8) + 1;
            pushes += freq[i] * cost;
            index++;
        }

        return pushes;
    }
}
```

---

# Dry Run

### Input

```
word = "xyzxyzxyzxyz"
```

Frequency

```
x = 4
y = 4
z = 4
```

Sorted

```
4 4 4
```

Assignments

| Index | Frequency | Cost | Contribution |
| ----- | --------- | ---- | ------------ |
| 0     | 4         | 1    | 4            |
| 1     | 4         | 1    | 4            |
| 2     | 4         | 1    | 4            |

Answer

```
4+4+4=12
```

---

# Complexity Analysis

### Time Complexity

* Counting frequencies → **O(n)**
* Sorting 26 frequencies → **O(26 log 26)** ≈ **O(1)**
* Traversing frequencies → **O(26)**

Overall:

**O(n)**

where `n` is the length of the word.

---

### Space Complexity

Frequency array:

```
26 integers
```

So,

**O(1)**

---

# Key Takeaways

* Since remapping is completely flexible, only the **frequency** of each letter matters.
* Assign the **most frequent letters** to positions requiring the **fewest pushes**.
* Each group of **8 letters** shares the same push cost because there are **8 available keys (2–9)**.
* The push cost for the `i`-th most frequent letter is:

```text
(i / 8) + 1
```

This greedy strategy guarantees the minimum total number of key presses.
