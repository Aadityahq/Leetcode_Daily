import java.util.*;

class Router {
    private int memoryLimit;
    private Deque<int[]> queue; // store packets as [source, dest, ts]
    private Set<String> seen;  // "src#dst#ts"
    private Map<Integer, ArrayList<Integer>> destTimes; // dest -> list of timestamps (in insertion order)
    private Map<Integer, Integer> destStart; // dest -> current start index (lazy deletion)

    public Router(int memoryLimit) {
        this.memoryLimit = memoryLimit;
        this.queue = new ArrayDeque<>();
        this.seen = new HashSet<>();
        this.destTimes = new HashMap<>();
        this.destStart = new HashMap<>();
    }

    public boolean addPacket(int source, int destination, int timestamp) {
        String key = source + "#" + destination + "#" + timestamp;
        if (seen.contains(key)) return false;

        // Evict oldest if memory full
        if (queue.size() == memoryLimit) {
            int[] old = queue.pollFirst();
            String oldKey = old[0] + "#" + old[1] + "#" + old[2];
            seen.remove(oldKey);

            // remove from that destination's front (lazy)
            ArrayList<Integer> list = destTimes.get(old[1]);
            int start = destStart.getOrDefault(old[1], 0);
            // Sanity: list.get(start) should equal old[2] (if logic correct)
            destStart.put(old[1], start + 1);
            if (destStart.get(old[1]) >= list.size()) {
                destTimes.remove(old[1]);
                destStart.remove(old[1]);
            }
        }

        // add new packet
        queue.addLast(new int[]{source, destination, timestamp});
        seen.add(key);

        destTimes.computeIfAbsent(destination, k -> new ArrayList<>()).add(timestamp);
        destStart.putIfAbsent(destination, 0);
        return true;
    }

    public int[] forwardPacket() {
        if (queue.isEmpty()) return new int[]{}; // empty
        int[] pkt = queue.pollFirst();
        String key = pkt[0] + "#" + pkt[1] + "#" + pkt[2];
        seen.remove(key);

        ArrayList<Integer> list = destTimes.get(pkt[1]);
        int start = destStart.getOrDefault(pkt[1], 0);
        // Sanity: list.get(start) should equal pkt[2]
        destStart.put(pkt[1], start + 1);
        if (destStart.get(pkt[1]) >= list.size()) {
            destTimes.remove(pkt[1]);
            destStart.remove(pkt[1]);
        }
        return pkt;
    }

    public int getCount(int destination, int startTime, int endTime) {
        if (!destTimes.containsKey(destination)) return 0;
        ArrayList<Integer> list = destTimes.get(destination);
        int startIdx = destStart.getOrDefault(destination, 0);
        int n = list.size();
        if (startIdx >= n) return 0;

        int lo = lowerBound(list, startIdx, n - 1, startTime);
        if (lo == -1) return 0;
        int hi = upperBound(list, startIdx, n - 1, endTime);
        if (hi == -1 || lo > hi) return 0;
        return hi - lo + 1;
    }

    // first index in [l..r] with val >= target, or -1
    private int lowerBound(ArrayList<Integer> a, int l, int r, int target) {
        int ans = -1;
        int lo = l, hi = r;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a.get(mid) >= target) {
                ans = mid;
                hi = mid - 1;
            } else lo = mid + 1;
        }
        return ans;
    }

    // last index in [l..r] with val <= target, or -1
    private int upperBound(ArrayList<Integer> a, int l, int r, int target) {
        int ans = -1;
        int lo = l, hi = r;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (a.get(mid) <= target) {
                ans = mid;
                lo = mid + 1;
            } else hi = mid - 1;
        }
        return ans;
    }
}
