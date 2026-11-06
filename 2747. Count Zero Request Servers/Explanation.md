# LeetCode 2747. Count Zero Request Servers



Medium

Sliding Window + Sorting + HashMap



The main concept behind this problem is Sliding Window with Sorting and Frequency Counting.

We need to find how many servers did not receive even a single request in a given time interval.

Let's understand the problem step by step.

## 1. Understanding the problem

You are given:

- `n`: Total number of servers. Their IDs range from `1` to `n`.
- `logs`: Contains server requests in the format `[server_id, time]`.
- `x`: Size of the time interval.
- `queries`: Each query represents a particular ending time.

For every query, we need to count the servers that received zero requests during this interval:

\\[ [query-x,\ query] \\]

Remember: Both the starting and ending times are included.

### Example

```
n = 3

logs = [[1,3], [2,6], [1,5]]

x = 5

queries = [10, 11]
```

We have three servers:

- Server 1
- Server 2
- Server 3

Query 1: query = 10

Time interval:

\\[ [10-5,\ 10] = [5,10] \\]

Requests inside this interval:

| Server   | Request time |
| -------- | ------------ |
| Server 1 | 5            |
| Server 2 | 6            |
| Server 3 | No request   |

Servers 1 and 2 received requests.

Therefore, only server 3 received zero requests.

Answer = `1`.

Query 2: query = 11

Time interval:

\\[ [11-5,\ 11] = [6,11] \\]

Requests inside this interval:

| Server   | Request time |
| -------- | ------------ |
| Server 1 | No request   |
| Server 2 | 6            |
| Server 3 | No request   |

Only server 2 received a request.

Therefore, servers 1 and 3 received zero requests.

Answer = `2`.

Final output:

```
[1, 2]
```

## 2. Java Solution (Optimized)

We will use:

1. Sorting
2. Sliding Window
3. HashMap
4. Two Pointers

### Code (Java)

```

import java.util.*;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        // Step 1: Sort logs based on request time
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        // Step 2: Store queries with their original indices
        int m = queries.length;
        int[][] sortedQueries = new int[m][2];

        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        // Step 3: Sort queries based on query time
        Arrays.sort(sortedQueries,
            (a, b) -> Integer.compare(a[0], b[0]));

        // Step 4: Maintain frequency of server requests
        Map<Integer, Integer> map = new HashMap<>();

        int[] ans = new int[m];

        int left = 0;
        int right = 0;

        // Step 5: Process queries using sliding window
        for (int[] q : sortedQueries) {

            int start = q[0] - x;
            int end = q[0];

            // Add logs whose time is <= end
            while (right < logs.length && logs[right][1] <= end) {

                int server = logs[right][0];

                map.put(server, map.getOrDefault(server, 0) + 1);

                right++;
            }

            // Remove logs whose time is < start
            while (left < right && logs[left][1] < start) {

                int server = logs[left][0];

                map.put(server, map.get(server) - 1);

                if (map.get(server) == 0) {
                    map.remove(server);
                }

                left++;
            }

            // Servers with no requests = total - active servers
            ans[q[1]] = n - map.size();
        }

        return ans;
    }
}

```

## 3. Understanding the approach (How and Why)

Before understanding the code, let's understand why we need these data structures.

### Step 1: Why do we sort the logs?

Initially, logs are not necessarily sorted by time.

For example:

```
logs = [[2,6], [1,3], [1,5]]
```

After sorting by request time:

```
logs = [[1,3], [1,5], [2,6]]
```

Why?

Because we want to process requests in chronological order.

When queries are processed from smaller to larger times, we can move through the logs using pointers instead of repeatedly checking every log.

This saves time.

### Step 2: Why do we sort queries?

Consider:

```
queries = [10, 3, 11]
```

These queries are not sorted.

If we process them directly, the sliding window would need to move backward and forward.

That makes the implementation more complicated.

Instead, we sort queries while preserving their original indices.

For example:

Original queries:

| Query | Original index |
| ----- | -------------- |
| 10    | 0              |
| 3     | 1              |
| 11    | 2              |

After sorting:

| Query | Original index |
| ----- | -------------- |
| 3     | 1              |
| 10    | 0              |
| 11    | 2              |

We store each query as:

```
sortedQueries[i][0] = queries[i];
sortedQueries[i][1] = i;
```

- `[0]` stores the query time.
- `[1]` stores its original index.

Why preserve the index?

Because the answer must be returned in the original order of queries, not the sorted order.

That's why we use:

```
ans[q[1]] = n - map.size();
```

### Step 3: Why do we use HashMap?

We need to know how many requests each server has received inside the current time window.

For this, we use:

```
Map<Integer, Integer> map = new HashMap<>();
```

Here:

- Key = Server ID
- Value = Number of requests received by that server inside the current window.

For example:

```
map = {
    1 : 2,
    2 : 1
}
```

This means:

- Server 1 received 2 requests.
- Server 2 received 1 request.
- Server 3 received 0 requests.

Therefore:

```
n - map.size()
```

Here:

```
3 - 2 = 1
```

So, one server received zero requests.

Why frequency instead of just storing server IDs?

Because a server can receive multiple requests.

Suppose server 1 receives requests at times 3 and 5.

If we remove the request at time 3, server 1 must still remain active because it has another request at time 5.

Frequency counting helps us handle this correctly.

### Step 4: Understanding the two pointers

We use two pointers:

```
int left = 0;
int right = 0;
```

Both pointers operate on the sorted `logs` array.

- `right` helps us add new requests to the current window.
- `left` helps us remove old requests from the current window.

Our current time window is:

\\[ [start,\ end] \\]

Where:

```
start = query - x;
end = query;
```

We perform two operations for every query.

Operation 1: Add requests to the window

```
while (right < logs.length && logs[right][1] <= end) {

    int server = logs[right][0];

    map.put(server, map.getOrDefault(server, 0) + 1);

    right++;
}
```

We add all requests whose time is less than or equal to the ending time.

Why `<= end`?

Because the ending time is inclusive.

For example, if our interval is `[5, 10]`, requests at time 10 must also be included.

Operation 2: Remove requests outside the window

```
while (left < right && logs[left][1] < start) {

    int server = logs[left][0];

    map.put(server, map.get(server) - 1);

    if (map.get(server) == 0) {
        map.remove(server);
    }

    left++;
}
```

We remove requests whose time is smaller than the starting time.

Why `< start` and not `<= start`?

Because the starting time is also inclusive.

For example, if our interval is `[5, 10]`, a request at time 5 must remain in the window.

When a server's frequency becomes zero, we remove it from the HashMap.

This is important because `map.size()` should represent only the servers that received at least one request in the current window.

## 4. Dry Run (Step-by-Step Execution)

Let's understand the complete execution.

```
n = 3

logs = [[1,3], [2,6], [1,5]]

x = 5

queries = [10, 11]
```

After sorting logs:

```
[[1,3], [1,5], [2,6]]
```

Sorted queries:

```
[[10,0], [11,1]]
```

Initially:

```
left = 0
right = 0
map = {}
```

Query 1: 10

Window:

\\[ [10-5,\ 10] = [5,10] \\]

Add requests up to time 10.

\| Request | Action | HashMap |\
\|---|---|---|\
\| [1,3] | Add |  |\
\| [1,5] | Add |  |\
\| [2,6] | Add |