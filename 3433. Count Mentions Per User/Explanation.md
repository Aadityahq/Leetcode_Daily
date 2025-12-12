# ⭐ **Problem Explanation (Simple & Clear)**

You are given:

* `numberOfUsers` → users numbered from `0` to `n-1`
* `events` → each event is either:

  * **MESSAGE** event → may mention users
  * **OFFLINE** event → a user goes offline for **60 time units**

All users start as **online**.

---

## 🔹 **Two Types of Events**

### **1️⃣ MESSAGE event**

Format:

```
["MESSAGE", timestamp, mentions_string]
```

`mentions_string` can contain:

| Token  | Meaning                                        |
| ------ | ---------------------------------------------- |
| `idX`  | mentions user X (can repeat, duplicates count) |
| `ALL`  | mentions **every** user, even offline users    |
| `HERE` | mentions **only currently online** users       |

Each mention counts separately.

---

### **2️⃣ OFFLINE event**

Format:

```
["OFFLINE", timestamp, userId]
```

* User becomes offline at `timestamp`
* They automatically come back online at `timestamp + 60`

---

## ⚠ Important rule

If an OFFLINE event and a MESSAGE happen at the **same timestamp**:

👉 **The OFFLINE status change must be applied first**,
THEN the MESSAGE is processed.

So event order must respect timestamp and priority.

---

## 🎯 **Goal**

Return an array `mentions[i]`:

> How many times was user `i` mentioned across all message events?

---

# ⭐ **Challenges in the Problem**

This problem looks simple, but has tricky constraints:

---

## 🔸 Challenge 1: Users can go offline and come back online automatically

We must track when a user returns online:

```
OFFLINE at time t → return at t + 60
```

We need a way to process automatic return events exactly at the right time.

---

## 🔸 Challenge 2: Order of events matters

Events must be processed in this order:

1. **Sort by timestamp ASC**
2. If timestamps are equal:

   * Process **OFFLINE** first
   * Then process **MESSAGE**

Sorting is required because input may not be ordered.

---

## 🔸 Challenge 3: The "ALL" and "HERE" rules

* `"ALL"` → mention **everyone**, even if offline
* `"HERE"` → mention only users who are **currently online**

---

# ⭐ **Solution Explanation**

We solve the problem in four major steps:

---

## 🟩 **Step 1: Convert events and sort them**

Sort events by:

1. **timestamp increasing**
2. For equal timestamp: **OFFLINE before MESSAGE**

This ensures rule correctness.

---

## 🟩 **Step 2: Track user online/offline states**

Use:

```java
boolean[] online = new boolean[numberOfUsers];
```

Initially all users are online.

---

## 🟩 **Step 3: Track automatic return to online**

Use a min-heap (priority queue):

```java
PriorityQueue<int[]> pq;
```

Each entry:

```
{returnTime, userId}
```

Before processing each event:

✔ Check if any user should come online
✔ If so, mark them online

---

## 🟩 **Step 4: Process events**

### ✔ OFFLINE event

* Mark user offline
* Add `(timestamp + 60, user)` to the min-heap

### ✔ MESSAGE event

Based on `mentions_string`:

#### **Case A — "ALL"**

Mention every user once:

```
mentions[i]++
```

#### **Case B — "HERE"**

Mention all **currently online** users.

#### **Case C — explicit ids ("id1 id0 id1")**

Parse tokens and increment the corresponding user mention counters.

Duplicates count separately.

---

# ⭐ **Why this solution is correct**

✔ Correctly handles automatic online return
✔ Ensures that status changes happen before messages at same timestamp
✔ Correctly applies rules for ALL and HERE
✔ Counts duplicate id mentions
✔ Efficient — constraints are small (100 events × 100 users)

This is exactly why this approach passes all LeetCode tests.

---

