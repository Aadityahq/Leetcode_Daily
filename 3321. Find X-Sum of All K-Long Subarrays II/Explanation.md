
## 🧩 Problem Understanding

You are given:

* An array `nums` (length up to `10⁵`)
* Two integers `k` and `x`

For every **subarray of length `k`**, you must compute its **x-sum**:

### X-sum definition (same as Problem I):

1. Count occurrences (frequencies) of each element in the subarray.
2. Keep only the **top `x` most frequent** elements.

   * If two elements have the same frequency → the **larger value** is considered more frequent.
3. Sum all occurrences of those chosen elements.

If there are **fewer than `x` distinct elements**, take the **sum of all elements** in that subarray.

---

## ⚙️ The Challenge

In **Problem I**, we could simply count frequencies from scratch for each subarray — O(n × k).
But here, `n` can be up to `10⁵`, and `k` can be large.

👉 So we need a **faster sliding window approach** that can:

* Add a new element (right side)
* Remove an old element (left side)
* Quickly know the **x most frequent elements** and their sum.

We must maintain these efficiently as the window slides.

---

## 💡 Core Idea

Use **two balanced sets (`TreeSet`s)**:

1. `top` — contains the **top `x` most frequent** elements.
2. `rest` — contains all **other elements** not in the top `x`.

Both are sorted using a **custom comparator** that orders pairs by:

* Higher frequency first
* If same frequency → larger value first

So the **first elements** in order are the “most frequent and largest”.

We also keep:

* A map `cnt` → value → frequency
* A variable `topSum` → the current x-sum (sum of all f × v in `top`)

---

## 🧱 Data Structures Used

```java
Map<Integer,Integer> cnt;    // value -> frequency
TreeSet<Pair> top, rest;     // track top x and remaining elements
long topSum;                 // total x-sum of current window
```

`Pair(f, v)` means (frequency, value).

---

## 🔍 Comparator Logic

```java
private static final Comparator<Pair> DESC = (a, b) -> {
    if (a.f != b.f) return Integer.compare(b.f, a.f);  // higher freq first
    return Integer.compare(b.v, a.v);                  // tie -> larger value first
};
```

This ensures `TreeSet` is sorted from most to least frequent, breaking ties by larger value.

---

## 🧩 Helper Methods Explained

### 1️⃣ `pull(int v, int f)`

Removes a pair `(f, v)` either from `top` or `rest`.

If removed from `top`, we must also **deduct its contribution from `topSum`**.

```java
private void pull(int v, int f){
    Pair key = new Pair(f, v);
    if (top.remove(key)) {
        topSum -= 1L * f * v;
    } else {
        rest.remove(key);
    }
}
```

**Why:**
When frequency changes (due to insert or erase), we must remove the old pair before re-inserting with the new frequency.

---

### 2️⃣ `pushToTop(int v, int f)`

Adds `(f, v)` into `top` and increases `topSum` accordingly.

```java
private void pushToTop(int v, int f){
    top.add(new Pair(f, v));
    topSum += 1L * f * v;
}
```

---

### 3️⃣ `insertVal(int v, int x)`

When adding a new element into the window:

1. Find its old frequency (if any).
2. Remove its old `(freq, value)` pair.
3. Increment frequency and insert the new one into `top`.
4. If `top` exceeds size `x`, move the **least frequent** element from `top` to `rest`.

```java
private void insertVal(int v, int x){
    int f = cnt.getOrDefault(v, 0);
    if (f > 0) pull(v, f);  // remove old entry
    f += 1;
    cnt.put(v, f);
    pushToTop(v, f);
    if (top.size() > x){
        Pair worst = top.last();   // smallest in top
        top.remove(worst);
        topSum -= 1L * worst.f * worst.v;
        rest.add(worst);
    }
}
```

**Why:**
We always want only the best `x` pairs in `top`.
If we exceed that, move the “worst” one (smallest frequency/value) out.

