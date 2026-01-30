## 🔍 Problem Understanding (Plain English)

You are given:

* `source` string → what you start with
* `target` string → what you want to end with
* Some **allowed substring transformations**:

  * `original[i] → changed[i]` at cost `cost[i]`

### What you can do

* Pick **any substring** of `source`
* Change it to another string **only if** that exact transformation exists
* Pay the given cost

### Important Rules

1. Operations must be on:

   * **Disjoint substrings**, OR
   * **Exactly the same substring range** (you can stack operations on the same range)
2. You want the **minimum total cost**
3. If conversion is impossible → return `-1`

---

## 🧠 Key Observations

### 1️⃣ Substrings behave like nodes in a graph

If you can do:

```
"c" → "e" (cost 1)
"e" → "b" (cost 2)
```

Then effectively:

```
"c" → "b" (cost 3)
```

So substring conversions can be **chained**.

👉 This means we should compute the **minimum cost between all substring pairs**
→ sounds like **shortest path** 🚀

---

### 2️⃣ We must respect substring boundaries

Because overlapping substrings are **not allowed**, we must process the string **from left to right**, carefully deciding:

* Convert 1 character
* Convert a whole substring
* Or do nothing if characters already match

👉 This is a **Dynamic Programming** problem

---

## 🧩 Solution Strategy

The solution has **3 big steps**

---

## 🧱 Step 1: Build a Graph of Substrings

* Every **unique substring** in `original` and `changed` gets an **ID**
* Create a graph where:

  * Edge: `original → changed`
  * Weight: `cost`

```java
Map<String, Integer> id = new HashMap<>();
long[][] dist = new long[201][201];
```

---

## 🔁 Step 2: Floyd–Warshall (All-Pairs Shortest Path)

Why?
Because multiple transformations can be chained:

```
a → b → c → d
```

We want the **cheapest** way to convert any substring into any other.

```java
for (int k = 0; k < sz; k++)
  for (int i = 0; i < sz; i++)
    for (int j = 0; j < sz; j++)
      dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
```

After this:

> `dist[u][v]` = minimum cost to convert substring `u` → `v`

---

## 📐 Step 3: Dynamic Programming on the String

### `dp[i]`

👉 Minimum cost to convert `source[0..i-1]` into `target[0..i-1]`

Initialize:

```java
dp[0] = 0;
```

---

### At each position `i`

#### ✅ Case 1: Characters already match

No cost needed:

```java
if (source.charAt(i) == target.charAt(i))
    dp[i + 1] = min(dp[i + 1], dp[i]);
```

---

#### 🔁 Case 2: Try converting substrings

For each possible length `L`:

* Check:

  * `source[i..i+L)`
  * `target[i..i+L)`
* If a conversion exists → apply it

```java
dp[i + L] = min(dp[i + L], dp[i] + dist[s][t]);
```

This guarantees:

* No overlapping operations
* Only valid substring replacements

---

## 🏁 Final Answer

```java
return dp[n] == INF ? -1 : dp[n];
```

* If we reached the end → return minimum cost
* Else → impossible ❌

---

## 🧪 Why Example 2 Works

```
"bcd" → "cde" (1)
"fgh" → "thh" (3)
"thh" → "ghh" (5)
```

* Disjoint substrings ✔
* Same substring updated twice ✔
* Total cost = `1 + 3 + 5 = 9`

---

## ⏱️ Time & Space Complexity

| Part           | Complexity                      |
| -------------- | ------------------------------- |
| Floyd-Warshall | `O(K³)` where `K ≤ 200`         |
| DP             | `O(N × unique_lengths)`         |
| Total          | **Fast enough for constraints** |

---

## 🧠 Final Intuition

Think of it as:

> 🔹 **Graph shortest path** for substring conversions
> 🔹 **DP on string positions** to avoid overlap

Once you see that combo, the problem clicks 💡

---
