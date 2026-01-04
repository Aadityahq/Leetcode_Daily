## 🧠 Problem Understanding (How to Think)

You are given an array `nums`.

👉 For **each number**, you must:

1. **Check if it has exactly 4 divisors**
2. If yes, **add the sum of those 4 divisors** to the answer

Finally, return the total sum.

---

## 🔍 Key Observation (VERY IMPORTANT)

A number has **exactly 4 divisors** in **only two cases**:

### Case 1:

**n = p³**, where `p` is a prime
Divisors → `{1, p, p², p³}`

Example:

* `8 = 2³` → divisors `{1,2,4,8}` ✔️

### Case 2:

**n = p × q**, where `p` and `q` are **distinct primes**
Divisors → `{1, p, q, n}`

Example:

* `21 = 3 × 7` → divisors `{1,3,7,21}` ✔️

❌ Any other number will **not** have exactly 4 divisors.

---

## 🚀 Efficient Strategy (Why This Works)

Instead of checking all divisors (slow ❌), we:

* Iterate from `2` to `√n`
* Count divisors in **pairs** `(i, n/i)`
* Stop early if divisors exceed 4
* If total divisors == 4 → add their sum

⏱️ Time Complexity:

* **O(n √m)**
  (`n = array length`, `m = max number ≤ 10⁵`)
* Efficient enough for constraints

---

## ✅ Java Solution (Clean & Optimized)

```java
class Solution {
    public int sumFourDivisors(int[] nums) {
        int totalSum = 0;

        for (int num : nums) {
            int count = 0;
            int sum = 0;

            for (int i = 1; i * i <= num; i++) {
                if (num % i == 0) {
                    int d1 = i;
                    int d2 = num / i;

                    if (d1 == d2) {
                        count++;
                        sum += d1;
                    } else {
                        count += 2;
                        sum += d1 + d2;
                    }

                    // Early exit if divisors exceed 4
                    if (count > 4) break;
                }
            }

            if (count == 4) {
                totalSum += sum;
            }
        }

        return totalSum;
    }
}
```

---

## ⏱️ Time & Space Complexity    
| Metric               | Value            |
| -------------------- | ---------------- |
| **Time Complexity**  | `O(n √m)`        |
| **Space Complexity** | `O(1)`           |         
✔️ Efficient for given constraints              