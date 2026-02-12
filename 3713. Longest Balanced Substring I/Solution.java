import java.util.*;

class Solution {
    public int longestBalanced(String s) {
        Map<Character, Integer> mp = new HashMap<>();
        int sz = s.length(), ans = 0;

        for (int i = 0; i < sz; i++) {
            mp.clear(); // Emptying the hash map for the new starting point
            for (int j = i; j < sz; j++) {
                char c = s.charAt(j);
                mp.put(c, mp.getOrDefault(c, 0) + 1);
                
                int cnt = mp.get(c);
                boolean flag = true;
                
                for (int count : mp.values()) {
                    if (count != cnt) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }
        return ans;
    }
}