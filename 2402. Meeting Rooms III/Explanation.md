## 🧠 Problem Explanation (in simple terms)

You are given:

* **`n` meeting rooms** numbered `0` to `n-1`
* A list of **meetings**, each with a **start time** and **end time**
* Meetings use **half-open intervals** `[start, end)` → start included, end excluded

### Rules for assigning meetings:

1. A meeting always goes to the **lowest-numbered free room**
2. If **no room is free**, the meeting is **delayed**

   * It keeps the **same duration**
   * It starts when **any room becomes free**
3. If multiple meetings are waiting, the one with the **earliest original start time** gets priority
4. After all meetings are scheduled, return the **room that hosted the most meetings**

   * If there’s a tie, return the **smallest room number**

---

## 🧩 Key Idea Behind the Solution

We need to **simulate meeting scheduling over time**, respecting all rules.

To do this efficiently, we use **two priority queues (min-heaps)**:

---

## 🏗️ Data Structures Used

### 1️⃣ `freeRoom` (Min-Heap of room numbers)

* Keeps track of **available rooms**
* Always gives the **lowest-numbered free room**
* Example: `[0, 1, 2]`

### 2️⃣ `used` (Min-Heap of `[endTime, roomNumber]`)

* Keeps track of **rooms currently in use**
* Sorted by:

  1. Earliest `endTime`
  2. Lowest `roomNumber` (tie-breaker)
* Helps us know **which room becomes free next**

### 3️⃣ `count[]`

* `count[i]` = number of meetings held in room `i`

---

## 🔁 Step-by-Step Logic (How the Algorithm Works)

### Step 1: Sort meetings by start time

```java
Arrays.sort(meetings, (a, b) -> Integer.compare(a[0], b[0]));
```

Why?
➡ Meetings must be processed in **chronological order**

---

### Step 2: Initialize rooms

```java
PriorityQueue<Integer> freeRoom = new PriorityQueue<>();
for (int i = 0; i < n; i++) freeRoom.offer(i);
```

➡ At the beginning, **all rooms are free**

---

### Step 3: Process each meeting

For each meeting `[start, end]`:

#### ✅ A. Free rooms that have finished before `start`

```java
while (!used.isEmpty() && used.peek()[0] <= start) {
    int room = (int) used.poll()[1];
    freeRoom.offer(room);
}
```

Why?
➡ Rooms whose meetings ended **before or at `start`** are now available

---

#### ✅ B. Calculate meeting duration

```java
long dur = end - start;
```

Why?
➡ Needed in case the meeting is **delayed**

---

#### ✅ C. Assign a room

##### Case 1: A room is free

```java
room = freeRoom.poll();
begin = start;
```

➡ Meeting starts **on time**

##### Case 2: No room is free → delay meeting

```java
long[] earliest = used.poll();
room = (int) earliest[1];
begin = earliest[0];
```

➡ Meeting starts **when the earliest room gets free**

---

#### ✅ D. Update counts and mark room as used

```java
count[room]++;
used.offer(new long[]{begin + dur, room});
```

➡ Room is busy until `begin + duration`

---

### Step 4: Find the most-used room

```java
int ans = 0;
for (int i = 0; i < n; i++) {
    if (count[i] > count[ans]) ans = i;
}
```

➡ If there’s a tie, smaller index wins automatically

---

## 🧪 Example Intuition (Example 1)

```
n = 2
meetings = [[0,10],[1,5],[2,7],[3,4]]
```

* Meetings at `2` and `3` get **delayed**
* Room usage:

  * Room 0 → 2 meetings
  * Room 1 → 2 meetings
    ➡ Return **room 0** (smallest index)

---

## ⏱️ Time & Space Complexity

### Time Complexity

* Sorting meetings: `O(m log m)`
* Each meeting uses heap ops: `O(log n)`
* Total: **`O(m log n)`**

### Space Complexity

* Heaps + count array: **`O(n)`**

---

## ✅ Why This Solution Is Correct

* Uses **min-heaps** to always:

  * Pick the **earliest free room**
  * Pick the **lowest room number**
* Accurately simulates **delays**
* Efficient enough for `10^5` meetings
* Matches all problem constraints and rules

---
