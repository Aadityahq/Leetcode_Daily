# ✅ **Understanding the Problem**

We have two types of characters:

1. **One-bit character** → represented by `0`
2. **Two-bit character** → represented by `10` or `11`

The array always **ends with 0**, and we need to check if this **last 0 is a one-bit character**.

---

# ✅ **Key Idea**

To decode characters from left to right:

* If we see a `0` → it takes **1 bit** → move `i = i + 1`
* If we see a `1` → it must start a **two-bit character** → move `i = i + 2`

So we simulate reading characters until we reach the end.

The **important part**:

We only care about whether **we land exactly on the last index** (`n-1`).
If yes → last char is a one-bit character.
If no → last char is part of a two-bit character.

---

# ✅ **Walking Through the Code**

```java
class Solution {
    public boolean isOneBitCharacter(int[] bits) {
        final int n = bits.length;
        int i = 0;
        
        while (i < n - 1) {
            i += 1 + bits[i];
        }
        
        return i == n - 1;
    }
}
```

Let’s break it down:

---

## 🔹 `while (i < n - 1)`

We stop **before the last bit** because we want to check if the last bit is used as part of a character or stands alone.

---

## 🔹 `i += 1 + bits[i]`

This line is smart and compact.

* If `bits[i] == 0`:
  → `i += 1 + 0` → `i += 1` (one-bit char)

* If `bits[i] == 1`:
  → `i += 1 + 1` → `i += 2` (two-bit char)

So this line does the entire character decoding logic!

---

## 🔹 `return i == n - 1`

After decoding:

✔ **If `i == n - 1`** → decoding stopped exactly on last bit → last bit is a **one-bit char**.

✘ **If `i > n - 1`** → last bit was consumed inside a **two-bit char**.

---

# ✅ **Example Walkthroughs**

## Example 1

`bits = [1,0,0]`

* i = 0 → bits[0] = 1 → i += 2 → i = 2
  Loop stops.

i == n-1?
2 == 2 → **true**

Last bit is a one-bit character.

---

## Example 2

`bits = [1,1,1,0]`

* i = 0 → bits[0] = 1 → i = 2
* i = 2 → bits[2] = 1 → i = 4
  Loop stops (i >= n-1).

i == 3?
4 != 3 → **false**

Last `0` is part of the two-bit character ending at index 3.

---

# 🎯 Final Explanation Summary

* Traverse characters correctly by jumping 1 or 2 steps.
* Stop before the last bit.
* If decoding lands exactly on the last bit → it is a one-bit character.
* Otherwise, that last `0` is part of a two-bit character.


