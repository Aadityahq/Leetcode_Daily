# 3408. Design Task Manager

This document explains the solution for LeetCode problem **3408. Design Task Manager** using Java.

## Problem Overview

You are to design a task management system that allows users to manage their tasks, each associated with a priority. The system must efficiently support adding, modifying, executing, and removing tasks.

### Operations

- **Initialization:**  
    `TaskManager(List<List<Integer>> tasks)` initializes the manager with a list of `[userId, taskId, priority]` triples.

- **Add Task:**  
    `add(int userId, int taskId, int priority)` adds a new task for a user.

- **Edit Task:**  
    `edit(int taskId, int newPriority)` updates the priority of an existing task.

- **Remove Task:**  
    `rmv(int taskId)` removes a task from the system.

- **Execute Top Task:**  
    `execTop()` executes and removes the task with the highest priority (breaking ties by highest taskId), returning the associated userId. Returns -1 if no tasks are available.

## How the Solution Works

- **Data Structures:**  
    - A `HashMap<Integer, Task>` to store task information for O(1) access and updates.
    - A `PriorityQueue<Task>` (max-heap) to efficiently retrieve the task with the highest priority and taskId.

- **Operations:**  
    - Adding, editing, and removing tasks update both the hash map and the heap as needed.
    - Executing the top task polls from the heap and removes the task from the hash map.

## Why This Approach

- **Efficiency:**  
    - Hash maps provide constant time complexity for add, edit, and remove operations.
    - The max-heap ensures efficient retrieval of the highest-priority task.

- **Scalability:**  
    - The design supports a large number of tasks and frequent updates, as required by the problem constraints.

- **Correctness:**  
    - The approach guarantees that `execTop()` always returns the correct userId according to the problem's rules.

## Usage

1. Initialize the `TaskManager` with the initial list of tasks.
2. Use `add`, `edit`, `rmv`, and `execTop` methods to manage and execute tasks as required.

## Example

```java
// Initialize with tasks: [userId, taskId, priority]
List<List<Integer>> tasks = Arrays.asList(
    Arrays.asList(1, 101, 5),
    Arrays.asList(2, 102, 10)
);
TaskManager tm = new TaskManager(tasks);

tm.add(3, 103, 7);         // Adds task 103 for user 3 with priority 7
tm.edit(101, 12);          // Updates task 101's priority to 12
tm.rmv(102);               // Removes task 102
int user = tm.execTop();   // Executes task 101 (highest priority), returns 1
```

## Example Walkthrough

- After initialization:  
    - Tasks: 101 (priority 5, user 1), 102 (priority 10, user 2)
- After `add(3, 103, 7)`:  
    - Tasks: 101 (5, 1), 102 (10, 2), 103 (7, 3)
- After `edit(101, 12)`:  
    - Tasks: 101 (12, 1), 102 (10, 2), 103 (7, 3)
- After `rmv(102)`:  
    - Tasks: 101 (12, 1), 103 (7, 3)
- After `execTop()`:  
    - Executes and removes 101 (highest priority), returns 1

---

For detailed code and more examples, refer to the solution file.