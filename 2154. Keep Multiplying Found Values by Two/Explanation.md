# ✅ **LeetCode 2154 – Keep Multiplying Found Values by Two**

### **🔍 Problem Understanding**

You are given:

* An integer array `nums`
* An integer `original`

You need to:

1. Check if `original` exists in `nums`
2. If yes → multiply `original` by 2
3. Repeat until `original` is NOT found in `nums`
4. Return the final value

So the number keeps doubling as long as it appears inside the array.

---

# 💡 **Approach Explanation**

### **➡️ How we solve it**

1. Since we must check if a number exists in the array quickly,
   → we put all numbers in a **HashSet** (O(1) lookup time).

2. While the current `original` value **exists in the set**:

   * multiply it by 2 (i.e., `original *= 2`)

3. When it's not found → stop and return it.

### **➡️ Why HashSet?**

* Array search takes **O(n)** every time
* But a HashSet gives **O(1)** lookup
* Since numbers may double many times, fast checking is important
* Time complexity becomes: **O(n)** to build set + few O(1) lookups

---

# ✅ **Java Code**

```java
class Solution {
    public int findFinalValue(int[] nums, int original) {
        // Step 1: Put all numbers into a HashSet for O(1) lookup
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        // Step 2: Keep doubling original while it exists in the set
        while (set.contains(original)) {
            original *= 2;
        }

        // Step 3: Return when original is no longer found
        return original;
    }
}
```

---

# 📝 **Explanation (How + Why)**

### **✔ Step 1: Build a HashSet**

We convert the array into a HashSet:

* **How?**
  Loop through array and add each element to the set

* **Why?**
  HashSet allows **constant time searching**
  (`set.contains(original)` is O(1))

---

### **✔ Step 2: Keep checking and doubling**

* **How?**
  Use a `while` loop:

  ```java
  while(set.contains(original)) {
      original *= 2;
  }
  ```

* **Why?**
  The process must repeat as long as the number is found in the list
  (the problem explicitly says to continue searching after doubling)

---

### **✔ Step 3: Return final value**

Once we reach a number **not** inside the array → we stop.

This is the final value.

---

# 🎯 Example Walkthrough

### **Input**

```
nums = [5,3,6,1,12], original = 3
```

### Process

* 3 found → 3 × 2 = **6**
* 6 found → 6 × 2 = **12**
* 12 found → 12 × 2 = **24**
* 24 NOT found → stop

### Output

```
24
```

