## 🔍 Problem Understanding

You are given an array `nums` such that:

* Length of array = `2n`
* There are **n + 1 unique elements**
* **Exactly one element appears `n` times**
* All other elements appear **once**

👉 Your task is to **find the element repeated `n` times**.

---

## 🧠 Key Insight (Most Important)

If one element appears **n times in an array of size 2n**, it **must repeat very frequently**.

That means:

* While scanning the array, **the first number you see twice is guaranteed to be the answer**.

Why?

* All other elements appear only once.
* Only the repeated element can show up again.

---

## ✅ Best & Simplest Approach (Using HashSet)

### Idea

* Traverse the array
* Store elements in a `HashSet`
* If an element already exists in the set → **return it immediately**

### Time & Space

* **Time:** `O(n)`
* **Space:** `O(n)`

---

## 🧾 Java Solution

```java
class Solution {
    public int repeatedNTimes(int[] nums) {
        HashSet<Integer> seen = new HashSet<>();
        
        for (int num : nums) {
            if (seen.contains(num)) {
                return num; // Found the repeated element
            }
            seen.add(num);
        }
        
        return -1; // Will never happen due to constraints
    }
}
```

---

## ✨ Example Walkthrough

### Input

```
nums = [2,1,2,5,3,2]
```

### Steps

* seen = {}
* read 2 → add to set
* read 1 → add
* read 2 → already in set ✅ → return **2**

---

## 🚀 Alternative Trick (Without Extra Space)

Because the repeated element appears many times, it often appears **adjacent or very close**.

```java
class Solution {
    public int repeatedNTimes(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) return nums[i];
        }
        return nums[0];
    }
}
```

✔ Works due to problem guarantees
✔ **O(n)** time, **O(1)** space

---

## 🏁 Final Takeaway

* Only **one element repeats many times**
* The **first duplicate you encounter is the answer**
* HashSet solution is **clean, safe, and interview-friendly**

