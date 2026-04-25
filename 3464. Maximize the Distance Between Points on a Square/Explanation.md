# 🧠 Problem Understanding (Intuition First)

You are given:

* A **square boundary**
* Some **points lying only on the boundary**
* You must pick **k points**

👉 Goal:
Maximize the **minimum Manhattan distance** between *any pair* of chosen points.

---

## 🔥 Key Observation

All points lie on the **boundary of the square**, not inside.

That means instead of thinking in 2D…

👉 You can **convert the square boundary into a 1D circular perimeter**

---

## 🧭 Mapping 2D → 1D (VERY IMPORTANT)

We walk around the square **clockwise** and assign positions.

![Image](https://images.openai.com/static-rsc-4/P49aKT-VRL9IZN_m0RPOZEnVqYbbM7lixPJwsUufOqBgb38eEs3dlRFAlQ-NH-Jm6oUwr82D-yg8jXKs0AvSE0_pcv5nW-DAO8XGZ9fV-ddkgDKWlMsQVqdxh_D3GvwQsNT65XSFb7kGQ0oSsodlXJg65BboC_3SY-VP-nMB-MEvd4zTLwmMfbyPWEW6Pz8Q?purpose=fullsize)

![Image](https://images.openai.com/static-rsc-4/c7BDVIJ1qVWxDG4hVhnLe6qJomuWhgVsjt_xZDM9DtgqALh9hNYIOVbo7W4NJg6BDRewUX41xx8gZplVB2VDxLRwjjkJQuwzKuUaFjOnxXIPaWkMDTqoE9IsntmjIyQi0ucePKly26wWasQ2jA5pRSHRjwDSwZAApz8IWQ1cecjdmshBghvEzuUGEpDJu_jZ?purpose=fullsize)

![Image](https://images.openai.com/static-rsc-4/w59V-zPCGozswe9BTnAkdVG0O_AU3fPyPGqx0YVYt-Nv3bX6bvGWpNj5bbetXJniXiewEGvsa5wImGfw6D3B5pYb8BHZL9kNx9JBVb1UQLBPayttMN6ADK6fa4XfOlBl7QHyfRophNztgjU70H34uPL9KvxEa5HhMFNHOnuMx8cV3OLI8HpKZ7-k364RVq4r?purpose=fullsize)

![Image](https://images.openai.com/static-rsc-4/0YiK4Q68mP-jMUxFH0p5_hXK4doh4dpxpw5ywH-ux4hiAEzO_OjLfLHH2vvQ4Vezy5hJzWgZoH8Nr75MwPsG0QySrO3j1Qq7sb6HzA8NU6o2QHszZ8TlbdstlZ5xVA83jYqMbz4RhXH0ORNxNRfLs0PKLoIjhJWpgnXdWCvpAPGoJwpqM9Y_S1XU_VFdCDHv?purpose=fullsize)

![Image](https://images.openai.com/static-rsc-4/eSGywL-_DV9mtMpiCsEblAP4KzT1l0ht4M7Fp4yJIdKVOsRLyCVzWOtZeRIFOH0VOtY-Pxivcze-rzSx74_-kM1RhqZMovysc9u14qEdw26ZDPKjd0YqtMMlTByMiGppS2yHazzagO1bKYw4HzLtRCsV6ysY_zL_vRTPtUCf_PgiwxU5Zm7DGEQm2aBgGW2o?purpose=fullsize)

For a square of side = `side`, perimeter = `4 * side`.

We map each point like this:

| Edge        | Condition | Mapping    |
| ----------- | --------- | ---------- |
| Left edge   | x = 0     | y          |
| Top edge    | y = side  | side + x   |
| Right edge  | x = side  | 3*side - y |
| Bottom edge | y = 0     | 4*side - x |

👉 Now every point becomes a **number on [0, 4*side]**

---

## 🤯 Why This Works

Because:

* Manhattan distance along edges behaves like **distance along perimeter**
* Now the problem becomes:

> Pick k points on a **circular line** such that minimum pairwise distance is maximized

---

# ⚡ Core Idea

👉 This is a **"maximize minimum distance"** problem

So we use:

### ✅ Binary Search on Answer

---

## 🔍 Binary Search Setup

We search for:

```
minimum distance = d
```

Range:

* `left = 1`
* `right = perimeter / k`

---

## 💡 Why perimeter / k?

Because:

* If you place k points evenly → max spacing = perimeter / k

---

# 🧪 Check Function (Greedy)

Now the important part:

👉 Can we pick k points such that each is at least distance `d` apart?

---

## 🧠 Greedy Strategy

1. Pick first point
2. Always pick the **next valid point ≥ current + d**
3. Repeat until k points selected

Code uses:

```java
Arrays.binarySearch(res, curr + n);
```

👉 Efficiently jumps to next valid point

---

## ⚠️ Circular Condition

This is NOT a line — it's a **circle**

So we must ensure:

```
last_point - first_point ≤ perimeter - d
```

👉 Why?

Because:

* Distance between last and first (wrapping around) must also ≥ d

---

## 🔁 Why Multiple Starting Points?

The first greedy attempt assumes starting at index 0.

But optimal solution might start elsewhere.

So we try:

```java
for (idx[0] = 1; idx[0] < idx[1]; idx[0]++)
```

👉 Shift starting point and retry

---

# 🧩 Full Flow

### Step 1: Convert points → 1D perimeter

```java
res[i] = ...
```

### Step 2: Sort

```java
Arrays.sort(res);
```

### Step 3: Binary Search

```java
while (left + 1 < right)
```

### Step 4: Check feasibility

```java
check(mid, ...)
```

---

# ⚙️ Complexity

* Sorting: `O(n log n)`
* Binary search: `log(side)`
* Each check: `O(k log n)`

👉 Overall:

```
O(n log n + log(side) * k log n)
```

Efficient for constraints.

---

# 🎯 Key Insights (VERY IMPORTANT)

### 💡 1. Convert geometry → linear problem

This is the **main trick**

---

### 💡 2. Binary search on answer

Classic pattern:

> "maximize minimum"

---

### 💡 3. Greedy placement

Always pick next valid point

---

### 💡 4. Handle circular wrap carefully

Most people miss this 👇

```
perimeter - d constraint
```

---

# 🧠 Mental Model

Imagine:

* You have a **circular track**
* You want to place **k people**
* Each must be at least distance `d` apart

👉 Can you do it?

That’s exactly this problem.

---

