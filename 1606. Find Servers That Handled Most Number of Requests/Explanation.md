# 1606. Find Servers That Handled Most Number of Requests

**Difficulty:** Hard  
**Topics:** Array, Binary Search, Ordered Set, Heap, Simulation  
**Approach:** TreeSet + PriorityQueue (Min Heap)

## 1. Problem Explanation

We are given:
- `k` servers numbered from `0` to `k-1`.
- `arrival[i]` represents the arrival time of the ith request.
- `load[i]` represents the time required to process the ith request.

Each server can handle only one request at a time.

We need to assign requests to servers according to the following rules:

1. The ith request first tries to get server `i % k`.
2. If that server is busy, we look for the next available server.
3. If no server is available, the request is dropped.
4. Once a server completes its request, it becomes available again.

Our task is to return the IDs of the servers that successfully handled the maximum number of requests.

## 2. Intuition

The main challenge is to efficiently manage two things:

1. Which servers are currently available?
2. When will busy servers become available again?

Instead of checking every server for every request, we can use two data structures.

### A. TreeSet – Available Servers

We use a `TreeSet<Integer>` named `free`.

It stores all currently available server IDs in sorted order.

Why TreeSet?

- It keeps server IDs sorted automatically.
- `ceiling(pref)` finds the smallest available server ID greater than or equal to `pref`.
- If no such server exists, we use `first()` to wrap around to the smallest available server.
- `remove()` removes the assigned server.

This helps us find the correct available server efficiently.

### B. PriorityQueue – Busy Servers

We use a min heap named `busy`.

Each entry contains:

`[completionTime, serverId]`

For example:

`[6, 1]`

This means server `1` will become available at time `6`.

Why PriorityQueue?

- It keeps the server with the earliest completion time at the top.
- Before processing a new request, we check whether any busy server has completed its previous request.
- If its completion time is less than or equal to the current arrival time, we move it back into the `free` TreeSet.

### C. Count Requests

We maintain an integer array:

`cnt[i]`

It stores how many requests server `i` has successfully handled.

After processing all requests:
- Find the maximum value in `cnt`.
- Return every server whose count equals that maximum.

## 3. Java Solution

```java
class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        // Stores available server IDs in sorted order
        TreeSet<Integer> free = new TreeSet<>();

        for (int i = 0; i < k; i++) {
            free.add(i);
        }

        // Min Heap: [completionTime, serverId]
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        // Counts requests handled by each server
        int[] cnt = new int[k];

        int n = arrival.length;

        for (int i = 0; i < n; i++) {

            long t = arrival[i];

            // Release all servers whose requests are completed
            while (!busy.isEmpty() && busy.peek()[0] <= t) {

                free.add((int) busy.poll()[1]);
            }

            // If no server is available, drop the request
            if (free.isEmpty()) {
                continue;
            }

            // Preferred server for the current request
            int pref = i % k;

            // Find the next available server
            Integer s = free.ceiling(pref);

            // Wrap around if no server is available after pref
            if (s == null) {
                s = free.first();
            }

            // Assign the request to the selected server
            free.remove(s);

            // Increase the request count
            cnt[s]++;

            // Calculate completion time and mark server as busy
            busy.offer(new long[]{t + load[i], s});
        }

        // Find the maximum number of requests handled
        int mx = 0;

        for (int c : cnt) {
            mx = Math.max(mx, c);
        }

        // Collect all busiest servers
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (cnt[i] == mx) {
                ans.add(i);
            }
        }

        return ans;
    }
}
```

## 4. Dry Run

Let's understand the algorithm using Example 1.

**Input:**

```text
k = 3

arrival = [1, 2, 3, 4, 5]
load    = [5, 2, 3, 3, 3]
```

Initially:

```text
Available Servers = [0, 1, 2]
Busy Servers      = []
Count             = [0, 0, 0]
```

### Request 0

```text
i = 0
arrival = 1
load = 5
pref = 0 % 3 = 0
```

- Server 0 is available.
- Assign request to server 0.
- Completion time = 1 + 5 = 6.

```text
Available = [1, 2]
Busy      = [[6, 0]]
Count     = [1, 0, 0]
```

### Request 1

```text
i = 1
arrival = 2
load = 2
pref = 1 % 3 = 1
```

- Server 0 is busy until time 6.
- Server 1 is available.
- Assign request to server 1.
- Completion time = 2 + 2 = 4.

```text
Available = [2]
Busy      = [[4, 1], [6, 0]]
Count     = [1, 1, 0]
```

