# 📘 **Explanation of the Greedy Solution

(757. Set Intersection Size At Least Two)**

We are given intervals, and we want to build the **smallest possible set `nums`** such that:

➡️ **Every interval contains at least 2 numbers from `nums`.**

This is a classic **greedy with sorting** problem.

---

# ✅ **Key Insight**

For each interval, we want to use numbers that are:

* **as late as possible** (so they help future intervals too)
* **shared by as many intervals as possible**

Thus we use the greedy strategy:

1. Sort intervals by:

   * **end increasing**, and if tie,
   * **start decreasing**

   ```java
   Arrays.sort(intervals, (a, b) -> 
       a[1] == b[1] ? b[0] - a[0] : a[1] - b[1]
   );
   ```

2. As we process intervals from left to right (by end):

   * We maintain two largest numbers chosen so far:
     `a` < `b`
   * These are currently included in our `nums`.

3. For each interval:

   * If it already has both `a` and `b` inside → do nothing
   * If it has only one of them → add one more number
   * If it has none → add the last two numbers of the interval

---

# 🧠 **Meaning of variables**

| Variable | Meaning                                                    |
| -------- | ---------------------------------------------------------- |
| `a`      | 2nd largest element we selected for the previous intervals |
| `b`      | largest element we selected                                |
| `ans`    | size of our final containing set                           |

Initially:

```java
int a = -1, b = -1;
```

---

# 🔍 **Case Analysis**

Let each interval be `[l, r]`.

### **Case 1: `l > b`**

➡️ The current interval **does not contain either `a` or `b`**
So the interval has **0 required numbers**, so we must add **2 numbers**.

We pick:
**r-1 and r** (the last two numbers of the interval)

Why this choice?
Because choosing the latest possible numbers helps future intervals.

Update:

```java
a = r - 1;
b = r;
ans += 2;
```

---

### **Case 2: `l > a`**

➡️ The current interval contains `b` but **not** `a`
So the interval has **only 1 number** included; we must add **1 more**.

We add **r**.

Update:

```java
a = b;
b = r;
ans += 1;
```

---

### **Case 3: Else**

➡️ The interval contains both `a` and `b`
We do nothing.

---

# 📌 **Final Java Code (Given)**

```java
class Solution {
    public int intersectionSizeTwo(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> 
            a[1] == b[1] ? b[0] - a[0] : a[1] - b[1]
        );

        int ans = 0;
        int a = -1, b = -1;

        for (int[] it : intervals) {
            int l = it[0], r = it[1];

            if (l > b) {
                a = r - 1;
                b = r;
                ans += 2;
            } else if (l > a) {
                a = b;
                b = r;
                ans += 1;
            }
        }

        return ans;
    }
}
```

---

# 📄 **Why does this greedy strategy work?**

### ✔️ Choosing numbers at the **end** of the interval

Maximizes the chance they appear in future intervals → reduces future additions.

### ✔️ Sorting by end ensures:

* When we process an interval, we have already made the best choices for earlier intervals.
* The earlier intervals "restrict" our choices the most.

### ✔️ Always maintaining exactly **two** relevant numbers (a, b)

Allows checking each interval in **O(1)** time.

### ✔️ Never picking unnecessary numbers

Greedy is optimal because:

* If we picked numbers earlier inside the interval, they would be less useful for future intervals.
* Choosing latest points minimizes conflicts.

---

# 🟦 **Example Walkthrough (Simple)**

### Input:

```
intervals = [[1,3], [3,7], [8,9]]
```

After sorting:

```
[1,3], [3,7], [8,9]
```

### Step-by-step:

#### Interval [1,3]:

does not contain anything → add {2,3}
a=2, b=3

#### Interval [3,7]:

contains b=3 only → add r=7
Now a=3, b=7

#### Interval [8,9]:

contains nothing → add {8,9}

Total = **5**

---

