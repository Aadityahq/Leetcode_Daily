# ⭐ **1262. Greatest Sum Divisible by Three — Full Explanation**

---

# 🔶 **Problem Explanation (What is being asked?)**

You are given an array of integers.
You need to choose **some** of these integers (any subset) such that:

1. Their **sum is divisible by 3**
2. The **sum is as large as possible**

You are NOT required to use all numbers.
You can skip any number if needed.

---

# 🔍 **Example Understanding**

### Example:

```
nums = [3, 6, 5, 1, 8]
```

You want the maximum sum that is divisible by 3.

One valid group is:

```
3 + 6 + 1 + 8 = 18
```

This is divisible by 3 and is **maximum possible**.

---

# ⚠️ Why is this difficult?

Because you can't just:

* Sort the numbers
* Or take all even numbers
* Or choose greedy picks directly

You need to think in terms of **remainders mod 3**.

---

---

# 🌟 **HOW the solution works**

The key idea comes from **modulo arithmetic**.

Every number has a remainder when divided by 3:

| Number      | Remainder (mod 3) |
| ----------- | ----------------- |
| 3, 6, 9, 12 | 0                 |
| 1, 4, 7, 10 | 1                 |
| 2, 5, 8, 11 | 2                 |

Let:

```
totalSum = sum(nums)
```

Now we check:

### Case 1

If:

```
totalSum % 3 == 0
```

Great!
The whole sum is already divisible → return it.

---

### Case 2

If:

```
totalSum % 3 == 1
```

We must subtract something that reduces remainder by 1.

Options:

#### Option A

Remove the **smallest number whose remainder is 1**
(because removing 1 fixes total % 3)

#### Option B

Remove **two smallest numbers whose remainder is 2**
because:

```
2 + 2 = 4 → 4 % 3 = 1
```

Take the **minimum loss** option.

---

### Case 3

If:

```
totalSum % 3 == 2
```

We must subtract something that reduces remainder by 2.

Options:

#### Option A

Remove the **smallest number whose remainder is 2**

#### Option B

Remove **two smallest numbers whose remainder is 1**

Take the **minimum loss** option.

---

---

# 🌟 **WHY the solution works (important logic)**

### ✔️ Fact 1:

To make a number divisible by 3, only the **remainder matters**.
Total sum's remainder can be:

* 0 → perfect
* 1 → remove something worth “1 mod 3”
* 2 → remove something worth “2 mod 3”

---

### ✔️ Fact 2:

We want to remove the **least amount**, because:

> Removing smallest possible values keeps the sum as LARGE as possible.

This is why we pick the **minimum value** from mod1 or mod2 lists.

---

### ✔️ Fact 3:

Why only TWO numbers ever need to be removed?

Because:

* 1 = 1
* 2 = 2
* 4 = 1 mod 3 (two 2’s)
* 5 = 2 mod 3 (two 1’s)

To adjust by “1” or “2”, at most **two small numbers are needed**.

---

### ✔️ Fact 4:

This greedy (removing smallest) approach is optimal because:

If you remove bigger numbers, you make the final sum smaller → bad.
Removing the **minimum possible cost** ensures maximum sum remains.

---

---

# 🧠 **Putting all logic together**

1. Compute the total sum.
2. If divisible by 3 → return it.
3. Separate numbers into groups:

   * mod1 list
   * mod2 list
4. Sort them to find the smallest ones.
5. Based on totalSum % 3:

   * Remove minimal loss combination.
6. Return updated total.

---

---

# ✅ **Java Solution (LeetCode Ready)**

```java
class Solution {
    public int maxSumDivThree(int[] nums) {
        int total = 0;

        List<Integer> mod1 = new ArrayList<>();
        List<Integer> mod2 = new ArrayList<>();

        // Step 1: sum & separate by remainders
        for (int n : nums) {
            total += n;
            if (n % 3 == 1) mod1.add(n);
            else if (n % 3 == 2) mod2.add(n);
        }

        // Step 2: sort mod lists
        Collections.sort(mod1);
        Collections.sort(mod2);

        if (total % 3 == 0) return total;  // already perfect

        int rem = total % 3;
        int optionA = Integer.MAX_VALUE;
        int optionB = Integer.MAX_VALUE;

        // If remainder = 1
        if (rem == 1) {
            if (!mod1.isEmpty()) optionA = mod1.get(0);    // smallest mod1 element
            if (mod2.size() >= 2) 
                optionB = mod2.get(0) + mod2.get(1);        // two smallest mod2
        } 
        // If remainder = 2
        else {
            if (!mod2.isEmpty()) optionA = mod2.get(0);    // smallest mod2 element
            if (mod1.size() >= 2) 
                optionB = mod1.get(0) + mod1.get(1);        // two smallest mod1
        }

        return total - Math.min(optionA, optionB);
    }
}
```

---

# 🎉 **Final Summary (Short + Clear)**

### ❓ What is the problem asking?

Pick any numbers so sum is **maximum** and **divisible by 3**.

### 💡 How to solve?

Use modulo math.
Compute total sum.
If sum % 3 ≠ 0, remove the **smallest amount needed** to fix it.

### 🔥 Why does this work?

Because only the remainder matters, and removing the **smallest elements** keeps the final sum the largest possible.

---

