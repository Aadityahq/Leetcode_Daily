## Understanding the Problem

For any number:

* A **peak** digit is greater than both neighbors.

  * Example: `121` → `2 > 1` and `2 > 1`, so `2` is a peak.
* A **valley** digit is smaller than both neighbors.

  * Example: `202` → `0 < 2` and `0 < 2`, so `0` is a valley.

Only digits that have **both left and right neighbors** can be checked.

Therefore:

* Numbers with length `< 3` have waviness = `0`.

### Example

`4848`

Digits:

```
4 8 4 8
```

Index 1:

```
8 > 4 and 8 > 4
```

Peak ✔

Index 2:

```
4 < 8 and 4 < 8
```

Valley ✔

Total waviness = 2

---

# Brute Force Idea

For every number from `num1` to `num2`:

1. Convert to string.
2. Check every middle digit.
3. Count peaks and valleys.
4. Add to answer.

Since:

```
num2 <= 100000
```

Maximum range size is only:

```
100000
```

and each number has at most 6 digits.

So complexity:

```
100000 × 6 = 600000
```

which is very small.

---

# Why This Works

For each position `i`:

A digit contributes exactly one waviness if:

```java
digit[i] > digit[i-1] && digit[i] > digit[i+1]
```

(peak)

or

```java
digit[i] < digit[i-1] && digit[i] < digit[i+1]
```

(valley)

We simply count all such positions.

---

# Java Solution

```java
class Solution {

    public int totalWaviness(int num1, int num2) {
        int ans = 0;

        for (int num = num1; num <= num2; num++) {
            ans += waviness(num);
        }

        return ans;
    }

    private int waviness(int num) {
        String s = String.valueOf(num);

        if (s.length() < 3) {
            return 0;
        }

        int count = 0;

        for (int i = 1; i < s.length() - 1; i++) {

            char left = s.charAt(i - 1);
            char cur = s.charAt(i);
            char right = s.charAt(i + 1);

            boolean peak = cur > left && cur > right;
            boolean valley = cur < left && cur < right;

            if (peak || valley) {
                count++;
            }
        }

        return count;
    }
}
```

---

# Dry Run

### Input

```java
num1 = 120
num2 = 130
```

### 120

```
1 2 0

2 > 1
2 > 0
```

Peak → waviness = 1

### 121

```
1 2 1

2 > 1
2 > 1
```

Peak → waviness = 1

### 130

```
1 3 0

3 > 1
3 > 0
```

Peak → waviness = 1

Others contribute 0.

Answer:

```
1 + 1 + 1 = 3
```

---

# Complexity Analysis

Let:

```
R = num2 - num1 + 1
D = number of digits (≤ 6)
```

Time Complexity:

```
O(R × D)
```

Worst case:

```
O(100000 × 6)
≈ O(600000)
```

Space Complexity:

```
O(1)
```

This is the intended/simple solution because the constraint `num2 ≤ 10^5` is small enough to check every number directly.
