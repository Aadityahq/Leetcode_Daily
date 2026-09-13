## 835. Image Overlap — Java

### 1. Understanding the Problem

We have two `n × n` binary matrices:

* `1` represents a filled pixel.
* `0` represents an empty pixel.

We can **translate** one image in any direction:

* left
* right
* up
* down
* diagonally

But we **cannot rotate** the image.

After moving the first image, we place it over the second image and count how many positions contain `1` in **both** images.

We need to find the **maximum possible overlap**.

---

## 2. Key Idea

The important observation is:

> Instead of actually moving the entire matrix, we can try every possible translation and count how many `1`s overlap.

For example, suppose a `1` in `img1` is at:

```text
(row1, col1)
```

and a `1` in `img2` is at:

```text
(row2, col2)
```

If we translate `img1` by:

```text
row2 - row1
col2 - col1
```

then these two `1`s will land on the same position.

So we can:

1. Find every `1` in `img1`.
2. Find every `1` in `img2`.
3. Consider every pair of `1`s.
4. Calculate the translation required to align them.
5. Count how many pairs require the same translation.
6. The largest count is the answer.

---

## 3. Why Does This Work?

Let's take:

```text
img1:

1 1 0
0 1 0
0 1 0
```

and

```text
img2:

0 0 0
0 1 1
0 0 1
```

Suppose we want this `1` from `img1`:

```text
img1[0][0]
```

to overlap with:

```text
img2[1][1]
```

The required translation is:

```text
row shift = 1 - 0 = 1
col shift = 1 - 0 = 1
```

So translation `(1, 1)` aligns these two pixels.

Now, if several pairs of `1`s produce the same translation `(1, 1)`, that means **all those pixels overlap when we apply that translation**.

Therefore:

```text
frequency of a translation = number of overlapping 1s
```

So we simply need to find the translation with the highest frequency.

---

# 4. Java Solution

```java
import java.util.*;

class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;

        List<int[]> ones1 = new ArrayList<>();
        List<int[]> ones2 = new ArrayList<>();

        // Store positions of 1s in img1
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) {
                    ones1.add(new int[]{i, j});
                }
            }
        }

        // Store positions of 1s in img2
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img2[i][j] == 1) {
                    ones2.add(new int[]{i, j});
                }
            }
        }

        Map<String, Integer> map = new HashMap<>();

        int answer = 0;

        // Try every pair of 1s
        for (int[] p1 : ones1) {
            for (int[] p2 : ones2) {

                int rowShift = p2[0] - p1[0];
                int colShift = p2[1] - p1[1];

                String key = rowShift + "," + colShift;

                int count = map.getOrDefault(key, 0) + 1;

                map.put(key, count);

                answer = Math.max(answer, count);
            }
        }

        return answer;
    }
}
```

---

# 5. Code Explanation

### Step 1: Store positions of `1`s

```java
List<int[]> ones1 = new ArrayList<>();
List<int[]> ones2 = new ArrayList<>();
```

Instead of repeatedly checking the entire matrices while trying translations, we only care about the positions containing `1`.

For example:

```text
img1:

1 1 0
0 1 0
0 1 0
```

The positions are:

```text
(0,0)
(0,1)
(1,1)
(2,1)
```

We store them in `ones1`.

Same thing for `img2`.

---

### Step 2: Compare every `1` from `img1` with every `1` from `img2`

```java
for (int[] p1 : ones1) {
    for (int[] p2 : ones2) {
```

Suppose:

```text
p1 = (0,0)
p2 = (1,1)
```

We calculate:

```java
int rowShift = p2[0] - p1[0];
int colShift = p2[1] - p1[1];
```

Therefore:

```text
rowShift = 1 - 0 = 1
colShift = 1 - 0 = 1
```

So:

```text
translation = (1,1)
```

---

### Step 3: Count how often each translation occurs

We use:

```java
Map<String, Integer> map = new HashMap<>();
```

The key represents the translation.

For example:

```text
"1,1"
"0,2"
"-1,0"
...
```

Then:

```java
int count = map.getOrDefault(key, 0) + 1;
map.put(key, count);
```

If `(1,1)` occurs 3 times:

```text
(1,1) → 3
```

that means translating `img1` by `(1,1)` creates **3 overlapping `1`s**.

---

### Step 4: Keep the maximum

```java
answer = Math.max(answer, count);
```

If we have:

```text
translation    overlap
-----------------------
(1,1)             3
(0,1)             2
(-1,2)            1
```

the answer is:

```text
3
```

---

# 6. Why We Don't Need to Actually Move the Matrix

This is the most important part of the solution.

A brute-force approach could literally create a shifted matrix for every possible movement and compare the two matrices.

But that's unnecessary.

Consider:

```text
img1 1 → moves → overlaps with img2 1
```

The only thing that matters is **how much we moved it**.

If three different pairs of pixels all require:

```text
rowShift = 1
colShift = 1
```

then all three pairs will overlap after the same movement.

Therefore, instead of moving the image, we just count:

```text
How many pairs of 1s have the same relative position?
```

That's exactly what the `HashMap` does.

---

# 7. Example Walkthrough

Consider:

```text
img1:

1 1
0 1
```

Positions:

```text
(0,0)
(0,1)
(1,1)
```

And:

```text
img2:

0 1
1 1
```

Positions:

```text
(0,1)
(1,0)
(1,1)
```

Now compare pairs.

For example:

```text
img1 (0,0)
img2 (0,1)
```

gives:

```text
shift = (0,1)
```

Another pair:

```text
img1 (0,1)
img2 (1,1)
```

gives:

```text
shift = (1,0)
```

Another:

```text
img1 (1,1)
img2 (1,1)
```

gives:

```text
shift = (0,0)
```

We continue this for every pair.

Whichever shift appears the most times gives the maximum overlap.

---

# 8. Complexity

Let:

```text
a = number of 1s in img1
b = number of 1s in img2
```

Finding the positions takes:

```text
O(n²)
```

Then we compare every `1` in `img1` with every `1` in `img2`:

```text
O(a × b)
```

In the worst case:

```text
a = n²
b = n²
```

Therefore:

```text
Time: O(n⁴)
Space: O(n⁴)
```

However, the constraints are only:

```text
n <= 30
```

so this approach is completely practical.

---

## 9. The Main Pattern to Remember

This problem is a good example of a very useful problem-solving technique:

> **Don't perform an expensive physical operation if you can represent its effect mathematically.**

Instead of:

```text
Move image
↓
Compare images
↓
Move image again
↓
Compare again
```

we do:

```text
Take two 1s
↓
Calculate required translation
↓
Count that translation
↓
Find the most frequent translation
```

The key formula is:

```text
rowShift = row2 - row1
colShift = col2 - col1
```

And the key insight is:

```text
Most frequent translation
        =
Maximum possible overlap
```

This is the core idea you should focus on understanding rather than memorizing the code.
