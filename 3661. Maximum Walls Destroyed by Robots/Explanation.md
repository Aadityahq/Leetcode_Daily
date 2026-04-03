# 🧠 Problem Understanding (Intuition First)

You have:

* Robots on a number line
* Each robot can shoot **left OR right (only once)**
* Each bullet:

  * Travels up to `distance[i]`
  * **Stops if it hits another robot**
  * Destroys all walls in its path

### 🎯 Goal:

Maximize **unique walls destroyed**

---

# 🚨 Key Observations (VERY IMPORTANT)

### 1. Robots block bullets

If a robot shoots right → it **cannot cross the next robot**

👉 So each robot’s shooting range is **bounded by neighbors**

---

### 2. Effective shooting ranges

For robot at index `i`:

#### 👉 Shooting LEFT:

```
[max(pos - dist, prevRobot + 1), pos]
```

#### 👉 Shooting RIGHT:

```
[pos, min(pos + dist, nextRobot - 1)]
```

---

### 3. Overlap Problem

Two robots can destroy the **same wall**

❗ But we must count each wall **only once**

👉 This is where DP + overlap removal comes in.

---

# 💡 Core Idea of Solution

We process robots **from left → right** and decide:

> For each robot → shoot LEFT or RIGHT

---

# 🧩 DP Definition

```
dp[i][0] = max walls till i if robot i shoots LEFT
dp[i][1] = max walls till i if robot i shoots RIGHT
```

---

# ⚙️ Preprocessing

### 1. Sort robots by position

```java
Arrays.sort(arr)
```

### 2. Sort walls

```java
Arrays.sort(walls)
```

### 3. Add dummy robot at end

```java
arr[n] = (1e9, 0)
```

👉 Helps avoid boundary checks

---

# 🔍 Counting walls in range

You used binary search:

```
countWalls(left, right)
```

👉 Counts how many walls exist in `[left, right]`

Using:

* `lowerBound`
* `upperBound`

⏱ Time: `O(log n)`

---

# 🚀 DP Transitions (Main Logic)

---

## ✅ Case 1: Robot shoots RIGHT

```java
dp[i][1] = max(dp[i-1][0], dp[i-1][1]) + rightWalls
```

### 🤔 Why?

* Shooting right does NOT interfere with previous robot
* So take best previous result

---

## ✅ Case 2: Robot shoots LEFT

### Option A: Previous robot also shot LEFT

```java
dp[i][0] = dp[i-1][0] + leftWalls
```

👉 No overlap → safe

---

### Option B: Previous robot shot RIGHT

⚠️ Now overlap can happen

```java
dp[i][0] = dp[i-1][1] + leftWalls - overlapWalls
```

---

### 🤯 Why subtract overlap?

Because:

* Previous robot (RIGHT) may have already destroyed some walls
* Current robot (LEFT) might destroy them again

👉 We must **avoid double counting**

---

### 🔍 Overlap calculation

```
overlap = intersection of:
    current LEFT range
    previous RIGHT range
```

---

# 📌 Final Answer

```java
max(dp[n-1][0], dp[n-1][1])
```

---

# 🧠 Why This Approach Works

### ✅ Greedy won't work

Because:

* Local best (shooting one direction) may block better global result

---

### ✅ DP works because:

* Each robot has only **2 choices**
* Decisions depend only on **previous robot**

👉 Classic **linear DP with state dependency**

---

# ⏱ Complexity

### Time:

* Sorting: `O(n log n)`
* Each robot:

  * Binary search → `O(log m)`

👉 Total:

```
O(n log n + n log m)
```

---

### Space:

```
O(n)
```

---

# 🔥 Interview Summary (How to Say)

👉 Start with:

> “Each robot can shoot left or right, but is blocked by other robots, so I first restrict each robot’s valid shooting range.”

👉 Then:

> “I use DP where dp[i][0/1] represents max walls destroyed if robot i shoots left/right.”

👉 Then:

> “For right, no overlap issue. For left, I handle overlap with previous right using subtraction.”

👉 End with:

> “Walls are counted efficiently using binary search over sorted walls.”

---

# 💯 Key Insight

👉 The hardest part:
**Handling overlap when switching directions (RIGHT → LEFT)**

---
