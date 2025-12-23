Below is a **clear problem explanation + optimized Java solution** that you can confidently explain in an interview or while submitting on LeetCode.

---

## 🧠 Problem Explanation (Simple Words)

You are given a list of **events**.
Each event has:

* `startTime`
* `endTime`
* `value`

You can attend **at most 2 events**, but they **must not overlap**.

⚠️ Important rule (inclusive time):

* If one event ends at time `t`, the next event must start at **`t + 1` or later**.

Your task is to **choose up to two non-overlapping events such that their total value is maximum**.

---

## 💡 Key Insight

Brute force checking all pairs would be **O(n²)** → too slow for `10⁵` events.

So we use a **smart greedy + binary search approach**.

---

## 🚀 Optimized Strategy (How & Why)

### Step 1: Sort events by start time

This helps us efficiently find the next valid non-overlapping event.

```text
Sort by startTime ascending
```

---

### Step 2: Precompute suffix maximum values

We create an array `suffixMax[i]` that stores:

> **Maximum event value from index `i` to the end**

This allows us to quickly know:

> “What is the best event I can attend after this one?”

---

### Step 3: For each event, binary search the next valid event

For event `i` ending at `endTime`:

* We binary search for the **first event whose startTime ≥ endTime + 1**
* Combine:

  ```
  current event value + best future event value
  ```

Also compare with choosing **only one event**.

---

## ⏱ Time & Space Complexity

* **Time:** `O(n log n)`
* **Space:** `O(n)`

Efficient and accepted ✅

---

## ✅ Java Solution (LeetCode Ready)

```java
import java.util.*;

class Solution {
    public int maxTwoEvents(int[][] events) {
        int n = events.length;

        // Step 1: Sort events by start time
        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));

        // Step 2: Suffix max array
        int[] suffixMax = new int[n];
        suffixMax[n - 1] = events[n - 1][2];

        for (int i = n - 2; i >= 0; i--) {
            suffixMax[i] = Math.max(suffixMax[i + 1], events[i][2]);
        }

        int answer = 0;

        // Step 3: For each event, binary search the next valid event
        for (int i = 0; i < n; i++) {
            int start = events[i][0];
            int end = events[i][1];
            int value = events[i][2];

            // Case 1: Take only this event
            answer = Math.max(answer, value);

            // Case 2: Take this event + another non-overlapping event
            int nextIndex = binarySearch(events, end + 1);

            if (nextIndex < n) {
                answer = Math.max(answer, value + suffixMax[nextIndex]);
            }
        }

        return answer;
    }

    // Binary search to find first event with startTime >= target
    private int binarySearch(int[][] events, int target) {
        int left = 0, right = events.length - 1;
        int result = events.length;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (events[mid][0] >= target) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }
}
```

---


