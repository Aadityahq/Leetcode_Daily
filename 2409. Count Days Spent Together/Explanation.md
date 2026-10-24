# LeetCode 2409: Count Days Spent Together

**Difficulty:** Easy  
**Topics:** String, Simulation

## 1. Problem Explanation

Alice and Bob are traveling to Rome for business meetings.

We are given four dates:

- `arriveAlice`: The date Alice arrives.
- `leaveAlice`: The date Alice leaves.
- `arriveBob`: The date Bob arrives.
- `leaveBob`: The date Bob leaves.

Each date is given in the `"MM-DD"` format.

Both Alice and Bob stay in Rome from their arrival date to their leaving date, **including both dates**.

Our task is to return the total number of days they spend together in Rome.

### Example

**Input:**
```text
arriveAlice = "08-15"
leaveAlice  = "08-18"
arriveBob   = "08-16"
leaveBob    = "08-19"
```

**Output:**
```text
3
```

**Explanation:**

Alice stays from August 15 to August 18.

Bob stays from August 16 to August 19.

Their common days are:

- August 16
- August 17
- August 18

Therefore, the answer is `3`.

---

## 2. Approach: Convert Dates into Day Numbers

### How does this approach work?

Comparing dates directly as strings can be confusing. Instead, we can convert every date into a day number representing its position in the year.

For example:

- January 1 → Day 1
- January 31 → Day 31
- February 1 → Day 32
- March 1 → Day 60

We can use an array to store the number of days in each month:

```java
int[] days = {
    31, 28, 31, 30, 31, 30,
    31, 31, 30, 31, 30, 31
};
```

Since the year is not a leap year, February has 28 days.

### Step 1: Convert each date

For a date such as `"08-15"`:

- Month = 8
- Day = 15

To calculate its day number, add the days in all previous months and then add the current day.

For example, August 15:

```text
January   = 31
February  = 28
March     = 31
April     = 30
May       = 31
June      = 30
July      = 31
August    = 15
----------------
Total     = 227
```

Therefore, August 15 corresponds to day 227.

### Step 2: Find the overlapping interval

Let:

- `start` = the later of the two arrival dates.
- `end` = the earlier of the two leaving dates.

In Java:

```java
int start = Math.max(arriveAlice, arriveBob);
int end = Math.min(leaveAlice, leaveBob);
```

**Why use `Math.max()` for the start?**

Both people are together only after both have arrived. Therefore, we choose the later arrival date.

**Why use `Math.min()` for the end?**

They can stay together only until the first person leaves. Therefore, we choose the earlier leaving date.

### Step 3: Calculate the answer

If `start <= end`, the dates overlap, so the number of common days is:

```java
end - start + 1
```

We add `1` because both the start and end dates are included.

If `start > end`, there is no overlap, so the answer is `0`.

---

## 3. Java Solution

```java
class Solution {

    private int convertToDays(String date) {
        int[] days = {
            31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31
        };

        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(3, 5));

        int totalDays = day;

        for (int i = 0; i < month - 1; i++) {
            totalDays += days[i];
        }

        return totalDays;
    }

    public int countDaysTogether(
        String arriveAlice,
        String leaveAlice,
        String arriveBob,
        String leaveBob
    ) {
        int aliceStart = convertToDays(arriveAlice);
        int aliceEnd = convertToDays(leaveAlice);

        int bobStart = convertToDays(arriveBob);
        int bobEnd = convertToDays(leaveBob);

        int start = Math.max(aliceStart, bobStart);
        int end = Math.min(aliceEnd, bobEnd);

        if (start > end) {
            return 0;
        }

        return end - start + 1;
    }
}
```

---

## 4. Dry Run

Consider Example 1:

```text
arriveAlice = "08-15"
leaveAlice  = "08-18"
arriveBob   = "08-16"
leaveBob    = "08-19"
```

### Step 1: Convert dates into day numbers

| Date | Calculation | Day number |
|---|---|---:|
| August 15 | 212 + 15 | 227 |
| August 18 | 212 + 18 | 230 |
| August 16 | 212 + 16 | 228 |
| August 19 | 212 + 19 | 231 |

There are 212 days before August begins.

### Step 2: Find the common interval

```java
start = Math.max(227, 228);  // 228
end   = Math.min(230, 231);  // 230
```

The common interval is from day 228 to day 230.

### Step 3: Count the days

```java
return 230 - 228 + 1;
```

Calculation:

```text
230 - 228 + 1 = 3
```

**Output: `3`**

---

## 5. Dry Run When There Is No Overlap

Consider Example 2:

```text
arriveAlice = "10-01"
leaveAlice  = "10-31"
arriveBob   = "11-01"
leaveBob    = "12-31"
```

Alice leaves before Bob arrives.

The converted dates give:

```text
Alice: 274 to 304
Bob:   305 to 365
```

Now calculate:

```java
start = Math.max(274, 305);  // 305
end   = Math.min(304, 365);  // 304
```

Since:

```text
start > end
305 > 304
```

There is no overlap.

**Output: `0`**

---

## 6. Why Do We Add `+1`?

Suppose the common dates are August 16, August 17, and August 18.

Their day numbers are 228, 229, and 230.

If we calculate:

```java
230 - 228
```

The answer is `2`, but there are actually three days.

Therefore, we add `1`:

```java
230 - 228 + 1 = 3
```

This is necessary because the problem includes both the arrival and leaving dates.

---

## 7. Complexity Analysis

Let `n` represent the maximum number of days in a year.

**Time Complexity: O(1)**

Each date conversion checks at most 11 months. We convert four dates, so the number of operations is bounded by a constant.

**Space Complexity: O(1)**

The month array has a fixed size of 12, and we use only a constant number of additional variables.

---

## 8. Key Takeaways

- Convert dates into numerical day values to make comparisons easier.
- The common start date is the later arrival date: `Math.max()`.
- The common end date is the earlier leaving date: `Math.min()`.
- If `start > end`, return `0`.
- Otherwise, return `end - start + 1` because the interval is inclusive.

**The main idea:** Find the intersection of two date intervals and count the days in that intersection.