### Request 2

```text
i = 2
arrival = 3
load = 3
pref = 2 % 3 = 2
```

- Server 1 is busy until time 4.
- Server 0 is busy until time 6.
- Server 2 is available.
- Assign request to server 2.
- Completion time = 3 + 3 = 6.

```text
Available = []
Busy      = [[4, 1], [6, 0], [6, 2]]
Count     = [1, 1, 1]
```

### Request 3

```text
i = 3
arrival = 4
load = 3
pref = 3 % 3 = 0
```

- Server 1 has completed its request at time 4.
- Move server 1 back into the available set.
- Server 0 and server 2 are still busy.
- The next available server starting from 0 is server 1.
- Assign request to server 1.
- Completion time = 4 + 3 = 7.

```text
Available = []
Busy      = [[6, 0], [6, 2], [7, 1]]
Count     = [1, 2, 1]
```

### Request 4

```text
i = 4
arrival = 5
load = 3
pref = 4 % 3 = 1
```

- Server 0 is busy until time 6.
- Server 1 is busy until time 7.
- Server 2 is busy until time 6.
- No server is available.
- Drop this request.

```text
Available = []
Count     = [1, 2, 1]
```

### Final Result

```text
Server 0 handled 1 request.
Server 1 handled 2 requests.
Server 2 handled 1 request.
```

The maximum number of requests handled is 2.

**Output:**

```text
[1]
```

## 5. Complete Request Summary

| Request | Arrival | Preferred Server | Assigned Server | Completion |
|---|---:|---:|---:|---:|
| 0 | 1 | 0 | 0 | 6 |
| 1 | 2 | 1 | 1 | 4 |
| 2 | 3 | 2 | 2 | 6 |
| 3 | 4 | 0 | 1 | 7 |
| 4 | 5 | 1 | Dropped | - |

Final request counts:

```text
cnt = [1, 2, 1]
```

## 6. Why Do We Use TreeSet and PriorityQueue?

| Data Structure | Purpose | Important Operation |
|---|---|---|
| TreeSet | Store available servers in sorted order | `ceiling()`, `first()`, `remove()` |
| PriorityQueue | Track busy servers by completion time | `peek()`, `poll()`, `offer()` |
| Array | Count handled requests | `cnt[i]++` |

**Why not use a normal array to find available servers?**

If we check every server one by one for every request, the time complexity can become O(n × k).

Using TreeSet allows us to find the next available server in O(log k) time.

## 7. Time and Space Complexity

Let:
- `n` = number of requests.
- `k` = number of servers.

### Time Complexity: O((n + k) log k)

1. Initializing the TreeSet takes O(k log k).
2. Every server assignment takes O(log k).
3. Every server release involves TreeSet insertion and PriorityQueue removal, each taking O(log k).
4. Every request is inserted into the heap at most once.
5. Finding the maximum count takes O(k).

Therefore, the overall time complexity is:

**O((n + k) log k)**

### Space Complexity: O(n + k)

- TreeSet stores at most k available servers.
- PriorityQueue stores at most k busy servers.
- Count array stores k values.
- The heap and TreeSet together contain at most k servers.

Therefore, the auxiliary space complexity is:

**O(k)**

## 8. Important Concepts to Remember

1. **TreeSet:** Useful when we need sorted elements and efficient searching.
2. **ceiling(x):** Returns the smallest element greater than or equal to x.
3. **first():** Returns the smallest element in the set.
4. **PriorityQueue:** Useful for processing events in increasing order of completion time.
5. **Min Heap:** Keeps the smallest completion time at the top.
6. **Simulation:** We process requests in arrival order and update server availability.

### Important Edge Case

We use:

```java
busy.peek()[0] <= t
```

instead of:

```java
busy.peek()[0] < t
```

Why?

If a server finishes its request exactly when a new request arrives, it is already available and can handle the new request.

Also, we use `long` for completion time because:

```java
arrival[i] + load[i]
```

can exceed the maximum value of an `int`.

## 9. Final Takeaway

The key idea is to divide servers into two groups:

- **Free servers:** Managed using TreeSet.
- **Busy servers:** Managed using a min PriorityQueue.

For each incoming request:

1. Release all completed servers.
2. Find the next available server using TreeSet.
3. Assign the request and update its count.
4. Store its completion time in the min heap.
5. Finally, return all servers with the highest request count.

**Remember:** TreeSet helps us find *which server* to assign, while PriorityQueue helps us determine *when a server becomes available again*.