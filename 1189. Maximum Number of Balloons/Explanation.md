## Problem Understanding

You are given a string `text`, and you need to determine how many times you can create the word **"balloon"** using its characters.

Each character from `text` can be used **only once**.

The target word is:

```text
balloon
```

Let's count the required letters:

| Character | Required Count |
| --------- | -------------- |
| b         | 1              |
| a         | 1              |
| l         | 2              |
| o         | 2              |
| n         | 1              |

Notice that:

* `l` appears **twice**
* `o` appears **twice**
* All other letters appear **once**

The answer depends on the **limiting character** (the one that runs out first).

---

## Example Walkthrough

### Example 1

```text
text = "nlaebolko"
```

Character frequencies:

```text
b = 1
a = 1
l = 2
o = 2
n = 1
```

Possible balloons:

```text
min(1, 1, 2/2, 2/2, 1)
= min(1, 1, 1, 1, 1)
= 1
```

Answer: `1`

---

### Example 2

```text
text = "loonbalxballpoon"
```

Frequencies:

```text
b = 2
a = 2
l = 4
o = 4
n = 2
```

Possible balloons:

```text
min(2, 2, 4/2, 4/2, 2)
= min(2, 2, 2, 2, 2)
= 2
```

Answer: `2`

---

## Intuition

To build `"balloon"`:

* Every balloon needs:

  * 1 `b`
  * 1 `a`
  * 2 `l`
  * 2 `o`
  * 1 `n`

So:

1. Count the frequency of every character in `text`.
2. Divide the counts of `l` and `o` by `2` because each balloon requires two of them.
3. The minimum among all required characters is the maximum number of balloons we can make.

---

## Why Does This Work?

Suppose you have:

```text
b = 5
a = 4
l = 8
o = 6
n = 3
```

You might think you can make many balloons, but `n = 3` means you can only create **3** complete `"balloon"` words.

Even though other letters are available in larger quantities, the smallest available requirement determines the answer.

This is a classic **frequency counting + minimum constraint** problem.

---

## Java Solution

```java
class Solution {
    public int maxNumberOfBalloons(String text) {
        int[] freq = new int[26];

        // Count frequency of each character
        for (char ch : text.toCharArray()) {
            freq[ch - 'a']++;
        }

        return Math.min(
                Math.min(freq['b' - 'a'], freq['a' - 'a']),
                Math.min(
                        Math.min(freq['l' - 'a'] / 2, freq['o' - 'a'] / 2),
                        Math.min(freq['n' - 'a'], Integer.MAX_VALUE)
                )
        );
    }
}
```

---

## Cleaner Version

```java
class Solution {
    public int maxNumberOfBalloons(String text) {
        int[] count = new int[26];

        for (char ch : text.toCharArray()) {
            count[ch - 'a']++;
        }

        return Math.min(
            Math.min(count['b' - 'a'], count['a' - 'a']),
            Math.min(
                Math.min(count['l' - 'a'] / 2, count['o' - 'a'] / 2),
                count['n' - 'a']
            )
        );
    }
}
```

---

## Dry Run

```text
text = "loonbalxballpoon"
```

Frequency array:

```text
a = 2
b = 2
l = 4
o = 4
n = 2
```

Adjust for repeated letters:

```text
l = 4 / 2 = 2
o = 4 / 2 = 2
```

Take minimum:

```text
min(2, 2, 2, 2, 2) = 2
```

Answer:

```text
2
```

---

## Complexity Analysis

* **Time Complexity:** `O(n)`

  * We traverse the string once.

* **Space Complexity:** `O(1)`

  * The frequency array always has size `26`.

---

## Key Takeaway

Whenever a problem asks:

> "How many times can you build a target word from given characters?"

Think:

1. Count character frequencies.
2. Compare with required frequencies.
3. The minimum ratio determines the answer.
