## 🔢 762. Prime Number of Set Bits in Binary Representation

### 🧠 Problem Understanding

You are given two integers: `left` and `right`.

For every number in the range **[left, right]**:

1. Convert the number to **binary**.
2. Count how many **1's (set bits)** are present.
3. Check if that count is a **prime number**.
4. Return the total count of such numbers.

---

### 📌 Example

#### Input:

```
left = 6, right = 10
```

| Number | Binary | Set Bits | Prime? |
| ------ | ------ | -------- | ------ |
| 6      | 110    | 2        | ✅ Yes  |
| 7      | 111    | 3        | ✅ Yes  |
| 8      | 1000   | 1        | ❌ No   |
| 9      | 1001   | 2        | ✅ Yes  |
| 10     | 1010   | 2        | ✅ Yes  |

✅ Total = **4**

---

## 💡 Key Observations

* Maximum number ≤ 10⁶
* Maximum set bits possible ≈ 20 (since 2²⁰ > 10⁶)
* So we only need to check prime numbers up to **20**
* Prime numbers ≤ 20:

  ```
  2, 3, 5, 7, 11, 13, 17, 19
  ```

---

## 🚀 Approach

1. Loop from `left` to `right`
2. Count set bits using:

   * `Integer.bitCount(n)` (built-in Java method)
3. Check if that count is prime
4. Keep incrementing answer

Time Complexity:

* At most 10⁴ numbers
* Each bitCount takes O(1)
* Overall: **O(N)** → Efficient ✅

---

## ✅ Java Solution

```java
class Solution {
    
    public int countPrimeSetBits(int left, int right) {
        int count = 0;
        
        for (int i = left; i <= right; i++) {
            int setBits = Integer.bitCount(i);
            
            if (isPrime(setBits)) {
                count++;
            }
        }
        
        return count;
    }
    
    private boolean isPrime(int num) {
        if (num < 2) return false;
        
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0) return false;
        }
        
        return true;
    }
}
```

---

## 🔎 Why This Works

* `Integer.bitCount(i)` efficiently counts 1s in binary.
* Prime check runs very fast because the number is small (≤ 20).
* We simply count valid numbers in range.

---

## 🧩 Even Faster Trick (Optional Optimization)

Instead of checking prime every time, we can store primes in a Set:

```java
Set<Integer> primes = Set.of(2,3,5,7,11,13,17,19);
```

Then just check:

```java
if (primes.contains(setBits))
```

This avoids repeated prime calculation.

---

## 🎯 Final Complexity

* **Time:** O(N)
* **Space:** O(1)

---

