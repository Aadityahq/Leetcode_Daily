Sure. Let's understand the problem first, then build the **brute-force Java solution using an array**, step by step.

## 1. What is the problem asking?

We are given:

```text
nums = [8, 2, 3, 4, 6]
k = 2
```

We need to find the **smallest positive multiple of `k` that is not present in `nums`**.

Multiples of `2` are:

```text
2, 4, 6, 8, 10, 12, 14, ...
```

Now check them in `nums`:

```text
2  → present ✅
4  → present ✅
6  → present ✅
8  → present ✅
10 → missing ❌
```

So the answer is:

```text
10
```

---

# 2. Brute-force idea using an array

The constraints are small:

```text
nums[i] <= 100
```

So we can create an array to mark which numbers are present.

For example:

```text
nums = [8, 2, 3, 4, 6]
```

Create:

```text
boolean[] present = new boolean[101];
```

Then mark every number:

```text
present[8] = true;
present[2] = true;
present[3] = true;
present[4] = true;
present[6] = true;
```

Conceptually:

| Number | Present? |
| ------ | -------- |
| 1      | false    |
| 2      | true     |
| 3      | true     |
| 4      | true     |
| 5      | false    |
| 6      | true     |
| 7      | false    |
| 8      | true     |

Now we start checking multiples of `k`.

---

# 3. How do we check multiples?

Start with:

```java
int multiple = k;
```

For `k = 2`:

```text
multiple = 2
```

Then repeatedly increase it by `k`:

```java
multiple += k;
```

So:

```text
2
↓ +2
4
↓ +2
6
↓ +2
8
↓ +2
10
```

For each multiple, check:

```java
present[multiple]
```

If it is present, continue.

If it is not present, return it immediately because we are checking from **smallest to largest**.

---

# 4. Java solution

```java
class Solution {
    public int smallestMissingMultiple(int[] nums, int k) {

        // Mark all numbers that are present
        boolean[] present = new boolean[101];

        for (int num : nums) {
            present[num] = true;
        }

        int multiple = k;

        while (true) {

            // If the multiple is not present,
            // it is the smallest missing multiple
            if (multiple > 100 || !present[multiple]) {
                return multiple;
            }

            // Move to the next multiple
            multiple += k;
        }
    }
}
```

---

# 5. Why do we check `multiple > 100`?

Our `present` array has indexes:

```text
0 to 100
```

We cannot do:

```java
present[102]
```

because that would cause:

```text
ArrayIndexOutOfBoundsException
```

Also, since every `nums[i]` is at most `100`, any multiple greater than `100` is **definitely missing** from `nums`.

Therefore:

```java
if (multiple > 100 || !present[multiple]) {
    return multiple;
}
```

For example:

```text
nums = [2, 4, 6, 8, ..., 100]
k = 2
```

If every even number up to `100` exists, the next multiple is:

```text
102
```

Since `102 > 100`, it cannot be inside `nums`, so return `102`.

---

# 6. Dry run

Let's dry run:

```text
nums = [8, 2, 3, 4, 6]
k = 2
```

### Step 1: Mark numbers

```text
present[2] = true
present[3] = true
present[4] = true
present[6] = true
present[8] = true
```

### Step 2: Start

```java
multiple = k; // 2
```

### Iteration 1

```text
multiple = 2

present[2] = true
```

So don't return.

```java
multiple += k;
```

Now:

```text
multiple = 4
```

### Iteration 2

```text
multiple = 4

present[4] = true
```

Continue.

```text
multiple = 6
```

### Iteration 3

```text
present[6] = true
```

Continue.

```text
multiple = 8
```

### Iteration 4

```text
present[8] = true
```

Continue.

```text
multiple = 10
```

### Iteration 5

```text
10 <= 100
present[10] = false
```

Therefore:

```java
return 10;
```

---

# 7. Why does this guarantee the smallest answer?

This is the most important part.

We check multiples in this exact order:

```text
k
2 × k
3 × k
4 × k
5 × k
...
```

From smallest to largest.

For `k = 2`:

```text
2 → 4 → 6 → 8 → 10 → ...
```

The **first missing multiple** we encounter must automatically be the **smallest missing multiple**.

We don't need to check anything after finding it.

---

## Time and Space Complexity

### Time Complexity

```text
O(n + 100/k)
```

In the worst case, effectively:

```text
O(n + 100)
```

### Space Complexity

```text
O(101)
```

Which is effectively:

```text
O(1)
```

because the array size is fixed by the constraints.

**Core idea to remember:** use the array as a lookup table, then check `k, 2k, 3k...` one by one until you find a number that is missing.