---

### 4️⃣ `eraseVal(int v, int x)`

When removing an element (sliding window left side):

1. Remove `(freq, value)` from `top` or `rest`.
2. Decrease its frequency by 1.
3. If frequency becomes 0 → remove it entirely.
4. If still > 0 → insert the new `(f, v)` into `rest`.
5. If `top` now has fewer than `x` elements, **promote** the best element from `rest` to `top`.

```java
private void eraseVal(int v, int x){
    Integer F = cnt.get(v);
    if (F == null || F == 0) return;
    int f = F;
    pull(v, f);
    f -= 1;
    if (f == 0) cnt.remove(v);
    else {
        cnt.put(v, f);
        rest.add(new Pair(f, v));
    }
    if (top.size() < x && !rest.isEmpty()){
        Pair best = rest.first();
        rest.remove(best);
        top.add(best);
        topSum += 1L * best.f * best.v;
    }
}
```

**Why:**
As elements leave the window, frequencies drop.
We need to rebalance between `top` and `rest` so `top` always contains exactly `x` (or fewer if not enough distinct) top elements.

---

## 🚀 Main Logic — Sliding Window

```java
public long[] findXSum(int[] nums, int k, int x) {
    int n = nums.length;
    long[] ans = new long[n - k + 1];
    cnt = new HashMap<>(Math.max(16, n * 2));
    top = new TreeSet<>(DESC);
    rest = new TreeSet<>(DESC);
    topSum = 0;

    // Step 1: Build the first window
    for (int i = 0; i < k; ++i) insertVal(nums[i], x);
    ans[0] = topSum;

    // Step 2: Slide the window
    for (int i = k; i < n; ++i){
        eraseVal(nums[i - k], x);  // remove leftmost element
        insertVal(nums[i], x);     // add new element
        ans[i - k + 1] = topSum;   // store x-sum for this window
    }
    return ans;
}
```

**Why it works:**

* Each step updates counts in O(log n) due to `TreeSet` operations.
* `topSum` always reflects the correct sum of top `x` frequent elements.
* We efficiently slide across the array in **O(n log n)** time — good for n = 10⁵.

---

## 🧠 Example Walkthrough

`nums = [1,1,2,2,3,4,2,3], k = 6, x = 2`

### Step 1: Initial window [1,1,2,2,3,4]

Frequencies → {1:2, 2:2, 3:1, 4:1}
Top 2: (1,2) → sum = 1+1+2+2 = 6
✅ ans[0] = 6

### Step 2: Slide → remove 1, add 2 → [1,2,2,3,4,2]

Frequencies → {1:1, 2:3, 3:1, 4:1}
Top 2: (2,4) → sum = 2+2+2+4 = 10
✅ ans[1] = 10

### Step 3: Slide → remove 1, add 3 → [2,2,3,4,2,3]

Frequencies → {2:3, 3:2, 4:1}
Top 2: (2,3) → sum = 2+2+2+3+3 = 12
✅ ans[2] = 12

Final Output → `[6, 10, 12]`

---

## ⏱️ Complexity

| Operation         | Time                          |
| ----------------- | ----------------------------- |
| Each insert/erase | O(log n) (TreeSet ops)        |
| For n windows     | O(n log n)                    |
| Space             | O(n) for frequency map + sets |

Efficient enough for n = 10⁵ 👌

---

## ✅ Final Takeaway

* This solution is an **optimized sliding window** version of Problem I.
* Uses **two TreeSets** to dynamically maintain top `x` frequent elements.
* Keeps track of `topSum` efficiently with every insert/erase.
* Time complexity: **O(n log n)**, making it suitable for large input sizes.

---

💬 **In summary:**

> Problem II generalizes the same logic as Problem I, but scales it efficiently with TreeSets and dynamic balancing. Instead of recomputing from scratch, it maintains frequency order and the running x-sum in real time as the window slides.
