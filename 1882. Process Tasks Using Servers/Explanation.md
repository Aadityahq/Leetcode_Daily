# LeetCode 1882 – Process Tasks Using Servers

Medium

Priority Queue

Greedy

## 1. Understanding the problem

We are given two arrays:

- `servers[i]` represents the weight of the ith server.
- `tasks[j]` represents the time required to complete the jth task.

We need to assign every task to a server according to these rules:

1. Choose a free server with the smallest weight.
2. If multiple servers have the same weight, choose the server with the smallest index.
3. Tasks must be assigned in their original order.
4. If all servers are busy, wait until a server becomes free.
5. Once a server finishes its task, it becomes available again.

We have to return an array `ans`, where `ans[j]` represents the index of the server assigned to task `j`.

### Example

```
servers = [3, 3, 2]
tasks   = [1, 2, 3, 2, 1, 2]
```

Output:

```
[2, 2, 0, 2, 1, 2]
```

We have three servers:

| Index | Weight |
| ----- | ------ |
| 0     | 3      |
| 1     | 3      |
| 2     | 2      |

Since server 2 has the smallest weight, it gets the first task.

## 2. Approach: Two Priority Queues

The main idea is to use two Min Heaps (Priority Queues).

Why do we need two Priority Queues?

Because we have two different situations:

- Finding the best server among available servers.
- Finding which busy server will become free first.

Let's understand both.

### A. Available Priority Queue

This stores all servers that are currently free.

Each element contains:

```
[weight, index]
```

Priority order:

1. Smaller weight.
2. Smaller index.

For example:

```
Available Servers:

[2, 2]
[3, 0]
[3, 1]
```

The first server selected will be server 2 because its weight is 2.

### B. Busy Priority Queue

This stores all servers that are currently processing tasks.

Each element contains:

```
[finishTime, weight, index]
```

Priority order:

1. Smaller finishing time.
2. Smaller weight.
3. Smaller index.

For example:

```
Busy Servers:

[5, 2, 2]
[7, 3, 0]
[9, 3, 1]
```

The first server to become free is server 2 because it finishes at time 5.

If multiple servers finish at the same time, we release all of them.

## 3. Java Solution

This is the corrected solution using `long` for finishing times.

```

import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        int[] ans = new int[m];

        // Available servers: [weight, index]
        PriorityQueue<int[]> available = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) {
                    return Integer.compare(a[0], b[0]);
                }
                return Integer.compare(a[1], b[1]);
            }
        );

        // Busy servers: [finishTime, weight, index]
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> {
                int cmp = Long.compare(a[0], b[0]);

                if (cmp != 0) return cmp;

                cmp = Long.compare(a[1], b[1]);

                if (cmp != 0) return cmp;

                return Long.compare(a[2], b[2]);
            }
        );

        // Initially, all servers are available.
        for (int i = 0; i < n; i++) {
            available.offer(new int[]{servers[i], i});
        }

        long time = 0;

        for (int j = 0; j < m; j++) {

            time = Math.max(time, j);

            // Release servers that have finished.
            while (!busy.isEmpty() && busy.peek()[0] <= time) {

                long[] s = busy.poll();

                available.offer(new int[]{
                    (int) s[1],
                    (int) s[2]
                });
            }

            // If no server is free, wait.
            if (available.isEmpty()) {

                time = busy.peek()[0];

                // Release all servers free at this time.
                while (!busy.isEmpty() && busy.peek()[0] <= time) {

                    long[] s = busy.poll();

                    available.offer(new int[]{
                        (int) s[1],
                        (int) s[2]
                    });
                }
            }

            // Select the best available server.
            int[] s = available.poll();

            int weight = s[0];
            int index = s[1];

            ans[j] = index;

            // Calculate finishing time.
            long finishTime = time + tasks[j];

            busy.offer(new long[]{
                finishTime,
                weight,
                index
            });
        }

        return ans;
    }
}

```

## 4. Explanation of the code step by step

Let's understand every important part.

### Step 1: Initialize variables

```
int n = servers.length;
int m = tasks.length;

int[] ans = new int[m];
```

- `n` stores the total number of servers.
- `m` stores the total number of tasks.
- `ans` stores the index of the server assigned to each task.

