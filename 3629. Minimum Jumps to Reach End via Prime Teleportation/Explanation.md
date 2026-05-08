# Intuition

We need the **minimum jumps**, and every jump has equal cost (`1`), so this is a classic case for:

> **BFS (Breadth First Search)**

because BFS always finds the shortest path in an unweighted graph.

---

# Understanding the Problem

From index `i`, we can:

1. Move normally:

   * `i + 1`
   * `i - 1`

2. Teleport:

   * Only if `nums[i]` is a **prime number**
   * Let that prime be `p`
   * Then we can jump to **any index `j`** where:

[
nums[j] \bmod p = 0
]

---

# Example

```text
nums = [1,2,4,6]
```

At index `1`:

```text
nums[1] = 2  (prime)
```

So we can teleport to any value divisible by `2`:

* 4
* 6

So:

```text
0 -> 1 -> 3
```

Answer = `2`

---

# Key Observation

Naively, for every prime `p`, checking all indices would become:

[
O(n^2)
]

which is too slow for `10^5`.

We need optimization.

---

# Important Optimization

We precompute:

```text
prime factor -> list of indices divisible by it
```

Example:

```text
nums = [2,3,4,7,9]
```

Divisibility map:

```text
2 -> [0,2]
3 -> [1,4]
7 -> [3]
```

Now when we are at prime `3`,
we instantly know all teleport destinations.

---

# Another Important Optimization

Suppose we already used teleportation for prime `2`.

Then using prime `2` again is useless because BFS already visited those shortest paths.

So after processing a prime once:

```java
map.remove(prime);
```

This prevents repeated work.

---

# Full BFS Flow

At every index:

1. Try left
2. Try right
3. If current value is prime:

   * teleport to all divisible indices

---

# Efficient Prime Checking

Constraints:

```text
nums[i] <= 10^6
```

We use **Sieve of Eratosthenes** to precompute primes.

Complexity:

[
O(MAX \log \log MAX)
]

which is efficient.

---

# Java Solution

```java
import java.util.*;

class Solution {

    public int minJumps(int[] nums) {

        int n = nums.length;

        if (n == 1) return 0;

        int MAX = 1_000_000;

        // Sieve for prime checking
        boolean[] isPrime = sieve(MAX);

        // Map: prime factor -> list of indices divisible by it
        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < n; i++) {

            int num = nums[i];

            // Find unique prime factors
            for (int p = 2; p * p <= num; p++) {

                if (num % p == 0) {

                    map.computeIfAbsent(p, k -> new ArrayList<>()).add(i);

                    while (num % p == 0) {
                        num /= p;
                    }
                }
            }

            if (num > 1) {
                map.computeIfAbsent(num, k -> new ArrayList<>()).add(i);
            }
        }

        // BFS
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];

        q.offer(0);
        visited[0] = true;

        int steps = 0;

        while (!q.isEmpty()) {

            int size = q.size();

            while (size-- > 0) {

                int idx = q.poll();

                if (idx == n - 1) {
                    return steps;
                }

                // Move left
                if (idx - 1 >= 0 && !visited[idx - 1]) {
                    visited[idx - 1] = true;
                    q.offer(idx - 1);
                }

                // Move right
                if (idx + 1 < n && !visited[idx + 1]) {
                    visited[idx + 1] = true;
                    q.offer(idx + 1);
                }

                int val = nums[idx];

                // Teleport if current number is prime
                if (isPrime[val] && map.containsKey(val)) {

                    for (int next : map.get(val)) {

                        if (!visited[next]) {
                            visited[next] = true;
                            q.offer(next);
                        }
                    }

                    // Important optimization
                    map.remove(val);
                }
            }

            steps++;
        }

        return -1;
    }

    // Sieve of Eratosthenes
    private boolean[] sieve(int n) {

        boolean[] prime = new boolean[n + 1];

        Arrays.fill(prime, true);

        prime[0] = false;
        prime[1] = false;

        for (int i = 2; i * i <= n; i++) {

            if (prime[i]) {

                for (int j = i * i; j <= n; j += i) {
                    prime[j] = false;
                }
            }
        }

        return prime;
    }
}
```

---

# Dry Run

## Input

```text
nums = [2,3,4,7,9]
```

---

## Step 0

Queue:

```text
[0]
```

At index `0`:

* left ❌
* right → `1`
* `nums[0]=2` prime

Teleport to indices divisible by `2`:

```text
[0,2]
```

Add `2`

Queue:

```text
[1,2]
```

Steps = 1

---

## Step 1

Process `1`

`nums[1]=3` prime

Teleport to:

```text
[1,4]
```

Add `4`

Now queue contains destination.

Steps = 2

---

Process `4`

Reached end.

Answer = `2`

---

# Time Complexity

## Building factor map

Each number factorized in roughly:

[
O(\sqrt{nums[i]})
]

Efficient enough for constraints.

---

## BFS

Each index visited once.

Each prime teleport list processed once.

So overall:

[
O(n + MAX\log\log MAX)
]

---

# Why BFS Works

Because:

* every move costs exactly `1`
* BFS explores all positions reachable in:

  * 1 jump
  * then 2 jumps
  * then 3 jumps

So first time reaching `n-1` is guaranteed minimum.

---

# Core Idea Summary

1. Treat indices as graph nodes
2. Use BFS for shortest path
3. Precompute divisible indices using prime factors
4. Process each prime teleport only once

That is the entire optimization.
