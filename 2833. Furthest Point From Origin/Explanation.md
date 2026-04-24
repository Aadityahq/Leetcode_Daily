## 🧠 Problem Understanding

You start at **position 0** on a number line.

You’re given a string like:

* `'L'` → move **left (-1)**
* `'R'` → move **right (+1)**
* `'_'` → **you can choose** either left OR right

👉 Goal: **maximize distance from origin (0)** after all moves.

Distance = `|final_position|`

---

## 🔍 Key Insight (MOST IMPORTANT)

You want to go as far as possible in **one direction**.

So:

* If you're already leaning **left**, push all `_` to **left**
* If leaning **right**, push all `_` to **right**

👉 Basically:

> Use all `_` to **boost your current direction**

---

## ⚙️ Step-by-Step Logic

### Step 1: Track fixed movement

```java
int count = 0;
```

* `+1` for `'R'`
* `-1` for `'L'`

---

### Step 2: Count flexible moves

```java
int countDash = 0;
```

* `_` can go anywhere → we will use them strategically

---

### Step 3: Final Strategy

After loop:

* `count` = net movement (right or left)
* `countDash` = free moves

---

### 🧩 Cases

#### ✅ Case 1: `count == 0`

You’re at origin.

👉 Best move:

* Use all `_` in one direction

```
result = countDash
```

---

#### ✅ Case 2: `count > 0` (already right)

👉 Push all `_` to right

```
result = count + countDash
```

---

#### ✅ Case 3: `count < 0` (already left)

👉 Convert to positive distance and add `_`

```
result = abs(count) + countDash
```

---

## 🔥 Simplified Formula

You can actually reduce everything to:

```java
return Math.abs(count) + countDash;
```

👉 No need for conditions at all.

---

## 💡 Why This Works

Because:

* `_` gives you **freedom**
* Best strategy = **commit to one direction fully**

You're maximizing:

```
|final position|
```

So mixing directions (left + right) reduces distance ❌
Going all-in one side increases distance ✅

---

## 🧪 Example Walkthrough

### Input:

```
"L_RL__R"
```

### Step-by-step:

* L → -1
* R → +1
* L → -1
* R → +1

Net:

```
count = 0
countDash = 3
```

👉 Best:

```
result = 3
```

---

## 🧼 Clean Final Code

```java
class Solution {
    public int furthestDistanceFromOrigin(String moves) {

        int count = 0;
        int countDash = 0;

        for (int i = 0; i < moves.length(); i++) {
            char ch = moves.charAt(i);

            if (ch == 'L') {
                count--;
            } else if (ch == 'R') {
                count++;
            } else {
                countDash++;
            }
        }

        return Math.abs(count) + countDash;
    }
}
```

---

