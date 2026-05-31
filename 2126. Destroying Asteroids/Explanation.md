## Problem Understanding

You have a planet with an initial `mass`.

Each asteroid has its own mass. You can choose the order in which the planet collides with the asteroids.

### Collision Rule

* If `planetMass >= asteroidMass`

  * asteroid is destroyed
  * planet gains that asteroid's mass
* Otherwise

  * planet is destroyed
  * answer is `false`

Return `true` if all asteroids can be destroyed.

---

### Example 1

```text
mass = 10
asteroids = [3,9,19,5,21]
```

Sort asteroids:

```text
[3,5,9,19,21]
```

Process:

```text
10 >= 3  -> mass = 13
13 >= 5  -> mass = 18
18 >= 9  -> mass = 27
27 >= 19 -> mass = 46
46 >= 21 -> mass = 67
```

All destroyed → `true`

---

### Key Observation (Greedy)

To maximize the chance of survival, always destroy the **smallest asteroid first**.

Why?

Suppose you can destroy a larger asteroid. Then you can definitely destroy any smaller asteroid too.

Destroying smaller asteroids first increases your mass gradually, making larger asteroids easier later.

Therefore:

1. Sort asteroids in ascending order.
2. Traverse them.
3. If current mass is smaller than an asteroid → return `false`.
4. Otherwise add asteroid mass to current mass.

---

## Algorithm

1. Sort `asteroids`.
2. Store mass in a `long` variable (important because mass can become very large).
3. For every asteroid:

   * If `currentMass < asteroid`

     * return `false`
   * Else

     * `currentMass += asteroid`
4. Return `true`.

---

## Dry Run

```text
mass = 5
asteroids = [4,9,23,4]
```

After sorting:

```text
[4,4,9,23]
```

Process:

```text
5 >= 4   -> mass = 9
9 >= 4   -> mass = 13
13 >= 9  -> mass = 22
22 < 23  -> cannot destroy
```

Return `false`.

---

## Java Solution

```java
import java.util.Arrays;

class Solution {
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        Arrays.sort(asteroids);

        long currMass = mass;

        for (int asteroid : asteroids) {
            if (currMass < asteroid) {
                return false;
            }

            currMass += asteroid;
        }

        return true;
    }
}
```

---

## Why Use `long`?

Constraints:

```text
n = 100000
asteroid[i] = 100000
```

The total mass can become:

```text
100000 × 100000 = 10^10
```

which is larger than the maximum value of `int`:

```text
2,147,483,647
```

So we must use:

```java
long currMass
```

---

## Complexity Analysis

Sorting:

```text
O(n log n)
```

Traversal:

```text
O(n)
```

Total:

```text
O(n log n)
```

Space:

```text
O(1)
```

(ignoring the space used by Java's sorting implementation).
