# 🧪 LeetCode 3494 – Find the Minimum Amount of Time to Brew Potions

**Difficulty:** Medium  
**Date Solved:** 09 Oct 2025  
**Language:** Java  

---

## 🧩 Problem Description

You are given two integer arrays:

- `skill` – the skill level of each wizard  
- `mana` – the mana required for each potion  

There are `n` wizards and `m` potions.  
Each potion must pass **sequentially** through all wizards.  

The time taken by wizard `i` on potion `j` is:

```

time[i][j] = skill[i] * mana[j]

````

### Constraints

1. A wizard can only work on one potion at a time.  
2. A potion can only be with one wizard at a time.  
3. Once a wizard finishes a potion, it is passed immediately to the next wizard.

Return the **minimum total time** required to brew all potions properly.

---

## 💡 Intuition and Why

This is a **logical scheduling problem** or **flow-shop simulation**:

- Each wizard = stage in a pipeline  
- Each potion = task flowing through the pipeline  

Key observations:

1. Each wizard cannot start a potion before:
   - The previous wizard finishes it (left constraint)  
   - He finishes his previous potion (right constraint)  

2. The processing time for a potion is `skill[i] * mana[j]`, which represents **wizard efficiency × potion difficulty**.

---

## 🧠 Approach (The How)

We use an array `f[i]` to store **the earliest time when wizard i will be free**.

### Step 1: Initialization

```java
long[] f = new long[n]; // all initialized to 0
````

All wizards are free at the beginning.

---

### Step 2: Process Each Potion

For each potion `j` with mana `x = mana[j]`:

#### 2.1 Forward Pass

```java
long current = f[0];
for (int i = 1; i < n; i++) {
    current = Math.max(current + (long) skill[i - 1] * x, f[i]);
}
```

* `current + skill[i-1]*x` → when the previous wizard finishes the potion
* `f[i]` → when the current wizard is free
* `Math.max(...)` ensures the wizard starts as early as possible respecting both constraints

#### 2.2 Finish Last Wizard

```java
f[n - 1] = current + (long) skill[n - 1] * x;
```

* The last wizard finishes the potion at this time

#### 2.3 Backward Pass

```java
for (int i = n - 2; i >= 0; i--) {
    f[i] = f[i + 1] - (long) skill[i + 1] * x;
}
```

* Ensures each wizard starts the potion so the next wizard can begin immediately after finishing
* Maintains pipeline synchronization

---

### Step 3: Result

* After processing all potions, `f[n-1]` → the time when the last wizard finishes the last potion
* This is the **minimum total time**.

---

## 🧮 Step-by-Step Example

**Input:**

```java
skill = [1, 5, 2, 4]
mana = [5, 1, 4, 2]
```

**Potion 0 (x = 5)**

* Forward Pass:

  * Wizard 0 → starts at 0, finishes at 0 + 1*5 = 5
  * Wizard 1 → max(5,0) + 5*5 = 30
  * Wizard 2 → max(30,0) + 2*5 = 40
* Last wizard finishes: f[3] = 40 + 4*5 = 60
* Backward Pass:

  * f[2] = 60 - 4*5 = 40
  * f[1] = 40 - 2*5 = 30
  * f[0] = 30 - 5*5 = 5

**Resulting f array:** `[5, 30, 40, 60]`
**Last wizard finishes:** `f[3] = 60`

Repeat for all potions → **final answer = 110**

---

## ⏱️ Complexity Analysis

| Type  | Complexity |
| ----- | ---------- |
| Time  | O(n × m)   |
| Space | O(n)       |

---

## ✅ Key Insights

* Track wizard availability with array `f[i]`
* Forward pass ensures no wizard starts before previous wizard finishes
* Backward pass synchronizes start times so next wizard is ready immediately
* Multiply `skill[i] * mana[j]` → time required by wizard i on potion j
* Return `f[n-1]` → minimum total time

---

## 🧑‍💻 Java Code

```java
class Solution {
    public long minTime(int[] skill, int[] mana) {
        int n = skill.length;
        int m = mana.length;

        long[] f = new long[n];
        
        for (int j = 0; j < m; j++) {
            long x = mana[j];
            long current = f[0]; 
            
            for (int i = 1; i < n; i++) {
                current = Math.max(current + (long) skill[i - 1] * x, f[i]);
            }
            
            f[n - 1] = current + (long) skill[n - 1] * x;
            
            for (int i = n - 2; i >= 0; i--) {
                f[i] = f[i + 1] - (long) skill[i + 1] * x;
            }
        }
        
        return f[n - 1];
    }
}
```

**Result:** Returns the minimum total time required for all potions to pass through all wizards sequentially.


