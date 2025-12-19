## 🔍 Problem Understanding (What is being asked?)

You are given:

* `prices[i]` → stock price on day `i`
* `strategy[i]` → action on day `i`

  * `-1` → buy → profit = `-prices[i]`
  * `0` → hold → profit = `0`
  * `1` → sell → profit = `+prices[i]`

👉 **Total profit** =
[
\sum strategy[i] \times prices[i]
]

You are allowed to make **at most one modification**:

* Pick **exactly `k` consecutive days**
* First `k/2` days → set to **hold (0)**
* Last `k/2` days → set to **sell (1)**

Your task: **maximize total profit** after at most one such modification.

⚠️ Important simplification:

> There are **no constraints on money or stock ownership**, so we don’t care about “invalid” buy/sell sequences.

---

## 💡 Key Insight (Why this approach?)

* The **original profit** is easy to compute.
* A modification **only affects one subarray of length `k`**.
* Instead of recomputing profit every time (which would be slow), we:

  * Calculate how much **extra gain** a modification gives
  * Add the **maximum possible gain** to the original profit

So the problem becomes:

> “For every subarray of size `k`, how much extra profit do I gain if I apply the modification here?”

---

## 🧠 Strategy Breakdown (How the solution works)

### Step 1: Prefix sums for fast calculations

```java
prefixProfit[i] = total profit from day 0 to day i-1
prefixPrice[i]  = total prices from day 0 to day i-1
```

Why?

* To calculate profit or price sum of any subarray in **O(1)** time.

---

### Step 2: Original total profit

```java
prefixProfit[n]
```

This is the profit **without any modification**.

---

### Step 3: Evaluate every possible modification window

For each subarray `[i, i+k-1]`:

#### 🔴 Old Gain (before modification)

```java
oldGain = prefixProfit[i+k] - prefixProfit[i];
```

This is the profit currently contributed by those `k` days.

---

#### 🟢 New Gain (after modification)

After modification:

* First `k/2` days → `0 × price = 0`
* Last `k/2` days → `1 × price = price`

So new gain = sum of prices in the **second half**:

```java
newGain = prefixPrice[i+k] - prefixPrice[i+k/2];
```

---

#### 📈 Extra Gain from this modification

```java
gain = newGain - oldGain;
```

We keep track of the **maximum gain** possible.

---

### Step 4: Final Answer

```java
return originalProfit + maxGain;
```

If no modification helps, `maxGain = 0`, so we keep the original profit.

---

## ✅ Why this solution is efficient

* Prefix sums → **O(n)** preprocessing
* Sliding window over `n` days → **O(n)**
* Total Time: **O(n)**
* Space: **O(n)**

This easily fits within constraints (`n ≤ 10^5`).

---

## 🧪 Example 1 Recap

```text
prices   = [4, 2, 8]
strategy = [-1, 0, 1]
k = 2
```

Original profit:

```
(-1×4) + (0×2) + (1×8) = 4
```

Modify `[0,1]`:

```
[0,1,1] → (0×4) + (1×2) + (1×8) = 10
```

Extra gain = `+6` → maximum

Final answer = `4 + 6 = 10`

---

