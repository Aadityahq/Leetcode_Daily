# 🧩 **Problem Explanation**

You are given a **binary array** `nums` (contains only 0's and 1's).

For every index `i`, you form a **binary number** by taking:

```
nums[0], nums[1], ..., nums[i]
```

This binary number is called `xi`.

### Example

If `nums = [1, 0, 1]`:

* `x0 = 1`      → decimal 1
* `x1 = 10`     → decimal 2
* `x2 = 101`    → decimal 5

For **each** prefix number, you must check whether it is **divisible by 5**.

So the output is an array of **true/false** values.

---

# ⚠️ **Why this problem is tricky**

The prefix can become very large because:

* nums.length can be up to **100,000**
* A binary number with 100,000 bits cannot fit into any normal integer type (`int`, `long`).

So you **cannot convert** the full prefix to a decimal number.

We need a smarter way.

---

# 💡 **Main Insight (How & Why)**

## ✔️ HOW does a binary number grow?

Every time you add a new bit at the end:

If `oldValue` is the decimal value of previous prefix:

```
newValue = oldValue * 2 + currentBit
```

Example:

* prefix = `101` → decimal 5
* next bit = `1`
* new value = `5 * 2 + 1 = 11`

This is the **HOW** the prefix number changes.

---

## ✔️ WHY use modulo (the key idea)?

We only care whether the number is divisible by 5:

```
xi % 5 == 0
```

BUT we cannot store xi (too big!).

Good news:
Divisibility only depends on the **remainder**.

So instead of storing the whole number, we store only:

```
current = (current * 2 + bit) % 5
```

This keeps `current` always between **0 and 4** — small and safe.

---

# 🔄 **HOW the update works**

At each bit:

```
current = (current * 2 + nums[i]) % 5
```

Then check:

```
if current == 0 → divisible by 5 → true
else → false
```

---

# 🧠 Example Showing HOW & WHY

Let nums = [0,1,1]

Start:

```
current = 0
```

### i = 0, bit = 0

```
current = (0*2 + 0) % 5 = 0
→ divisible → true
```

### i = 1, bit = 1

```
current = (0*2 + 1) % 5 = 1
→ NOT divisible → false
```

### i = 2, bit = 1

```
current = (1*2 + 1) % 5 = 3
→ NOT divisible → false
```

Final answer → `[true, false, false]`

---

# ✅ Summary (What, How, Why)

### **WHAT:**

Check if binary prefixes are divisible by 5.

### **HOW:**

Update prefix using:

```
prefix = (prefix * 2 + bit) % 5
```

### **WHY:**

* Full number becomes too large
* Modulo keeps values small (0–4)
* Divisibility only depends on remainder

---


# ✅ **Solution Explanation — HOW & WHY**

We want to know if each prefix of the binary array forms a number divisible by 5.

Example:
For nums = [1,0,1], the prefixes are:

* `1` → 1
* `10` → 2
* `101` → 5

We check if each decimal value is divisible by 5.

But we **cannot** convert every prefix to a full decimal number — it becomes too large.

So the solution uses **modulo math**.

---

# 🔍 HOW the solution works

### **Step 1 — Use a variable to store the prefix value mod 5**

We keep:

```
current = prefixValue % 5
```

This means:

* We don't store the full number (which may be huge)
* We only store the remainder (0,1,2,3,4)

Remainder is enough to check divisibility.

---

### **Step 2 — Process each bit**

Binary numbers follow the rule:

```
newValue = oldValue * 2 + bit
```

So we do:

```
current = (current * 2 + bit) % 5
```

This gives us the new remainder.

---

### **Step 3 — Check divisibility**

If:

```
current == 0
```

then the prefix number is divisible by 5.

Add `true` to the answer; otherwise add `false`.

---

# 🤔 WHY this method works

### ✔️ Because of modulo property:

```
(a * 2 + b) % 5 = ((a % 5) * 2 + b) % 5
```

This means:

* Instead of calculating the full number
* We can just keep calculating the remainder

And whenever remainder becomes 0 → full number is divisible by 5.

### ✔️ Prevents overflow

A binary prefix of length 100,000 bits is enormous.
But we only store numbers from 0 to 4.

### ✔️ Efficient

* **O(n)** time
* **O(1)** memory
* Perfect for huge binary inputs

---

# 🧠 Example Walkthrough (How + Why)

nums = [0,1,1]

Start: current = 0

| Step | Bit | Formula           | current | Reason Why                  |
| ---- | --- | ----------------- | ------- | --------------------------- |
| 0    | 0   | (0*2 + 0) % 5 = 0 | 0       | remainder 0 → divisible     |
| 1    | 1   | (0*2 + 1) % 5 = 1 | 1       | remainder 1 → not divisible |
| 2    | 1   | (1*2 + 1) % 5 = 3 | 3       | remainder 3 → not divisible |

Output → **[true, false, false]**

---

# ✅ Final Java Solution

```java
class Solution {
    public List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> answer = new ArrayList<>();
        int current = 0;  // stores prefix % 5

        for (int bit : nums) {
            current = (current * 2 + bit) % 5;  // HOW we update prefix
            answer.add(current == 0);            // WHY: remainder 0 → divisible
        }

        return answer;
    }
}
```

---

# 📌 Final Notes — HOW & WHY Summary

### **HOW:**

* Build prefix using: `(current * 2 + bit) % 5`
* Check if remainder = 0

### **WHY:**

* Full binary number becomes too large
* Modulo keeps numbers tiny and manageable
* Divisibility depends only on remainder
* Efficient for very large inputs

---

