## ✅ 696. Count Binary Substrings

### 🔎 Problem Understanding (Very Important)

We are given a binary string (`0` and `1` only).

We must count **non-empty substrings** that:

1. Have **equal number of 0's and 1's**
2. All 0's are grouped together
3. All 1's are grouped together

That means valid substrings look like:

* `"0011"` ✅
* `"01"` ✅
* `"1100"` ✅
* `"10"` ✅

But NOT like:

* `"0101"` ❌ (because 0’s and 1’s are not grouped consecutively)

---

## 🧠 Key Observation (Core Logic)

Instead of checking all substrings (which would be O(n²)), we use a **pattern observation**.

### 👉 Idea:

Binary strings form **groups of consecutive characters**.

Example:

```
s = "00110011"

Groups:
"00" → length 2  
"11" → length 2  
"00" → length 2  
"11" → length 2
```

So group sizes = `[2, 2, 2, 2]`

---

### ⭐ Important Insight:

For every **adjacent pair of groups**,
we can form:

```
min(size_of_group1, size_of_group2)
```

valid substrings.

Why?

Because valid substrings must:

* Take equal number of 0's and 1's
* And they must come from two consecutive groups

---

### 📌 Example 1

```
s = "00110011"
Groups = [2,2,2,2]
```

Now count:

```
min(2,2) = 2
min(2,2) = 2
min(2,2) = 2
```

Total = 2 + 2 + 2 = 6 ✅

---

### 📌 Example 2

```
s = "10101"

Groups:
"1" → 1
"0" → 1
"1" → 1
"0" → 1
"1" → 1

Group sizes = [1,1,1,1,1]
```

Now:

```
min(1,1) = 1
min(1,1) = 1
min(1,1) = 1
min(1,1) = 1
```

Total = 4 ✅

---

# 🚀 Efficient Approach (O(n) Time, O(1) Space)

We don’t even need an array.

We only track:

* `prevGroup` → previous group size
* `currGroup` → current group size
* `count` → answer

---

## 💻 Java Solution

```java
class Solution {
    public int countBinarySubstrings(String s) {
        int prevGroup = 0;
        int currGroup = 1;
        int count = 0;

        for (int i = 1; i < s.length(); i++) {
            
            if (s.charAt(i) == s.charAt(i - 1)) {
                currGroup++;  // same group continues
            } else {
                // group changes
                count += Math.min(prevGroup, currGroup);
                prevGroup = currGroup;
                currGroup = 1;
            }
        }

        // Add last comparison
        count += Math.min(prevGroup, currGroup);

        return count;
    }
}
```

---

# 🧠 Why This Works

Whenever a group changes:

```
000111
```

Let:

* 0-group size = 3
* 1-group size = 3

We can form:

```
"01"
"0011"
"000111"
```

Which equals:

```
min(3,3) = 3
```

Because we can match 1-1, 2-2, 3-3 characters.

---

# ⏱ Time & Space Complexity

| Complexity | Value |
| ---------- | ----- |
| Time       | O(n)  |
| Space      | O(1)  |

We scan once.
We use only variables — no extra array.

---

