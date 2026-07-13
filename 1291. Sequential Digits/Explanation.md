## 🟢 LeetCode 1291. Sequential Digits (Medium)

### 🔹 Problem Explanation

You are given two integers `low` and `high`.

A number is called a **Sequential Digit Number** if every digit is exactly **1 greater** than the previous digit.

For example:

* ✅ `123` → Sequential (`1 → 2 → 3`)
* ✅ `4567` → Sequential (`4 → 5 → 6 → 7`)
* ❌ `124` → Not sequential (`2 → 4`)
* ❌ `135` → Not sequential

Your task is to **return all sequential digit numbers between `low` and `high` (inclusive)** in **sorted order**.

---

### Example 1

```text
Input:
low = 100
high = 300

Output:
[123, 234]
```

**Explanation**

Sequential numbers in this range are:

```
123
234
```

---

### Example 2

```text
Input:
low = 1000
high = 13000

Output:
[1234,2345,3456,4567,5678,6789,12345]
```

---

# 🔍 Observation

The digits must always increase by exactly **1**.

Possible sequential numbers are limited:

```
12
23
34
45
56
67
78
89

123
234
345
...

1234
2345
...

123456789
```

Since digits only go from **1 to 9**, there are only a **small number** of sequential numbers possible.

Instead of checking every number between `low` and `high`, we can simply **generate all valid sequential numbers**.

---

# 💡 Idea

Start from every possible first digit.

For example

Start with `1`

```
1
12
123
1234
12345
...
123456789
```

Start with `2`

```
2
23
234
2345
23456
...
```

Continue until `9`.

While generating:

* if number is inside `[low, high]`

  * add it to answer
* if number exceeds `high`

  * stop extending that sequence

Finally sort the result.

---

# 🧠 Algorithm

For every starting digit from **1 to 9**

* Initialize current number = starting digit
* Initialize next digit = starting digit + 1

While next digit ≤ 9

* Append next digit

```
current = current * 10 + nextDigit
```

* If current lies inside range

  * add to answer

Increase next digit.

Finally sort the answer.

---

# ✅ Dry Run

Suppose

```
low = 100
high = 500
```

Start from 1

```
12
123 ✅
1234 (too large)
```

Start from 2

```
23
234 ✅
2345 (too large)
```

Start from 3

```
34
345 ✅
```

Start from 4

```
45
456 ✅
```

Start from 5

```
56
567 (>500)
```

Answer

```
[123,234,345,456]
```

---

# ✅ Java Solution

```java
class Solution {
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> result = new ArrayList<>();

        for (int start = 1; start <= 9; start++) {
            int num = start;

            for (int next = start + 1; next <= 9; next++) {
                num = num * 10 + next;

                if (num >= low && num <= high) {
                    result.add(num);
                }
            }
        }

        Collections.sort(result);
        return result;
    }
}
```

---

# ✅ Why does this work?

Every sequential number is uniquely determined by:

* its **starting digit**
* continuously adding the next digit.

Example

Start = 4

```
4
45
456
4567
45678
```

No sequential number can be missed because every valid sequence starts from exactly one digit.

No invalid number is generated because we always append the next consecutive digit.

---

# ✅ Time Complexity

There are at most

```
9 starts
```

Each start generates at most

```
8 numbers
```

Total generated numbers

```
9 × 8 = 72
```

Sorting at most 36 valid sequential numbers is effectively constant time.

**Time Complexity:**

```
O(1)
```

(Constant work)

---

# ✅ Space Complexity

Only the output list is used.

```
O(1)
```

(excluding the output list)

---

## 📌 Key Takeaways

* Don't iterate from `low` to `high`; generate only valid sequential numbers.
* A sequential number is completely defined by its starting digit.
* Since there are very few possible sequential numbers, generation is much more efficient than checking every number.

---


