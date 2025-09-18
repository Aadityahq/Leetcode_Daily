import java.util.*;

public class TaskManager {
    private static class Task {
        int userId;
        int taskId;
        int priority;

        Task(int userId, int taskId, int priority) {
            this.userId = userId;
            this.taskId = taskId;
            this.priority = priority;
        }
    }

    // Max-heap: higher priority first; if equal priority, higher taskId first
    private final PriorityQueue<Task> pq;
    // Latest (authoritative) state for each taskId
    private final Map<Integer, Task> taskMap;

    // Constructor accepts List<List<Integer>> like LeetCode driver would pass
    public TaskManager(List<List<Integer>> tasks) {
        pq = new PriorityQueue<>((a, b) -> {
            if (a.priority != b.priority) return Integer.compare(b.priority, a.priority);
            return Integer.compare(b.taskId, a.taskId);
        });
        taskMap = new HashMap<>();

        if (tasks != null) {
            for (List<Integer> t : tasks) {
                int userId = t.get(0);
                int taskId = t.get(1);
                int priority = t.get(2);
                Task task = new Task(userId, taskId, priority);
                taskMap.put(taskId, task);
                pq.offer(task);
            }
        }
    }

    public void add(int userId, int taskId, int priority) {
        Task task = new Task(userId, taskId, priority);
        taskMap.put(taskId, task);
        pq.offer(task);
    }

    public void edit(int taskId, int newPriority) {
        Task cur = taskMap.get(taskId);
        if (cur != null) {
            Task updated = new Task(cur.userId, taskId, newPriority);
            taskMap.put(taskId, updated);
            pq.offer(updated);
        }
    }

    public void rmv(int taskId) {
        // lazy removal: remove authoritative entry; PQ entries will be ignored later
        taskMap.remove(taskId);
    }

    public int execTop() {
        while (!pq.isEmpty()) {
            Task top = pq.peek();
            Task valid = taskMap.get(top.taskId);

            // accept only if the PQ entry exactly matches the current authoritative entry
            if (valid != null && valid.priority == top.priority && valid.userId == top.userId) {
                pq.poll();
                taskMap.remove(top.taskId);
                return top.userId;
            } else {
                // outdated or removed entry — discard
                pq.poll();
            }
        }
        return -1;
    }
}
