import java.util.*;

class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();

        // Score outside all parentheses
        stack.push(0);

        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                // Start a new level
                stack.push(0);
            } else {
                // Finish the current level
                int innerScore = stack.pop();

                int value;

                if (innerScore == 0) {
                    // "()"
                    value = 1;
                } else {
                    // "(A)"
                    value = 2 * innerScore;
                }

                // Add score to the previous level
                stack.push(stack.pop() + value);
            }
        }

        return stack.peek();
    }
}