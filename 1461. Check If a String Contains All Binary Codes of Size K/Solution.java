import java.util.HashSet;

class Solution {
    public boolean hasAllCodes(String s, int k) {
        int total = 1 << k; // 2^k
        
        // Quick fail: not enough substrings possible
        if (s.length() - k + 1 < total) {
            return false;
        }

        HashSet<String> set = new HashSet<>();

        for (int i = 0; i <= s.length() - k; i++) {
            set.add(s.substring(i, i + k));
            
            if (set.size() == total) {
                return true;
            }
        }

        return false;
    }
}