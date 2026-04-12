# 🧠 Problem Intuition

You are typing a word using **2 fingers** on a keyboard grid:

```
A B C D E F
G H I J K L
M N O P Q R
S T U V W X
Y Z
```

Each letter has coordinates:

* index = `letter - 'A'`
* row = `index / 6`
* col = `index % 6`

Distance = **Manhattan distance**:
[
|x_1 - x_2| + |y_1 - y_2|
]

👉 Key twist:

* You have **two fingers**
* You can choose which finger types each letter
* Initial positions are **free (cost = 0)**

---

# 💡 Core Idea

At every step, you decide:

👉 “Which finger should press this character?”

This is a **decision-making / DP problem**.

---

# 🔁 DP State Definition

```
dp[i][j][k]
```

Means:

* You have typed first `i` characters
* Finger1 is at letter `j`
* Finger2 is at letter `k`
* Value = **minimum cost**

---

# ⚡ Transition (Main Logic)

For current character `t`:

You have **2 choices**:

---

### ✅ Option 1: Use Finger 2

Move finger2 from `k → t`

```
dp[i+1][j][t] = dp[i][j][k] + dist(k, t)
```

---

### ✅ Option 2: Use Finger 1

Move finger1 from `j → t`

```
dp[i+1][t][k] = dp[i][j][k] + dist(j, t)
```

---

# 📏 Distance Function

Your function:

```java
int cal(int a, int b) {
    return Math.abs(a / 6 - b / 6) + Math.abs(a % 6 - b % 6);
}
```

This directly implements Manhattan distance.

---

# 🧩 Important Trick (Why it works)

Initially:

```
dp[0][j][k] = 0 for all j, k
```

👉 Meaning:
Both fingers can start anywhere **without cost**

This is the key optimization.

---

# 🧮 Final Answer

After processing all characters:

```
min(dp[n][j][k]) for all j, k
```

---

# 🔍 Walkthrough Example

### Input: `"CAKE"`

Steps:

1. C → choose any finger → cost = 0
2. A → move same finger → cost = 2
3. K → use other finger → cost = 0
4. E → move second finger → cost = 1

Total = **3**

---

# 🚀 Why DP is Needed

Without DP:

* Try all combinations → **exponential (2^n)** ❌

With DP:

* States = `n × 26 × 26 ≈ 300 × 676 ≈ 2e5` ✅
* Efficient

---

# 🧠 Key Insight

You are **not tracking finger identities**, only:

👉 “Where are the two fingers right now?”

This avoids permutations and keeps DP small.

---

# ⏱ Complexity

* Time: `O(n × 26 × 26)`
* Space: `O(n × 26 × 26)`

(Optimizable to `O(26 × 26)` using rolling array)

---

# 🔥 Why This Approach is Smart

* Converts problem into **state transitions**
* Uses **symmetry of fingers**
* Exploits **free initial positions**

---

# 🧾 Summary

* At each character → choose finger
* Track positions of both fingers
* Use DP to store best cost
* Take minimum at the end

---

