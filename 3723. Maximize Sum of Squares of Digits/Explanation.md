# LeetCode 3723: Maximize Sum of Squares of Digits

Approach: Greedy Algorithm Language: Java Difficulty: Medium

## 1. Problem explanation

You are given two integers:

- `num` — the exact number of digits the answer must contain.
- `sum` — the sum of all digits in the answer.

You need to construct a number that satisfies both conditions and maximizes the sum of the squares of its digits.

If multiple numbers have the same maximum score, return the numerically largest number as a string.

If no valid number exists, return an empty string `""`.

### Example

Input:

```
num = 2, sum = 3
```

Possible numbers are `12`, `21`, and `30`.

| Number | Digit sum | Sum of squares      |
| ------ | --------- | ------------------- |
| 12     | 3         | \\(1^2 + 2^2 = 5\\) |
| 21     | 3         | \\(2^2 + 1^2 = 5\\) |
| 30     | 3         | \\(3^2 + 0^2 = 9\\) |

Output:

```
"30"
```

The answer is `30` because its sum of squared digits is the largest.

## 2. Java solution

```

class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < num; i++) {
            int digit = Math.min(9, sum);

            result.append(digit);
            sum -= digit;
        }

        return result.toString();
    }
}

```

## 3. Explanation: How and why does it work?

### Step 1: Check whether a valid number exists

Every digit can have a maximum value of `9`.

Therefore, the maximum possible digit sum for `num` digits is:

\\[ \text{maximum sum} = 9 \times num \\]

If `sum` is greater than this value, no valid number can exist.

```
if (sum > 9 * num) {
    return "";
}
```

For example, if `num = 2` and `sum = 19`, the maximum possible sum is \\(9 + 9 = 18\\). So, we return `""`.

### Step 2: Create the answer from left to right

We want to maximize:

\\[ d_1^2+d_2^2+\cdots+d\_{num}^2 \\]

The key observation is that concentrating the digit sum into larger digits produces a greater sum of squares.

For example, if the digit sum is `8`:

- `4, 4` gives \\(4^2+4^2=32\\).
- `8, 0` gives \\(8^2+0^2=64\\).

Therefore, we should put as much of the remaining sum as possible into each digit, up to `9`.

```
int digit = Math.min(9, sum);
```

This line selects the smaller of `9` and the remaining sum.

- If `sum = 17`, choose digit `9`.
- If `sum = 8`, choose digit `8`.
- If `sum = 0`, choose digit `0`.

### Step 3: Add the digit and update the sum

```
result.append(digit);
sum -= digit;
```

We add the selected digit to the answer and subtract it from the remaining sum.

We repeat this process until we have exactly `num` digits.

### Step 4: Return the result

```
return result.toString();
```

We use `StringBuilder` because the answer can contain up to \\(2 \times 10^5\\) digits. Using a string also allows us to return the answer without converting it into a potentially overflowing integer.

## 4. Dry run

Input:

```
num = 3
sum = 20
```

Initially, `result = ""`.

| Iteration | Remaining sum | Chosen digit | Result  |
| --------- | ------------- | ------------ | ------- |
| 1         | 20            | 9            | `"9"`   |
| 2         | 11            | 9            | `"99"`  |
| 3         | 2             | 2            | `"992"` |

Output:

```
"992"
```

Check the answer:

- Number of digits = 3
- Digit sum = \\(9 + 9 + 2 = 20\\)
- Sum of squares = \\(81 + 81 + 4 = 166\\)

We cannot increase either of the first two digits because they are already `9`. Thus, the greedy approach maximizes the score.

## 5. Why do we choose the largest digit first?

Suppose we have two digits \\(a\\) and \\(b\\), where \\(a \ge b\\).

Moving one unit from the smaller digit to the larger digit changes their squared sum by:

\\[ \begin{aligned} \Delta &=(a+1)^2+(b-1)^2-a^2-b^2\\\ &=2(a-b)+2 \end{aligned} \\]

Since \\(a \ge b\\), this change is positive.

That means making an already larger digit even larger, while reducing a smaller digit, increases the score. We continue this process until one digit reaches `9` or the other reaches `0`.

Also, putting the largest possible digit first ensures the numerically largest answer among the maximum-score answers.

This is why the greedy algorithm works.

## 6. Complexity analysis

Let \\(n\\) be the value of `num`.

- Time complexity: \\(O(n)\\), because we process each digit once.
- Auxiliary space complexity: \\(O(n)\\), because the result stores `num` digits.

The solution efficiently handles the maximum constraint of 200,000 digits.

## 7. Important edge cases

| Input (`num`, `sum`) | Output  |
| -------------------- | ------- |
| `(2, 3)`             | `"30"`  |
| `(2, 17)`            | `"98"`  |
| `(1, 10)`            | `""`    |
| `(3, 27)`            | `"999"` |
| `(3, 1)`             | `"100"` |

Remember the greedy pattern: whenever you need to maximize the sum of squared digits while keeping the total digit sum fixed, fill each digit with as much of the remaining sum as possible, up to `9`.