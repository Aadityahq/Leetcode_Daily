# 2300. Successful Pairs of Spells and Potions 🧙‍♂️🧪

## 🧾 Problem Statement
You are given two positive integer arrays — `spells` and `potions`, and an integer `success`.

A spell and potion pair is **successful** if:
spell[i] * potion[j] >= success


Return an array `pairs[]` where each element represents the number of potions that will form a successful pair with each spell.

---

## 📘 Example

### Example 1:
**Input:**
spells = [5,1,3]
potions = [1,2,3,4,5]
success = 7


**Output:**
[4,0,3]



**Explanation:**
| Spell | Possible Products | Successful Count |
|--------|------------------|------------------|
| 5 | [5,10,15,20,25] | 4 |
| 1 | [1,2,3,4,5] | 0 |
| 3 | [3,6,9,12,15] | 3 |

---

## 🧠 Approach (How and Why)

### Why sorting helps?
If we sort the potions, we can use **binary search** to quickly find how many potions are strong enough for a given spell.

### Steps:
1. Sort `potions`.
2. For each spell:
   - Calculate minimum potion strength needed:
     ```
     minPotion = ceil(success / spell)
     ```
   - Use binary search to find the first potion ≥ `minPotion`.
   - Count = total potions - index found.
3. Store the count in the result array.

---

## ⏱️ Time Complexity
| Operation | Complexity |
|------------|-------------|
| Sorting potions | O(m log m) |
| For each spell (binary search) | O(n log m) |
| **Total** | **O(m log m + n log m)** |

---

## 💻 Java Implementation
```java
import java.util.*;

public class SuccessfulPairs {
    public static int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);
        int n = spells.length;
        int m = potions.length;
        int[] res = new int[n];
        
        for (int i = 0; i < n; i++) {
            long minPotion = (success + spells[i] - 1) / spells[i]; // ceiling division
            int idx = binarySearch(potions, minPotion);
            res[i] = m - idx;
        }
        return res;
    }

    private static int binarySearch(int[] potions, long target) {
        int left = 0, right = potions.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (potions[mid] < target)
                left = mid + 1;
            else
                right = mid;
        }
        return left;
    }

    public static void main(String[] args) {
        int[] spells = {5, 1, 3};
        int[] potions = {1, 2, 3, 4, 5};
        long success = 7;
        System.out.println(Arrays.toString(successfulPairs(spells, potions, success)));
    }
}
🧩 Key Takeaways
Sorting + Binary Search is a powerful combo for range queries.

Avoid brute force (O(n * m)) — too slow for large inputs.

Using ceiling division prevents integer rounding errors.