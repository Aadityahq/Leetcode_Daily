import java.util.*;

class Solution {
    public String findLexSmallestString(String s, int a, int b) {
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        
        String smallest = s;
        queue.offer(s);
        visited.add(s);
        
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            if (curr.compareTo(smallest) < 0) {
                smallest = curr;
            }
            
            // Operation 1: Add 'a' to odd indices
            StringBuilder sb = new StringBuilder(curr);
            for (int i = 1; i < sb.length(); i += 2) {
                int newVal = (sb.charAt(i) - '0' + a) % 10;
                sb.setCharAt(i, (char) (newVal + '0'));
            }
            String added = sb.toString();
            
            if (visited.add(added)) queue.offer(added);
            
            // Operation 2: Rotate by 'b'
            String rotated = curr.substring(curr.length() - b) + curr.substring(0, curr.length() - b);
            if (visited.add(rotated)) queue.offer(rotated);
        }
        
        return smallest;
    }
}
