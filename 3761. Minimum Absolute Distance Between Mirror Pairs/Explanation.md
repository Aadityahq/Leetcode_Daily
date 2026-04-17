# 🧠 Problem Summary

You are given an array `nums`.

A **mirror pair (i, j)** exists if:

* `i < j`
* `reverse(nums[i]) == nums[j]`

👉 You must return:

[
\text{minimum } |i - j|
]

If no such pair exists → return `-1`.

---

# 🔍 Key Insight (Your Approach)

Instead of checking:

> “Does reverse(nums[i]) exist later?”

Your code flips the thinking:

> “Have I already seen a number whose reverse equals the current number?”

---

# 💡 How Your Code Works

### Important Line 👇

```java
if (mpp.containsKey(nums[i]))
```

👉 This means:

* You are checking if **current number** was already stored as a **reverse of some previous number**

---

# 🔁 Flow of Logic

### Step-by-step:

1. Traverse array from left → right
2. For each `nums[i]`:

   ### ✅ Step 1: Check

   ```java
   if (mpp.containsKey(nums[i]))
   ```

   * If YES → a mirror pair is found
   * Because:

     ```
     reverse(nums[j]) = nums[i]
     ```
   * So:

     ```
     nums[j] reversed == nums[i]
     ```
   * Valid mirror condition ✔

---

### ✅ Step 2: Update Answer

```java
ans = Math.min(ans, i - mpp.get(nums[i]));
```

* `mpp.get(nums[i])` → gives index `j`
* Distance = `i - j`

---

### ✅ Step 3: Store Reverse

```java
mpp.put(reverse(nums[i]), i);
```

👉 This is the **main trick**

* Instead of storing number → store its **reverse**
* So future elements can match directly

---

# 🔄 Why This Works (Core Idea)

You store:

```
reverse(nums[i]) → i
```

Later if you see:

```
nums[j] == reverse(nums[i])
```

👉 You immediately detect a mirror pair

---

# 📊 Example Walkthrough

### Input:

```text
nums = [12, 21, 45, 54]
```

---

### Iteration:

| i | nums[i] | map before   | match? | action                        |
| - | ------- | ------------ | ------ | ----------------------------- |
| 0 | 12      | {}           | ❌      | store reverse(12)=21 → (21,0) |
| 1 | 21      | {21:0}       | ✅      | distance = 1                  |
| 2 | 45      | {21:0}       | ❌      | store reverse(45)=54          |
| 3 | 54      | {21:0, 54:2} | ✅      | distance = 1                  |

👉 Final Answer = **1**

---

# ⚠️ Important Detail

👉 You store:

```java
mpp.put(reverse(nums[i]), i);
```

NOT:

```java
mpp.put(nums[i], i);
```

This ensures:

* Future elements match **directly**
* No need to reverse every time for lookup

---

# ⏱️ Complexity

### Time Complexity:

* Loop → `O(n)`
* Reverse → `O(d)` (digits)

👉 Total: **O(n)**

---

### Space Complexity:

* HashMap → **O(n)**

---

# 🚀 Why This Solution is Smart

Compared to the usual approach:

| Normal Approach               | Your Approach          |
| ----------------------------- | ---------------------- |
| Check reverse(nums[i]) in map | Check nums[i] in map   |
| Store nums[i]                 | Store reverse(nums[i]) |

👉 Your version:

* Reduces one reverse operation in lookup
* Cleaner matching logic

---

# 🧩 Interview Explanation (Perfect Answer)

You can say:

> “I use a HashMap where I store the reverse of each number along with its index. While iterating, I check if the current number already exists in the map. If it does, it means a previous number’s reverse matches the current number, forming a mirror pair. I then compute the distance and keep track of the minimum.”

---
