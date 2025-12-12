import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int[] countMentions(int numberOfUsers, List<List<String>> eventsInput) {
        int n = numberOfUsers;
        int[] mentions = new int[n];

        // Convert events into sortable form
        class Event {
            String type;
            int time;
            String payload;
            int index;

            Event(String t, int ti, String p, int idx) {
                type = t; time = ti; payload = p; index = idx;
            }
        }

        List<Event> events = new ArrayList<>();
        for (int i = 0; i < eventsInput.size(); i++) {
            List<String> e = eventsInput.get(i);
            events.add(new Event(
                e.get(0),
                Integer.parseInt(e.get(1)),
                e.get(2),
                i
            ));
        }

        // Sort by time, and OFFLINE before MESSAGE on same timestamp
        events.sort((a, b) -> {
            if (a.time != b.time) return a.time - b.time;
            if (!a.type.equals(b.type)) {
                if (a.type.equals("OFFLINE")) return -1;
                if (b.type.equals("OFFLINE")) return 1;
            }
            return a.index - b.index; // stable fallback
        });

        boolean[] online = new boolean[n];
        Arrays.fill(online, true);

        // Min-heap for users coming back online
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); 
        // each entry = {returnTime, userId}

        for (Event ev : events) {
            int curr = ev.time;

            // Bring back users whose offline time has ended
            while (!pq.isEmpty() && pq.peek()[0] <= curr) {
                int[] top = pq.poll();
                online[top[1]] = true;
            }

            if (ev.type.equals("OFFLINE")) {
                int user = Integer.parseInt(ev.payload);
                online[user] = false;
                pq.add(new int[]{curr + 60, user});
            } 
            else { // MESSAGE
                String msg = ev.payload;

                // CASE 1: ALL
                if (msg.equals("ALL")) {
                    for (int i = 0; i < n; i++) mentions[i]++;
                }
                // CASE 2: HERE
                else if (msg.equals("HERE")) {
                    for (int i = 0; i < n; i++)
                        if (online[i]) mentions[i]++;
                }
                // CASE 3: explicit id mentions
                else {
                    String[] arr = msg.split(" ");
                    for (String s : arr) {
                        if (s.startsWith("id")) {
                            int uid = Integer.parseInt(s.substring(2));
                            mentions[uid]++;
                        }
                    }
                }
            }
        }

        return mentions;
    }
}