### Step 2: Create the available Priority Queue

```
PriorityQueue<int[]> available = new PriorityQueue<>(
    (a, b) -> {
        if (a[0] != b[0]) {
            return Integer.compare(a[0], b[0]);
        }
        return Integer.compare(a[1], b[1]);
    }
);
```

Each element stores:

```
[weight, index]
```

Here:

- `a[0]` represents the server weight.
- `a[1]` represents the server index.

First, we compare weights.

If weights are equal, we compare indices.

This ensures that we always get the server with the smallest weight and then the smallest index.

### Step 3: Create the busy Priority Queue

```
PriorityQueue<long[]> busy = new PriorityQueue<>(
    (a, b) -> {
        int cmp = Long.compare(a[0], b[0]);

        if (cmp != 0) return cmp;

        cmp = Long.compare(a[1], b[1]);

        if (cmp != 0) return cmp;

        return Long.compare(a[2], b[2]);
    }
);
```

Each element stores:

```
[finishTime, weight, index]
```

We compare:

1. Finishing time.
2. Weight.
3. Index.

Why?

Because we need to identify which server becomes free first.

If two servers become free simultaneously, we also maintain their weight and index priorities.

Why `long[]` instead of `int[]`?

Because finishing times can become large when many tasks are processed.

Using `long` prevents integer overflow.

### Step 4: Initially, all servers are available

```
for (int i = 0; i < n; i++) {
    available.offer(new int[]{servers[i], i});
}
```

We insert every server into the available Priority Queue.

For example:

```
servers = [3, 3, 2]
```

The available heap contains:

```
[2, 2]
[3, 0]
[3, 1]
```

All servers are initially free.

### Step 5: Initialize current time

```
long time = 0;
```

This variable represents the actual time at which we are assigning a task.

Initially, time is zero.

### Step 6: Process tasks one by one

```
for (int j = 0; j < m; j++) {
```

We process every task in its original order.

### Step 7: Update current time

```
time = Math.max(time, j);
```

This is one of the most important lines.

Normally, task `j` arrives at time `j`.

But sometimes all servers are busy, and we may need to wait.

For example:

```
Task arrives at time = 4
Earliest server becomes free at = 7
```

We cannot assign the task at time 4.

We must wait until time 7.

Therefore:

```
time = Math.max(time, j);
```

It ensures that our current time never goes backwards and that we never process a task before it arrives.

### Step 8: Release completed servers

```
while (!busy.isEmpty() && busy.peek()[0] <= time) {

    long[] s = busy.poll();

    available.offer(new int[]{
        (int) s[1],
        (int) s[2]
    });
}
```

This checks whether any busy server has completed its task.

`busy.peek()[0]` gives the finishing time of the earliest-finishing server.

If its finishing time is less than or equal to the current time, the server is free.

We remove it from the busy heap and insert it into the available heap.

Why use `while` instead of `if`?

Because multiple servers can finish at the same time. We must release all of them.

### Step 9: Wait if all servers are busy

```
if (available.isEmpty()) {

    time = busy.peek()[0];

    while (!busy.isEmpty() && busy.peek()[0] <= time) {

        long[] s = busy.poll();

        available.offer(new int[]{
            (int) s[1],
            (int) s[2]
        });
    }
}
```

If the available heap is empty, it means every server is busy.

So what should we do?

1. Find the earliest finishing time using `busy.peek()[0]`.
2. Move the current time to that finishing time.
3. Release all servers that finish at or before that time.
4. Now we can assign the waiting task.

### Step 10: Select the best server

```
int[] s = available.poll();

int weight = s[0];
int index = s[1];

ans[j] = index;
```

We remove the best available server.

Because of our comparator, the selected server automatically has:

- The smallest weight.
- The smallest index in case of a tie.

We store its index in the answer array.

### Step 11: Calculate finishing time

```
long finishTime = time + tasks[j];
```

A server assigned a task at time `time` will become free after `tasks[j]` seconds.

Therefore:

\\[ \text{finishTime} = \text{currentTime} + \text{taskDuration} \\]

For example:

```
Current time = 5
Task duration = 3

Finish time = 5 + 3 = 8
```

