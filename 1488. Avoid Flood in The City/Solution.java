import java.util.*;

class Solution {
    public int[] avoidFlood(int[] rains) {
        int n = rains.length;
        int[] ans = new int[n];
        Map<Integer, Integer> lakeMap = new HashMap<>(); // lake -> last day filled
        TreeSet<Integer> dryDays = new TreeSet<>();      // days where we can dry
       
        for (int i = 0; i < n; i++) {
            if (rains[i] == 0) {
                dryDays.add(i);
                ans[i] = 1;  // temporarily assign 1, we’ll update if needed
            } else {
                int lake = rains[i];
                ans[i] = -1;
                if (lakeMap.containsKey(lake)) {
                    int lastRainDay = lakeMap.get(lake);
                    Integer dryDay = dryDays.higher(lastRainDay); // find next dry day
                    if (dryDay == null) {
                        return new int[0]; // flood!
                    }
                    ans[dryDay] = lake; // dry this lake
                    dryDays.remove(dryDay);
                }
                lakeMap.put(lake, i);
            }
        }
        return ans;
    }
}
