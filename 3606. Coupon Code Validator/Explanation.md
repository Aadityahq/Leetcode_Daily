# 3606. Coupon Code Validator
## 🔍 Problem Understanding (What is asked?)

You are given **n coupons**, each described by:

* `code[i]` → coupon code (string)
* `businessLine[i]` → category of the coupon (string)
* `isActive[i]` → whether the coupon is active (boolean)

A coupon is **VALID** only if **all** these conditions are true:

1. **Code is valid**

   * Not empty
   * Contains **only**:

     * letters (`a-z`, `A-Z`)
     * digits (`0-9`)
     * underscore (`_`)

2. **Business line is valid**

   * Must be one of:

     ```
     electronics, grocery, pharmacy, restaurant
     ```

3. **Coupon is active**

   * `isActive[i] == true`

---

## 📦 Output Requirement

Return **only the codes** of valid coupons, sorted as:

1. **By business line order**

   ```
   electronics → grocery → pharmacy → restaurant
   ```
2. **Within the same business line**, sort by **lexicographical order of code**

---

## 🧠 Approach (How & Why)

### Step 1: Define valid business lines

We use a fixed order:

```
electronics, grocery, pharmacy, restaurant
```

Why?
Because sorting depends on this **custom order**, not alphabetical order.

---

### Step 2: Validate each coupon

For each index `i`:

* Check if `isActive[i]` is true
* Check if `businessLine[i]` is in the allowed list
* Check if `code[i]`:

  * is not empty
  * matches regex: `[A-Za-z0-9_]+`

Only then we accept the coupon.

---

### Step 3: Group coupons by business line

Why?
Because sorting requires **business-line priority first**, then code order.

We store valid codes in a map like:

```
electronics → [codes...]
grocery     → [codes...]
pharmacy    → [codes...]
restaurant  → [codes...]
```

---

### Step 4: Sort and collect results

* For each business line (in required order):

  * Sort its codes lexicographically
  * Add them to the final result list

---

## ✅ Java Solution

```java
import java.util.*;

class Solution {
    public List<String> validateCoupons(
            String[] code,
            String[] businessLine,
            boolean[] isActive) {

        // Required business line order
        String[] order = {"electronics", "grocery", "pharmacy", "restaurant"};
        Set<String> validBusiness = new HashSet<>(Arrays.asList(order));

        // Map to store valid codes by business line
        Map<String, List<String>> map = new HashMap<>();
        for (String b : order) {
            map.put(b, new ArrayList<>());
        }

        // Regex for valid coupon code
        String regex = "[A-Za-z0-9_]+";

        for (int i = 0; i < code.length; i++) {

            // Check active
            if (!isActive[i]) continue;

            // Check business line
            if (!validBusiness.contains(businessLine[i])) continue;

            // Check code validity
            if (code[i] == null || code[i].isEmpty()) continue;
            if (!code[i].matches(regex)) continue;

            // Valid coupon
            map.get(businessLine[i]).add(code[i]);
        }

        // Prepare result
        List<String> result = new ArrayList<>();

        for (String b : order) {
            List<String> list = map.get(b);
            Collections.sort(list); // lexicographical order
            result.addAll(list);
        }

        return result;
    }
}
```

---

## ⏱️ Time & Space Complexity

* **Time Complexity**:

  * Validation: `O(n)`
  * Sorting (worst case): `O(n log n)`
* **Space Complexity**:

  * `O(n)` for storing valid coupons

---

## 🧪 Example Walkthrough (Example 1)

Input:

```
code = ["SAVE20","","PHARMA5","SAVE@20"]
businessLine = ["restaurant","grocery","pharmacy","restaurant"]
isActive = [true,true,true,true]
```

Valid coupons:

* `"SAVE20"` → valid
* `"PHARMA5"` → valid

Sorted by business line:

```
pharmacy → PHARMA5
restaurant → SAVE20
```

Output:

```
["PHARMA5", "SAVE20"]
```

---