The server will become available at time 8.

### Step 12: Insert the server into the busy heap

```
busy.offer(new long[]{
    finishTime,
    weight,
    index
});
```

After assigning the task, the server is no longer available.

We insert it into the busy heap with its finishing time, weight and index.

Finally:

```
return ans;
```

## 5. Dry Run with Example 1

Input:

```
servers = [3, 3, 2]
tasks   = [1, 2, 3, 2, 1, 2]
```

Initially:

```
Available = [2,2], [3,0], [3,1]
Busy      = []
Time      = 0
```

Let's process each task.

| Task | Arrival time | Actual time | Assigned server | Finish time |
| ---- | ------------ | ----------- | --------------- | ----------- |
| 0    | 0            | 0           | 2               | 1           |
| 1    | 1            | 1           | 2               | 3           |
| 2    | 2            | 2           | 0               | 5           |
| 3    | 3            | 3           | 2               | 5           |
| 4    | 4            | 4           | 1               | 5           |
| 5    | 5            | 5           | 2               | 7           |

Let's understand a few important steps.

Task 0:

- All servers are free.
- Server 2 has the smallest weight.
- Assign task 0 to server 2.
- It finishes at time 1.

Task 1:

- Server 2 becomes free at time 1.
- Assign task 1 to server 2 again.
- It finishes at time 3.

Task 2:

- Server 2 is busy.
- Servers 0 and 1 are available.
- Both have weight 3.
- Choose server 0 because its index is smaller.

Task 5:

- All three servers finish their tasks at time 5.
- All become available.
- Server 2 has the smallest weight.
- Assign task 5 to server 2.

Final output:

```
[2, 2, 0, 2, 1, 2]
```

## 6. Understanding your failing test case

Input:

```
servers = [31,96,73,90,15,11,1,90,72,9,30,88]

tasks = [87,10,3,5,76,74,38,64,16,64,93,95,60,79,54,26,30,44,64,71]
```

Expected output:

```
[6,9,5,4,10,5,0,8,4,2,11,9,3,7,1,4,0,4,1,8]
```

Notice that server 6 has weight 1, so it is selected first.

Server 9 has weight 9, so it is selected next when available.

The algorithm does not permanently prefer one server. Once a server becomes busy, it is removed from the available heap. When it finishes, it returns to the available heap and competes again according to its weight and index.

This is why maintaining both heaps is important.

## 7. Complexity Analysis

Let:

- `n` = number of servers.
- `m` = number of tasks.

| Operation             | Time complexity      |
| --------------------- | -------------------- |
| Insert all servers    | \\(O(n\log n)\\)     |
| Process tasks         | \\(O(m\log n)\\)     |
| Insert/remove servers | \\(O((n+m)\log n)\\) |

Overall Time Complexity:

\\[ \boxed{O((n+m)\log n)} \\]

Space Complexity:

\\[ \boxed{O(n+m)} \\]

The two Priority Queues together store at most `n` servers, requiring \\(O(n)\\) space. The answer array requires \\(O(m)\\) space.

## 8. Important concepts to remember

| Concept        | Why we use it                                     |
| -------------- | ------------------------------------------------- |
| Priority Queue | Efficiently select the best element               |
| Min Heap       | Get the smallest priority element                 |
| Two Heaps      | Separate free and busy servers                    |
| Greedy         | Always select the best currently available server |
| Simulation     | Process tasks according to time                   |
| `long`         | Prevent finishing-time overflow                   |

### Three important lines to remember

1\. Update time:

```
time = Math.max(time, j);
```

2\. Release completed servers:

```
while (!busy.isEmpty() && busy.peek()[0] <= time)
```

3\. Calculate finishing time:

```
long finishTime = time + tasks[j];
```

### Interview questions for practice

1. Why do we need two Priority Queues instead of one?
2. Why do we use `while` instead of `if` when releasing servers?
3. Why do we use `Math.max(time, j)`?
4. Why do we store finishing time instead of task duration in the busy heap?
5. Why do we use `long` for finishing time?

My suggestion: Before moving to the next problem, try explaining the purpose of both heaps without looking at the code. If you can explain why each heap has a different comparator, you have understood the core logic of this problem.