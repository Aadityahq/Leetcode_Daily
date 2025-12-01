Here is a **clear “What, Why, How” explanation** of the problem and your solution code.

---

# ✅ **Problem Explanation**

You have:

* **n computers**
* **batteries[]**, where each battery has some minutes of charge.

You can:

* Put **one battery at a time** in each computer.
* Swap batteries **instantly** (takes 0 time).
* You want **all n computers running simultaneously** for as long as possible.

---

## **Core Idea of the Problem**

Even though each computer can use only **one battery at a time**, in total:

### ➤ We can treat all battery energy as a *global pool*.

Because we can always:

* Let a computer run until its current battery drains.
* Swap in another battery from the pool.

So the question becomes:

### ❓ How long can n computers run if they together need

`n × T` units of energy to run for `T` minutes?

This requires:

```
T ≤ (total battery energy available) / n
```

But there is one issue…

---

# ❗ Key Constraint: Largest Batteries Can Be “Too Big”

Sometimes a big battery **can’t be used evenly** to support all n computers.

Example:

```
n = 2  
batteries = [10, 1]
```

Total energy = 11
Max possible T = 11 / 2 = 5 minutes

But is 5 possible?

* One computer gets battery 10 → fine
* Other computer gets only 1 → it dies after 1 minute

Even swapping batteries cannot fix this—because the second computer simply doesn’t have enough small batteries to support continuous running.

So large batteries cause imbalance.

---

# ⭐ **Observation**

A battery with huge capacity (e.g., 10) cannot compensate if other batteries are too small.

We need to **remove such oversized batteries** from consideration until the energy can be evenly distributed across n computers.

---

# ✅ **Solution Idea**

### Step 1: Compute total battery energy

```java
long totalEnergy = 0;
for (int battery : batteries) {
    totalEnergy += battery;
}
```

### Step 2: Sort batteries

```java
Arrays.sort(batteries);
```

Why?
Because we want to process the largest batteries first to check if they are “too big”.

---

# ❗ Why remove large batteries?

For each battery (starting from the final, largest one):

Check:

```
If battery[i] > totalEnergy / n
```

This means:

* This battery alone is too powerful.
* It cannot be part of the fair distribution.
* It will prevent equal runtime for all computers.

So:

### We remove it from totalEnergy and reduce n by 1.

```java
totalEnergy -= batteries[i];
n--;
```

We continue until **all remaining batteries** are small enough to distribute fairly.

---

# 📌 Final Runtime Calculation

Once all massive batteries are removed:

```
max runtime = totalEnergy / n
```

This is now mathematically guaranteed to be achievable.

---

# 🧠 Why this works

This solution uses a deep insight:

### ✔ Maximum simultaneous runtime occurs when

all computers run for exactly the same time.

You can always schedule battery swaps to achieve this time **as long as**:

1. The total energy is enough.
2. No single battery is so large that it skews the distribution.

We remove those oversized batteries and use the rest.

This is exactly like trying to fill **n buckets** equally.
If one jug (battery) is too big, you remove it until the distribution fits.

---

# ✔ Final Return

```java
return totalEnergy / n;
```

---

# 🔚 Summary in One Line

👉 **The solution sorts batteries, removes those that prevent equal distribution, and returns the maximum fair runtime using total remaining energy divided by remaining machines.**

---
