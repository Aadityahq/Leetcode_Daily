## 🧩 Problem Understanding

We are given an array `nums`, and we can start at **any position** where `nums[curr] == 0`.
We also choose a **direction**:

* `left` (move `curr--`)
* `right` (move `curr++`)

Then we repeatedly follow the rules:

1. If we go out of range, stop.
2. If current element is `0` → just move in the same direction.
3. If current element is `> 0`:

   * Decrease it by 1.
   * Reverse direction.
   * Move one step in the *new* direction.

We must find how many starting positions (with direction) can make **all elements = 0** in the end.

---

## 💡 Intuition — *How and Why It Works*

Let’s analyze the process:

* Every non-zero number needs to be **decremented** eventually.
* Whenever you hit a **positive number**, you:

  * Decrease it by 1.
  * Reverse direction.
* This “zig-zag” pattern continues until you move out of bounds.

So effectively, you are **bouncing between non-zero numbers**, decreasing them until everything becomes zero.

### Key Observation:

For the process to make *everything zero*:

* Every positive element must be **reachable** from the starting zero by this zig-zag path.
* The sequence must **balance perfectly** — meaning you should not exit before visiting all non-zero elements enough times.

If any element remains non-zero when the process ends (because you moved out of bounds too early), the start position and direction are **invalid**.

---

## 🧠 Approach

1. Find all indices where `nums[i] == 0`.
2. For each zero position:

   * Try both directions: left and right.
   * Simulate the described process.
   * If all elements become zero → count it as a valid selection.
3. Return the total count.

Since `n ≤ 100`, a full simulation for each zero is efficient.

---

## 💻 Java Solution (with Comments)

```java
public class MakeArrayElementsZero {
    public static void main(String[] args) {
        int[] nums1 = {1, 0, 2, 0, 3};
        int[] nums2 = {2, 3, 4, 0, 4, 1, 0};

        System.out.println(validSelections(nums1)); // Output: 2
        System.out.println(validSelections(nums2)); // Output: 0
    }

    public static int validSelections(int[] nums) {
        int n = nums.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                // Try both directions
                if (canMakeAllZero(nums, i, 1)) count++;   // Move right
                if (canMakeAllZero(nums, i, -1)) count++;  // Move left
            }
        }
        return count;
    }

    private static boolean canMakeAllZero(int[] nums, int start, int dir) {
        int n = nums.length;
        int[] arr = nums.clone(); // copy to avoid modifying original
        int curr = start;

        while (curr >= 0 && curr < n) {
            if (arr[curr] == 0) {
                // Just move in the same direction
                curr += dir;
            } else {
                // Decrease by 1 and reverse direction
                arr[curr]--;
                dir = -dir;
                curr += dir;
            }
        }

        // Check if all became zero
        for (int val : arr) {
            if (val != 0) return false;
        }
        return true;
    }
}
```

---

## 🧾 Example Walkthrough

### Example: `nums = [1, 0, 2, 0, 3]`

* Start at `curr = 3` (value = 0)

  * Try **left**: it works → all become `[0,0,0,0,0]`
  * Try **right**: also works → `[0,0,0,0,0]`
* Other zero index (`i = 1`) doesn’t work for both directions.

✅ **Answer = 2**

---

## 🧩 Why This Works

* The simulation directly follows the problem rules.
* We try every possible valid starting zero and direction.
* Since we check all combinations and simulate faithfully, the answer is guaranteed correct.
* Time complexity: **O(n²)** (since `n ≤ 100`, this is fine).

---
