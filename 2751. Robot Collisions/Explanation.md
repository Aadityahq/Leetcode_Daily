# 🧠 Problem Understanding (Intuition)

You have robots on a line:

* Each robot has:

  * `position`
  * `health`
  * `direction` → `'L'` or `'R'`

👉 All robots move **at the same speed**

---

## 💥 When do collisions happen?

Only when:

* A robot moving **Right (`R`)**
* Meets a robot moving **Left (`L`)**

👉 Because they move toward each other

---

## ⚔️ Collision Rules

When two robots collide:

| Case                   | Result                   |
| ---------------------- | ------------------------ |
| `health A > health B`  | B dies, A health = A - 1 |
| `health A < health B`  | A dies, B health = B - 1 |
| `health A == health B` | Both die                 |

---

# 🚨 Key Observations (VERY IMPORTANT)

### 1. Order matters (positions are UNSORTED)

Robots collide based on position, not index.

👉 So we must **sort robots by position**

---

### 2. Only R vs L collisions matter

* `R → R` ❌ no collision
* `L → L` ❌ no collision
* `L → R` ❌ moving away
* `R → L` ✅ collision

---

### 3. This becomes a **STACK problem**

Why?

Because:

* Right-moving robots are “waiting” to collide
* Left-moving robots collide with the **closest previous R**

👉 That’s exactly **LIFO (stack behavior)**

---

# ⚙️ Step-by-Step Code Explanation

---

## ✅ Step 1: Store indices & sort by position

```java
Integer[] order = new Integer[n];
for(int i=0;i<n;i++) order[i]=i;

Arrays.sort(order,(a,b)->pos[a]-pos[b]);
```

👉 We don’t change arrays, just process indices in sorted order

---

## ✅ Step 2: Track alive robots

```java
boolean[] alive = new boolean[n];
Arrays.fill(alive,true);
```

---

## ✅ Step 3: Stack for Right-moving robots

```java
Deque<Integer> st = new ArrayDeque<>();
```

👉 Stack stores indices of robots moving **Right**

---

# 🔁 Main Logic

```java
for(int idx:order)
```

We process robots **left → right**

---

## 👉 Case 1: Robot moving RIGHT

```java
if(d.charAt(idx)=='R') st.push(idx);
```

👉 Just store it → may collide later

---

## 👉 Case 2: Robot moving LEFT

```java
else{
    while(!st.isEmpty()){
```

👉 Now collision happens with previous R robots

---

# ⚔️ Collision Handling (CORE LOGIC)

Let:

* `top = st.peek()` → previous R robot
* `idx` → current L robot

---

## 🟥 Case 1: R robot weaker

```java
if(h[top] < h[idx]){
    alive[top]=false;
    st.pop();
    h[idx]--;
}
```

### WHY?

* L robot wins
* R robot dies
* L loses 1 health
* Continue → may hit more robots

---

## 🟩 Case 2: R robot stronger

```java
else if(h[top] > h[idx]){
    alive[idx]=false;
    h[top]--;
    break;
}
```

### WHY?

* R robot survives
* L robot dies
* R loses 1 health
* Stop (L is gone)

---

## 🟨 Case 3: Equal health

```java
else{
    alive[top]=false;
    alive[idx]=false;
    st.pop();
    break;
}
```

### WHY?

* Both die
* No further collisions

---

# 📦 Final Step: Collect survivors

```java
for(int i=0;i<n;i++)
    if(alive[i]) res.add(h[i]);
```

👉 Return in original order

---

# 🔥 WHY THIS APPROACH WORKS

## ✔️ Sorting ensures correct collision order

Robots interact based on position, not input order.

---

## ✔️ Stack ensures nearest collision first

The closest previous `R` robot collides first.

---

## ✔️ While loop handles chain collisions

Example:

```
R R R L
```

👉 L can collide with multiple R robots → handled by loop

---

# ⏱️ Complexity

| Operation  | Time           |
| ---------- | -------------- |
| Sorting    | O(n log n)     |
| Processing | O(n)           |
| Total      | **O(n log n)** |

---

# 🧠 Intuition Summary (Interview Line)

👉

> “We sort robots by position and simulate collisions using a stack.
> Right-moving robots are stored, and when a left-moving robot appears, we resolve collisions greedily with the nearest right-moving robot until no collision remains.”

---

# 💡 Quick Visualization

Example:

```
Positions: 1   3   5   6
Dirs:      R   L   R   L
```

Process:

```
R → push
L → collide with R
R → push
L → collide with R
```

---

# 🚀 Final Takeaway

This is a **classic pattern**:

* Sort + Stack + Simulation

👉 Similar to:

* Asteroid Collision problem
* Next Greater Element logic

---