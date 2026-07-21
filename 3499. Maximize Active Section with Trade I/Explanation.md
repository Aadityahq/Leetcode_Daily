This problem looks confusing because of the wording **"trade"**. The key is to understand **what is actually happening**.

---

# Intuition

We have a binary string.

* `1` = active
* `0` = inactive

We can perform **at most one trade**.

A trade consists of **two mandatory steps**:

1. Pick a block of consecutive `1`s that is surrounded by `0`s and change it to `0`s.
2. Then pick a block of consecutive `0`s that is surrounded by `1`s and change it to `1`s.

The goal is to maximize the total number of `1`s.

---

# First understand the augmentation

The problem says to imagine

```
t = "1" + s + "1"
```

Example

```
s = 0100

t = 101001
```

These extra `1`s are **only for checking whether a block is surrounded**.

They are **not counted** in the final answer.

---

# What does "surrounded" mean?

A block is surrounded if both sides are opposite characters.

Example

```
0011100
```

The block

```
111
```

has

```
0 111 0
```

So it is surrounded.

---

Example

```
111000
```

The left block of ones

```
111
```

is **not surrounded**

because

```
111000
^ beginning
```

There is no zero on the left.

But after augmentation,

```
111000

↓

11110001
```

Now we check again.

---

# Step 1

Choose one surrounded block of ones.

Example

```
010111010
```

```
0 111 0
```

Turn it into

```
000000010
```

We lose

```
length_of_block
```

ones.

---

# Step 2

Now choose one surrounded block of zeros.

Example

```
100001
```

```
1 0000 1
```

Turn it into

```
111111
```

Now we gain

```
length_of_zero_block
```

ones.

---

# Important Observation

Suppose we have

```
111000111
```

The zero block is

```
000
```

It is surrounded by two blocks of ones.

If we remove **either** adjacent one block,

```
111000111

↓

000000111
```

or

```
111000000
```

the two zero blocks merge.

Eventually the whole region

```
111000111
```

becomes

```
000000000
```

Then step 2 changes everything into ones.

Final

```
111111111
```

So

```
111 + 000 +111
```

becomes all ones.

---

# Huge Observation

Whenever we pick a surrounded one block,

the only zero block that becomes larger is

```
leftZeros + removedOnes + rightZeros
```

Example

```
00011100
```

Remove

```
111
```

↓

```
00000000
```

Now the zero block length is

```
3 + 3 + 2 = 8
```

If we convert it back,

we gain

```
8
```

ones.

But we already lost

```
3
```

ones.

Net gain

```
8-3 = 5
```

Notice

```
5 = leftZeros + rightZeros
```

So the removed ones cancel out.

---

# Therefore

Suppose

```
zeros_left = L
ones = M
zeros_right = R
```

Current ones

```
totalOnes
```

Trade result

```
totalOnes - M + (L+M+R)

=
totalOnes + L + R
```

Amazing!

The middle ones disappear mathematically.

The answer only depends on

```
left zero block
+
right zero block
```

---

# So the problem becomes

Find every pattern

```
0...0
1...1
0...0
```

For each

```
gain

=

leftZeros
+
rightZeros
```

Maximum answer

```
totalOnes + gain
```

---

# Example

```
0100
```

Current ones

```
1
```

Runs

```
0
1
00
```

Left zero

```
1
```

Right zero

```
2
```

Gain

```
3
```

Answer

```
1+3=4
```

Correct.

---

Example

```
01010
```

Runs

```
0
1
0
1
0
```

Current ones

```
2
```

First one block

Gain

```
1+1=2
```

Answer

```
4
```

Second one block

Again

```
2
```

Answer

```
4
```

Correct.

---

Example

```
01
```

Augmented

```
1011
```

There is **no surrounded one block**.

No trade possible.

Answer

```
1
```

---

# Efficient Implementation

Instead of scanning characters repeatedly,

compress the string into runs.

Example

```
00011100111
```

becomes

```
(0,3)
(1,3)
(0,2)
(1,3)
```

Store

```
character
length
```

Then iterate through runs.

Whenever

```
0-run
1-run
0-run
```

appears,

calculate

```
gain

=

leftZeroLength
+
rightZeroLength
```

Answer

```
max(answer,
totalOnes+gain)
```

---

# Why does this work?

The trade always removes exactly one surrounded `1` block.

Removing it merges the zero blocks on both sides into one larger zero block.

Changing that merged zero block back into ones restores the removed ones and additionally converts the left and right zero blocks to ones.

Hence:

```
Final Ones

=

Initial Ones

+

Left Zero Length

+

Right Zero Length
```

No other arrangement can produce a larger gain because only the two neighboring zero blocks are affected by removing that specific one block.

---

# Complexity

Let **n** be the string length.

* Counting runs: **O(n)**
* Counting ones: **O(n)**
* Scanning runs: **O(n)**

Overall:

* **Time:** `O(n)`
* **Space:** `O(n)` (or `O(1)` extra if processed on the fly)

---

# Java Solution

```java
class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n = s.length();
        int ones = 0;

        for (char c : s.toCharArray()) {
            if (c == '1') ones++;
        }

        // Augment the string
        String t = "1" + s + "1";

        List<Character> chars = new ArrayList<>();
        List<Integer> len = new ArrayList<>();

        int i = 0;
        while (i < t.length()) {
            char ch = t.charAt(i);
            int j = i;
            while (j < t.length() && t.charAt(j) == ch) j++;

            chars.add(ch);
            len.add(j - i);

            i = j;
        }

        int ans = ones;

        for (int k = 1; k + 1 < chars.size(); k++) {
            if (chars.get(k) == '1'
                    && chars.get(k - 1) == '0'
                    && chars.get(k + 1) == '0') {

                ans = Math.max(ans,
                        ones + len.get(k - 1) + len.get(k + 1));
            }
        }

        return ans;
    }
}
```

### Key insight to remember

If a surrounded `1` block has:

* left zero block of length **L**
* right zero block of length **R**

then performing the optimal trade centered on that block changes the total number of active sections from:

```
initialOnes
```

to

```
initialOnes + L + R
```

So you never need to simulate the trade—just evaluate every `0-run → 1-run → 0-run` pattern and take the maximum gain.
