import java.util.*;

class Solution {
    public List<String> removeInvalidParentheses(String s) {

        List<String> result = new ArrayList<>();

        // BFS queue
        Queue<String> queue = new LinkedList<>();
        queue.offer(s);

        // To avoid processing duplicate strings
        Set<String> visited = new HashSet<>();
        visited.add(s);

        // Once we find valid strings, don't generate next level
        boolean found = false;

        while (!queue.isEmpty()) {

            String current = queue.poll();

            // Check if current string is valid
            if (isValid(current)) {
                result.add(current);
                found = true;
            }

            // If valid strings are found, we have used
            // minimum number of removals.
            if (found) {
                continue;
            }

            // Try removing each parenthesis
            for (int i = 0; i < current.length(); i++) {

                // Only remove parentheses, not letters
                if (current.charAt(i) != '(' &&
                    current.charAt(i) != ')') {
                    continue;
                }

                String next =
                    current.substring(0, i) +
                    current.substring(i + 1);

                // Avoid duplicates
                if (visited.add(next)) {
                    queue.offer(next);
                }
            }
        }

        return result;
    }

    // Checks whether a string has valid parentheses
    private boolean isValid(String s) {

        int balance = 0;

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                balance++;
            } 
            else if (ch == ')') {
                balance--;

                // More closing parentheses than opening
                if (balance < 0) {
                    return false;
                }
            }
        }

        // All opening parentheses must be closed
        return balance == 0;
    }
}